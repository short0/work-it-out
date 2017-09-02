package com.example.android.lad;

/**
 * Created by user on 29/08/2017.
 */

// An ExerciseRecord has information about an exercise activity
// m stands for "member"
public class ExerciseRecord {

    String mDate; // Date of exercise
    String mWeight; // Current weight on that date

    /**
     * Initialize the a new object
     *
     * @param mDate
     * @param mWeight
     */
    public ExerciseRecord(String mDate, String mWeight) {
        this.mDate = mDate;
        this.mWeight = mWeight;

    }

    /**
     * Get date
     *
     * @return
     */
    public String getmDate() {
        return mDate;
    }

    /**
     * Get weight
     *
     * @return
     */
    public String getmWeight() {
        return mWeight;
    }

    /**
     * Modify date
     *
     * @param mDate
     */
    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    /**
     * Modify weight
     *
     * @param mWeight
     */
    public void setmWeight(String mWeight) { this.mWeight = mWeight; }
}
