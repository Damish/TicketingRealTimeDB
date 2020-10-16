package com.damishs.ticketingrealtimedb.ui.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.damishs.ticketingrealtimedb.Models.Reader;
import com.damishs.ticketingrealtimedb.R;
import com.damishs.ticketingrealtimedb.ui.Reader.TokenReader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginReader extends AppCompatActivity {

    EditText editTextReaderName;
    Button btnSignIn;

    DatabaseReference databaseReader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_reader);


        btnSignIn = findViewById(R.id.btnLogin);
        editTextReaderName = findViewById(R.id.editTextReaderID);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editTextReaderName.getText().toString();

                if (name.isEmpty()) {
                    editTextReaderName.setError("Please enter Reader name");
                    editTextReaderName.requestFocus();
                    Toast.makeText(LoginReader.this, "Fill Blanks", Toast.LENGTH_SHORT).show();
                } else if (!(name.isEmpty())) {

                    databaseReader = FirebaseDatabase.getInstance().getReference("readers").child(editTextReaderName.getText().toString());

                    //check if its a token reader
                    databaseReader.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() != null) {

                                Reader tokenReader = snapshot.getValue(Reader.class);

                                Toast.makeText(LoginReader.this, "Token Reader Data: " + tokenReader.getName(), Toast.LENGTH_SHORT).show();

                                Intent intentToReader = new Intent(LoginReader.this, TokenReader.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("READERID", tokenReader.getName());
                                bundle.putString("READERLOCATION", tokenReader.getReaderLocation());
                                intentToReader.putExtras(bundle);
                                startActivity(intentToReader);

                            }else{
                                Toast.makeText(LoginReader.this, "Reader not found!!!", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }


            }

        });
    }

}
