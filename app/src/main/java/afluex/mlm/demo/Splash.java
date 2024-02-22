package afluex.mlm.demo;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;
import com.google.firebase.FirebaseApp;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Splash extends BaseActivity {

    @BindView(R.id.img)
    ImageView img;
    public static String referId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Animation aniSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.delay_zoom_in);
        img.startAnimation(aniSlide);

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, pendingDynamicLinkData -> {
                    // Get deep link from result (may be null if no link is found)
                    Uri deepLink = null;
                    if (pendingDynamicLinkData != null) {
                        deepLink = pendingDynamicLinkData.getLink();
                        LoggerUtil.logItem(deepLink.toString());
                        String link = deepLink.toString();
                        if (link.contains("referId")) {
                            link = link.substring(link.lastIndexOf("=") + 1);
                            referId = link;
                            LoggerUtil.logItem(referId);
                        }
//                        else if (link.contains("invitedby")) {
//                            link = link.substring(link.lastIndexOf("=") + 1);
//                            inviteCode = link;
//                            LoggerUtil.logItem(inviteCode);
//                        }
                    }
                });


        new Handler().postDelayed(() -> {
            if (!PreferencesManager.getInstance(context).getIsFirstTimeLaunch()) {
                PreferencesManager.getInstance(context).setIsFirstTimeLaunch(false);
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    checkUpdate();
                } else {
                    showMessage(getString(R.string.alert_internet));
                }
            } else {
                goToActivityWithFinish(context, ShoppingMainActivity.class, null);
            }
        }, 2500);
    }

    private void getAutoLogin() {
        showLoading();
//        {"FireBaseId":"fsdfds","DeviceId":"dsfsd"}
        JsonObject param = new JsonObject();
        param.addProperty("FireBaseId", String.valueOf(FirebaseMessaging.getInstance().getToken()));
        param.addProperty("DeviceId", Utils.getAndroidDeviceId(context));

        LoggerUtil.logItem(param);
        Call<JsonObject> call = apiServices.getAutoLogin(bodyParam(param));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.code());
                ResponseAutoLogin responseLogin;
                try {
                    if (response.isSuccessful()) {
                        String paramResponse = Cons.decryptMsg(response.body().get("Body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        Gson gson = new GsonBuilder().create();
                        responseLogin = gson.fromJson(paramResponse, ResponseAutoLogin.class);
                        LoggerUtil.logItem(responseLogin);
//                        TODO

                        if (responseLogin.getStatus().equalsIgnoreCase("0")) {
                            PreferencesManager.getInstance(context).setUserid(responseLogin.getPkUserId());
                            PreferencesManager.getInstance(context).setFirstName(responseLogin.getFirstName());
                            PreferencesManager.getInstance(context).setLastName(responseLogin.getLastName());
                            PreferencesManager.getInstance(context).setFullname(responseLogin.getFirstName() + " " +
                                    responseLogin.getLastName());
                            PreferencesManager.getInstance(context).setEmail(responseLogin.getEmail());
                            PreferencesManager.getInstance(context).setMobileno(responseLogin.getMobileNo());
                            PreferencesManager.getInstance(context).setImage(responseLogin.getProfilePic());
                            PreferencesManager.getInstance(context).setLoginId(responseLogin.getLoginId());
                            PreferencesManager.getInstance(context).setAssociateStatus(responseLogin.getAssociateStatus());
                            goToActivityWithFinish(context, ShoppingMainActivity.class, null);
                            showMessage("Welcome, " + responseLogin.getFirstName());
                        } else {
                            goToActivityWithFinish(context, Login.class, null);
                        }
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
                LoggerUtil.logItem(t);
                showMessage("Something went wrong!");
            }
        });
    }

    private AppUpdater updater;

    private void checkUpdate() {
        AppUpdaterUtils appUpdaterUtils = new AppUpdaterUtils(this)
                .setUpdateFrom(UpdateFrom.GOOGLE_PLAY)
                .withListener(new AppUpdaterUtils.UpdateListener() {
                    @Override
                    public void onSuccess(Update update, Boolean isUpdateAvailable) {
                        Log.d("Latest Version", update.getLatestVersion());
                        Log.d("Latest Version Code", "=" + update.getLatestVersionCode());
                        Log.d("Release notes", update.getReleaseNotes());
                        Log.d("URL", "=" + update.getUrlToDownload());
                        Log.d("Is update available?", Boolean.toString(isUpdateAvailable));
                        isUpdateAvailable=false;
                        if (isUpdateAvailable) {
                            if (updater == null) {
                                updater = new AppUpdater(Splash.this).setDisplay(Display.DIALOG);
                                updater.setContentOnUpdateAvailable("Update " + update.getLatestVersion() + " is available to download. Download the latest version of DW Pe to get latest" +
                                        "features, improvements and bug fixes.");
                                updater.setButtonDoNotShowAgain("");
                                updater.setButtonDismiss("");
                                updater.setCancelable(false);
                                updater.start();
                            } else {
                                updater.start();
                            }
                        } else {
//                            TODO
                            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                                getAutoLogin();
                            } else {
                                showMessage(getString(R.string.alert_internet));
                            }
                        }
                    }

                    @Override
                    public void onFailed(AppUpdaterError error) {
                        Log.d("AppUpdater Error", "Something went wrong");
                    }
                });
        appUpdaterUtils.start();
    }
}
