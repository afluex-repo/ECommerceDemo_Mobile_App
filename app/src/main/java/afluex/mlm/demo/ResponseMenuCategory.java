package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMenuCategory {

    @SerializedName("Status")
    private String status;

    @SerializedName("menu")
    private List<LstnmainItem> lstnmain;

    public String getStatus(){
        return status;
    }

    public List<LstnmainItem> getLstnmain(){
        return lstnmain;
    }
}