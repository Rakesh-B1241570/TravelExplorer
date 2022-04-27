package uk.ac.tees.b1241570.travelexplorer.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import studio.knowhere.travelappg.Activity.CoffeShopActivity;
import studio.knowhere.travelappg.Activity.MealsActivity;
import studio.knowhere.travelappg.Activity.ShoppingMallActivity;
import studio.knowhere.travelappg.Activity.TemplesActivity;
import studio.knowhere.travelappg.Activity.TreckingPlaceActivity;
import studio.knowhere.travelappg.Database.PreferenceManager;
import studio.knowhere.travelappg.Database.SqliteHelper;
import studio.knowhere.travelappg.Database.TripPlace;

public class BreakfastActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog pDialog;
    private ListView lv;
    String id, userid, assetid, date;
    Button mClick;
    ArrayList<HashMap<String, String>> contactList;
    ActivtyDay activtyDay;
    ArrayList<String> mList = new ArrayList<>();
    double latitude, longitude;
    EditText mTime;
    SimpleDateFormat dateFormatter;
    TimePickerDialog mTimePicker;
    private FusedLocationProviderClient client;
    String PURPOSE = "BREAKFAST";
    SqliteHelper sqliteHelper;
    PreferenceManager preferenceManager;
    Calendar mcurrentTime;
    int selectedHourfinal, selectedMinutefinal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);

        contactList = new ArrayList<>();
        activtyDay = new ActivtyDay();

        mList = activtyDay.getInstance().getList();
        sqliteHelper = new SqliteHelper(this);
        getcurrentadress();
        preferenceManager = new PreferenceManager(getApplicationContext());

        lv = (ListView) findViewById(R.id.listofhotels_listview);
        mTime = (EditText) findViewById(R.id.time_id);
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        setTimeField();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object SelctedItem = parent.getItemAtPosition(position);
                Log.v("TAG", "SELECTED ITEM" + SelctedItem);
                String value = String.valueOf(parent.getItemAtPosition(position));
                Log.v("TAG", "VALES ARE" + value);
                ArrayList<String> arrayListvalue = new ArrayList<>(Arrays.asList(value.split(",")));
                ArrayList<String> arrayListPlaceName = new ArrayList<>(Arrays.asList(arrayListvalue.get(0).split("=")));
                String PlaceName = arrayListPlaceName.get(1);
//                ArrayList<String> arrayListLattitude = new ArrayList<>(Arrays.asList(arrayListvalue.get(4).split("=")));
//                String PlaceLat = arrayListLattitude.get(1);
//                ArrayList<String> arrayListLongitude = new ArrayList<>(Arrays.asList(arrayListvalue.get(2).split("=")));
//                String PlaceLong = arrayListLongitude.get(1);
                TextView lat = view.findViewById(R.id.lat_id);
                TextView lng = view.findViewById(R.id.lng_id);
                String PlaceLong = lng.getText().toString();
                String PlaceLat = lat.getText().toString();
                String Userid = preferenceManager.getKeyUserid(userid);
                //String date = preferenceManager.getKeyAssetid(assetid);

                sqliteHelper.addTripPlace(new TripPlace(null, Userid, date, mTime.getEditableText().toString(), PlaceLat, PlaceLong, PURPOSE, PlaceName));

                startAlert();
                if (mList.size() == 0) {
                    Toast.makeText(BreakfastActivity.this, "Lastoption", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BreakfastActivity.this, HomeActivity.class);
                    startActivity(intent);

                } else {

                    Log.v("TAG", "INDEX ZERO" + mList.get(0));
                    if (mList.get(0).equalsIgnoreCase("BREAKFAST")) {
                        mList.remove("BREAKFAST");
                        activtyDay.getInstance().setList(mList);

                    } else if (mList.get(0).equalsIgnoreCase("SHOPPING")) {
                        mList.remove("SHOPPING");
                        activtyDay.getInstance().setList(mList);
                        Intent intent = new Intent(BreakfastActivity.this, ShoppingMallActivity.class);
                        startActivity(intent);
                    } else if (mList.get(0).equalsIgnoreCase("TRECKING")) {
                        mList.remove("TRECKING");
                        activtyDay.getInstance().setList(mList);
                        Intent intent = new Intent(BreakfastActivity.this, TreckingPlaceActivity.class);
                        startActivity(intent);
                    } else if (mList.get(0).equalsIgnoreCase("TEMPLE")) {
                        mList.remove("TEMPLE");
                        activtyDay.getInstance().setList(mList);
                        Intent intent = new Intent(BreakfastActivity.this, TemplesActivity.class);
                        startActivity(intent);
                    } else if (mList.get(0).equalsIgnoreCase("COFFE")) {
                        mList.remove("COFFE");
                        activtyDay.getInstance().setList(mList);
                        Intent intent = new Intent(BreakfastActivity.this, CoffeShopActivity.class);
                        startActivity(intent);
                    } else if (mList.get(0).equalsIgnoreCase("MEAL")) {
                        mList.remove("MEAL");
                        activtyDay.getInstance().setList(mList);
                        Intent intent = new Intent(BreakfastActivity.this, MealsActivity.class);
                        startActivity(intent);
                    }
                }

                // Toast.makeText(BreakfastActivity.this, "item is"+SelctedItem+value, Toast.LENGTH_SHORT).show();
               /* Intent intent = new Intent(BreakfastActivity.this, IndividualProfileActivity.class);
                intent.putExtra("KEY",value);
                startActivity(intent);*/

            }
        });

    }

    @Override
    public void onClick(View view) {

    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(BreakfastActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
           // String jsonStr = sh.makeServiceCall("https://api.foursquare.com/v2/venues/search?client_id=LRREPRA2NJJC022WL4TRTHBQQBRUDRKIUOPV2RF5RYXRN5L5&client_secret=AQME2R4XW1NVLJG2KPDCKVSCPEBHLGBZDOUSYDKDJE3U13AJ&v=20200224&ll=" + latitude + "," + longitude + "&query=HOTEL");
            String jsonStr = sh.makeServiceCall("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latitude+"%2C"+longitude+"&radius=100000&type=food|returant&keyword=food&key=AIzaSyD5v2LwR5Vf3xVPIb8P6kqy_tn2YY5XfdU");

            Log.v("TAG", "URL IS" + "https://api.foursquare.com/v2/venues/search?client_id=BO51ZH5NB2GJMI1GXCEN4SRBICLKHX1ZIO3SPCJGHFSLEVQD&client_secret=AQHXA1BLWNYA55PQQESETWSOFFIPZUHZQNUJRDOSLRXQ2IAX&v=20200224&ll=" + latitude + "," + longitude + "&query=HOTEL");
            // Log.e("TAG", "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    System.out.println(jsonStr);
                   // JSONObject responsejsonObj = jsonObj.getJSONObject("jsonStr");
                    // Getting JSON Array node
                    JSONArray venuesresult = jsonObj.getJSONArray("results");

                    // looping through All Contacts
                    for (int i = 0; i < venuesresult.length(); i++) {
                        JSONObject c = venuesresult.getJSONObject(i);


                        String name = c.getString("name");
                        String distance = c.getString("rating");
                        String formattedAddress = c.getString("vicinity");

                       // distance = String.valueOf(Float.parseFloat(distance) /);

                        JSONObject geometry = c.getJSONObject("geometry");
                        JSONObject location = geometry.getJSONObject("location");
                        String lat = location.getString("lat");
                        String lng = location.getString("lng");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("name", name);
                        contact.put("distance", distance);
                        contact.put("formattedAddress", formattedAddress);
                        contact.put("lat", lat);
                        contact.put("lng", lng);

                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e("TAG", "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e("TAG", "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    BreakfastActivity.this, contactList,
                    R.layout.list_hotel_item, new String[]{"name", "distance",
                    "formattedAddress", "lat", "lng"}, new int[]{R.id.name_id,
                    R.id.distance_id, R.id.formattedaddress_id, R.id.lat_id, R.id.lng_id});

            lv.setAdapter(adapter);
        }


    }

    public void getcurrentadress() {

        client = LocationServices.getFusedLocationProviderClient(BreakfastActivity.this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        client.getLastLocation().addOnSuccessListener(BreakfastActivity.this, new OnSuccessListener<Location>() {
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
                    new GetContacts().execute();
                }
            }
        });
    }
    private  void setTimeField() {
        mTime.setOnClickListener(this);

        mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
       // mcurrentTime.setTimeInMillis(System.currentTimeMillis());

        mTimePicker = new TimePickerDialog(BreakfastActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour,  int selectedMinute) {
                mTime.setText(selectedHour + ":" + selectedMinute);
                selectedHourfinal = selectedHour;
                selectedMinutefinal = selectedMinute;
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
        Log.v("TAG","CUTTENT TIME"+mcurrentTime);
        Log.v("TAG","SELECTED TIME"+mTime.getEditableText().toString());


    }

    public void startAlert() {

        Calendar cal = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());


        String[] time = mTime.getEditableText().toString().split(":");

        cal.set(Calendar.HOUR_OF_DAY, selectedHourfinal);  //HOUR
        cal.set(Calendar.MINUTE, selectedMinutefinal);       //MIN

        Log.v("TAG","SELESTED OUR AND MINUTE"+selectedMinutefinal);
       // cal.set(Calendar.SECOND, 10);       //SEC
       // EditText text = findViewById(R.id.time);
       // int i = Integer.parseInt(String.valueOf(mcurrentTime));
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
       // Toast.makeText(this, "Alarm set in "+cal.getTimeInMillis()+"  seconds", Toast.LENGTH_LONG).show();




    }

}
