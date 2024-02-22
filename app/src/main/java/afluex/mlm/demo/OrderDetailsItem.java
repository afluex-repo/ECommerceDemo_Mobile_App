package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailsItem{

	@SerializedName("PK_OrderID")
	private String pKOrderID;

	@SerializedName("OrderSummary")
	private List<OrderSummaryItem> orderSummary;

	@SerializedName("OrderAmount")
	private String orderAmount;

	@SerializedName("OrderNo")
	private String orderNo;

	@SerializedName("OrderDate")
	private String orderDate;

	public String getPKOrderID(){
		return pKOrderID;
	}

	public List<OrderSummaryItem> getOrderSummary(){
		return orderSummary;
	}

	public String getOrderAmount(){
		return orderAmount;
	}

	public String getOrderNo(){
		return orderNo;
	}

	public String getOrderDate(){
		return orderDate;
	}
}