package com.damishs.ticketingrealtimedb.ui.Manager;

import androidx.appcompat.app.AppCompatActivity;
import com.damishs.ticketingrealtimedb.R;
import com.damishs.ticketingrealtimedb.ui.home.Artist;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddRoutes extends AppCompatActivity {
    private EditText Bus,Startp,endp,fare,totfare,totpassngr,validpssngr,invalipssngr;
    private Spinner time,day;
    private Button Add_Route;
    DatabaseReference databaseArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_routes);

        Bus=(EditText)findViewById(R.id.editText_bus);
        Startp=(EditText)findViewById(R.id.editText_start_p);
        endp=(EditText)findViewById(R.id.editText_end_p);
        fare=(EditText)findViewById(R.id.editText_price);
        Add_Route=(Button)findViewById(R.id.button_add_routes);
        totfare=(EditText)findViewById(R.id.txt_totfare);
        totpassngr=(EditText) findViewById(R.id.txt_no_passengr);
        validpssngr=(EditText) findViewById(R.id.txt_valid_pssngr);
        invalipssngr=(EditText)findViewById(R.id.txt_invalidp);
        time=(Spinner)findViewById(R.id.spinner_time);
        day=(Spinner)findViewById(R.id.txt_day);


        databaseArtists = FirebaseDatabase.getInstance().getReference("Routes");



        Add_Route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddRoutes();
            }
        });
    }
    //adding Routes

    public void AddRoutes(){


        String bus2 =Bus.getText().toString().trim();
        String start2 =Startp.getText().toString().trim();
        String end2 =endp.getText().toString().trim();
        String fare2 =fare.getText().toString().trim();
        String  totfare2= totfare.getText().toString().trim();
        String totpassngr2=totpassngr.getText().toString().trim();
        String validpssngr2=validpssngr.getText().toString().trim();
        String invalipssngr2=invalipssngr.getText().toString().trim();
        String day2=day.getSelectedItem().toString().trim();
        String time2=time.getSelectedItem().toString().trim();




        if (!TextUtils.isEmpty(bus2)||!TextUtils.isEmpty(start2)||!TextUtils.isEmpty(end2)||!TextUtils.isEmpty(fare2)) {

            //unique id is generated
            String id = databaseArtists.push().getKey();
            RouteModel routeModel=new RouteModel(id, bus2, day2, start2, end2, fare2, totpassngr2, invalipssngr2, totfare2, time2,validpssngr2);


            //overwrite data to created id
            databaseArtists.child(id).setValue(routeModel);

            Toast.makeText(this, "Added successfully!!!", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(this, "You should enter name!!!", Toast.LENGTH_SHORT).show();
        }



    }
}
