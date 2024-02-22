package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

public class ResponseSendOTP {

	@SerializedName("Status")
	private String status;

	@SerializedName("OTP")
	private String oTP;

	public String getStatus(){
		return status;
	}

	public String getOTP(){
		return oTP;
	}
}