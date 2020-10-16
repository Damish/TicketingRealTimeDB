package com.damishs.ticketingrealtimedb.ui.Lists;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.damishs.ticketingrealtimedb.R;
import com.damishs.ticketingrealtimedb.ui.home.Artist;

import java.util.List;

public class MyTripsList extends ArrayAdapter<MyTrip> {

    private Activity context;
    private List<MyTrip> tripsList;


    public MyTripsList(Activity context, List<MyTrip> tripsList) {
        super(context, R.layout.list_layout, tripsList);
        this.context = context;
        this.tripsList = tripsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.mytrips_list_layout,null,true);


        TextView textViewdepartureTime =listViewItem.findViewById(R.id.textViewdepartureTime);
        TextView textViewdepartureVenue =listViewItem.findViewById(R.id.textViewdepartureVenue);
        TextView textViewarrivalTime =listViewItem.findViewById(R.id.textViewarrivalTime);
        TextView textViewarrivalVenue =listViewItem.findViewById(R.id.textViewarrivalVenue);
        TextView textViewfareAmount =listViewItem.findViewById(R.id.textViewfareAmount);

        MyTrip myTrip = tripsList.get(position);

                textViewdepartureTime.setText(myTrip.getDepartureTime());
                textViewdepartureVenue.setText(myTrip.getDepartureVenue());
                textViewarrivalTime.setText(myTrip.getArrivalTime());
                textViewarrivalVenue.setText(myTrip.getArrivalVenue());
                textViewfareAmount.setText(myTrip.getFareAmount());

        return listViewItem;

    }
}
