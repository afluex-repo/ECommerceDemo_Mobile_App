package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGetAddress{

	@SerializedName("Status")
	private String status;

	@SerializedName("CustomerId")
	private String customerId;

	@SerializedName("lstaddress")
	private List<LstaddressItem> lstaddress;

	public String getStatus(){
		return status;
	}

	public String getCustomerId(){
		return customerId;
	}

	public List<LstaddressItem> getLstaddress(){
		return lstaddress;
	}
}