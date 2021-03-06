package dev.wow.homeservices.viewModels;

import android.arch.lifecycle.ViewModel;
import android.view.View;

import dev.wow.homeservices.City;
import dev.wow.homeservices.R;

import dev.wow.homeservices.Sign_up_verifyDirections;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;


public class Sign_up_verify_view_model extends ViewModel {
    DatabaseReference customerref = FirebaseDatabase.getInstance().getReference().child("customer");
    DatabaseReference barberref = FirebaseDatabase.getInstance().getReference().child("homeservice");
    private View v;
    private String Phonenumber;
    private String name;
    private String city;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        Phonenumber = phonenumber;
    }

    public DatabaseReference getCustomerref() {
        return customerref;
    }

    public DatabaseReference getBarberref() {
        return barberref;
    }

    public View getV() {
        return v;
    }

    public void setV(View v) {
        this.v = v;
    }
    public void navigatetodestination(int i){

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
        String phonenumber = getPhonenumber();
        String name = getName();
        String city = getCity();


        if (i == 1) {
            customerref.child(phonenumber).child("name").setValue(name);
            customerref.child(phonenumber).child("city").setValue(city);
            customerref.child(phonenumber).child("address").setValue(getAddress());
            customerref.child(phonenumber).child("phonenumber").setValue(phonenumber);

            City.setName(name);
            City.setPhonenumber(phonenumber);
            City.setCity(city);
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.sign_up_verify, true)
                    .build();
            Sign_up_verifyDirections.ActionSignUpVerifyToCustomerMain action = Sign_up_verifyDirections.actionSignUpVerifyToCustomerMain();
            action.setPhonenumber(phonenumber);
            action.setCity(city);


            Navigation.findNavController(getV()).navigate(action, navOptions);


        } else {
            City.setPhonenumber(phonenumber);
            City.setName(name);
            City.setCity(city);
            barberref.child(phonenumber).child("name").setValue(name);
            barberref.child(phonenumber).child("city").setValue(city);
            barberref.child(phonenumber).child("address").setValue(getAddress());
            barberref.child(phonenumber).child("phonenumber").setValue(phonenumber);


            for (int j = 1; j <= 13; j++) {
                String s = String.valueOf(j);
                s = "time" + s;
                barberref.child(phonenumber).child(today).child(s).child("name").setValue("----");
                barberref.child(phonenumber).child(today).child(s).child("service").setValue("----");
                barberref.child(phonenumber).child(today).child(s).child("status").setValue("0");
                barberref.child(phonenumber).child(today).child(s).child("phonenumber").setValue("0");


            }




            Sign_up_verifyDirections.ActionSignUpVerifyToServicesByBarber action = Sign_up_verifyDirections.actionSignUpVerifyToServicesByBarber();

            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.sign_up_verify, true)
                    .build();

            Navigation.findNavController(getV()).navigate(action, navOptions);

        }

    }
}
