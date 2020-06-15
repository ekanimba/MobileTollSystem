package tollMobile.application;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DataBaseLayer.UserData;
import Model.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment implements View.OnClickListener{


    public UserProfileFragment() {
        // Required empty public constructor
    }


    private ImageButton btn_back_settings;
    private TextView profile_account_name,profile_account_email,profile_fname, profile_lname, profile_phone, profile_adress, profile_country, profile_city, profile_zipcode;

    private LinearLayout edit_account_first_name;
    private LinearLayout edit_account_last_name;
    private LinearLayout edit_account_phone_number;
    private LinearLayout edit_account_address;
    private LinearLayout edit_account_country;
    private LinearLayout edit_account_city;
    private LinearLayout edit_account_zipcode;

    private ArrayList<String> dataToEdit;
    private Map<String, String> dataToEditmap = new HashMap<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);


        edit_account_first_name = view.findViewById(R.id.edit_account_first_name);
        edit_account_last_name = view.findViewById(R.id.edit_account_last_name);
        edit_account_phone_number = view.findViewById(R.id.edit_account_phone_number);
        edit_account_address = view.findViewById(R.id.edit_account_address);
        edit_account_country = view.findViewById(R.id.edit_account_country);
        edit_account_city = view.findViewById(R.id.edit_account_city);
        edit_account_zipcode = view.findViewById(R.id.edit_account_zipcode);
        btn_back_settings = view.findViewById(R.id.btn_back_settings);



        profile_account_name = view.findViewById(R.id.profile_account_name);
        profile_account_email = view.findViewById(R.id.profile_account_email);
        profile_fname = view.findViewById(R.id.profile_fName);
        profile_lname = view.findViewById(R.id.profile_lName);
        profile_phone = view.findViewById(R.id.profile_phoneNumber);
        profile_adress = view.findViewById(R.id.profile_adress);
        profile_country = view.findViewById(R.id.profile_country);
        profile_city = view.findViewById(R.id.profile_city);
        profile_zipcode = view.findViewById(R.id.profile_zipcode);


        edit_account_first_name.setOnClickListener(this);
        edit_account_last_name.setOnClickListener(this);
        edit_account_phone_number.setOnClickListener(this);
        edit_account_address.setOnClickListener(this);
        edit_account_country.setOnClickListener(this);
        edit_account_city.setOnClickListener(this);
        edit_account_zipcode.setOnClickListener(this);
        btn_back_settings.setOnClickListener(this);


        updateProfile();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_account_first_name:
                ((HomeActivity)getActivity()).transactionFragmentWithData(new EditUserProfileFragment(), "fName", dataToEditmap.get("fName"));
                break;
            case R.id.edit_account_last_name:
                ((HomeActivity)getActivity()).transactionFragmentWithData(new EditUserProfileFragment(), "lName", dataToEditmap.get("lName"));
                break;
            case R.id.edit_account_phone_number:
                ((HomeActivity)getActivity()).transactionFragmentWithData(new EditUserProfileFragment(), "phoneNumber", dataToEditmap.get("phoneNumber"));
                break;
            case R.id.edit_account_address:
                ((HomeActivity)getActivity()).transactionFragmentWithData(new EditUserProfileFragment(), "address", dataToEditmap.get("address"));
                break;
            case R.id.edit_account_country:
                ((HomeActivity)getActivity()).transactionFragmentWithData(new EditUserProfileFragment(), "country", dataToEditmap.get("country"));
                break;
            case R.id.edit_account_city:
                ((HomeActivity)getActivity()).transactionFragmentWithData(new EditUserProfileFragment(), "city", dataToEditmap.get("city"));
                break;
            case R.id.edit_account_zipcode:
                ((HomeActivity)getActivity()).transactionFragmentWithData(new EditUserProfileFragment(), "zipcode", dataToEditmap.get("zipcode"));
                break;
            case R.id.btn_back_settings:
                ((HomeActivity)getActivity()).transactionFragment(new SettingsFragment(), false);
                break;
        }
    }

    public void updateProfile(){
        UserData.getUser(new UserData.UserCallBack() {
            @Override
            public void onUserCallBack(User user) {

                dataToEditmap.put("fName", user.getfName());
                dataToEditmap.put("lName", user.getlName());
                dataToEditmap.put("phoneNumber", user.getPhoneNumber());
                dataToEditmap.put("address", user.getAddress());
                dataToEditmap.put("country", user.getCountry());
                dataToEditmap.put("city", user.getCity());
                dataToEditmap.put("zipcode", user.getZipCode());

                profile_account_name.setText(user.getlName()+" "+user.getfName());
                profile_account_email.setText(user.getEmail());
                profile_fname.setText(user.getfName());
                profile_lname.setText(user.getlName());
                profile_phone.setText(user.getPhoneNumber());
                profile_adress.setText(user.getAddress());
                profile_country.setText(user.getCountry());
                profile_city.setText(user.getCity());
                profile_zipcode.setText(user.getZipCode());
            }
        });
    }

}
