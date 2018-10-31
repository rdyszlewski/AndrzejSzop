package pl.szop.andrzejshop.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class ImageUtils {


    public static byte[] getBytesFromResource(Context context, int resource){
        android.graphics.Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),resource);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
