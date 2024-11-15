package dto;

public class CartDTOShw1013 {
    private int cartId;          // 장바구니 항목의 고유 ID    
    private int userNum;         // 사용자 번호
    private String userName;         // 사용자 이름
    private int productId;       // 제품 ID
    private String productName;  // 제품 이름
    private int price;        // 제품 가격
    private int quantity;        // 구매 수량
    private int stock;           // 제품 재고

    // 기본 생성자
    public CartDTOShw1013() {}

    // 매개변수 있는 생성자
    public CartDTOShw1013(int cartId, int userNum,  String userName, int productId, String productName, int price, int quantity, int stock) {
        this.cartId = cartId;
        this.userNum = userNum;
        this.userName = userName;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.stock = stock;
    }
    public CartDTOShw1013(int cartId, int userNum,  String userName,String productName, int price, int quantity, int stock) {
        this.cartId = cartId;
        this.userNum = userNum;
        this.userName = userName;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.stock = stock;
    }

    public CartDTOShw1013(int cartId, String productName, int price, int quantity, int stock) {
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

    public int getUserNum() {
        return userNum;
    }
    
    public String getUserName() {
        return userName;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
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

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }
    
    public void setUserNum(String userName) {
        this.userName = userName;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(int price) {
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
        return "CartDTOShw1013{" +
                "cartId=" + cartId +
                ", userNum=" + userNum +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", stock=" + stock +
                '}';
    }
}
