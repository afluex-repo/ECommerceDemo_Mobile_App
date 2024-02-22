package afluex.mlm.demo;

import android.annotation.SuppressLint;
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

public class AdapterSideMenuCategoryImage extends RecyclerView.Adapter<AdapterSideMenuCategoryImage.MyViewHolder> {

    private Context context;
    private List<LstnmainItem> productList;
    SideMenuListener listener;

    public AdapterSideMenuCategoryImage(Context mContext, List<LstnmainItem> productList, SideMenuListener listener) {
        this.context = mContext;
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shopping_menu_image, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(productList.get(position).getImage())
                .placeholder(R.drawable.ic_user_bg)
                .error(R.drawable.ic_user_bg)
                .into(holder.imgCategory);

        holder.itemView.setOnClickListener(v -> listener.openCategory(productList.get(position).getMainCategoryDetails()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_category)
        ImageView imgCategory;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}