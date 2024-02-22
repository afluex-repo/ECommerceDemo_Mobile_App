package afluex.mlm.demo.responseProductDetails;

import com.google.gson.annotations.SerializedName;

public class LstsizeItem{

	@SerializedName("Status")
	private String status;

	@SerializedName("SizeID")
	private String sizeID;

	@SerializedName("SizeName")
	private String sizeName;

	public String getStatus(){
		return status;
	}

	public String getSizeID(){
		return sizeID;
	}

	public String getSizeName(){
		return sizeName;
	}
}