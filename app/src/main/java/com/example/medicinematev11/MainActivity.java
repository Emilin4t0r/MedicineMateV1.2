package com.example.medicinematev11;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.Attributes;

/**
 * 
 */
public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ListView medicineList;
    private TextView medicineName;
    private TextView medicineAmount;
    private TextView medicineTime;
    private Button addingButton;

    String newName;
    String newAmount;
    String newTime;

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
                    medicineTime.setEnabled(false);
                    medicineTime.setVisibility(View.INVISIBLE);
                    addingButton.setEnabled(false);
                    addingButton.setVisibility(View.INVISIBLE);

                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);

                    medicineList.setEnabled(false);
                    medicineList.setVisibility(View.INVISIBLE);

                    medicineName.setEnabled(true);
                    medicineName.setVisibility(View.VISIBLE);
                    medicineAmount.setEnabled(true);
                    medicineAmount.setVisibility(View.VISIBLE);
                    medicineTime.setEnabled(true);
                    medicineTime.setVisibility(View.VISIBLE);
                    addingButton.setEnabled(true);
                    addingButton.setVisibility(View.VISIBLE);

                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);

                    medicineList.setEnabled(false);
                    medicineList.setVisibility(View.INVISIBLE);

                    medicineName.setEnabled(false);
                    medicineName.setVisibility(View.INVISIBLE);
                    medicineAmount.setEnabled(false);
                    medicineAmount.setVisibility(View.INVISIBLE);
                    medicineTime.setEnabled(false);
                    medicineTime.setVisibility(View.INVISIBLE);
                    addingButton.setEnabled(false);
                    addingButton.setVisibility(View.INVISIBLE);

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
        medicineTime = (TextView) findViewById(R.id.medicineTimeView);
        addingButton = (Button) findViewById(R.id.addButton);

        medicineName.setEnabled(false);
        medicineName.setVisibility(View.INVISIBLE);
        medicineAmount.setEnabled(false);
        medicineAmount.setVisibility(View.INVISIBLE);
        medicineTime.setEnabled(false);
        medicineTime.setVisibility(View.INVISIBLE);
        addingButton.setEnabled(false);
        addingButton.setVisibility(View.INVISIBLE);

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
                EditText timeText = (EditText) findViewById(R.id.medicineTimeView);
                newName = nameText.getText().toString();
                newAmount = amountText.getText().toString();
                newTime = timeText.getText().toString();

                ReminderClass reminderClass = new ReminderClass(newName, newAmount, newTime);

                if (newName.isEmpty() || newAmount.isEmpty() || newTime.isEmpty()) {

                }else {
                    reminders_list.add(reminderClass.toString());

                    arrayAdapter.notifyDataSetChanged();

                    nameText.setText(null);
                    amountText.setText(null);
                    timeText.setText(null);
                }
            }
        });
    }
}
