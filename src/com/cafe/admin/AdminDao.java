package com.cafe.admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {

	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/cafe_db";
	private String id = "cafe";
	private String pw = "cafe";

	// 생성자

	// 메소드 gs

	// 메소드 일반

	// DB연결 메소드
	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 자원정리 메소드
	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 영수증 리스트 전체출력, 리스트 새로고침
	public List<ReceiptVo> selectReceiptAll(String state) {

		List<ReceiptVo> receiptList = new ArrayList<ReceiptVo>();

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " select receipt_id, ";
			query += "        user_id, ";
			query += "		  receipt_date, ";
			query += "        receipt_finish, ";
			query += "        receipt_state ";
			query += " from Receipt ";
			query += " where receipt_state = ? ";
			query += " order by receipt_id ";

			// *바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, state);

			// *실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			// 리스트로 만들기
			while (rs.next()) {
				int id = rs.getInt("receipt_id");
				int userId = rs.getInt("user_id");
				String date = rs.getString("receipt_date");
				String finish = rs.getString("receipt_finish");
				String receiptState = rs.getString("receipt_state");

				ReceiptVo receiptVo = new ReceiptVo(id, userId, date, finish, receiptState);
				receiptList.add(receiptVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return receiptList; // 리스트의 *주소를 리턴해준다

	}

	// 영수증 번호를 입력받으면 회원이 주문한 영수증 번호, 음료, 수량 출력
	public List<UserOrderVo> selectReceiptOne(int id) {
		
		List<UserOrderVo> userOrderList = new ArrayList<UserOrderVo>();
		//UserOrderVo userOrderVo = null;
		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " SELECT u.receipt_id, ";
			query += "		  d.drink_name, ";
			query += "        u.drink_cnt ";
			query += " FROM UserOrder u ";
			query += " INNER JOIN Drink d ";
			query += " ON u.drink_id = d.drink_id ";
			query += " WHERE u.receipt_id = ? ";
			// *바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);

			// *실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			// 리스트로 만들기
			while (rs.next()) {

				int receiptId = rs.getInt("receipt_id");
				String name = rs.getString("drink_name");
				int cnt = rs.getInt("drink_cnt");

				UserOrderVo userOrderVo = new UserOrderVo(receiptId, name, cnt);
				userOrderList.add(userOrderVo);
				
				//userOrderVo = new UserOrderVo(receiptId, name, cnt);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return userOrderList; // 리스트의 *주소를 리턴해준다

	}

	// 주문상태를 완료로 수정
	public int updateState(String state, int id) { // 입력받은 영수증 번호, 상태
		int count = -1;

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " update Receipt ";
			query += " set receipt_state = ? ";
			query += " where receipt_id = ? ";

			// *바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, state);
			pstmt.setInt(2, id);

			// *실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 수정되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return count;
	}

	// 날짜입력으로 판매 상품 출력
	public List<SaleAmountVo> selectSaleAmountAll(String orderDate) {

		List<SaleAmountVo> saleAmountList = new ArrayList<SaleAmountVo>();

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " select u.user_id, ";
			query += " 		  d.drink_name, ";
			query += "		  sum(uo.drink_cnt) AS drink_cnt, ";
			query += "        d.drink_price, ";
			query += "        sum(uo.drink_cnt * d.drink_price) AS sum_price, ";
			query += "        date_format(r.receipt_date, '%Y-%m-%d') AS receipt_date, ";
			query += "        date_format(r.receipt_date, '%H:%i:%s') AS receipt_time ";
			query += " from UserOrder uo ";
			query += " inner join Drink d  ";
			query += " on uo.drink_id = d.drink_id ";
			query += " inner join Receipt r ";
			query += " on uo.receipt_id = r.receipt_id ";
			query += " inner join Users u ";
			query += " on r.user_id = u.user_id ";
			query += " where date_format(r.receipt_date, '%Y-%m-%d') = ? ";
			query += " group by d.drink_name, d.drink_price, r.receipt_date, u.user_id ";

			// *바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, orderDate);

			// *실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			// 리스트로 만들기
			while (rs.next()) {
				int id = rs.getInt("user_id");
				String drinkName = rs.getString("drink_name");
				int drinkCnt = rs.getInt("drink_cnt");
				int drinkPrice = rs.getInt("drink_price");
				int saleAmountSum = rs.getInt("sum_price");
				String ymd = rs.getString("receipt_date");
				String rtime = rs.getString("receipt_time");

				SaleAmountVo saleAmountVo = new SaleAmountVo(id,drinkName, drinkCnt, drinkPrice, saleAmountSum, ymd, rtime);
				saleAmountList.add(saleAmountVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return saleAmountList; // 리스트의 *주소를 리턴해준다

	}

	// 상품 리스트 추가
	public int insertDrink(String name, String content, int price) {

		int count = -1;

		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " insert into Drink ";
			query += " values(null, ?, ?, ?) ";

			// *바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, content);
			pstmt.setInt(3, price);

			// *실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 등록되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return count;
	}

	// 상품 리스트 수정
	public int updateDrink(String name, String content, int price, int id) {

		int count = -1;

		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " update Drink ";
			query += " set drink_name = ?, ";
			query += " 	   drink_content = ?, ";
			query += " 	   drink_price = ? ";
			query += " where drink_id = ? ";

			// *바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, content);
			pstmt.setInt(3, price);
			pstmt.setInt(4, id);

			// *실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 업데이트 되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return count;
	}

	// 상품 리스트 삭제
	public int deleteDrink(int id) {

		int count = -1;

		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " delete from Drink ";
			query += " where drink_id = ? ";

			// *바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);

			// *실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return count;
	}

	// 상품 리스트 전체 출력
	public List<DrinkVo> selectDrinkAll() {

		List<DrinkVo> drinkList = new ArrayList<DrinkVo>();

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " select drink_id, ";
			query += "		  drink_name, ";
			query += "        drink_content, ";
			query += "        drink_price ";
			query += " from Drink ";
			query += " order by drink_id ";

			// *바인딩
			pstmt = conn.prepareStatement(query);

			// *실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			// 리스트로 만들기
			while (rs.next()) {
				int id = rs.getInt("drink_id");
				String name = rs.getString("drink_name");
				String content = rs.getString("drink_content");
				int price = rs.getInt("drink_price");

				DrinkVo drinkVo = new DrinkVo(id, name, content, price);
				drinkList.add(drinkVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return drinkList; // 리스트의 *주소를 리턴해준다

	}

}