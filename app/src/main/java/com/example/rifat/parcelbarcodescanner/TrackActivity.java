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

public class TrackActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private EditText editCode;
    private Button btnTrack,btnMap;
    private String centralData;
    private JSONObject jsonObject;
    private JSONArray jsonArray;

    private String barcode,source,lastTrack;
    private static final String TAG = "TrackActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        editCode =findViewById(R.id.editTrack);
        btnTrack = findViewById(R.id.btnTrack);
        btnMap =findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if( isServicesOK()){
                    init();
                }
            }
        });
        btnTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable()){
                    SetGetData setGetData = new SetGetData();
                    setGetData.execute("GetData",editCode.getText().toString());

                    progressDialog =new ProgressDialog(TrackActivity.this);
                    progressDialog.setMessage("Getting Parcel info...");
                    progressDialog.show();
                }else{
                    Toast.makeText(TrackActivity.this,"Check Internet",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    private void init(){
        Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrackActivity.this, MapsActivity.class);
                /*String[] latlong=lastTrack.split(",");
                intent.putExtra("Lat",latlong[0]);
                intent.putExtra("Long",latlong[1]);*/
                startActivity(intent);
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private class SetGetData extends AsyncTask<String,Void,String> {

        String data;
        JSONObject object;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            Boolean bool=false;

            String task = strings[0];
            String getData_url = "https://ritbolt.000webhostapp.com/getData.php";

            if(task.equals("GetData")){

                String barcode = strings[1];
                String Json_String;
                StringBuilder Json_Data=new StringBuilder();

                try {
                    URL url;

                    try {
                        HttpURLConnection urlc = (HttpURLConnection) (new URL(getData_url).openConnection());
                        urlc.setRequestProperty("User-Agent", "Test");
                        urlc.setRequestProperty("Connection", "close");
                        urlc.setConnectTimeout(15000);
                        urlc.connect();
                        bool = (urlc.getResponseCode() == 200);
                    } catch (IOException e) {

                    }

                    if(!bool){
                        return "BadLuck";
                    }

                    url = new URL(getData_url);

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));

                    String data = URLEncoder.encode("barcode","UTF-8")+"="+URLEncoder.encode(barcode,"UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();

                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(IS));

                    while ((Json_String = reader.readLine()) != null){
                        Json_Data.append(Json_String).append("\n");
                    }
                    reader.close();
                    IS.close();
                    httpURLConnection.disconnect();

                    return Json_Data.toString().trim();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return "";
        }

        @Override
        protected void onPostExecute(final String postresult) {

            if (postresult.equals("BadLuck") || postresult.equals("")) {
                progressDialog.dismiss();
                Toast.makeText(TrackActivity.this, "Currently Website isn't responding ! Please Try again", Toast.LENGTH_LONG).show();
            }else{
                progressDialog.dismiss();

                centralData = postresult;

                try {
                    System.out.println("Rony");
                    jsonObject = new JSONObject(centralData);
                    jsonArray = jsonObject.getJSONArray("server_responce");
                    System.out.println("Rony 2 "+jsonArray.length());
                    for (int k = 0; k < jsonArray.length(); k++) {
                        object = jsonArray.getJSONObject(k);

                        barcode = object.getString("Barcode");
                        source = object.getString("Sender_Address");
                        lastTrack = object.getString("LastScanned");
                        System.out.println("Rony 3 ");
                        final Dialog dialog = new Dialog(TrackActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.track_dialog);
                        TextView tbarcode,tsource,tlastTrack;

                        tbarcode = dialog.findViewById(R.id.Fname);
                        tsource = dialog.findViewById(R.id.Fphone);
                        tlastTrack = dialog.findViewById(R.id.Fsource);

                        tbarcode.setText(barcode);
                        tsource.setText(source);
                        //last scanned ekhn map e dekhabe
                      //  tlastTrack.setText(lastTrack);
                        dialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        }
    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");


        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(TrackActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(TrackActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    }

