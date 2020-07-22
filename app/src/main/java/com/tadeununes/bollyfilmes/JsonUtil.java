package com.tadeununes.bollyfilmes;

/*
{
"page": 1,
"total_results": 10000,
"total_pages": 500,
"results": []
}
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    public static List<ItemFilme> fromJsonToList (String json){
        List<ItemFilme> list = new ArrayList<>();
        try {
            JSONObject jsonBase = new JSONObject(json);
            JSONArray results = jsonBase.getJSONArray("results");

            for (int i = 0; i < results.length(); i++){
                JSONObject filmeObject = results.getJSONObject(i);
                ItemFilme itemFilme = new ItemFilme(filmeObject);
                list.add(itemFilme);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

}
