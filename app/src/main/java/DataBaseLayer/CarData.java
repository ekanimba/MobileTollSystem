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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import Model.Car;

public class CarData {
    private static DatabaseReference mDatabase;
    private static FirebaseAuth auth;
    private static final String TAG = "CARLOG";
    private static boolean carExists;
    private static String key;


    public static void AddNewCar(Car car){

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        key = mDatabase.push().getKey();

        try {
            mDatabase.child("users").child(auth.getCurrentUser().getUid()).child("cars").child(key).setValue(car);

        } catch(DatabaseException e) {
            throw e;
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public static void getCarList(final CarListcallBack callBack){

        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth =  FirebaseAuth.getInstance();

        Query query = mDatabase.child("users").child(Objects.requireNonNull(auth.getUid())).child("cars");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Car> tempList = new ArrayList<>();
                ArrayList<String> keyList = new ArrayList<>();

                try{
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String key = snapshot.getKey();

                        String carBrand = snapshot.child("carBrand").getValue(String.class);
                        String carModel = snapshot.child("carModel").getValue(String.class);;
                        String carFuel = snapshot.child("carFuel").getValue(String.class);;
                        String carRegistrationNumber = snapshot.child("carRegistrationNumber").getValue(String.class);
                        keyList.add(key);
                        tempList.add(new Car(carBrand, carModel, carFuel, carRegistrationNumber));
                    }

                    callBack.onCarListcallBack(tempList, keyList);

                }catch (DatabaseException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public static void getFName(final UserData.FNameCallBack callBack){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        Query query = mDatabase.child("users").child(auth.getCurrentUser().getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String fName = null;
                if(dataSnapshot.child("fName").getValue(String.class) != null){
                    fName= dataSnapshot.child("fName").getValue(String.class);
                }

                callBack.onFNameCallBack(fName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public static void deleteCar(String carRegistrationNumber){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        Query query = mDatabase.child("users").child(Objects.requireNonNull(auth.getUid())).child("cars").orderByChild("carRegistrationNumber").equalTo(carRegistrationNumber);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for( DataSnapshot carNumberSnapshot: dataSnapshot.getChildren()) {
                    carNumberSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    public interface CarListcallBack{
        void onCarListcallBack(ArrayList<Car> cars,ArrayList<String>keyList);
    }
}
