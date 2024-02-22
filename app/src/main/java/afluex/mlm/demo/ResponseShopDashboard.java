package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseShopDashboard {

	@SerializedName("Status")
	private String status;

	@SerializedName("lstbanner")
	private List<LstbannerItem> lstbanner;

	@SerializedName("lstproduct")
	private List<LstproductItem> lstproduct;

	public String getStatus(){
		return status;
	}

	public List<LstbannerItem> getLstbanner(){
		return lstbanner;
	}

	public List<LstproductItem> getLstproduct(){
		return lstproduct;
	}
}