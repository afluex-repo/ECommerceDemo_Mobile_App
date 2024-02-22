package afluex.mlm.demo;



import static afluex.mlm.demo.app.AppConfig.PAYLOAD_BUNDLE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import afluex.mlm.demo.app.PreferencesManager;
import afluex.mlm.demo.app.TouchImageView;
import afluex.mlm.demo.common.LoggerUtil;
import afluex.mlm.demo.common.NetworkUtils;
import afluex.mlm.demo.responseProductDetails.LstimagesItem;
import afluex.mlm.demo.responseProductDetails.ResponseProductDetails;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetails extends BaseActivity implements SelectedVarientsListener {
    @BindView(R.id.productSlider)
    SliderView productSlider;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_cart)
    ImageView imgCart;
    ResponseProductDetails productDetails;

    @BindView(R.id.tv_product_name)
    TextView tvProductName;
    @BindView(R.id.tv_offer_price)
    TextView tvOfferPrice;
    @BindView(R.id.tv_mrp)
    TextView tvMrp;
    @BindView(R.id.tv_off_percent)
    TextView tvOffPercent;
    @BindView(R.id.tv_availability)
    TextView tvAvailability;
    @BindView(R.id.tv_seller)
    TextView tvSeller;
    @BindView(R.id.tv_delivery)
    TextView tvDelivery;
    @BindView(R.id.tv_short_desc)
    TextView tvShortDesc;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.rv_categories)
    RecyclerView rvCategories;
    @BindView(R.id.btn_buy_now)
    Button btnBuyNow;
    @BindView(R.id.btn_add_cart)
    Button btnAddCart;
    @BindView(R.id.tv_cart_count)
    TextView tv_cart_count;
    @BindView(R.id.tv_redeem)
    TextView tv_redeem;

    List<LstimagesItem> imagesDetailsItems;

    String size = "", color = "", flavour = "", ram = "", Material = "", storage = "";
    String productId = "", producyInfoId = "", vendorId = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_product_details);
        ButterKnife.bind(this);
        productId = getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("productId");
        color = getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("colorId");
    }

    @OnClick({R.id.back_btn, R.id.img_cart, R.id.btn_buy_now, R.id.btn_add_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.img_cart:
                goToActivity(context, CartActivity.class, null);
                break;
            case R.id.btn_buy_now:
                if (btnBuyNow.getText().toString().equalsIgnoreCase("Go To Cart"))
                    goToActivity(context, CartActivity.class, null);
                else
                    addToCart(true);
                break;
            case R.id.btn_add_cart:
                addToCart(false);
                break;
        }
    }

    private void getProductDetails() {
        showLoading();
//        {"ProductID":"9","ColorID":"0","SizeID":"6","RamID":"0","StorageID":"0","FlavorID":"3","MaterialID":"","LastSelected":"Flavor"}
        JsonObject details = new JsonObject();
        details.addProperty("ProductID", productId);
        details.addProperty("ColorID", color);
        details.addProperty("SizeID", size);
        details.addProperty("RamID", ram);
        details.addProperty("StorageID", storage);
        details.addProperty("FlavorID", flavour);
        details.addProperty("MaterialID", Material);
        details.addProperty("CustomerId", "");
        details.addProperty("FK_UserId", PreferencesManager.getInstance(context).getUserid());

        Log.e("nbjkb","1"+productId);
        Log.e("nbjkb","2"+color);
        Log.e("nbjkb","3"+size);
        Log.e("nbjkb","4"+ram);
        Log.e("nbjkb","5"+storage);
        Log.e("nbjkb","6"+flavour);
        Log.e("nbjkb","7"+Material);
        Log.e("nbjkb","8"+PreferencesManager.getInstance(context).getUserid());




        LoggerUtil.logItem(details);
        Call<JsonObject> call = apiServices.getProductDetails(bodyParam(details));
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
                        productDetails = gson.fromJson(paramResponse, ResponseProductDetails.class);
                        LoggerUtil.logItem(productDetails);
                        if (productDetails.getStatus().equalsIgnoreCase("0")) {
                            producyInfoId = productDetails.getProductInfoCode();
                            vendorId = productDetails.getFkVendorId();
                            setData(productDetails);
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

    private void setData(ResponseProductDetails body) {
        tvProductName.setText(body.getProductName());
        tvMrp.setText(String.format("₹ %s", body.getMRP()));
        tvOfferPrice.setText(String.format("₹ %s", body.getOfferedPrice()));
        tvMrp.setPaintFlags(tvMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tvSeller.setText(String.format("%s (Seller)", body.getVendorName()));
        tvDelivery.setText(String.format("Delivery: ₹ %s", body.getDeliveryCharge()));
        tvDesc.setText(body.getDescription());
        tvShortDesc.setText(body.getShortDescription());
        tvDesc.setCompoundDrawablesWithIntrinsicBounds(R.drawable.btn_green, 0, 0, 0);
        tvTitle.setText("Afluex Shopping");
        imagesDetailsItems = new ArrayList<>();
        imagesDetailsItems.clear();
        for (int i = 0; i < body.getVarients().size(); i++) {
            if (body.getVarients().get(i).getTitle().equalsIgnoreCase("Images")) {
                imagesDetailsItems = body.getVarients().get(i).getLstimages();
                break;
            }
        }

        if(TextUtils.isEmpty(PreferencesManager.getInstance(context).getUserid())){
            btnBuyNow.setText("Add To Cart");
            btnAddCart.setVisibility(View.GONE);
        }else{
            btnBuyNow.setText("Buy Now");
            btnAddCart.setVisibility(View.VISIBLE);
        }



        if (Float.parseFloat(body.getRedeemPrice()) > 0) {
            tv_redeem.setVisibility(View.VISIBLE);
            tv_redeem.setText("Redeem: " + body.getRedeemPrice() + "Points");
        } else tv_redeem.setVisibility(View.GONE);

        if (body.getIsAvailable().equalsIgnoreCase("1")) {
            tvAvailability.setText("Available");
            tvAvailability.setTextColor(getResources().getColor(R.color.white));

            btnAddCart.setVisibility(View.VISIBLE);
            btnBuyNow.setVisibility(View.VISIBLE);
        } else {
            tvAvailability.setText("Out Of Stock");
            tvAvailability.setTextColor(getResources().getColor(R.color.white));
            btnAddCart.setVisibility(View.GONE);
            btnBuyNow.setVisibility(View.GONE);
        }

        if (body.getIsCart().equalsIgnoreCase("1")) {
            if(TextUtils.isEmpty(PreferencesManager.getInstance(context).getUserid())){
                btnBuyNow.setText("Add To Cart");
                btnAddCart.setVisibility(View.GONE);
            }else{
                btnAddCart.setVisibility(View.GONE);
                btnBuyNow.setText("Go To Cart");
            }

        } else {
            btnAddCart.setVisibility(View.VISIBLE);
            btnBuyNow.setText("Buy Now");
        }

        SliderAdapter shoppingBanner = new SliderAdapter(context, imagesDetailsItems);
        productSlider.setSliderAdapter(shoppingBanner);

        LocalBroadcastManager.getInstance(ProductDetails.this).registerReceiver(mMessageReceiver,
                new IntentFilter("zoom_image"));

        AdapterVarients varients = new AdapterVarients(context, body.getVarients(), ProductDetails.this);
        rvCategories.setLayoutManager(new LinearLayoutManager(context));
        rvCategories.setHasFixedSize(true);
        rvCategories.setAdapter(varients);
//        DividerItemDecoration itemDecor = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
//        rvCategories.addItemDecoration(itemDecor);
    }

    public BroadcastReceiver mMessageReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String link=intent.getStringExtra("image_link");
            String type=intent.getStringExtra("type");
            int position=intent.getIntExtra("position",0);

            Log.e("ImageLinjgcgy",link);

            final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(ProductDetails.this);
            View mView = LayoutInflater.from(ProductDetails.this).inflate(R.layout.dialog_zoom_image, null);
            alert.setView(mView);

            TouchImageView touchImageView=mView.findViewById(R.id.image_view);
            ImageView ic_close=mView.findViewById(R.id.ic_close);


            Glide.with(ProductDetails.this).load(link)
                    .into(touchImageView);




            android.app.AlertDialog alertDialog = alert.create();
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            alertDialog.show();

            ic_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
alertDialog.dismiss();
                }
            });














        }
    };

    @Override
    public void selectedVarients(String size, String color, String flavour, String ram, String Material, String storage) {
        this.size = "";
        this.color = "";
        this.flavour = "";
        this.ram = "";
        this.Material = "";
        this.storage = "";
        if (size.length() != 0)
            this.size = size;
        if (color.length() != 0)
            this.color = color;
        if (flavour.length() != 0)
            this.flavour = flavour;
        if (ram.length() != 0)
            this.ram = ram;
        if (Material.length() != 0)
            this.Material = Material;
        if (storage.length() != 0)
            this.storage = storage;
        getProductDetails();
    }

    private void addToCart(boolean moveToCart) {
//        {"ProductInfoCode":"21062020120033","CustomerId":"1","ProductQuantity":"1","Fk_vendorId":"1","Type":"Add"}
        JsonObject addToCart = new JsonObject();
        addToCart.addProperty("ProductInfoCode", producyInfoId);
        addToCart.addProperty("CustomerId", PreferencesManager.getInstance(context).getUserid());
        addToCart.addProperty("ProductQuantity", "1");
        addToCart.addProperty("Fk_vendorId", vendorId);
        addToCart.addProperty("Type", "Add");
        showLoading();

        LoggerUtil.logItem(addToCart);

        Call<JsonObject> call = apiServices.AddToCart(bodyParam(addToCart));


        Log.e("Sargadfgdfg",""+bodyParam(addToCart));
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
                            btnAddCart.setVisibility(View.GONE);
                            btnBuyNow.setText("Go To Cart");
                            getCartCount();
                            if (moveToCart)
                                goToActivity(context, CartActivity.class, null);
                        }
                        showMessage(responseAddToCart.getErrorMessage());
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
    protected void onResume() {
        super.onResume();
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getCartCount();
            getProductDetails();
        } else showMessage(getString(R.string.alert_internet));
    }

    private void getCartCount() {
//        {"CustomerId":"1"}

        if(TextUtils.isEmpty(PreferencesManager.getInstance(context).getUserid())){
            tv_cart_count.setVisibility(View.GONE);
        }else{
            JsonObject items = new JsonObject();
            items.addProperty("CustomerId", PreferencesManager.getInstance(context).getUserid());
            Log.e("jkgydtdt",PreferencesManager.getInstance(context).getUserid());

            Call<JsonObject> call = apiServices.CartCount(bodyParam(items));

            Log.e("jkgydtdt",""+bodyParam(items));

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    try {
                        Log.e("jkgydtdt",""+response.isSuccessful());
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("Body").getAsString(), cross_intent);
                            LoggerUtil.logItem(paramResponse);
                            Gson gson = new GsonBuilder().create();
                            ResponseCartCount responseAddToCart = gson.fromJson(paramResponse, ResponseCartCount.class);
                            LoggerUtil.logItem(responseAddToCart);
                            Log.e("jkgydtdt",""+responseAddToCart.getStatus());
                            if (responseAddToCart.getStatus().equalsIgnoreCase("0")) {
                                Log.e("jkgydtdt","Count"+responseAddToCart.getCount());
                                if (!responseAddToCart.getCount().equalsIgnoreCase("0")) {
                                    tv_cart_count.setVisibility(View.VISIBLE);
                                    tv_cart_count.setText(responseAddToCart.getCount());
                                } else tv_cart_count.setVisibility(View.GONE);
                            } else tv_cart_count.setVisibility(View.GONE);
                        }else{
                            tv_cart_count.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        tv_cart_count.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                }
            });
        }

    }
}
