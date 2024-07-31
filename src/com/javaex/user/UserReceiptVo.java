package com.javaex.user;

public class UserReceiptVo {
	
	//필드
	private int receipt_id;	//영수증번호
	private int user_id;	//회원번호
	private String receipt_date;	//주문시간
	private String receipt_finish;	//제조완료시간
	private String receipt_state;	//주문상태
	
	//생성자
	public UserReceiptVo() {
		super();
	}

	public UserReceiptVo(int receipt_id, int user_id, String receipt_date, String receipt_finish,
			String receipt_state) {
		super();
		this.receipt_id = receipt_id;
		this.user_id = user_id;
		this.receipt_date = receipt_date;
		this.receipt_finish = receipt_finish;
		this.receipt_state = receipt_state;
	}

	
	//메서드 gs
	public int getReceipt_id() {
		return receipt_id;
	}

	public void setReceipt_id(int receipt_id) {
		this.receipt_id = receipt_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getReceipt_date() {
		return receipt_date;
	}

	public void setReceipt_date(String receipt_date) {
		this.receipt_date = receipt_date;
	}

	public String getReceipt_finish() {
		return receipt_finish;
	}

	public void setReceipt_finish(String receipt_finish) {
		this.receipt_finish = receipt_finish;
	}

	public String getReceipt_state() {
		return receipt_state;
	}

	public void setReceipt_state(String receipt_state) {
		this.receipt_state = receipt_state;
	}
	//메서드 일반

	@Override
	public String toString() {
		return "UserReceiptVo [receipt_id=" + receipt_id + ", user_id=" + user_id + ", receipt_date=" + receipt_date
				+ ", receipt_finish=" + receipt_finish + ", receipt_state=" + receipt_state + "]";
	}
	
	
	
}