package com.damishs.ticketingrealtimedb.ui.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.damishs.ticketingrealtimedb.R;

public class Login extends AppCompatActivity {

    Button btnCreateAccount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);


        btnCreateAccount = findViewById(R.id.btnCreateAccount);


        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SignUp.class);
                startActivity(i);
                finish();
            }
        });

    }
}
