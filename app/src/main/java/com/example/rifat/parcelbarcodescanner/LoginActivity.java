package com.example.rifat.parcelbarcodescanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button login;
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.btnlogin);

        final EditText phone=(EditText)findViewById(R.id.logEdit1);
        final EditText pass= (EditText)findViewById(R.id.logEdit2);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ApiHandler.CheckInfo(LoginActivity.this, phone.getText().toString(), new CheckCustomer() {
                    @Override
                    public void onCallbackResponse(NewCustomerClass customerClass) {



                        if(customerClass.getPk().equals("-1"))
                        {
                            Toast.makeText(LoginActivity.this,"your Account is not valid",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                           if(pass.getText().toString().equals(customerClass.getPassword()))
                           {
                               UserInfo.user=customerClass;
                               startActivity(new Intent(LoginActivity.this,MainActivity.class));
                           }
                           else
                           {
                               Toast.makeText(LoginActivity.this,"your Password is wrong",Toast.LENGTH_SHORT).show();
                           }
                        }
                    }
                });

            }
        });

        TextView newCustomer=(TextView)findViewById(R.id.txt2);
        newCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,UserRegistration.class));
            }
        });
    }
}
