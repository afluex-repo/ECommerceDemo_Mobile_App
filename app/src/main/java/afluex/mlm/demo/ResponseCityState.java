package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

public class ResponseCityState {

	@SerializedName("Status")
	private String status;

	@SerializedName("State")
	private String state;

	@SerializedName("City")
	private String city;

	@SerializedName("ErrorMessage")
	private String errorMessage;

	@SerializedName("PinCode")
	private String pinCode;

	public String getStatus(){
		return status;
	}

	public String getState(){
		return state;
	}

	public String getCity(){
		return city;
	}

	public String getErrorMessage(){
		return errorMessage;
	}

	public String getPinCode(){
		return pinCode;
	}
}