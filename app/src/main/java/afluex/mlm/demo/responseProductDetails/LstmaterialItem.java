package afluex.mlm.demo.responseProductDetails;

import com.google.gson.annotations.SerializedName;

public class LstmaterialItem{

	@SerializedName("Status")
	private String status;

	@SerializedName("MaterialID")
	private String materialID;

	@SerializedName("MaterialName")
	private String materialName;

	public String getStatus(){
		return status;
	}

	public String getMaterialID(){
		return materialID;
	}

	public String getMaterialName(){
		return materialName;
	}
}