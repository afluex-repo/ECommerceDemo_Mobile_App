package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

public class CartDetailsItem {

    @SerializedName("DeliveryCharge")
    private String deliveryCharge;

    @SerializedName("ProductInfo")
    private String productInfo;

    @SerializedName("VendorName")
    private String vendorName;

    @SerializedName("PK_CartID")
    private String pKCartID;

    @SerializedName("ProductName")
    private String productName;

    @SerializedName("Rate")
    private String rate;

    @SerializedName("VendorId")
    private String vendorId;

    @SerializedName("Quantity")
    private String quantity;

    @SerializedName("ProductInfoCode")
    private String productInfoCode;

    @SerializedName("SubTotal")
    private String subTotal;

    @SerializedName("ImagePath")
    private String ImagePath;

    @SerializedName("RedeemPrice")
    private String RedeemPrice;

    public String getRedeemPrice() {
        return RedeemPrice;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getPKCartID() {
        return pKCartID;
    }

    public String getProductName() {
        return productName;
    }

    public String getRate() {
        return rate;
    }

    public String getVendorId() {
        return vendorId;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getProductInfoCode() {
        return productInfoCode;
    }

    public String getSubTotal() {
        return subTotal;
    }
}