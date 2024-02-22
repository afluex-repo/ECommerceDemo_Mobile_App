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

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterShoppingOrders extends RecyclerView.Adapter<AdapterShoppingOrders.MyViewHolder> {
    private Context context;
    private List<OrderDetailsItem> productList;
    CancelOrderListener listener;

    public AdapterShoppingOrders(Context mContext, List<OrderDetailsItem> productList, CancelOrderListener listener) {
        this.context = mContext;
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_orders, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvOrderId.setText(productList.get(position).getOrderNo());
        holder.tvOrderAmt.setText(String.format("₹ %s", productList.get(position).getOrderAmount()));
        holder.tvOrderDate.setText(productList.get(position).getOrderDate());

        AdapterOrderProducts adapterSubCategory = new AdapterOrderProducts(context, productList.get(position).getOrderSummary(), listener);
        holder.rvProducts.setAdapter(adapterSubCategory);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_order_id)
        TextView tvOrderId;
        @BindView(R.id.tv_order_date)
        TextView tvOrderDate;
        @BindView(R.id.tv_order_amt)
        TextView tvOrderAmt;
        @BindView(R.id.rv_products)
        RecyclerView rvProducts;


        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            rvProducts.setLayoutManager(new LinearLayoutManager(context));
        }
    }

}
