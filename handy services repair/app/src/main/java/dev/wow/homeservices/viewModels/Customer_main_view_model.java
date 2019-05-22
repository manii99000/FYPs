package dev.wow.homeservices.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import dev.wow.homeservices.liveDatas.Customer_main_live_data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Customer_main_view_model extends ViewModel {
    private final DatabaseReference barberref = FirebaseDatabase.getInstance().getReference().child("homeservice");
    private Customer_main_live_data live_data = new Customer_main_live_data(barberref);


    public LiveData<DataSnapshot> getDataSnapshotLiveDatabarberlist() {

        return live_data;
    }
}
