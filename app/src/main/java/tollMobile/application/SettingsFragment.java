package tollMobile.application;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import DataBaseLayer.UserData;
import Model.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private TextView signOutBtn;
    private LinearLayout accountProfile;
    private LinearLayout accountPayments;
    private TextView accountName;
    private TextView accountEmail;
    private Switch accountNotitication;
    private Switch accountAutopayment;
    Boolean notifications;
    Boolean autoPayment;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        signOutBtn = view.findViewById(R.id.signOutBtn);
        accountProfile = view.findViewById(R.id.account_profile);
        accountName = view.findViewById(R.id.account_name);
        accountEmail = view.findViewById(R.id.account_email);
        accountPayments = view.findViewById(R.id.account_payments);

        accountNotitication = view.findViewById(R.id.account_notitication);
        accountAutopayment = view.findViewById(R.id.accountAutopayment);

        accountNotitication.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UserData.changeNotifications(isChecked);
            }
        });

        accountAutopayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UserData.changeAutoPayment(isChecked);
            }
        });

        accountPayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)getActivity()).transactionFragment(new BankCardsFragment(), true);
            }
        });
        accountProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)getActivity()).transactionFragment(new UserProfileFragment(), true);
            }
        });

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        updateAccount();

        return view;
    }

    public void updateAccount(){
        UserData.getUser(new UserData.UserCallBack() {
            @Override
            public void onUserCallBack(User user) {
                accountName.setText(user.getfName()+" "+user.getlName());
                accountEmail.setText(user.getEmail());
                accountNotitication.setChecked(user.getNotifications());
                accountAutopayment.setChecked(user.getAutoPayment());
                notifications = user.getNotifications();
                autoPayment = user.getAutoPayment();
            }
        });
    }

}
