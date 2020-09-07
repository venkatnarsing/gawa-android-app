package org.gawa.owner;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewAdapter extends BaseAdapter{

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    public static final String OWNER_NAME_COLUMN ="ownerName";
    public static final String PHONE_NUMBER_COLUMN ="phoneNumber";
    public static final String PLOT_NUMBER_COLUMN ="plotNumber";

    public ListViewAdapter(Activity activity,ArrayList<HashMap<String, String>> list){
        super();
        this.activity=activity;
        this.list=list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private class ViewHolder{
        TextView textOwnerName;
        TextView textPhoneNumber;
        TextView textPlotNumber;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ViewHolder holder;

        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){

            convertView=inflater.inflate(R.layout.list_owners, null);
            holder=new ViewHolder();

            holder.textOwnerName =(TextView) convertView.findViewById(R.id.textOwnerName);
            holder.textPhoneNumber =(TextView) convertView.findViewById(R.id.textPhoneNumber);
            holder.textPlotNumber =(TextView) convertView.findViewById(R.id.textPlotNumber);

            convertView.setTag(holder);
        }else{

            holder=(ViewHolder) convertView.getTag();
        }

        HashMap<String, String> map=list.get(position);
        holder.textOwnerName.setText(map.get(OWNER_NAME_COLUMN));
        holder.textPhoneNumber.setText(map.get(PHONE_NUMBER_COLUMN));
        holder.textPlotNumber.setText(map.get(PLOT_NUMBER_COLUMN));

        return convertView;
    }

}
