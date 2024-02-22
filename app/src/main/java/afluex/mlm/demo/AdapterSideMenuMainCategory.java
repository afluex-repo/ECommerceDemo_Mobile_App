package afluex.mlm.demo;

import android.annotation.SuppressLint;
import android.content.Context;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterSideMenuMainCategory extends RecyclerView.Adapter<AdapterSideMenuMainCategory.MyViewHolder> {

    private Context context;
    private List<LstnmainItem> productList;
    SideMenuListener listener;
    Boolean open=false;

    public AdapterSideMenuMainCategory(Context mContext, List<LstnmainItem> productList, SideMenuListener listener) {
        this.context = mContext;
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shopping_manu_main_category, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tv_category.setText(productList.get(position).getMainCategory());
//        holder.itemView.setOnClickListener(v -> listener.openCategory(productList.get(position).getMainCategoryDetails()));
//        Log.e("JHVHf",""+productList.get(position).getMainCategoryDetails().size());
        Log.e("JHVHf",""+productList.get(position).getImage());
//        AdapterSiemenuCategory siemenuCategory = new AdapterSiemenuCategory(context, productList.get(position).getMainCategoryDetails());
//        holder.rv_category.setAdapter(siemenuCategory);
        Glide.with(context).load(productList.get(position).getImage())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.img_category);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(open){
                    open=false;
                    holder.rv_category.setVisibility(View.GONE);

                }else{
                    open=true;
                    holder.rv_category.setVisibility(View.VISIBLE);
                    AdapterSiemenuCategory siemenuCategory = new AdapterSiemenuCategory(context, productList.get(position).getMainCategoryDetails());
                    holder.rv_category.setAdapter(siemenuCategory);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_category)
        TextView tv_category;
        @BindView(R.id.img_category)
        ImageView img_category;
         @BindView(R.id.rv_category)
        RecyclerView rv_category;


        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}