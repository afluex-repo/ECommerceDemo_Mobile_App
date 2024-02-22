package afluex.mlm.demo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseCategory {
    @SerializedName("lstproduct")
    @Expose

    private ArrayList<LstproductItem> lstproductItemArrayList;


    public ArrayList<LstproductItem> getLstproductItemArrayList() {
        return lstproductItemArrayList;
    }

    public void setLstproductItemArrayList(ArrayList<LstproductItem> lstproductItemArrayList) {
        this.lstproductItemArrayList = lstproductItemArrayList;
    }
}
