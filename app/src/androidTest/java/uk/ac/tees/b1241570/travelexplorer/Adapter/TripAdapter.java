package uk.ac.tees.b1241570.travelexplorer.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import studio.knowhere.travelappg.R;
import studio.knowhere.travelappg.ui.Class.TripPlacePoJO;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.MyViewHolder> {

    private Context context;
    private List<TripPlacePoJO> tripPlacePoJOS;
    private FusedLocationProviderClient client;
    double latitude, longitude;

    public TripAdapter(Context context, List<TripPlacePoJO> tripPlacePoJOS) {
        this.context = context;
        this.tripPlacePoJOS = tripPlacePoJOS;
    }
    @Override
    public int getItemCount() {
        return tripPlacePoJOS.size();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
      final TripPlacePoJO tripPlacePoJO = tripPlacePoJOS.get(position);
        Log.v("tag","list count"+tripPlacePoJOS.size());
        holder.mpurpose.setText(tripPlacePoJO.getPurpose());
        holder.mtime.setText(tripPlacePoJO.getTime());
        holder.mplacename.setText(tripPlacePoJO.getPlacename());
        holder.mDate.setText(tripPlacePoJO.getDate());

        client = LocationServices.getFusedLocationProviderClient(context);

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        client.getLastLocation().addOnSuccessListener((Activity) context, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // Toast.makeText(getApplicationContext(),"ff:0",Toast.LENGTH_LONG).show();
                    // locationAdd.setText(address);
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    Log.v("tag", "lat is" + latitude);
                    Log.v("tag", "long is" + longitude);
                    // Toast.makeText(getContext(),"ff:0"+latitude,Toast.LENGTH_LONG).show();
                   // new BreakfastActivity.GetContacts().execute();
                }
            }
        });

        holder.mMapVew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr="+latitude+","+longitude+"&daddr="+tripPlacePoJO.getLattitude()+","+tripPlacePoJO.getLongitude()+""));
                context.startActivity(intent);
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mpurpose;
        public TextView mtime;
        public TextView mplacename;
        public TextView mDate;
        public Button mMapVew;

        public MyViewHolder(View view) {
            super(view);
            mpurpose = view.findViewById(R.id.purpose);
            mtime = view.findViewById(R.id.time);
            mplacename = view.findViewById(R.id.placename);
            mDate = view.findViewById(R.id.date);
            mMapVew = view.findViewById(R.id.view_map);
        }
    }

}