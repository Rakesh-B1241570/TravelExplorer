package uk.ac.tees.b1241570.travelexplorer.ui.home;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import studio.knowhere.travelappg.Activity.PlacesActivity;
import studio.knowhere.travelappg.Database.SqliteHelper;
import studio.knowhere.travelappg.R;
import studio.knowhere.travelappg.ui.Class.CurrentLatLong;

public class HomeFragment extends Fragment {

    private studio.knowhere.travelappg.ui.home.HomeViewModel homeViewModel;
    private FusedLocationProviderClient client;
    double latitude, longitude;
    String address;
    TextView textView;
    Button mViewPlace;
    CurrentLatLong currentLatLong;
    SimpleDateFormat dateFormatter;
    SqliteHelper sqliteHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(studio.knowhere.travelappg.ui.home.HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        textView = root.findViewById(R.id.text_home);
        mViewPlace = root.findViewById(R.id.viewplace);
        currentLatLong = new CurrentLatLong();
        client = LocationServices.getFusedLocationProviderClient(getContext());

        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        Log.v("TAG", "date is" + date);
       /* sqliteHelper = new SqliteHelper(getContext());
        if (!sqliteHelper.isDateExists(date)) {

            //Email does not exist now add new user to database
           // sqliteHelper.addUser(new User(null, UserName, Email, Password));
            Toast.makeText(getContext(), "Previous Data Saved", Toast.LENGTH_LONG).show();

        }else {
           // sqliteHelper.deleteAllTrip();
            //Email exists with email input provided so show error user already exist
            Toast.makeText(getContext(), "Previous Data Cleared", Toast.LENGTH_LONG).show();
        }*/

        client.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    //  Toast.makeText(getContext(),"ff:0",Toast.LENGTH_LONG).show();
                    // locationAdd.setText(address);
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    Log.v("tag", "lat is" + latitude);
                    Log.v("tag", "long is" + longitude);
                    // Toast.makeText(getContext(),"ff:0"+latitude,Toast.LENGTH_LONG).show();
                    getAddress(latitude, longitude);
                    currentLatLong.setLattitude(String.valueOf(latitude));
                    currentLatLong.setLongitude(String.valueOf(longitude));
                }
            }
        });

        mViewPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getContext(), PlacesActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }

    private void getAddress(double latitude, double longitude) {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getContext(), Locale.getDefault());


        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            address = addresses.get(0).getAddressLine(0);
            textView.setText(address);
            Log.v("ddff", "adres:" + address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        client.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // Toast.makeText(getContext(),"ff:0",Toast.LENGTH_LONG).show();
                    // locationAdd.setText(address);
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    Log.v("tag", "lat is" + latitude);
                    Log.v("tag", "long is" + longitude);
                    // Toast.makeText(getContext(),"ff:0"+latitude,Toast.LENGTH_LONG).show();
                    getAddress(latitude, longitude);
                    currentLatLong.setLattitude(String.valueOf(latitude));
                    currentLatLong.setLongitude(String.valueOf(longitude));
                }
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();

        client.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    //  Toast.makeText(getContext(),"ff:0",Toast.LENGTH_LONG).show();
                    // locationAdd.setText(address);
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    Log.v("tag", "lat is" + latitude);
                    Log.v("tag", "long is" + longitude);
                    // Toast.makeText(getContext(),"ff:0"+latitude,Toast.LENGTH_LONG).show();
                    getAddress(latitude, longitude);
                    currentLatLong.setLattitude(String.valueOf(latitude));
                    currentLatLong.setLongitude(String.valueOf(longitude));
                }
            }
        });
    }
}