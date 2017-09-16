package com.example.android.lad;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by user on 29/08/2017.
 */

public class ExerciseRecordAdapter extends ArrayAdapter<ExerciseRecord> {
    private List<ExerciseRecord> mRecords;
    private int mPosition;
    /**
     * Construct a new ArrayAdapter
     * @param context
     * @param exerciseRecords
     */
    public ExerciseRecordAdapter(Context context, List<ExerciseRecord> exerciseRecords)
    {
        super(context, 0, exerciseRecords);
        mRecords = exerciseRecords;
    }

    /**
     * Display a record at specific position in the list
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        // Check if there is a recycled view availabe to reuse
        View listItemView = convertView;
        mPosition = position;
        if (listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.record_list_item, parent, false);
        }

        // Find the record at current position
        final ExerciseRecord currentExerciseRecord = getItem(position);

        // Find the date view using id
        final EditText dateView = (EditText) listItemView.findViewById(R.id.date);

        // Set the text on date view
        dateView.setText(currentExerciseRecord.getmDate());

        // Check if text has been changed and save the new value to current object
        dateView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() != 0) {
                    String dateText = dateView.getText().toString();
                    Log.d("ExerciseRecordAdapter", dateText);
                    mRecords.get(mPosition).setmDate(dateText);
                }
            }
        });

        // Find the weight view using id
        final EditText weightView = (EditText) listItemView.findViewById(R.id.weight);

        // Set the text on weight view
        weightView.setText(currentExerciseRecord.getmWeight());

        // Check if text has been changed and save the new value to current object
        weightView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String weightText = weightView.getText().toString();
                Log.d("ExerciseRecordAdapter", weightText);
                mRecords.get(mPosition).setmWeight(weightText);
            }
        });

        // Find the camera_link using id
        ImageView cameraLink = (ImageView) listItemView.findViewById(R.id.camera_link);

        // Set action on cameraLink click
        cameraLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Going to your image", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), CameraActivity.class);
                getContext().startActivity(intent);
            }
        });

        // Find the checkbox_link using id
        ImageView checkboxLink = (ImageView) listItemView.findViewById(R.id.checkbox_link);

        // Set action on checkboxLink click
        checkboxLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Going to workout list", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), WorkoutActivity.class);
                getContext().startActivity(intent);
            }
        });

        // Find the timepicker_link using id
        ImageView timepickerLink = (ImageView) listItemView.findViewById(R.id.timepicker_link);

        // Set action on timepicker_Link click
        timepickerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Going duration selection", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), DurationActivity.class);
                getContext().startActivity(intent);
            }
        });

        // Find the delete_record using id
        ImageView deleteRecord = (ImageView) listItemView.findViewById(R.id.delete_record);

        // Set action on delete_record click
        deleteRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Deleting record", Toast.LENGTH_SHORT).show();

                // Remove record at current position
                mRecords.remove(position);
                // refresh the ListView
                notifyDataSetChanged();
            }
        });

        return listItemView;
    }
}
