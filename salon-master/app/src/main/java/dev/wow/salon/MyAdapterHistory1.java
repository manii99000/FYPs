package dev.wow.salon;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MyAdapterHistory1 extends RecyclerView.Adapter<MyAdapterHistory1.ViewHolder> {
    private List<TimeItem> tm;
    private Context context;

    public MyAdapterHistory1(List<TimeItem> tm, Context context) {
        this.tm = tm;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.time_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final TimeItem tm1 = tm.get(i);

        viewHolder.text_time.setText(tm1.getTime());


    }

    @Override
    public int getItemCount() {
        return tm.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView text_time;

        private LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_time = (TextView)itemView.findViewById(R.id.history_time);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.time_layout);
        }
    }
}
