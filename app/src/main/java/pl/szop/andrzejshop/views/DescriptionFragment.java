package pl.szop.andrzejshop.views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.szop.andrzejshop.R;
import pl.szop.andrzejshop.models.Product;
import pl.szop.andrzejshop.utils.ResourceUtils;

public class DescriptionFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private TextView cDescriptionView;
    private Product mProduct;
    private View cView;

    public DescriptionFragment() {
        // Required empty public constructor
    }

    public void setProduct(Product product){
        mProduct = product;
        setValues();
    }

    private void setValues(){
        ViewGroup viewGroup = (ViewGroup) cView;
        for(int i=0; i<viewGroup.getChildCount(); i++){
            View view = viewGroup.getChildAt(i);
            String resourceName = ResourceUtils.getViewName(view);
            ((TextView)view).setText(resourceName);
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DescriptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DescriptionFragment newInstance(String param1, String param2) {
        DescriptionFragment fragment = new DescriptionFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_description, container, false);
        cDescriptionView = view.findViewById(R.id.description);
        cView = view;
        if (mProduct != null){
            setValues();
        }
        return view;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
