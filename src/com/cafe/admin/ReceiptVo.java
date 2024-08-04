package com.cafe.admin;

public class ReceiptVo {

	private int receiptId;
	private int userId;
	private String receiptDate;
	private String receiptFinish;
	private String receiptState;

	public ReceiptVo() {

	}

	public ReceiptVo(int receiptId, int userId, String receiptDate, String receiptFinish, String receiptState) {
		this.receiptId = receiptId;
		this.userId = userId;
		this.receiptDate = receiptDate;
		this.receiptFinish = receiptFinish;
		this.receiptState = receiptState;
	}

	public int getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(int receiptId) {
		this.receiptId = receiptId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	@Override
	public String toString() {
		return "ReceiptVo [receiptId=" + receiptId
				+ ", userId=" + userId + ", receiptDate=" + receiptDate
				+ ", receiptFinish=" + receiptFinish + ", receiptState=" + receiptState + "]";
	}

	
	
}