package tollMobile.application;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Adapters.SelectBankCardAdapter;
import DataBaseLayer.BankCardData;
import DataBaseLayer.TripData;
import Model.BankCard;
import Model.Trip;


/**
 * A simple {@link Fragment} subclass.
 */
public class PayTrip extends Fragment{


    Trip triptemp;
    Button buttonPayTrip;
    String receiveTripId;



    public TextView textPaydateTrip;
    public TextView textPayEntryTollTime;
    public TextView textPayExitTollTime;
    public TextView textPayEntryTollName;
    public TextView textPayExitTollName;
    public TextView textPayTicketPrice;
    public TextView textPayCarRegistrationNumber;
    public TextView textPayTagId;
    private int cardIcon;
    private static ArrayList<BankCard> bankCarDataList;
    private Spinner spinner;
    private BankCard bankCard;
    String tripId;
    Double tickectPrice;

    List<String> list;

    public PayTrip() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bankCarDataList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pay_trip, container, false);
        spinner = view.findViewById(R.id.select_bank_Card_Spinner);


        cardIcon = R.drawable.ic_credit_card;
        updateBankCards();


        SelectBankCardAdapter selectBankCardAdapter = new SelectBankCardAdapter(getActivity(),bankCarDataList);
        spinner.setAdapter(selectBankCardAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bankCarDataList.get(position).getCardCcv();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        this.buttonPayTrip = view.findViewById(R.id.buttonpayTrip);
        this.textPaydateTrip = view.findViewById(R.id.textPaydateTrip);
        this.textPayEntryTollTime = view.findViewById(R.id.textPayEntryTollTime);
        this.textPayExitTollTime = view.findViewById(R.id.textPayExitTollTime);
        this.textPayEntryTollName = view.findViewById(R.id.textPayEntryTollName);
        this.textPayExitTollName = view.findViewById(R.id.textPayExitTollName);
        this.textPayTicketPrice = view.findViewById(R.id.textPayTicketPrice);
        this.textPayCarRegistrationNumber = view.findViewById(R.id.textPayCarRegistrationNumber);
        this.textPayTagId = view.findViewById(R.id.textPayTagId);

        Bundle bundle = this.getArguments();
        receiveTripId = bundle.getString("TripId");
        updateTrip(receiveTripId);

        buttonPayTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TripData.performPayment(tripId, tickectPrice, new TripData.PerformPaymentcallBack() {
                    @Override
                    public void onPerformPaymentcallBack(Boolean paid) {
                        if(paid){
                            ((HomeActivity)getActivity()).transactionFragment(new HomeFragment(), false);
                        }else{

                        }
                    }
                });
            }
        });
        return view;
    }


    public void updateTrip(String trip){
        TripData.getTrip(trip, new TripData.TripcallBack() {
            @Override
            public void onTripcallBack(Trip trip) {
                triptemp = trip;
                textPaydateTrip.setText(trip.getDateTrip().substring(0,15));
                textPayEntryTollTime.setText(trip.getEntryTime().substring(16,21));
                textPayExitTollTime.setText(trip.getExitTime().substring(16,21));
                textPayEntryTollName.setText(trip.getEntryToll());
                textPayExitTollName.setText(trip.getExitToll());
                //textPayTicketPrice.setText(trip.getTicketPrice());
                textPayCarRegistrationNumber.setText(trip.getCarRegistrationNumber());
                textPayTagId.setText(trip.getTripId());
                buttonPayTrip.setText("Pay "+trip.getTicketPrice()+" PLN");
                tripId = trip.getTripId();
                tickectPrice = Double.valueOf(trip.getTicketPrice());
            }
        });
    }

    public void updateBankCards(){
        BankCardData.getBankCardList(new BankCardData.BankCardListcallBack() {
            @Override
            public void onBankCardListcallBack(ArrayList<BankCard> bankCards, ArrayList<String> keyList) {
                bankCarDataList.clear();
                bankCarDataList.addAll(bankCards);
            }
        });
    }
}
