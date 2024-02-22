//package afluex.mlm.demo;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import com.bumptech.glide.Glide;
//
//import com.smarteist.autoimageslider.SliderViewAdapter;
//
//import java.util.List;
//
//import afluex.mlm.demo.responseProductDetails.LstimagesItem;
//
//public class AdapterProductImages extends
//        SliderViewAdapter<AdapterProductImages.SliderAdapterVH> {
//
//    private Context context;
//    private List<LstimagesItem> mSliderItems;
//
//    public AdapterProductImages(Context context, List<LstimagesItem> mSliderItems) {
//        this.context = context;
//        this.mSliderItems = mSliderItems;
//    }
//
//    @Override
//    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
//        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_product_images, null);
//        return new SliderAdapterVH(inflate);
//    }
//
//    @Override
//    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {
//        Glide.with(viewHolder.itemView)
//                .load(mSliderItems.get(position).getImagePath())
//                .fitCenter()
//                .into(viewHolder.img_banner);
//
//    }
//
//    @Override
//    public int getCount() {
//        //slider view count could be dynamic size
//        return mSliderItems.size();
//    }
//
//    class SliderAdapterVH extends ViewHolder {
//        ImageView img_banner;
//
//        public SliderAdapterVH(View itemView) {
//            super(itemView);
//            img_banner = itemView.findViewById(R.id.img_banner);
//        }
//    }
//
//}