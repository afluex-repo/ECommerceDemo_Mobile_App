package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

public class ResponseValidMobile {

	@SerializedName("MobileNo")
	private String mobileNo;

	@SerializedName("Status")
	private String status;

	@SerializedName("ErrorMessage")
	private Object errorMessage;

	@SerializedName("Name")
	private String name;

	@SerializedName("Pk_UserId")
	private String Pk_UserId;

	public String getMobileNo(){
		return mobileNo;
	}

	public String getStatus(){
		return status;
	}

	public Object getErrorMessage(){
		return errorMessage;
	}

	public String getName(){
		return name;
	}

	public String getPk_UserId() {
		return Pk_UserId;
	}

	public void setPk_UserId(String pk_UserId) {
		Pk_UserId = pk_UserId;
	}
}