package com.cafe.admin;

public class DrinkVo {

	private int drinkId;
	private String drinkName;
	private String drinkContent;
	private int drinkPrice;

	public DrinkVo() {

	}

	public DrinkVo(int drinkId, String drinkName, String drinkContent, int drinkPrice) {
		this.drinkId = drinkId;
		this.drinkName = drinkName;
		this.drinkContent = drinkContent;
		this.drinkPrice = drinkPrice;
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

	@Override
	public String toString() {
		return "DrinkVo [drinkId=" + drinkId + ", drinkName=" + drinkName + ", drinkContent=" + drinkContent
				+ ", drinkPrice=" + drinkPrice + "]";
	}

}
