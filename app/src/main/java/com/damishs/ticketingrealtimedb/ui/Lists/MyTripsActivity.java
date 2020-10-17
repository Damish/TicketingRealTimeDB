package com.damishs.ticketingrealtimedb.ui.Lists;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.damishs.ticketingrealtimedb.Models.Passenger;
import com.damishs.ticketingrealtimedb.Models.Token;
import com.damishs.ticketingrealtimedb.R;
import com.damishs.ticketingrealtimedb.ui.Login.HomeActivity;
import com.damishs.ticketingrealtimedb.ui.Login.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyTripsActivity extends AppCompatActivity {

    DatabaseReference databasePassengers, databaseLogs;

    Button btnGoBack;

    ListView listViewMyTrips;
    List<MyTrip> MyTripsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trips);

        listViewMyTrips = findViewById(R.id.listViewMyTrips);
        MyTripsList = new ArrayList<>();

        btnGoBack = findViewById(R.id.btnGoBack);


        Bundle bundle = getIntent().getExtras();
        final String UserEmail = bundle.getString("USEREMAIL");


        databasePassengers = FirebaseDatabase.getInstance().getReference("passengers");
        databasePassengers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot artistSnapShot : snapshot.getChildren()) {
                    final Passenger passenger = artistSnapShot.getValue(Passenger.class);

                    if (passenger.getUsername().equals(UserEmail)) {


                        databaseLogs = FirebaseDatabase.getInstance().getReference("logs");
                        databaseLogs.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                MyTripsList.clear();

                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    Token log = child.getValue(Token.class);

                                    if (log.getTokenID().equals(passenger.getTokenID())) {
                                        //Toast.makeText(MyTripsActivity.this, "Log Message Departure: " + log.getDepartureVenue(), Toast.LENGTH_LONG).show();

                                        MyTrip myTrip = new MyTrip(log.getDepartureTime(), log.getDepartureVenue(), log.getArrivalTime(), log.getArrivalVenue(), log.getFareAmount());
                                        MyTripsList.add(myTrip);

                                        ArrayAdapter adapter = new MyTripsList(MyTripsActivity.this, MyTripsList);
                                        listViewMyTrips.setAdapter(adapter);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intToMain = new Intent(MyTripsActivity.this, HomeActivity.class);
                startActivity(intToMain);
                finish();
            }
        });


    }




}
