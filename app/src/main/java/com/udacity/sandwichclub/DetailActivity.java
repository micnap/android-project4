package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;
import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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

        try {
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);
            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI(sandwich);
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ingredientsIv);

            setTitle(sandwich.getMainName());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        // Get reference to textviews to display the data
        TextView origin = (TextView) findViewById(R.id.origin_tv);
        TextView description = (TextView) findViewById(R.id.description_tv);
        TextView alias = (TextView) findViewById(R.id.also_known_tv);
        TextView ingredients = (TextView) findViewById(R.id.ingredients_tv);

        // Set the values of the textviews.
        origin.setText(sandwich.getPlaceOfOrigin());
        description.setText(sandwich.getDescription());

        int numAliases = sandwich.getAlsoKnownAs().size();
        for (int i = 0; i < numAliases; i++) {
            alias.append(sandwich.getAlsoKnownAs().get(i));
            if (i + 1 < numAliases) {
                alias.append(", ");
            }
        }

        int numIngredients = sandwich.getIngredients().size();
        for (int i = 0; i < numIngredients; i++) {
            ingredients.append(" - " + sandwich.getIngredients().get(i) + "\n");
        }
    }
}
