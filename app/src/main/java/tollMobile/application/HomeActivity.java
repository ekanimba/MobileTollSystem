package tollMobile.application;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import Model.Trip;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView navView;


    private HomeFragment homeFragment;
    private CarsFragment carsFragment;
    private UserProfileFragment profileFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        navView = findViewById(R.id.bottomNavView);

        homeFragment = new HomeFragment();
        carsFragment = new CarsFragment();
        profileFragment = new UserProfileFragment();


        //SetFragment(profileFragment, carsFragment, profileFragment);
        loadFragment(new HomeFragment());

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;

                switch (menuItem.getItemId()) {
                    case R.id.menu_home:
                        fragment = new HomeFragment();
                        break;
                        //SetFragment(homeFragment, profileFragment, profileFragment);
                        //return true;
                    case R.id.menu_cars:
                        fragment = new CarsFragment();
                        break;
                        //SetFragment(carsFragment,homeFragment ,profileFragment);
                        //return true;
                    case R.id.menu_profile:
                        fragment = new SettingsFragment();
                        break;
                        //SetFragment(profileFragment, homeFragment ,homeFragment);
                        //return true;
                }
                return loadFragment(fragment);
            }
        });
    }

    public void transactionFragment(Fragment fragment, boolean navbuttonV){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        if(navbuttonV){
            navView.setVisibility(View.GONE);
        }else{
            navView.setVisibility(View.VISIBLE);
        }
    }
    public void transactionFragmentWithData(Fragment fragment, String data, String dataContent){
        ArrayList<String> dataToPass = new ArrayList<>();
        dataToPass.add(data);
        dataToPass.add(dataContent);

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("DataToEdit", dataToPass);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void transactionFragmentWithTripId(Fragment fragment, String tripId, Boolean navbuttonV){
        Bundle bundle = new Bundle();
        bundle.putString("TripId", tripId);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        if(navbuttonV){
            navView.setVisibility(View.GONE);
        }else{
            navView.setVisibility(View.VISIBLE);
        }
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
    public boolean shouldAllowBack(){
        return false;
    }


    @Override
    public void onBackPressed() {

        if(!shouldAllowBack()){

        }else {
            super.onBackPressed();
        }
    }
}
