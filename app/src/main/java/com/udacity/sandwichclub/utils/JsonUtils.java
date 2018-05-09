package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        Sandwich sandwich = new Sandwich();

        try {
            JSONObject jsonSandwich = new JSONObject(json);

            // Extract the main name
            JSONObject name = jsonSandwich.getJSONObject("name");
            String mainName = name.getString("mainName");
            sandwich.setMainName(mainName);

            // Extract the alsoKnownAs
            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
            if (alsoKnownAs != null) {
                ArrayList<String> aliases = new ArrayList<>();
                for (int i = 0; i < alsoKnownAs.length(); i++) {
                    aliases.add(alsoKnownAs.getString(i));

                }
                sandwich.setAlsoKnownAs(aliases);
            }

            // Extract place of origin
            sandwich.setPlaceOfOrigin(jsonSandwich.getString("placeOfOrigin"));

            // Extract description
            sandwich.setDescription(jsonSandwich.getString("description"));

            // Extract image
            sandwich.setImage(jsonSandwich.getString("image"));


            // Extract ingredients
            JSONArray jsonIngredients = jsonSandwich.getJSONArray("ingredients");
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
