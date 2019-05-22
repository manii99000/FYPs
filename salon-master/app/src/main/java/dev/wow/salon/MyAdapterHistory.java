package dev.wow.salon;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MyAdapterHistory extends RecyclerView.Adapter<MyAdapterHistory.ViewHolder> {

    private List<HistoryItem> historyItems;
    private Context context;


    public MyAdapterHistory(List<HistoryItem> historyItems, Context context) {
        this.historyItems = historyItems;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final HistoryItem historyItem = historyItems.get(i);
        viewHolder.text_date.setText(historyItem.getDate());
        viewHolder.text_time.setText(historyItem.getTime());
        viewHolder.text_name.setText(historyItem.getName());
        viewHolder.text_address.setText(historyItem.getAddress());
        viewHolder.text_phonenumber.setText(historyItem.getPhonenumber());
        TrackActivity.abc = historyItem.getAddress();
        MapsActivityNavigate.salon_cord = historyItem.getAddress();
        SharedPreferences prefs =  context.getApplicationContext().getSharedPreferences("my_prefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("TIME", historyItem.getTime());
        edit.putString("DATE",historyItem.getDate());
        edit.putString("NAME",historyItem.getName());
        edit.commit();
    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text_date;
        private TextView text_time;
        private TextView text_name;
        private TextView text_address;
        private TextView text_phonenumber;
        private LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_date = (TextView)itemView.findViewById(R.id.history_date);
            text_time = (TextView)itemView.findViewById(R.id.history_time);
            text_name = (TextView)itemView.findViewById(R.id.history_name);
            text_address = (TextView)itemView.findViewById(R.id.history_address);
            text_phonenumber = (TextView)itemView.findViewById(R.id.history_phonenumber);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.history_layout);
        }
    }
}
