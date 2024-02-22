package afluex.mlm.demo.responseProductDetails;

import com.google.gson.annotations.SerializedName;

public class LstramItem{

	@SerializedName("RamName")
	private String ramName;

	@SerializedName("Status")
	private String status;

	@SerializedName("RamID")
	private String ramID;

	public String getRamName(){
		return ramName;
	}

	public String getStatus(){
		return status;
	}

	public String getRamID(){
		return ramID;
	}
}