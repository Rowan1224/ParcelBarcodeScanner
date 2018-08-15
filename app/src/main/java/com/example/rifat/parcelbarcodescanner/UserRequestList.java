package com.example.rifat.parcelbarcodescanner;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

public class UserRequestList extends AppCompatActivity {

    private static final int Activity_Num=2;
    public static Activity kill3;

    List<UserRequests> results=new ArrayList<>();
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottm_tab_3);

        kill3 = this;
        setupBottomNavigationViewEx();

        list=(ListView)findViewById(R.id.tasklist);


        ApiHandler.Requests(UserRequestList.this, new UserReq() {
            @Override
            public void onCallbackResponse(List<UserRequests> userRequests) {
                CustomAdapterTask customAdapterTask=
                        new CustomAdapterTask(UserRequestList.this,userRequests);
                customAdapterTask.notifyDataSetChanged();
                results=userRequests;
                list.setAdapter(customAdapterTask);
            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                UserRequests requests=results.get(i);
                Intent intent=new Intent(UserRequestList.this,QR_Code.class);
                intent.putExtra("QR",requests.getQR_code());
                startActivity(intent);
            }
        });
    }

    private void setupBottomNavigationViewEx(){
        BottomNavigationViewEx bottomNavigationViewEx= findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationViewHelper(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(UserRequestList.this,bottomNavigationViewEx);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem menuItem=menu.getItem(Activity_Num);
        menuItem.setChecked(true);
    }

}
