package com.example.medicinematev11;

import java.util.ArrayList;

/**
 *
 */
public class ReminderClass {

    ArrayList<ReminderClass> newReminder;

    String name;
    String amount;
    String time;

    /**
     * @param name
     * @param amount
     * @param time
     */
    public ReminderClass (String name, String amount, String time){
        this.name = name;
        this.amount = amount;
        this.time = time;
    }
    /**
     * @return
     */
    @Override
    public String toString(){
        String output = (name + " | " + amount + " | " + time);
        return output;
    }
}
