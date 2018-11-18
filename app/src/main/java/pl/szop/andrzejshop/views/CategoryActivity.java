package pl.szop.andrzejshop.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pl.szop.andrzejshop.R;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Button btnCriminal = findViewById(R.id.button_criminal);

        btnCriminal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProductsListActivity("category");
            }
        });

        Button btnFantasy = findViewById(R.id.button_fantasy);

        btnFantasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProductsListActivity("fantastyka");
            }
        });

        Button btnThriller = findViewById(R.id.button_thriller);

        btnThriller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProductsListActivity("thriller");
            }
        });

        Button btnSciFi = findViewById(R.id.button_scifi);

        btnSciFi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProductsListActivity("Science fiction");
            }
        });

    }
    private void startProductsListActivity(String cat) {
        Intent intent = new Intent(this, ProductsActivity.class);
        intent.putExtra("categoryType", cat);
        startActivity(intent);
    }
}
