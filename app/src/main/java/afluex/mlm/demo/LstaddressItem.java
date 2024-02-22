package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LstaddressItem{

	@SerializedName("Address")
	private List<AddressItem> address;

	@SerializedName("Title")
	private String title;

	public List<AddressItem> getAddress(){
		return address;
	}

	public String getTitle(){
		return title;
	}
}