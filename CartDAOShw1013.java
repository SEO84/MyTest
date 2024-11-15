package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dto.CartDTOShw1013;

public class CartDAOShw1013 {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "scott";
    private static final String PASSWORD = "tiger";

    // DB 연결 생성 메서드
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // ResultSet 데이터를 CartDTO로 변환
    private CartDTOShw1013 mapResultSetToCartDTO(ResultSet rs) throws SQLException {
        return new CartDTOShw1013(
                rs.getInt("cart_id"),
                rs.getInt("user_num"),
                rs.getString("username"),
                rs.getString("product_name"),
                rs.getInt("price"),
                rs.getInt("quantity"),
                rs.getInt("stock")
        );
    }

    // 장바구니 항목 전체 조회
    public List<CartDTOShw1013> getAllCartItems() {
        String query = "SELECT c.cart_id, u.user_num, u.username, p.name AS product_name, p.price, c.quantity, p.stock "
                     + "FROM cart_t c "
                     + "JOIN product_t p ON c.product_id = p.product_id "
                     + "JOIN user_t u ON c.user_num = u.user_num";

        List<CartDTOShw1013> cartItems = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                cartItems.add(mapResultSetToCartDTO(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    // 공통 쿼리 실행 메서드 (INSERT, UPDATE, DELETE)
    private void executeUpdate(String query, Object... params) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 장바구니 항목 추가
    public void addCartItem(CartDTOShw1013 cartItem) {
        String query = "INSERT INTO cart_t (cart_id, user_num, quantity) VALUES (?, ?, ?)";
        executeUpdate(query, cartItem.getCartId(), cartItem.getUserNum(), cartItem.getQuantity());
    }

    // 장바구니 항목 업데이트
    public void updateCartItem(int cartId, int quantity) {
        String query = "UPDATE cart_t SET quantity = ? WHERE cart_id = ?";
        executeUpdate(query, quantity, cartId);
    }

    // 장바구니 항목 삭제
    public void deleteCartItem(int cartId) {
        String query = "DELETE FROM cart_t WHERE cart_id = ?";
        executeUpdate(query, cartId);
    }

    // 결제 처리 (주문 및 상세 정보 저장, 재고 업데이트, 장바구니 비우기)
    public void completeOrder(int userNum) {
        String insertOrderQuery = "INSERT INTO order_t (order_id, user_num, order_date, total_amount, status) "
                                + "VALUES (ORDER_SEQ.NEXTVAL, ?, SYSDATE, ?, 'COMPLETED')";
        String selectCartQuery = "SELECT c.product_id, c.quantity, p.price FROM cart_t c "
                               + "JOIN product_t p ON c.product_id = p.product_id WHERE c.user_num = ?";
        String insertOrderDetailQuery = "INSERT INTO o_detail_t (order_detail_id, order_id, product_id, quantity, price) "
                                       + "VALUES (ORDER_DETAIL_SEQ.NEXTVAL, ORDER_SEQ.CURRVAL, ?, ?, ?)";
        String updateStockQuery = "UPDATE product_t SET stock = stock - ? WHERE product_id = ?";
        String clearCartQuery = "DELETE FROM cart_t WHERE user_num = ?";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false); // 트랜잭션 시작

            try (PreparedStatement selectCartStmt = conn.prepareStatement(selectCartQuery);
                 PreparedStatement insertOrderStmt = conn.prepareStatement(insertOrderQuery);
                 PreparedStatement insertOrderDetailStmt = conn.prepareStatement(insertOrderDetailQuery);
                 PreparedStatement updateStockStmt = conn.prepareStatement(updateStockQuery);
                 PreparedStatement clearCartStmt = conn.prepareStatement(clearCartQuery)) {

                // 카트 정보 가져오기
                selectCartStmt.setInt(1, userNum);
                ResultSet rs = selectCartStmt.executeQuery();

                int totalAmount = 0;

                // 카트 데이터 처리
                while (rs.next()) {
                    int productId = rs.getInt("product_id");
                    int quantity = rs.getInt("quantity");
                    int price = rs.getInt("price");

                    totalAmount += price * quantity;

                    // 주문 상세 정보 추가
                    insertOrderDetailStmt.setInt(1, productId);
                    insertOrderDetailStmt.setInt(2, quantity);
                    insertOrderDetailStmt.setInt(3, price * quantity);
                    insertOrderDetailStmt.executeUpdate();

                    // 재고 업데이트
                    updateStockStmt.setInt(1, quantity);
                    updateStockStmt.setInt(2, productId);
                    updateStockStmt.executeUpdate();
                }

                // 주문 정보 추가
                insertOrderStmt.setInt(1, userNum);
                insertOrderStmt.setInt(2, totalAmount);
                insertOrderStmt.executeUpdate();

                // 장바구니 비우기
                clearCartStmt.setInt(1, userNum);
                clearCartStmt.executeUpdate();

                conn.commit(); // 트랜잭션 커밋
            } catch (SQLException e) {
                conn.rollback(); // 트랜잭션 롤백
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
