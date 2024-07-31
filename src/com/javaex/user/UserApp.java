package com.javaex.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class UserApp {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		List<UserVo> foodList = new ArrayList<UserVo>();
		UserDao userDao = new UserDao();

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

				// 로그인 된 회원 객체
				UserVo userVo;
				
				while (on2) {
					System.out.println("============= 로그인 ===============");
					System.out.print("아이디를 입력해주세요:");
					String id = sc.nextLine();
					System.out.print("비밀번호를 입력해주세요:");
					String pw = sc.nextLine();
					
					userVo = userDao.userLogin(id, pw);
					if ( userVo == null ) {
						System.out.println("아이디와 비번을 확인해주세요");
						break;
					} else {
						loginNo = userVo.getUserId();
						
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
							String id = sc.nextLine();
							System.out.print("비밀번호를 입력해주세요:");
							String pw = sc.nextLine();
							System.out.print("이름을 입력해주세요:");
							String name = sc.nextLine();
							System.out.print("번호를 입력해주세요:");
							String hp = sc.nextLine();
							
							userDao.updateUser(id, pw, name, hp, loginId);
							
							System.out.println("회원정보가 수정되었습니다.");

						} else if (choice.equals("2")) {
							// 지금까지의 주문내역 로직짜기
							userDao.selectAllOrderReceipt(3);
							
							System.out.println("지금까지의 주문내역입니다.");
							
							//세부영수증 확인
							userDao.selectOneReceipt(3);
							

						} else if (choice.equals("3")) {
							// 주문하기 로직
							System.out.println("주문을 해주세용");
						} else if (choice.equals("0")) {
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
					System.out.print("이름을 입력해주세요:");
					String name = sc.nextLine();
					System.out.print("전화번호를 입력해주세요:");
					String hp = sc.nextLine();
					System.out.print("아이디를 입력해주세요:");
					String id = sc.nextLine();
					System.out.print("비밀번호를 입력해주세요:");
					String pw = sc.nextLine();

					System.out.println("회원가입이 완료되었습니다");

					break;

				}

				// 프로그램 종료
			} else if (num.equals("3")) {
				System.out.println("프로그램이 종료되었습니다.");
				on = false;
			}

		} // 목록 while 끝

	}
}