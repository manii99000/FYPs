package dev.wow.salon.viewModels;

import android.arch.lifecycle.ViewModel;

import dev.wow.salon.City;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Services_by_barber_view_model extends ViewModel {
    private DatabaseReference myref  = FirebaseDatabase.getInstance().getReference().child("barber").child(City.getPhonenumber()).child("services");

    public void setvalue(String a, String b){
        myref.child(a).setValue(b);
    }
}
