package pl.szop.andrzejshop.data.database;

import pl.szop.andrzejshop.models.Book;
import pl.szop.andrzejshop.models.BookDetails;

public class ProductDAOSettings {

    public static String[] SEARCH_COLUMN  = {"title", "author"};
    public static Class PRODUCT_CLASS = Book.class;
    public static Class PRODUCT_DETAILS_CLASS = BookDetails.class;
}
