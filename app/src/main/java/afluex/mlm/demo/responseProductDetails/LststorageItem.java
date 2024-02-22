package afluex.mlm.demo.responseProductDetails;

import com.google.gson.annotations.SerializedName;

public class LststorageItem{

	@SerializedName("Status")
	private String status;

	@SerializedName("StorageName")
	private String storageName;

	@SerializedName("StorageID")
	private String storageID;

	public String getStatus(){
		return status;
	}

	public String getStorageName(){
		return storageName;
	}

	public String getStorageID(){
		return storageID;
	}
}