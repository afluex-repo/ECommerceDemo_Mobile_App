package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTermsConditions{

	@SerializedName("Status")
	private String status;

	@SerializedName("lstTermsList")
	private List<LstTermsListItem> lstTermsList;

	public String getStatus(){
		return status;
	}

	public List<LstTermsListItem> getLstTermsList(){
		return lstTermsList;
	}
}