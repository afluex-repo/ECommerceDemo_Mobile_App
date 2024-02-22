package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin {

	@SerializedName("MobileNo")
	private String mobileNo;

	@SerializedName("Status")
	private String status;

	@SerializedName("LoginId")
	private String loginId;

	@SerializedName("DeviceId")
	private String deviceId;

	@SerializedName("FirstName")
	private String firstName;

	@SerializedName("ProfilePic")
	private String profilePic;

	@SerializedName("Pk_UserId")
	private String pkUserId;

	@SerializedName("LastName")
	private String lastName;

	@SerializedName("ErrorMessage")
	private String errorMessage;

	@SerializedName("FireBaseId")
	private String fireBaseId;

	@SerializedName("Password")
	private String password;

	@SerializedName("Email")
	private String Email;

	@SerializedName("IsPinCreated")
	private String IsPinCreated;

	@SerializedName("AssociateStatus")
	private String AssociateStatus;

	public String getAssociateStatus() {
		return AssociateStatus;
	}

	public String getIsPinCreated() {
		return IsPinCreated;
	}

	public String getEmail() {
		return Email;
	}

	public String getMobileNo(){
		return mobileNo;
	}

	public String getStatus(){
		return status;
	}

	public String getLoginId(){
		return loginId;
	}

	public String getDeviceId(){
		return deviceId;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getProfilePic(){
		return profilePic;
	}

	public String getPkUserId(){
		return pkUserId;
	}

	public String getLastName(){
		return lastName;
	}

	public String getErrorMessage(){
		return errorMessage;
	}

	public String getFireBaseId(){
		return fireBaseId;
	}

	public String getPassword(){
		return password;
	}
}