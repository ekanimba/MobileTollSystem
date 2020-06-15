package tollMobile.application;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

import DataBaseLayer.CarData;
import Model.Car;


public class AddCarFragment extends Fragment {

    String inputCarBrand, inputCarModel, inputCarFuel, inputCarRegistrationNumber;
    ImageButton buttonCloseCarfragment;
    EditText editTextCarBrand, editTextCarModel, editCarFuel, editTextCarRegistrationNumber;
    Button buttonAddvehicle;



    public AddCarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_car, container, false);

        buttonCloseCarfragment = view.findViewById(R.id.buttonCloseCarfragment);
        editTextCarBrand = view.findViewById(R.id.editTextCarBrand);
        editTextCarModel = view.findViewById(R.id.editTextCarModel);
        editCarFuel = view.findViewById(R.id.editCarFuel);
        editTextCarRegistrationNumber = view.findViewById(R.id.editTextCarRegistrationNumber);

        buttonAddvehicle = view.findViewById(R.id.buttonAddvehicle);




        buttonCloseCarfragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)getActivity()).transactionFragment(new CarsFragment(), false);
            }
        });

        buttonAddvehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputCarBrand = editTextCarBrand.getText().toString();
                inputCarModel = editTextCarModel.getText().toString();
                inputCarFuel = editCarFuel.getText().toString();
                inputCarRegistrationNumber = editTextCarRegistrationNumber.getText().toString();

                Car car = new Car(inputCarBrand,inputCarModel,inputCarFuel,inputCarRegistrationNumber);

                CarData.AddNewCar(car);
                ((HomeActivity)getActivity()).transactionFragment(new CarsFragment(), false);
            }
        });


        return view;
    }

}
