package pl.szop.andrzejshop.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import pl.szop.andrzejshop.data.Filter;
import pl.szop.andrzejshop.MyApplication;
import pl.szop.andrzejshop.R;
import pl.szop.andrzejshop.actions.AddToCartAction;
import pl.szop.andrzejshop.adapters.ProductsAdapter;
import pl.szop.andrzejshop.data.database.ProductDAO;
import pl.szop.andrzejshop.models.Book;
import pl.szop.andrzejshop.models.Product;
import pl.szop.andrzejshop.rules.BoughtRule;


public class ProductsListFragment extends Fragment {

    private RecyclerView cProductsList;
    private OnFragmentInteractionListener mListener;
    private ProductsAdapter mAdapter;

    public ProductsListFragment() {
        // Required empty public constructor
        // Register EventBus methods
        EventBus.getDefault().register(this);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductsListFragment newInstance(String param1, String param2) {
        ProductsListFragment fragment = new ProductsListFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_products_list, container, false);
        prepareProductsList(view);
        return view;
    }

    private void prepareProductsList(View view) {
        final int LIST_RESOURCE = R.id.products_list;
        final int ITEM_LAYOUT = R.layout.item_product_list;
        RecyclerView productList = view.findViewById(LIST_RESOURCE);

        ProductsAdapter adapter = createProductsAdapter(ITEM_LAYOUT);
        productList.setAdapter(adapter);
        setListLayout(productList);

        cProductsList = productList;
        mAdapter = adapter;
        loadProducts(null); //load all products
    }

    @NonNull
    private ProductsAdapter createProductsAdapter(int ITEM_LAYOUT) {
//        List<? extends Product> products = loadProducts(null);
        List<? extends Product> products = new ArrayList<>();
        ProductsAdapter adapter = new ProductsAdapter(getActivity(), ITEM_LAYOUT, products, productId->startDetailsActivity(productId));
        addActions(adapter);
        addRules(adapter);
        return adapter;
    }

    public  void loadProducts(Filter filter) {
        List<? extends Product> products =  MyApplication.instance().getDataProvider().getProducts(filter);
        mAdapter.setItems(products);
    }

    private void addActions(ProductsAdapter adapter) {
        adapter.addAction(R.id.buy_button, AddToCartAction.NAME);
    }

    private void addRules(ProductsAdapter adapter){
        adapter.addRule(R.id.buy_button, BoughtRule.NAME, false);
        adapter.addRule(R.id.price, BoughtRule.NAME, false);
        adapter.addRule(R.id.buyed, BoughtRule.NAME, true);
    }

    private void startDetailsActivity(Long productId){
        Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
        intent.putExtra("id", productId);
        startActivity(intent);
    }

    private void setListLayout(RecyclerView productList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        productList.setLayoutManager(layoutManager);
        productList.setItemAnimator(new DefaultItemAnimator());
    }

    public void changeListLayout(){
        RecyclerView.LayoutManager layoutManager;
        if (cProductsList.getLayoutManager() instanceof GridLayoutManager){
            layoutManager = new LinearLayoutManager(getActivity());
            mAdapter.setResource(R.layout.item_product_list);
        } else {
            layoutManager = new GridLayoutManager(getActivity(), 2);
            mAdapter.setResource(R.layout.item_product_grid);

        }
        cProductsList.setLayoutManager(layoutManager);
        cProductsList.setAdapter(mAdapter);
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void filter(Filter filter){
        if(filter.getText() != null){
            List<? extends Product> products = MyApplication.instance().getDataProvider().getProducts(filter);
            mAdapter.setItems(products);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    // TODO: delete this
    @Subscribe
    public void testEventBus(TestEvent testEvent){
        Toast.makeText(getActivity(), "Testowanie EventBus", Toast.LENGTH_SHORT).show();
    }

    public static class TestEvent{

    }
}
