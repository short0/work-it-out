package com.example.android.lad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BodyFatCalculatorActivity extends AppCompatActivity {

    private Button saveBtn;
    private EditText height = null;
    private EditText neck = null;
    private EditText waist = null;
    private EditText hip = null;
    private TextView tvBodyFat;
    private double bodyFat;
    private CheckBox male;
    private CheckBox female;


    ExerciseRecord record;
    DatabaseHandler database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_fat_calculator);

        database = new DatabaseHandler(getApplicationContext());

        record = (ExerciseRecord) getIntent().getSerializableExtra("record");

        saveBtn = (Button) findViewById(R.id.saveBtn);
        if (record == null)
        {
            saveBtn.setText("Calculate");
        }

        height = (EditText) findViewById(R.id.etHeight);
        neck = (EditText) findViewById(R.id.etNeck);
        waist = (EditText) findViewById(R.id.etWaist);
        hip = (EditText) findViewById(R.id.etHip);
        tvBodyFat = (TextView) findViewById(R.id.tvBodyFat);
        male = (CheckBox) findViewById(R.id.checkMale);
        female = (CheckBox) findViewById(R.id.checkFemale);

        // this allows only one gender checkbox to be selected
        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                {
                    female.setChecked(false);
                }
            }
        });
        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    male.setChecked(false);
                }
            }
        });

        // when the save button is pressed the body fat is calculated
        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (height.getText().toString().length() > 0 && neck.getText().toString().length() > 0 && waist.getText().toString().length() > 0
                        && hip.getText().toString().length() >0)
                {
                    if (male.isChecked())
                    {
                        bodyFat = calcBodyFatMale(Double.parseDouble(waist.getText().toString()), Double.parseDouble(neck.getText().toString()), Double.parseDouble(height.getText().toString()));
                        bodyFat = (double) Math.round(bodyFat * 10d) / 10d;
                        tvBodyFat.setText(Double.toString(bodyFat) + "%");

                        if (record != null) {
                            record.setmBodyFatPercentage("" + bodyFat);
                            database.updateRecord(record);
                            Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();
                            Intent detailsIntent = new Intent(BodyFatCalculatorActivity.this, RecordDetailsActivity.class);

                            detailsIntent.putExtra("record", record);

                            startActivity(detailsIntent);
                        }

                    } else if (female.isChecked())
                    {
                        bodyFat = calcBodyFatFemale(Double.parseDouble(waist.getText().toString()), Double.parseDouble(hip.getText().toString())
                                , Double.parseDouble(neck.getText().toString()), Double.parseDouble(height.getText().toString()));
                        bodyFat = (double) Math.round(bodyFat * 10d) / 10d;
                        tvBodyFat.setText(Double.toString(bodyFat) + "%");

                        if (record != null) {
                            record.setmBodyFatPercentage("" + bodyFat);
                            database.updateRecord(record);
                            Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();
                            Intent detailsIntent = new Intent(BodyFatCalculatorActivity.this, RecordDetailsActivity.class);

                            detailsIntent.putExtra("record", record);

                            startActivity(detailsIntent);
                        }

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), String.valueOf("Please selet a gender"), Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please enter all fields",Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
    //method to calculate males body fat percentage
    public double calcBodyFatMale(double waist, double neck, double height)
    {
        double result;
        result = 495/(1.0324-0.19077 * (Math.log10(waist-neck)) + 0.15456 *(Math.log10(height)))- 450;
        return result;
    }
    //method to calculate females body fat percentage
    public double calcBodyFatFemale(double waist, double hip, double neck, double height)
    {
        double result;
        result = 495/(1.29579-0.35004 * (Math.log10(waist+hip-neck)) + 0.22100 * (Math.log10(height))) -450;
        return result;
    }
    //method to get body fat percentagae
    public double getBodyFat()
    {
        return bodyFat;
    }

}
