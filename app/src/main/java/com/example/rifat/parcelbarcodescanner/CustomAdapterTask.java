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
import java.util.List;

public class CustomAdapterTask extends ArrayAdapter<UserRequests>{

    Context context;
    private List<UserRequests> TaskList=new ArrayList<>();

    public CustomAdapterTask(@NonNull Context context, List<UserRequests> taskList) {
        super(context,R.layout.req_list,taskList);
        this.context = context;
        TaskList = taskList;
    }

    public static class ViewHolder{

        TextView sphone;
        TextView rphone;
        TextView time;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        UserRequests userRequests=getItem(position);
        ViewHolder viewHolder;

        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(context);
            convertView=inflater.inflate(R.layout.req_list,parent,false);

            viewHolder.rphone=(TextView)convertView.findViewById(R.id.txtDestinationCode);
            viewHolder.sphone=(TextView)convertView.findViewById(R.id.txtDescription);
            viewHolder.time=(TextView)convertView.findViewById(R.id.txtIssueTime);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder)convertView.getTag();

        }

        assert userRequests != null;

        if(position%2 == 0){
            convertView.setBackgroundResource(R.color.exp1);
        }else{
            convertView.setBackgroundResource(R.color.white);
        }

        viewHolder.sphone.setText(userRequests.getDescription());
        viewHolder.rphone.setText(userRequests.getDestination_code());
        viewHolder.time.setText(userRequests.getIssue_time());

        return convertView;

    }
}
