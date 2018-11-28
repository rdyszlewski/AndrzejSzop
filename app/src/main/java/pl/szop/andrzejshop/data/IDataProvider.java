package pl.szop.andrzejshop.data;

import java.util.List;

import pl.szop.andrzejshop.data.criteria.Criteria;
import pl.szop.andrzejshop.models.Image;
import pl.szop.andrzejshop.models.Product;

public interface IDataProvider {

    List<? extends Product> getProducts(Criteria filter);
    List<? extends Product> getProducts();
    Product getDetails(Long id);
    List<Image> getImages(Long productId);
    boolean isFavorite(Long id);
    void setFavorite(Long id, boolean favorite);
    List<? extends Product> getFavorites();
}
