package com.javaex.admin;

public class AdminVo {
	
	///////  필드
	
	// Users table
	private int userId;
	private String id;
	private int pw;
	private String userName;
	private String userHp;
	
	
	// Drink table
	private int drinkId;
	private String drinkName;
	private String drinkContent;
	private int drinkPrice;
	
	// Receipt table
	private int receiptId;
	private String receiptDate;
	private String receiptFinish;
	private String receiptState;
	//private int userId;
	
	// UserOrder table
	private int userOrderId;
	//private int receiptId;
	//private int drinkId;
	//private int userId;
	private int drinkCnt;
	
	
	private int totalPrice;

	
	//  필드   ///////////////////////////////////////
	
	///////  생성자
	
	public AdminVo() {
		super();
	}
	
	public AdminVo(int userId, String id, int pw, String userName, String userHp, int drinkId, String drinkName,
			String drinkContent, int drinkPrice, int receiptId, String receiptDate, String receiptFinish,
			String receiptState, int userOrderId, int drinkCut) {
		super();
		this.userId = userId;
		this.id = id;
		this.pw = pw;
		this.userName = userName;
		this.userHp = userHp;
		this.drinkId = drinkId;
		this.drinkName = drinkName;
		this.drinkContent = drinkContent;
		this.drinkPrice = drinkPrice;
		this.receiptId = receiptId;
		this.receiptDate = receiptDate;
		this.receiptFinish = receiptFinish;
		this.receiptState = receiptState;
		this.userOrderId = userOrderId;
		this.drinkCnt = drinkCnt;
	}
	
	//  생성자   ///////////////////////////////////////
	
	
	///////  메서드 gs
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getPw() {
		return pw;
	}
	public void setPw(int pw) {
		this.pw = pw;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserHp() {
		return userHp;
	}
	public void setUserHp(String userHp) {
		this.userHp = userHp;
	}
	public int getDrinkId() {
		return drinkId;
	}
	public void setDrinkId(int drinkId) {
		this.drinkId = drinkId;
	}
	public String getDrinkName() {
		return drinkName;
	}
	public void setDrinkName(String drinkName) {
		this.drinkName = drinkName;
	}
	public String getDrinkContent() {
		return drinkContent;
	}
	public void setDrinkContent(String drinkContent) {
		this.drinkContent = drinkContent;
	}
	public int getDrinkPrice() {
		return drinkPrice;
	}
	public void setDrinkPrice(int drinkPrice) {
		this.drinkPrice = drinkPrice;
	}
	public int getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(int receiptId) {
		this.receiptId = receiptId;
	}
	public String getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}
	public String getReceiptFinish() {
		return receiptFinish;
	}
	public void setReceiptFinish(String receiptFinish) {
		this.receiptFinish = receiptFinish;
	}
	public String getReceiptState() {
		return receiptState;
	}
	public void setReceiptState(String receiptState) {
		this.receiptState = receiptState;
	}
	public int getUserOrderId() {
		return userOrderId;
	}
	public void setUserOrderId(int userOrderId) {
		this.userOrderId = userOrderId;
	}
	public int getDrinkCnt() {
		return drinkCnt;
	}
	public void setDrinkCnt(int drinkCnt) {
		this.drinkCnt = drinkCnt;
	}

	

	//  메서드 gs   ///////////////////////////////////////
	
	
	///////  메서드 일반
	
	@Override
	public String toString() {
		return "cafeVo [userId=" + userId + ", id=" + id + ", pw=" + pw + ", userName=" + userName + ", userHp="
				+ userHp + ", drinkId=" + drinkId + ", drinkName=" + drinkName + ", drinkContent=" + drinkContent
				+ ", drinkPrice=" + drinkPrice + ", receiptId=" + receiptId + ", receiptDate=" + receiptDate
				+ ", receiptFinish=" + receiptFinish + ", receiptState=" + receiptState + ", userOrderId=" + userOrderId
				+ ", drink_cut=" + drinkCnt + "]";
	}
	
	
	//  메서드 일반   ///////////////////////////////////////

}
