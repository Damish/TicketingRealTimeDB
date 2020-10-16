package com.damishs.ticketingrealtimedb.ui.Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.damishs.ticketingrealtimedb.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayFareDetails extends AppCompatActivity {

    private DatabaseReference databaseRoute;
    private BarChart barChart;
    private ArrayList<RouteModel> routeModels;
    private EditText startpt,endpt;
    private int Monday,Tuesday,Wednesday,Thursday,Friday;
    private Button search_fare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_fare_details);


        databaseRoute= FirebaseDatabase.getInstance().getReference("Routes");
        startpt=(EditText)findViewById(R.id.editText_startpt);
        endpt=(EditText)findViewById(R.id.editText_stoppt);
        search_fare=(Button)findViewById(R.id.btn_fare);
        barChart=(BarChart)findViewById(R.id.bar_chart_fare);


        search_fare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty( startpt.getText().toString().trim()) && TextUtils.isEmpty( endpt.getText().toString().trim()) ){
                    Toast.makeText(DisplayFareDetails.this, "missing data", Toast.LENGTH_SHORT).show();

                }else {
                    Monday=0;
                    Tuesday=0;
                    Wednesday=0;
                    Thursday=0;
                    Friday=0;

                    ViewAll();
                }
            }
        });




    }

    private void ViewAll(){

        databaseRoute.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    RouteModel routeModel=postSnapshot.getValue(RouteModel.class);


                        if ( TextUtils.equals(routeModel.getStartpoint(),startpt.getText().toString()) && TextUtils.equals(routeModel.getEndpoint(),endpt.getText().toString())){

                            if (TextUtils.equals("Monday", routeModel.getDay())){
                                Monday+=Integer.parseInt(routeModel.getTotalFare());
                            }else if (TextUtils.equals("Tuesday", routeModel.getDay())){
                                Tuesday+=Integer.parseInt(routeModel.getTotalFare());
                            }else if (TextUtils.equals("Wednesday", routeModel.getDay())){
                                Wednesday+=Integer.parseInt(routeModel.getTotalFare());
                            }else if (TextUtils.equals("Thursday", routeModel.getDay())){
                                Thursday+=Integer.parseInt(routeModel.getTotalFare());
                            }else if (TextUtils.equals("Friday", routeModel.getDay())){
                                Friday+=Integer.parseInt(routeModel.getTotalFare());
                            }

                        }

                }
                if (Monday==0 && Tuesday==0 && Wednesday==0 && Thursday==0 && Friday==0){
                    Toast.makeText(DisplayFareDetails.this, "No such Root!!!", Toast.LENGTH_SHORT).show();
                }
                DisplayChart();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    public void DisplayChart(){





        ArrayList<BarEntry> barEntries=new ArrayList<>() ;
        barEntries.add(new BarEntry((float)Monday,0));
        barEntries.add(new BarEntry((float)Tuesday,1));
        barEntries.add(new BarEntry((float)Wednesday,2));
        barEntries.add(new BarEntry((float)Thursday,3));
        barEntries.add(new BarEntry((float)Friday,4));



        BarDataSet barDataSet=new BarDataSet(barEntries,"Fare collection");
        BarData theData;


        ArrayList<String> theDates =new ArrayList<>();
        theDates.add("Monday");
        theDates.add("Tuesday");
        theDates.add("Wednesday");
        theDates.add("Thursday");
        theDates.add("Friday");

        theData= new BarData(theDates,barDataSet);

        barChart.setData(theData);

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);

    }
}
