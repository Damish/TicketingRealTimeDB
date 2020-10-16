package com.damishs.ticketingrealtimedb.ui.Manager;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.damishs.ticketingrealtimedb.R;

import java.util.ArrayList;

public class ListAdapterRoute extends BaseAdapter implements Filterable {
    private static final String TAG="ListAdapteraddclass";
    private Context mContext;
    private int mResource;
    private ArrayList<RouteModel> object,original;
    private CustomFilter cm;



    public ListAdapterRoute(Context context, int resource, ArrayList<RouteModel> objects ){
        mContext=context;
        mResource=resource;
        object=objects;
        original=objects;
    }

    @Override
    public int getCount() {
        return original.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String startpointst=original.get(position).getStartpoint();
        String endpointst=original.get(position).getEndpoint();
        String dayst=original.get(position).getDay();
        String timest=original.get(position).getTimeperiod();
        String passengerst=original.get(position).getNoofpassengers();
        String pricest=original.get(position).getRouteprice();

        LayoutInflater inflater=LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);


//        String monthst=getItem(position).get();

//        Toast.makeText(mContext, "a:"+startpointst, Toast.LENGTH_SHORT).show();

        TextView start=(TextView)convertView.findViewById(R.id.txt_start);
        TextView stop=(TextView)convertView.findViewById(R.id.txt_stop);
        TextView day1=(TextView)convertView.findViewById(R.id.txt_day);
        TextView passenger1=(TextView)convertView.findViewById(R.id.txt_passengers);
        TextView time1=(TextView)convertView.findViewById(R.id.txt_time);
        TextView price1=(TextView)convertView.findViewById(R.id.txt_price);
        start.setText(startpointst);
        stop.setText(endpointst);
        day1.setText(dayst);
        time1.setText(timest);
        passenger1.setText(passengerst);
        price1.setText(pricest);


        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (cm==null){
            cm=new CustomFilter();
        }
        return cm;
    }

    class CustomFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results= new FilterResults();

            if (constraint!=null && constraint.length()>0) {
                constraint = constraint.toString().toUpperCase();
                ArrayList<RouteModel> filters = new ArrayList<>();

                String st=constraint.toString();

                String[] str=st.split("/");

                for (int i = 0; i < object.size(); i++) {
                    if (!TextUtils.isEmpty(str[1])){
                        if (object.get(i).getStartpoint().toUpperCase().contains(str[0]) && object.get(i).getEndpoint().toUpperCase().contains(str[1]) && object.get(i).getDay().toUpperCase().contains(str[2])&& object.get(i).getTimeperiod().toUpperCase().contains(str[3]) ){
                            RouteModel singlerow = new RouteModel(object.get(i).getRouteID(), object.get(i).getBus(),object.get(i).getDay(),object.get(i).getStartpoint(),object.get(i).getEndpoint(),object.get(i).getRouteprice(),object.get(i).getNoofpassengers(),object.get(i).getTotalFare(),object.get(i).getTimeperiod());
                            filters.add(singlerow);
                        }
                    }


                }
                results.count = filters.size();
                results.values = filters;
            }else {
                results.count=object.size();
                results.values=object;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            original=(ArrayList<RouteModel>)results.values;
            notifyDataSetChanged();
        }
    }
}
