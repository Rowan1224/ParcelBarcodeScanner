package com.example.rifat.parcelbarcodescanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavigationViewHelper {
    public static void setupBottomNavigationViewHelper(BottomNavigationViewEx bottomNavigationViewEx){
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
        bottomNavigationViewEx.setIconSize(30,30);
    }
    public static void enableNavigation(final Context context,BottomNavigationViewEx viewEx){
        viewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.ic_scan:
                        Intent intent1=new Intent(context, MainActivity.class);
                        //Activity_Num=0
                        context.startActivity(intent1);

                        if(context.getClass().getSimpleName().equals("ParcelRequestFirstPart")){
                            ParcelRequestFirstPart.kill2.finish();
                        }else if(context.getClass().getSimpleName().equals("UserRequestList")){
                            UserRequestList.kill3.finish();
                        }
                        break;
                    case R.id.ic_new:
                        Intent intent2=new Intent(context, ParcelRequestFirstPart.class);
                        //Activity_Num=1
                        context.startActivity(intent2);

                        if(context.getClass().getSimpleName().equals("MainActivity")){
                            MainActivity.kill.finish();
                        }else if(context.getClass().getSimpleName().equals("UserRequestList")){
                            UserRequestList.kill3.finish();
                        }
                        break;
                    case R.id.ic_request:
                        Intent intent3=new Intent(context, UserRequestList.class);
                        //Activity_Num=2
                        context.startActivity(intent3);

                        if(context.getClass().getSimpleName().equals("MainActivity")){
                            MainActivity.kill.finish();
                        }else if(context.getClass().getSimpleName().equals("ParcelRequestFirstPart")){
                            ParcelRequestFirstPart.kill2.finish();
                        }
                        break;
                }
                return false;
            }
        });
    }
}
