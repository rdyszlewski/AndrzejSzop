package pl.szop.andrzejshop.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pl.szop.andrzejshop.models.Image;
import pl.szop.andrzejshop.models.Product;

public class ViewAdapter {

    public static void bindView(View view, Product product) {
        if(hasChildrenViews(view)){
            ViewGroup viewGroup = (ViewGroup) view;
            View childView;
            for(int i =0; i <viewGroup.getChildCount(); i++){
                childView = viewGroup.getChildAt(i);
                bindView(childView, product);
            }
        } else {
            String viewName = getResourceName(view);
            try{
                Object value = product.getValue(viewName);
                setValue(view, value);
            } catch (Exception ignored){
                // method getValue can generate exception when not found correct method in object
            }
        }
    }

    private static boolean hasChildrenViews(View view) {
        return view instanceof ViewGroup;
    }

    private static void setValue(View view, Object value){
        if(view == null){
            return;
        }

        if (view instanceof TextView){
            setText(value, (TextView) view);
        } else if (view instanceof ImageView){
            setImage(value, (ImageView) view);
        } else if (view instanceof ViewPager){
//            ImageAdapter adapter = new ImageAdapter(view.getContext(), (List<Image>) value);
//            ((ViewPager)view).setAdapter(adapter);
        } else {
            System.out.println();
        }
        // TODO serve another types
    }

    private static void setImage(Object value, ImageView view) {
        if(value instanceof byte[]){
            byte[] data = (byte[]) value;
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            view.setImageBitmap(bitmap);
        }else if(value instanceof Integer){
            view.setImageResource((Integer) value);
        } else if (value instanceof Bitmap){
            view.setImageBitmap((Bitmap) value);
        }
    }

    private static void setText(Object value, TextView view) {
        if(value instanceof String){
            view.setText((String)value);
        } else if(value instanceof Double) {
            view.setText(String.valueOf(value));
        }
    }

    private static String getResourceName(View view){
        String resourceName = view.getContext().getResources().getResourceName(view.getId());
        int splashIndex = resourceName.indexOf('/');
        return resourceName.substring(splashIndex+1);
    }
}
