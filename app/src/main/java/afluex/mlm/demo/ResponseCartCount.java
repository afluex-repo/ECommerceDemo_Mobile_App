package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

public class ResponseCartCount {

	@SerializedName("Status")
	private String status;

	@SerializedName("Count")
	private String count;

	@SerializedName("CustomerId")
	private String customerId;

	@SerializedName("ErrorMessage")
	private String errorMessage;

	public String getStatus(){
		return status;
	}

	public String getCount(){
		return count;
	}

	public String getCustomerId(){
		return customerId;
	}

	public String getErrorMessage(){
		return errorMessage;
	}
}