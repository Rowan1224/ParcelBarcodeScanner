package com.example.rifat.parcelbarcodescanner;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class ResultActivity extends AppCompatActivity {

    public String result;
    private String centralData;
    private JSONObject jsonObject;
    private JSONArray jsonArray;

    private TextView Tname,Tphone,Tsource,Tdestination,Taddress,Ttype;
    private String Vsender_name,Vsender_phone ,Vsender_address , Vreciv_name,Vreciv_phone;
    private String Vdestination_code,Vdestination_address,Vtype,Vbarcode,Vtime,Vinformed,Vdelivery_date,Vdelivery_code,VlastScanned;
    private ProgressDialog progressDialog;

    private Button btnSaveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Button scan = findViewById(R.id.btnScanAgain);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final Bundle mbundle = getIntent().getExtras();

        if(mbundle != null){
            this.result = mbundle.getString("code");
        }

        TextView txtCode = findViewById(R.id.txtCode);
        txtCode.setText("Code : "+result);

        if(isNetworkAvailable()){
            final SetGetData setGetData = new SetGetData();
            setGetData.execute("GetData",result);

            progressDialog =new ProgressDialog(ResultActivity.this);
            progressDialog.setMessage("Getting Parcel info...");
            progressDialog.show();
        }else{
            Toast.makeText(ResultActivity.this,"Check Internet",Toast.LENGTH_LONG).show();
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private class SetGetData extends AsyncTask<String,Void,String>{

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
            String setData_url = "http://192.168.0.104/parcel/setData.php";
            String check_url = "http://192.168.0.104/parcel/checkData.php";

            if(task.equals("GetData") || task.equals("CheckData")){
                String barcode = strings[1];
                String Json_String;
                StringBuilder Json_Data=new StringBuilder();

                try {
                    URL url;

                    if(task.equals("GetData")){

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
                    }else{
                        url = new URL(check_url);
                    }

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
            
            if(task.equals("SetData")){

                String sender_name = strings[1];
                String sender_phone= strings[2];
                String sender_address= strings[3];
                String reciv_name= strings[4];
                String reciv_phone= strings[5];
                String destination_code= strings[6];
                String destination_address= strings[7];
                String type= strings[8];
                String barcode= strings[9];
                String time= strings[10];
                String informed= "";
                String delivery_date= "";
                String delivery_code= "";

                try {
                    URL url = new URL(setData_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                    String data = URLEncoder.encode("sender_name","UTF-8")+"="+URLEncoder.encode(sender_name,"UTF-8")+"&"+
                            URLEncoder.encode("sender_phone","UTF-8")+"="+URLEncoder.encode(sender_phone,"UTF-8")+"&"+
                            URLEncoder.encode("sender_address","UTF-8")+"="+URLEncoder.encode(sender_address,"UTF-8")+"&"+
                            URLEncoder.encode("reciv_name","UTF-8")+"="+URLEncoder.encode(reciv_name,"UTF-8")+"&"+
                            URLEncoder.encode("reciv_phone","UTF-8")+"="+URLEncoder.encode(reciv_phone,"UTF-8")+"&"+
                            URLEncoder.encode("destination_code","UTF-8")+"="+URLEncoder.encode(destination_code,"UTF-8")+"&"+
                            URLEncoder.encode("destination_address","UTF-8")+"="+URLEncoder.encode(destination_address,"UTF-8")+"&"+
                            URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type,"UTF-8")+"&"+
                            URLEncoder.encode("barcode","UTF-8")+"="+URLEncoder.encode(barcode,"UTF-8")+"&"+
                            URLEncoder.encode("time","UTF-8")+"="+URLEncoder.encode(time,"UTF-8")+"&"+
                            URLEncoder.encode("informed","UTF-8")+"="+URLEncoder.encode(informed,"UTF-8")+"&"+
                            URLEncoder.encode("delivery_date","UTF-8")+"="+URLEncoder.encode(delivery_date,"UTF-8")+"&"+
                            URLEncoder.encode("delivery_code","UTF-8")+"="+URLEncoder.encode(delivery_code,"UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();
                    InputStream IS = httpURLConnection.getInputStream();
                    IS.close();

                    return "Done";

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

            btnSaveData = findViewById(R.id.btnSaveData);
            System.out.println("Rony "+postresult);

            if(postresult.equals("BadLuck") || postresult.equals("")){
                progressDialog.dismiss();
                Toast.makeText(ResultActivity.this,"Currently Website isn't responding ! Please Try again",Toast.LENGTH_LONG).show();
            }
            
            else if(postresult.equals("Done")){
                final Dialog dialog = new Dialog(ResultActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog);
                TextView head = dialog.findViewById(R.id.dialogHead);
                head.setText("New Parcel");
                TextView txt = dialog.findViewById(R.id.popUptxt);
                Button done = dialog.findViewById(R.id.btnDone);
                txt.setText("Information for this parcel is added in the Local Database.");
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
            else if(postresult.contains("Yes")){
                final Dialog dialog2 = new Dialog(ResultActivity.this);
                dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog2.setContentView(R.layout.dialog);
                TextView head = dialog2.findViewById(R.id.dialogHead);
                head.setText("Old Parcel");
                TextView txt = dialog2.findViewById(R.id.popUptxt);
                TextView date = dialog2.findViewById(R.id.txtDate);
                Button done = dialog2.findViewById(R.id.btnDone);
                txt.setText("This parcel has already been added in the local database on date :");
                date.setText(postresult.substring(3));
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog2.dismiss();
                    }
                });
                dialog2.show();
            }
            else if(postresult.equals("No")){
                btnSaveData.setVisibility(View.VISIBLE);
            }
            else{
                centralData = postresult;

                try {
                    jsonObject = new JSONObject(centralData);
                    jsonArray = jsonObject.getJSONArray("server_responce");

                    Tname = findViewById(R.id.Fname);
                    Tphone = findViewById(R.id.Fphone);
                    Tsource = findViewById(R.id.Fsource);
                    Tdestination = findViewById(R.id.Fdestination);
                    Taddress = findViewById(R.id.Faddress);
                    Ttype = findViewById(R.id.Ftype);

                    for(int k=0;k<jsonArray.length();k++){
                        object = jsonArray.getJSONObject(k);
                        Vsender_name = object.getString("Sender_Name");
                        Vsender_phone = object.getString("Sender_Phone");
                        Vsender_address = object.getString("Sender_Address");
                        Vreciv_name = object.getString("Reciv_Name");
                        Vreciv_phone = object.getString("Reciv_Phone");
                        Vdestination_code = object.getString("Destination_Code");
                        Vdestination_address = object.getString("Destination_Address");
                        Vtype = object.getString("Type");
                        Vbarcode = object.getString("Barcode");
                        Vtime = object.getString("Time");
                        VlastScanned=object.getString("LastScanned");
                        Tname.setText(Vreciv_name);
                        Tphone.setText(Vreciv_phone);
                        Tsource.setText(Vsender_address);
                        String dCode = object.getString("Destination_Code");
                        String[] arDcode = dCode.split("-");
                        String district = Readfile.getDistrict(arDcode[0]);
                        String upazilla = Readfile.getUpazilla(arDcode[0]+"-"+arDcode[1]);
                        String union = Readfile.getUnion(dCode);
                        Tdestination.setText(district+","+upazilla+","+union);
                        Taddress.setText(Vdestination_address);
                        Ttype.setText(Vtype);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressDialog.dismiss();

                btnSaveData.setVisibility(View.GONE);

                final SetGetData check = new SetGetData();
                check.execute("CheckData",result);

                btnSaveData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final SetGetData setGetData = new SetGetData();
                        setGetData.execute("SetData",Vsender_name,Vsender_phone,Vsender_address,Vreciv_name,Vreciv_phone,Vdestination_code,Vdestination_address,Vtype,Vbarcode,Vtime);
                    }
                });
            }
            
        }

    }

}