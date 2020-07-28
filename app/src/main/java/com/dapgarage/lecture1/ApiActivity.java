package com.dapgarage.lecture1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dapgarage.lecture1.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApiActivity extends AppCompatActivity {


    private static final String TAG = ApiActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        String url = "https://domain.in/api/users";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        // Request # 1

        // Get All USERS
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: " + response);

                try {
                    JSONObject resObj = new JSONObject(response);

                    int page = resObj.getInt("page");
                    int per_page = resObj.getInt("per_page");
                    int total = resObj.getInt("total");
                    int total_pages = resObj.getInt("total_pages");

                    JSONObject ad = resObj.getJSONObject("ad");
                    String company = ad.getString("company");
                    String url = ad.getString("url");
                    String text = ad.getString("text");

                    JSONArray data = resObj.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++){
                        JSONObject userInfo = data.getJSONObject(i);

                        String id = userInfo.getString("id");
                        String email = userInfo.getString("email");
                        String first_name = userInfo.getString("first_name");
                        String last_name = userInfo.getString("last_name");
                        String avatar = userInfo.getString("avatar");
                    }

                    String errorMessage = resObj.optString("errorMessage");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Create new USER
        StringRequest createNewUser = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+ response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", "Hamza");
                params.put("job", "Developer");
                return params;
            }

            @Override
            protected String getParamsEncoding() {
                return super.getParamsEncoding();
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return super.getBody();
            }

            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("auth Bearer ", "d0uf0fuwe8[fw88cw9m9x9e[d90uundc0u9u9cum09u09[u2[eu9q09[euv");
                return headers;
            }*/

        };


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    int page = response.getInt("page");
                    int per_page = response.getInt("per_page");
                    int total = response.getInt("total");
                    int total_pages = response.getInt("total_pages");

                    JSONObject ad = response.getJSONObject("ad");
                    String company = ad.getString("company");
                    String url = ad.getString("url");
                    String text = ad.getString("text");

                    JSONArray data = response.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++){
                        JSONObject userInfo = data.getJSONObject(i);

                        String id = userInfo.getString("id");
                        String email = userInfo.getString("email");
                        String first_name = userInfo.getString("first_name");
                        String last_name = userInfo.getString("last_name");
                        String avatar = userInfo.getString("avatar");
                    }

                    String errorMessage = response.optString("errorMessage");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject userInfo = response.getJSONObject(i);

                        String id = userInfo.getString("id");
                        String email = userInfo.getString("email");
                        String first_name = userInfo.getString("first_name");
                        String last_name = userInfo.getString("last_name");
                        String avatar = userInfo.getString("avatar");
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        //queue.add(stringRequest);
        queue.add(createNewUser);
        /*queue.add(jsonObjectRequest);
        queue.add(jsonArrayRequest);*/

    }
}