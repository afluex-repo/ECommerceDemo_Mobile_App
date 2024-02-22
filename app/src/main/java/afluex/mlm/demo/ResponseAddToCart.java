package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

public class ResponseAddToCart {

	@SerializedName("ProductQuantity")
	private String productQuantity;

	@SerializedName("Status")
	private String status;

	@SerializedName("Fk_vendorId")
	private String fkVendorId;

	@SerializedName("CustomerId")
	private String customerId;

	@SerializedName("ErrorMessage")
	private String errorMessage;

	@SerializedName("ProductInfoCode")
	private String productInfoCode;

	public String getProductQuantity(){
		return productQuantity;
	}

	public String getStatus(){
		return status;
	}

	public String getFkVendorId(){
		return fkVendorId;
	}

	public String getCustomerId(){
		return customerId;
	}

	public String getErrorMessage(){
		return errorMessage;
	}

	public String getProductInfoCode(){
		return productInfoCode;
	}
}