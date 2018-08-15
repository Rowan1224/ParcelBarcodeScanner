package com.example.rifat.parcelbarcodescanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ParcelReqSecondPart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parcel_form);

        final Spinner deliverytype = (Spinner) findViewById(R.id.regspin);
        final Spinner description = (Spinner) findViewById(R.id.regspin2);

        ArrayList<String> type=new ArrayList<>();
        type.add("Delivery Type");
        type.add("Office");
        type.add("Home");

        ArrayList<String> type2=new ArrayList<>();
        type2.add("Yellow (Tk 80)");
        type2.add("White (Tk 150)");
        type2.add("Box (Tk 220)");
        type2.add("Container (Tk 1000)");
        final ArrayList<String> keys=new ArrayList<>();
        keys.add("Yellow");
        keys.add("White");
        keys.add("Box");
        keys.add("Container");

        SpinnerAdapter adapter=new SpinnerAdapter(ParcelReqSecondPart.this,
                R.layout.snippet_reg_spinner,type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deliverytype.setAdapter(adapter);

        SpinnerAdapter adapter2=new SpinnerAdapter(ParcelReqSecondPart.this,
                R.layout.snippet_reg_spinner,type2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        description.setAdapter(adapter2);

        Button submit=(Button)findViewById(R.id.btnSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomerDetails receiver= (CustomerDetails) getIntent().
                        getSerializableExtra("receiver");
                NewCustomerClass sender=UserInfo.user;


                DateFormat df = new SimpleDateFormat("ddMMyyyyHHmm");
                DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy-HH:mm");
                String date = df.format(Calendar.getInstance().getTime());


                String des=keys.get(description.getSelectedItemPosition());
                String delivery_type=deliverytype.getSelectedItem().toString();
                String receiver_phone=getIntent().getExtras().getString("pk");
                String phone=sender.getPk();// from user infromation class;
                Boolean temp=true;
                String QR_code=date+"-"+sender.getPhone()+"-"+receiver.getPhone();
                String destination_code=receiver.getAddress();
                String issue_time=df1.format(Calendar.getInstance().getTime());

                ParcelDetails details=new ParcelDetails(phone,receiver_phone,QR_code,delivery_type,des,
                        destination_code,issue_time,temp);

                ApiHandler.PostReq(ParcelReqSecondPart.this,details);

                finish();
                Intent intent=new Intent(ParcelReqSecondPart.this,MainActivity.class);
                startActivity(intent);








            }
        });
    }
}
