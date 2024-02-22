package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseOrders {

	@SerializedName("Status")
	private String status;

	@SerializedName("CustomerId")
	private String customerId;

	@SerializedName("ErrorMessage")
	private String errorMessage;

	@SerializedName("lstorder")
	private List<LstorderItem> lstorder;

	public String getStatus(){
		return status;
	}

	public String getCustomerId(){
		return customerId;
	}

	public String getErrorMessage(){
		return errorMessage;
	}

	public List<LstorderItem> getLstorder(){
		return lstorder;
	}
}