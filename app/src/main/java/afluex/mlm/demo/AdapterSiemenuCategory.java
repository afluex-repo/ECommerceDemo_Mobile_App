package afluex.mlm.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterSiemenuCategory extends RecyclerView.Adapter<AdapterSiemenuCategory.MyViewHolder> {

    private Context context;
    private List<MainCategoryDetailsItem> productList;
    Boolean open=false;


    public AdapterSiemenuCategory(Context mContext, List<MainCategoryDetailsItem> productList) {
        this.context = mContext;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shopping_sidemune_category, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvCategoryName.setText(productList.get(position).getMainCategoryName());
        Log.e("JHVHf",""+productList.get(position).getMainCategoryName());
        AdapterSidemenuSubCategory subCategory = new AdapterSidemenuSubCategory(context, productList.get(position).getLstCategory());
        holder.rvSubCategory.setAdapter(subCategory);

        holder.tvCategoryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!open){
                    open=true;
                    holder.tvCategoryName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_minus_small, 0);
                    holder.rvSubCategory.setVisibility(View.VISIBLE);
                }else{
                    open=false;
                    holder.tvCategoryName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_plus, 0);
                    holder.rvSubCategory.setVisibility(View.GONE);
                }
            }
        });

//        if (productList.get(position).isOpen()) {
//            holder.tvCategoryName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_minus_small, 0);
//            holder.rvSubCategory.setVisibility(View.VISIBLE);
//        } else {
//            holder.tvCategoryName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_plus, 0);
//            holder.rvSubCategory.setVisibility(View.GONE);
//        }
//
//        holder.tvCategoryName.setOnClickListener(v -> {
//            for (int i = 0; i < productList.size(); i++) {
//                productList.get(i).setOpen(false);
//            }
//            new Handler().postDelayed(() -> {
//                productList.get(position).setOpen(true);
//                notifyDataSetChanged();
//            }, 100);
//        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_category_name)
        TextView tvCategoryName;
        @BindView(R.id.rv_sub_category)
        RecyclerView rvSubCategory;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            rvSubCategory.setLayoutManager(new LinearLayoutManager(context));
            DividerItemDecoration itemDecor = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
            rvSubCategory.addItemDecoration(itemDecor);
        }
    }
}