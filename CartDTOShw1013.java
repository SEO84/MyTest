package dto;

public class CartDTOShw1013 {
    private int cartId;          // 장바구니 항목의 고유 ID
    private String productName;  // 제품 이름
    private double price;        // 제품 가격
    private int quantity;        // 구매 수량
    private int stock;           // 제품 재고

    // 기본 생성자
    public CartDTOShw1013() {}

    // 매개변수 있는 생성자
    public CartDTOShw1013(int cartId, String productName, double price, int quantity, int stock) {
        this.cartId = cartId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.stock = stock;
    }

    // Getter 메서드
    public int getCartId() {
        return cartId;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getStock() {
        return stock;
    }

    // Setter 메서드
    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // toString() 메서드 (객체 정보를 문자열로 반환)
    @Override
    public String toString() {
        return "CartDTO{" +
                "cartId=" + cartId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", stock=" + stock +
                '}';
    }
}
