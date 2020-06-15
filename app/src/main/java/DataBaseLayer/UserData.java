package DataBaseLayer;

import androidx.annotation.NonNull;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

import Model.User;

public class UserData {

    private static DatabaseReference mDatabase;
    private static FirebaseAuth auth;
    private static boolean userExist;
    private static String TAG = "USERLOG";

    // create an account in firebase

    public static void createUserAccount(String fName, String lName, String email, String phoneNumber, String address, String city, String country, String zipCode, Boolean notifications, Boolean autoPayment) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        User user = new User(fName, lName, email, phoneNumber, address,city,  country, zipCode, notifications, autoPayment);

        try {
            mDatabase.child("users").child(auth.getCurrentUser().getUid()).setValue(user);

        } catch (DatabaseException e) {
            throw e;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    public static void updateBalance(final double balance) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        mDatabase.child("users").child(Objects.requireNonNull(auth.getUid())).child("balance").setValue(balance);
    }

    public static void getUser (final UserCallBack callBack) {

        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        Query query = mDatabase.child("users").child(Objects.requireNonNull(auth.getUid()));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    callBack.onUserCallBack(new User(
                            dataSnapshot.child("fName").getValue(String.class),
                            dataSnapshot.child("lName").getValue(String.class),
                            dataSnapshot.child("email").getValue(String.class),
                            dataSnapshot.child("phoneNumber").getValue(String.class),
                            dataSnapshot.child("address").getValue(String.class),
                            dataSnapshot.child("city").getValue(String.class),
                            dataSnapshot.child("country").getValue(String.class),
                            dataSnapshot.child("zipCode").getValue(String.class),
                            dataSnapshot.child("notifications").getValue(Boolean.class),
                            dataSnapshot.child("autoPayment").getValue(Boolean.class)
                    ));
                }catch (DatabaseException e){
                    e.printStackTrace();
                }catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public static void changeNotifications(Boolean notifications){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        try {
            mDatabase.child("users").child(auth.getCurrentUser().getUid()).child("notifications").setValue(notifications);

        } catch (DatabaseException e) {
            throw e;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    public static void changeAutoPayment(Boolean autoPayment){
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users/"+auth.getCurrentUser().getUid());
        HashMap hashMapautoPayment = new HashMap();
        hashMapautoPayment.put("autoPayment",autoPayment);
        mDatabase.updateChildren(hashMapautoPayment);
    }

    public static void updateEditUser(String Context, String data){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        try {
            mDatabase.child("users").child(auth.getCurrentUser().getUid()).child(Context).setValue(data);

        } catch (DatabaseException e) {
            throw e;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static void getBalance(final BalanceCallBack callBack){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        Query query = mDatabase.child("users").child(auth.getCurrentUser().getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    callBack.onBalanceCallBack(dataSnapshot.child("balance").getValue(double.class));
                }catch (DatabaseException e){
                    e.printStackTrace();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public interface UserCallBack{
        void onUserCallBack(User user);
    }
    public interface EmailCallBack{
        void onEmailCallBack(String email);
    }

    public interface BalanceCallBack{
        void onBalanceCallBack(double balance);
    }

    public interface FNameCallBack{
        void onFNameCallBack(String fName);
    }
}
