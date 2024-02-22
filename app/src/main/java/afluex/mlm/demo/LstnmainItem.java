package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LstnmainItem{

    @SerializedName("MainData")
    private List<MainCategoryDetailsItem> mainCategoryDetails;
  @SerializedName("image")
    private String image;
  @SerializedName("MainCategoryId")
    private String MainCategoryId;
@SerializedName("MainCategory")
    private String MainCategory;

    public List<MainCategoryDetailsItem> getMainCategoryDetails(){
        return mainCategoryDetails;
    }

    public void setMainCategoryDetails(List<MainCategoryDetailsItem> mainCategoryDetails) {
        this.mainCategoryDetails = mainCategoryDetails;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMainCategoryId() {
        return MainCategoryId;
    }

    public void setMainCategoryId(String mainCategoryId) {
        MainCategoryId = mainCategoryId;
    }

    public String getMainCategory() {
        return MainCategory;
    }

    public void setMainCategory(String mainCategory) {
        MainCategory = mainCategory;
    }
}