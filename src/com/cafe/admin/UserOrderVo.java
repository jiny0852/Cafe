package com.cafe.admin;

public class UserOrderVo {

	private int receiptId;
	private String drinkName;
	private int drinkCnt;

	public UserOrderVo() {

	}

	public UserOrderVo(int receiptId, String drinkName, int drinkCnt) {
		this.receiptId = receiptId;
		this.drinkName = drinkName;
		this.drinkCnt = drinkCnt;
	}

	public int getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(int receiptId) {
		this.receiptId = receiptId;
	}

	public String getDrinkName() {
		return drinkName;
	}

	public void setDrinkName(String drinkName) {
		this.drinkName = drinkName;
	}

	public int getDrinkCnt() {
		return drinkCnt;
	}

	public void setDrinkCnt(int drinkCnt) {
		this.drinkCnt = drinkCnt;
	}

	@Override
	public String toString() {
		return "ReceiptVo [receiptId=" + receiptId + ", drinkName=" + drinkName + ", drinkCnt=" + drinkCnt + "]";
	}

}