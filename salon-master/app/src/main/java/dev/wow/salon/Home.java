package dev.wow.salon;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.wow.salon.viewModels.Home_view_model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Home extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        final Home_view_model mviewmodel = ViewModelProviders.of(this).get(Home_view_model.class);
        mviewmodel.setV(getView());
        int a = mviewmodel.finduser();
        if (a == 1) {
            final String phonenumber = mviewmodel.getPhonenumber();
            DatabaseReference customerref = mviewmodel.getCustomerref();
            customerref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(phonenumber).exists()) {
                        //Customer
                        String city = dataSnapshot.child(phonenumber).child("city").getValue().toString();
                        String name = dataSnapshot.child(phonenumber).child("name").getValue().toString();
                        String address = dataSnapshot.child(phonenumber).child("address").getValue().toString();
                        MapsActivityNavigate.cust_cord = address;
                        mviewmodel.setdetail(phonenumber, city, name);
                        mviewmodel.navigateCustomer();
                    } else {
                        //barber
                        final DatabaseReference barberref = mviewmodel.getBarberref();
                        barberref.child(phonenumber).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Calendar calendar = Calendar.getInstance();
                                String year = String.valueOf(calendar.get(Calendar.YEAR));
                                String month = String.valueOf(calendar.get(Calendar.MONTH)+1);
                                String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
                                if(day.length()==1){
                                    day = "0"+day;
                                }
                                if(month.length()==1){
                                    month = "0"+month;
                                }
                                String today = day + month + year;
                                String city = dataSnapshot.child("city").getValue().toString();
                                String name = dataSnapshot.child("name").getValue().toString();
                                String address = dataSnapshot.child("address").getValue().toString();
                                City.setAddress(address);
                                //yaha p url dset karna hai




                                mviewmodel.setdetail(phonenumber, city, name);
                                if (!dataSnapshot.child(today).exists()) {
                                    mviewmodel.setInitialData();
                                }
                                mviewmodel.navigateBarber();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } else {
            mviewmodel.navigateSignup();
        }
    }
}
