package pl.szop.andrzejshop.rules;

import java.lang.reflect.InvocationTargetException;

import pl.szop.andrzejshop.MyApplication;
import pl.szop.andrzejshop.models.Book;
import pl.szop.andrzejshop.models.Product;

public class FavoritesRule implements Rule {
    public static final String NAME = "FAVORITE";

    @Override
    public Action getAction() {
        return Action.CHECKING;
    }

    @Override
    public boolean check(Object... objects) {
        Product product = (Product) objects[0];
        Long id = product.getId();
        return MyApplication.instance().getDataProvider().isFavorite(id);
    }

    @Override
    public boolean isNegative() {
        return false;
    }
}
