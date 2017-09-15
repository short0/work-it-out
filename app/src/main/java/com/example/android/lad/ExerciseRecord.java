package com.example.android.lad;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 29/08/2017.
 */

// An ExerciseRecord has information about an exercise activity
// m stands for "member"
public class ExerciseRecord {

    String mDuration;
    String mDate; // Date of exercise
    String mWeight; // Current weight on that date
    String mBodyParts;


    /**
     * Initialize the a new object
     *
     * @param mDate
     * @param mWeight
     */
    public ExerciseRecord(String mDuration, String mDate, String mWeight, String mBodyParts) {
        this.mDuration = mDuration;
        this.mDate = mDate;
        this.mWeight = mWeight;
        this.mBodyParts = mBodyParts;
    }

    public ExerciseRecord()
    {
        this(   "0.0h",
                new SimpleDateFormat().format(new Date()),
                "0.0kg",
                "Body parts");
//        Date currentDate = new Date();
//        SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy");
//        String dateToString = dateFormater.format(currentDate);


    }

    public String getmDuration() { return mDuration; }
    public String getmDate() {
        return mDate;
    }
    public String getmWeight() {
        return mWeight;
    }
    public String getmBodyParts() { return mBodyParts; }

    public void setmDuration(String mDuration) { this.mDuration = mDuration; }
    public void setmDate(String mDate) {
        this.mDate = mDate;
    }
    public void setmWeight(String mWeight) { this.mWeight = mWeight; }
    public void setmBodyParts(String mBodyParts) { this.mBodyParts = mBodyParts;}
}
