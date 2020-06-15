package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Model.TollEntry;
import tollMobile.application.R;

public class TollEntryAdapter extends RecyclerView.Adapter<TollEntryAdapter.ViewHolder>{

    private ArrayList<TollEntry> listTollEntry;
    private OnItemClickListener listener;

    public TollEntryAdapter(ArrayList<TollEntry> listItem, TollEntryAdapter.OnItemClickListener listener){
        this.listTollEntry = listItem;
        this.listener = listener;

    }



    public ArrayList<TollEntry> getListTollEntry() {
        return listTollEntry;
    }

    public interface OnItemClickListener {
        void onItemClick(TollEntry tollentry);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.toll_entry_layout,viewGroup,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TollEntry tollentry = listTollEntry.get(position);
        String entryTollTime = tollentry.getEntryTime();
        Date dateDecode = new Date();
        try{
            dateDecode = new SimpleDateFormat("E MMM DD YYYY HH:mm:ss 'GMT'Z (zz)").parse(entryTollTime);
        } catch (Exception e){

        }

        holder.textViewEntryTime.setText(dateDecode.getHours()+":"+dateDecode.getMinutes());
        holder.textViewEntryToll.setText(tollentry.getEntryToll());
        holder.textViewCarRegistrationNumber.setText(tollentry.getCarRegistrationNumber());

    }

    @Override
    public int getItemCount() {
        return listTollEntry.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewEntryToll;
        public TextView textViewCarRegistrationNumber;
        public TextView textViewEntryTime;

        public ViewHolder(View itemView) {
            super(itemView);

            this.textViewEntryToll = itemView.findViewById(R.id.textViewEntryToll);
            this.textViewCarRegistrationNumber = itemView.findViewById(R.id.textViewCarRegistrationNumber);
            this.textViewEntryTime = itemView.findViewById(R.id.textViewEntryTime);

        }
    }
}
