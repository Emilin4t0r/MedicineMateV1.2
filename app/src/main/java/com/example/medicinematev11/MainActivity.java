package com.example.medicinematev11;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.Attributes;

/**
 * 
 */
public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private ListView medicineList;
    private TextView medicineName;
    private TextView medicineAmount;
    private ArrayList reminders_list;
    private Button addingButton;
    private Button timeButton;
    public SharedPreferences shared;
    private String arrayString;
    private ImageView imageView;

    String newName;
    String newAmount;
    String newTime = "";

    String alarmName;
    String alarmUri;


    int newHour;
    int newMinutes;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    medicineList.setEnabled(true);
                    medicineList.setVisibility(View.VISIBLE);
                    medicineName.setEnabled(false);
                    medicineName.setVisibility(View.INVISIBLE);
                    medicineAmount.setEnabled(false);
                    medicineAmount.setVisibility(View.INVISIBLE);
                    addingButton.setEnabled(false);
                    addingButton.setVisibility(View.INVISIBLE);
                    timeButton.setEnabled(false);
                    timeButton.setVisibility(View.INVISIBLE);
                    imageView.setEnabled(false);
                    imageView.setVisibility(View.INVISIBLE);

                    return true;
                case R.id.navigation_dashboard:
                    medicineList.setEnabled(false);
                    medicineList.setVisibility(View.INVISIBLE);
                    medicineName.setEnabled(true);
                    medicineName.setVisibility(View.VISIBLE);
                    medicineAmount.setEnabled(true);
                    medicineAmount.setVisibility(View.VISIBLE);
                    addingButton.setEnabled(true);
                    addingButton.setVisibility(View.VISIBLE);
                    timeButton.setEnabled(true);
                    timeButton.setVisibility(View.VISIBLE);
                    imageView.setEnabled(false);
                    imageView.setVisibility(View.INVISIBLE);

                    return true;
                case R.id.navigation_notifications:
                    medicineList.setEnabled(false);
                    medicineList.setVisibility(View.INVISIBLE);
                    medicineName.setEnabled(false);
                    medicineName.setVisibility(View.INVISIBLE);
                    medicineAmount.setEnabled(false);
                    medicineAmount.setVisibility(View.INVISIBLE);
                    addingButton.setEnabled(false);
                    addingButton.setVisibility(View.INVISIBLE);
                    timeButton.setEnabled(false);
                    timeButton.setVisibility(View.INVISIBLE);
                    imageView.setEnabled(true);
                    imageView.setVisibility(View.VISIBLE);

                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        shared = getSharedPreferences("App_settings", MODE_PRIVATE);
        reminders_list = new ArrayList<String>();
        medicineList = (ListView) findViewById(R.id.medicineListView);
        medicineName = (TextView) findViewById(R.id.medicineNameView);
        medicineAmount = (TextView) findViewById(R.id.medicineAmountView);
        imageView = (ImageView) findViewById(R.id.imageView);
        addingButton = (Button) findViewById(R.id.addButton);
        timeButton = (Button) findViewById(R.id.timeButton);

        medicineName.setEnabled(false);
        medicineName.setVisibility(View.INVISIBLE);
        medicineAmount.setEnabled(false);
        medicineAmount.setVisibility(View.INVISIBLE);
        addingButton.setEnabled(false);
        addingButton.setVisibility(View.INVISIBLE);
        timeButton.setEnabled(false);
        timeButton.setVisibility(View.INVISIBLE);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        String[] reminders = new String[]{};

        final List<String> reminders_list = new ArrayList<String>(Arrays.asList(reminders));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, reminders_list);

        medicineList.setAdapter(arrayAdapter);


        Set<String> set = shared.getStringSet("DATE_LIST", null);
        if(set != null) {
            reminders_list.addAll(set);


        }
        arrayAdapter.notifyDataSetChanged();


        addingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText nameText = (EditText) findViewById(R.id.medicineNameView);
                EditText amountText = (EditText) findViewById(R.id.medicineAmountView);

                newName = nameText.getText().toString();
                newAmount = amountText.getText().toString();


                ReminderClass reminderClass = new ReminderClass(newName, newAmount, newTime);

                if (newName.isEmpty() || newAmount.isEmpty() || newTime.isEmpty()) {

                }else {
                    arrayString = reminderClass.toString();
                    reminders_list.add(arrayString);
                    arrayAdapter.notifyDataSetChanged();
                    SharedPreferences.Editor editor = shared.edit();
                    Set<String> set = new HashSet<String>();
                    set.addAll(reminders_list);
                    editor.putStringSet("DATE_LIST", set);
                    editor.apply();

                    newAlarm();

                    clearVar();
                }
            }

        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");

            }
        });
        medicineList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                final String idD = reminders_list.get(position);

                AlertDialog.Builder adb=new AlertDialog.Builder(MainActivity.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete the alert?");
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        delAlarm(idD);
                        reminders_list.remove(positionToRemove);
                        arrayAdapter.notifyDataSetChanged();
                        //tallenna dataset
                        SharedPreferences.Editor editor = shared.edit();
                        Set<String> set = new HashSet<String>();
                        set.addAll(reminders_list);
                        editor.putStringSet("DATE_LIST", set);
                        editor.apply();
                        arrayAdapter.notifyDataSetChanged();
                    }});
                adb.show();
            }
        });
    }
    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        newHour = hourOfDay;
        newMinutes = minute;

        String newerMinutes = Integer.toString(newMinutes);
        int length = newerMinutes.length();
        if(length == 1){
            newerMinutes = "0" + newerMinutes;
        }else{}

        newTime = newHour + ":" + newerMinutes;

        timeButton.setText(newTime);
    }

    public void newAlarm() {
        Intent alarm = new Intent(AlarmClock.ACTION_SET_ALARM);

        alarm.putExtra(AlarmClock.EXTRA_HOUR, newHour);
        alarm.putExtra(AlarmClock.EXTRA_MINUTES, newMinutes);

        // näkymä ei enää vaihdu herätyskelloon, kun tätä käytetään
        alarm.putExtra(AlarmClock.EXTRA_SKIP_UI, true);

        //luodaan muuttuja, joka asetetaan hälyytyksen viestiksi
        alarmName = (newName + " | " + newAmount + " | " + newTime);
        alarm.putExtra(AlarmClock.EXTRA_MESSAGE, alarmName);


        startActivity(alarm);
    }

    public void delAlarm(String id) {
        //poistaa hälyytyksen, kun listasta poistetaan muistutus

        String identfier = id;

        Intent alarmDel = new Intent(AlarmClock.ACTION_DISMISS_ALARM);

        //valitaan poistettava hälyytys
        alarmDel.putExtra(AlarmClock.EXTRA_ALARM_SEARCH_MODE, AlarmClock.ALARM_SEARCH_MODE_LABEL);
        alarmDel.putExtra(AlarmClock.EXTRA_MESSAGE, identfier);

        startActivity(alarmDel);
    }

    public void clearVar() {

        EditText nameText = (EditText) findViewById(R.id.medicineNameView);
        EditText amountText = (EditText) findViewById(R.id.medicineAmountView);
        nameText.setText(null);
        amountText.setText(null);
        timeButton.setText(R.string.hint_time);
        newHour = 0;
        newMinutes = 0;
        newTime = "";
        alarmName = "";
    }
}
