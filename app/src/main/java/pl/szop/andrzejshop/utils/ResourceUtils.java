package pl.szop.andrzejshop.utils;

import android.view.View;

public class ResourceUtils {

    public static String getViewName(View view){
        String resourceName = view.getContext().getResources().getResourceName(view.getId());
        int splashIndex = resourceName.indexOf('/');
        return resourceName.substring(splashIndex + 1);
    }
}
