package tollMobile.application;


import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import Adapters.CarAdapter;
import DataBaseLayer.CarData;
import Model.Car;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarsFragment extends Fragment {

    ImageButton btnAddCar;


    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<Car> carDataList;
    private static ArrayList<String>carIdList;
    private static CarAdapter carAdapter;
    CoordinatorLayout coordinatorLayout;
    private static boolean delCar;


    public CarsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cars, container, false);

        recyclerView = view.findViewById(R.id.car_recycler_view);
        coordinatorLayout = view.findViewById(R.id.coordinatorLayout);
        delCar= false;

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());

        updateCarList();
        recyclerView.setAdapter(carAdapter);

        enableSwipeToDeleteAndUndo();


        btnAddCar = view.findViewById(R.id.btnAddCar);
        btnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)getActivity()).transactionFragment(new AddCarFragment(), true);
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        carDataList = new ArrayList<>();
        carIdList = new ArrayList<>();
        carAdapter = new CarAdapter(carDataList, new CarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Car car) {
                Toast.makeText(getContext(), "Item Clicked", Toast.LENGTH_LONG).show();

            }
        }){

        };
        updateCarList();


    }

    public static void updateCarList(){

        CarData.getCarList(new CarData.CarListcallBack() {
            @Override
            public void onCarListcallBack(ArrayList<Car> cars, ArrayList<String> keyList) {
                carDataList.clear();
                carDataList.addAll(cars);
                carIdList.clear();
                carIdList.addAll(keyList);
                carAdapter.notifyDataSetChanged();
            }
        });
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                final Car car = carAdapter.getListItem().get(position);

                carAdapter.removeItem(position);
                delCar = true;

                Snackbar snackbar = Snackbar.make(coordinatorLayout,car.getCarBrand()+": "+car.getCarModel()+"has be deleted",Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        carAdapter.restoreItem(car, position);
                        recyclerView.scrollToPosition(position);
                        delCar = false;
                    }
                });

                if(delCar == true){
                    CarData.deleteCar(car.getCarRegistrationNumber());
                }

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
