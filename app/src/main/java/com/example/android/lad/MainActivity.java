package com.example.android.lad;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    FloatingActionButton fab;
    Date currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);

        // Use ArrayList to hold record data
        final ArrayList<ExerciseRecord> exerciseRecords = new ArrayList<>();

        // Add test data
        //exerciseRecords.add(new ExerciseRecord("", ""));

        // Find Record ListView in the layout
        ListView recordsListView = (ListView) findViewById(R.id.records_list_view);

        // Create adapter for ListView
        final ExerciseRecordAdapter adapter = new ExerciseRecordAdapter(this, exerciseRecords);

        // Set Adapter on ListView so the list item will be automatically created
        recordsListView.setAdapter(adapter);

        recordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "Details of record", Toast.LENGTH_SHORT).show();
                Intent intent_calendar = new Intent(MainActivity.this, RecordDetailsActivity.class);
                startActivity(intent_calendar);
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentDate = new Date();
                SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy");
                String dateToString = dateFormater.format(currentDate);
                Toast.makeText(getApplicationContext(), "Adding record", Toast.LENGTH_SHORT).show();
                exerciseRecords.add(new ExerciseRecord());
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_calendar:
                // User chose the "Calendar" item, show the app settings UI...
                Toast.makeText(this, "Calendar", Toast.LENGTH_SHORT).show();
                Intent intent_calendar = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent_calendar);
                return true;

            case R.id.action_graph:
                // User chose the "Graph" action, mark the current item
                Toast.makeText(this, "Graph", Toast.LENGTH_SHORT).show();
                Intent intent_graph = new Intent(MainActivity.this, GraphActivity.class);
                startActivity(intent_graph);
                return true;

            case R.id.action_body_fat_calculator:
                // User chose the "Body Fat Calculator" item, show the app settings UI...
                Toast.makeText(this, "Body Fat Calculator", Toast.LENGTH_SHORT).show();
                Intent intent_body_fat_calculator = new Intent(MainActivity.this, BodyFatCalculatorActivity.class);
                startActivity(intent_body_fat_calculator);
                return true;

            case R.id.action_statistics:
                // User chose the "Statistics" action, mark the current item
                Toast.makeText(this, "Statistics", Toast.LENGTH_SHORT).show();
                Intent intent_statistics = new Intent(MainActivity.this, StatisticsActivity.class);
                startActivity(intent_statistics);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
