package dev.wow.salon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TrackActivity extends AppCompatActivity {


    private Handler handler = new Handler();
    private Runnable runnable;
    private TextView tv_days, tv_hour, tv_minute, tv_second;
    Button tbtn;
    TextView app_name, app_send, app_rec, time_to;
    String value3;
    public  static String abc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        Intent intent = getIntent();
        final String value = intent.getStringExtra("key");

        tbtn = (Button) findViewById(R.id.track_btn);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("hh:mm:ss aa");
        final String datetime = dateformat.format(c.getTime());
        app_name = (TextView) findViewById(R.id.salon_name_txt);
        app_send = (TextView) findViewById(R.id.appoint_send_txt);
        app_rec  = (TextView) findViewById(R.id.appoint_recieved_txt);
        time_to  = (TextView) findViewById(R.id.time_to_txt);




        tv_days = findViewById(R.id.tv_days);
        tv_hour = findViewById(R.id.tv_hour);
        tv_minute = findViewById(R.id.tv_minute);
        tv_second = findViewById(R.id.tv_second);






        SharedPreferences bb = getSharedPreferences("my_prefs", 0);
        try {



            time_to.setText("Time to Appointment:");



            app_name.setText(value);




            app_send.setText("Appointment Send: Successfully!!!");
            value3 = bb.getString("DATE", "");
            String m = bb.getString("TIME", "");
            String hour = m.substring(0, 3);
            hour = hour.substring(0, 1);
            value3 = value3.replace("/", "-");
            String EVENT_DATE_TIME = value3 + " " + hour + ":00:00";
            new Handler().postDelayed(new Runnable(){
                public void run(){
                    app_rec.setText("Appointment Recieved: Recieved By the Salon Successfully!");
                }
            }, 15000);
            //time_to.setText("Time of Appointment: ");
            countDownStart(EVENT_DATE_TIME);
        }
        catch(Exception e)
        {
            Toast.makeText(this,"Error "+e.getMessage(),Toast.LENGTH_LONG);
        }


        tbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent myIntent = new Intent(TrackActivity.this, MapsActivityNavigate.class);

                TrackActivity.this.startActivity(myIntent);
            }
        });
    }

    private void countDownStart(final String dtt) {
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    handler.postDelayed(this, 1000);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy h:mm:ss");
                    Date event_date = dateFormat.parse(dtt);
                    Date current_date = new Date();
                    if (!current_date.after(event_date)) {
                        long diff = event_date.getTime() - current_date.getTime();
                        long Days = diff / (24 * 60 * 60 * 1000);
                        long Hours = diff / (60 * 60 * 1000) % 24;
                        long Minutes = diff / (60 * 1000) % 60;
                        long Seconds = diff / 1000 % 60;
                        //
                        tv_days.setText(String.format("%02d", Days));
                        tv_hour.setText(String.format("%02d", Hours));
                        tv_minute.setText(String.format("%02d", Minutes));
                        tv_second.setText(String.format("%02d", Seconds));
                    } else {

                        handler.removeCallbacks(runnable);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }





}
