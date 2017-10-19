package com.example.android.lad;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by user on 16/09/2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        String dayString = ((day > 9) ? "" : "0") + day;
        month++;
        String monthString = ((month > 9) ? "" : "0") + month;
        String yearString = "" + year;
        String dateString = dayString + "/" + monthString + "/" + yearString;
        TextView dateTextView = (TextView) getActivity().findViewById(R.id.date_edit_text);
        dateTextView.setText(dateString);
    }
}
