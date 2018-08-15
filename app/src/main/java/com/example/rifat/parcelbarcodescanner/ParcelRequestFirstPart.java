package com.example.rifat.parcelbarcodescanner;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class ParcelRequestFirstPart extends AppCompatActivity {

    private static final int Activity_Num=1;

    public static Activity kill2;

    String pk;
    private static final String TAG = "ParcelRequestFirstPart";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottm_tab_2);

        kill2 = this;
        setupBottomNavigationViewEx();

        final EditText name=(EditText)findViewById(R.id.regEdit2);
        final EditText phone=(EditText)findViewById(R.id.regEdit1);
        final EditText address=(EditText)findViewById(R.id.regEdit3);
        Button next=(Button)findViewById(R.id.btnSubmit);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name=name.getText().toString();
                String Phone=phone.getText().toString();
                String Address=address.getText().toString();



                final CustomerDetails details=new CustomerDetails(Phone,Name,Address);

                ApiHandler.CheckInfo(ParcelRequestFirstPart.this, Phone, new CheckCustomer() {
                    @Override
                    public void onCallbackResponse(NewCustomerClass check) {
                        if(check.getPk().equals("-1"))
                        {
                            ApiHandler.AddCustomer(ParcelRequestFirstPart.this, details, new NewCustomer() {
                                @Override
                                public void onCallbackResponse(CustomerDetails details) {
                                    pk=details.getPk();
                                    Log.d(TAG, "onCallbackResponse: PK "+pk );
                                    Intent intent=new Intent(ParcelRequestFirstPart.this,ParcelReqSecondPart.class);
                                    intent.putExtra("pk",pk);
                                    intent.putExtra("receiver",details);
                                    startActivity(intent);
                                }
                            });

                        }
                        else
                        {
                            pk=check.getPk();
                            Intent intent=new Intent(ParcelRequestFirstPart.this,ParcelReqSecondPart.class);
                            intent.putExtra("pk",pk);
                            intent.putExtra("receiver",details);
                            startActivity(intent);
                        }


                    }
                });



            }
        });

    }

    private void setupBottomNavigationViewEx(){
        BottomNavigationViewEx bottomNavigationViewEx= findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationViewHelper(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(ParcelRequestFirstPart.this,bottomNavigationViewEx);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem menuItem=menu.getItem(Activity_Num);
        menuItem.setChecked(true);
    }

}
