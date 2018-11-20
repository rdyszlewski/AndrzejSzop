package pl.szop.andrzejshop.actions;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;

import pl.szop.andrzejshop.MyApplication;
import pl.szop.andrzejshop.models.Product;

public class RemoveFromFavoritesAction implements Action{

    public static final String NAME = "REMOVE_FROM_FAVORITES";


    @Override
    public void execute(Object object, Context context) {
        try {
            Long id = (Long) ((Product)object).getValue("id");
            MyApplication.instance().getDataProvider().setFavorite(id, false);
            // TODO zrobić odświeżanie listy po wykonaiu akcji. Prawdopodobnie będzie trzeba zmienić trochę interfejs, aby mieć dostęp do listy
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
