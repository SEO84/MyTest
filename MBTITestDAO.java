package Shw1013_MBTI;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MBTITestDAO {

    // 생성자: JDBC 드라이버를 로드
    public MBTITestDAO() {
        try {
            Class.forName(DBConfig.DRIVER); // Oracle JDBC 드라이버 로드
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // 드라이버 로드 실패 시 스택 트레이스 출력
        }
    }

    // 결과 추가 메소드
    public int addResult(MBTITestDTO result) {
        // SQL 쿼리: 새로운 결과를 mbti_results3 테이블에 삽입
        String sql = "insert into mbti_results3(id, name, phone, mbti) values(mbti_results3_seq.NEXTVAL, ?, ?, ?)";
        int rowsAffected = 0; // 영향을 받은 행의 수를 저장할 변수

        try (Connection con = DriverManager.getConnection(DBConfig.URL, DBConfig.USERID, DBConfig.PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            // PreparedStatement에 파라미터 설정
            pstmt.setString(1, result.getName());
            pstmt.setString(2, result.getPhone());
            pstmt.setString(3, result.getMbti());

            // SQL 실행 및 영향을 받은 행의 수 반환
            rowsAffected = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // SQL 예외 발생 시 스택 트레이스 출력
        }

        return rowsAffected; // 삽입된 행의 수 반환
    }

    // 모든 결과 조회 메소드
    public List<MBTITestDTO> getAllResults() {
        List<MBTITestDTO> results = new ArrayList<>(); // 결과를 저장할 리스트
        String sql = "SELECT id, name, phone, mbti FROM mbti_results3"; // SQL 쿼리: 모든 결과 조회

        try (Connection con = DriverManager.getConnection(DBConfig.URL, DBConfig.USERID, DBConfig.PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // 결과 집합에서 데이터를 읽어 리스트에 추가
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String mbti = rs.getString("mbti");

                results.add(new MBTITestDTO(id, name, phone, mbti)); // DTO 객체 생성 및 리스트에 추가
            }

        } catch (SQLException e) {
            e.printStackTrace(); // SQL 예외 발생 시 스택 트레이스 출력
        }

        return results; // 조회된 결과 리스트 반환
    }
}
