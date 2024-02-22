package afluex.mlm.demo.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;

import afluex.mlm.demo.R;


public class DialogUtil {
    private static final String TAG = "DialogUtil";

    private static ProgressDialog progressDialog;

    private DialogUtil() {
        // This utility class is not publicly instantiable
    }

    public static ProgressDialog showLoadingDialog(Activity activity, String callingPlace) {
        Log.d(TAG, "showLoadingDialog: " + callingPlace);
        progressDialog = new ProgressDialog(activity, ProgressDialog.THEME_HOLO_DARK);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(true);
        return progressDialog;
    }

    public static void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }
}