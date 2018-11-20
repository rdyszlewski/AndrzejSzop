package pl.szop.andrzejshop.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import pl.szop.andrzejshop.MyApplication;
import pl.szop.andrzejshop.R;
import pl.szop.andrzejshop.actions.RemoveFromFavoritesAction;
import pl.szop.andrzejshop.adapters.ProductsAdapter;
import pl.szop.andrzejshop.models.Book;
import pl.szop.andrzejshop.models.Favorites;
import pl.szop.andrzejshop.models.Product;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView cFavoritesList;
    private ProductsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initComponents();
    }

    private void initComponents(){
        cFavoritesList = findViewById(R.id.favorites_list);
        List<Favorites> favorites = (List<Favorites>) MyApplication.instance().getDataProvider().getFavorites();
        List<Book> products = new ArrayList<>();
        // TODO wymyślić jakiś łatwiejszy sposób na to
        for (Favorites fav : favorites){
            products.add(fav.getBook());
        }
        ProductsAdapter adapter = new ProductsAdapter(this, R.layout.item_favorites, new ArrayList<>(), productId -> {}); // TODO wstawić jakiegoś słuchacza
        adapter.addAction(R.id.remove_button, RemoveFromFavoritesAction.NAME);
        cFavoritesList.setAdapter(adapter);
        setListLayout(cFavoritesList);
        mAdapter = adapter;
        mAdapter.setItems(products);
        // TODO wstawić jakąś akcję, do usuwania elementów z listy
    }

    private void setListLayout(RecyclerView productList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        productList.setLayoutManager(layoutManager);
        productList.setItemAnimator(new DefaultItemAnimator());
    }

}
