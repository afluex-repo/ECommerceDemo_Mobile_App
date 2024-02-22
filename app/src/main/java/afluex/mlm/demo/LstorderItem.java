package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LstorderItem{

	@SerializedName("OrderDetails")
	private List<OrderDetailsItem> orderDetails;

	@SerializedName("Title")
	private String title;

	public List<OrderDetailsItem> getOrderDetails(){
		return orderDetails;
	}

	public String getTitle(){
		return title;
	}
}