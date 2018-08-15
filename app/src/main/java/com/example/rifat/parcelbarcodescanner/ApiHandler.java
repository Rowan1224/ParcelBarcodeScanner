package com.example.rifat.parcelbarcodescanner;

import android.app.VoiceInteractor;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rifat.parcelbarcodescanner.DeviceClass;
import com.example.rifat.parcelbarcodescanner.MainActivity;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiHandler {

    private static final String TAG = "ApiHandler";

    List<DeviceClass> results=new ArrayList<DeviceClass>();

    private static LatLng BaseLocation=new LatLng(25.753495, 89.252704);
    public static String URL="http://192.168.0.118:8080/";
    public  static String TrackParcel=URL+"api/TrackParcel/";
    public  static String ParcelRequest=URL+"api/NewParcel/";
    public  static String CustomerInfo=URL+"api/Customer/";
    public  static String NewCustomer=URL+"api/NewCustomer/";
    public  static String AddNewCustomer=URL+"api/AddNewCustomer/";
    public  static String Requests=URL+"api/UserReq/";
    public static String UpdateCustomerInfo=URL+"api/CustomerInfo/";


    public static void RequestGET(Context context, String QR_Code, final TrackParcelInterface track) {
        RequestQueue requestQueue= Volley.newRequestQueue(context);

        StringRequest objectRequest=new StringRequest(Request.Method.GET, TrackParcel+QR_Code,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: "+response);
                        JsonParser parser=new JsonParser();
                        JsonObject result= (JsonObject) parser.parse(response);
                        track.onCallbackResponse(result);





                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: "+error.toString());
            }
        });

        requestQueue.add(objectRequest);


    }

    public static LatLng JsonParser(JsonObject response)
    {
        String lat,lng;
        String EmpId;
        LatLng location=BaseLocation;
        JsonArray Parcel=response.get("Parcel").getAsJsonArray();
        try {
            EmpId = Parcel.get(0).getAsJsonObject().get("tracking_code").getAsString();

            }catch (Exception e)
        {
            EmpId="625";
        }

        Log.d(TAG, "JsonParser: "+EmpId);

        JsonArray EmpArray=response.getAsJsonArray("TrackParcel");
        for(int i=0;i<EmpArray.size();i++)
        {
           JsonObject iteration=EmpArray.get(i).getAsJsonObject();
           Gson gson=new Gson();
           DeviceClass deviceClass=gson.fromJson(iteration,DeviceClass.class);
            Log.d(TAG, "JsonParser: "+deviceClass.getEmpId());
           if(deviceClass.getActivity().equals("On Field") && deviceClass.getEmpId().equals(EmpId)){

               lat=deviceClass.getLatitude();
               lng=deviceClass.getLongitude();
               location=new LatLng(Float.parseFloat(lat),Float.parseFloat(lng));
               break;

           }




        }
        return location;
    }

    public static ParcelDetails JsonParser_Parcel(JsonObject response)
    {
        Gson gson=new Gson();
        JsonObject result=response.get("Parcel").getAsJsonArray().get(0).getAsJsonObject();
        ParcelDetails details=gson.fromJson(result,ParcelDetails.class);

        Log.d(TAG, "JsonParser_Parcel: "+details.getDestination_code());

        return details;

    }

    public static void  PostReq(final Context context, ParcelDetails details)
    {
        RequestQueue queue=Volley.newRequestQueue(context);
        Gson gson=new Gson();

       String param=gson.toJson(details);

        try {
            JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, ParcelRequest,
                    new JSONObject(param), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(context,"Request sent",Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String body="check";
                    //get status code here
                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                    //get response body and parse with appropriate encoding
                    if(error.networkResponse.data!=null) {
                        try {
                            body = new String(error.networkResponse.data,"UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.d(TAG, "onErrorResponse: "+body);

                }
            });

            if(isNetworkAvailable(context))
            {
                queue.add(request);
            }


        } catch (JSONException e) {

        }

    }

    public static void CheckInfo(Context context, String phone, final CheckCustomer track)
    {
        final RequestQueue requestQueue= Volley.newRequestQueue(context);

        StringRequest objectRequest=new StringRequest(Request.Method.GET, CustomerInfo+phone,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: "+response);

                        if(response.length()>2)
                        {
                            Gson gson=new Gson();
                            JsonParser parser=new JsonParser();
                            JsonArray array=parser.parse(response).getAsJsonArray();
                            NewCustomerClass check=gson.fromJson(array.get(0).toString(),NewCustomerClass.class);
                            track.onCallbackResponse(check);
                        }
                        else
                        {
                            NewCustomerClass check=new NewCustomerClass("-1","no","no","no","no");
                            track.onCallbackResponse(check);
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
    public static void Requests(Context context, final UserReq req)
    {
        final RequestQueue requestQueue= Volley.newRequestQueue(context);

        StringRequest objectRequest=new StringRequest(Request.Method.GET, Requests,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: "+response);
                        JsonParser parser=new JsonParser();
                        JsonArray result= (JsonArray) parser.parse(response);

                        List<UserRequests> list=new ArrayList<>();

                       for(int i=0;i<result.size();i++)
                       {
                           String pk=result.get(i).getAsJsonObject().get("sender_phone").getAsString();
                           if(pk.equals(UserInfo.user.getPk()))
                           {
                               Gson gson=new Gson();
                               UserRequests userRequests=gson.fromJson(result.get(i).toString(),UserRequests.class);

                               list.add(userRequests);
                           }
                       }

                        req.onCallbackResponse(list);



                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: "+error.toString());
            }
        });

        if(isNetworkAvailable(context))
        {
            requestQueue.add(objectRequest);
        }




    }


    public  static void AddCustomer(final Context context, final CustomerDetails details, final NewCustomer customer)
    {

        RequestQueue queue = Volley.newRequestQueue(context);

        Gson gson=new Gson();

        String json=gson.toJson(details);
        try {
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, NewCustomer
                    , new JSONObject(json), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {


                    Gson gson=new Gson();
                    CustomerDetails customerDetails=gson.fromJson(response.toString(),CustomerDetails.class);
                    customer.onCallbackResponse(customerDetails);




                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String body="check";
                    //get status code here
                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                    //get response body and parse with appropriate encoding
                    if(error.networkResponse.data!=null) {
                        try {
                            body = new String(error.networkResponse.data,"UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.d(TAG, "onErrorResponse: "+body);

                }

            });

            if(isNetworkAvailable(context))
            {
                queue.add(jsonObjectRequest);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public  static void AddNewCustomer(final Context context, final NewCustomerClass details, final
    AddNewCustomer customer)
    {

        RequestQueue queue = Volley.newRequestQueue(context);

        Gson gson=new Gson();

        String json=gson.toJson(details);
        try {
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, NewCustomer
                    , new JSONObject(json), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Gson gson=new Gson();
                  NewCustomerClass customerDetails=gson.fromJson(response.toString(),NewCustomerClass.class);
                  customer.onCallbackResponse(customerDetails);




                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String body="check";
                    //get status code here
                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                    //get response body and parse with appropriate encoding
                    if(error.networkResponse.data!=null) {
                        try {
                            body = new String(error.networkResponse.data,"UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.d(TAG, "onErrorResponse: "+body);

                }

            });

            if(isNetworkAvailable(context))
            {
                queue.add(jsonObjectRequest);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void UpdateUser(Context context, NewCustomerClass customerClass, final AddNewCustomer customer)
    {

        RequestQueue queue=Volley.newRequestQueue(context);
        Gson gson =new Gson();
        String pk=customerClass.getPk();
        try {
            JsonObjectRequest request=new JsonObjectRequest(Request.Method.PUT, UpdateCustomerInfo + pk
                    + '/', new JSONObject(gson.toJson(customerClass)), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Gson gson1=new Gson();

                    customer.onCallbackResponse(gson1.fromJson(response.toString(),NewCustomerClass.class));


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    String body="check";
                    //get status code here
                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                    //get response body and parse with appropriate encoding
                    if(error.networkResponse.data!=null) {
                        try {
                            body = new String(error.networkResponse.data,"UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.d(TAG, "onErrorResponse: "+body);

                }
            });

            queue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public  static  boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }



}

interface TrackParcelInterface{

    void onCallbackResponse(JsonObject response);

}
interface CheckCustomer{

    void onCallbackResponse(NewCustomerClass customerClass);

}
interface NewCustomer{

    void onCallbackResponse(CustomerDetails details);

}
interface AddNewCustomer{

    void onCallbackResponse(NewCustomerClass details);

}
interface UserReq{
    void onCallbackResponse(List<UserRequests> userRequests);
}
