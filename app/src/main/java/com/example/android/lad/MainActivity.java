package com.example.android.lad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Use ArrayList to hold record data
        final ArrayList<ExerciseRecord> exerciseRecords = new ArrayList<>();

        // Add test data
        exerciseRecords.add(new ExerciseRecord("", ""));

        // Find Record ListView in the layout
        ListView recordsListView = (ListView) findViewById(R.id.records_list_view);

        // Create adapter for ListView
        final ExerciseRecordAdapter adapter = new ExerciseRecordAdapter(this, exerciseRecords);

        // Set Adapter on ListView so the list item will be automatically created
        recordsListView.setAdapter(adapter);

        // Find Add Record button
        Button addRecordButton = (Button) findViewById(R.id.add_record_button);

        // Set action on the button
        addRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exerciseRecords.add(new ExerciseRecord("",""));
                adapter.notifyDataSetChanged();
            }
        });
    }
}
