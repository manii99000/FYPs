package dev.wow.salon;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import dev.wow.salon.viewModels.Customer_main_view_model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;


public class Customer_main extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;
    private DatabaseReference myref;
    private DatabaseReference customerref;
    private String phonenumber;
    private static String city;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        SharedPreferences bb = getContext().getSharedPreferences("my_prefs", 0);
        bb.edit().clear().commit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_main, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myref = FirebaseDatabase.getInstance().getReference().child("barber");
        customerref = FirebaseDatabase.getInstance().getReference().child("customer");
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();



        Customer_mainArgs args = Customer_mainArgs.fromBundle(getArguments());
        phonenumber = args.getPhonenumber();
        city = args.getCity();
        listItems = new ArrayList<ListItem>();


        Customer_main_view_model view_model = ViewModelProviders.of(this).get(Customer_main_view_model.class);


        LiveData<DataSnapshot> liveData = view_model.getDataSnapshotLiveDatabarberlist();

        liveData.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable DataSnapshot dataSnapshot) {
                listItems.clear();
                if (dataSnapshot != null) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        listItems.add(snapshot.getValue(ListItem.class));
                        adapter.notifyDataSetChanged();

                    }
                }
            }
        });

        adapter = new MyAdapter(listItems, getContext());

        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit_profile:
                Customer_mainDirections.ActionCustomerMainToEditProfile action = Customer_mainDirections.actionCustomerMainToEditProfile();
                action.setPhonenumber(phonenumber);
                action.setType(0);
                Navigation.findNavController(getView()).navigate(action);
                return true;
            case R.id.menu_booking_history:
                Customer_mainDirections.ActionCustomerMainToCustomerBookedAppointment action1 = Customer_mainDirections.actionCustomerMainToCustomerBookedAppointment();
                action1.setPhonenumber(phonenumber);
                Navigation.findNavController(getView()).navigate(action1);
                return true;
            case R.id.menu_view_wallet:
                Customer_mainDirections.ActionCustomerMainToCustomerBookedAppointment action2 = Customer_mainDirections.actionCustomerMainToCustomerBookedAppointment();
                action2.setPhonenumber(phonenumber);
                Navigation.findNavController(getView()).navigate(action2);
                return true;
            case R.id.menu_logout:
                FirebaseAuth.getInstance().signOut();
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.customer_main, true)
                        .build();
                Navigation.findNavController(getView()).navigate(R.id.action_customer_main_to_home,null,navOptions);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

}
