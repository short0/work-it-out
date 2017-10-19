package com.example.android.lad;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
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
        durationView.setText("" + formatDuration(currentExerciseRecord.getmDuration()) + "h");

        GradientDrawable durationCircle = (GradientDrawable) durationView.getBackground();
        int durationColour = getDurationColor(formatDuration(currentExerciseRecord.getmDuration()));
        durationCircle.setColor(durationColour);

        // Find the date view using id
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);

        // Set the text on date view
        dateView.setText(currentExerciseRecord.getmDate());

        // Find the weight view using id
        TextView weightView = (TextView) listItemView.findViewById(R.id.weight);

        // Set the text on weight view
        weightView.setText("" + formatWeight(currentExerciseRecord.getmWeight()) + "kg");

        // Find the body parts view using id
        TextView bodyPartsView = (TextView) listItemView.findViewById(R.id.body_parts);

        // Set the text on body parts view
//         bodyPartsView.setText("aaaaaaa");
        bodyPartsView.setText(currentExerciseRecord.getmBodyParts());

        TextView bodyFatPercentageView = (TextView) listItemView.findViewById(R.id.body_fat_percentage);

        // Set the text on weight view
        bodyFatPercentageView.setText("" + formatBodyFatPercentage(currentExerciseRecord.getmBodyFatPercentage()) + "%");

        // Find the delete_record using id
        ImageView deleteRecord = (ImageView) listItemView.findViewById(R.id.delete_record);

        // Set action on delete_record click
        deleteRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler database = new DatabaseHandler(getContext());
                database.deleteRecord(mRecords.get(position));

                Toast.makeText(getContext(), "Deleting record", Toast.LENGTH_SHORT).show();

                // Remove record at current position
                mRecords.remove(position);
                // refresh the ListView
                notifyDataSetChanged();
            }
        });

        return listItemView;
    }

    private String formatDuration(String duration) {
        double temp = Double.parseDouble(duration);
        DecimalFormat durationFormat = new DecimalFormat("0.0");
        return durationFormat.format(temp);
    }

    private String formatWeight(String weight) {
        double temp = Double.parseDouble(weight);
        DecimalFormat weightFormat = new DecimalFormat("0.0");
        return weightFormat.format(temp);
    }

    private String formatBodyFatPercentage(String bodyFatPercentage) {
        double temp = Double.parseDouble(bodyFatPercentage);
        DecimalFormat bodyFatPercentageFormat = new DecimalFormat("0.00");
        return bodyFatPercentageFormat.format(temp);
    }

    private int getDurationColor(String duration) {
        double temp = Double.parseDouble(duration);
        int durationColorResourceId;
        int durationFloor = (int) Math.floor(temp);
        switch (durationFloor) {
            case 0:
            case 1:
                durationColorResourceId = R.color.duration1;
                break;
            case 2:
                durationColorResourceId = R.color.duration2;
                break;
            case 3:
                durationColorResourceId = R.color.duration3;
                break;
            case 4:
                durationColorResourceId = R.color.duration4;
                break;
            case 5:
                durationColorResourceId = R.color.duration5;
                break;
            case 6:
                durationColorResourceId = R.color.duration6;
                break;
            case 7:
                durationColorResourceId = R.color.duration7;
                break;
            case 8:
                durationColorResourceId = R.color.duration8;
                break;
            case 9:
                durationColorResourceId = R.color.duration9;
                break;
            default:
                durationColorResourceId = R.color.duration10plus;
                break;
        }

        return ContextCompat.getColor(getContext(), durationColorResourceId);
    }
}
