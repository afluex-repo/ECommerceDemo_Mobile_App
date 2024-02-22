package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

public class LstbannerItem{

	@SerializedName("BannerImage")
	private String bannerImage;

	public String getBannerImage(){
		return bannerImage;
	}
}