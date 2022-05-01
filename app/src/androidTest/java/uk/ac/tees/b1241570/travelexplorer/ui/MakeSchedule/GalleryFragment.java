package uk.ac.tees.b1241570.travelexplorer.ui.MakeSchedule;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import studio.knowhere.travelappg.Activity.CoffeShopActivity;
import studio.knowhere.travelappg.Activity.MealsActivity;
import studio.knowhere.travelappg.Activity.ShoppingMallActivity;
import studio.knowhere.travelappg.Activity.TemplesActivity;
import studio.knowhere.travelappg.Activity.TreckingPlaceActivity;
import studio.knowhere.travelappg.BreakfastActivity;
import studio.knowhere.travelappg.R;
import studio.knowhere.travelappg.ui.Class.ActivtyDay;

public class GalleryFragment extends Fragment implements View.OnClickListener {

    private studio.knowhere.travelappg.ui.MakeSchedule.GalleryViewModel galleryViewModel;
    Button Submit;
    EditText mDate, mFromTime, mTotime;
    CheckBox mBreakFastCheck, mShoppingCheck, mTreckingheck, mTemplecheck, mCoffecheck, mMealsCheck;
    ActivtyDay activtyDay;
    ArrayList<String> mList = new ArrayList<>();
    DatePickerDialog EnterDatePickerDialog;
    TimePickerDialog mTimePicker;
    SimpleDateFormat dateFormatter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(studio.knowhere.travelappg.ui.MakeSchedule.GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        mBreakFastCheck = (CheckBox) root.findViewById(R.id.breakfast_id);
        mShoppingCheck = (CheckBox) root.findViewById(R.id.shoppingmall_id);
        mTreckingheck = (CheckBox) root.findViewById(R.id.trecking_id);
        mTemplecheck = (CheckBox) root.findViewById(R.id.temples_id);
        mCoffecheck = (CheckBox) root.findViewById(R.id.coffeshop_id);
        mMealsCheck = (CheckBox) root.findViewById(R.id.meals_id);
        Submit = (Button) root.findViewById(R.id.submit_id);
        mDate = (EditText) root.findViewById(R.id.current_date);
        mFromTime = (EditText) root.findViewById(R.id.from_time_id);
        mTotime = (EditText) root.findViewById(R.id.to_time_id);


        activtyDay = new ActivtyDay();
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        mDate.setText(date);
        // setDateTimeField();
        setTimeField();
        setTimeToField();

        mBreakFastCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    mList.add("BREAKFAST");
                } else {
                    mList.remove("BREAKFAST");
                }
            }
        });
        mShoppingCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    mList.add("SHOPPING");
                } else {
                    mList.remove("SHOPPING");
                }
            }
        });

        mTreckingheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    mList.add("TRECKING");
                } else {
                    mList.remove("TRECKING");
                }
            }
        });
        mTemplecheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    mList.add("TEMPLE");
                } else {
                    mList.remove("TEMPLE");
                }
            }
        });
        mCoffecheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    mList.add("COFFE");
                } else {
                    mList.remove("COFFE");
                }
            }
        });

        mMealsCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    mList.add("MEAL");
                } else {
                    mList.remove("MEAL");
                }
            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v("TAG", "ARRAY VALES ARE" + mList);

                if (mList.size() == 0) {
                    Toast.makeText(getContext(), "Please select any one", Toast.LENGTH_SHORT).show();
                } else {

                    Log.v("TAG", "INDEX ZERO" + mList.get(0));
                    if (mList.get(0).equalsIgnoreCase("BREAKFAST")) {
                        mList.remove("BREAKFAST");
                        activtyDay.getInstance().setList(mList);
                        Intent intent = new Intent(getContext(), BreakfastActivity.class);
                        startActivity(intent);
                    } else if (mList.get(0).equalsIgnoreCase("SHOPPING")) {
                        mList.remove("SHOPPING");
                        activtyDay.getInstance().setList(mList);
                        Intent intent = new Intent(getContext(), ShoppingMallActivity.class);
                        startActivity(intent);
                    } else if (mList.get(0).equalsIgnoreCase("TRECKING")) {
                        mList.remove("TRECKING");
                        activtyDay.getInstance().setList(mList);
                        Intent intent = new Intent(getContext(), TreckingPlaceActivity.class);
                        startActivity(intent);
                    } else if (mList.get(0).equalsIgnoreCase("TEMPLE")) {
                        mList.remove("TEMPLE");
                        activtyDay.getInstance().setList(mList);
                        Intent intent = new Intent(getContext(), TemplesActivity.class);
                        startActivity(intent);
                    } else if (mList.get(0).equalsIgnoreCase("COFFE")) {
                        mList.remove("COFFE");
                        activtyDay.getInstance().setList(mList);
                        Intent intent = new Intent(getContext(), CoffeShopActivity.class);
                        startActivity(intent);
                    } else if (mList.get(0).equalsIgnoreCase("MEAL")) {
                        mList.remove("MEAL");
                        activtyDay.getInstance().setList(mList);
                        Intent intent = new Intent(getContext(), MealsActivity.class);
                        startActivity(intent);
                    }
                }

            }
        });
        return root;
    }

    private void setDateTimeField() {
        //  mdate = (EditText) findViewById(R.id.appoint_date_ed_id);
        mDate.setInputType(InputType.TYPE_NULL);
        mDate.requestFocus();
        mDate.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        EnterDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                Log.v("newCalendar", "" + newDate);
                newDate.set(year, monthOfYear, dayOfMonth);

                // mDate.setText(dateFormatter.format(newDate.getTime()));
                Log.v("newCalendar", "" + mDate);

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        EnterDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);


    }

    //for time picker
    private void setTimeField() {
        mFromTime.setOnClickListener(this);

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                mFromTime.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select From Time");
        mTimePicker.show();

    }

    private void setTimeToField() {
        mTotime.setOnClickListener(this);

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                mTotime.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select To Time");
        mTimePicker.show();

    }


    @Override
    public void onClick(View view) {
        if (view == mDate) {
            EnterDatePickerDialog.show();
        }
    }
}