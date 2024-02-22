package afluex.mlm.demo.app;

import android.app.Application;


import afluex.mlm.demo.R;
import afluex.mlm.demo.common.ConnectivityReceiver;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class AppController extends Application {
    private static AppController mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/roboto_regular.ttf")
                .disableCustomViewInflation()
                .build());
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
