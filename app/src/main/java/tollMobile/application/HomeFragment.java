package tollMobile.application;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import Adapters.CarAdapter;
import Adapters.TollEntryAdapter;
import Adapters.TripAdapter;
import DataBaseLayer.TollEntryData;
import DataBaseLayer.TripData;
import Model.Car;
import Model.TollEntry;
import Model.Trip;
import Model.User;
import utils.HomeFragmentUtils;
import DataBaseLayer.UserData;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private TextView greetings, date, user_balance, user_name ;
    private static HomeFragmentUtils utils;

    private static ArrayList<TollEntry> tollEntryDataList;
    private static ArrayList<String>tollentryIdList;
    private static RecyclerView recyclerViewTollEntry;
    private static TollEntryAdapter tollEntryAdapter;
    private RecyclerView.LayoutManager layoutManagerTollEntry;
    CoordinatorLayout cardCoordinatorLayoutTollEntry;


    private static RecyclerView.Adapter adapterTrip;
    private RecyclerView.LayoutManager layoutManagerTrip;
    private static RecyclerView recyclerViewTrip;
    private static ArrayList<Trip> tripDataList;
    private static ArrayList<String> tripIdList;
    private static TripAdapter tripAdapter;
    CoordinatorLayout cardCoordinatorLayoutTrip;

    ImageButton btn_back_home_fragment;
    TextView TextView_Add_money_preview;
    EditText EditText_Add_money;
    Button Button_Add_money;
    LinearLayout add_money_layout;
    LinearLayout home_layout;




    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        date = view.findViewById(R.id.date);
        greetings = view.findViewById(R.id.greetings);
        user_balance = view.findViewById(R.id.user_balance);
        user_name = view.findViewById(R.id.user_name);
        utils = new HomeFragmentUtils();

        btn_back_home_fragment = view.findViewById(R.id.btn_back_home_fragment);
        TextView_Add_money_preview = view.findViewById(R.id.TextView_Add_money_preview);
        EditText_Add_money = view.findViewById(R.id.EditText_Add_money);
        Button_Add_money = view.findViewById(R.id.Button_Add_money);
        add_money_layout = view.findViewById(R.id.add_money_layout);
        home_layout = view.findViewById(R.id.home_layout);



        Button_Add_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_money_layout.setVisibility(v.VISIBLE);
                home_layout.setVisibility(v.GONE);
            }
        });

        btn_back_home_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_money_layout.setVisibility(v.GONE);
                home_layout.setVisibility(v.VISIBLE);
            }
        });

        recyclerViewTollEntry = view.findViewById(R.id.Toll_entry_recycler_view);
        recyclerViewTrip = view.findViewById(R.id.trip_recycler_view);

        cardCoordinatorLayoutTollEntry = view.findViewById(R.id.cardCoordinatorLayoutTollEntry);
        cardCoordinatorLayoutTrip = view.findViewById(R.id.cardCoordinatorLayoutTrip);

        recyclerViewTollEntry.setHasFixedSize(true);

        recyclerViewTrip.setHasFixedSize(true);

        layoutManagerTollEntry = new LinearLayoutManager(getActivity());
        recyclerViewTollEntry.setLayoutManager(layoutManagerTollEntry);

        layoutManagerTrip = new LinearLayoutManager(getActivity());
        recyclerViewTrip.setLayoutManager(layoutManagerTrip);


        updateTripList();
        updateCarList();

        recyclerViewTollEntry.setAdapter(tollEntryAdapter);
        recyclerViewTrip.setAdapter(tripAdapter);




        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tollEntryDataList = new ArrayList<>();
        tollentryIdList = new ArrayList<>();

        tripDataList = new ArrayList<>();
        tripIdList = new ArrayList<>();

        tollEntryAdapter = new TollEntryAdapter(tollEntryDataList, new TollEntryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TollEntry tollentry) {
                Toast.makeText(getContext(), "Item Clicked", Toast.LENGTH_LONG).show();
            }
        });

        tripAdapter = new TripAdapter(tripDataList, new TripAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Trip trip) {
                Toast.makeText(getContext(), "Item Clicked", Toast.LENGTH_LONG).show();
            }
        });

        updateCarList();
        updateTripList();
    }

    public static void updateCarList(){

        TollEntryData.getTollEntryList(new TollEntryData.EntryTollListcallBack() {
            @Override
            public void onEntryTollListcallBack(ArrayList<TollEntry> toll, ArrayList<String> keyList) {
                tollEntryDataList.clear();
                tollEntryDataList.addAll(toll);
                tollentryIdList.clear();
                tollentryIdList.addAll(keyList);
                tollEntryAdapter.notifyDataSetChanged();
            }
        });
    }

    public static void updateTripList(){
        TripData.getTripList(new TripData.TripListcallBack() {
            @Override
            public void onTripListcallBack(ArrayList<Trip> trips, ArrayList<String> keyList) {
                tripDataList.clear();
                tripDataList.addAll(trips);
                tripDataList = TripData.reverseTripList(tripDataList);
                tripIdList.clear();
                tripIdList.addAll(keyList);
                tripAdapter.notifyDataSetChanged();
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        Calendar c = Calendar.getInstance();
        greetings.setText(utils.getGreetings(c));
        date.setText(utils.getDate());

        updateUser();

    }

    public void updateUser(){

        UserData.getBalance(new UserData.BalanceCallBack() {
            @Override
            public void onBalanceCallBack(double balance) {
                user_balance.setText(Double.toString(balance )+" PLN");
            }
        });

        UserData.getUser(new UserData.UserCallBack() {
            @Override
            public void onUserCallBack(User user) {
                user_name.setText(user.getlName()+" "+user.getfName());
            }
        });
    }

}
