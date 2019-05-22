package dev.wow.homeservices.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import dev.wow.homeservices.liveDatas.Tomorrow_live_data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Tomorrow_view_model extends ViewModel {


    private final DatabaseReference timeref = FirebaseDatabase.getInstance().getReference().child("homeservice")
            .child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().substring(1));
    private final Tomorrow_live_data live_data = new Tomorrow_live_data(timeref);

    public LiveData<DataSnapshot> getDataSnapshotLiveDatatomorrow(){

        return  live_data;

    }
}
