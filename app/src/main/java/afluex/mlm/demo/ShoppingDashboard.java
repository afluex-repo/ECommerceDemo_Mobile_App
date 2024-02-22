package afluex.mlm.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import afluex.mlm.demo.common.LoggerUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingDashboard extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.rv_shop_offers)
    DiscreteScrollView rvShopOffers;
    @BindView(R.id.rv_products)
    RecyclerView rvProducts;

    String subcategoryId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_dashboard, container, false);
        unbinder = ButterKnife.bind(this, view);

        rvProducts.setLayoutManager(new GridLayoutManager(context, 2));
        getArgumentsData();
//        getDashboardData();

        return view;
    }

    private void getArgumentsData() {
        if(getArguments()==null){

            Log.e("Calling","Dashboard");
            rvShopOffers.setVisibility(View.VISIBLE);
            getDashboardData();


        }else{
            Log.e("Calling","Category");
            subcategoryId=getArguments().getString("subCategoryId");
            rvShopOffers.setVisibility(View.GONE);
            getCategoryWiseData();
        }
    }



    private void getCategoryWiseData() {
//        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("Fk_SubCategoryId", subcategoryId);
        object.addProperty("CategoryId", "");
        object.addProperty("Fk_MaincategoryId", "");

        Call<ResponseCategory> call = apiServices.GetCategoryWiseProduct(object);
        call.enqueue(new Callback<ResponseCategory>() {
            @Override
            public void onResponse(Call<ResponseCategory> call, Response<ResponseCategory> response) {
//                hideLoading();
                if(response.body().getLstproductItemArrayList()!=null){
                    if(response.body().getLstproductItemArrayList().size()>0){
                        AdapterShopDashboardProducts dashboardProducts =
                                new AdapterShopDashboardProducts(context,
                                        response.body().getLstproductItemArrayList(), ShoppingDashboard.this);
                        rvProducts.setAdapter(dashboardProducts);
                    }else{
                        showMessage("No product found!");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCategory> call, Throwable t) {

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void getDashboardData() {
//        showLoading();
        Call<JsonObject> call = apiServices.shopDashborad();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                hideLoading();
                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.code());
                ResponseShopDashboard mlmDashboard;
                try {
                    if (response.isSuccessful()) {
                        String paramResponse = Cons.decryptMsg(response.body().get("Body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        Gson gson = new GsonBuilder().create();
                        mlmDashboard = gson.fromJson(paramResponse, ResponseShopDashboard.class);
                        LoggerUtil.logItem(mlmDashboard);
                        if (mlmDashboard.getStatus().equalsIgnoreCase("0")) {


                            AdapterShopDashboardProducts dashboardProducts =
                                    new AdapterShopDashboardProducts(context,
                                            mlmDashboard.getLstproduct(), ShoppingDashboard.this);
                            rvProducts.setAdapter(dashboardProducts);
                            AdapterShopBanner adapterShopBanner = new AdapterShopBanner(context, mlmDashboard.getLstbanner());
                            rvShopOffers.setAdapter(adapterShopBanner);
                            rvShopOffers.scrollToPosition(0);
                            rvShopOffers.setClampTransformProgressAfter(3);
                            rvShopOffers.setSlideOnFlingThreshold(1500);
                            rvShopOffers.setSlideOnFling(true);
                            rvShopOffers.setOverScrollEnabled(true);
                            ScaleTransformer transformer = new ScaleTransformer.Builder()
                                    .setMaxScale(1.0f)
                                    .setMinScale(0.7f)
                                    .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                                    .setPivotY(Pivot.Y.CENTER) // CENTER is a default one
                                    .build();
                            rvShopOffers.setItemTransformer(transformer);
                        } else showMessage("No product found!");
                    } else {
                        showMessage(response.errorBody().string());
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
    public void getClick(int position, String value, String colorID) {
        super.getClick(position, value, colorID);
        Bundle bundle = new Bundle();
        bundle.putString("productId", value);
        bundle.putString("colorId", colorID);
        goToActivity(ProductDetails.class, bundle);
    }
}
