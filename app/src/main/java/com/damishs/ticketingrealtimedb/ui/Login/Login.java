package com.damishs.ticketingrealtimedb.ui.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.damishs.ticketingrealtimedb.Models.Reader;
import com.damishs.ticketingrealtimedb.R;
import com.damishs.ticketingrealtimedb.ui.Reader.TokenReader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText emailID,password;
    Button btnSignIn,btnReaderLogin;
    TextView tvSignUp;

    FirebaseAuth mFirebaseAuth;

    DatabaseReference databaseReader;

    static Boolean isTokenReader = false;

    private  FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = Login.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(Login.this, R.color.color_bg_Dark));

        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();

        emailID = findViewById(R.id.editTextName);
        password = findViewById(R.id.editTextPassword);
        btnSignIn = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.textViewSignUp);
        btnReaderLogin = findViewById(R.id.btnReaderLogin);


        //Sign out if already signed in
        FirebaseAuth.getInstance().signOut();


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

                String email = emailID.getText().toString().trim();
                String pwd = password.getText().toString().trim();

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

                    mFirebaseAuth.signInWithEmailAndPassword(emailID.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(Login.this, "Login Error", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                final String LoggedUserEmail = emailID.getText().toString().trim();

                                if(LoggedUserEmail.equals("Admin@gmail.com")||LoggedUserEmail.equals("admin@gmail.com")){

                                    Toast.makeText(Login.this, "FirebaseAdmin : " + LoggedUserEmail, Toast.LENGTH_SHORT).show();
                                    Intent intentToHome = new Intent(Login.this,Admin.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("USEREMAIL",  LoggedUserEmail);
                                    intentToHome.putExtras(bundle);
                                    startActivity(intentToHome);

                                }else{

                                    Toast.makeText(Login.this, "You're Logged in!", Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(Login.this, "FirebaseUser : " + LoggedUserEmail, Toast.LENGTH_SHORT).show();
                                    Intent intentToHome = new Intent(Login.this,HomeActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("USEREMAIL",  LoggedUserEmail);
                                    intentToHome.putExtras(bundle);
                                    startActivity(intentToHome);
                                }
                            }
                        }
                    });
                }else{
                    Toast.makeText(Login.this, "Error Occured", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToSignUp = new Intent(Login.this,SignUp.class);
                startActivity(intentToSignUp);
            }
        });


        btnReaderLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToSignUp = new Intent(Login.this,LoginReader.class);
                startActivity(intentToSignUp);
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
