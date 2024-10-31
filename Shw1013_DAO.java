package ex_241023_cha15.homework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Shw1013_DAO {
    private final String driver = "oracle.jdbc.driver.OracleDriver"; // 드라이버 정보
    private final String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private final String userid = "scott";
    private final String passwd = "tiger";

    // 생성자에서 드라이버 로딩
    public Shw1013_DAO() {
        try {
            Class.forName(driver); // 드라이버를 로딩
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addUser(Shw1013_DTO user) {
        String sql = "INSERT INTO member501 (id, name, email, password) VALUES (member501_seq.NEXTVAL, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.executeUpdate();
            System.out.println("사용자 추가 성공: " + user.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Shw1013_DTO> getAllUsers() {
        List<Shw1013_DTO> users = new ArrayList<>();
        String sql = "SELECT name, email, password FROM member501";
        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Shw1013_DTO user = new Shw1013_DTO(rs.getString("name"), rs.getString("email"), rs.getString("password"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
