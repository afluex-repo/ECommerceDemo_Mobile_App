package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LstTermsListItem{

	@SerializedName("TermsListDetails")
	private List<TermsListDetailsItem> termsListDetails;

	@SerializedName("Title")
	private String title;

	public List<TermsListDetailsItem> getTermsListDetails(){
		return termsListDetails;
	}

	public String getTitle(){
		return title;
	}
}