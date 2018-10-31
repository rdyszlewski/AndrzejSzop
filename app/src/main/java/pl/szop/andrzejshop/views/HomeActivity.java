package pl.szop.andrzejshop.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pl.szop.andrzejshop.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // TODO: remove this. Temporary. Start another activity
        startProductsListActivity();
    }

    private void startProductsListActivity() {
        Intent intent = new Intent(this, ProductsActivity.class);
        startActivity(intent);
    }
}
