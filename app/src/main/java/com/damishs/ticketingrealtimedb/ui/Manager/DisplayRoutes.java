package com.damishs.ticketingrealtimedb.ui.Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.damishs.ticketingrealtimedb.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayRoutes extends AppCompatActivity {

    private EditText startpoint,endpoint;
    private Spinner sday,stime;
    private Button searchRoute;
    private DatabaseReference databaseRoute;
    private ArrayList<RouteModel> routeModels;
    private ListAdapterRoute adapter;
    private ListView RouterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_routes);


        startpoint=(EditText)findViewById(R.id.editText_start_po);
        endpoint=(EditText)findViewById(R.id.editText_end_po);
        sday=(Spinner)findViewById(R.id.spinner_day);
        stime=(Spinner)findViewById(R.id.spinner_time);
        searchRoute=(Button)findViewById(R.id.button_search_route);

        databaseRoute= FirebaseDatabase.getInstance().getReference("Routes");

        RouterList=(ListView)findViewById(R.id.listview_Route);
        routeModels=new ArrayList<>();

        searchRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(!TextUtils.isEmpty()){
//
//                }
                adapter.getFilter().filter(startpoint.getText().toString().trim()+"/"+endpoint.getText().toString().trim()+"/"+sday.getSelectedItem().toString().trim()+"/"+stime.getSelectedItem().toString().trim());
            }
        });

        viewAllFiles();
    }

    private void  viewAllFiles(){
        databaseRoute.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    RouteModel routeModel=postSnapshot.getValue(RouteModel.class);
                    routeModels.add(routeModel);
                    //Toast.makeText(DisplayRoutes.this, "a"+routeModel.getEndpoint(), Toast.LENGTH_SHORT).show();
                }
                adapter=new ListAdapterRoute(DisplayRoutes.this,R.layout.itemcardroute,routeModels);

                RouterList.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
