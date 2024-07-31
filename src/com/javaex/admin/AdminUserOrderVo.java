package com.javaex.admin;

public class AdminUserOrderVo {
	
	//필드
	private int userOrderId;
	private int receiptId;
	private int drinkId;
	private int userId;
	private int drink_cut;
	
	
	
	//생성자
	public AdminUserOrderVo() {
		
	}
	public AdminUserOrderVo(int userOrderId, int receiptId, int drinkId, int userId, int drink_cut) {
		super();
		this.userOrderId = userOrderId;
		this.receiptId = receiptId;
		this.drinkId = drinkId;
		this.userId = userId;
		this.drink_cut = drink_cut;
	}
	

	//메서드 gs
	public int getUserOrderId() {
		return userOrderId;
	}
	public void setUserOrderId(int userOrderId) {
		this.userOrderId = userOrderId;
	}
	public int getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(int receiptId) {
		this.receiptId = receiptId;
	}
	public int getDrinkId() {
		return drinkId;
	}
	public void setDrinkId(int drinkId) {
		this.drinkId = drinkId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getDrink_cut() {
		return drink_cut;
	}
	public void setDrink_cut(int drink_cut) {
		this.drink_cut = drink_cut;
	}

	
	
	//메서드 일반
	
	@Override
	public String toString() {
		return "userOrderVo [userOrderId=" + userOrderId + ", receiptId=" + receiptId + ", drinkId=" + drinkId
				+ ", userId=" + userId + ", drink_cut=" + drink_cut + "]";
	}
	
	
	

}
