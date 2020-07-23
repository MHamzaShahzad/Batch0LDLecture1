package com.dapgarage.lecture1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dapgarage.lecture1.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiActivity extends AppCompatActivity {


    private static final String TAG = ApiActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        // Request # 1
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://reqres.in/api/users", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: " + response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    int page = jsonResponse.getInt("page");
                    int per_page = jsonResponse.getInt("per_page");
                    int total = jsonResponse.getInt("total");
                    int total_pages = jsonResponse.getInt("total_pages");

                    JSONObject ad = jsonResponse.getJSONObject("ad");
                    String ad_company = ad.getString("company");
                    String ad_url = ad.getString("url");
                    String ad_text = ad.getString("text");

                    JSONArray data = jsonResponse.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++){
                        JSONObject dataObj = data.getJSONObject(i);
                        int id = dataObj.getInt("id");
                        String email = dataObj.getString("email");
                        String first_name = dataObj.getString("first_name");
                        String last_name = dataObj.getString("last_name");
                        String avatar = dataObj.getString("avatar");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

    }
}