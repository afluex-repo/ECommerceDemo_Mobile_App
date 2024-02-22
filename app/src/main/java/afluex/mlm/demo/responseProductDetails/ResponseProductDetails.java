package afluex.mlm.demo.responseProductDetails;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseProductDetails{

	@SerializedName("DeliveryCharge")
	private String deliveryCharge;

	@SerializedName("ShareURL")
	private String ShareURL;

	@SerializedName("Status")
	private String status;

	@SerializedName("MaterialID")
	private String materialID;

	@SerializedName("VendorName")
	private String vendorName;

	@SerializedName("Description")
	private String description;

	@SerializedName("ProductName")
	private String productName;

	@SerializedName("RamID")
	private String ramID;

	@SerializedName("MRP")
	private String mRP;

	@SerializedName("ProductID")
	private String productID;

	@SerializedName("Varients")
	private List<VarientsItem> varients;

	@SerializedName("ProductInfoCode")
	private String productInfoCode;

	@SerializedName("StorageID")
	private String storageID;

	@SerializedName("ShortDescription")
	private String shortDescription;

	@SerializedName("ColorID")
	private String colorID;

	@SerializedName("LastSelected")
	private Object lastSelected;

	@SerializedName("FlavorID")
	private String flavorID;

	@SerializedName("lstimages")
	private List<Object> lstimages;

	@SerializedName("Fk_vendorId")
	private String fkVendorId;

	@SerializedName("ImagePath")
	private String imagePath;

	@SerializedName("OfferedPrice")
	private String offeredPrice;

	@SerializedName("SizeID")
	private String sizeID;

	@SerializedName("IsAvailable")
	private String IsAvailable;

	@SerializedName("RedeemPrice")
	private String RedeemPrice;

	@SerializedName("IsCart")
	private String IsCart;

	public String getIsCart() {
		return IsCart;
	}

	public String getRedeemPrice() {
		return RedeemPrice;
	}

	public String getIsAvailable() {
		return IsAvailable;
	}

	public String getShareURL() {
		return ShareURL;
	}

	public String getDeliveryCharge(){
		return deliveryCharge;
	}

	public String getStatus(){
		return status;
	}

	public String getMaterialID(){
		return materialID;
	}

	public String getVendorName(){
		return vendorName;
	}

	public String getDescription(){
		return description;
	}

	public String getProductName(){
		return productName;
	}

	public String getRamID(){
		return ramID;
	}

	public String getMRP(){
		return mRP;
	}

	public String getProductID(){
		return productID;
	}

	public List<VarientsItem> getVarients(){
		return varients;
	}

	public String getProductInfoCode(){
		return productInfoCode;
	}

	public String getStorageID(){
		return storageID;
	}

	public String getShortDescription(){
		return shortDescription;
	}

	public String getColorID(){
		return colorID;
	}

	public Object getLastSelected(){
		return lastSelected;
	}

	public String getFlavorID(){
		return flavorID;
	}

	public List<Object> getLstimages(){
		return lstimages;
	}

	public String getFkVendorId(){
		return fkVendorId;
	}

	public String getImagePath(){
		return imagePath;
	}

	public String getOfferedPrice(){
		return offeredPrice;
	}

	public String getSizeID(){
		return sizeID;
	}
}