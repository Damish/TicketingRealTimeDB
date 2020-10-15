package com.damishs.ticketingrealtimedb.ui.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.damishs.ticketingrealtimedb.Models.Passenger;
import com.damishs.ticketingrealtimedb.Models.Token;
import com.damishs.ticketingrealtimedb.R;
import com.damishs.ticketingrealtimedb.ui.Lists.MyTrip;
import com.damishs.ticketingrealtimedb.ui.Lists.MyTripsList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    Button btnLogOut;
    TextView textViewUserID;

    TextView textViewName1, textViewAccountNo1, textViewPassengerID1, textViewNic1, textViewUsername1;

    EditText editTextTokenGenerated;

    DatabaseReference databasePassengers, databaseLogs;


    ListView listViewMyTrips;

    List<MyTrip> MyTripsList;


    private static final String TAG = HomeActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnLogOut = findViewById(R.id.btnLogOut);
        textViewUserID = findViewById(R.id.textViewUserID);
        editTextTokenGenerated = findViewById(R.id.editTextTokenGenerated);
        textViewName1 = findViewById(R.id.textViewName1);
        textViewAccountNo1 = findViewById(R.id.textViewAccountNo1);
        textViewPassengerID1 = findViewById(R.id.textViewPassengerID1);
        textViewNic1 = findViewById(R.id.textViewNic1);
        textViewUsername1 = findViewById(R.id.textViewUsername1);

        listViewMyTrips = findViewById(R.id.listViewMyTrips);

        MyTripsList = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        final String UserEmail = bundle.getString("USEREMAIL");
        textViewUserID.setText("Hello, " + UserEmail);


        databasePassengers = FirebaseDatabase.getInstance().getReference("passengers");
        databasePassengers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot artistSnapShot : snapshot.getChildren()) {
                    final Passenger passenger = artistSnapShot.getValue(Passenger.class);

                    if (passenger.getUsername().equals(UserEmail)) {

                        Log.d(TAG, "Passenger Name: " + passenger.getName());
                        Log.d(TAG, "Passenger Acc No: " + passenger.getAccountNo());
                        Log.d(TAG, "Passenger Id: " + passenger.getId());
                        Log.d(TAG, "Passenger Nic: " + passenger.getNic());
                        Log.d(TAG, "Passenger Username: " + passenger.getUsername());
                        Log.d(TAG, "Passenger Token ID: " + passenger.getTokenID());

                        editTextTokenGenerated.setText(passenger.getTokenID());
                        textViewName1.setText(passenger.getName());
                        textViewAccountNo1.setText(passenger.getAccountNo());
                        textViewPassengerID1.setText(passenger.getId());
                        textViewNic1.setText(passenger.getNic());
                        textViewUsername1.setText(passenger.getUsername());


                        databaseLogs = FirebaseDatabase.getInstance().getReference("logs");
                        databaseLogs.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                MyTripsList.clear();

                                for (DataSnapshot child : dataSnapshot.getChildren()) {

                                    Token log = child.getValue(Token.class);

                                    if (log.getTokenID().equals(passenger.getTokenID())) {
                                        Toast.makeText(HomeActivity.this, "Log Message Departure: " + log.getDepartureVenue(), Toast.LENGTH_LONG).show();


                                        MyTrip myTrip = new MyTrip(log.getDepartureTime(), log.getDepartureVenue(), log.getArrivalTime(), log.getArrivalVenue(), log.getFareAmount());
                                        MyTripsList.add(myTrip);


                                        ArrayAdapter adapter = new MyTripsList(HomeActivity.this, MyTripsList);
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


        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(HomeActivity.this, Login.class);
                startActivity(intToMain);
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        FirebaseAuth.getInstance().signOut();

    }

}
