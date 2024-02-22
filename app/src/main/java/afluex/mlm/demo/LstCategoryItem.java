package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LstCategoryItem {

    @SerializedName("FK_MainCategory")
    private String fKMainCategory;

    @SerializedName("id")
    private String fKCategoryID;

    @SerializedName("SubCategory")
    private String categoryName;

    @SerializedName("LstsubCategory")
    private List<LstsubCategoryItem> lstsubCategory;

    private boolean isOpen;

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getFKMainCategory() {
        return fKMainCategory;
    }

    public String getFKCategoryID() {
        return fKCategoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<LstsubCategoryItem> getLstsubCategory() {
        return lstsubCategory;
    }
}