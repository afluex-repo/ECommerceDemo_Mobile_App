package afluex.mlm.demo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelNewArrivals {
    @SerializedName("ColorName")
    @Expose
    private Object colorName;
    @SerializedName("Images")
    @Expose
    private String images;
    @SerializedName("MRP")
    @Expose
    private String mrp;
    @SerializedName("OfferedPrice")
    @Expose
    private String offeredPrice;
    @SerializedName("Pk_ProductId")
    @Expose
    private String pkProductId;
    @SerializedName("ProductName")
    @Expose
    private String productName;
    @SerializedName("SoldOutCss")
    @Expose
    private String soldOutCss;

    public Object getColorName() {
        return colorName;
    }

    public void setColorName(Object colorName) {
        this.colorName = colorName;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getOfferedPrice() {
        return offeredPrice;
    }

    public void setOfferedPrice(String offeredPrice) {
        this.offeredPrice = offeredPrice;
    }

    public String getPkProductId() {
        return pkProductId;
    }

    public void setPkProductId(String pkProductId) {
        this.pkProductId = pkProductId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSoldOutCss() {
        return soldOutCss;
    }

    public void setSoldOutCss(String soldOutCss) {
        this.soldOutCss = soldOutCss;
    }
}
