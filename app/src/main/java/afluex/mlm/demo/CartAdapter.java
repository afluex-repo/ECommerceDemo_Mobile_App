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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private Context context;
    private List<CartDetailsItem> productList;
    CartIncDecListener listener;

    public CartAdapter(Context mContext, List<CartDetailsItem> productList, CartIncDecListener listener) {
        this.context = mContext;
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cart, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load("https://shoppingportal.afluex.com/" + (productList.get(position).getImagePath()).replace("..", "")).into(holder.imgProduct);
        holder.tvProductName.setText(productList.get(position).getProductName());
        holder.tvProductInfo.setText(productList.get(position).getProductInfo());
        holder.tvSeller.setText(String.format("%s (Seller)", productList.get(position).getVendorName()));
        holder.tvPrice.setText(String.format("₹ %s", productList.get(position).getSubTotal()));
        holder.tvDelivery.setText(String.format("Delivery Charges : ₹ %s", productList.get(position).getDeliveryCharge()));
        holder.number_button.setNumber(productList.get(position).getQuantity());

        holder.number_button.setOnValueChangeListener((view, oldValue, newValue) -> {
            if (oldValue < newValue)
                listener.getCardItemQuantity(productList.get(position).getProductInfoCode(), productList.get(position).getVendorId(), "Add");
            else
                listener.getCardItemQuantity(productList.get(position).getProductInfoCode(), productList.get(position).getVendorId(), "Subtract");
            Log.d("Number", String.format("oldValue: %d   newValue: %d", oldValue, newValue));
        });

        if (Float.parseFloat(productList.get(position).getRedeemPrice()) > 0) {
            holder.tv_redeem.setVisibility(View.VISIBLE);
            holder.tv_redeem.setText("Redeem: " + productList.get(position).getRedeemPrice() + "Points");
        } else holder.tv_redeem.setVisibility(View.GONE);

        holder.tvRemove.setOnClickListener(view -> {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
            builder1.setTitle("Remove Item");
            builder1.setMessage("Do you really want to Remove this item from your cart?");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Yes",
                    (dialog, id) -> {
                        listener.removeCartItem(productList.get(position).getPKCartID());
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
        @BindView(R.id.tv_seller)
        TextView tvSeller;
        @BindView(R.id.tv_delivery)
        TextView tvDelivery;
        @BindView(R.id.tv_remove)
        TextView tvRemove;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_redeem)
        TextView tv_redeem;
        @BindView(R.id.number_button)
        ElegantNumberButton number_button;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}