package pl.szop.andrzejshop.actions;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.InvocationTargetException;

import pl.szop.andrzejshop.MyApplication;
import pl.szop.andrzejshop.events.RefreshFavoritesEvent;
import pl.szop.andrzejshop.models.Product;

public class AddToFavoritesAction implements Action {

    public static final String NAME = "ADD_TO_FAVORITES";

    @Override
    public void execute(Object object, Context context) {
        Long id = ((Product)object).getId();
        // TODO spróbować to zrobić bez pobierania
        boolean favorites = MyApplication.instance().getDataProvider().isFavorite(id);
        MyApplication.instance().getDataProvider().setFavorite(id, !favorites);
        EventBus.getDefault().post(new RefreshFavoritesEvent());
    }
}
