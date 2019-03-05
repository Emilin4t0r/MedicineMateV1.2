package com.example.medicinematev11;

public class ReminderClass {

    String name;
    String amount;
    String time;



    public ReminderClass (String name, String amount, String time){
        this.name = name;
        this.amount = amount;
        this.time = time;
    }



    @Override
    public String toString(){
        String output = (name + " " + amount + " " + time);
        return output;
    }
}
