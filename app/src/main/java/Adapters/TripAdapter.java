package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import DataBaseLayer.TripData;
import Model.Trip;
import tollMobile.application.EditUserProfileFragment;
import tollMobile.application.HomeActivity;
import tollMobile.application.HomeFragment;
import tollMobile.application.PayTrip;
import tollMobile.application.R;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder>{

    public ArrayList<Trip> getListItem(){
        return listItem;
    }
    public interface OnItemClickListener {
        void onItemClick(Trip trip);
    }
    private ArrayList<Trip> listItem;
    private OnItemClickListener listener;
    private Context mContext;

    public TripAdapter(ArrayList<Trip> listItem,OnItemClickListener listener){
        this.listItem = listItem;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.journey_trip_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Trip trip = listItem.get(position);

        if(trip.getPaid()){
            holder.buttonpayTrip.setVisibility(View.GONE);
            holder.textTicketPrice.setVisibility(View.VISIBLE);
        } else {
            holder.buttonpayTrip.setVisibility(View.VISIBLE);
            holder.textTicketPrice.setVisibility(View.GONE);
        }
        holder.buttonpayTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TripData.performPayment(trip);
                transactionFragmentWithTripId(v,new PayTrip(),trip.getTripId());

            }
        });

        holder.textdateTrip.setText(trip.getDateTrip().substring(0,15));
        holder.textEntryTollName.setText(trip.getEntryToll());
        holder.textExitTollName.setText(trip.getExitToll());
        holder.textentryTollTime.setText(trip.getEntryTime().substring(16,21));
        holder.textexitTollTime.setText(trip.getExitTime().substring(16,21));
        holder.textTicketPrice.setText(trip.getTicketPrice()+" PLN");
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textdateTrip;
        public TextView textentryTollTime;
        public TextView textexitTollTime;
        public TextView textEntryTollName;
        public TextView textExitTollName;
        public TextView textTicketPrice;
        private Button buttonpayTrip;

        private Context context;
        public ViewHolder(View itemView){
            super(itemView);
            context = itemView.getContext();


            this.textdateTrip = itemView.findViewById(R.id.textdateTrip);
            this.textentryTollTime = itemView.findViewById(R.id.textentryTollTime);
            this.textexitTollTime = itemView.findViewById(R.id.textexitTollTime);
            this.textEntryTollName = itemView.findViewById(R.id.textEntryTollName);
            this.textExitTollName = itemView.findViewById(R.id.textExitTollName);
            this.textTicketPrice = itemView.findViewById(R.id.textTicketPrice);
            this.buttonpayTrip = itemView.findViewById(R.id.buttonpayTrip);
        }
    }

    public void transactionFragmentWithTripId(View v,Fragment fragment, String tripId) {
            HomeActivity homeActivity = (HomeActivity) v.getContext();
            homeActivity.transactionFragmentWithTripId(fragment, tripId, false);
    }


}
