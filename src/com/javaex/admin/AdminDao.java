package com.javaex.admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao {

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
	
	
	//주문 리스트 selectall by미완 userOrder 새로고침도 같은 매서드임
	
	//판매완료 scan 영수증 번호 selectOne userOrder  update 상태
	
	//판매수량 scan 날짜 select 날짜  해서 판매된 수량 리스트 전체 판매량 리스트 출력
	
	//상품 관리 - insert
	
	//상품 관리 - update
	
	//상품 관리 - delete
	
	//상품 관리 - selectAll
 	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

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

	// 회원정보 수정
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

	////////////////// 조회수정삭제등록//////////////////
	///////////////////////////////////////////////

}
