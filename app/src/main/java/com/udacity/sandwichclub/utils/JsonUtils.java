package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        final String KEY_NAME = "name";
        final String KEY_MAIN_NAME = "mainName";
        final String KEY_ALSO_KNOWN_AS = "alsoKnownAs";
        final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
        final String KEY_DESCRIPTION = "description";
        final String KEY_IMAGE = "image";
        final String KEY_INGREDIENTS = "ingredients";

        Sandwich sandwich = new Sandwich();

        try {
            JSONObject jsonSandwich = new JSONObject(json);

            // Extract the main name
            JSONObject name = jsonSandwich.getJSONObject(KEY_NAME);
            String mainName = name.getString(KEY_MAIN_NAME);
            sandwich.setMainName(mainName);

            // Extract the alsoKnownAs
            JSONArray alsoKnownAs = name.getJSONArray(KEY_ALSO_KNOWN_AS);
            if (alsoKnownAs != null) {
                ArrayList<String> aliases = new ArrayList<>();
                for (int i = 0; i < alsoKnownAs.length(); i++) {
                    aliases.add(alsoKnownAs.getString(i));
                }
                sandwich.setAlsoKnownAs(aliases);
            }

            // Extract place of origin
            sandwich.setPlaceOfOrigin(jsonSandwich.getString(KEY_PLACE_OF_ORIGIN));

            // Extract description
            sandwich.setDescription(jsonSandwich.getString(KEY_DESCRIPTION));

            // Extract image
            sandwich.setImage(jsonSandwich.getString(KEY_IMAGE));

            // Extract ingredients
            JSONArray jsonIngredients = jsonSandwich.getJSONArray(KEY_INGREDIENTS);
            if (jsonIngredients != null) {
                ArrayList<String> ingredientsArray = new ArrayList<>();
                for (int i = 0; i < jsonIngredients.length(); i++) {
                    ingredientsArray.add(jsonIngredients.getString(i));
                }
                sandwich.setIngredients(ingredientsArray);
            }

        } catch (JSONException e) {
            throw new JSONException(e + ": problem with JSON");
        }

        return sandwich;
    }
}
