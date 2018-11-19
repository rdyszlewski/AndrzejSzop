package pl.szop.andrzejshop.utils;

import android.content.Context;
import android.view.View;

public class ResourceUtils {

    public static String getViewName(View view){
        String resourceName = view.getContext().getResources().getResourceName(view.getId());
        int splashIndex = resourceName.indexOf('/');
        return resourceName.substring(splashIndex + 1);
    }

    public static String getStringByName(String name, Context context){
        String packageName = context.getPackageName();
        int resId = context.getResources().getIdentifier(name, "string", packageName);
        return context.getString(resId);
    }
}
