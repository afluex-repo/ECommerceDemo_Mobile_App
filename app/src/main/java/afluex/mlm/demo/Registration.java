package afluex.mlm.demo;



import static afluex.mlm.demo.Splash.referId;
import static afluex.mlm.demo.app.AppConfig.PAYLOAD_BUNDLE;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import afluex.mlm.demo.app.PreferencesManager;
import afluex.mlm.demo.common.LoggerUtil;
import afluex.mlm.demo.common.NetworkUtils;
import afluex.mlm.demo.common.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends BaseActivity implements TextWatcher {

    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.et_referral)
    EditText etReferral;
    @BindView(R.id.et_first_name)
    EditText etFirstName;
    @BindView(R.id.et_last_name)
    EditText etLastName;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_pincode)
    EditText etPincode;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_terms)
    TextView tvTerms;
    @BindView(R.id.cb_terms)
    CheckBox cbTerms;
    Dialog otpDialog;
    String enteredOTP = "";
    EditText etOtp1, etOtp2, etOtp3, etOtp4;
    @BindView(R.id.txtSponsorName)
    TextView txtSponsorName;
    boolean validReferral = false;
    boolean validMobile = false;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.progressBarMobile)
    ProgressBar progressBarMobile;
    @BindView(R.id.mobileStatus)
    ImageView mobileStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        String account = getColoredSpanned(getResources().getString(R.string.log_in_text), "#000313");
        String signup = getColoredSpanned("<u>" + getResources().getString(R.string.log_in) + "</u>", "#FF5722");
        tvLogin.setText(Html.fromHtml(account + " " + signup));

        etPincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etPincode.getText().toString().length() == 6) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0)
                        getStateCity();
                    else
                        showMessage(getResources().getString(R.string.alert_internet));
                } else {
                    tvCity.setText("");
                    tvState.setText("");
                }
            }
        });

        etReferral.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etReferral.getText().toString().length() == 8) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0)
                        getReferralName();
                    else
                        showMessage(getResources().getString(R.string.alert_internet));
                } else {
                    progressBar.setVisibility(View.GONE);
                    validReferral = false;
                    txtSponsorName.setTextColor(ContextCompat.getColor(context, R.color.orange));
                    txtSponsorName.setText("Enter Valid Referral invite code.");
                }
            }
        });

        etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etMobile.getText().toString().length() == 10) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0)
                        getValidMobile();
                    else
                        showMessage(getResources().getString(R.string.alert_internet));
                } else {
                    progressBarMobile.setVisibility(View.GONE);
                    validMobile = true;
                    mobileStatus.setImageResource(R.drawable.ic_cross);
//                    txtSponsorName.setTextColor(ContextCompat.getColor(context, R.color.orange));
//                    txtSponsorName.setText("Enter Valid Referral invite code.");
                }
            }
        });

        if (getIntent().getBundleExtra(PAYLOAD_BUNDLE) != null) {
            String mobile = getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("mobile");
            etMobile.setText(mobile);
        }

        etReferral.setText(referId);
    }

    @OnClick({R.id.tv_terms, R.id.btn_register, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_terms:
                if (NetworkUtils.getConnectivityStatus(context) != 0)
                    getTermsCondition();
                else
                    showMessage(getResources().getString(R.string.alert_internet));
                break;
            case R.id.btn_register:
                if (validate())
                    sendOTP("register");
                break;
            case R.id.tv_login:
                goToActivityWithFinish(context, Login.class, null);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        goToActivityWithFinish(context, Login.class, null);
    }

    void otpDialog() {
        otpDialog = new Dialog(context);
        otpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        otpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        otpDialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        otpDialog.setContentView(R.layout.dialog_otp_verify);

        Button btn_continue = otpDialog.findViewById(R.id.btn_continue);
        Button btn_cancel = otpDialog.findViewById(R.id.btn_cancel);
        TextView tv_resend = otpDialog.findViewById(R.id.tv_resend);
        TextView tv_verify_text = otpDialog.findViewById(R.id.tv_verify_text);
        tv_verify_text.setText("Please Enter the One Time Password Sent to +91 " + etMobile.getText().toString().trim());

        etOtp1 = otpDialog.findViewById(R.id.et_otp1);
        etOtp2 = otpDialog.findViewById(R.id.et_otp2);
        etOtp3 = otpDialog.findViewById(R.id.et_otp3);
        etOtp4 = otpDialog.findViewById(R.id.et_otp4);

        etOtp1.addTextChangedListener(this);
        etOtp2.addTextChangedListener(this);
        etOtp3.addTextChangedListener(this);
        etOtp4.addTextChangedListener(this);

        btn_continue.setOnClickListener(v -> {
            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                if (enteredOTP.length() == 4) {
                    getRegistered();
                } else showMessage("Enter 4 digit OTP!");
            } else
                showMessage(getResources().getString(R.string.alert_internet));
        });

        btn_cancel.setOnClickListener(v -> otpDialog.cancel());

        tv_resend.setOnClickListener(v -> sendOTP("resend"));

        otpDialog.setCancelable(false);
        otpDialog.show();
    }

    private void getStateCity() {
        showLoading();
        hideKeyboard();
//        {"PinCode":"226004"}
        JsonObject object = new JsonObject();
        object.addProperty("PinCode", etPincode.getText().toString());
        LoggerUtil.logItem(object);
        Call<JsonObject> call = apiServices.GetStateCity(bodyParam(object));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.code());
                ResponseCityState responseCityState;
                try {
                    if (response.isSuccessful()) {
                        String paramResponse = Cons.decryptMsg(response.body().get("Body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        Gson gson = new GsonBuilder().create();
                        responseCityState = gson.fromJson(paramResponse, ResponseCityState.class);
                        LoggerUtil.logItem(responseCityState);
                        if (responseCityState.getStatus().equalsIgnoreCase("0")) {
                            tvCity.setText(responseCityState.getCity());
                            tvState.setText(responseCityState.getState());
                        } else showMessage(responseCityState.getErrorMessage());
                    } else {
                        showMessage("Something went wrong!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                hideLoading();
            }
        });
    }

    private void getReferralName() {
        progressBar.setVisibility(View.VISIBLE);
        hideKeyboard();
        JsonObject object = new JsonObject();
        object.addProperty("SponsorId", etReferral.getText().toString());
        LoggerUtil.logItem(object);
        Call<JsonObject> call = apiServices.GetSponsorName(bodyParam(object));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressBar.setVisibility(View.GONE);
                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.code());
                ResponseSponsor responseSponsor;
                try {
                    if (response.isSuccessful()) {
                        String paramResponse = Cons.decryptMsg(response.body().get("Body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        Gson gson = new GsonBuilder().create();
                        responseSponsor = gson.fromJson(paramResponse, ResponseSponsor.class);
                        LoggerUtil.logItem(responseSponsor);
                        if (responseSponsor.getStatus().equalsIgnoreCase("0")) {
                            validReferral = true;
                            txtSponsorName.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                            txtSponsorName.setText(responseSponsor.getSponsorName());
                        } else {
                            validReferral = false;
                            txtSponsorName.setTextColor(ContextCompat.getColor(context, R.color.orange));
                            txtSponsorName.setText("Enter Valid Referral invite code.");
                        }
                    } else {
                        validReferral = false;
                        txtSponsorName.setTextColor(ContextCompat.getColor(context, R.color.orange));
                        txtSponsorName.setText("Enter Valid Referral invite code.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void getValidMobile() {
        progressBarMobile.setVisibility(View.VISIBLE);
        mobileStatus.setVisibility(View.GONE);
        hideKeyboard();
        JsonObject object = new JsonObject();
        object.addProperty("MobileNo", etMobile.getText().toString());
        LoggerUtil.logItem(object);
        Call<JsonObject> call = apiServices.CheckMobileNo(bodyParam(object));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressBarMobile.setVisibility(View.GONE);
                mobileStatus.setVisibility(View.VISIBLE);
                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.code());
                ResponseValidMobile responseSponsor;
                try {
                    if (response.isSuccessful()) {
                        String paramResponse = Cons.decryptMsg(response.body().get("Body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        Gson gson = new GsonBuilder().create();
                        responseSponsor = gson.fromJson(paramResponse, ResponseValidMobile.class);
                        LoggerUtil.logItem(responseSponsor);
                        if (responseSponsor.getStatus().equalsIgnoreCase("0")) {
                            validMobile = false;
                            mobileStatus.setImageResource(R.drawable.ic_cross);
                        } else {
                            validMobile = true;
                            mobileStatus.setImageResource(R.drawable.ic_tick);
                        }
                    } else {
                        validMobile = false;
                        mobileStatus.setImageResource(R.drawable.ic_cross);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressBarMobile.setVisibility(View.GONE);
            }
        });
    }

    private void getTermsCondition() {
        showLoading();
        Call<JsonObject> call = apiServices.GetTermsCondition();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.code());
                ResponseTermsConditions conditions;
                try {
                    if (response.isSuccessful()) {
                        String paramResponse = Cons.decryptMsg(response.body().get("Body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        Gson gson = new GsonBuilder().create();
                        conditions = gson.fromJson(paramResponse, ResponseTermsConditions.class);
                        LoggerUtil.logItem(conditions);
                        if (conditions.getStatus().equalsIgnoreCase("0")) {
                            String terms = "";
                            for (int i = 0; i < conditions.getLstTermsList().get(0).getTermsListDetails().size(); i++) {
                                terms = terms + "\n" + conditions.getLstTermsList().get(0).getTermsListDetails().get(i).getTermscondition();
                            }
                            createInfoDialog(context, "Terms & Conditions", terms);
                        } else
                            showMessage("Something went wrong!");
                    } else {
                        showMessage("Something went wrong!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                hideLoading();
            }
        });
    }

    private void sendOTP(String from) {
//        {"MobileNo":"8052949381"}
        hideKeyboard();
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("MobileNo", etMobile.getText().toString().trim());
        LoggerUtil.logItem(object);

        Call<JsonObject> call = apiServices.SendOTP(bodyParam(object));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.code());
                ResponseSendOTP sendOTP;
                try {
                    if (response.isSuccessful()) {
                        String paramResponse = Cons.decryptMsg(response.body().get("Body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        Gson gson = new GsonBuilder().create();
                        sendOTP = gson.fromJson(paramResponse, ResponseSendOTP.class);
                        LoggerUtil.logItem(sendOTP);
                        if (sendOTP.getStatus().equalsIgnoreCase("0")) {
                            if (from.equalsIgnoreCase("register"))
                                otpDialog();
//                            showMessage(sendOTP.getOTP());
                        } else
                            showMessage("Something went wrong!");
                    } else {
                        showMessage("Something went wrong!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                hideLoading();
            }
        });
    }

    private void getRegistered() {
//        {"SponsorId":"dwpe","Email":"","MobileNo":"0123456888","FirstName":"Test","LastName":"","PinCode":"","Password":"123456","OTP":"1234"}
        showLoading();
        hideKeyboard();
        JsonObject object = new JsonObject();
        object.addProperty("SponsorId", etReferral.getText().toString());
        object.addProperty("Email", etEmail.getText().toString());
        object.addProperty("MobileNo", etMobile.getText().toString());
        object.addProperty("FirstName", etFirstName.getText().toString());
        object.addProperty("LastName", etLastName.getText().toString());
        object.addProperty("PinCode", etPincode.getText().toString());
        object.addProperty("Password", etPassword.getText().toString());
        object.addProperty("OTP", enteredOTP);
        object.addProperty("FireBaseId", String.valueOf(FirebaseMessaging.getInstance().getToken()));
        object.addProperty("DeviceId", Utils.getAndroidDeviceId(context));

        LoggerUtil.logItem(object);
        Call<JsonObject> call = apiServices.getRegistration(bodyParam(object));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.code());
                ResponseRegistration registration;
                try {
                    if (response.isSuccessful()) {
                        String paramResponse = Cons.decryptMsg(response.body().get("Body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        Gson gson = new GsonBuilder().create();
                        registration = gson.fromJson(paramResponse, ResponseRegistration.class);
                        LoggerUtil.logItem(registration);
                        if (registration.getStatus().equalsIgnoreCase("0")) {
                            otpDialog.dismiss();
                            PreferencesManager.getInstance(context).setUserid(registration.getPkUserId());
                            PreferencesManager.getInstance(context).setFirstName(registration.getFirstName());
                            PreferencesManager.getInstance(context).setLastName(registration.getLastName());
                            PreferencesManager.getInstance(context).setFullname(registration.getFirstName() + " " +
                                    registration.getLastName());
                            PreferencesManager.getInstance(context).setEmail(registration.getEmail());
                            PreferencesManager.getInstance(context).setIsPinCreated(registration.getIsPinCreated());
                            PreferencesManager.getInstance(context).setMobileno(registration.getMobileNo());
                            PreferencesManager.getInstance(context).setImage(registration.getProfilePic());
                            PreferencesManager.getInstance(context).setLoginId(registration.getLoginId());
                            PreferencesManager.getInstance(context).setAssociateStatus(registration.getAssociateStatus());
                            goToActivityWithFinish(context, ShoppingMainActivity.class, null);
                            showMessage("Welcome, " + registration.getFirstName());
                        } else
                            showMessage(registration.getErrorMessage());
                    } else {
                        showMessage("Something went wrong!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                hideLoading();
            }
        });
    }

    private boolean validate() {

        if (!validReferral) {
            txtSponsorName.setTextColor(ContextCompat.getColor(context, R.color.orange));
            txtSponsorName.setText("Enter Valid Referral invite code.");
            return false;
        } else if (etFirstName.getText().toString().trim().length() < 2) {
            showMessage("Enter first name!");
            return false;
        } else if (etMobile.getText().toString().trim().length() != 10) {
            showMessage("Enter mobile number!");
            return false;
        } else if (!validMobile) {
            showMessage("Mobile number already exist!");
            return false;
        } else if (!Utils.isEmailValid(etEmail.getText().toString().trim())) {
            showMessage("Enter valid email Id");
            return false;
        } else if (etPassword.getText().toString().trim().length() < 6) {
            showMessage("Enter minimum 6 digit password!");
            return false;
        } else if (tvCity.getText().toString().trim().length() < 2) {
            showMessage("Enter PIN code!");
            return false;
        } else if (!cbTerms.isChecked()) {
            showMessage("Accept terms & conditions!");
            return false;
        }
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() == 1) {
            if (etOtp1.length() == 1) {
                etOtp2.requestFocus();
            }
            if (etOtp2.length() == 1) {
                etOtp3.requestFocus();
            }
            if (etOtp3.length() == 1) {
                etOtp4.requestFocus();
            }
            if (etOtp4.length() == 1) {
                enteredOTP = etOtp1.getText().toString() + etOtp2.getText().toString() + etOtp3.getText().toString() + etOtp4.getText().toString();
            }
        } else if (editable.length() == 0) {
            if (etOtp4.length() == 0) {
                etOtp3.requestFocus();
            }
            if (etOtp3.length() == 0) {
                etOtp2.requestFocus();
            }
            if (etOtp2.length() == 0) {
                etOtp1.requestFocus();
            }
        }
    }
}