package pl.szop.andrzejshop.views;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.szop.andrzejshop.adapters.SortingAdapter;
import pl.szop.andrzejshop.data.criteria.Criteria;
import pl.szop.andrzejshop.R;
import pl.szop.andrzejshop.data.criteria.Sort;

public class ProductsActivity extends AppCompatActivity implements ProductsListFragment.OnFragmentInteractionListener {

    private final int FILTER_REQUEST = 1;

    private Button cFilterButton;
    private Button cSortButton;
    private ImageView cChangeViewButton;

    private Toolbar cToolbar;

    private EditText cSearchEditText;
    private ImageButton cSearchButton;

    private ProductsListFragment mFragment;

//    private Button btnCat;
    private Button cFavoritesButton;
    private String category = "";

    private Criteria mCurrentCriteria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentCriteria = new Criteria();
        setContentView(R.layout.activity_products_list);
//        createActionBar();
        Bundle b = getIntent().getExtras();
        String cat = null;
        if (b != null) {
            cat = b.getString("categoryType");
        }
        TextView myButton = (TextView) findViewById(R.id.categoryText);
        if (cat != null) {
            category = cat;
            myButton.setVisibility(View.VISIBLE);
            myButton.setText("Category: " + cat);
        } else {
            myButton.setVisibility(View.GONE);
        }

//        btnCat  = findViewById(R.id.cat_button);
//        btnCat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                gotToCategoryActivity();
//            }
//        });
        initComponents();

        Bundle bundle = new Bundle();
        bundle.putString("categoryName", category);
        ProductsListFragment prodList = new ProductsListFragment();
        prodList.setArguments(bundle);
        loadFragment(prodList);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mFragment.loadProducts(mCurrentCriteria);
    }
    private void gotToCategoryActivity() {
        Intent intent = new Intent(ProductsActivity.this, CategoryActivity.class);
        startActivity(intent);
    }

    private void createActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.action_bar);
        cSearchEditText = actionBar.getCustomView().findViewById(R.id.search_edit_text);
        cSearchButton = actionBar.getCustomView().findViewById(R.id.search_button);
    }

    private void initComponents(){
        cFilterButton = findViewById(R.id.filter_button);
        cSortButton = findViewById(R.id.sort_button);
        cChangeViewButton = findViewById(R.id.change_view_button);
        cFavoritesButton = findViewById(R.id.favorites_button);

        cFilterButton.setOnClickListener(v->startFilterActivity());
        cSortButton.setOnClickListener(v -> openSortingDialog());
        cChangeViewButton.setOnClickListener(v -> mFragment.changeListLayout());
        cFavoritesButton.setOnClickListener(v -> startFavoritesActivity());
    }

    private void startFilterActivity() {
        Intent intent = new Intent(this, FilterActivity.class);
        intent.putExtra("criteria", mCurrentCriteria);
        startActivityForResult(intent, FILTER_REQUEST);
    }

    private void startFavoritesActivity() {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }

    private void openSortingDialog(){
        List<Sort> sortingOptions = getSortingOptions();
        int currentSortingOptions = getCurrentSortingOptions(sortingOptions, mCurrentCriteria);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.sorting));
        builder.setSingleChoiceItems(new SortingAdapter(this, sortingOptions, android.R.layout.select_dialog_singlechoice), currentSortingOptions, (dialog, which) -> {
            Criteria filter = mCurrentCriteria;
            filter.setSorting(sortingOptions.get(which));
            mFragment.loadProducts(filter);
            dialog.dismiss();
        });
        builder.create().show();
    }

    private int getCurrentSortingOptions(List<Sort> sortingOptions, Criteria currentFilter){
        Sort currentSort = currentFilter.getSort();
        Sort sort;
        for(int i=0; i<sortingOptions.size(); i++){
            sort = sortingOptions.get(i);
            if(currentSort == null && sort == null){
                return i;
            } else if (currentSort == null || sort == null){
                continue;
            }
            if(currentSort.equals(sort)){
                return i;
            }
        }
        throw new IllegalArgumentException();
    }

    @NonNull
    private List<Sort> getSortingOptions() {
        List<Sort> sortingOptions = new ArrayList<>();
        sortingOptions.add(null);
        sortingOptions.add(new Sort("title", false));
        sortingOptions.add(new Sort("title", true));
        sortingOptions.add(new Sort("author", false));
        sortingOptions.add(new Sort("author", true));
        sortingOptions.add(new Sort("price", false));
        sortingOptions.add(new Sort("price", true));
        return sortingOptions;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.products_container, fragment);
        fragmentTransaction.commit();
        mFragment = (ProductsListFragment) fragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        // TODO dodać wyszukiwanie. Może zrobić jeszcze jakąś liste z podpowiedziami
        MenuItem searchItem = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Criteria filter = mCurrentCriteria;
                filter.setText(query);
                mFragment.loadProducts(filter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // TODO
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == FILTER_REQUEST) {
            if(resultCode == Activity.RESULT_OK){
                Criteria criteria = data.getParcelableExtra("result");
                mCurrentCriteria = criteria;
                mFragment.filter(criteria);
            }
        }
    }
}
