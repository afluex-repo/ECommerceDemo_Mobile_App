package afluex.mlm.demo.responseProductDetails;

import com.google.gson.annotations.SerializedName;

public class LstcolorItem{

	@SerializedName("Status")
	private String status;

	@SerializedName("ColorCode")
	private String colorCode;

	@SerializedName("ColorID")
	private String colorID;

	@SerializedName("ColorName")
	private String colorName;

	public String getStatus(){
		return status;
	}

	public String getColorCode(){
		return colorCode;
	}

	public String getColorID(){
		return colorID;
	}

	public String getColorName(){
		return colorName;
	}
}