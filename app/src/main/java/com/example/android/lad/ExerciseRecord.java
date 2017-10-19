package com.example.android.lad;

import java.io.Serializable;

/**
 * Created by user on 29/08/2017.
 */

// An ExerciseRecord has information about an exercise activity
// m stands for "member"
public class ExerciseRecord implements Serializable
{

    int id;
    String mDuration;
    String mDate; // Date of exercise
    String mWeight; // Current weight on that date
    String mBodyParts;
    String mBodyFatPercentage;
    String mPhotoPath;


    public ExerciseRecord(int id, String mDuration, String mDate, String mWeight, String mBodyParts, String mBodyFatPercentage, String mPhotoPath) {
        this.id = id;
        this.mDuration = mDuration;
        this.mDate = mDate;
        this.mWeight = mWeight;
        this.mBodyParts = mBodyParts;
        this.mBodyFatPercentage = mBodyFatPercentage;
        this.mPhotoPath = mPhotoPath;
    }

    public ExerciseRecord(String mDuration, String mDate, String mWeight, String mBodyParts, String mBodyFatPercentage, String mPhotoPath) {
        this.mDuration = mDuration;
        this.mDate = mDate;
        this.mWeight = mWeight;
        this.mBodyParts = mBodyParts;
        this.mBodyFatPercentage = mBodyFatPercentage;
        this.mPhotoPath = mPhotoPath;
    }

    public ExerciseRecord(){}
//    public ExerciseRecord()
//    {
//        this(   "0.0h",
//                "dd/mm/yyyy",
//                "0.0kg",
//                "Body parts");
////        Date currentDate = new Date();
////        SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy");
////        String dateToString = dateFormater.format(currentDate);
//
//
//    }

    public int getID() { return id; }
    public String getmDuration() { return mDuration; }
    public String getmDate() {
        return mDate;
    }
    public String getmWeight() {
        return mWeight;
    }
    public String getmBodyParts() { return mBodyParts; }
    public String getmBodyFatPercentage() { return mBodyFatPercentage; }
    public String getmPhotoPath(){ return mPhotoPath; }

    public void setID(int id) { this.id = id; }
    public void setmDuration(String mDuration) { this.mDuration = mDuration; }
    public void setmDate(String mDate) {
        this.mDate = mDate;
    }
    public void setmWeight(String mWeight) { this.mWeight = mWeight; }
    public void setmBodyParts(String mBodyParts) { this.mBodyParts = mBodyParts;}
    public void setmBodyFatPercentage(String mBodyFatPercentage) { this.mBodyFatPercentage = mBodyFatPercentage; }
    public void setmPhotoPath(String mPhotoPath) { this.mPhotoPath = mPhotoPath; }

}
