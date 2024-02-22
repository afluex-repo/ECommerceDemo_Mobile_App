package afluex.mlm.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {

    private Context context;
    private List<AddressItem> productList;

    public AddressAdapter(Context mContext, List<AddressItem> productList) {
        this.context = mContext;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_address, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvName.setText(productList.get(position).getName());
        holder.tvContact.setText(productList.get(position).getContatNo());
        holder.imgSelect.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_untick));
        holder.tvAddress.setText(String.format("%s, Near %s, %s, %s (%s)", productList.get(position).getHouseNo(), productList.get(position).getLocality(), productList.get(position).getLandMark(), productList.get(position).getPinCode(), productList.get(position).getAddressType()));

//        if (productList.get(position).getIsDefault().equalsIgnoreCase("True"))
//            holder.imgSelect.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_tick));
//        else
//            holder.imgSelect.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_untick));

        holder.imgSelect.setOnClickListener(view -> {
            AddressManager.selectedAddressId = productList.get(position).getPk_AddressId();
            notifyDataSetChanged();
            new Handler().postDelayed(() -> holder.imgSelect.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_tick)), 200);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_contact)
        TextView tvContact;
        @BindView(R.id.img_select)
        ImageView imgSelect;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}

