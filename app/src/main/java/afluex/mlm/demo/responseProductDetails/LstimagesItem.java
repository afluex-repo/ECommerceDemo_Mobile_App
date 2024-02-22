package afluex.mlm.demo.responseProductDetails;

import com.google.gson.annotations.SerializedName;

public class LstimagesItem{

	@SerializedName("ImagePath")
	private String imagePath;

	public String getImagePath(){
		return imagePath;
	}
}