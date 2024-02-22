package afluex.mlm.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterSidemenuSubCategory extends RecyclerView.Adapter<AdapterSidemenuSubCategory.MyViewHolder> {

    private Context context;
    private List<LstsubCategoryItem> productList;

    public AdapterSidemenuSubCategory(Context mContext, List<LstsubCategoryItem> productList) {
        this.context = mContext;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shopping_sidemenu_subcategory, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.e("MLMEDF",""+productList.get(position).getSubCategoryName());
        holder.tvCategoryName.setText(productList.get(position).getSubCategoryName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("SubCategoryClicked",productList.get(position).getSubCategoryName());

                Intent intent = new Intent("site_position");

                intent.putExtra("subCategoryId",productList.get(position).getPKSubCategoryID());
                intent.putExtra("mainCategoryId",productList.get(position).getFKMainCategory());
                intent.putExtra("categoryId",productList.get(position).getFKCategoryID());
                intent.putExtra("name",productList.get(position).getSubCategoryName());




//                    intent.putExtra("position1", holder.getAdapterPosition());
//                    intent.putExtra("workerDeletedType", workerType);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_category_name)
        TextView tvCategoryName;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
