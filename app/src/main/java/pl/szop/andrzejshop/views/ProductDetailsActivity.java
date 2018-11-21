package pl.szop.andrzejshop.views;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import pl.szop.andrzejshop.MyApplication;
import pl.szop.andrzejshop.R;
import pl.szop.andrzejshop.actions.AddToCartAction;
import pl.szop.andrzejshop.actions.AddToFavoritesAction;
import pl.szop.andrzejshop.adapters.ViewAdapter;
import pl.szop.andrzejshop.models.BookDetails;
import pl.szop.andrzejshop.models.Image;
import pl.szop.andrzejshop.rules.FavoritesRule;

public class ProductDetailsActivity extends AppCompatActivity implements ImagesFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        View view = findViewById(R.id.content);
        Intent intent = getIntent();
        Long productId = intent.getLongExtra("id", 0L);

        BookDetails details = (BookDetails) MyApplication.instance().getDataProvider().getDetails(productId);
        ViewAdapter viewAdapter = new ViewAdapter();
        viewAdapter.addAction(R.id.buy_button, AddToCartAction.NAME);
        viewAdapter.addAction(R.id.favorites, AddToFavoritesAction.NAME);
        viewAdapter.addRule(R.id.favorites, FavoritesRule.NAME, false);
        viewAdapter.bindView(view, details, true);
        ImagesFragment imagesFragment = (ImagesFragment) getSupportFragmentManager().findFragmentById(R.id.images_fragment);
        imagesFragment.setImages(details.getId(), null);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
