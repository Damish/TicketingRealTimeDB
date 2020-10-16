package com.damishs.ticketingrealtimedb.ui.dashboard;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.damishs.ticketingrealtimedb.R;
import com.damishs.ticketingrealtimedb.ui.Lists.MyTripsActivity;
import com.damishs.ticketingrealtimedb.ui.Login.HomeActivity;
import com.damishs.ticketingrealtimedb.ui.home.Artist;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class DashboardFragment extends Fragment {

    private EditText cardName,card_no,CVV,amount;
    private TextView balance;
    private Button datepicker;
    private RadioButton radio1,radio2;
    private int finalyear,finalmonth,finalday;
    private Button paybtn;

    private DatabaseReference databasePayment;
    private DatabaseReference updateaccountbalance;
    private String accountId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        cardName=(EditText)root.findViewById(R.id.editTextCardName);
        card_no=(EditText)root.findViewById(R.id.editTextNumberPassword);
        CVV=(EditText)root.findViewById(R.id.editText_CVV);
        amount=(EditText)root.findViewById(R.id.editText_amount);
        radio1=(RadioButton)root.findViewById(R.id.radioButton5);
        radio2=(RadioButton)root.findViewById(R.id.radioButton6);
        balance=(TextView)root.findViewById(R.id.textView2_balance);
        paybtn=(Button)root.findViewById(R.id.button_pay);



        //datepicker

        datepicker=(Button)root.findViewById(R.id.datepicker);

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c=Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(),R.style.UserDialog1, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        finalyear=year;
                        finalmonth=month+1;
                        finalday=dayOfMonth;
                        String dateSt="date:"+finalyear+"/"+finalmonth+"/"+finalday;
                        datepicker.setText(dateSt);
                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });

        accountId="-MJh5Bt_5B1vfNtXJz4J";
        databasePayment = FirebaseDatabase.getInstance().getReference("payment");
        updateaccountbalance=FirebaseDatabase.getInstance().getReference("accounts/"+accountId);

        updateaccountbalance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("availableBalance")){
                    balance.setText(snapshot.child("availableBalance").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        String id = databasePayment.push().getKey();




        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCredits();
            }
        });




        return root;
    }

    //add credits

    public void AddCredits() {

        String cardName1  = cardName.getText().toString().trim();
        String card_no1  = card_no.getText().toString().trim();
        String exp_date1=datepicker.getText().toString().trim();
        String CVV1  = CVV.getText().toString().trim();

        String balance1=balance.getText().toString().trim();
        String amount1  = amount.getText().toString().trim();

        String sum= String.valueOf( Integer.parseInt(balance1) + Integer.parseInt(amount1) );




        if (!TextUtils.isEmpty(accountId)||!TextUtils.isEmpty(cardName1)||!TextUtils.isEmpty(card_no1)||!TextUtils.isEmpty(exp_date1)||!TextUtils.isEmpty(CVV1)||!TextUtils.isEmpty(sum)){

            //unique id is generated
            String id = databasePayment.push().getKey();
            Paymentmodel paymentmodel=new Paymentmodel(accountId,sum ,cardName1,card_no1, exp_date1,CVV1, amount1);

            //overwrite data to created id
            databasePayment.child(id).setValue(paymentmodel);
            Toast.makeText(this.getContext(), "payment added", Toast.LENGTH_SHORT).show();

            updateaccountbalance.child("availableBalance").setValue(sum);

            AlertDialog.Builder ald=new AlertDialog.Builder(getContext());
            ald.setMessage(""+amount1+" Credits added Successfully!!!  your Current credits-"+sum+"");
            ald.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SettxtNull();
                }
            });
            ald.show();


        } else {
            Toast.makeText(this.getContext(), "You should enter missing fields!!!", Toast.LENGTH_SHORT).show();
        }

    }

    public void SettxtNull(){

        cardName.setText("");
        card_no.setText("");
        CVV.setText("");
        datepicker.setText("Select Date");
        amount.setText("");
        radio1.setChecked(false);
        radio2.setChecked(false);
    }

}