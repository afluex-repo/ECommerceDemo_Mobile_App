package afluex.mlm.demo.app;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Abhishek on 1/3/18.
 */

public class PreferencesManager {

    //app login variables
    private static final String PREF_NAME = "com.cryptoxin.PREF";
    private static final String userid = "userid";
    private static final String firstName = "firstName";
    private static final String lastName = "lastName";
    private static final String LoginId = "LoginId";
    private static final String isPinCreated = "isPinCreated";
    private static final String TransactionPIN = "TransactionPIN";
    private static final String AssociateStatus = "AssociateStatus";


    private static final String taxiowneruid = "taxiowneruid";
    private static final String servicetype = "servicetype";

    private static final String fullname = "fullname";
    private static final String email = "email";
    private static final String mobileno = "mobileno";
    private static final String alternatemobile = "alternatemobile";
    private static final String aadhaar = "aadhaar";
    private static final String operationcity = "operationcity";
    private static final String completeprofile = "completeprofile";
    private static final String loginstatus = "loginstatus";
    private static final String dutystatus = "dutystatus";
    private static final String image = "image";
    private static final String address = "address";
    private static final String zipcode = "zipcode";
    private static final String vehicleno = "vehicleno";
    private static final String vehiclename = "vehiclename";
    private static final String vehicleimage = "vehicleimage";
    private static final String operationcityid = "operationcityid";
    private static final String countdocs = "countdocs";
    private static final String bookingtableid = "bookingtableid";
    private static final String language = "language";

    public static PreferencesManager sInstance;
    private final SharedPreferences mPref;

    public static boolean isNowLoauched = true;
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    private PreferencesManager(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    //for fragment
    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
    }

    //for getting instance
    public static synchronized PreferencesManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
        return sInstance;
    }

    public boolean clear() {
        return mPref.edit().clear().commit();
    }

    //firstTime
    public void setIsFirstTimeLaunch(Boolean value) {
        mPref.edit().putBoolean(IS_FIRST_TIME_LAUNCH, value).apply();
    }

    public boolean getIsFirstTimeLaunch() {
        return mPref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    //id
    public void setUserid(String value) {
        mPref.edit().putString(userid, value).apply();
    }

    public String getUserid() {
        return mPref.getString(userid, "");
    }

    //AssociateStatus
    public void setAssociateStatus(String value) {
        mPref.edit().putString(AssociateStatus, value).apply();
    }

    public String getAssociateStatus() {
        return mPref.getString(AssociateStatus, "");
    }

    //isPinCreated
    public void setIsPinCreated(String value) {
        mPref.edit().putString(isPinCreated, value).apply();
    }

    public String getIsPinCreated() {
        return mPref.getString(isPinCreated, "false");
    }

    //TransactionPIN
    public void setTransactionPIN(String value) {
        mPref.edit().putString(TransactionPIN, value).apply();
    }

    public String getTransactionPIN() {
        return mPref.getString(TransactionPIN, "");
    }

    //firstName
    public void setFirstName(String value) {
        mPref.edit().putString(firstName, value).apply();
    }

    public String getFirstName() {
        return mPref.getString(firstName, "");
    }

    //lastname
    public void setLastName(String value) {
        mPref.edit().putString(lastName, value).apply();
    }

    public String getLastName() {
        return mPref.getString(lastName, "");
    }

    //taxiowneruid
    public void setTaxiowneruid(String value) {
        mPref.edit().putString(taxiowneruid, value).apply();
    }

    public String getTaxiowneruid() {
        return mPref.getString(taxiowneruid, "");
    }

    //servicetype
    public void setServicetype(String value) {
        mPref.edit().putString(servicetype, value).apply();
    }

    public String getServicetype() {
        return mPref.getString(servicetype, "");
    }

    //LoginId
    public void setLoginId(String value) {
        mPref.edit().putString(LoginId, value).apply();
    }

    public String getLoginId() {
        return mPref.getString(LoginId, "");
    }

    //fullname
    public void setFullname(String value) {
        mPref.edit().putString(fullname, value).apply();
    }

    public String getFullname() {
        return mPref.getString(fullname, "");
    }

    //email
    public void setEmail(String value) {
        mPref.edit().putString(email, value).apply();
    }

    public String getEmail() {
        return mPref.getString(email, "");
    }

    //mobileno
    public void setMobileno(String value) {
        mPref.edit().putString(mobileno, value).apply();
    }

    public String getMobileno() {
        return mPref.getString(mobileno, "");
    }

    //alternatemobile
    public void setAlternatemobile(String value) {
        mPref.edit().putString(alternatemobile, value).apply();
    }

    public String getAlternatemobile() {
        return mPref.getString(alternatemobile, "");
    }

    //aadhaar
    public void setAadhaar(String value) {
        mPref.edit().putString(aadhaar, value).apply();
    }

    public String getAadhaar() {
        return mPref.getString(aadhaar, "");
    }

    //operationcity
    public void setOperationcity(String value) {
        mPref.edit().putString(operationcity, value).apply();
    }

    public String getOperationcity() {
        return mPref.getString(operationcity, "");
    }

    //completeprofile
    public void setCompleteprofile(String value) {
        mPref.edit().putString(completeprofile, value).apply();
    }

    public String getCompleteprofile() {
        return mPref.getString(completeprofile, "");
    }

    //loginstatus
    public void setLoginstatus(String value) {
        mPref.edit().putString(loginstatus, value).apply();
    }

    public String getLoginstatus() {
        return mPref.getString(loginstatus, "");
    }

    //dutystatus
    public void setDutystatus(String value) {
        mPref.edit().putString(dutystatus, value).apply();
    }

    public String getDutystatus() {
        return mPref.getString(dutystatus, "");
    }

    //image
    public void setImage(String value) {
        mPref.edit().putString(image, value).apply();
    }

    public String getImage() {
        return mPref.getString(image, "");
    }

    //address
    public void setAddress(String value) {
        mPref.edit().putString(address, value).apply();
    }

    public String getAddress() {
        return mPref.getString(address, "");
    }

    //zipcode
    public void setZipcode(String value) {
        mPref.edit().putString(zipcode, value).apply();
    }

    public String getZipcode() {
        return mPref.getString(zipcode, "");
    }

    //vehicleno
    public void setVehicleno(String value) {
        mPref.edit().putString(vehicleno, value).apply();
    }

    public String getVehicleno() {
        return mPref.getString(vehicleno, "");
    }

    //vehiclename
    public void setVehiclename(String value) {
        mPref.edit().putString(vehiclename, value).apply();
    }

    public String getVehiclename() {
        return mPref.getString(vehiclename, "");
    }

    //operationcityid
    public void setOperationcityid(String value) {
        mPref.edit().putString(operationcityid, value).apply();
    }

    public String getOperationcityid() {
        return mPref.getString(operationcityid, "");
    }

    //countdocs
    public void setCountdocs(String value) {
        mPref.edit().putString(countdocs, value).apply();
    }

    public String getCountdocs() {
        return mPref.getString(countdocs, "");
    }

    //vehicleimage

    public void setVehicleimage(String value) {
        mPref.edit().putString(vehicleimage, value).apply();
    }

    public String getVehicleimage() {
        return mPref.getString(vehicleimage, "");
    }

    //bookingtableid
    public void setBookingtableid(String value) {
        mPref.edit().putString(bookingtableid, value).apply();
    }

    public String getBookingtableid() {
        return mPref.getString(bookingtableid, "");
    }

    //language
    public void setLanguage(String value) {
        mPref.edit().putString(language, value).apply();
    }

    public String getLanguage() {
        return mPref.getString(language, "");
    }

}
