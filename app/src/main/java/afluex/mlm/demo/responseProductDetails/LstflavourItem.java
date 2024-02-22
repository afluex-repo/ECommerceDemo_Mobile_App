package afluex.mlm.demo.responseProductDetails;

import com.google.gson.annotations.SerializedName;

public class LstflavourItem{

	@SerializedName("Status")
	private String status;

	@SerializedName("FlavorName")
	private String flavorName;

	@SerializedName("FlavorID")
	private String flavorID;

	public String getStatus(){
		return status;
	}

	public String getFlavorName(){
		return flavorName;
	}

	public String getFlavorID(){
		return flavorID;
	}
}