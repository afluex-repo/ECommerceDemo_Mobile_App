package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

public class ResponseRemoveCartItem {

	@SerializedName("Status")
	private String status;

	@SerializedName("CartId")
	private String cartId;

	@SerializedName("CustomerId")
	private String customerId;

	@SerializedName("ErrorMessage")
	private String errorMessage;

	public String getStatus(){
		return status;
	}

	public String getCartId(){
		return cartId;
	}

	public String getCustomerId(){
		return customerId;
	}

	public String getErrorMessage(){
		return errorMessage;
	}
}