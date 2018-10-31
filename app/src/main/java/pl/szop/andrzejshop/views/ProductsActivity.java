package pl.szop.andrzejshop.views;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import pl.szop.andrzejshop.R;

public class ProductsActivity extends AppCompatActivity implements ProductsListFragment.OnFragmentInteractionListener {

    private Button cFilterButton;
    private Button cSortButton;
    private ImageView cChangeViewButton;
    private FrameLayout cFragmentContainer;

    private ProductsListFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);

        initComponents();
        loadFragment(new ProductsListFragment());
    }

    private void initComponents(){
        cFilterButton = findViewById(R.id.filter_button);
        cSortButton = findViewById(R.id.sort_button);
        cChangeViewButton = findViewById(R.id.change_view_button);
        cFragmentContainer = findViewById(R.id.products_container);

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
    public void onFragmentInteraction(Uri uri) {
        // TODO
    }
}
