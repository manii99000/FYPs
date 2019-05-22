package dev.wow.salon.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dev.wow.salon.liveDatas.Barber_main_live_data;
import dev.wow.salon.liveDatas.Barber_wallet_live_data;

public class BarberWallet_view_model extends ViewModel {

    private final DatabaseReference timeref = FirebaseDatabase.getInstance().getReference().child("barber")
            .child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().substring(1));
    public final Barber_wallet_live_data livedata_barber = new Barber_wallet_live_data(timeref);

    public LiveData<DataSnapshot> getDataSnapshotLiveDatabarber()
    {
        return livedata_barber;
    }


}
