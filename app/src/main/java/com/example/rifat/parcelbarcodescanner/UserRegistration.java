package com.example.rifat.parcelbarcodescanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.api.Api;

public class UserRegistration extends AppCompatActivity {

    String phone,name,address,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_register);



        Button btn=(Button)findViewById(R.id.btnSubmit);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText editText1=(EditText)findViewById(R.id.regEdit1);
                EditText editText2=(EditText)findViewById(R.id.regEdit2);
                EditText editText3=(EditText)findViewById(R.id.regEdit3);
                EditText editText4=(EditText)findViewById(R.id.regEdit4);


                phone=editText2.getText().toString();
                name=editText1.getText().toString();
                address=editText3.getText().toString();
                password=editText4.getText().toString();



                ApiHandler.CheckInfo(UserRegistration.this, phone, new CheckCustomer() {
                    @Override
                    public void onCallbackResponse(NewCustomerClass pk) {

                        if (pk.getPk().equals("-1")) {
                            final NewCustomerClass customerClass = new NewCustomerClass(phone, name, address, password);


                            ApiHandler.AddNewCustomer(UserRegistration.this, customerClass, new AddNewCustomer() {
                                @Override
                                public void onCallbackResponse(NewCustomerClass details) {

                                    UserInfo.user=details;
                                }
                            });

                        }
                        else {
                            final NewCustomerClass customerClass = new NewCustomerClass(pk.getPk(),phone, name, address, password);


                            ApiHandler.UpdateUser(UserRegistration.this, customerClass, new AddNewCustomer() {
                                @Override
                                public void onCallbackResponse(NewCustomerClass details) {
                                    UserInfo.user=details;

                                    startActivity(new Intent(UserRegistration.this,MainActivity.class));
                                }
                            });


                        }
                    }
                });




            }
        });




    }
}
