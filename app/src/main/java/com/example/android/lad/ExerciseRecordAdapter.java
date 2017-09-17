package com.example.android.lad;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
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
        ExerciseRecord currentExerciseRecord = getItem(position);

        // Find the duration view using id
        TextView durationView = (TextView) listItemView.findViewById(R.id.duration);

        // Set the text on duration view
        durationView.setText(currentExerciseRecord.getmDuration());

        // Find the date view using id
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);

        // Set the text on date view
        dateView.setText(currentExerciseRecord.getmDate());

        // Find the weight view using id
        TextView weightView = (TextView) listItemView.findViewById(R.id.weight);

        // Set the text on weight view
        weightView.setText(currentExerciseRecord.getmWeight());

        // Find the body parts view using id
        TextView bodyPartsView = (TextView) listItemView.findViewById(R.id.body_parts);

        // Set the text on body parts view
        bodyPartsView.setText(currentExerciseRecord.getmBodyParts());


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
