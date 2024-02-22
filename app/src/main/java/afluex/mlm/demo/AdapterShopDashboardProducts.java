package afluex.mlm.demo;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import java.util.List;

import afluex.mlm.demo.retrofit.MvpView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterShopDashboardProducts extends RecyclerView.Adapter<AdapterShopDashboardProducts.ViewHolder> {
    private List<LstproductItem> itemArrayList;
    private Context mContext;
    private MvpView mvp;

    public AdapterShopDashboardProducts(Context context, List<LstproductItem> itemArrayList, MvpView mvp) {
        mContext = context;
        this.itemArrayList = itemArrayList;
        this.mvp = mvp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shop_dashboard_products, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {
        Glide.with(mContext).load(itemArrayList.get(listPosition).getImages()).into(holder.imgProduct);
        Log.e("Bjhb",itemArrayList.get(listPosition).getImages());
        Log.e("Bjhb",itemArrayList.get(listPosition).getPkProductId());
        holder.tvProductName.setText(itemArrayList.get(listPosition).getProductName());
        holder.tvMrp.setPaintFlags(holder.tvMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvMrp.setText("₹" + itemArrayList.get(listPosition).getMRP());
        holder.tvOfferPrice.setText("₹" + itemArrayList.get(listPosition).getOfferedPrice());

        holder.itemView.setOnClickListener(v -> mvp.getClick(listPosition, itemArrayList.get(listPosition).getPkProductId(),itemArrayList.get(listPosition).getColorID()));

    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_product)
        ImageView imgProduct;
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.img_fav)
        ImageView imgFav;
        @BindView(R.id.tv_offer_price)
        TextView tvOfferPrice;
        @BindView(R.id.tv_mrp)
        TextView tvMrp;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
