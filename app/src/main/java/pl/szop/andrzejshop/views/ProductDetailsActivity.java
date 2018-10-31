package pl.szop.andrzejshop.views;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.lang.reflect.InvocationTargetException;

import pl.szop.andrzejshop.MyApplication;
import pl.szop.andrzejshop.R;
import pl.szop.andrzejshop.adapters.ViewAdapter;
import pl.szop.andrzejshop.models.BookDetails;

public class ProductDetailsActivity extends AppCompatActivity {

    private ViewPager cViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        View view = findViewById(R.id.content);
        BookDetails details = ((MyApplication)getApplication()).getDaoSession().getBookDetailsDao().loadDeep(1L);

        ViewAdapter.bindView(view, details);
    }

    private void initComponents(){
        cViewPager = findViewById(R.id.details_pager);

    }
}
