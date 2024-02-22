package afluex.mlm.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import afluex.mlm.demo.responseProductDetails.LstmaterialItem;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterMaterial extends RecyclerView.Adapter<AdapterMaterial.MyViewHolder> {

    private Context context;
    private List<LstmaterialItem> productList;
    SelectedVarientsListener listener;

    public AdapterMaterial(Context mContext, List<LstmaterialItem> productList, SelectedVarientsListener listener) {
        this.context = mContext;
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sub_category, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvCategory.setText(productList.get(position).getMaterialName());
        if (productList.get(position).getStatus().contains("selected")) {
            holder.tvCategory.setTextColor(context.getResources().getColor(R.color.text_color));
            holder.tvCategory.setBackgroundColor(context.getResources().getColor(R.color.dot_light_screen2));
            holder.tvCategory.setTextAppearance(context, R.style.TextFieldBold);
        } else {
            holder.tvCategory.setTextColor(context.getResources().getColor(R.color.text_color));
            holder.tvCategory.setTextAppearance(context, R.style.TextFieldNormal);
        }

        holder.tvCategory.setOnClickListener(view -> {
            if (!productList.get(position).getStatus().contains("selected"))
                listener.selectedVarients("", "", "", "", productList.get(position).getMaterialID(), "");
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_category)
        TextView tvCategory;


        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}

