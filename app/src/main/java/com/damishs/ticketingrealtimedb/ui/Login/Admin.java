package com.damishs.ticketingrealtimedb.ui.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.damishs.ticketingrealtimedb.Models.Reader;
import com.damishs.ticketingrealtimedb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin extends AppCompatActivity {

    EditText location, readerName,editTextReaderID;
    Button btnSignUp, btnLogOut;

    FirebaseAuth mFirebaseAuth;

    DatabaseReference databaseReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mFirebaseAuth = FirebaseAuth.getInstance();

        location = findViewById(R.id.editTextLocationAdmin);
        btnSignUp = findViewById(R.id.btnSignUpAdmin);
        btnLogOut = findViewById(R.id.btnLogOut);
        readerName = findViewById(R.id.readerName);
        editTextReaderID = findViewById(R.id.editTextReaderID);

        databaseReader = FirebaseDatabase.getInstance().getReference("readers");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String name1 = readerName.getText().toString();
                String location1 = location.getText().toString();

                if (name1.isEmpty()) {
                    readerName.setError("Please enter Reader name");
                    readerName.requestFocus();
                } else if (location1.isEmpty()) {
                    location.setError("Please enter your Reader Location");
                    location.requestFocus();
                } else if (name1.isEmpty() && location1.isEmpty()) {
                    Toast.makeText(Admin.this, "Fill Blanks", Toast.LENGTH_SHORT).show();
                } else if (!(name1.isEmpty() && location1.isEmpty())) {

                    Reader reader = new Reader(readerName.getText().toString(), location.getText().toString());
                    //create station from emailID

                    String childId = databaseReader.push().getKey();
                    databaseReader.child(childId).setValue(reader);

                    editTextReaderID.setText(childId);

                    Toast.makeText(Admin.this, "New Reader added Sucessfully", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(Admin.this, "Error Occured", Toast.LENGTH_SHORT).show();
                }

            }
        });


        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(Admin.this, Login.class);
                startActivity(intToMain);
                finish();
            }
        });


    }
}
