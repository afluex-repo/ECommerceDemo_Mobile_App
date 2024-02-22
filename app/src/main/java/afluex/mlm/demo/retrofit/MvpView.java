package afluex.mlm.demo.retrofit;

import androidx.annotation.StringRes;


public interface MvpView {
    void showLoading();

    void hideLoading();

    void openActivityOnTokenExpire();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();

    void getData(String str1, String str2);

    void getClick(int position, String value, String colorID);

    void getvehicledata(String vehiclename, String vehicleid);

    void getAccpetRejectBooking(String id, String bookid, String action, String remark, String reason);
}
