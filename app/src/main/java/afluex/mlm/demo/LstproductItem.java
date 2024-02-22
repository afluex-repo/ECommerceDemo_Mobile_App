package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

public class LstproductItem{

	@SerializedName("Discount")
	private String discount;

	@SerializedName("ProductName")
	private String productName;

	@SerializedName("Images")
	private String images;

	@SerializedName("MRP")
	private String mRP;

	@SerializedName("Pk_ProductId")
	private String pkProductId;

	@SerializedName("OfferedPrice")
	private String offeredPrice;

	@SerializedName("ColorID")
	private String ColorID;

	public String getDiscount(){
		return discount;
	}

	public String getProductName(){
		return productName;
	}

	public String getImages(){
		return images;
	}

	public String getMRP(){
		return mRP;
	}

	public String getPkProductId(){
		return pkProductId;
	}

	public String getOfferedPrice(){
		return offeredPrice;
	}


	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getmRP() {
		return mRP;
	}

	public void setmRP(String mRP) {
		this.mRP = mRP;
	}

	public void setPkProductId(String pkProductId) {
		this.pkProductId = pkProductId;
	}

	public void setOfferedPrice(String offeredPrice) {
		this.offeredPrice = offeredPrice;
	}

	public String getColorID() {
		return ColorID;
	}

	public void setColorID(String colorID) {
		ColorID = colorID;
	}
}