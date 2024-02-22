package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

public class ResponseFrgtPassword {

	@SerializedName("MobileNo")
	private String mobileNo;

	@SerializedName("Status")
	private String status;

	@SerializedName("OTP")
	private String oTP;

	@SerializedName("ErrorMessage")
	private String errorMessage;

	@SerializedName("Password")
	private String password;

	public String getMobileNo(){
		return mobileNo;
	}

	public String getStatus(){
		return status;
	}

	public String getOTP(){
		return oTP;
	}

	public String getErrorMessage(){
		return errorMessage;
	}

	public String getPassword(){
		return password;
	}
}