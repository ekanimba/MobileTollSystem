package DataBaseLayer;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import Model.BankCard;

public class BankCardData {

    private static DatabaseReference mDatabase;
    private static FirebaseAuth auth;
    private static final String TAG = "BANKCARDLOG";
    private static String key;


    public static void AddNewBankCard(BankCard bankCard){

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        key = mDatabase.push().getKey();

        try {
            mDatabase.child("users").child(auth.getCurrentUser().getUid()).child("bankcards").child(key).setValue(bankCard);

        } catch(DatabaseException e) {
            throw e;
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public static void getBankCardList(final BankCardListcallBack callBack){

        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth =  FirebaseAuth.getInstance();

        Query query = mDatabase.child("users").child(Objects.requireNonNull(auth.getUid())).child("bankcards");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<BankCard> tempList = new ArrayList<>();
                ArrayList<String> keyList = new ArrayList<>();

                try{
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String key = snapshot.getKey();

                        String cardNumber = snapshot.child("cardNumber").getValue(String.class);;
                        String cardHolderName = snapshot.child("cardHolderName").getValue(String.class);;
                        String cardExpNumberMonth = snapshot.child("cardExpNumberMonth").getValue(String.class);;
                        String cardExpNumberYear = snapshot.child("cardExpNumberYear").getValue(String.class);;
                        String cardCcv = snapshot.child("cardCcv").getValue(String.class);


                        keyList.add(key);
                        tempList.add(new BankCard(cardNumber, cardHolderName, cardExpNumberMonth, cardExpNumberYear,cardCcv));
                    }

                    callBack.onBankCardListcallBack(tempList, keyList);
                }catch (DatabaseException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void deleteBankCard(String cardNumber){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        Query query = mDatabase.child("users").child(Objects.requireNonNull(auth.getUid())).child("bankcards").orderByChild("cardNumber").equalTo(cardNumber);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for( DataSnapshot cardNumberSnapshot: dataSnapshot.getChildren()) {
                    cardNumberSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    public interface BankCardListcallBack{
        void onBankCardListcallBack(ArrayList<BankCard> bankCards, ArrayList<String>keyList);
    }
}
