package com.example.rifat.parcelbarcodescanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.pusher.pushnotifications.PushNotifications;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int Activity_Num=0;
    public static Activity kill;

    private static final String TAG = "MainActivity";
    TextView txtCode;
    ImageView btnScan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottm_tab_1);
        setupBottomNavigationViewEx();
//
//        Task<InstanceIdResult> token = FirebaseInstanceId.getInstance().getInstanceId();
//
//        token.addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//            @Override
//            public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                //Log.d(TAG, "onCreate: token" + task.getResult().getToken());
//            }
//        });


        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();

        InputStream is = getResources().openRawResource(R.raw.test);
        try {
            String s = IOUtils.toString(is, StandardCharsets.UTF_8);
            String[] st = s.split("\n");

            for (int i = 0; i < st.length; i++) {
                list.add(st[i]);
            }

            is = getResources().openRawResource(R.raw.sylhet_city);
            s = IOUtils.toString(is, StandardCharsets.UTF_8);
            st = s.split("\n");

            for (int i = 0; i < st.length; i++) {
                list2.add(st[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Readfile.read(list, list2);
        btnScan =(ImageView) findViewById(R.id.btnScan);
        Typeface mface = Typeface.createFromAsset(getAssets(), "fonts/STENCIL.TTF");

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ScannerActivity.class));
                finish();
            }
        });

        kill = this;

       /* PushNotifications.start(getApplicationContext(), "17786dda-39c1-4746-a7fc-71e068166850");
        PushNotifications.subscribe("hello");*/

    }

    private void setupBottomNavigationViewEx(){
        BottomNavigationViewEx bottomNavigationViewEx= findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationViewHelper(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(MainActivity.this,bottomNavigationViewEx);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem menuItem=menu.getItem(Activity_Num);
        menuItem.setChecked(true);
        menuItem.setEnabled(true);
    }

    //MyFirebaseMessagingService myFirebaseMessagingService=new MyFirebaseMessagingService();
}
