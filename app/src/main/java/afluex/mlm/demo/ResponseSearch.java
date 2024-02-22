package afluex.mlm.demo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseSearch {

    @SerializedName("ProductList")
    @Expose
    private ArrayList<ModelSearch>searchArrayList;


    public ArrayList<ModelSearch> getSearchArrayList() {
        return searchArrayList;
    }

    public void setSearchArrayList(ArrayList<ModelSearch> searchArrayList) {
        this.searchArrayList = searchArrayList;
    }
}
