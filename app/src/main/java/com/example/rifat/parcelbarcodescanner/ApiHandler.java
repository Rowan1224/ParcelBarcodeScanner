package com.example.rifat.parcelbarcodescanner;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rifat.parcelbarcodescanner.DeviceClass;
import com.example.rifat.parcelbarcodescanner.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiHandler {

    private static final String TAG = "ApiHandler";

    List<DeviceClass> results=new ArrayList<DeviceClass>();


    public void RequestGET(Context context, String url) {
        RequestQueue requestQueue= Volley.newRequestQueue(context);

        StringRequest objectRequest=new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: "+response);
                        Gson gson=new Gson();

                        results=Arrays.asList(gson.fromJson(response,DeviceClass[].class));
                        for(int i=0;i<results.size();i++)
                        {
                            Log.d(TAG, "Test: "+results.get(i).getEmpId());
                        }



                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: "+error.toString());
            }
        });

        requestQueue.add(objectRequest);


    }


   /* StringRequest post=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d(TAG, "onResponse: " + response);
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            error.printStackTrace();
            Log.d(TAG, "onErrorResponse: "+error.toString());
        }
    }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> params=new HashMap<String, String>();

            params.put("EmpId","5");
            params.put("Latitude","24.565");
            params.put("Longitude","91.85");
            params.put("password","123987");
            params.put("activity","onField");




            return params;
        }
    };
    StringRequest put=new StringRequest(Request.Method.PUT, url+'3'+'/', new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d(TAG, "onResponse: " + response);
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            error.printStackTrace();
            Log.d(TAG, "onErrorResponse: "+error.toString());
        }
    }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> params=new HashMap<String, String>();

            params.put("EmpId","3");
            params.put("Latitude","24.565");
            params.put("Longitude","91.85");
            params.put("password","123456");
            params.put("activity","onOffice");




            return params;
        }
    };
        requestQueue.add(post);
        requestQueue.add(put);
*/
}


