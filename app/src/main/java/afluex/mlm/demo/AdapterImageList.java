package afluex.mlm.demo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import afluex.mlm.demo.responseProductDetails.LstimagesItem;

public class AdapterImageList extends RecyclerView.Adapter<AdapterImageList.HolderImageList> {

    private Context context;
    private final List<LstimagesItem> mSliderItems;
    int position_global;

    public AdapterImageList(Context context, List<LstimagesItem> mSliderItems, int position_global) {
        this.context = context;
        this.mSliderItems = mSliderItems;
        this.position_global = position_global;
    }

    @NonNull
    @Override
    public AdapterImageList.HolderImageList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_list_image,parent,false);
        return new AdapterImageList.HolderImageList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterImageList.HolderImageList holder, int position) {
        final LstimagesItem sliderItem = mSliderItems.get(position);
        Glide.with(context).load(sliderItem.getImagePath())
                .into(holder.imageView);

        if (position==position_global){
            holder.ll_main.setBackground(context.getDrawable(R.drawable.shape_rectangle_black));
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent("zoom_image");

                intent.putExtra("image_link",sliderItem.getImagePath());
                intent.putExtra("position",position);
                intent.putExtra("type","adapter");




//                    intent.putExtra("position1", holder.getAdapterPosition());
//                    intent.putExtra("workerDeletedType", workerType);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mSliderItems.size();
    }

    public class HolderImageList extends RecyclerView.ViewHolder {

        LinearLayout ll_main;
        ImageView imageView;
        public HolderImageList(@NonNull View itemView) {
            super(itemView);
            ll_main=itemView.findViewById(R.id.ll_main);
            imageView=itemView.findViewById(R.id.image_view);
        }
    }
}
