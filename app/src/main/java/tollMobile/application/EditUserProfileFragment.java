package tollMobile.application;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import DataBaseLayer.UserData;


public class EditUserProfileFragment extends Fragment {


    TextView textView_edit_context;
    EditText edit_text_context;
    Button btn_edit_confirm;
    ImageButton btn_back_user_profile;
    ArrayList<String> receiveData;

    public EditUserProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_user_profile, container, false);

        textView_edit_context = view.findViewById(R.id.textView_edit_context);
        edit_text_context = view.findViewById(R.id.edit_text_context);
        btn_edit_confirm = view.findViewById(R.id.btn_edit_confirm);

        btn_back_user_profile = view.findViewById(R.id.btn_back_user_profile);

        Bundle bundle = this.getArguments();
        receiveData = bundle.getStringArrayList("DataToEdit");


        textView_edit_context.setText(displayContent(receiveData.get(0)));
        edit_text_context.setText(receiveData.get(1));


        btn_edit_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData.updateEditUser(receiveData.get(0), edit_text_context.getText().toString());
                ((HomeActivity)getActivity()).transactionFragment(new UserProfileFragment(), true);

            }
        });

        btn_back_user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)getActivity()).transactionFragment(new UserProfileFragment(), true);
            }
        });
        return view;
    }

    private String displayContent(String display){
        String outDisplay ="";
        switch (display){
            case "fName":
                outDisplay="First Name";
                break;
            case "lName":
                outDisplay="Last Name";
                break;
            case "phoneNumber":
                outDisplay="Last Name";
                break;
            case "address":
                outDisplay="Last Name";
                break;
            case "country":
                outDisplay="Last Name";
                break;
            case "city":
                outDisplay="Last Name";
                break;
            case "zipcode":
                outDisplay="Last Name";
                break;
        }
        return outDisplay;
    }




}
