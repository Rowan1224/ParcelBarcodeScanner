package com.example.rifat.parcelbarcodescanner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<String> {
    Context mcontext;
    int mresource;
    ArrayList<String> place;
    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        mcontext=context;
        mresource=resource;
        place=objects;
    }

    class ViewHolder{
        TextView txt;
    }

    private String getItam(int position){
        return place.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            LayoutInflater inflater=LayoutInflater.from(mcontext);
            convertView=inflater.inflate(mresource,parent,false);
            holder=new ViewHolder();
            holder.txt= convertView.findViewById(R.id.txtspin);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder) convertView.getTag();
        }
        String place=getItam(position);
        holder.txt.setText(place);
        return convertView;
    }
}