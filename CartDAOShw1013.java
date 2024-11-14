package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.CartDTOShw1013;

public class CartDAOShw1013 {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "scott";
    private static final String PASSWORD = "tiger";

    // 장바구니 항목 전체 조회
    public List<CartDTOShw1013> getAllCartItems() {
        List<CartDTOShw1013> cartItems = new ArrayList<>();
        String query = "SELECT cart_id, product_name, price, quantity, stock FROM CART";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
            	CartDTOShw1013 item = new CartDTOShw1013(
                    rs.getInt("cart_id"),           // cart_id
                    rs.getString("product_name"),   // product_name
                    rs.getDouble("price"),          // price
                    rs.getInt("quantity"),          // quantity
                    rs.getInt("stock")              // stock
                );
                cartItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    // 장바구니 항목 추가
    public void addCartItem(CartDTOShw1013 cartItem) {
        String query = "INSERT INTO CART (cart_id, product_name, price, quantity, stock) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, cartItem.getCartId());
            pstmt.setString(2, cartItem.getProductName());
            pstmt.setDouble(3, cartItem.getPrice());
            pstmt.setInt(4, cartItem.getQuantity());
            pstmt.setInt(5, cartItem.getStock());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 장바구니 항목 업데이트
    public void updateCartItem(int cartId, int quantity) {
        String query = "UPDATE CART SET quantity = ? WHERE cart_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, quantity);
            pstmt.setInt(2, cartId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 장바구니 항목 삭제
    public void deleteCartItem(int cartId) {
        String query = "DELETE FROM CART WHERE cart_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, cartId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
