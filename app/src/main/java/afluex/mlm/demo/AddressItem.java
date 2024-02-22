package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

public class AddressItem {

    @SerializedName("HouseNo")
    private String houseNo;

    @SerializedName("Locality")
    private String locality;

    @SerializedName("LandMark")
    private String landMark;

    @SerializedName("AddressType")
    private String addressType;

    @SerializedName("PinCode")
    private String pinCode;

    @SerializedName("Pk_AddressId")
    private String Pk_AddressId;

    @SerializedName("ContatNo")
    private String ContatNo;

    @SerializedName("IsDefault")
    private String IsDefault;

    @SerializedName("Name")
    private String Name;


    private Boolean selected;

    public String getContatNo() {
        return ContatNo;
    }

    public String getIsDefault() {
        return IsDefault;
    }

    public String getName() {
        return Name;
    }

    public String getPk_AddressId() {
        return Pk_AddressId;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public String getLocality() {
        return locality;
    }

    public String getLandMark() {
        return landMark;
    }

    public String getAddressType() {
        return addressType;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public void setPk_AddressId(String pk_AddressId) {
        Pk_AddressId = pk_AddressId;
    }

    public void setContatNo(String contatNo) {
        ContatNo = contatNo;
    }

    public void setIsDefault(String isDefault) {
        IsDefault = isDefault;
    }

    public void setName(String name) {
        Name = name;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}