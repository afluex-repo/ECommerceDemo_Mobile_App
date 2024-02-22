package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

public class ResponseSponsor {

	@SerializedName("SponsorId")
	private String sponsorId;

	@SerializedName("Status")
	private String status;

	@SerializedName("SponsorName")
	private String sponsorName;

	@SerializedName("ErrorMessage")
	private Object errorMessage;

	public String getSponsorId(){
		return sponsorId;
	}

	public String getStatus(){
		return status;
	}

	public String getSponsorName(){
		return sponsorName;
	}

	public Object getErrorMessage(){
		return errorMessage;
	}
}