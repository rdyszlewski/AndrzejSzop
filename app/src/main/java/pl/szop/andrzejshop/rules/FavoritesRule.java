package pl.szop.andrzejshop.rules;

import pl.szop.andrzejshop.MyApplication;
import pl.szop.andrzejshop.models.Book;

public class FavoritesRule implements Rule {
    public static final String NAME = "FAVORITE";

    @Override
    public Action getAction() {
        return Action.CHECKING;
    }

    @Override
    public boolean check(Object... objects) {
        Book book = (Book) objects[0];
        Long id = book.getId();
        boolean isFavorite = MyApplication.instance().getDataProvider().isFavorite(id);
        return isFavorite;
    }

    @Override
    public boolean isNegative() {
        return false;
    }
}
