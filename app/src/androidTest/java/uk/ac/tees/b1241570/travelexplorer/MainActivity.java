package uk.ac.tees.b1241570.travelexplorer;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.b1241570.travelexplorer.Database.PreferenceManager;
import uk.ac.tees.b1241570.travelexplorer.Database.SqliteHelper;
import uk.ac.tees.b1241570.travelexplorer.Database.User;


public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    //Declaration EditTexts
    EditText editTextEmail;
    EditText editTextPassword;

    //Declaration TextInputLayout
    TextInputLayout textInputLayoutEmail;
    TextInputLayout textInputLayoutPassword;

    //Declaration Button
    Button buttonLogin;

    //Declaration SqliteHelper
    SqliteHelper sqliteHelper;
    PreferenceManager preferenceManager;
    NotificationManagerCompat mNotificationManagerCompat;
    final String CHANNEL_ID = "Important_mail_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAndRequestPermissions();
        //  createNotificationChannel();
        mNotificationManagerCompat = NotificationManagerCompat.from(MainActivity.this);
        sqliteHelper = new SqliteHelper(this);
        initCreateAccountTextView();
        initViews();
        preferenceManager = new PreferenceManager(getApplicationContext());

        //set click event of login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                mNotificationManagerCompat.cancelAll();
//
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
//                        R.drawable.ic_audiotrack_dark);
//
//                //open the url when user taps the notification
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.c1ctech.com/"));
//                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
//
//
//                Notification notification = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
//                        .setSmallIcon(R.mipmap.ic_launcher_round)
//                        .setContentTitle("title")
//                        .setContentText("text")
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                        //Set the intent that will fire when the user taps the notification
//                        .setContentIntent(pendingIntent)
//                        .setAutoCancel(true)
//                        .build();
//
//                // notificationId is a unique int for each notification that you must define
//                mNotificationManagerCompat.notify(1, notification);


                //Check user input is correct or not
                if (validate()) {

                    //Get values from EditText fields
                    String Email = editTextEmail.getText().toString();
                    String Password = editTextPassword.getText().toString();

                    //Authenticate user
                    User currentUser = sqliteHelper.Authenticate(new User(null, null, Email, Password));

                    //Check Authentication is successful or not
                    if (currentUser != null) {
                        String iduser = currentUser.id;
                        String name = currentUser.userName;
                        preferenceManager.setKeyUserid(iduser);
                        Log.v("TAG", "CURRENT USER" + name + iduser);
                        Snackbar.make(buttonLogin, "Successfully Logged in!", Snackbar.LENGTH_LONG).show();

                        //User Logged in Successfully Launch You home screen activity
                        Intent intentw = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intentw);
                        finish();
                    } else {

                        //User Logged in Failed
                        Snackbar.make(buttonLogin, "Failed to log in , please try again", Snackbar.LENGTH_LONG).show();

                    }
                }
            }
        });


    }

    private void createNotificationChannel() {

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            //Channel name
            CharSequence name = "Important_mail_channel";

            //Channel description
            String description = "This channel will show notification only to important people";

            //The importance level you assign to a channel applies to all notifications that you post to it.
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            //Create the NotificationChannel
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);

            //Set channel description
            channel.setDescription(description);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void initCreateAccountTextView() {
        TextView textViewCreateAccount = (TextView) findViewById(R.id.textViewCreateAccount);
        textViewCreateAccount.setText(fromHtml("<font color='#ffffff'>I don't have account yet. </font><font color='#0c0099'>Register</font>"));
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    //this method is used to connect XML views to its Objects
    private void initViews() {
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);

    }


    //This method is for handling fromHtml method deprecation
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    //This method is used to validate input given by user
    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            editTextEmail.setError("Please enter valid email!");
        } else {
            valid = true;
            editTextEmail.setError(null);
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            editTextPassword.setError("Please enter valid password!");
        } else {
            if (Password.length() > 5) {
                valid = true;
                editTextPassword.setError(null);
            } else {
                valid = false;
                editTextPassword.setError("Password is to short!");
            }
        }

        return valid;
    }


    private boolean checkAndRequestPermissions() {

        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
}
