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
import com.damishs.ticketingrealtimedb.ui.Lists.MyTripsActivity;
import com.damishs.ticketingrealtimedb.ui.Lists.MyTripsList;
import com.damishs.ticketingrealtimedb.ui.Payment.PaymentActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    Button btnLogOut,btnMyTrips,btnAddCredits;
    TextView textViewUserID;
    TextView textViewName1, textViewAccountNo1, textViewPassengerID1, textViewNic1, textViewUsername1;
    EditText editTextTokenGenerated;

    DatabaseReference databasePassengers, databaseLogs;

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
        btnMyTrips = findViewById(R.id.btnMyTrips);
        btnAddCredits= findViewById(R.id.btnAddCredits);

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


        btnMyTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToHome = new Intent(HomeActivity.this, MyTripsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("USEREMAIL",  textViewUsername1.getText().toString());
                intentToHome.putExtras(bundle);
                startActivity(intentToHome);
            }
        });

        btnAddCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToHome = new Intent(HomeActivity.this, PaymentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("USERACCNO",  textViewAccountNo1.getText().toString());
                intentToHome.putExtras(bundle);
                startActivity(intentToHome);
            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        FirebaseAuth.getInstance().signOut();

    }

}
