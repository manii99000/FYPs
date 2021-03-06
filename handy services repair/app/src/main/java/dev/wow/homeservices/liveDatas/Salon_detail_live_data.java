package dev.wow.homeservices.liveDatas;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import dev.wow.homeservices.City;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Salon_detail_live_data extends LiveData<DataSnapshot> {
    private final Query query;

    private final MyValueEventListener listener = new MyValueEventListener();

    public Salon_detail_live_data(Query query){
        this.query = query;
    }

    public Salon_detail_live_data (DatabaseReference ref, int a){
        String phonenumber = City.getBarberphonenumber();
        if(a==0){
            this.query = ref.child(phonenumber);

        }
        else {
            this.query = ref.child(phonenumber).child("services");
        }

    }

    @Override
    protected void onActive(){
        query.addValueEventListener(listener);
    }

    @Override
    protected void onInactive(){
        query.removeEventListener(listener);
    }


    public class MyValueEventListener implements ValueEventListener {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(dataSnapshot);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }
}
