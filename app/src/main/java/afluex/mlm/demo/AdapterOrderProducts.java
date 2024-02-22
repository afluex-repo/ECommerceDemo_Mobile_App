package afluex.mlm.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterOrderProducts extends RecyclerView.Adapter<AdapterOrderProducts.MyViewHolder> {

    private Context context;
    private List<OrderSummaryItem> productList;
    CancelOrderListener listener;

    public AdapterOrderProducts(Context mContext, List<OrderSummaryItem> productList, CancelOrderListener listener) {
        this.context = mContext;
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_order_products, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(productList.get(position).getImagePath())
                .into(holder.imgProduct);
        holder.tvProductName.setText(productList.get(position).getProductName());
        holder.tvProductInfo.setText(productList.get(position).getProductInfo().trim());
        holder.tvQuantity.setText(String.format("%s (Quantity)", productList.get(position).getQuantity()));
        holder.tvPrice.setText(String.format("₹ %s", productList.get(position).getAmount()));
        holder.tvDelivery.setText(String.format("Delivered By : %s", productList.get(position).getExpectedDelivery()));
        holder.tvStatus.setText(productList.get(position).getOrderStatus());
        holder.tv_delivery_chrg.setText(String.format("Delivery: ₹%s", productList.get(position).getDeliveryCharge()));

        if (Float.parseFloat(productList.get(position).getRedeemPrice()) > 0) {
            holder.tv_redeem.setVisibility(View.VISIBLE);
            holder.tv_redeem.setText("Redeemed " + productList.get(position).getRedeemPrice() + " Points");
        } else holder.tv_redeem.setVisibility(View.GONE);

        if (productList.get(position).getOrderStatus().equalsIgnoreCase("Placed"))
            holder.tvRemove.setVisibility(View.VISIBLE);
        else holder.tvRemove.setVisibility(View.GONE);

        holder.tvRemove.setOnClickListener(view -> {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
            builder1.setTitle("Cancel Order");
            builder1.setMessage("Do you really want to Cancel this order?");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Yes",
                    (dialog, id) -> {
                        listener.cancelOrder(productList.get(position).getPK_OrderDetailsID());
                        dialog.cancel();
                    });
            builder1.setNegativeButton("No", (dialog, id) -> dialog.cancel());
            AlertDialog alert11 = builder1.create();
            alert11.show();
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_product)
        ImageView imgProduct;
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.tv_product_info)
        TextView tvProductInfo;
        @BindView(R.id.tv_quantity)
        TextView tvQuantity;
        @BindView(R.id.tv_delivery)
        TextView tvDelivery;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_remove)
        TextView tvRemove;
        @BindView(R.id.tv_redeem)
        TextView tv_redeem;
        @BindView(R.id.tv_delivery_chrg)
        TextView tv_delivery_chrg;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}

