package com.example.medicinematev11;

import java.util.ArrayList;
import java.util.List;

public class ReminderSingleton {

    ArrayList<ReminderClass> newReminder;

    private static /*final*/ ReminderSingleton ourInstance = new ReminderSingleton();

    static ReminderSingleton getInstance() {
        return ourInstance;
    }

    private ReminderSingleton() {
        newReminder = new ArrayList<ReminderClass>();

        newReminder.add(new ReminderClass("Testname", "100mg", "12:00"));
        newReminder.add(new ReminderClass("Testname 2", "400mg", "18:00"));
    }

    public List<ReminderClass> getNewReminder(){
        return newReminder;
    }
}
