package com.example.rifat.parcelbarcodescanner;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Objects;

public class TrackActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private EditText editCode;
    private Button btnTrack,btnMap;
    private String centralData;
    String check;
    public String result;

    private LatLng location;
    private  ParcelDetails parcelDetails;
    private static final String TAG = "TrackActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);


        result= Objects.requireNonNull(getIntent().getExtras()).getString("code");
        editCode=(EditText) findViewById(R.id.editTrack);
        editCode.setText(result);



        ApiHandler.RequestGET(TrackActivity.this, result, new TrackParcelInterface() {
            @Override
            public void onCallbackResponse(JsonObject response) {

                check=response.get("Parcel").
                        getAsJsonArray().get(0).getAsJsonObject().get("tracking_code").getAsString();


                if(check.contains("emp"))
                {
                    check="emp";
                }
                else if(check.contains("off"))
                {
                    check="off";
                }
                else
                {
                    check="Delivered";
                }
                location=ApiHandler.JsonParser(response);
                parcelDetails=ApiHandler.JsonParser_Parcel(response);

                btnTrack = findViewById(R.id.btnTrack);
//                btnMap =findViewById(R.id.btnMap);
//                btnMap.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//
//
//                        if( isServicesOK() ){
//                            Intent intent = new Intent(TrackActivity.this, MapsActivity.class);
//                            intent.putExtra("lat",location.latitude);
//                            intent.putExtra("lng",location.longitude);
//
//                            startActivity(intent);
//                        }
//                    }
//                });


                btnTrack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isNetworkAvailable()){

                            Intent intent = new Intent(TrackActivity.this, ResultActivity.class);
                            intent.putExtra("lat",location.latitude);
                            intent.putExtra("lng",location.longitude);
                            intent.putExtra("details",parcelDetails);
                            intent.putExtra("Check",check);

                            startActivity(intent);

                        }else{
                            Toast.makeText(TrackActivity.this,"Check Internet",Toast.LENGTH_LONG).show();
                        }

                    }
                });


            }
        });




    }
    private void init(){
        Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }




    }

