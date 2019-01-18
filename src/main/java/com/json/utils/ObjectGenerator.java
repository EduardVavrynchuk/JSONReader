package com.json.utils;

import com.google.gson.Gson;
import com.json.db.entities.Document;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObjectGenerator {

    private final static String NAME_OF_ARRAY = "data";

    public static List<Document> convertResponseToObject(ResponseBody responseBody) throws JSONException, IOException {
        JSONObject jsonObject = new JSONObject(responseBody.string());
        JSONArray myResponse = jsonObject.getJSONArray(NAME_OF_ARRAY);

        List<Document> documents = new ArrayList<>();

        for (int i = 0; i < myResponse.length(); i++) {
            Document document = new Gson().fromJson(myResponse.getString(i), Document.class);

            documents.add(document);
        }

        return documents;
    }

}
