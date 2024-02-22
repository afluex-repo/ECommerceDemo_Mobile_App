package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

public class OrderSummaryItem{

	@SerializedName("ExpectedDelivery")
	private String expectedDelivery;

	@SerializedName("ProductInfo")
	private String productInfo;

	@SerializedName("OrderStatus")
	private String orderStatus;

	@SerializedName("ProductName")
	private String productName;

	@SerializedName("Rate")
	private String rate;

	@SerializedName("Amount")
	private String amount;

	@SerializedName("ImagePath")
	private String imagePath;

	@SerializedName("Quantity")
	private String quantity;

	@SerializedName("PK_OrderDetailsID")
	private String PK_OrderDetailsID;

	@SerializedName("RedeemPrice")
	private String RedeemPrice;

	@SerializedName("DeliveryCharge")
	private String DeliveryCharge;

	public String getDeliveryCharge() {
		return DeliveryCharge;
	}

	public String getRedeemPrice() {
		return RedeemPrice;
	}

	public String getPK_OrderDetailsID() {
		return PK_OrderDetailsID;
	}

	public String getExpectedDelivery(){
		return expectedDelivery;
	}

	public String getProductInfo(){
		return productInfo;
	}

	public String getOrderStatus(){
		return orderStatus;
	}

	public String getProductName(){
		return productName;
	}

	public String getRate(){
		return rate;
	}

	public String getAmount(){
		return amount;
	}

	public String getImagePath(){
		return imagePath;
	}

	public String getQuantity(){
		return quantity;
	}
}