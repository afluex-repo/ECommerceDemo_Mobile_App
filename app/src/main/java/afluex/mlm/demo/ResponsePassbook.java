package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

public class ResponsePassbook {

	@SerializedName("Status")
	private String status;

	@SerializedName("ShoppingPoint")
	private String shoppingPoint;

	@SerializedName("Commission")
	private String commission;

	@SerializedName("Unclear")
	private String unclear;

	@SerializedName("Pk_UserId")
	private String pkUserId;

	@SerializedName("DWPeBalance")
	private String dWPeBalance;

	@SerializedName("ErrorMessage")
	private String errorMessage;

	@SerializedName("Hold")
	private String hold;

	@SerializedName("PIN")
	private String pin;

	public String getStatus(){
		return status;
	}

	public String getShoppingPoint(){
		return shoppingPoint;
	}

	public String getCommission(){
		return commission;
	}

	public String getUnclear(){
		return unclear;
	}

	public String getPkUserId(){
		return pkUserId;
	}

	public String getDWPeBalance(){
		return dWPeBalance;
	}

	public String getErrorMessage(){
		return errorMessage;
	}

	public String getHold(){
		return hold;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}
}