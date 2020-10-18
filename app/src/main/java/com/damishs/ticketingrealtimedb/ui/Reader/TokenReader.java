package com.damishs.ticketingrealtimedb.ui.Reader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.damishs.ticketingrealtimedb.Models.Token;
import com.damishs.ticketingrealtimedb.R;
import com.damishs.ticketingrealtimedb.ui.Login.Admin;
import com.damishs.ticketingrealtimedb.ui.Login.Login;
import com.damishs.ticketingrealtimedb.ui.Login.LoginReader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;

public class TokenReader extends AppCompatActivity {

    TextView textViewLocation,textViewTokenID;
    EditText editTextTokenID;
    Button buttonTap,buttonLogoutReader;

    DatabaseReference databaseTokens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader1);

        textViewTokenID = findViewById(R.id.textViewTokenID1);
        textViewLocation = findViewById(R.id.textViewLocation);
        editTextTokenID = findViewById(R.id.editTextTokenID);
        buttonTap = findViewById(R.id.buttonTap);
        buttonLogoutReader = findViewById(R.id.buttonLogoutReader);

        Bundle bundle = getIntent().getExtras();
        String readerID = bundle.getString("READERID");
        final String readerLocation = bundle.getString("READERLOCATION");

        textViewTokenID.setText(readerID);
        textViewLocation.setText("Token Reader - "+ readerLocation);



        buttonTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = editTextTokenID.getText().toString();
                if(id.equals("")){
                    editTextTokenID.setError("Please enter token id");
                    editTextTokenID.requestFocus();
                }else {


                    databaseTokens = FirebaseDatabase.getInstance().getReference("tokens").child(id);

                    //check if its a token reader
                    databaseTokens.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() != null) {

                                Token token = snapshot.getValue(Token.class);

                                String tokenID = token.getTokenID();
                                String customerID = token.getCustomerID();
                                String departureTime = token.getDepartureTime();
                                String departureVenue = token.getDepartureVenue();
                                String arrivalTime = token.getArrivalTime();
                                String arrivalVenue = token.getArrivalVenue();
                                String fareAmount = token.getFareAmount();


//                            Toast.makeText(TokenReader.this, "Token ID tapped: "+snapshot.getValue(), Toast.LENGTH_LONG).show();

                                if (departureVenue.equals("")) {
                                    String timeDeparture = LocalDateTime.now().toString();
                                    updateDeparture(tokenID, readerLocation, timeDeparture);

                                } else if (arrivalVenue.equals("")) {
                                    String timeArrival = LocalDateTime.now().toString();
                                    updateArrival(tokenID, readerLocation, timeArrival);


                                    //calculate fare amount

                                    int amount = 1000;
                                    fareAmount = String.valueOf(amount);


                                    Token logsToken = new Token(tokenID, customerID, departureTime, departureVenue, timeArrival, readerLocation, fareAmount);

                                    //Create trip details logs here
                                    DatabaseReference databaseReferenceLogs = FirebaseDatabase.getInstance().getReference("logs");
                                    String logId = databaseReferenceLogs.push().getKey();
                                    databaseReferenceLogs.child(logId).setValue(logsToken);


                                    resetTokenData(tokenID);

                                } else {
                                    Toast.makeText(TokenReader.this, "Token ID Entered :" + tokenID, Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(TokenReader.this, "Invalid Token", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                editTextTokenID.setText("");

            }
        });


        buttonLogoutReader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intToLogin = new Intent(TokenReader.this, LoginReader.class);
                startActivity(intToLogin);
                finish();
            }
        });



    }


    private boolean updateDeparture(String id,String departure,String timeDeparture) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("tokens").child(id).child("departureVenue");
        databaseReference.setValue(departure);

        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("tokens").child(id).child("departureTime");

        databaseReference2.setValue(timeDeparture);

        Toast.makeText(this, "Updated Destination Venue & Time,Trip Started at "+timeDeparture, Toast.LENGTH_LONG).show();

        return true;
    }

    private boolean updateArrival(String id,String arrival,String timeArrival) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("tokens").child(id).child("arrivalVenue");
        databaseReference.setValue(arrival);

        DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference("tokens").child(id).child("arrivalTime");

        databaseReference3.setValue(timeArrival);

        Toast.makeText(this, "Updated Arrival Venue & Time,Trip Ended!!! Now in,"+arrival+" Time: "+timeArrival, Toast.LENGTH_LONG).show();


        return true;
    }


    public void resetTokenData(String id){

        //Reset both values
        DatabaseReference databaseReferenceDepartureVenue = FirebaseDatabase.getInstance().getReference("tokens").child(id).child("departureVenue");
        databaseReferenceDepartureVenue.setValue("");

        DatabaseReference databaseReferenceArrivalVenue = FirebaseDatabase.getInstance().getReference("tokens").child(id).child("arrivalVenue");
        databaseReferenceArrivalVenue.setValue("");

        DatabaseReference databaseReferenceDepartureTime = FirebaseDatabase.getInstance().getReference("tokens").child(id).child("departureTime");
        databaseReferenceDepartureTime.setValue("");

        DatabaseReference databaseReferenceArrivalTime = FirebaseDatabase.getInstance().getReference("tokens").child(id).child("arrivalTime");
        databaseReferenceArrivalTime.setValue("");


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Sign out if already signed in
        FirebaseAuth.getInstance().signOut();

    }
}
