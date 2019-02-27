package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();
        try {
            JSONObject sandwichJSONObject = new JSONObject(json);
            sandwich.setMainName(sandwichJSONObject.getJSONObject("name").getString("mainName"));
            sandwich.setAlsoKnownAs(convertJsonArraytoArrayList(sandwichJSONObject.getJSONObject("name").getJSONArray("alsoKnownAs")));
            sandwich.setPlaceOfOrigin(sandwichJSONObject.getString("placeOfOrigin"));
            sandwich.setDescription(sandwichJSONObject.getString("description"));
            sandwich.setImage(sandwichJSONObject.getString("image"));
            sandwich.setIngredients(convertJsonArraytoArrayList(sandwichJSONObject.getJSONArray("ingredients")));

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return sandwich;
    }

    private static ArrayList convertJsonArraytoArrayList(JSONArray array) {

        ArrayList listdata = new ArrayList<>();
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                try {
                    listdata.add(array.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return listdata;
    }
}
