package com.damishs.ticketingrealtimedb.ui.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.damishs.ticketingrealtimedb.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText emailID,password;
    Button btnSignIn;
    TextView tvSignUp;

    FirebaseAuth mFirebaseAuth;

    Button btnCreateAccount;

    private  FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();

        emailID = findViewById(R.id.editTextName);
        password = findViewById(R.id.editTextPassword);
        btnSignIn = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.textViewSignUp);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);

        //Sign out if already signed in
        FirebaseAuth.getInstance().signOut();

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SignUp.class);
                startActivity(i);
                finish();
            }
        });


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if( mFirebaseUser != null){
                    Toast.makeText(Login.this, "Youre Logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login.this,HomeActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(Login.this, "Please Login", Toast.LENGTH_SHORT).show();
                }

            }
        };

        btnSignIn.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(Login.this, "Fill Blanks", Toast.LENGTH_SHORT).show();
                }
                else if(! (email.isEmpty() && pwd.isEmpty() )){


                    mFirebaseAuth.signInWithEmailAndPassword(emailID.getText().toString(), password.getText().toString()).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(!task.isSuccessful()){
                                Toast.makeText(Login.this, "Login Error", Toast.LENGTH_SHORT).show();
                            }
                            else{

                                String LoggedUserEmail = emailID.getText().toString();

                                Toast.makeText(Login.this, "FirebaseUser : " + LoggedUserEmail, Toast.LENGTH_SHORT).show();

                                Intent intentToHome = new Intent(Login.this,HomeActivity.class);

                                Bundle bundle = new Bundle();
                                bundle.putString("USEREMAIL",  LoggedUserEmail);
                                intentToHome.putExtras(bundle);

                                startActivity(intentToHome);

                            }

                        }
                    });

                }else{
                    Toast.makeText(Login.this, "Error Occured", Toast.LENGTH_SHORT).show();
                }

            }
        });

//        tvSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentToSignUp = new Intent(Login.this,SignUp2.class);
//                startActivity(intentToSignUp);
//            }
//        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
