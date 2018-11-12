package pl.szop.andrzejshop.views;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import pl.szop.andrzejshop.data.Filter;
import pl.szop.andrzejshop.R;

public class ProductsActivity extends AppCompatActivity implements ProductsListFragment.OnFragmentInteractionListener {

    private Button cFilterButton;
    private Button cSortButton;
    private ImageView cChangeViewButton;

    private Toolbar cToolbar;

    private EditText cSearchEditText;
    private ImageButton cSearchButton;

    private ProductsListFragment mFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);
//        createActionBar();

        initComponents();
        loadFragment(new ProductsListFragment());
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

        cChangeViewButton.setOnClickListener(v -> mFragment.changeListLayout());
        // TODO add action to the buttons
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
//        final SearchView searchView = (SearchView) menu.findItem(R.id.search_action);

        // TODO dodać wyszukiwanie. Może zrobić jeszcze jakąś liste z podpowiedziami
        MenuItem searchItem = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Filter filter = new Filter();
                filter.setText(query);
//                mFragment.filer(filter);
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
}
