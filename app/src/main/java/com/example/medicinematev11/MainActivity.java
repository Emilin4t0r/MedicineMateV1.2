package com.example.medicinematev11;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ListView medicineList;
    private TextView medicineName;
    private TextView medicineAmount;
    private TextView medicineTime;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);

                    medicineList.setEnabled(true);                  //sets the list so that it can be used
                    medicineList.setVisibility(View.VISIBLE);       //sets the list visible

                    medicineName.setEnabled(false);
                    medicineName.setVisibility(View.INVISIBLE);
                    medicineAmount.setEnabled(false);
                    medicineAmount.setVisibility(View.INVISIBLE);
                    medicineTime.setEnabled(false);
                    medicineTime.setVisibility(View.INVISIBLE);

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

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medicineList = (ListView) findViewById(R.id.medicineListView);

        medicineName = (TextView) findViewById(R.id.medicineNameView);
        medicineAmount = (TextView) findViewById(R.id.medicineAmountView);
        medicineTime = (TextView) findViewById(R.id.medicineTimeView);
        medicineName.setEnabled(false);
        medicineName.setVisibility(View.INVISIBLE);
        medicineAmount.setEnabled(false);
        medicineAmount.setVisibility(View.INVISIBLE);
        medicineTime.setEnabled(false);
        medicineTime.setVisibility(View.INVISIBLE);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        medicineList.setAdapter(new ArrayAdapter<ReminderClass>(this, android.R.layout.simple_list_item_1, ReminderSingleton.getInstance().getNewReminder())
        );
    }

}
