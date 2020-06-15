package tollMobile.application;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import DataBaseLayer.UserData;

public class CreateAccountFragment extends Fragment {

    Scene scene1, scene2, scene3;

    private EditText inputFName, inputLName, inputEmail, inputPhoneNumber, inputAddress, inputCity, inputCountry, inputZipCode, inputPassword, inputNumberPlate;
    private String fName, lName, email, phoneNumber, address, city, country, zipCode, password, numberPlate;
    private Boolean notifications, autoPayment;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_create_account, null);
        // Inflate the layout for this fragment


        auth = FirebaseAuth.getInstance();

        final ImageView backlogin = (ImageView) view.findViewById(R.id.backLogin);
        final ImageView backscene1 = (ImageView) view.findViewById(R.id.backscene1);
        final ImageView backscene2 = (ImageView) view.findViewById(R.id.backscene2);

        final Button buttonscene1 = (Button) view.findViewById(R.id.buttonscene1);
        final Button buttonscene2 = (Button) view.findViewById(R.id.buttonscene2);

        final ImageView imagescene1 = (ImageView) view.findViewById(R.id.imagescene1);
        final ImageView imagescene2 = (ImageView) view.findViewById(R.id.imagescene2);
        final ImageView imagescene3 = (ImageView) view.findViewById(R.id.imagescene3);

        ViewGroup sceneRoot = (ViewGroup) view.findViewById(R.id.scene_root);



        scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.create_account_scene_1, getActivity());
        scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.create_account_scene_2, getActivity());
        scene3 = Scene.getSceneForLayout(sceneRoot, R.layout.create_account_scene_3, getActivity());

        inputFName = (EditText) view.findViewById(R.id.editTextFirstname);
        inputLName = (EditText) view.findViewById(R.id.editTextlastname);
        inputEmail = (EditText) view.findViewById(R.id.editTextEmail);
        inputPhoneNumber = (EditText)view.findViewById(R.id.editTextPhone);
        inputPassword = (EditText) view.findViewById(R.id.editTextPassword);

        final Transition fadeTransition = TransitionInflater.from(getActivity()).inflateTransition(R.transition.fade_transition);

        backlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment loginFragment = new LoginFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, loginFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


        backscene1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.go(scene1, fadeTransition);
                backscene1.setVisibility(View.GONE);
                backlogin.setVisibility(View.VISIBLE);

                buttonscene2.setVisibility(View.GONE);
                buttonscene1.setVisibility(View.VISIBLE);

                imagescene2.setVisibility(View.GONE);
                imagescene1.setVisibility(View.VISIBLE);
            }
        });
        backscene2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.go(scene2, fadeTransition);
                backscene2.setVisibility(View.GONE);
                backscene1.setVisibility(View.VISIBLE);

                buttonscene2.setVisibility(View.VISIBLE);

                imagescene3.setVisibility(View.GONE);
                imagescene2.setVisibility(View.VISIBLE);
            }
        });

        // First scene where user enters first name , last name etc
        buttonscene1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = inputEmail.getText().toString();
                fName = inputFName.getText().toString();
                lName = inputLName.getText().toString();
                password = inputPassword.getText().toString();
                phoneNumber = inputPhoneNumber.getText().toString();





                // check if there's any field that is empty
                if (TextUtils.isEmpty(fName)) {
                    Toast.makeText(getContext(), "Enter First Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(lName)) {
                    Toast.makeText(getContext(), "Enter Last Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(getContext(), "Enter Phone Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                TransitionManager.go(scene2, fadeTransition);
                buttonscene1.setVisibility(View.GONE);
                buttonscene2.setVisibility(View.VISIBLE);

                imagescene1.setVisibility(View.GONE);
                imagescene2.setVisibility(View.VISIBLE);

                backlogin.setVisibility(View.GONE);
                backscene1.setVisibility(View.VISIBLE);


            }
        });

        // second scene where user enters address, zip code etc
        buttonscene2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inputAddress = (EditText) view.findViewById(R.id.editTextAddress);
                inputZipCode = (EditText) view.findViewById(R.id.editTextzipcode);
                inputCity = (EditText) view.findViewById(R.id.editTextcity);
                inputCountry = (EditText) view.findViewById(R.id.editTextcountry);
                notifications = true;
                autoPayment = true;

                address = inputAddress.getText().toString();
                city = inputCity.getText().toString();
                zipCode = inputZipCode.getText().toString();
                country = inputCountry.getText().toString();

                // check if there's any field that is empty
                if (TextUtils.isEmpty(address)) {
                    Toast.makeText(getContext(), "Enter Address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(city)) {
                    Toast.makeText(getContext(), "Enter City", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(zipCode)) {
                    Toast.makeText(getContext(), "Enter Zip Code", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(country)) {
                    Toast.makeText(getContext(), "Enter Country", Toast.LENGTH_SHORT).show();
                    return;
                }

                TransitionManager.go(scene3,fadeTransition);
                buttonscene2.setVisibility(View.GONE);

                imagescene2.setVisibility(View.GONE);
                imagescene3.setVisibility(View.VISIBLE);

                backscene1.setVisibility(View.GONE);
                backscene2.setVisibility(View.VISIBLE);

                Log.d("testing", " clicking");

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()) {
                            Toast.makeText(getContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }else {
                            //Create new user and car
                            UserData.createUserAccount(fName, lName, email, phoneNumber, address,city,  country, zipCode, notifications, autoPayment);
                            setToHomeActivity();
                        }
                    }
                });
            }
        });

        return view;
    }

    private void setToHomeActivity(){
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
    }

}
