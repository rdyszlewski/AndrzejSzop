package pl.szop.andrzejshop.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import pl.szop.andrzejshop.R;
import pl.szop.andrzejshop.models.Image;

public class ImageAdapter extends PagerAdapter {

    public interface ClickListener{
        void onClick(int position);
    }

    private List<Image> mImages;
    private Context mContext;

    private ClickListener mClickListener;

    public ImageAdapter(Context context, List<Image> images){
        mContext = context;
        mImages = images;
    }

    public void setClickListener(ClickListener clickListener){
        mClickListener = clickListener;
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position){
        Image image = mImages.get(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_image, collection, false);
        ImageView imageView = view.findViewById(R.id.product_item);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image.getImage(), 0, image.getImage().length);
        imageView.setImageBitmap(bitmap);
        collection.addView(view, 0);
        if(mClickListener != null){
            imageView.setOnClickListener(v -> mClickListener.onClick(position));
        }
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((View)object);
    }
}
