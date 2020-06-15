package DataBaseLayer;

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

import Model.Car;
import Model.TollEntry;

public class TollEntryData {

    private static DatabaseReference mDatabase;
    private static FirebaseAuth auth;
    private static final String TAG = "TollentryLog";

    public static void getTollEntryList(final EntryTollListcallBack callBack){

        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth =  FirebaseAuth.getInstance();

        Query query = mDatabase.child("users").child(Objects.requireNonNull(auth.getUid())).child("toll").child("entry");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<TollEntry> tempList = new ArrayList<>();
                ArrayList<String> keyList = new ArrayList<>();

                try{
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String key = snapshot.getKey();

                        String entryTime = snapshot.child("entryTime").getValue(String.class);
                        String entryToll = snapshot.child("entryToll").getValue(String.class);
                        String tagId = snapshot.child("tagId").getValue(String.class);
                        String userId = snapshot.child("userId").getValue(String.class);
                        String carRegistrationNumber = snapshot.child("carRegistrationNumber").getValue(String.class);

                        tempList.add(new TollEntry(entryTime , entryToll, tagId, userId,carRegistrationNumber));

                        keyList.add(key);

                    }

                    callBack.onEntryTollListcallBack(tempList, keyList);

                }catch (DatabaseException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

        public interface EntryTollListcallBack{
        void onEntryTollListcallBack(ArrayList<TollEntry> toll, ArrayList<String>keyList);
    }
}
