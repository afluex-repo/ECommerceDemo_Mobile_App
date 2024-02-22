package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCartItems{

	@SerializedName("Status")
	private String status;

	@SerializedName("lstcart")
	private List<LstcartItem> lstcart;

	@SerializedName("CustomerId")
	private String customerId;

	@SerializedName("ErrorMessage")
	private String errorMessage;

	public String getStatus(){
		return status;
	}

	public List<LstcartItem> getLstcart(){
		return lstcart;
	}

	public String getCustomerId(){
		return customerId;
	}

	public String getErrorMessage(){
		return errorMessage;
	}
}