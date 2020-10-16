package com.damishs.ticketingrealtimedb.ui.Manager;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayTravelInfo extends AppCompatActivity {

    private DatabaseReference databaseRoute;
    private EditText startp_In,endp_In;
    private Button Find_In;
    private BarChart barChart;
    private ArrayList<RouteModel> routeModels;
    private  int Monday,Tuesday,Wednesday,Thursday,Friday;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_travel_info);


        databaseRoute= FirebaseDatabase.getInstance().getReference("Routes");

        startp_In=(EditText)findViewById(R.id.txt_start_invalid);
        endp_In=(EditText)findViewById(R.id.txt_end_Invalid);
        Find_In=(Button)findViewById(R.id.btn_invalid);
        barChart=(BarChart)findViewById(R.id.bar_chart_Invalid);


        Find_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(startp_In.getText())&& TextUtils.isEmpty(endp_In.getText())){
                    Toast.makeText(DisplayTravelInfo.this, "Missing Data!!!", Toast.LENGTH_SHORT).show();
                }else {
                    Monday=0;
                    Tuesday=0;
                    Wednesday=0;
                    Thursday=0;
                    Friday=0;
                    ViewAllInvalid();
                }
            }
        });



    }

    private void ViewAllInvalid(){

        databaseRoute.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    RouteModel routeModel=postSnapshot.getValue(RouteModel.class);

                        if ( TextUtils.equals(routeModel.getStartpoint(),startp_In.getText().toString()) && TextUtils.equals(routeModel.getEndpoint(),endp_In.getText().toString())){

                            if (TextUtils.equals("Monday", routeModel.getDay())){
                                Monday+=Integer.parseInt(routeModel.getInvalidpassenger());
                            }else if (TextUtils.equals("Tuesday", routeModel.getDay())){
                                Tuesday+=Integer.parseInt(routeModel.getInvalidpassenger());
                            }else if (TextUtils.equals("Wednesday", routeModel.getDay())){
                                Wednesday+=Integer.parseInt(routeModel.getInvalidpassenger());
                            }else if (TextUtils.equals("Thursday", routeModel.getDay())){
                                Thursday+=Integer.parseInt(routeModel.getInvalidpassenger());
                            }else if (TextUtils.equals("Friday", routeModel.getDay())){
                                Friday+=Integer.parseInt(routeModel.getInvalidpassenger());
                            }

                        }

                }
                if (Monday==0 && Tuesday==0 && Wednesday==0 && Thursday==0 && Friday==0){
                    Toast.makeText(DisplayTravelInfo.this, "No such Route!!!", Toast.LENGTH_SHORT).show();
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



        BarDataSet barDataSet=new BarDataSet(barEntries,"No of Invalid Passengers");
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
