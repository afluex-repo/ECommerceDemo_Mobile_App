package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainCategoryDetailsItem{

    @SerializedName("PK_CategoryID")
    private String fKMainCategory;

    @SerializedName("Category")
    private String mainCategoryName;

    @SerializedName("CategoryData")
    private List<LstsubCategoryItem> lstCategory;

    @SerializedName("Image")
    private String image;

    public String getFKMainCategory(){
        return fKMainCategory;
    }

    public String getMainCategoryName(){
        return mainCategoryName;
    }

    public void setfKMainCategory(String fKMainCategory) {
        this.fKMainCategory = fKMainCategory;
    }

    public void setMainCategoryName(String mainCategoryName) {
        this.mainCategoryName = mainCategoryName;
    }

    public String getfKMainCategory() {
        return fKMainCategory;
    }

    public List<LstsubCategoryItem> getLstCategory() {
        return lstCategory;
    }

    public void setLstCategory(List<LstsubCategoryItem> lstCategory) {
        this.lstCategory = lstCategory;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage(){
        return image;
    }
}