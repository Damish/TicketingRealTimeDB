package com.damishs.ticketingrealtimedb.ui.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.damishs.ticketingrealtimedb.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp2 extends AppCompatActivity {

    EditText emailID,password;
    Button btnSignUp;
    TextView tvSignIn;

    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        mFirebaseAuth = FirebaseAuth.getInstance();

        emailID = findViewById(R.id.editTextNameSignUp);
        password = findViewById(R.id.editTextPasswordSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvSignIn = findViewById(R.id.textViewLogin);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailID.getText().toString();
                String pwd = password.getText().toString();

                if(email.isEmpty()){
                    emailID.setError("Please enter email id");
                    emailID.requestFocus();
                }else if (pwd.isEmpty()){
                    password.setError("Please enter your password");
                    emailID.requestFocus();
                }
                else if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(SignUp2.this, "Fill Blanks", Toast.LENGTH_SHORT).show();
                }
                else if(! (email.isEmpty() && pwd.isEmpty() )){


                    mFirebaseAuth.createUserWithEmailAndPassword(emailID.getText().toString(), password.getText().toString()).addOnCompleteListener(SignUp2.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(SignUp2.this, "Signup Sucessful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUp2.this,HomeActivity.class));

                            }else{
                                Toast.makeText(SignUp2.this, "Signup Unsucessful, Please try Again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(SignUp2.this, "Error Occured", Toast.LENGTH_SHORT).show();
                }

            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUp2.this,Login.class);
                startActivity(i);

            }
        });


    }
}
