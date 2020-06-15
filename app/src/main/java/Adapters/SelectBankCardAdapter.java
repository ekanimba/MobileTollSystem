package Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import Model.BankCard;
import tollMobile.application.R;

public class SelectBankCardAdapter extends BaseAdapter {

    Context context;
    ArrayList<BankCard> listbankCards;
    LayoutInflater layoutInflater;

    public SelectBankCardAdapter(Context context,ArrayList<BankCard> bankCard){
        this.context = context;
        this.listbankCards = bankCard;
        layoutInflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        view = layoutInflater.inflate(R.layout.bank_cards_spinner_layout,null);

        TextView spinnercardHolderName = view.findViewById(R.id.spinnercardHolderName);
        TextView SpinnercardNumber = view.findViewById(R.id.SpinnercardNumber);

        spinnercardHolderName.setText(listbankCards.get(position).getCardHolderName());
        SpinnercardNumber.setText(listbankCards.get(position).getCardNumber());

        return view;
    }

}
