package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

public class ResponseSaveAddress {

	@SerializedName("HouseNo")
	private Object houseNo;

	@SerializedName("Status")
	private String status;

	@SerializedName("Locality")
	private Object locality;

	@SerializedName("LandMark")
	private Object landMark;

	@SerializedName("CustomerId")
	private Object customerId;

	@SerializedName("AddressType")
	private Object addressType;

	@SerializedName("ErrorMessage")
	private String errorMessage;

	@SerializedName("PinCode")
	private Object pinCode;

	public Object getHouseNo(){
		return houseNo;
	}

	public String getStatus(){
		return status;
	}

	public Object getLocality(){
		return locality;
	}

	public Object getLandMark(){
		return landMark;
	}

	public Object getCustomerId(){
		return customerId;
	}

	public Object getAddressType(){
		return addressType;
	}

	public String getErrorMessage(){
		return errorMessage;
	}

	public Object getPinCode(){
		return pinCode;
	}
}