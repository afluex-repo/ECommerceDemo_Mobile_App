package afluex.mlm.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

import afluex.mlm.demo.responseProductDetails.VarientsItem;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterVarients extends RecyclerView.Adapter<AdapterVarients.MyViewHolder> {
    private Context context;
    private List<VarientsItem> productList;
    SelectedVarientsListener listener;

    public AdapterVarients(Context mContext, List<VarientsItem> productList, SelectedVarientsListener listener) {
        this.context = mContext;
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_varients, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        switch (productList.get(position).getTitle()) {
            case "Size":
                if (productList.get(position).getLstsize() != null && productList.get(position).getLstsize().size() > 0) {
                    holder.itemView.setVisibility(View.VISIBLE);
                    holder.tvCategory.setVisibility(View.VISIBLE);
                    holder.tvCategory.setText(productList.get(position).getTitle());
                    AdapterSize adapterSize = new AdapterSize(context, productList.get(position).getLstsize(), listener);
                    holder.rvSubCategory.setAdapter(adapterSize);
                } else holder.itemView.setVisibility(View.GONE);
                break;
            case "Color":
                if (productList.get(position).getLstcolor() != null && productList.get(position).getLstcolor().size() > 0) {
                    holder.tvCategory.setVisibility(View.VISIBLE);
                    holder.itemView.setVisibility(View.VISIBLE);
                    holder.tvCategory.setText(productList.get(position).getTitle());
                    AdapterColor adapterColor = new AdapterColor(context, productList.get(position).getLstcolor(), listener);
                    holder.rvSubCategory.setAdapter(adapterColor);
                } else holder.itemView.setVisibility(View.GONE);
                break;
            case "Flavour":
                if (productList.get(position).getLstflavour() != null && productList.get(position).getLstflavour().size() > 0) {
                    holder.tvCategory.setVisibility(View.VISIBLE);
                    holder.itemView.setVisibility(View.VISIBLE);
                    holder.tvCategory.setText(productList.get(position).getTitle());
                    AdapterFlavoue adapterFlavoue = new AdapterFlavoue(context, productList.get(position).getLstflavour(), listener);
                    holder.rvSubCategory.setAdapter(adapterFlavoue);
                } else holder.itemView.setVisibility(View.GONE);
                break;
            case "Ram":
                if (productList.get(position).getLstram() != null && productList.get(position).getLstram().size() > 0) {
                    holder.tvCategory.setVisibility(View.VISIBLE);
                    holder.itemView.setVisibility(View.VISIBLE);
                    holder.tvCategory.setText(productList.get(position).getTitle());
                    AdapterRam adapterRam = new AdapterRam(context, productList.get(position).getLstram(), listener);
                    holder.rvSubCategory.setAdapter(adapterRam);
                } else holder.itemView.setVisibility(View.GONE);
                break;
            case "Storage":
                if (productList.get(position).getLststorage() != null && productList.get(position).getLststorage().size() > 0) {
                    holder.tvCategory.setVisibility(View.VISIBLE);
                    holder.itemView.setVisibility(View.VISIBLE);
                    holder.tvCategory.setText(productList.get(position).getTitle());
                    AdapterStorage adapterStorage = new AdapterStorage(context, productList.get(position).getLststorage(), listener);
                    holder.rvSubCategory.setAdapter(adapterStorage);
                } else holder.itemView.setVisibility(View.GONE);
                break;
            case "Material":
                if (productList.get(position).getLstmaterial() != null && productList.get(position).getLstmaterial().size() > 0) {
                    holder.tvCategory.setVisibility(View.VISIBLE);
                    holder.itemView.setVisibility(View.VISIBLE);
                    holder.tvCategory.setText(productList.get(position).getTitle());
                    AdapterMaterial adapterMaterial = new AdapterMaterial(context, productList.get(position).getLstmaterial(), listener);
                    holder.rvSubCategory.setAdapter(adapterMaterial);
                } else holder.itemView.setVisibility(View.GONE);
                break;
            case "Images":
                holder.itemView.setVisibility(View.GONE);
                break;
            default:
                holder.itemView.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return productList.size() - 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_category)
        TextView tvCategory;
        @BindView(R.id.rv_sub_category)
        RecyclerView rvSubCategory;


        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            rvSubCategory.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        }
    }
}
