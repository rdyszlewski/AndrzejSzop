package pl.szop.andrzejshop.actions;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.InvocationTargetException;

import pl.szop.andrzejshop.MyApplication;
import pl.szop.andrzejshop.events.RefreshFavoritesEvent;
import pl.szop.andrzejshop.models.Product;

public class RemoveFromFavoritesAction implements Action{

    public static final String NAME = "REMOVE_FROM_FAVORITES";


    @Override
    public void execute(Object object, Context context) {
        try {
            Long id = (Long) ((Product)object).getValue("id");
            MyApplication.instance().getDataProvider().setFavorite(id, false);
            EventBus.getDefault().post(new RefreshFavoritesEvent((Product) object));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
