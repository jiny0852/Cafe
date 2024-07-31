package com.javaex.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.admin.AdminUserVo;



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
	
	//회원 로그인 조회 -- selectone scan id pw  
	public AdminUserVo userLogin(String uid, String upw) {
		
		int count = -1;
		
		this.getConnection();
		
		AdminUserVo userVo = null;
		
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
				
				userVo = new AdminUserVo(userId, id, pw, name, hp);
				
				
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
	

	
	
	//회원 정보 수정 -- update scan name ph id pw
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
	
	
	//회원 주문 정보 조회 -- selectall by userid 
	
	
	
	
	//회원가입 -- user insert
	
	
	//상품 출력 -- drink list selectall
	
	
	//상품 선택 -- receipt insert scan cnt
	
	
	
	
	
	
	
	
	
	
	
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