package pl.szop.andrzejshop.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

import pl.szop.andrzejshop.MyApplication;
import pl.szop.andrzejshop.R;
import pl.szop.andrzejshop.adapters.CategoryAdapter;
import pl.szop.andrzejshop.data.criteria.Criteria;
import pl.szop.andrzejshop.data.criteria.Filter;
import pl.szop.andrzejshop.models.Category;

public class FilterActivity extends AppCompatActivity {

    private Spinner cCategorySpinner;
    private Button cFilterButton;

    private Criteria mCurrentCriteria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initComponents();

        Intent intent = getIntent();
        mCurrentCriteria = intent.getParcelableExtra("criteria");
    }

    private void initComponents(){
        cCategorySpinner = findViewById(R.id.category_spinner);
        cFilterButton = findViewById(R.id.filter_button);
        cFilterButton.setOnClickListener(v->filter());

        fillCategories();
    }

    private void fillCategories(){
        List<Category> categories = MyApplication.instance().getDataProvider().getCategories();
        categories.set(0, null);
        CategoryAdapter adapter = new CategoryAdapter(categories, this, R.layout.item_filter_category);
        cCategorySpinner.setAdapter(adapter);
    }

    private void filter(){
        Intent intent = new Intent();
        intent.putExtra("result", getCriteria());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private Criteria getCriteria(){
        Criteria criteria = mCurrentCriteria;
        Long categoryId = cCategorySpinner.getSelectedItem() != null ? ((Category)cCategorySpinner.getSelectedItem()).getId() : null;
        Filter filter = new Filter("category", categoryId, Filter.Option.EQUAL);
        criteria.setFilter("category", filter);

        return criteria;
    }



}
