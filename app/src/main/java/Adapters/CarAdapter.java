package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Model.Car;
import tollMobile.application.R;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {

    public ArrayList<Car> getListItem() {
        return listItem;
    }

    public interface OnItemClickListener {
        void onItemClick(Car car);
    }

    private ArrayList<Car> listItem;
    //private Context context;
    //private String TAG;
    private OnItemClickListener listener;
    private Animation animationUp;
    private Animation animationDown;

    public CarAdapter(ArrayList<Car> listItem, OnItemClickListener listener){
        this.listItem = listItem;
        //this.context = context;
        //this.TAG = tag;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.car_cards_layout,viewGroup,false);

        animationUp = AnimationUtils.loadAnimation(viewGroup.getContext(), R.animator.slide_up);
        animationDown = AnimationUtils.loadAnimation(viewGroup.getContext(), R.animator.slide_down);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Car car = listItem.get(position);

        holder.textViewCarBrand.setText(car.getCarBrand());
        holder.textViewRegistrationNumber.setText(car.getCarRegistrationNumber());

        holder.EdittextViewCarBrand.setText(car.getCarBrand());
        holder.EdittextViewCarModel.setText(car.getCarModel());
        holder.EdittextViewCarFuel.setText(car.getCarFuel());
        holder.EdittextViewCarRegistrationNumber.setText(car.getCarRegistrationNumber());


        holder.bind(listItem.get(position), listener);

    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public void removeItem(int position) {
        listItem.remove(position);
        notifyItemRemoved(position);
    }
    public void restoreItem(Car car, int position){
        listItem.add(position, car);
        notifyItemInserted(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewCarBrand;
        public TextView textViewRegistrationNumber;
        public EditText EdittextViewCarBrand;
        public EditText EdittextViewCarModel;
        public EditText EdittextViewCarFuel;
        public EditText EdittextViewCarRegistrationNumber;
        public LinearLayout visibleCarDetails;

        public ViewHolder(View itemView) {
            super(itemView);

            this.textViewCarBrand = itemView.findViewById(R.id.textViewCarBrand);
            this.textViewRegistrationNumber = itemView.findViewById(R.id.textViewRegistrationNumber);

            this.EdittextViewCarBrand = itemView.findViewById(R.id.editTextCarBrandDetails);
            this.EdittextViewCarModel = itemView.findViewById(R.id.editTextCarModelDetails);
            this.EdittextViewCarFuel = itemView.findViewById(R.id.editCarFuelDetails);
            this.EdittextViewCarRegistrationNumber = itemView.findViewById(R.id.editTextCarRegistrationNumberDetails);
            this.visibleCarDetails = itemView.findViewById(R.id.carDetails);
        }


        // delete if it does not work showing car details in in carFragments with animation
        public void bind(final Car car, OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(visibleCarDetails.getVisibility() == View.GONE){
                        visibleCarDetails.setVisibility(View.VISIBLE);
                        visibleCarDetails.startAnimation(animationDown);
                    } else {
                        visibleCarDetails.setVisibility(View.GONE);
                        visibleCarDetails.startAnimation(animationUp);
                    }
                }
            });
        }
    }
}
