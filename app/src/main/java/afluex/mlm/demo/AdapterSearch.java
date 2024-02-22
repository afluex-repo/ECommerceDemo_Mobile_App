package afluex.mlm.demo;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import afluex.mlm.demo.retrofit.MvpView;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.HolderSearch> {

    private Context context;
    private ArrayList<ModelNewArrivals> modelNewArrivalsArrayList;

    private MvpView mvp;

    public AdapterSearch(Context context, ArrayList<ModelNewArrivals> modelNewArrivalsArrayList, MvpView mvp) {
        this.context = context;
        this.modelNewArrivalsArrayList = modelNewArrivalsArrayList;
        this.mvp = mvp;
    }

    @NonNull
    @Override
    public AdapterSearch.HolderSearch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_search_single_row,parent,false);
        return new AdapterSearch.HolderSearch(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSearch.HolderSearch holder, int position) {
        
        ModelNewArrivals modelNewArrivals=modelNewArrivalsArrayList.get(position);
        Glide.with(context).load(modelNewArrivalsArrayList.get(position).getImages()).into(holder.img_product);
        holder.txt_product_name.setText(modelNewArrivalsArrayList.get(position).getProductName());
        holder.tv_mrp.setPaintFlags(holder.tv_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_mrp.setText("₹" + modelNewArrivalsArrayList.get(position).getMrp());
        holder.tv_discounted.setText("₹" + modelNewArrivalsArrayList.get(position).getOfferedPrice());

        holder.itemView.setOnClickListener(v -> mvp.getClick(position, modelNewArrivalsArrayList.get(position).getPkProductId(), ""));

    }

    @Override
    public int getItemCount() {
        return modelNewArrivalsArrayList.size();
    }

    public class HolderSearch extends RecyclerView.ViewHolder {
        
        TextView txt_product_name,tv_mrp,tv_discounted;
        ImageView img_product;
        public HolderSearch(@NonNull View itemView) {
            super(itemView);

            txt_product_name=itemView.findViewById(R.id.txt_product_name);
            tv_mrp=itemView.findViewById(R.id.tv_mrp);
            tv_discounted=itemView.findViewById(R.id.tv_discounted);
            img_product=itemView.findViewById(R.id.img_product);
        }
    }
}
