package com.damishs.ticketingrealtimedb.ui.Login;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.damishs.ticketingrealtimedb.Models.Account;
import com.damishs.ticketingrealtimedb.Models.Passenger;
import com.damishs.ticketingrealtimedb.Models.Token;
import com.damishs.ticketingrealtimedb.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {


    TextView textViewToken, textViewBillingDetails;
    EditText editTextName, editTextUsername, editTextPassword, editTextNic;
    Button btnAddAccount, btnGetToken, btnCreateAccount;
    DatabaseReference databaseAccount;
    DatabaseReference databasePassenger;
    DatabaseReference databaseToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);


        databaseAccount = FirebaseDatabase.getInstance().getReference("accounts");
        databasePassenger = FirebaseDatabase.getInstance().getReference("passengers");
        databaseToken = FirebaseDatabase.getInstance().getReference("tokens");

        textViewBillingDetails = findViewById(R.id.textViewBillingAccountNo);
        textViewToken = findViewById(R.id.textViewToken);
        editTextName = findViewById(R.id.editTextName);
        editTextNic = findViewById(R.id.editTextNic);
        btnAddAccount = findViewById(R.id.btn_addBilling_Account);
        btnGetToken = findViewById(R.id.btn_getToken);
        btnCreateAccount = findViewById(R.id.btn_createAccount);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextUsername = findViewById(R.id.editTextUsername);


        btnGetToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //generate token id
                String tokenId = databaseToken.push().getKey();
                textViewToken.setText(tokenId);

                //hide get new token button
                Button btnGetToken = findViewById(R.id.btn_getToken);
                btnGetToken.setVisibility(View.GONE);
            }
        });


        btnAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editTextName.getText().toString().trim();
                String nic = editTextNic.getText().toString().trim();
                String billingDetails = textViewBillingDetails.getText().toString().trim();
                String tokenId = textViewToken.getText().toString().trim();

                if (!(TextUtils.isEmpty(name)) && !TextUtils.isEmpty(nic) && !TextUtils.isEmpty(tokenId) && TextUtils.isEmpty(billingDetails)) {

                    String customerId = databasePassenger.push().getKey();
                    showNewAccountDialog(customerId, tokenId, name);
                    Toast.makeText(view.getContext(), "Open Alert Dialog!!! Token Id : " + tokenId, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(view.getContext(), "Fill Blanks", Toast.LENGTH_SHORT).show();
                }

            }
        });


        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String accountNo = textViewBillingDetails.getText().toString();

                DatabaseReference databaseReferenceGeneratedPassengerID = FirebaseDatabase.getInstance().getReference("accounts").child(accountNo).child("customerID");

                databaseReferenceGeneratedPassengerID.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String passengerID = dataSnapshot.getValue(String.class);


                        //part-1 on create account- Add New Passenger data
                        String name = editTextName.getText().toString();
                        String nic = editTextNic.getText().toString();
                        String tokenID = textViewToken.getText().toString();
                        String accountNo = textViewBillingDetails.getText().toString();
                        String username = editTextUsername.getText().toString();
                        String password = editTextPassword.getText().toString();

                        if (!(TextUtils.isEmpty(name)) && !TextUtils.isEmpty(nic) && !TextUtils.isEmpty(tokenID) && !TextUtils.isEmpty(accountNo) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {

                            Passenger passenger = new Passenger(passengerID, nic, name, tokenID, accountNo, username, password);
                            //overwrite data to created id
                            databasePassenger.child(passengerID).setValue(passenger);


                            //part-2 on create account- Add New Token data
                            DatabaseReference databaseReferenceGeneratedToken = FirebaseDatabase.getInstance().getReference("tokens").child(tokenID);
                            Token token = new Token(tokenID, passengerID);
                            //overwrite data to created id
                            databaseReferenceGeneratedToken.setValue(token);
                            Toast.makeText(SignUp.this, "Account Created Sucessfully", Toast.LENGTH_SHORT).show();


                        } else {
                            Toast.makeText(SignUp.this, "Fill Blanks", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


            }
        });


    }


    private void showNewAccountDialog(final String customerID, final String tokenID, final String customerName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.add_account_dialog, null);

        dialogBuilder.setView(dialogView);

        final TextView textViewCustomerID = dialogView.findViewById(R.id.textViewCustomerID);
        final TextView textViewTokenID = dialogView.findViewById(R.id.textViewTokenID);
        final TextView textViewName = dialogView.findViewById(R.id.textViewName);
        final Button buttonCreatePaymentAccount = dialogView.findViewById(R.id.buttonCreatePaymentAccount);


        textViewCustomerID.setText(customerID);
        textViewTokenID.setText(tokenID);
        textViewName.setText(customerName);

        dialogBuilder.setTitle("Add new Account");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonCreatePaymentAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //Generate Account No
                String accountNo = databaseAccount.push().getKey();

                //set initial balance of account
                String Initial_Balance = "0";

                Account account = new Account(customerID, customerName, accountNo, tokenID, Initial_Balance);

                //overwrite data to created id
                databaseAccount.child(accountNo).setValue(account);
                Toast.makeText(view.getContext(), "Account added", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();

                TextView textViewBillingAccountNo = findViewById(R.id.textViewBillingAccountNo);
                textViewBillingAccountNo.setText(accountNo);

                //Set fields un editable - After creating an Account
                EditText editTextName = findViewById(R.id.editTextName);
                EditText editTextNic = findViewById(R.id.editTextNic);

                editTextName.setInputType(InputType.TYPE_NULL);
                editTextName.setTextIsSelectable(true);

                editTextNic.setInputType(InputType.TYPE_NULL);
                editTextNic.setTextIsSelectable(true);

                Button btnGetToken = findViewById(R.id.btn_addBilling_Account);
                btnGetToken.setVisibility(View.GONE);

            }

        });


    }


}
