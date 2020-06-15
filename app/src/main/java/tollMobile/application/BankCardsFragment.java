package tollMobile.application;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import Adapters.BankCardAdapter;
import DataBaseLayer.BankCardData;
import Model.BankCard;


/**
 * A simple {@link Fragment} subclass.
 */
public class BankCardsFragment extends Fragment {

    ImageButton btnAddBankCards;
    ImageButton btn_back_settings;

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<BankCard> bankCarDataList;
    private static ArrayList<String>bankCardIdList;
    private static BankCardAdapter bankCardAdapter;
    CoordinatorLayout coordinatorLayout;
    private static boolean delCard;

    public BankCardsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_bank_cards, container, false);

        btnAddBankCards = view.findViewById(R.id.btnAddBankCards);
        btn_back_settings = view.findViewById(R.id.btn_back_settings);


        recyclerView = view.findViewById(R.id.bankCard_recycler_view);
        coordinatorLayout = view.findViewById(R.id.cardCoordinatorLayout);
        delCard = false;

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        updateCardList();
        recyclerView.setAdapter(bankCardAdapter);

        btn_back_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)getActivity()).transactionFragment(new SettingsFragment(), false);
            }
        });

        btnAddBankCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)getActivity()).transactionFragment(new AddBankCardFragment(), true);
            }
        });


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bankCarDataList = new ArrayList<>();
        bankCardIdList = new ArrayList<>();
        bankCardAdapter = new BankCardAdapter(bankCarDataList, new BankCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BankCard bankCard) {
                Toast.makeText(getContext(), "Item Clicked", Toast.LENGTH_LONG).show();
            }
        });

        updateCardList();
    }

    public static void updateCardList(){

        BankCardData.getBankCardList(new BankCardData.BankCardListcallBack() {
            @Override
            public void onBankCardListcallBack(ArrayList<BankCard> bankCards, ArrayList<String> keyList) {
                bankCarDataList.clear();
                bankCarDataList.addAll(bankCards);
                bankCardIdList.clear();
                bankCardIdList.addAll(keyList);
                bankCardAdapter.notifyDataSetChanged();
            }
        });
    }


}
