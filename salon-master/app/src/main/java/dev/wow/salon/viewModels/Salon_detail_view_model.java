package dev.wow.salon.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import dev.wow.salon.liveDatas.Salon_detail_live_data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Salon_detail_view_model extends ViewModel {

    DatabaseReference barberref = FirebaseDatabase.getInstance().getReference().child("barber");
    Salon_detail_live_data livedatabarber= new Salon_detail_live_data(barberref,0);
    Salon_detail_live_data livedataservices = new Salon_detail_live_data(barberref,1);
    public LiveData<DataSnapshot> getLivedatabarber() {

        return livedatabarber;
    }

    public LiveData<DataSnapshot> getLivedataservices() {


        return livedataservices;
    }
}
