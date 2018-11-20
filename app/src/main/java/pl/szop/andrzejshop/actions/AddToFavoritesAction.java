package pl.szop.andrzejshop.actions;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;

import pl.szop.andrzejshop.MyApplication;
import pl.szop.andrzejshop.models.Product;

public class AddToFavoritesAction implements Action {

    public static final String NAME = "ADD_TO_FAVORITES";

    @Override
    public void execute(Object object, Context context) {
        try {
            Long id = (Long) ((Product)object).getValue("id");
            // TODO spróbować to zrobić bez pobierania
            boolean favorites = MyApplication.instance().getDataProvider().isFavorite(id);
            MyApplication.instance().getDataProvider().setFavorite(id, !favorites);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
