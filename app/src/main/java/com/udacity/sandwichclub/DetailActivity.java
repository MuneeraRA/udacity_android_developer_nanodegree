package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView txtOrigin;
    TextView txtAlsoKnown;
    TextView txtPlaceOfOrigin;
    TextView txtIngredients;
    TextView txtDescription;
    TextView txtIngredientsLabel;
    TextView txtDescriptionLabel;
    ImageView ingredientsIv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        bindViews();

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);


        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        txtOrigin.setText(sandwich.getMainName());
        if (sandwich.getAlsoKnownAs().size() == 0) {
            txtAlsoKnown.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
                txtAlsoKnown.append(" #" + sandwich.getAlsoKnownAs().get(i).toLowerCase() + "  ");
            }
        }
        if (sandwich.getIngredients().size() > 0)
            txtIngredientsLabel.setVisibility(View.VISIBLE);

        for (int i = 0; i < sandwich.getIngredients().size(); i++) {
            txtIngredients.append("- " + sandwich.getIngredients().get(i) + "\n");
        }
        txtPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
        if (sandwich.getDescription().length() > 0)
            txtDescriptionLabel.setVisibility(View.VISIBLE);

        txtDescription.setText(sandwich.getDescription());
        if (sandwich.getImage().length() == 0) {
            ingredientsIv.setVisibility(View.GONE);
        } else {
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ingredientsIv);
        }
    }

    private void bindViews() {
        txtOrigin = (TextView) findViewById(R.id.origin_tv);
        txtAlsoKnown = (TextView) findViewById(R.id.also_known_tv);
        txtPlaceOfOrigin = (TextView) findViewById(R.id.place_of_origin_tx);
        txtDescription = (TextView) findViewById(R.id.description_tv);
        txtIngredients = (TextView) findViewById(R.id.ingredients_tv);
        txtIngredientsLabel = (TextView) findViewById(R.id.txt_ingredients_label);
        txtDescriptionLabel = (TextView) findViewById(R.id.txt_description_label);
        ingredientsIv = findViewById(R.id.image_iv);

    }
}
