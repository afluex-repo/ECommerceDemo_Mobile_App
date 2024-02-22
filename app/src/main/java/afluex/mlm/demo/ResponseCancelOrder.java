package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

public class ResponseCancelOrder {

	@SerializedName("Status")
	private String status;

	@SerializedName("PK_OrderDetailsID")
	private String pKOrderDetailsID;

	@SerializedName("CustomerId")
	private String customerId;

	@SerializedName("ErrorMessage")
	private String errorMessage;

	public String getStatus(){
		return status;
	}

	public String getPKOrderDetailsID(){
		return pKOrderDetailsID;
	}

	public String getCustomerId(){
		return customerId;
	}

	public String getErrorMessage(){
		return errorMessage;
	}
}