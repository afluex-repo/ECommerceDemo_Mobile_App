package afluex.mlm.demo;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import afluex.mlm.demo.common.LoggerUtil;
import afluex.mlm.demo.common.NetworkUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends BaseActivity implements TextWatcher {

    @BindView(R.id.et_mobile)
    AppCompatEditText etMobile;
    @BindView(R.id.cv_frgt_pswd)
    CardView cvFrgtPswd;
    @BindView(R.id.et_password)
    AppCompatEditText etPassword;
    @BindView(R.id.et_cnfm_password)
    AppCompatEditText etCnfmPassword;
    @BindView(R.id.cv_new_password)
    CardView cvNewPassword;
    @BindView(R.id.progressBarMobile)
    ProgressBar progressBarMobile;
    @BindView(R.id.mobileStatus)
    ImageView mobileStatus;

    Dialog otpDialog;
    String enteredOTP = "";
    EditText etOtp1, etOtp2, etOtp3, etOtp4;
    boolean validMobile = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        ButterKnife.bind(this);

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
                    validMobile = false;
                    mobileStatus.setImageResource(R.drawable.ic_cross);
//                    txtSponsorName.setTextColor(ContextCompat.getColor(context, R.color.orange));
//                    txtSponsorName.setText("Enter Valid Referral invite code.");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        goToActivityWithFinish(context, Login.class, null);
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
                    cvFrgtPswd.setVisibility(View.GONE);
                    cvNewPassword.setVisibility(View.VISIBLE);
                    otpDialog.dismiss();
                    showMessage("Enter new password!");
                } else showMessage("Enter 4 digit OTP!");
            } else
                showMessage(getResources().getString(R.string.alert_internet));
        });

        btn_cancel.setOnClickListener(v -> otpDialog.cancel());

        tv_resend.setOnClickListener(v -> sendOTP("resend"));

        otpDialog.setCancelable(false);
        otpDialog.show();
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

    @OnClick({R.id.btn_submit, R.id.btn_submit_new})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    if (validMobile) {
                        sendOTP("register");
                    } else showMessage("Enter registered mobile number!");
                } else showMessage(getString(R.string.alert_internet));
                break;
            case R.id.btn_submit_new:
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    if (validate()) {
                        forgotPassword();
                    } else showMessage("Enter registered mobile number!");
                } else showMessage(getString(R.string.alert_internet));
                break;
        }
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
                            validMobile = true;
                            mobileStatus.setImageResource(R.drawable.ic_tick);
                        } else {
                            validMobile = false;
                            mobileStatus.setImageResource(R.drawable.ic_cross);
                        }
                    } else {
                        validMobile = true;
                        mobileStatus.setImageResource(R.drawable.ic_tick);
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

    private void forgotPassword() {
//        {"MobileNo":"8052949381","Password":"123456","OTP":"1234"}
        hideKeyboard();
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("MobileNo", etMobile.getText().toString().trim());
        object.addProperty("Password", etPassword.getText().toString().trim());
        object.addProperty("OTP", enteredOTP);

        LoggerUtil.logItem(object);
        Call<JsonObject> call = apiServices.ForgetPass(bodyParam(object));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.code());
                ResponseFrgtPassword sendOTP;
                try {
                    if (response.isSuccessful()) {
                        String paramResponse = Cons.decryptMsg(response.body().get("Body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        Gson gson = new GsonBuilder().create();
                        sendOTP = gson.fromJson(paramResponse, ResponseFrgtPassword.class);
                        LoggerUtil.logItem(sendOTP);
                        if (sendOTP.getStatus().equalsIgnoreCase("0")) {
                            goToActivityWithFinish(context, Login.class, null);
                            showMessage("Reset Successfully");
                        } else
                            showMessage(sendOTP.getErrorMessage());
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
        if (etPassword.getText().toString().trim().length() < 6) {
            showMessage("Enter New Password!");
            return false;
        } else if (!etCnfmPassword.getText().toString().trim().equals(etPassword.getText().toString().trim())) {
            showMessage("New Password Not Matched!");
            return false;
        }
        return true;
    }
}