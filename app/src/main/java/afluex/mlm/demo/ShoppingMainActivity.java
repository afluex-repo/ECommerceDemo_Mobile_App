package afluex.mlm.demo;

import static android.app.PendingIntent.getActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.List;

import afluex.mlm.demo.app.PreferencesManager;
import afluex.mlm.demo.common.LoggerUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingMainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        SideMenuListener{

    //    private SlidingRootNav slidingRootNav;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    DrawerLayout drawer;
    ImageView img_side_menu, img_cart, img_user,img_search;
    TextView tv_user_name, tv_dashboard, tv_home, tv_orders, my_cart,action_logout;
    RecyclerView rv_cat_images, rv_main_category, rv_category;
    TextView tv_cart_count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setTitle(context, getResources().getString(R.string.app_name), R.color.white, 18);

        img_search = findViewById(R.id.img_search);
        img_side_menu = findViewById(R.id.img_side_menu);
        img_cart = findViewById(R.id.img_cart);
        drawer = findViewById(R.id.drawer_layout);
        tv_cart_count = findViewById(R.id.tv_cart_count);
        if(TextUtils.isEmpty(PreferencesManager.getInstance(context).getUserid())) {
            tv_cart_count.setVisibility(View.GONE);
        }else{
            getCartCount();
        }
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);

        img_side_menu.setOnClickListener(v -> {
            if (drawer.isDrawerOpen(GravityCompat.START))
                drawer.closeDrawers();
            else drawer.openDrawer(GravityCompat.START);
        });

        rv_cat_images = hView.findViewById(R.id.rv_cat_images);
        rv_main_category = hView.findViewById(R.id.rv_main_category);
        rv_category = hView.findViewById(R.id.rv_category);

        rv_cat_images.setLayoutManager(new LinearLayoutManager(context));
        rv_main_category.setLayoutManager(new LinearLayoutManager(context));
        rv_category.setLayoutManager(new LinearLayoutManager(context));

        img_cart.setOnClickListener(v -> goToActivity(context, CartActivity.class, null));

        img_user = hView.findViewById(R.id.img_user);
        tv_user_name = hView.findViewById(R.id.tv_user_name);
        tv_dashboard = hView.findViewById(R.id.tv_dashboard);
        tv_dashboard.setOnClickListener(this);
        tv_home = hView.findViewById(R.id.tv_home);
        tv_home.setOnClickListener(this);
        tv_orders = hView.findViewById(R.id.tv_orders);
        tv_orders.setOnClickListener(this);
        my_cart = hView.findViewById(R.id.my_cart);
        action_logout = hView.findViewById(R.id.action_logout);
        action_logout.setOnClickListener(this);
        my_cart.setOnClickListener(this);
        tv_user_name.setText(PreferencesManager.getInstance(context).getFullname());

        Glide.with(context).load(PreferencesManager.getInstance(context).getImage())
                .apply(RequestOptions.circleCropTransform().placeholder(R.mipmap.ic_launcher_round)
                        .error(R.mipmap.ic_launcher_round))
                .into(img_user);


        LocalBroadcastManager.getInstance(ShoppingMainActivity.this).registerReceiver(mMessageReceiver,
                new IntentFilter("site_position"));


        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReplaceFragment(new SearchFragment(), "Search Product");
            }
        });

        ReplaceFragment(new ShoppingDashboard(), "Afluex Shopping");
        getMenu();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setTitle(Context context, String txt, int color, int size) {
        // tv2.setVisibility(View.GONE);

        toolbarTitle.setText(txt);
        toolbarTitle.setTextSize(size);
        toolbarTitle.setTextColor(context.getResources().getColor(color));
    }

    public Fragment currentFragment;

    public void ReplaceFragment(Fragment setFragment, String title) {
        new Handler().postDelayed(() -> {
            currentFragment = setFragment;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container, setFragment, title);
            toolbarTitle.setText(title);
            transaction.commitAllowingStateLoss();
        }, 400);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() < 1) {
            if (currentFragment instanceof ShoppingDashboard) {
                super.onBackPressed();
                goToActivityWithFinish(context, Splash.class, null);
            } else {
                ReplaceFragment(new ShoppingDashboard(), "Afluex Shopping");
            }
        } else {
            fm.popBackStack();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_dashboard:
                drawer.closeDrawer(GravityCompat.START);
                if (!(currentFragment instanceof ShoppingDashboard))
                    ReplaceFragment(new ShoppingDashboard(), "Afluex Shopping");
                break;
            case R.id.tv_home:
                drawer.closeDrawer(GravityCompat.START);
                goToActivityWithFinish(context, Splash.class, null);
                break;
            case R.id.tv_orders:
                drawer.closeDrawer(GravityCompat.START);
                goToActivity(context, MyShoppingOrders.class, null);
                break;
            case R.id.my_cart:
                drawer.closeDrawer(GravityCompat.START);
                goToActivity(context, CartActivity.class, null);
                break;

            case R.id.action_logout:

                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(ShoppingMainActivity.this);
                builder.setCancelable(false);
                builder.setTitle("Are you Sure you want to Log out?")
                        .setMessage("Once you log out you need to log in again.")
                        .setCancelable(true)
                        .setPositiveButton("Log out", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PreferencesManager.getInstance(ShoppingMainActivity.this).clear();
                                startActivity(new Intent(ShoppingMainActivity.this,Login.class));
                                drawer.closeDrawer(GravityCompat.START);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                drawer.closeDrawer(GravityCompat.START);
                            }
                        });
                builder.show();

                break;
        }
//        slidingRootNav.closeMenu(true);
    }

    private void getMenu() {
        Call<ResponseMenuCategory> call = apiServices.GetMenu();
        call.enqueue(new Callback<ResponseMenuCategory>() {
            @Override
            public void onResponse(Call<ResponseMenuCategory> call, Response<ResponseMenuCategory> response) {
                LoggerUtil.logItem(response.body());
//                AdapterSideMenuCategoryImage menuCategoryImage = new AdapterSideMenuCategoryImage(context,
//                        response.body().getLstnmain(), ShoppingMainActivity.this);
//
//                Log.e("CTREFGF",""+response.body().getLstnmain().size());
//                rv_cat_images.setAdapter(menuCategoryImage);
                rv_cat_images.setVisibility(View.GONE);
                DividerItemDecoration itemDecor = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
                rv_cat_images.addItemDecoration(itemDecor);
                AdapterSideMenuMainCategory menuMainCategory = new AdapterSideMenuMainCategory(context,
                        response.body().getLstnmain(), ShoppingMainActivity.this);
                rv_main_category.setAdapter(menuMainCategory);
                rv_main_category.addItemDecoration(itemDecor);
                rv_main_category.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<ResponseMenuCategory> call, Throwable t) {

            }
        });
    }

    @Override
    public void openCategory(List<MainCategoryDetailsItem> list) {
        rv_main_category.setVisibility(View.GONE);
        rv_main_category.animate();
        rv_category.setVisibility(View.VISIBLE);
        rv_category.animate();
        AdapterSiemenuCategory siemenuCategory = new AdapterSiemenuCategory(context, list);
        rv_category.setAdapter(siemenuCategory);
    }
    public BroadcastReceiver mMessageReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String subcategoryId=intent.getStringExtra("subCategoryId");
            String mainCategoryId=intent.getStringExtra("mainCategoryId");
            String categoryId=intent.getStringExtra("categoryId");
            String name=intent.getStringExtra("name");

            Log.e("Salary_id","1::::: "+subcategoryId );
            Log.e("Salary_id","2::::: "+mainCategoryId );
            Log.e("Salary_id","3::::: "+categoryId );

            Bundle bundle =new Bundle();
            bundle.putString("subCategoryId",subcategoryId);
            bundle.putString("mainCategoryId",mainCategoryId);
            bundle.putString("categoryId",categoryId);
            Fragment fragment=new ShoppingDashboard();
            fragment.setArguments(bundle);
            drawer.closeDrawers();
            ReplaceFragment(fragment,name);

        }
    };

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

    @Override
    protected void onResume() {
        super.onResume();
        if(TextUtils.isEmpty(PreferencesManager.getInstance(context).getUserid())) {
            tv_cart_count.setVisibility(View.GONE);
        }else{
            getCartCount();
        }
    }
}

//    @Override
//    public void openCategory(List<LstCategoryItem> list) {
//        rv_main_category.setVisibility(View.GONE);
//        rv_main_category.animate();
//        rv_category.setVisibility(View.VISIBLE);
//        rv_category.animate();
//        AdapterSiemenuCategory siemenuCategory = new AdapterSiemenuCategory(context, list);
//        rv_category.setAdapter(siemenuCategory);
//    }
//}