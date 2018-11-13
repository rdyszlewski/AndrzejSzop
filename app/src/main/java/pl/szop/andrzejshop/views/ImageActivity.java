package pl.szop.andrzejshop.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import pl.szop.andrzejshop.R;


public class ImageActivity extends AppCompatActivity implements ImagesFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        Long product = intent.getLongExtra("product", 0L);

        ImagesFragment fragment = (ImagesFragment) getSupportFragmentManager().findFragmentById(R.id.images_fragment);
        fragment.setImages(product, position);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
