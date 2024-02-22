package afluex.mlm.demo;

import static afluex.mlm.demo.app.AppConfig.PAYLOAD_BUNDLE;



import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;


import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Random;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import afluex.mlm.demo.app.PreferencesManager;
import afluex.mlm.demo.common.DialogUtil;
import afluex.mlm.demo.common.LoggerUtil;
import afluex.mlm.demo.common.Utils;
import afluex.mlm.demo.retrofit.ApiServices;
import afluex.mlm.demo.retrofit.MvpView;
import afluex.mlm.demo.retrofit.ServiceGenerator;


public abstract class BaseFragment extends Fragment implements MvpView {
    // Toolbar toolbar;
    protected static final int ASK_SEND_SMS_PERMISSION_REQUEST_CODE = 14;
    private static final String TAG = "BaseFragment";
    private ProgressDialog mProgressDialog;
    protected final Gson gson = new Gson();
    //protected Entity mEntity;
    protected String latitude = "0", longitude = "0", lastActiveTime;
    public Activity context;
    public ApiServices apiServices, createServiceUtilityV2, apiServicesRecharge;

    public SecretKey cross_intent, temp_compared;
    String keys1 = BuildConfig.ANDROID_ICON;
    String keys2 = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        apiServices = ServiceGenerator.createService(ApiServices.class);
        createServiceUtilityV2 = ServiceGenerator.createServiceUtilityV2(ApiServices.class);
        apiServicesRecharge = ServiceGenerator.createServiceRecharge(ApiServices.class);
        try {
            temp_compared = new SecretKeySpec(keys1.getBytes(), "AES");
            keys2 = Cons.decryptMsg(BuildConfig.DWPAY_COMPARED, temp_compared);
            cross_intent = new SecretKeySpec(keys2.getBytes(), "AES");
        } catch (Exception e) {
        }
    }

    public JsonObject bodyParam(JsonObject param) {
        JsonObject body = new JsonObject();
        try {
            body.addProperty("body", Cons.encryptMsg(param.toString(), cross_intent));
            LoggerUtil.logItem(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }

    public void showAlert(String msg, int color, int icon) {
//        Alerter.create(context)
//                .setText(msg)
//                .setTextAppearance(R.style.alertTextColor)
//                .setBackgroundColorRes(color)
//                .setIcon(icon)
//                .show();
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public String getColoredSpanned(String text, String color) {
        return "<font color=" + color + ">" + text + "</font>";
    }

    @Override
    public void getAccpetRejectBooking(String id, String bookid, String action, String remark, String reason) {

    }

    @Override
    public void getvehicledata(String vehiclename, String vehicleid) {

    }

    @Override
    public void getData(String epinNo, String from) {
    }

    @Override
    public void openActivityOnTokenExpire() {

    }

    @Override
    public void onError(int resId) {

    }

    @Override
    public void onError(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int resId) {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void hideKeyboard() {
        Utils.hideSoftKeyboard(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PreferencesManager.initializeInstance(getContext());
    }

    public String generatePin() {
        Random random = new Random();
        @SuppressLint("DefaultLocale") String randomPIN = String.format("%04d", random.nextInt(10000));
        return randomPIN;
    }

    public void showLoading() {
        //hideLoading();
        mProgressDialog = DialogUtil.showLoadingDialog(getActivity(), TAG);
        mProgressDialog.setCancelable(false);
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public void createInfoDialog(Context context, String title,
                                 String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setNegativeButton(
                "OK",
                (dialog, id) -> dialog.cancel());

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void goToActivity(Class<?> classActivity, Bundle bundle) {
        Utils.hideSoftKeyboard(getActivity());
        Intent intent = new Intent(getContext(), classActivity);
        if (bundle != null)
            intent.putExtra(PAYLOAD_BUNDLE, bundle);
        getActivity().startActivity(intent);
    }

    public void goToActivityWithFinish(Class<?> classActivity, Bundle bundle) {
        Intent intent = new Intent(getContext(), classActivity);
        if (bundle != null)
            intent.putExtra(PAYLOAD_BUNDLE, bundle);
        Utils.hideSoftKeyboard(getActivity());
        getActivity().startActivity(intent);
        getActivity().finish();
    }

    public void checkSMSPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
            requestSMSPermission();
    }

    public void requestSMSPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.SEND_SMS)
                || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_SMS)
                || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.RECEIVE_SMS)) {
            Utils.createSimpleDialog1(getActivity(), getString(R.string.alert_text), getString(R.string.permission_camera_rationale11), getString(R.string.reqst_permission), () -> ActivityCompat.requestPermissions(getActivity(), new String[]{
                            Manifest.permission.SEND_SMS,
                            Manifest.permission.READ_SMS,
                            Manifest.permission.RECEIVE_SMS},
                    ASK_SEND_SMS_PERMISSION_REQUEST_CODE));
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                            Manifest.permission.SEND_SMS,
                            Manifest.permission.READ_SMS,
                            Manifest.permission.RECEIVE_SMS
                    },
                    ASK_SEND_SMS_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void shareDataText(String shareBody, String shareSubject) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
    }

    @Override
    public void getClick(int position, String value, String colorID) {

    }
}
