package afluex.mlm.demo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;


import afluex.mlm.demo.app.TouchImageView;
import afluex.mlm.demo.responseProductDetails.LstimagesItem;


public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {

    // list for storing urls of images.
    private Context context;
    private final List<LstimagesItem> mSliderItems;

    public SliderAdapter(Context context, List<LstimagesItem> mSliderItems) {
        this.context = context;
        this.mSliderItems = mSliderItems;
    }


    // Constructor


    // We are inflating the slider_layout
    // inside on Create View Holder method.
    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, null);
        return new SliderAdapterViewHolder(inflate);
    }

    // Inside on bind view holder we will
    // set data to item of Slider View.
    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {

        final LstimagesItem sliderItem = mSliderItems.get(position);
        Glide.with(context).load(sliderItem.getImagePath())
                .into(viewHolder.imageViewBackground);

        viewHolder.imageViewBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent("zoom_image");

                intent.putExtra("image_link",sliderItem.getImagePath());
                intent.putExtra("position",position);




//                    intent.putExtra("position1", holder.getAdapterPosition());
//                    intent.putExtra("workerDeletedType", workerType);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

            }
        });


//        PhotoViewAttacher pAttacher;
//        pAttacher = new PhotoViewAttacher(viewHolder.imageViewBackground);
//        pAttacher.update();





    }

    // this method will return
    // the count of our list.
    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {
        // Adapter class for initializing
        // the views of our slider view.
        ImageView imageViewBackground;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.myimage);
        }
    }
}