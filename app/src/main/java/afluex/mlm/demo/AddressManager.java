package afluex.mlm.demo;



import static afluex.mlm.demo.app.AppConfig.PAYLOAD_BUNDLE;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.Objects;

import afluex.mlm.demo.app.PreferencesManager;
import afluex.mlm.demo.common.LoggerUtil;
import afluex.mlm.demo.common.NetworkUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressManager extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_categories)
    RecyclerView rvCategories;
    @BindView(R.id.tv_final_amount)
    TextView tvFinalAmount;
    @BindView(R.id.rv_amount)
    RelativeLayout rvAmount;

    Dialog address_dialog, payment_dialog;
    EditText et_city, et_state;
    String addressType = "Home", selectedPaymentMode = "Cash";
    public static String selectedAddressId = "";
    String walletBalance = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_address_activity);
        ButterKnife.bind(this);

        tvTitle.setText("Address");
        rvCategories.setLayoutManager(new LinearLayoutManager(context));

        if (NetworkUtils.getConnectivityStatus(context) != 0)
            getAddress();
        else showMessage(getString(R.string.alert_internet));

        if (getIntent().getBundleExtra(PAYLOAD_BUNDLE) != null) {
            if (getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("from").equalsIgnoreCase("cart")) {
                rvAmount.setVisibility(View.VISIBLE);
                tvFinalAmount.setText("₹ " + String.format(getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("amount")));
            } else {
                rvAmount.setVisibility(View.GONE);
            }
        }
    }

    @OnClick({R.id.iv_back, R.id.btn_add_address, R.id.pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_add_address:
                addAddressDialog();
                break;
            case R.id.pay:
                getPassbook();
                break;
        }
    }

    private void addAddressDialog() {
        address_dialog = new Dialog(context);
        address_dialog.setCanceledOnTouchOutside(false);
        address_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        address_dialog.setContentView(R.layout.dialog_address);
        Objects.requireNonNull(address_dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        Button btn_cancel = address_dialog.findViewById(R.id.btn_cancel);
        Button btn_submit = address_dialog.findViewById(R.id.btn_submit);

        et_city = address_dialog.findViewById(R.id.et_city);
        et_state = address_dialog.findViewById(R.id.et_state);
        EditText et_landmark = address_dialog.findViewById(R.id.et_landmark);
        EditText et_area = address_dialog.findViewById(R.id.et_area);
        EditText et_name = address_dialog.findViewById(R.id.et_name);
        EditText et_mobile = address_dialog.findViewById(R.id.et_mobile);
        EditText et_address = address_dialog.findViewById(R.id.et_address);
        EditText et_pincode = address_dialog.findViewById(R.id.et_pincode);
        RadioGroup radioGroup = address_dialog.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {
            if (i == R.id.rb_home)
                addressType = "Home";
            else addressType = "Work";
        });

        et_pincode.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0 && s.length() == 6)
                    getStateCity(et_pincode.getText().toString().trim());
            }
        });

        btn_submit.setOnClickListener(view -> {
            if (et_name.getText().toString().length() < 2 ||
                    et_mobile.getText().toString().length() != 10 ||
                    et_area.getText().toString().length() < 2 ||
                    et_landmark.getText().toString().length() < 2 ||
                    et_address.getText().toString().length() < 3 ||
                    et_pincode.getText().toString().length() != 6) {
                showMessage("Enter all fields");
            } else
                saveAddress(et_area.getText().toString(), et_address.getText().toString(), et_landmark.getText().toString(),
                        et_pincode.getText().toString(), addressType, et_name.getText().toString().trim(), et_mobile.getText().toString().trim());
        });

        btn_cancel.setOnClickListener(view -> address_dialog.dismiss());

        address_dialog.setCancelable(false);
        address_dialog.setCanceledOnTouchOutside(false);
        address_dialog.show();
    }

    private void getStateCity(String pincode) {
        showLoading();
        hideKeyboard();
//        {"PinCode":"226004"}
        JsonObject object = new JsonObject();
        object.addProperty("PinCode", pincode);
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
                            et_city.setText(responseCityState.getCity());
                            et_state.setText(responseCityState.getState());
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

    private void getAddress() {

        JsonObject items = new JsonObject();
        items.addProperty("CustomerId", PreferencesManager.getInstance(context).getUserid());

        LoggerUtil.logItem(items);
        showLoading();

        Call<JsonObject> call = apiServices.GetAddress(bodyParam(items));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.code());
                ResponseGetAddress responseSaveAddress;
                try {
                    if (response.isSuccessful()) {
                        String paramResponse = Cons.decryptMsg(response.body().get("Body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        Gson gson = new GsonBuilder().create();
                        responseSaveAddress = gson.fromJson(paramResponse, ResponseGetAddress.class);
                        LoggerUtil.logItem(responseSaveAddress);
                        if (responseSaveAddress.getStatus().equalsIgnoreCase("0")) {
                            rvAmount.setVisibility(View.VISIBLE);
                            for (int i = 0; i < responseSaveAddress.getLstaddress().get(0).getAddress().size(); i++) {
                                if (responseSaveAddress.getLstaddress().get(0).getAddress().get(i).getIsDefault().equalsIgnoreCase("True")) {
                                    selectedAddressId = responseSaveAddress.getLstaddress().get(0).getAddress().get(i).getPk_AddressId();
                                    break;
                                }
                            }
                            AddressAdapter addressAdapter = new AddressAdapter(context, responseSaveAddress.getLstaddress().get(0).getAddress());
                            rvCategories.setAdapter(addressAdapter);
                        } else {
                            rvAmount.setVisibility(View.GONE);
                            showMessage("No Address Found.");
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
            }
        });

    }

    private void saveAddress(String house, String locality, String lanmark, String pincode, String addressType, String name, String mobile) {
//{"HouseNo":"269/255","CustomerId":"1","Locality":"Birhana","LandMark":"Sohan Lal School","PinCode":"226004","AddressType":"Home","Name":"Piyush","ContatNo":"8052949381","IsDefault":"1"}
        JsonObject address = new JsonObject();
        address.addProperty("HouseNo", house);
        address.addProperty("CustomerId", PreferencesManager.getInstance(context).getUserid());
        address.addProperty("Locality", locality);

        address.addProperty("LandMark", lanmark);
        address.addProperty("PinCode", pincode);
        address.addProperty("AddressType", addressType);

        address.addProperty("Name", name);
        address.addProperty("ContatNo", mobile);
        address.addProperty("IsDefault", "1");

        LoggerUtil.logItem(address);
        showLoading();
        hideKeyboard();

        Call<JsonObject> call = apiServices.SaveAddress(bodyParam(address));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.code());
                ResponseSaveAddress responseSaveAddress;
                try {
                    if (response.isSuccessful()) {
                        String paramResponse = Cons.decryptMsg(response.body().get("Body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        Gson gson = new GsonBuilder().create();
                        responseSaveAddress = gson.fromJson(paramResponse, ResponseSaveAddress.class);
                        LoggerUtil.logItem(responseSaveAddress);
                        if (responseSaveAddress.getStatus().equalsIgnoreCase("0")) {
                            address_dialog.dismiss();
                            getAddress();
                        } else showMessage(responseSaveAddress.getErrorMessage());
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

    private void getPassbook() {
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("Pk_UserId", PreferencesManager.getInstance(context).getUserid());
        LoggerUtil.logItem(object);

        Call<JsonObject> call = apiServices.PassBook(bodyParam(object));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.code());
                ResponsePassbook mlmDashboard;
                try {
                    if (response.isSuccessful()) {
                        String paramResponse = Cons.decryptMsg(response.body().get("Body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        Gson gson = new GsonBuilder().create();
                        mlmDashboard = gson.fromJson(paramResponse, ResponsePassbook.class);
                        LoggerUtil.logItem(mlmDashboard);
                        if (mlmDashboard.getStatus().equalsIgnoreCase("0")) {
                            walletBalance = mlmDashboard.getDWPeBalance();
                            psymentDialog();
                        } else
                            showMessage(mlmDashboard.getErrorMessage());
                        Log.e("Sarfa",mlmDashboard.getErrorMessage());
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

    private void psymentDialog() {
        payment_dialog = new Dialog(context);
        payment_dialog.setCanceledOnTouchOutside(false);
        payment_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        payment_dialog.setContentView(R.layout.dialog_payment_mode);
        Objects.requireNonNull(payment_dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        RadioGroup radioGroup = payment_dialog.findViewById(R.id.radioGroup);
        Button btn_pay = payment_dialog.findViewById(R.id.btn_pay);
        TextView tv_pay_amt = payment_dialog.findViewById(R.id.tv_pay_amt);
        ImageView img_close = payment_dialog.findViewById(R.id.img_close);
        RadioButton rb_wallet = payment_dialog.findViewById(R.id.rb_wallet);
        rb_wallet.setText("Wallet (₹ " + walletBalance + ")");

        img_close.setOnClickListener(view -> payment_dialog.dismiss());

        tv_pay_amt.setText(String.format("Payable Amount ₹ %s", getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("amount")));

        radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {
            if (i == R.id.rb_cod)
                selectedPaymentMode = "Cash";
            else selectedPaymentMode = "Wallet";
        });

        btn_pay.setOnClickListener(view -> {
            if (selectedPaymentMode.equalsIgnoreCase("Wallet")) {
                if (Double.parseDouble(walletBalance) >= Double.parseDouble(getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("amount"))) {
                    placeOrder(selectedPaymentMode);
                } else showMessage("Insufficient Amount");
            } else {
                placeOrder(selectedPaymentMode);
            }
        });

        payment_dialog.setCancelable(false);
        payment_dialog.setCanceledOnTouchOutside(false);
        payment_dialog.show();
    }

    private void placeOrder(String payMode) {
        payment_dialog.dismiss();
//        {"CustomerId":"1","Fk_AddressId":"1","PaymentMode":"Cash","IsShoopingRedeem":"1"}
        payment_dialog.dismiss();
        JsonObject order = new JsonObject();
        order.addProperty("CustomerId", PreferencesManager.getInstance(context).getUserid());
        order.addProperty("Fk_AddressId", selectedAddressId);
        order.addProperty("PaymentMode", payMode);
        order.addProperty("IsShoopingRedeem", getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("spoints"));
        order.addProperty("IsBecomePrime", 0);

//        Log.e("Sarfa",PreferencesManager.getInstance(context).getUserid());
//        Log.e("Sarfa",selectedAddressId);
//        Log.e("Sarfa",payMode);


        showLoading();
        LoggerUtil.logItem(order);

        Call<JsonObject> call = apiServices.PlaceOrder(bodyParam(order));

        Log.e("Sarfa",""+bodyParam(order));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.code());
                ResponsePlaceOrder placeOrder;
                try {
                    if (response.isSuccessful()) {
                        String paramResponse = Cons.decryptMsg(response.body().get("Body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        Gson gson = new GsonBuilder().create();
                        placeOrder = gson.fromJson(paramResponse, ResponsePlaceOrder.class);
                        LoggerUtil.logItem(placeOrder);
                        if (placeOrder.getStatus().equalsIgnoreCase("0")) {
                            OrderPlacedDialog(placeOrder.getOrderNo());
                        } else{
                            showMessage(placeOrder.getErrorMessage());

                            Log.e("Sarfa",placeOrder.getErrorMessage());
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
            }
        });

    }

    private void OrderPlacedDialog(String orderId) {
        Dialog success_dialog = new Dialog(context);
        success_dialog.setCanceledOnTouchOutside(false);
        success_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        success_dialog.setContentView(R.layout.dialog_order_success);
        Objects.requireNonNull(success_dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        TextView tv_order_id = success_dialog.findViewById(R.id.tv_order_id);
        tv_order_id.setText(String.format("Order Id  = %s", orderId));

        Button btn_my_orders = success_dialog.findViewById(R.id.btn_my_orders);
        btn_my_orders.setOnClickListener(view -> {
            success_dialog.dismiss();
            goToActivityWithFinish(context, MyShoppingOrders.class, null);
        });

        success_dialog.setCancelable(false);
        success_dialog.setCanceledOnTouchOutside(false);
        success_dialog.show();
    }
}

