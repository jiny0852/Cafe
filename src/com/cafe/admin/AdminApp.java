package com.cafe.admin;

import java.util.List;
import java.util.Scanner;

public class AdminApp {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		AdminDao adminDao = new AdminDao();
		System.out.println("* 관리자 프로그램 *");

		while (true) {
			System.out.println("======================= 목록 ==========================================");
			System.out.println("1.주문내역 확인     2.판매수량     3.상품관리     4.종료");
			System.out.println("======================================================================");
			System.out.print("번호를 입력해주세요 >> ");
			int menu = sc.nextInt();

			if (menu == 1) {

				while (true) {
					System.out.println("");
					System.out.println("================== 주문내역 확인 ===============================================");
					System.out.println("1.새로고침     2.영수증 번호 입력     3.뒤로가기(0)");
					System.out
							.println("===============================================================================");

					System.out
							.println("------------------------------------------------------------------------------");
					System.out.println("영수증번호  |  회원번호  |        주문시간       |        완료시간        |  주문상태");
					System.out
							.println("------------------------------------------------------------------------------");

					// Receipt 테이블의 리스트 출력(상태가 미완인것)
					List<ReceiptVo> receiptList1 = adminDao.selectReceiptAll("준비");
					for (int i = 0; i < receiptList1.size(); i++) {
						System.out.print("   " + receiptList1.get(i).getReceiptId());
						System.out.print("          " + receiptList1.get(i).getUserId());
						System.out.print("       " + receiptList1.get(i).getReceiptDate());
						System.out.print("    " + receiptList1.get(i).getReceiptFinish());
						System.out.println("      " + receiptList1.get(i).getReceiptState());
					}
					System.out.println("");

					System.out.print("번호를 입력해주세요 >> ");
					int menu01 = sc.nextInt();
					System.out.println("");

					if (menu01 == 1) {

						// 새로고침

					} else if (menu01 == 2) {
						boolean one = true;
						while (one) {
							System.out.println("");
							System.out.println("================= 영수증 번호 입력 ===============");
							System.out.print("영수증 번호를 입력해주세요 >> ");
							int menu02 = sc.nextInt();

							System.out.println("");
							System.out.println("********** 주문 상세 출력 **********");

							List<UserOrderVo> userOrderList = adminDao.selectReceiptOne(menu02);
							List<ReceiptVo> receiptListReady = adminDao.selectReceiptAll("준비");
							List<ReceiptVo> receiptListFinish = adminDao.selectReceiptAll("완료");

							boolean targetFinish = false;
							boolean targetReady = false;

							/* 영수증리스트에는 menu02 가 무조건 있어야 해 */
							/* 영수증리스트에 menu02가 없으면 틀렸으니까 다시 입력하라고 해야해 */

							/* 영수증리스트에 영수증id = menu02 이고, 상태가 완료면 -> 이미 완료된 상품입니다 메시지 출력 */
							if (receiptListFinish.size() > 0) {
								for (int i = 0; i < receiptListFinish.size(); i++) {
									if (receiptListFinish.get(i).getReceiptId() == menu02) {
										System.out.println("");
										System.out.println("이미 완료된 상품입니다.");
										System.out.println("");
										one = false;
										targetFinish = true;
									}
								}
							}

							if (receiptListReady.size() > 0) {
								for (int i = 0; i < receiptListReady.size(); i++) {
									if (receiptListReady.get(i).getReceiptId() == menu02) {
										System.out.print("영수증 번호: " + userOrderList.get(i).getReceiptId() + "\t");
										System.out.print("상품명: " + userOrderList.get(i).getDrinkName() + "\t");
										System.out.println("수량: " + userOrderList.get(i).getDrinkCnt());
										System.out.println("");
										targetReady = true;
									}
								}
							}

							if (!targetFinish && !targetReady) {
								System.out.println("");
								System.out.println("일치하는 영수증 번호가 없습니다. 다시 입력해주세요.");
								System.out.println("");
								break;
							}
							
							/* 주문준비상태일때는 1번을 누르면 완료처리를 할 수 있고 0번은 뒤로가기야 */
							/* 주문완료상태일때는 1번 옵션이 없고 0번은 뒤로가기야 */
							while (true) {
								if (targetFinish) {
									System.out.print("1.뒤로가기(0) >> ");
								} else if (targetReady) {
									System.out.print("1.완료 2.뒤로가기(0) >> ");
								}

								int menu03 = sc.nextInt();

								if (menu03 == 1 && targetReady) {
									// 주문 상태를 완료로 변경
									adminDao.updateState("완료", menu02);
									one = false;
									break;
								} else if (menu03 == 0) {
									// 뒤로가기
									one = false;
									break;
								} else {
									System.out.println("잘못된 번호입니다. 다시 입력해주세요.");
								}
							}

						}

					} else if (menu01 == 0) {

						// 뒤로가기
						break;

					} else {
						System.out.println("잘못된 번호입니다. 다시 입력해주세요.");
					}

				}

			} else if (menu == 2) {
				sc.nextLine(); // 개행 오류방지
				while (true) {
					System.out.println("");
					System.out.println("=============================== 판매수량 ================================");
					System.out.print("날짜를 입력해주세요(뒤로가기는 0입력) >> ");
					String date = sc.nextLine(); // 2024-07-31
					System.out.println("");

					if (date.equals("0")) {
						break;
					}

					List<SaleAmountVo> saleAmountList = adminDao.selectSaleAmountAll(date);
					int sum = 0;

					if (saleAmountList.size() == 0) {
						System.out.println("해당 날짜에 판매된 상품이 없습니다.");
					} else {
						for (int i = 0; i < saleAmountList.size(); i++) { // 음료 판매 총합
							System.out.print("회원번호: " + saleAmountList.get(i).getUserId() + "\t");
							System.out.print("상품명: " + saleAmountList.get(i).getDrinkName() + "\t");
							System.out.print("수량: " + saleAmountList.get(i).getDrinkCnt() + "\t");
							System.out.print("음료 총합: " + saleAmountList.get(i).getSaleAmountSum() + "\t");
							System.out.println("판매시간: " + saleAmountList.get(i).getRtime());
							sum += saleAmountList.get(i).getSaleAmountSum();
						}
						System.out.println("판매 총합: " + sum);
					}

				}

			} else if (menu == 3) {
				while (true) {

					System.out.println("");
					System.out.println("============================ 상품관리 ===============================");
					System.out.println("1.상품 리스트    2.추가     3.수정     4.삭제     5.뒤로가기(0)");
					System.out.println("===================================================================");
					System.out.print("번호를 입력해주세요 >> ");
					int menu01 = sc.nextInt();

					if (menu01 == 1) {

						// 상품리스트 출력
						System.out.println("");
						System.out.println("-------------------------------------------------------------");
						System.out.println(" 상품번호  |       상품명      |        상품설명       |    가격   ");
						System.out.println("--------------------------------------------------------------");
						List<DrinkVo> drinkList = adminDao.selectDrinkAll();
						for (int i = 0; i < drinkList.size(); i++) {
							System.out.print("   " + drinkList.get(i).getDrinkId() + "\t   ");
							System.out.print(drinkList.get(i).getDrinkName() + "\t\t");
							System.out.print(drinkList.get(i).getDrinkContent() + "\t\t");
							System.out.println(+drinkList.get(i).getDrinkPrice());
						}

					} else if (menu01 == 2) {

						sc.nextLine();
						System.out.println("");
						System.out.print("추가할 상품의 이름을 입력해주세요 >> ");
						String name = sc.nextLine();

						System.out.println("");
						System.out.print("추가할 상품의 설명을 입력해주세요 >> ");
						String content = sc.nextLine();

						System.out.println("");
						System.out.print("추가할 상품의 가격을 입력해주세요 >> ");
						int price = sc.nextInt();

						adminDao.insertDrink(name, content, price);

					} else if (menu01 == 3) {

						System.out.println("");
						System.out.print("수정할 상품의 번호를 입력해주세요 >> ");
						int id = sc.nextInt();
						sc.nextLine();

						System.out.println("");
						System.out.print("상품의 이름을 입력해주세요 >> ");
						String name = sc.nextLine();

						System.out.println("");
						System.out.print("상품의 설명을 입력해주세요 >> ");
						String content = sc.nextLine();

						System.out.println("");
						System.out.print("상품의 가격을 입력해주세요 >> ");
						int price = sc.nextInt();

						adminDao.updateDrink(name, content, price, id);

					} else if (menu01 == 4) {

						System.out.println("");
						System.out.print("삭제할 상품의 번호를 입력해주세요 >>");
						int drinkId = sc.nextInt();

						adminDao.deleteDrink(drinkId);

					} else if (menu01 == 0) {

						System.out.println("");
						// 뒤로가기
						break;

					} else {

						System.out.println("");
						System.out.println("잘못된 번호입니다. 다시 입력해주세요.");
					}

				}

			} else if (menu == 4) {
				System.out.println("");
				System.out.println("======================== 프로그램이 종료되었습니다. =========================");
				break;
			} else {
				System.out.println("");
				System.out.println("잘못된 번호입니다. 다시 입력해주세요.");
				System.out.println("");
			}

		}

		sc.close();
	}

}