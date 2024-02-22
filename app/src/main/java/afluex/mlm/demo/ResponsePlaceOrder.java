package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

public class ResponsePlaceOrder {

	@SerializedName("Status")
	private String status;

	@SerializedName("Fk_AddressId")
	private String fkAddressId;

	@SerializedName("OrderNo")
	private String orderNo;

	@SerializedName("CustomerId")
	private String customerId;

	@SerializedName("ErrorMessage")
	private String errorMessage;

	@SerializedName("PaymentMode")
	private String paymentMode;

	public String getStatus(){
		return status;
	}

	public String getFkAddressId(){
		return fkAddressId;
	}

	public String getOrderNo(){
		return orderNo;
	}

	public String getCustomerId(){
		return customerId;
	}

	public String getErrorMessage(){
		return errorMessage;
	}

	public String getPaymentMode(){
		return paymentMode;
	}
}