package pl.szop.andrzejshop.events;

import pl.szop.andrzejshop.models.Favorites;
import pl.szop.andrzejshop.models.Product;

public class RefreshFavoritesEvent {

    private Product mProduct;

    public RefreshFavoritesEvent(Product product){
        mProduct = product;
    }

    public Product getProduct() {
        return mProduct;
    }
}
