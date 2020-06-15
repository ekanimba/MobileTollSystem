package tollMobile.application;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import DataBaseLayer.BankCardData;
import Model.BankCard;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddBankCardFragment extends Fragment {

    String inputCardNumber;String inputCardHolderName;String inputCardExpNumberMonth;String inputCardExpNumberYear;String inputCardCcv;
    EditText editTextCardNumber, editTextCardHolderName, editCardExpNumberMonth, editTextCardExpNumberYear, editTextCardCcv;
    ImageButton btn_back_bank_cards;
    Button buttonAddCard;


    public AddBankCardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_bank_card, container, false);

        btn_back_bank_cards = view.findViewById(R.id.btn_back_bank_cards);

        editTextCardNumber = view.findViewById(R.id.editTextCardNumber);
        editTextCardHolderName = view.findViewById(R.id.editTextCardHolderName);
        editCardExpNumberMonth = view.findViewById(R.id.editCardExpNumberMonth);
        editTextCardExpNumberYear = view.findViewById(R.id.editTextCardExpNumberYear);
        editTextCardCcv = view.findViewById(R.id.editTextCardCcv);

        buttonAddCard = view.findViewById(R.id.buttonAddCard);


        btn_back_bank_cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)getActivity()).transactionFragment(new BankCardsFragment(), true);
            }
        });

        buttonAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputCardNumber = editTextCardNumber.getText().toString();
                inputCardHolderName = editTextCardHolderName.getText().toString();
                inputCardExpNumberMonth = editCardExpNumberMonth.getText().toString();
                inputCardExpNumberYear = editTextCardExpNumberYear.getText().toString();
                inputCardCcv = editTextCardCcv.getText().toString();

                BankCard bankCard = new BankCard(inputCardNumber, inputCardHolderName, inputCardExpNumberMonth, inputCardExpNumberYear, inputCardCcv);

                BankCardData.AddNewBankCard(bankCard);
                ((HomeActivity)getActivity()).transactionFragment(new BankCardsFragment(), false);
            }
        });

        return view;
    }

}
