package DataBaseLayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import Model.Trip;

public class TripData {

    private static DatabaseReference mDatabase;
    private static DatabaseReference dbUsersref;
    private static DatabaseReference DatabaseUpdateTrip;
    private static FirebaseAuth auth;
    private static final String TAG = "TripData";
    private static Double userBalance;


    public static void getTrip(String tripId, final TripcallBack tripcallBack){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth =  FirebaseAuth.getInstance();

        Query query = mDatabase.child("users").child(Objects.requireNonNull(auth.getUid())).child("toll/exit/"+tripId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String entryToll = snapshot.child("entryToll").getValue(String.class);
                String exitToll = snapshot.child("exitToll").getValue(String.class);
                String entryTime = snapshot.child("entryTime").getValue(String.class);
                String exitTime = snapshot.child("exitTime").getValue(String.class);
                String ticketPrice = snapshot.child("ticketPrice").getValue(String.class);
                String carRegistrationNumber = snapshot.child("carRegistrationNumber").getValue(String.class);
                String dateTrip = snapshot.child("exitTime").getValue(String.class);
                Boolean paid = snapshot.child("paid").getValue(Boolean.class);
                String tripId = snapshot.child("tripId").getValue(String.class);

                Trip temp = new Trip(entryToll, exitToll,entryTime, exitTime, ticketPrice,carRegistrationNumber,dateTrip, paid, tripId);

                tripcallBack.onTripcallBack(temp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void getTripList(final TripListcallBack tripListcallBack){

        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth =  FirebaseAuth.getInstance();

        Query query = mDatabase.child("users").child(Objects.requireNonNull(auth.getUid())).child("toll/exit");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Trip> tempList = new ArrayList<>();
                ArrayList<String> keyList = new ArrayList<>();

                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String key = snapshot.getKey();

                        String entryToll = snapshot.child("entryToll").getValue(String.class);
                        String exitToll = snapshot.child("exitToll").getValue(String.class);
                        String entryTime = snapshot.child("entryTime").getValue(String.class);
                        String exitTime = snapshot.child("exitTime").getValue(String.class);
                        String ticketPrice = snapshot.child("ticketPrice").getValue(String.class);
                        String carRegistrationNumber = snapshot.child("carRegistrationNumber").getValue(String.class);
                        String dateTrip = snapshot.child("exitTime").getValue(String.class);
                        Boolean paid = snapshot.child("paid").getValue(Boolean.class);
                        String tripId = snapshot.child("tripId").getValue(String.class);


                        keyList.add(key);
                        tempList.add(new Trip(entryToll, exitToll,entryTime, exitTime, ticketPrice,carRegistrationNumber,dateTrip, paid, tripId));
                    }

                    tripListcallBack.onTripListcallBack(tempList,keyList);
                } catch (DatabaseException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public static void performPayment(final String tripId,final Double tickectPrice, final PerformPaymentcallBack performPaymentcallBack){
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users/"+auth.getCurrentUser().getUid()+"/toll/exit/"+tripId);
        dbUsersref = FirebaseDatabase.getInstance().getReference("users/"+auth.getCurrentUser().getUid());
        dbUsersref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userBalance = dataSnapshot.child("balance").getValue(Double.class);
                if(tickectPrice <= userBalance){
                    dbUsersref.child("balance").setValue(userBalance);

                    HashMap hashMapbalance = new HashMap();
                    hashMapbalance.put("balance",userBalance-tickectPrice);
                    HashMap hashMapPaid = new HashMap();
                    hashMapPaid.put("paid",true);
                    mDatabase.updateChildren(hashMapPaid);
                    dbUsersref.updateChildren(hashMapbalance);
                    performPaymentcallBack.onPerformPaymentcallBack(true);
                } else {
                    performPaymentcallBack.onPerformPaymentcallBack(false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public interface PerformPaymentcallBack{
        void onPerformPaymentcallBack(Boolean paid);
    }

    public interface TripListcallBack{
        void onTripListcallBack(ArrayList<Trip> trips, ArrayList<String>keyList);
    }
    public interface TripcallBack{
        void onTripcallBack(Trip trip);
    }


    public static ArrayList<Trip> reverseTripList(ArrayList<Trip> alist)
    {
        // Arraylist for storing reversed elements
        // this.revArrayList = alist;
        for (int i = 0; i < alist.size() / 2; i++) {
            Trip temp = alist.get(i);
            alist.set(i, alist.get(alist.size() - i - 1));
            alist.set(alist.size() - i - 1, temp);
        }

        // Return the reversed arraylist
        return alist;
    }
}
