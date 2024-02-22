package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

public class LstsubCategoryItem{

    @SerializedName("SubCategory")
    private String subCategoryName;

    @SerializedName("FK_MainCategory")
    private String fKMainCategory;

    @SerializedName("FK_CategoryID")
    private String fKCategoryID;

    @SerializedName("id")
    private String pKSubCategoryID;

    public String getSubCategoryName(){
        return subCategoryName;
    }

    public String getFKMainCategory(){
        return fKMainCategory;
    }

    public String getFKCategoryID(){
        return fKCategoryID;
    }

    public String getPKSubCategoryID(){
        return pKSubCategoryID;
    }
}