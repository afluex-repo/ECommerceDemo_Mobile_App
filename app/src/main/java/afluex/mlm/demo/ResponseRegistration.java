package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

public class ResponseRegistration {

    @SerializedName("MobileNo")
    private String mobileNo;

    @SerializedName("Status")
    private String status;

    @SerializedName("Email")
    private String email;

    @SerializedName("LoginId")
    private String loginId;

    @SerializedName("DeviceId")
    private String deviceId;

    @SerializedName("FirstName")
    private String firstName;

    @SerializedName("Pk_UserId")
    private String pkUserId;

    @SerializedName("OTP")
    private String oTP;

    @SerializedName("FireBaseId")
    private String fireBaseId;

    @SerializedName("PinCode")
    private String pinCode;

    @SerializedName("SponsorId")
    private String sponsorId;

    @SerializedName("ProfilePic")
    private String profilePic;

    @SerializedName("LastName")
    private String lastName;

    @SerializedName("ErrorMessage")
    private String errorMessage;

    @SerializedName("Password")
    private String password;

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

    public String getMobileNo() {
        return mobileNo;
    }

    public String getStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPkUserId() {
        return pkUserId;
    }

    public String getOTP() {
        return oTP;
    }

    public String getFireBaseId() {
        return fireBaseId;
    }

    public String getPinCode() {
        return pinCode;
    }

    public String getSponsorId() {
        return sponsorId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getLastName() {
        return lastName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getPassword() {
        return password;
    }
}