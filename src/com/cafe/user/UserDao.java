package com.cafe.user;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

   // 필드
   // 1번
   private Connection conn = null;
   private PreparedStatement pstmt = null;
   private ResultSet rs = null;

   // 2번
   private String driver = "com.mysql.cj.jdbc.Driver";
   private String url = "jdbc:mysql://localhost:3306/cafe_db";
   private String id = "cafe";
   private String pw = "cafe";

   // db연결 일반메소드
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
   } // db연결 메소드 끝

   // 자원정리 메소드
   private void close() {
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

   // << 삭제 메서드 >>
   public int deleteDrink(int drinkId) {

      int count = -1;

      try {

         // 3. SQL문 준비 / 바인딩 / 실행
         // sql문
         String query = "";

         query += " delete from drink ";
         query += " where drink_id = ? ";

         // 바인딩
         pstmt = conn.prepareStatement(query);
         pstmt.setInt(1, drinkId);

         // 실행
         count = pstmt.executeUpdate();

         // 4.결과처리

         System.out.println(count + "건 삭제되었습니다.");

      } catch (SQLException e) {
         System.out.println("error:" + e);
      }

      return count;
   } // 삭제 메서드 끝

   // << 회원정보 추가 (유저번호, 아이디, 비번, 이름, 폰번호) >>
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
   // 회원 로그인 조회 메서드

   public UserVo userLogin(String uid, String upw) {

      int count = -1;

      this.getConnection();

      UserVo userVo = null;

      try {

         // 3. SQL문 준비 / 바인딩 / 실행
         // *sql문 준비
         String query = "";
         query += " select     user_id, ";
         query += "          id, ";
         query += "           pw, ";
         query += "           user_name, ";
         query += "           user_hp ";
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
            String hp = rs.getString("user_hp");

            userVo = new UserVo(userId, id, pw, name, hp);

            count++;

         }
         // System.out.println("로그인 되었습니다");

      } catch (SQLException e) {
         System.out.println("error:" + e);
      }

      this.close();

      return userVo;

   }

   // 회원정보 수정메서드
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

   // 상품출력 메서드
   public List<UserDrinkVo> selectDrinkAll() {

      List<UserDrinkVo> drinkList = new ArrayList<UserDrinkVo>();

      this.getConnection();

      try {
         // 3. SQL문 준비 / 바인딩 / 실행
         // sql문
         String query = "";
         query += " select drink_id ";
         query += "        ,drink_name ";
         query += "         ,drink_content ";
         query += "        ,drink_price ";
         query += " from Drink ";

         // 바인딩
         pstmt = conn.prepareStatement(query);

         // 실행
         rs = pstmt.executeQuery();

         // 반복
         while (rs.next()) {
            int id = rs.getInt("drink_id");
            String drink = rs.getString("drink_name");
            String content = rs.getString("drink_content");
            String price = rs.getString("drink_price");

            UserDrinkVo DrinkVo = new UserDrinkVo(id, drink, content, price);
            drinkList.add(DrinkVo);

         }
      } catch (SQLException e) {
         System.out.println("error:" + e);
      }
      this.close();
      return drinkList;

   }

   

   // receipt insert (입력받은 상품 영수증)
   // 영수증번호 인설트메서드
   public int insertReceipt(int user_id) {
      int count = -1;

      // DB연결 메소드 호출
      this.getConnection();

      try {
         // 3. SQL문 준비 / 바인딩 / 실행

         // - sql문 준비
         String query = "";
         query += " insert into receipt (receipt_id, user_id,receipt_state) ";
         query += " values (null,?,?) ";

         /*
          * INSERT INTO 테이블명 (COLUMN_LIST) VALUES (COLUMN_LIST에 넣을 VALUE_LIST);
          * 
          */

         // - 바인딩
         pstmt = conn.prepareStatement(query);
         pstmt.setInt(1, user_id); // 로그인시 자동생성된 user_id
         pstmt.setString(2, "준비");

         // - 실행
         count = pstmt.executeUpdate();

         // 4.결과처리
         // System.out.println((count/2) + "건 접수 되었습니다.");

      } catch (SQLException e) {
         System.out.println("error:" + e);
      }

      this.close();

      return count;
   }

    public int insertUserOrder(int receipt_id, int userId, int drinkId, int drinkCnt) {
         
         System.out.println("receipt_id" + receipt_id);
         System.out.println("userId" + userId);
         System.out.println("drinkId" + drinkId);
         System.out.println("drinkCnt" + drinkCnt);
         // DB연결 메소드 호출
               this.getConnection(); 
               int count = -1;
               try {
                  // 3. SQL문 준비 / 바인딩 / 실행

                  // - sql문 준비
                  String query = "";
                  query += " insert into userOrder ";
                  query += " values  ( null,?,?,?,?) ";

      
                  // - 바인딩
                  pstmt = conn.prepareStatement(query);
                  pstmt.setInt(1,receipt_id);  
                  pstmt.setInt(2, drinkId);
                  pstmt.setInt(3, userId);
                  pstmt.setInt(4, drinkCnt);
                  

                  // - 실행
                  count = pstmt.executeUpdate();
                  
                  
                  // 4.결과처리
                  //System.out.println((count) + "건 접수 되었습니다.");

               } catch (SQLException e) {
                  System.out.println("error:" + e);
               }

               this.close();
               return count;
         
      }

   // selectReceiptId

   public int selectReceiptId(int loginNo) {

      UserOrderVo usVo = new UserOrderVo();
      int receiptId = 0;

      int count = -1; // 최소값을 일부러 -1로 넣는다

      this.getConnection();

      try {
         // 3. SQL문 준비 / 바인딩 / 실행

         // - sql문 준비
         String query = "";
         query += " select receipt_id  ";
         query += "   from userOrder ";
         query += "    where user_id = ? ";

         // - 바인딩
         pstmt = conn.prepareStatement(query);
         pstmt.setInt(1, loginNo);

         // - 실행
         rs = pstmt.executeQuery();

         // 4.결과처리
         rs.next();
         receiptId = rs.getInt("receipt_id");

         /*
          * 
          * while (rs.next()) {
          * 
          * int receiptId = rs.getInt("receipt_id"); String title =
          * rs.getString("title"); String pubs = rs.getString("pubs"); String pubDate =
          * rs.getString("pub_date"); int authorId = rs.getInt("author_id");
          * 
          * bookVo.setBookId(bookId); bookVo.setTitle(title); bookVo.setPubs(pubs);
          * bookVo.setPub_date(pubs); bookVo.setAuthor_id(authorId);
          * 
          * }
          */

         System.out.println((count + 2) + "건 조회 되었습니다.");

      } catch (SQLException e) {
         System.out.println("error:" + e);
      }

      this.close();

      return receiptId; // orderUser 의 id
   }

   public int InsertUserOrder(int receiptId, int userId, int drinkId, int drinkCnt) {

      int count = 0;
      // DB연결 메소드 호출
      this.getConnection();

      try {
         // 3. SQL문 준비 / 바인딩 / 실행

         // - sql문 준비
         String query = "";
         query += " insert into userOrder ";
         query += " values (null , ?, ?, ?, ?) ";

         // - 바인딩
         pstmt = conn.prepareStatement(query);
         pstmt.setInt(1, receiptId);
         pstmt.setInt(2, userId);
         pstmt.setInt(3, drinkId);
         pstmt.setInt(4, drinkCnt);

         // - 실행
         count = pstmt.executeUpdate();

         // 4.결과처리
         // System.out.println(count + "건 접수 되었습니다.");

      } catch (SQLException e) {
         System.out.println("error:" + e);
      }

      this.close();

      return count;

   }

   // selectAll
   // 회원 번호를 입력해주세요
   // 회원 주문 정보 조회 -- selectall by userid

   public List<AllVo> selectAllOrderReceipt(int userId) {

      System.out.println("전체 조회 로직");

      // 리스트 만들기
      List<AllVo> orderReceiptList = new ArrayList<AllVo>();

      int count = -1; // 최소값을 일부러 -1로 넣는다

      this.getConnection();

      try {
         // 3. SQL문 준비 / 바인딩 / 실행

         // - sql문 준비
         String query = "";
         query += " select   receipt_id, ";
         query += "          receipt_finish ";
         query += " from receipt ";
         query += " where user_id = ? ";

         // - 바인딩
         pstmt = conn.prepareStatement(query);

         // - 실행
         rs = pstmt.executeQuery();
         pstmt.setInt(1, userId);

         // 4.결과처리

         while (rs.next()) {

            int receiptId = rs.getInt("receipt_id");
            String receiptFinish = rs.getString("receipt_finish");

            AllVo userVo = new AllVo(receiptId, receiptFinish);
            orderReceiptList.add(userVo);

            count++;

         }

         System.out.println(count + "건 조회 되었습니다.");

      } catch (SQLException e) {
         System.out.println("error:" + e);
      }

      this.close();

      return orderReceiptList;
   }

   // 추가
    public List<AllVo> selectUserOrderAll(int receiptId) {

         List<AllVo> userOrderList = new ArrayList<AllVo>();

         this.getConnection();

         try {
            // 3. SQL문 준비 / 바인딩 / 실행
            // sql문
            String query = "";
            query += " select d.drink_id, ";
           query   += "    d.drink_name, ";
            query += "          uo.drink_cnt ";
            query += " from userorder uo, drink d ";
            query += " where uo.drink_id = d.drink_id ";
            query += " and uo.receipt_id = ?";
            query += " order by uo.drink_id asc ";

            // 바인딩
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, receiptId);

            // 실행
            rs = pstmt.executeQuery();

            // 반복
            while (rs.next()) {
                int did = rs.getInt("drink_id");
                 String id = rs.getString("drink_name");
                 int cnt = rs.getInt("drink_cnt");

                 AllVo userOrderVo = new AllVo(did, id, cnt);
                 userOrderList.add(userOrderVo);
            }
         } catch (SQLException e) {
            System.out.println("error:" + e);
         }
         this.close();
         return userOrderList;

      }

   // 상세조회
   public List<UserOrderVo> selectOneReceipt(int receiptId) {

         List<UserOrderVo> uoList = new ArrayList<UserOrderVo>();
         
         int count = -1;

         this.getConnection();

         try {
            // 3. SQL문 준비 / 바인딩 / 실행

            // - sql문 준비
            String query = ""; 
            query += " select   drink_id, ";
            query += "         drink_cnt ";
            query += " from Userorder ";
            query += " where receipt_id = ? ";
            query += " order by drink_id asc ";

            // - 바인딩
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, receiptId);
            

            // - 실행
            rs = pstmt.executeQuery();
            

            // 4.결과처리
                     
            while (rs.next()) {
            
               int drinkId = rs.getInt("drink_id");
               int drinkCnt = rs.getInt("drink_cnt");
               
               
               UserOrderVo uoVo = new UserOrderVo(drinkId, drinkCnt);
               uoList.add(uoVo);
               
            }
            
            
            System.out.println( (count+2) + "건 조회 되었습니다.");

            
         }  catch (SQLException e) {
            System.out.println("error:" + e);
         }

         this.close();

         return uoList;
      }

   // 보내기 (판매내역 연결 rid)
   public int sentReceiptId(int userId) {

      int rid = 0;

      this.getConnection();

      try {
         // 3. SQL문 준비 / 바인딩 / 실행
         // sql문
         String query = "";
         query += " select  LAST_INSERT_ID(receipt_id) rid";
         query += " from receipt ";
         query += " where user_id = ?  ";

         // 바인딩
         pstmt = conn.prepareStatement(query);
         pstmt.setInt(1, userId);

         // 실행
         rs = pstmt.executeQuery();

         // 반복
         while (rs.next()) {
            rid = rs.getInt("rid");

         }
      } catch (SQLException e) {
         System.out.println("error:" + e);
      }
      this.close();

      return rid;

   }

}
