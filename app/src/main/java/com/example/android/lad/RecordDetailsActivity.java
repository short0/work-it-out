package com.example.android.lad;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RecordDetailsActivity extends AppCompatActivity {

    TextView dateEditText;
    EditText weightEditText;
    EditText durationEditText;
    FloatingActionButton fabSave;

    CheckBox shoulders;
    CheckBox biceps;
    CheckBox triceps;
    CheckBox legs;
    CheckBox chest;
    CheckBox back;
    CheckBox cardio;

    DatabaseHandler database;
    ExerciseRecord record;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_details);

        database = new DatabaseHandler(getApplicationContext());

        record = (ExerciseRecord) getIntent().getSerializableExtra("record");

        dateEditText = (TextView) findViewById(R.id.date_edit_text);
        dateEditText.setText(record.getmDate());
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        weightEditText = (EditText) findViewById(R.id.weight_edit_text);
        weightEditText.setText(record.getmWeight());

        durationEditText = (EditText) findViewById(R.id.duration_edit_text);
        durationEditText.setText(record.getmDuration());

        shoulders = (CheckBox) findViewById(R.id.shoulders_checkbox);
        biceps = (CheckBox) findViewById(R.id.biceps_checkbox);
        triceps = (CheckBox) findViewById(R.id.triceps_checkbox);
        legs = (CheckBox) findViewById(R.id.legs_checkbox);
        chest = (CheckBox) findViewById(R.id.chest_checkbox);
        back = (CheckBox) findViewById(R.id.back_checkbox);
        cardio = (CheckBox) findViewById(R.id.cardio_checkbox);

        String bodyPartsString = record.getmBodyParts();
        String[] tokens = bodyPartsString.split(",");
        for (String t : tokens)
        {
            String temp = t.trim().toLowerCase();
            if (temp.equals("shoulders")) shoulders.setChecked(true);
            if (temp.equals("biceps")) biceps.setChecked(true);
            if (temp.equals("triceps")) triceps.setChecked(true);
            if (temp.equals("legs")) legs.setChecked(true);
            if (temp.equals("chest")) chest.setChecked(true);
            if (temp.equals("back")) back.setChecked(true);
            if (temp.equals("cardio")) cardio.setChecked(true);

        }


        fabSave = (FloatingActionButton) findViewById(R.id.fab_save);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Saving", Toast.LENGTH_SHORT).show();

                String bodyParts = "";
                if (shoulders.isChecked()) bodyParts += ", Shoulder";
                if (biceps.isChecked()) bodyParts += ", Biceps";
                if (triceps.isChecked()) bodyParts += ", Triceps";
                if (legs.isChecked()) bodyParts += ", Legs";
                if (chest.isChecked()) bodyParts += ", Chest";
                if (back.isChecked()) bodyParts += ", Back";
                if (cardio.isChecked()) bodyParts += ", Cardio";

                String formattedBodyParts = "";
                if (bodyParts.length() > 2) {
                    formattedBodyParts = bodyParts.substring(2);
                }

                //Toast.makeText(getApplicationContext(), formattedBodyParts, Toast.LENGTH_SHORT).show();

                int id = record.getID();
                record.setmDate(dateEditText.getText().toString());

                String checkNullWeight = weightEditText.getText().toString();
                if (checkNullWeight.isEmpty()) {
                    record.setmWeight("0.0");
                } else {
                    record.setmWeight(weightEditText.getText().toString());
                }

                String checkNullDuration = durationEditText.getText().toString();
                if (checkNullDuration.isEmpty()){
                    record.setmDuration("0.0");
                } else {
                    record.setmDuration(durationEditText.getText().toString());
                }

                if (formattedBodyParts.length() > 2) {
                    record.setmBodyParts(formattedBodyParts);
                }
                else
                {
                    record.setmBodyParts("");
                }
                database.updateRecord(record);
            }
        });
    }
}
