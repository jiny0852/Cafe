package com.javaex.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.ex05.BookVo;


public class UserDao {

	// 0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/cafe_db";
	private String id = "cafe";
	private String pw = "cafe";

	// 생성자

	// 메서드 gs

	// 메서드 일반

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

	//////////////////////////////////////////////////////
	//////////////////////////////////////////////////////

	// 회원 로그인 조회 -- selectone scan id pw
	public UserVo userLogin(String uid, String upw) {

		int count = -1;

		this.getConnection();

		UserVo userVo = null;

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " select  	user_id, ";
			query += " 			id, ";
			query += " 		    pw, ";
			query += "     		user_name, ";
			query += "     		user_hp ";
			query += " from users ";
			query += " where id = ? ";
			query += " and pw = ? ";

			// *바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, uid);
			pstmt.setString(2, upw);

			// - 실행
			rs = pstmt.executeQuery();

			// 4.결과처리

			while (rs.next()) {

				int userId = rs.getInt("user_id");
				String id = rs.getString("id");
				String pw = rs.getString("pw");
				String name = rs.getString("user_name");
				String hp = rs.getString("user_name");

				userVo = new UserVo(userId, id, pw, name, hp);

				count++;

			}
			System.out.println("로그인 되었습니다");

		} catch (SQLException e) {
			System.out.println("error:" + e);
			System.out.println("아이디와 비번을 확인해주세요");
		}

		this.close();

		return userVo;

	}

	// 회원 정보 수정 -- update scan name ph id pw
	public int updateUser(String id, String pw, String name, String hp, int userId) {
		int count = -1;

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " update Users ";
			query += " set id = ?, ";
			query += "     pw = ?, ";
			query += "     user_name = ?, ";
			query += "     user_hp = ? ";
			query += " where user_id = ? ";

			// *바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			pstmt.setString(4, hp);
			pstmt.setInt(5, userId);

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

	// 회원 번호를 입력해주세요
	// 회원 주문 정보 조회 -- selectall by userid
	
	public List<AllVo> selectAllOrderReceipt(int userId) {

		System.out.println("전체 조회 로직");

		//리스트 만들기
		List<AllVo> orderReceiptList = new ArrayList<AllVo>();

		int count = -1; //최소값을 일부러 -1로 넣는다 
		
		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행

			// - sql문 준비
			String query = ""; 
			query += " select   r.receipt_id, ";
			query += " 			r.receipt_finish ";
			query += " from receipt r, userOrder o, (select user_id, user_name ";
			query += " 								 from users where user_id = ?) u ";
			query += " where u.user_id = r.user_id ";
			query += " and r.receipt_id = o.receipt_id ";

			// - 바인딩
			pstmt = conn.prepareStatement(query);

			// - 실행
			rs = pstmt.executeQuery();
			pstmt.setInt(1, userId);


			// 4.결과처리
						
			while (rs.next()) {
			
				int receiptId = rs.getInt("r.receipt_id");
				String receiptFinish = rs.getString("r.receipt_finish");
				
				AllVo userVo = new AllVo(receiptId, receiptFinish);
				orderReceiptList.add(userVo);
				
				count++; 
				
			}
			
			System.out.println(count + "건 조회 되었습니다.");

			
		}  catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();


		return orderReceiptList;
	}
	
	
	//회원 주문 정보 상세 조회 -- selectone by userid
	
	public AllVo selectOneReceipt(int receiptId) {

		AllVo userVo = new AllVo();
		
		int count = -1;

		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행

			// - sql문 준비
			String query = ""; 
			query += " select   o.userOrder_id, ";
			query += "			d.drink_name, ";
			query += " 			o.drink_cnt ";
			query += " from receipt r, userOrder o, drink d ";
			query += " where r.receipt_id = o.receipt_id ";
			query += " and d.drink_id = o.drink_id ";
			query += " and r.receipt_id = ? ";

			// - 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, receiptId);
			

			// - 실행
			rs = pstmt.executeQuery();
			

			// 4.결과처리
						
			while (rs.next()) {
			
				int userOrderId = rs.getInt("o.userOrder_id");
				int drinkId = rs.getInt("d.drink_name");
				int drinkCnt = rs.getInt("o.drink_cnt");
				
				userVo.setUserOrderId(userOrderId);
				userVo.setDrinkId(drinkId);
				userVo.setDrinkCnt(drinkCnt);
				
			}
			
			
			System.out.println( (count+2) + "건 조회 되었습니다.");

			
		}  catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return userVo;
	}
	
	

	// 회원가입 -- user insert

	public int insertUser(String id, String pw, String userName, String userHp) {

		int count = -1;

		// DB연결 메소드 호출
		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행

			// - sql문 준비
			String query = "";
			query += " insert into users ";
			query += " values(null, ?, ?, ?, ?) ";

			// - 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, userName);
			pstmt.setString(4, userHp);

			// - 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 등록 되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return count;
	}

	// 상품 출력 -- drink list selectall
	
	
	
	

	// 상품 선택 -- receipt insert scan cnt
	
	/*
	주문 음료 리스트로 가지고 있어라 0 
    
    userDao.insetReceipt(loginNo);
   int receiptId = userDao.selectReceiptId();
   
   반복  리스트의 사이즈만큼
   userDao.insertOrderUser(receiptId,회원번호, food, mount )
   반복*/
   //int receiptId = userDao.selectReceiptId();
	public int selectReceiptId(loginNo) {
		
		
		UserOrderVo usVo = new UserOrderVo();
		int receiptId = 0;
		
		int count = -1; //최소값을 일부러 -1로 넣는다 

		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행

			// - sql문 준비
			String query = ""; 
			query += " select receipt_id  ";
			query += "	from userOrder ";
			query += " 	where user_id = ? ";

			// - 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, loginNo);
			

			// - 실행
			rs = pstmt.executeQuery();
			
			
			// 4.결과처리
			rs.next();
			receiptId = rs.getInt("receipt_id");
			

			/*
						
			while (rs.next()) {
			
				int receiptId = rs.getInt("receipt_id");
				String title = rs.getString("title");
				String pubs = rs.getString("pubs");
				String pubDate = rs.getString("pub_date");
				int authorId = rs.getInt("author_id");
				
				bookVo.setBookId(bookId);
				bookVo.setTitle(title);
				bookVo.setPubs(pubs);
				bookVo.setPub_date(pubs);
				bookVo.setAuthor_id(authorId);
				
			}
			*/
			
			System.out.println( (count+2) + "건 조회 되었습니다.");

			
		}  catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();
		
		
		return receiptId; //orderUser 의 id
	}
   
	
	

	///////////////////////////////////////////////
	////////////////// 조회수정삭제등록//////////////////

	// 삭제
	public int deleteDrink(int drink_id) {

		int count = -1;

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// sql문
			String query = "";

			query += " delete from drink ";
			query += " where drink_id = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, drink_id);

			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리

			System.out.println(count + "건 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return count;
	} // 삭제 메서드 끝

}