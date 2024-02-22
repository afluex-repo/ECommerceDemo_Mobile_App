package afluex.mlm.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class MyShoppingOrders extends BaseActivity implements CancelOrderListener {

    @BindView(R.id.rv_orders)
    RecyclerView rvOrders;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ahopping_orders_activity);
        ButterKnife.bind(this);

        tvTitle.setText("My Orders");
        rvOrders.setLayoutManager(new LinearLayoutManager(context));
        if (NetworkUtils.getConnectivityStatus(context) != 0)
            getMyOrders();
        else showMessage(getString(R.string.alert_internet));
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }

    private void getMyOrders() {
//        {"CustomerId":"1"}
        JsonObject items = new JsonObject();
        items.addProperty("CustomerId", PreferencesManager.getInstance(context).getUserid());


        showLoading();
        LoggerUtil.logItem(items);

        Call<JsonObject> call = apiServices.MyOrders(bodyParam(items));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.code());
                ResponseOrders mlmDashboard;
                try {
                    if (response.isSuccessful()) {
                        String paramResponse = Cons.decryptMsg(response.body().get("Body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        Gson gson = new GsonBuilder().create();
                        mlmDashboard = gson.fromJson(paramResponse, ResponseOrders.class);
                        LoggerUtil.logItem(mlmDashboard);

                        Log.e("Sacfgc",""+mlmDashboard.getLstorder().get(0).getOrderDetails().size());
                        if (mlmDashboard.getStatus().equalsIgnoreCase("0")) {
                            AdapterShoppingOrders adapter = new AdapterShoppingOrders(context, mlmDashboard.getLstorder().get(0).getOrderDetails(), MyShoppingOrders.this);
                            rvOrders.setVisibility(View.VISIBLE);
                            rvOrders.setAdapter(adapter);
                            DividerItemDecoration itemDecor = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
                            rvOrders.addItemDecoration(itemDecor);
                        } else {
                            rvOrders.setVisibility(View.GONE);
                            showMessage("No Order Found");
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

    @Override
    public void cancelOrder(String productId) {
//        {"CustomerId":"1","PK_OrderDetailsID":"1"}
        JsonObject order = new JsonObject();
        order.addProperty("CustomerId", PreferencesManager.getInstance(context).getUserid());
        order.addProperty("PK_OrderDetailsID", productId);

        showLoading();
        LoggerUtil.logItem(order);

        Call<JsonObject> call = apiServices.CancelOrder(bodyParam(order));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.code());
                ResponseCancelOrder mlmDashboard;
                try {
                    if (response.isSuccessful()) {
                        String paramResponse = Cons.decryptMsg(response.body().get("Body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        Gson gson = new GsonBuilder().create();
                        mlmDashboard = gson.fromJson(paramResponse, ResponseCancelOrder.class);
                        LoggerUtil.logItem(mlmDashboard);
                        if (mlmDashboard.getStatus().equalsIgnoreCase("0"))
                            getMyOrders();
                        else showMessage(mlmDashboard.getErrorMessage() + "");
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
}
