package com.example.medicinematev11;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemClickListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.Attributes;

/**
 * 
 */
public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private TextView mTextMessage;
    private ListView medicineList;
    private TextView medicineName;
    private TextView medicineAmount;

    private Button addingButton;
    private Button timeButton;


    String newName;
    String newAmount;
    String newTime = "";

    String alarmName;

    int newHour;
    int newMinutes;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);

                    medicineList.setEnabled(true);                  //sets the list usable
                    medicineList.setVisibility(View.VISIBLE);       //sets the list visible

                    medicineName.setEnabled(false);
                    medicineName.setVisibility(View.INVISIBLE);
                    medicineAmount.setEnabled(false);
                    medicineAmount.setVisibility(View.INVISIBLE);

                    addingButton.setEnabled(false);
                    addingButton.setVisibility(View.INVISIBLE);

                    timeButton.setEnabled(false);
                    timeButton.setVisibility(View.INVISIBLE);

                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);

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

                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);

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

                    return true;
            }
            return false;
        }
    };
    public static final String NAME_MESSAGE = "Name placeholder";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        medicineList = (ListView) findViewById(R.id.medicineListView);
        medicineName = (TextView) findViewById(R.id.medicineNameView);
        medicineAmount = (TextView) findViewById(R.id.medicineAmountView);

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

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        medicineList.setAdapter(new ArrayAdapter<ReminderClass>(this, android.R.layout.simple_list_item_1, ReminderSingleton.getInstance().getNewReminder()));

        //uutta shittii
        String[] reminders = new String[]{};

        final List<String> reminders_list = new ArrayList<String>(Arrays.asList(reminders));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, reminders_list);

        medicineList.setAdapter(arrayAdapter);

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
                    reminders_list.add(reminderClass.toString());

                    arrayAdapter.notifyDataSetChanged();

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
                AlertDialog.Builder adb=new AlertDialog.Builder(MainActivity.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + position);
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        reminders_list.remove(positionToRemove);
                        arrayAdapter.notifyDataSetChanged();
                    }});
                adb.show();
            }
        });
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        newHour = hourOfDay;
        newMinutes = minute;

        newTime = newHour + ":" + newMinutes;

        timeButton.setText(newTime);

    }

    public void newAlarm() {
        Intent alarm = new Intent(AlarmClock.ACTION_SET_ALARM);



        alarm.putExtra(AlarmClock.EXTRA_HOUR, newHour);
        alarm.putExtra(AlarmClock.EXTRA_MINUTES, newMinutes);

        // näkymä ei enää vaihdu herätyskelloon, kun tätä käytetään
        alarm.putExtra(AlarmClock.EXTRA_SKIP_UI, true);

        //luodaan muuttuja, joka asetetaan hälyytyksen viestiksi
        alarmName = newName + "   |   " + newAmount;
        alarm.putExtra(AlarmClock.EXTRA_MESSAGE, alarmName);

        startActivity(alarm);

    }

    public void delAlarm() {
        //poistaa hälyytyksen, kun listasta poistetaan muistutus

        //puuttuu vielä :/
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
