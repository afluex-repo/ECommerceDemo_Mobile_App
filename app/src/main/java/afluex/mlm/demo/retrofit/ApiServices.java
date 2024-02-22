package afluex.mlm.demo.retrofit;



import com.google.gson.JsonObject;

import afluex.mlm.demo.ResponseCategory;
import afluex.mlm.demo.ResponseMenuCategory;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface ApiServices {

    @POST("Login1")
    Call<JsonObject> getLoginEnc(@Body JsonObject object);

    @POST("GetStateCity")
    Call<JsonObject> GetStateCity(@Body JsonObject object);

    @POST("GetSponsorName")
    Call<JsonObject> GetSponsorName(@Body JsonObject object);

    @POST("CheckMobileNo")
    Call<JsonObject> CheckMobileNo(@Body JsonObject object);

    @POST("TermsCondition")
    Call<JsonObject> GetTermsCondition();

    @POST("SendOTP")
    Call<JsonObject> SendOTP(@Body JsonObject object);

    @POST("Registration1")
    Call<JsonObject> getRegistration(@Body JsonObject object);

    @POST("DownlineList")
    Call<JsonObject> getDownLine(@Body JsonObject object);

    @POST("DirectList")
    Call<JsonObject> getDirectList(@Body JsonObject object);

    @POST("TeamStatus")
    Call<JsonObject> getTeamStatus(@Body JsonObject object);

    @POST("ChangePassword")
    Call<JsonObject> ChangePassword(@Body JsonObject object);

    @POST("DashBoard")
    Call<JsonObject> getMLMDashBoard(@Body JsonObject object);

    @POST("ViewProfile")
    Call<JsonObject> getViewProfile(@Body JsonObject object);

    @POST("UpdateProfile")
    Call<JsonObject> updateProfile(@Body JsonObject object);

    @POST("AutoLogin")
    Call<JsonObject> getAutoLogin(@Body JsonObject object);

    @POST("Logout")
    Call<JsonObject> getLogout(@Body JsonObject object);

    @POST("ForgetPass")
    Call<JsonObject> ForgetPass(@Body JsonObject object);

    @POST("PassBook")
    Call<JsonObject> PassBook(@Body JsonObject object);

    @POST("GetLevelWiseList")
    Call<JsonObject> GetLevelWiseList(@Body JsonObject object);

    @POST("UpdateProfile")
    Call<JsonObject> UpdateProfile(@Body JsonObject object);

    @POST("CreateOrder")
    Call<JsonObject> CreateOrder(@Body JsonObject object);

    @POST("DWPayLedger")
    Call<JsonObject> DWPayLedger(@Body JsonObject object);

    @POST("CommissionLedger")
    Call<JsonObject> CommissionLedger(@Body JsonObject object);

    @POST("SupportList")
    Call<JsonObject> SupportList(@Body JsonObject object);

    @POST("ShoppingLedger")
    Call<JsonObject> ShoppingLedger(@Body JsonObject object);

    @POST("GetCommissionWallet")
    Call<JsonObject> GetCommissionWallet(@Body JsonObject object);

    @POST("GetUnclearWallet")
    Call<JsonObject> GetUnclearWallet(@Body JsonObject object);

    @POST("FetchPaymentByOrder")
    Call<JsonObject> FetchPaymentByOrder(@Body JsonObject object);

    @POST("PrepaidRecharge")
    Call<JsonObject> PrepaidRecharge(@Body JsonObject object);

    @POST("ForRechargeMobilePrepaid")
    Call<JsonObject> PrepaidRechargeJio(@Body JsonObject object);

    @POST("ForFetchPlanPrepaid")
    Call<JsonObject> FetchPlanJio(@Body JsonObject object);

    @POST("PostpaidRecharge")
    Call<JsonObject> PostpaidRecharge(@Body JsonObject object);

    @POST("Pay")
    Call<JsonObject> Pay(@Body JsonObject object);

    @GET()
    Call<JsonObject> getAPI(@Url String url);

    @Multipart
    @POST("ImageUpload/MediaUpload")
    Call<JsonObject> uploadImage(@Part("Pk_UserId") RequestBody userId,
                                 @Part("DocumentType") RequestBody imgType, @Part() MultipartBody.Part file);

//    @Multipart
//    @POST("Support/MediaUpload")
//    Call<ResponseSupportUpload> uploadSupportQuery(@Part("Pk_UserId") RequestBody userId,
//                                                   @Part("Subject") RequestBody Subject,
//                                                   @Part("Message") RequestBody Message,
//                                                   @Part() MultipartBody.Part file);

    @POST("CreatePin")
    Call<JsonObject> CreatePin(@Body JsonObject object);

    @POST("ActivateId")
    Call<JsonObject> ActivateId(@Body JsonObject object);

    @POST("GetOrderValue")
    Call<JsonObject> getOrderValue(@Body JsonObject object);

    // Recent Recharges

    @POST("ElectricityBillPayment")
    Call<JsonObject> billPayment(@Body JsonObject object);

    @POST("GasBillPayment")
    Call<JsonObject> gasbillPayment(@Body JsonObject object);

    @POST("Insurance")
    Call<JsonObject> insurancebillPayment(@Body JsonObject object);

    @POST("BroadBandProvider")
    Call<JsonObject> broadBandBillPayment(@Body JsonObject object);

    @POST("WaterBillPayment")
    Call<JsonObject> waterBillPayment(@Body JsonObject object);

    @POST("GetAllProvider")
    Call<JsonObject> getAllProvider(@Body JsonObject object);

    @POST("GetAllProviderStateWise")
    Call<JsonObject> getElectricityProvider(@Body JsonObject object);


    @POST("ElectricityVerification")
    Call<JsonObject> getElectricityVerification(@Body JsonObject verification);

    @POST("WaterBillVerification")
    Call<JsonObject> getWaterBillVerification(@Body JsonObject verification);

    @POST("GasPaymentVerification")
    Call<JsonObject> getGasPaymentVerification(@Body JsonObject verification);

    @POST("BroadBandVerify")
    Call<JsonObject> getBroadBandVerify(@Body JsonObject verification);

    @POST("InsuranceVerify")
    Call<JsonObject> getInsuranceVerify(@Body JsonObject verification);

    @POST("GetServicesDetailsForApplication")
    Call<JsonObject> getDashBoardMenu();

    @POST("DTH")
    Call<JsonObject> getDTHRecharge(@Body JsonObject param);

    @POST("StateList")
    Call<JsonObject> StateList();

    //    'DTH Recharge'
//'Postpaid'
//'Prepaid Recharge'
//
//Gas
//Water
//Insurance
//Electricity
//Broad Band
    @POST("RecentRecharges")
    Call<JsonObject> getRecentRecharges(@Body JsonObject param);

    @POST("BannerList")
    Call<JsonObject> BannerList();

    @POST("BusinessDashboardBanner")
    Call<JsonObject> BusinessDashboardBanner();

    @POST("GetShoppingDetails")
    Call<JsonObject> GetShoppingDetails(@Body JsonObject param);

    @POST("GetCategoryWiseProduct")
    Call<ResponseCategory> GetCategoryWiseProduct(@Body JsonObject param);

    @POST("UpdateNotification")
    Call<JsonObject> UpdateNotification(@Body JsonObject param);

    @Multipart
    @POST("OffinePayment/MediaUpload")
    Call<JsonObject> uploadOfflineRequest(@Part("Fk_UserId") RequestBody userId,
                                          @Part("PaymentMode") RequestBody PaymentMode,
                                          @Part("TransactionNo") RequestBody TransactionNo,
                                          @Part("TransactionDate") RequestBody TransactionDate,
                                          @Part("Amount") RequestBody Amount,
                                          @Part("BankName") RequestBody BankName,
                                          @Part("BranchName") RequestBody BranchName,
                                          @Part() MultipartBody.Part file);

//    Shopping

    @POST("ProductList")
    Call<JsonObject> shopDashborad();

    @POST("QuickView")
    Call<JsonObject> getProductDetails(@Body JsonObject param);

    @POST("AddToCart")
    Call<JsonObject> AddToCart(@Body JsonObject param);

    @POST("CartCount")
    Call<JsonObject> CartCount(@Body JsonObject param);

    @POST("ShowCart")
    Call<JsonObject> ShowCart(@Body JsonObject param);

    @POST("RemoveItemFromCart")
    Call<JsonObject> RemoveItemFromCart(@Body JsonObject param);

    @POST("PrimeProductList")
    Call<JsonObject> PrimeProductList();

    @POST("Popup")
    Call<JsonObject> getPopup();

    @POST("SaveAddress")
    Call<JsonObject> SaveAddress(@Body JsonObject param);

    @POST("GetAddress")
    Call<JsonObject> GetAddress(@Body JsonObject param);

    @POST("PlaceOrder")
    Call<JsonObject> PlaceOrder(@Body JsonObject param);

    @POST("MyOrders")
    Call<JsonObject> MyOrders(@Body JsonObject param);

    @POST("CancelOrder")
    Call<JsonObject> CancelOrder(@Body JsonObject param);

    @POST("TransferToBank")
    Call<JsonObject> TransferToBank(@Body JsonObject param);

    @POST("TransferCommissionToDwpewallet")
    Call<JsonObject> TransferCommissionToDwpewallet(@Body JsonObject param);

    @POST("GetMenu")
    Call<ResponseMenuCategory> GetMenu();


    @POST("GlobalSearch")
    Call<JsonObject> GlobalSearch(@Body JsonObject param);
}