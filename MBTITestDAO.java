package Shw1013_MBTI;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MBTITestDAO {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USERID = "scott";
	private static final String PASSWORD = "tiger";

	public MBTITestDAO() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// 결과 추가
	public int addResult(MBTITestDTO result) {
		String sql = "insert into mbti_results3(" + "id,name,phone,mbti" + ") "
				+ "values(mbti_results3_seq.NEXTVAL,?,?,?)";
		int rowsAffected = 0;

		try (Connection con = DriverManager.getConnection(URL, USERID, PASSWORD);
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, result.getName());
			pstmt.setString(2, result.getPhone());
			pstmt.setString(3, result.getMbti());

			rowsAffected = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowsAffected;
	}

	// 모든 결과 조회
	public List<MBTITestDTO> getAllResults() {
		List<MBTITestDTO> results = new ArrayList<>();
		String sql = "SELECT id, name, phone, mbti FROM mbti_results3";

		try (Connection con = DriverManager.getConnection(URL, USERID, PASSWORD);
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String mbti = rs.getString("mbti");

				results.add(new MBTITestDTO(id, name, phone, mbti));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return results;
	}
}
