package dev.wow.homeservices;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class BarberWallet extends Fragment {

    private String phonenumber;
    private DatabaseReference timeref;
    private TextView totalincome, salonname;

    public BarberWallet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_barber_wallet, container, false);


        phonenumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().substring(1);
        timeref = FirebaseDatabase.getInstance().getReference().child("homeservice").child(phonenumber);
        totalincome = (TextView) view.findViewById(R.id.total_income_value_txt);
        salonname = (TextView) view.findViewById(R.id.salon_name_txt);










        // Inflate the layout for this fragment
        return view;



    }


    @Override
    public void onStart() {
        super.onStart();
        City.setPhonenumber(phonenumber);



        timeref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                salonname.setText(dataSnapshot.child("name").getValue().toString());
                String[] day = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
                String[] month = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
                String[] time = {"time1","time2","time3","time4","time5","time6","time7","time8","time9","time10","time11","time12","time13"};
                String value = "0";




                for(String Day: day){
                    for(String Month: month){
                        for(String Time: time){
                            if (dataSnapshot.child(Day+Month+"2019").exists()) {
                                if (dataSnapshot.child(Day+Month+"2019").child(Time).child("status").getValue().toString().equals("2")) {

                                    try{
                                        value = value + "," + dataSnapshot.child(Day+Month+"2019").child(Time).child("totalincome").getValue().toString();
                                        //totalincome.setVisibility(View.GONE);
                                        String[] val = value.split(",");
                                        int sum = 0;
                                        for (String element : val) {
                                            try{
                                                sum += Integer.parseInt(element);
                                                totalincome.setText(String.valueOf(sum));
                                            }catch(Exception e){
                                                //Toast.makeText(getActivity(), "Error: " + element, Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                    }catch(Exception e){
                                        //Toast.makeText(getActivity(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                        }
                    }
                }
                /*if (dataSnapshot.child("20052019").exists()) {
                    if (dataSnapshot.child("20052019").child("time3").child("status").getValue().toString().equals("2")) {
                        totalincome.setText(dataSnapshot.child("20052019").child("time3").child("totalincome").getValue().toString());
                    }
                }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

}
