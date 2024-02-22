package afluex.mlm.demo.responseProductDetails;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VarientsItem{

	@SerializedName("lstcolor")
	private List<LstcolorItem> lstcolor;

	@SerializedName("lstflavour")
	private List<LstflavourItem> lstflavour;

	@SerializedName("lstimages")
	private List<LstimagesItem> lstimages;

	@SerializedName("lstram")
	private List<LstramItem> lstram;

	@SerializedName("lstsize")
	private List<LstsizeItem> lstsize;

	@SerializedName("Title")
	private String title;

	@SerializedName("lstmaterial")
	private List<LstmaterialItem> lstmaterial;

	@SerializedName("lststorage")
	private List<LststorageItem> lststorage;

	public List<LstcolorItem> getLstcolor(){
		return lstcolor;
	}

	public List<LstflavourItem> getLstflavour(){
		return lstflavour;
	}

	public List<LstimagesItem> getLstimages(){
		return lstimages;
	}

	public List<LstramItem> getLstram(){
		return lstram;
	}

	public List<LstsizeItem> getLstsize(){
		return lstsize;
	}

	public String getTitle(){
		return title;
	}

	public List<LstmaterialItem> getLstmaterial(){
		return lstmaterial;
	}

	public List<LststorageItem> getLststorage(){
		return lststorage;
	}
}