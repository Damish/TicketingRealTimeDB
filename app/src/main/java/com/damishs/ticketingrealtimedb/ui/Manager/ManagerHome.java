package com.damishs.ticketingrealtimedb.ui.Manager;

import androidx.appcompat.app.AppCompatActivity;
import com.damishs.ticketingrealtimedb.R;

import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.Button;

public class ManagerHome extends AppCompatActivity {
    private Button btnAdd_Route,search_Route,travelInfo,farecollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home);

        btnAdd_Route=(Button)findViewById(R.id.button5Add_Routes);
        search_Route=(Button)findViewById(R.id.button_route_Details);
        travelInfo=(Button)findViewById(R.id.button_fare_details);
        farecollection=(Button)findViewById(R.id.btn_TravelInfo);

        btnAdd_Route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click_activity_Add();
            }
        });

        search_Route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Move_activity_Routedetail();
            }
        });

        travelInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Move_activity_fare();
            }
        });

        farecollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Move_travel_Info();
            }
        });



    }

    public void Click_activity_Add(){
        Intent intent=new Intent(this,AddRoutes.class);
        startActivity(intent);
    }
    public void Move_activity_Routedetail(){
        Intent intent=new Intent(this,DisplayRoutes.class);
        startActivity(intent);
    }
    public void Move_activity_fare(){
        Intent intent=new Intent(this,DisplayFareDetails.class);
        startActivity(intent);
    }
    public void Move_travel_Info(){
        Intent intent=new Intent(this,DisplayTravelInfo.class);
        startActivity(intent);
    }


}
