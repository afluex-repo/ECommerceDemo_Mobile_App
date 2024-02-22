package afluex.mlm.demo;

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
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;


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

public class Login extends BaseActivity implements TextWatcher {

    @BindView(R.id.et_userid)
    AppCompatEditText etUserid;
    @BindView(R.id.et_password)
    AppCompatEditText etPassword;
    @BindView(R.id.tv_signup)
    TextView tvSignup;

    Dialog otpDialog;
    String enteredOTP = "";
    EditText etOtp1, etOtp2, etOtp3, etOtp4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        String account = getColoredSpanned(getResources().getString(R.string.sign_in_text), "#000313");
        String signup = getColoredSpanned("<u>" + getResources().getString(R.string.signup) + "</u>", "#FF5722");
        tvSignup.setText(Html.fromHtml(account + " " + signup));
    }

    @OnClick({R.id.tv_forgot_pswd, R.id.btn_login, R.id.tv_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forgot_pswd:
                goToActivityWithFinish(context, ForgotPassword.class, null);
                break;
            case R.id.btn_login:
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    if (validate())
                        getLogin("", "");
                } else
                    showMessage(getResources().getString(R.string.alert_internet));
                break;
            case R.id.tv_signup:
                goToActivityWithFinish(context, Registration.class, null);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getLogin(String otp, String from) {
        hideKeyboard();
        showLoading();
//        {"MobileNo":"8052949381","Password":"123456","FireBaseId":"fsdfds","DeviceId":"dsfsd"}
        JsonObject param = new JsonObject();
        param.addProperty("MobileNo", etUserid.getText().toString().trim());
        param.addProperty("Password", etPassword.getText().toString().trim());
        param.addProperty("FireBaseId", String.valueOf(FirebaseMessaging.getInstance().getToken()));
        param.addProperty("DeviceId", Utils.getAndroidDeviceId(context));
        param.addProperty("OTP", otp);

        LoggerUtil.logItem(param);
        Call<JsonObject> call = apiServices.getLoginEnc(bodyParam(param));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.code());
                ResponseLogin responseLogin;
                if (otpDialog != null) {
                    otpDialog.dismiss();
                }
                try {
                    if (response.isSuccessful()) {
                        String paramResponse = Cons.decryptMsg(response.body().get("Body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        Gson gson = new GsonBuilder().create();
                        responseLogin = gson.fromJson(paramResponse, ResponseLogin.class);
                        LoggerUtil.logItem(responseLogin);
//                        TODO

                        if (responseLogin.getStatus().equalsIgnoreCase("0")) {
                            PreferencesManager.getInstance(context).setUserid(responseLogin.getPkUserId());
                            PreferencesManager.getInstance(context).setFirstName(responseLogin.getFirstName());
                            PreferencesManager.getInstance(context).setLastName(responseLogin.getLastName());
                            PreferencesManager.getInstance(context).setFullname(responseLogin.getFirstName() + " " +
                                    responseLogin.getLastName());
                            PreferencesManager.getInstance(context).setEmail(responseLogin.getEmail());
                            PreferencesManager.getInstance(context).setIsPinCreated(responseLogin.getIsPinCreated());
                            PreferencesManager.getInstance(context).setMobileno(responseLogin.getMobileNo());
                            PreferencesManager.getInstance(context).setImage(responseLogin.getProfilePic());
                            PreferencesManager.getInstance(context).setLoginId(responseLogin.getLoginId());
                            PreferencesManager.getInstance(context).setAssociateStatus(responseLogin.getAssociateStatus());
                            goToActivityWithFinish(context, ShoppingMainActivity.class, null);
                            showMessage("Welcome, " + responseLogin.getFirstName());
                        } else if (responseLogin.getErrorMessage().equalsIgnoreCase("Already Login")) {
                            if (!from.equalsIgnoreCase("resend")) {
                                otpDialog();
                            }
                        } else showMessage(responseLogin.getErrorMessage());
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
                showMessage("Something went wrong!");
            }
        });
    }

    private boolean validate() {
        if (etUserid.getText().toString().trim().length() != 10) {
            showMessage("Enter registered mobile number!");
            return false;
        } else if (etPassword.getText().toString().trim().length() < 6) {
            showMessage("Enter password!");
            return false;
        }
        return true;
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
        tv_verify_text.setText("Please Enter the One Time Password Sent to +91 " + etUserid.getText().toString().trim());

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
                    getLogin(enteredOTP, "");
                } else showMessage("Enter 4 digit OTP!");
            } else
                showMessage(getResources().getString(R.string.alert_internet));
        });

        btn_cancel.setOnClickListener(v -> otpDialog.cancel());

        tv_resend.setOnClickListener(v -> getLogin("", "resend"));

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
}
