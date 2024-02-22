package afluex.mlm.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterShopBanner extends RecyclerView.Adapter<AdapterShopBanner.CityViewHolder> {
    Context context;
    List<LstbannerItem> models;

    public AdapterShopBanner(Context context, List<LstbannerItem> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.shop_adapter_banner, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        Glide.with(context).load(models.get(position).getBannerImage()).into(holder.img_thumb);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_banner)
        ImageView img_thumb;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}