package com.cafe.user;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App03 {

   public static void main(String[] args) {

      UserDao userDao = new UserDao(); // UserDao 주소값 가져오기
      Scanner sc = new Scanner(System.in);
      // List foodList = new ArrayList();
      UserVo userVo = new UserVo();   //로그인시 아이디확인용
      List<AllVo> userOrderList = new ArrayList<AllVo>();
      

      boolean on = true; // 목록 while문
      boolean on2 = true; // 로그인 while문
      boolean on3 = true; // 로그인성공시 while문
      boolean on4 = true; // 회원가입 while문
      boolean stop = true; // 임시 while문(주문받기)

      int loginNo = 0; // 로그인된 번호(입력받을번호)

      while (on) {
         // 디폴트 화면 (목록)
         System.out.println("============= 목록 ===============");
         System.out.println("1.로그인");
         System.out.println("2.회원가입");
         System.out.println("3.종료");
         System.out.print("번호를 입력해주세요:");
         String num = sc.nextLine();

         // (로그인)
         if (num.equals("1")) {

            while (on2) {
               System.out.println("============= 로그인 ===============");
               System.out.print("아이디를 입력해주세요:");
               String id = sc.nextLine();
               System.out.print("비밀번호를 입력해주세요:");
               String pw = sc.nextLine();

               // 로그인
               userVo = userDao.userLogin(id, pw);
               if ( userVo == null ) {
                  System.out.println("아이디와 비번을 확인해주세요");
                  break;
               } else {
                  loginNo = userVo.getUserId();
                  //회원번호
               }
               
               
               System.out.println("============= 로그인성공 ===============");

               while (on3) {
                  System.out.println("=====================================");
                  System.out.println("1.회원정보수정");
                  System.out.println("2.지금까지의 주문내역");
                  System.out.println("3.주문하기");
                  System.out.println("4.뒤로가기: 0번");
                  System.out.println("=====================================");
                  System.out.print("번호를 입력해주세요:");
                  String choice = sc.nextLine();

                  if (choice.equals("1")) {
                     // 정보수정 로직
                     System.out.print("아이디를 입력해주세요:");
                     String rid = sc.nextLine();
                     System.out.print("비밀번호를 입력해주세요:");
                     String rpw = sc.nextLine();
                     System.out.print("이름을 입력해주세요:");
                     String rname = sc.nextLine();
                     System.out.print("번호를 입력해주세요:");
                     String rhp = sc.nextLine();

                     userDao.updateUser(rid, rpw, rname, rhp, loginNo);

                     System.out.println("회원정보가 수정되었습니다.");

                  } else if (choice.equals("2")) {
                     // 지금까지의 주문내역 로직짜기
                     System.out.println("지금까지의 주문내역입니다.");

                     
                     List<AllVo> receList = userDao.selectAllOrderReceipt(loginNo);
                      System.out.println("손님별 전체 주문내역이 출력 되어야했음.........");
                      System.out.println("다음 기회에 ^^^^^^^^ ");
                     
/*
                     for (int i = 0; i < receList.size(); i++) {
                        System.out.println(receList.get(i).getReceiptId());
                        System.out.println(receList.get(i).getReceiptFinish());
                     }
*/
                     System.out.print("1. 상세 조회  2. 뒤로가기");
                     int n = sc.nextInt();

                     if (n == 1) {
                        System.out.println(userDao.selectOneReceipt(n));
                     } else if (n == 2) {
                        System.out.println("회원 선택창으로 돌아갑니다.");
                        break;
                     } else {
                        System.out.println("잘못된 입력입니다");
                        break;
                     }

                  } else if (choice.equals("3")) {
                     // 상품 리스트 출력로직

                     // 리스트로 관리
                     List<UserDrinkVo> drink = userDao.selectDrinkAll();

                     userDao.insertReceipt(loginNo); // 영수증 생성
                     // 마지막으로 생성된 receipt_id 와 rid연결해주는곳

                     // int rid = userDao.insertReceipt(loginNo); // 영수증 등록
                     int rid = userDao.sentReceiptId(loginNo);
                     int food = 0;
                     int amount = 0;
                     int count = 0;

                     // 주문 출력
                     int drinkCnt = 0; // 음료리스트
                     for (int i = 0; i < drink.size(); i++) {
                        System.out.println(drink.get(i));
                        drinkCnt++;
                     }

                     while (그만) {

                        System.out.print("원하시는 상품을 입력해주세요(-1이면 종료):");
                        food = sc.nextInt();

                        if (food == -1) {
                           stop = false;

                        } else if (food > drinkCnt) {  //내가선택한 음료수번호 > 음료수종류총갯수
                           // drink_id가 기존에 없는값이 들어왔을때
                           System.out.println("잘못된 입력입니다");
                           stop = false;

                         } else {
                                    System.out.print("수량을 입력해주세요:");
                                    amount = sc.nextInt();
                                    count++;
                                    userDao.insertUserOrder(rid, loginNo, food, amount);
                                    
                                 }

                                 // UserVo result = new UserVo(food, amount);
                                 // foodList.add(result);

                              }

                              System.out.println("<주문내역>");
                              System.out.println("=====================");
                             
                              
                              
                              userOrderList = userDao.selectUserOrderAll(rid);
                              for (int i = 0; i < userOrderList.size(); i++) {
                                 System.out.println(userOrderList.get(i).getDrinkName());
                                 System.out.println(userOrderList.get(i).getDrinkCnt());
                              }
                               
                              System.out.println("=====================");


                           }

                           else if (choice.equals("0")) {
                              System.out.println("뒤로가기");

                     on2 = false;
                     on3 = false;

                  } else {
                     System.out.println("잘못입력하셨습니다.");
                  }
               }
            }

            // 회원가입

         } else if (num.equals("2")) {

            while (on4) {
               System.out.print("아이디를 입력해주세요:");
               String id = sc.nextLine();
               System.out.print("비밀번호를 입력해주세요:");
               String pw = sc.nextLine();
               System.out.print("이름을 입력해주세요:");
               String name = sc.nextLine();
               System.out.print("전화번호 입력해주세요:");
               String hp = sc.nextLine();

               int cnt = userDao.insertUser(id, pw, name, hp);

               if (cnt == 1) {
                  System.out.println("회원가입이 완료되었습니다");
                  on4 = false;
               } else {
                  System.out.println("회원가입 실패하였습니다");
                  break;
               }

            }

            // 프로그램 종료
         } else if (num.equals("3")) {
            System.out.println("프로그램이 종료되었습니다.");
            on = false;
         }

      } // 목록 while 끝

   }
}
