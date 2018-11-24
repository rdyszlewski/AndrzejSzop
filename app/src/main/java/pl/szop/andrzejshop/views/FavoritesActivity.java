package pl.szop.andrzejshop.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import pl.szop.andrzejshop.MyApplication;
import pl.szop.andrzejshop.R;
import pl.szop.andrzejshop.actions.RemoveFromFavoritesAction;
import pl.szop.andrzejshop.adapters.ProductsAdapter;
import pl.szop.andrzejshop.events.RefreshFavoritesEvent;
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
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop(){
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void refreshEvent(RefreshFavoritesEvent event){
        Product product = event.getProduct();
        mAdapter.removeItem(product);
    }

    private void initComponents(){
        cFavoritesList = findViewById(R.id.favorites_list);
        List<? extends Product> favorites = MyApplication.instance().getDataProvider().getFavorites();

        ProductsAdapter adapter = new ProductsAdapter(this, R.layout.item_favorites, new ArrayList<>(), productId -> {}); // TODO wstawić jakiegoś słuchacza
        adapter.addAction(R.id.remove_button, RemoveFromFavoritesAction.NAME);
        cFavoritesList.setAdapter(adapter);
        setListLayout(cFavoritesList);
        mAdapter = adapter;
        mAdapter.setItems(favorites);
    }

    private void setListLayout(RecyclerView productList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        productList.setLayoutManager(layoutManager);
        productList.setItemAnimator(new DefaultItemAnimator());
    }

}
