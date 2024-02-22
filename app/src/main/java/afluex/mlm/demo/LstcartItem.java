package afluex.mlm.demo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LstcartItem{

	@SerializedName("CartDetails")
	private List<CartDetailsItem> cartDetails;

	@SerializedName("Title")
	private String title;

	public List<CartDetailsItem> getCartDetails(){
		return cartDetails;
	}

	public String getTitle(){
		return title;
	}
}