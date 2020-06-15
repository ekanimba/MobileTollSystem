package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Model.BankCard;
import Model.Car;
import tollMobile.application.R;

public class BankCardAdapter extends RecyclerView.Adapter<BankCardAdapter.ViewHolder> {


    private ArrayList<BankCard> listCard;
    //private Context context;
    //private String TAG;
    private OnItemClickListener listener;
    private Animation animationUp;
    private Animation animationDown;

    public BankCardAdapter(ArrayList<BankCard> listItem, BankCardAdapter.OnItemClickListener listener){
        this.listCard = listItem;
        //this.context = context;
        //this.TAG = tag;
        this.listener = listener;
    }

    public ArrayList<BankCard> getListCard() {
        return listCard;
    }

    public interface OnItemClickListener {
        void onItemClick(BankCard bankCard);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bank_cards,viewGroup,false);

        animationUp = AnimationUtils.loadAnimation(viewGroup.getContext(), R.animator.slide_up);
        animationDown = AnimationUtils.loadAnimation(viewGroup.getContext(), R.animator.slide_down);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BankCard bankCard = listCard.get(position);


        holder.textViewcardHolderName.setText(bankCard.getCardHolderName());
        holder.textViewcardNumber.setText(bankCard.getCardNumber());

        holder.card_number_display.setText(bankCard.getCardNumber());
        holder.card_holderName_display.setText(bankCard.getCardHolderName());;
        holder.card_exp_display.setText(bankCard.getCardExpNumberMonth()+"/"+bankCard.getCardExpNumberYear());


        holder.bind(listCard.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return listCard.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView textViewcardHolderName;
        public TextView textViewcardNumber;

        public LinearLayout bankCardDetails;
        public LinearLayout cardHeader;

        public TextView card_number_display;
        public TextView card_holderName_display;
        public TextView card_exp_display;


        public ViewHolder(View itemView){
            super(itemView);

            this.textViewcardHolderName = itemView.findViewById(R.id.textViewcardHolderName);
            this.textViewcardNumber = itemView.findViewById(R.id.textViewcardNumber);
            this.bankCardDetails = itemView.findViewById(R.id.bankCardDetails);
            this.cardHeader = itemView.findViewById(R.id.cardHeader);


            this.card_number_display = itemView.findViewById(R.id.card_number_display);
            this.card_holderName_display = itemView.findViewById(R.id.card_holderName_display);
            this.card_exp_display = itemView.findViewById(R.id.card_exp_display);


        }


        public void bind(final BankCard bankCard, OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bankCardDetails.getVisibility() == View.GONE){
                        bankCardDetails.setVisibility(View.VISIBLE);
                        //cardHeader.setVisibility(View.GONE);
                        bankCardDetails.startAnimation(animationDown);
                    } else {
                        bankCardDetails.setVisibility(View.GONE);
                        //cardHeader.setVisibility(View.VISIBLE);
                        bankCardDetails.startAnimation(animationUp);
                    }
                }
            });
        }
    }




}
