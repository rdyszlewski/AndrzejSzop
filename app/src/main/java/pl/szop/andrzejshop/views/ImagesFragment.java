package pl.szop.andrzejshop.views;

import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import java.util.ArrayList;
import java.util.List;

import pl.szop.andrzejshop.MyApplication;
import pl.szop.andrzejshop.R;
import pl.szop.andrzejshop.adapters.ImageAdapter;

import pl.szop.andrzejshop.models.Image;

public class ImagesFragment extends Fragment {

    private ViewPager cViewPager;
    private CirclePageIndicator cIndicator;
    private OnFragmentInteractionListener mListener;

    public ImagesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_images, container, false);
        cViewPager = view.findViewById(R.id.images);
        cIndicator = view.findViewById(R.id.indicator);
        return view;

    }

    public void setImages(Long productId, Integer position){
        List<Image> images = MyApplication.instance().getDataProvider().getImages(productId);
        if(images == null){
            return;
        }
        ImageAdapter adapter = new ImageAdapter(getContext(),images);
        adapter.setClickListener(index-> startImageActivity(productId, index));

        cViewPager.setAdapter(adapter);
        cIndicator.setViewPager(cViewPager);
        if(position != null){
            cViewPager.setCurrentItem(position);
        }

    }

    private void startImageActivity(Long productId, int index) {
        Intent intent = new Intent(getContext(), ImageActivity.class);
        intent.putExtra("position", index);
        intent.putExtra("product", productId);
        startActivity(intent);
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
}
