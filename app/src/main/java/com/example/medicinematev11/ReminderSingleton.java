package com.example.medicinematev11;

import android.content.Intent;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ReminderSingleton {

    ArrayList<ReminderClass> newReminder;

    private static /*final*/ ReminderSingleton ourInstance = new ReminderSingleton();

    /**
     * @return
     */
    static ReminderSingleton getInstance() {
        return ourInstance;
    }


    /**
     *
     */
    private ReminderSingleton(){

        newReminder = new ArrayList<ReminderClass>();

        newReminder.add(new ReminderClass("Testname", "400mg", "18:00"));
        newReminder.add(new ReminderClass("Testname2", "30mg", "12:00"));
    }

    /**
     * @return
     */
    public List<ReminderClass> getNewReminder() {
        return newReminder;
    }
}
