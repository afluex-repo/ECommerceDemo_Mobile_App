package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

public class ResponseLogout {

	@SerializedName("Status")
	private String status;

	@SerializedName("DeviceId")
	private String deviceId;

	@SerializedName("ErrorMessage")
	private String errorMessage;

	public String getStatus(){
		return status;
	}

	public String getDeviceId(){
		return deviceId;
	}

	public String getErrorMessage(){
		return errorMessage;
	}
}