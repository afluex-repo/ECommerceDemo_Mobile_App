package afluex.mlm.demo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelSearch {

    @SerializedName("Newarrivals")
    @Expose
    private ArrayList<ModelNewArrivals>newArrivals;


    public ArrayList<ModelNewArrivals> getNewArrivals() {
        return newArrivals;
    }

    public void setNewArrivals(ArrayList<ModelNewArrivals> newArrivals) {
        this.newArrivals = newArrivals;
    }
}
