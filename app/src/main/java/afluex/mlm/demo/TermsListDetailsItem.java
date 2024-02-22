package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

public class TermsListDetailsItem{

	@SerializedName("Termscondition")
	private String termscondition;

	public String getTermscondition(){
		return termscondition;
	}
}