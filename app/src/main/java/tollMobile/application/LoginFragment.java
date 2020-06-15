package tollMobile.application;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment {

    private FirebaseAuth auth;
    private String email, password;
    private EditText inputEmail, inputPassword;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, null);


        auth = FirebaseAuth.getInstance();

        TextView textView = (TextView) view.findViewById(R.id.signup);
        Button buttonsignin = view.findViewById(R.id.buttonSignIn);

        inputEmail = (EditText) view.findViewById(R.id.editTextUserID);
        inputPassword = (EditText) view.findViewById(R.id.editTextUserPassword);

        buttonsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("checkS", "signed in");
                /*
                Fragment homeFragment = new HomeFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, homeFragment)
                        .addToBackStack(null)
                        .commit();
                        */
                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), "Enter Email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(getContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                            } else {
                                setToHomeActivity();
                            }
                        }
                    });
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment createAccountFragment = new CreateAccountFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, createAccountFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

    private void setToHomeActivity(){
        Intent myIntent = new Intent(getActivity(), HomeActivity.class);
        startActivity(myIntent);
        getActivity().overridePendingTransition(R.animator.slide_in, R.animator.slide_out);
    }
}
