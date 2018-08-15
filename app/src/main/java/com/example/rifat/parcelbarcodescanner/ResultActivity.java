package com.example.rifat.parcelbarcodescanner;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
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
import java.util.List;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    public String result;
    Geocoder geocoder;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String TAG = "ResultActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView scanReport =(TextView) findViewById(R.id.Status);
        TextView dest=(TextView)findViewById(R.id.destinationCode);
        Button map=(Button) findViewById(R.id.btnMap);
        ImageView mapImage=(ImageView)findViewById(R.id.Map);




        if(isNetworkAvailable()){

        String check=getIntent().getExtras().getString("Check");
        ParcelDetails details= (ParcelDetails) getIntent().getSerializableExtra("details");

        dest.setText(details.getDestination_code());
        String report="";

        if(check.equals("off"))
        {
            report=report+getString(R.string.Scan_Report2);
            report=report+"Sylhet Branch,Sylhet,Bangladesh";
            scanReport.setText(report);


        }
        else if(check.equals("Delivered"))
        {
            report=report+getString(R.string.Delivered);
            scanReport.setText(report);
            map.setVisibility(View.INVISIBLE);
            mapImage.setVisibility(View.INVISIBLE);
        }
        else{
            EmpStatus(scanReport,report);
        }


        }else{
            Toast.makeText(ResultActivity.this,"Check Internet",Toast.LENGTH_LONG).show();
        }

        map.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        if( isServicesOK() ){
                            Intent intent = new Intent(ResultActivity.this, MapsActivity.class);
                            intent.putExtra("lat",getIntent().getExtras().getDouble("lat"));
                            intent.putExtra("lng",getIntent().getExtras().getDouble("lng"));

                            startActivity(intent);
                        }
                    }
                });




    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private void EmpStatus(TextView scanReport,String report)
    {
        List<Address> addresses;
        double lat=getIntent().getExtras().getDouble("lat");
        double lng=getIntent().getExtras().getDouble("lng");
        ParcelDetails details= (ParcelDetails) getIntent().getSerializableExtra("details");
        geocoder=new Geocoder(ResultActivity.this, Locale.getDefault());

       report =report+ getString(R.string.Scan_Report2)+'\n';

        try {
            addresses=geocoder.getFromLocation(lat,lng,1);

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

            Log.d(TAG, "onCreate: "+ address);

            report=report+address;
            scanReport.setText(report);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");


        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(ResultActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(ResultActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


}