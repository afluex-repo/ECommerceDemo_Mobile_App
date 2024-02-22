package afluex.mlm.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import afluex.mlm.demo.app.PreferencesManager;
import afluex.mlm.demo.common.LoggerUtil;
import afluex.mlm.demo.common.NetworkUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends BaseActivity implements CartIncDecListener {

    @BindView(R.id.rv_categories)
    RecyclerView rvCategories;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_delivery_charges)
    TextView tvDeliveryCharges;
    @BindView(R.id.tv_payable_amt)
    TextView tvPayableAmt;
    @BindView(R.id.tv_final_amount)
    TextView tv_final_amount;
    Double payAmount = 0.00, deliveryCharges = 0.00, productAmount = 0.00;
    //    CartAdapter adapter;
    @BindView(R.id.cv_empty)
    ConstraintLayout cvEmpty;
    @BindView(R.id.cv_found)
    ConstraintLayout cvFound;

    //    Coupon Widgets
    EditText coupon_code;
    TextView tv_apply_coupon;

    boolean isShoppingPointsApplied = false;
    float couponAmount = 0;

    //    Coupon dialog
    RecyclerView recycler_couponlist;
    @BindView(R.id.tv_product_count)
    TextView tv_product_count;
    @BindView(R.id.product_rupees)
    TextView product_rupees;
    @BindView(R.id.tv_coupon)
    TextView tvCoupon;
    @BindView(R.id.textView9)
    TextView textView9;
    @BindView(R.id.tv_shopping_amt)
    TextView tv_shopping_amt;
    @BindView(R.id.s_use_wallet)
    Switch sUseWallet;
    @BindView(R.id.btn_continue_shopping)
    Button btn_continue_shopping;
    @BindView(R.id.tv_wallet_balance)
    TextView tvWalletBalance;
    @BindView(R.id.tv_applied_amount)
    TextView tvAppliedAmount;
    private BottomSheetDialog couponDialog;
    private CartAdapter cartAdapter;
    double totalShoppingPoints = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_cart);
        ButterKnife.bind(this);

        tv_apply_coupon = findViewById(R.id.tv_apply_coupon);
        coupon_code = findViewById(R.id.coupon_code);

        tvTitle.setText("Cart");
        rvCategories.setLayoutManager(new LinearLayoutManager(context));
        getCartItems();
        if(PreferencesManager.getInstance(context).getUserid().equals("")||PreferencesManager.getInstance(context).getUserid()==null){
          btn_continue_shopping.setText("Go to Login");
          textView9.setText("You are not logged in");
        }else{
            btn_continue_shopping.setText("Continue Shopping");
            textView9.setText("Your Shopping Cart Is Empty");
        }

        sUseWallet.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (NetworkUtils.getConnectivityStatus(context) != 0)
                    getPassbook();
                else showMessage(getString(R.string.alert_internet));
            } else {
                tvCoupon.setVisibility(View.GONE);
                tv_shopping_amt.setVisibility(View.GONE);
                tvAppliedAmount.setVisibility(View.GONE);
                calculateShoppingPoints(false);
                sUseWallet.setChecked(false);
            }
        });
    }

    @OnClick({R.id.pay, R.id.iv_back, R.id.btn_continue_shopping, R.id.show_coupons})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pay:
                Bundle bundle = new Bundle();
                bundle.putString("amount", String.valueOf(payAmount));
                bundle.putString("from", "cart");
                if (sUseWallet.isChecked())
                    bundle.putString("spoints", "1");
                else
                    bundle.putString("spoints", "1");
                goToActivity(context, AddressManager.class, bundle);
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_continue_shopping:

                if(PreferencesManager.getInstance(context).getUserid().equals("")||PreferencesManager.getInstance(context).getUserid()==null){
                    goToActivityWithFinish(context, Login.class, null);
                }else{
                    goToActivityWithFinish(context, ShoppingMainActivity.class, null);
                }

                break;
            case R.id.show_coupons:
//                GetCoupons();
                break;
        }
    }

    private void getCartItems() {
        JsonObject items = new JsonObject();
        items.addProperty("CustomerId", PreferencesManager.getInstance(context).getUserid());

        showLoading();
        Call<JsonObject> call = apiServices.ShowCart(bodyParam(items));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideLoading();
//                LoggerUtil.logItem(response.body());
                try {
                    if (response.isSuccessful()) {
                        sUseWallet.setChecked(false);
                        tvCoupon.setVisibility(View.GONE);
                        tv_shopping_amt.setVisibility(View.GONE);
                        tvAppliedAmount.setVisibility(View.GONE);
                        String paramResponse = Cons.decryptMsg(response.body().get("Body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        Gson gson = new GsonBuilder().create();
                        ResponseCartItems responseCartItems = gson.fromJson(paramResponse, ResponseCartItems.class);
                        LoggerUtil.logItem(responseCartItems);
                        if (responseCartItems.getStatus().equalsIgnoreCase("0")) {
                            cvEmpty.setVisibility(View.GONE);
                            cvFound.setVisibility(View.VISIBLE);
                            payAmount = 0.00;
                            deliveryCharges = 0.00;
                            totalShoppingPoints = 0.00;
                            productAmount = 0.00;
                            for (int i = 0; i < responseCartItems.getLstcart().get(0).getCartDetails().size(); i++) {
                                productAmount = productAmount + Double.parseDouble(responseCartItems.getLstcart().get(0).getCartDetails().get(i).getSubTotal());
                                payAmount = payAmount + Double.parseDouble(responseCartItems.getLstcart().get(0).getCartDetails().get(i).getSubTotal());
                                deliveryCharges = deliveryCharges + Double.parseDouble(responseCartItems.getLstcart().get(0).getCartDetails().get(i).getDeliveryCharge());
                                totalShoppingPoints = totalShoppingPoints + Double.parseDouble(responseCartItems.getLstcart().get(0).getCartDetails().get(i).getRedeemPrice());
                            }
                            payAmount = payAmount + deliveryCharges;
                            product_rupees.setText("₹ " + String.valueOf(productAmount));
                            tv_product_count.setText(String.format("Product (%d)", responseCartItems.getLstcart().get(0).getCartDetails().size()));
                            tvDeliveryCharges.setText("₹ " + String.valueOf(deliveryCharges));
                            tvPayableAmt.setText("₹ " + String.valueOf(payAmount));
                            tv_final_amount.setText("₹ " + String.valueOf(payAmount));
                            cartAdapter = new CartAdapter(context, responseCartItems.getLstcart().get(0).getCartDetails(), CartActivity.this);
                            rvCategories.setAdapter(cartAdapter);
                        } else {
                            cvEmpty.setVisibility(View.VISIBLE);
                            cvFound.setVisibility(View.GONE);
                            rvCategories.setVisibility(View.GONE);
                            showMessage(responseCartItems.getErrorMessage() + "");
                        }
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

    @Override
    public void getCardItemQuantity(String productInfo, String vendorId, String type) {
        addToCart(productInfo, vendorId, type);
    }

    private void addToCart(String producyInfoId, String vendorId, String type) {
//        {"ProductInfoCode":"21062020120033","CustomerId":"1","ProductQuantity":"1","Fk_vendorId":"1","Type":"Add"}
        JsonObject addToCart = new JsonObject();
        addToCart.addProperty("ProductInfoCode", producyInfoId);
        addToCart.addProperty("CustomerId", PreferencesManager.getInstance(context).getUserid());
        addToCart.addProperty("ProductQuantity", "1");
        addToCart.addProperty("Fk_vendorId", vendorId);
        addToCart.addProperty("Type", type);
        showLoading();

        LoggerUtil.logItem(addToCart);

        Call<JsonObject> call = apiServices.AddToCart(bodyParam(addToCart));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    if (response.isSuccessful()) {
                        String paramResponse = Cons.decryptMsg(response.body().get("Body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        Gson gson = new GsonBuilder().create();
                        ResponseAddToCart responseAddToCart = gson.fromJson(paramResponse, ResponseAddToCart.class);
                        LoggerUtil.logItem(responseAddToCart);
                        if (responseAddToCart.getStatus().equalsIgnoreCase("0")) {
                            getCartItems();
                        } else cartAdapter.notifyDataSetChanged();
                        showMessage(responseAddToCart.getErrorMessage());
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                hideLoading();
            }
        });
    }

    @Override
    public void removeCartItem(String cartId) {
        removeItem(cartId);
    }

    private void removeItem(String cartId) {
//        {"CustomerId":"1","CartId":"1"}
        JsonObject item = new JsonObject();
        item.addProperty("CartId", cartId);
        item.addProperty("CustomerId", PreferencesManager.getInstance(context).getUserid());

        showLoading();
        LoggerUtil.logItem(item);

        Call<JsonObject> call = apiServices.RemoveItemFromCart(bodyParam(item));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    if (response.isSuccessful()) {
                        String paramResponse = Cons.decryptMsg(response.body().get("Body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        Gson gson = new GsonBuilder().create();
                        ResponseRemoveCartItem responseRemoveCartItem = gson.fromJson(paramResponse, ResponseRemoveCartItem.class);
                        LoggerUtil.logItem(responseRemoveCartItem);
                        if (responseRemoveCartItem.getStatus().equalsIgnoreCase("0")) {
                            getCartItems();
                        }
                        showMessage(responseRemoveCartItem.getErrorMessage());
                    }
                } catch (
                        Exception e) {
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
                            tvWalletBalance.setText("Shopping Balance : ₹ " + mlmDashboard.getShoppingPoint());
                            if (Float.parseFloat(mlmDashboard.getShoppingPoint()) > totalShoppingPoints) {
                                tvCoupon.setVisibility(View.VISIBLE);
                                tv_shopping_amt.setVisibility(View.VISIBLE);
                                tvAppliedAmount.setVisibility(View.VISIBLE);
//                                tvAppliedAmount.setTextColor(getResources().getColor(R.color.colorAccent));
                                tvWalletBalance.setText("Shopping Balance : ₹ " + mlmDashboard.getShoppingPoint());
                                tvAppliedAmount.setText("You will save ₹ " + totalShoppingPoints + " using Shopping Points");
//                                walletAppliedAmount = Float.parseFloat(response.optString("canUse"));
                                tv_shopping_amt.setText("- ₹ " + totalShoppingPoints);
                                isShoppingPointsApplied = true;
                                calculateShoppingPoints(true);
                            } else {
                                tvCoupon.setVisibility(View.GONE);
                                tv_shopping_amt.setVisibility(View.GONE);
                                tvAppliedAmount.setVisibility(View.GONE);
                                calculateShoppingPoints(false);
                                sUseWallet.setChecked(false);
                                showMessage(("Not Sufficient Shopping Points!"));
                            }
                        } else
                            showMessage(mlmDashboard.getErrorMessage());
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

    private void calculateShoppingPoints(boolean useShopping) {
        if (useShopping) {
            payAmount = payAmount - totalShoppingPoints;
        } else {
            if (isShoppingPointsApplied) {
                payAmount = payAmount + totalShoppingPoints;
            }
        }
        tvPayableAmt.setText("₹ " + String.valueOf(payAmount));
        tv_final_amount.setText("₹ " + String.valueOf(payAmount));
    }
}
