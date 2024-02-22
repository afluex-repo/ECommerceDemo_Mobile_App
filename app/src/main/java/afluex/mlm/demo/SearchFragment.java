package afluex.mlm.demo;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import afluex.mlm.demo.common.LoggerUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends BaseFragment {

    EditText et_search;
    RecyclerView rv_products;
    TextView txt_no_products;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        et_search=view.findViewById(R.id.et_search);
        rv_products=view.findViewById(R.id.rv_products);
        txt_no_products=view.findViewById(R.id.txt_no_products);

        rv_products.setVisibility(View.GONE);
        txt_no_products.setVisibility(View.VISIBLE);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    getSearch(s.toString());
                }else{
                    rv_products.setVisibility(View.GONE);
                    txt_no_products.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // Inflate the layout for this fragment
        return view;


    }

    private void getSearch(String s) {
        JsonObject details = new JsonObject();
        details.addProperty("ProductName", s);

        LoggerUtil.logItem(details);
        Call<JsonObject> call = apiServices.GlobalSearch(bodyParam(details));
        Log.e("Pagut",""+bodyParam(details));


        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.code());
                ResponseSearch mlmDashboard;
                try {
                    if (response.isSuccessful()) {
                        String paramResponse = Cons.decryptMsg(response.body().get("Body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        Gson gson = new GsonBuilder().create();
                        mlmDashboard = gson.fromJson(paramResponse, ResponseSearch.class);

                        if(mlmDashboard.getSearchArrayList().get(0).getNewArrivals().size()>0){
                            rv_products.setVisibility(View.VISIBLE);
                            txt_no_products.setVisibility(View.GONE);
                            AdapterSearch adapterSearch=new AdapterSearch(getActivity(),mlmDashboard.getSearchArrayList().get(0).getNewArrivals(),SearchFragment.this);
                            rv_products.setAdapter(adapterSearch);

                        }else{
                            txt_no_products.setText("No Product found");
                            rv_products.setVisibility(View.GONE);
                            txt_no_products.setVisibility(View.VISIBLE);
                        }
                        Log.e("Pagut",paramResponse);
                    } else {
                        showMessage("Something went wrong!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }


    @Override
    public void getClick(int position, String value, String colorID) {
        super.getClick(position, value, colorID);
        Bundle bundle = new Bundle();
        bundle.putString("productId", value);
        goToActivity(ProductDetails.class, bundle);
    }
}