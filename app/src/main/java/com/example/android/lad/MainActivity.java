package com.example.android.lad;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    ListView recordsListView;
    ArrayList<ExerciseRecord> records;
    ArrayList<ExerciseRecord> exerciseRecords;
    DatabaseHandler database;
    ExerciseRecordAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Allowing Strict mode policy for Nougat support
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

/**
 * In the first run, uncomment the next line to delete the old database
 * Then comment it again so that the database will not be deleted in subsequent runs
 */
//        getApplicationContext().deleteDatabase("exercisesManager");
        // Get Database
        database = new DatabaseHandler(getApplicationContext());

        // Use ArrayList to hold record data
        exerciseRecords = new ArrayList<ExerciseRecord>();

        // Load Database
        loadDatabase();

        // Delete all records
//        deleteAllRecords();


        // Set toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);

        // Add test data
        //exerciseRecords.add(new ExerciseRecord("", ""));

        // Find Record ListView in the layout
        recordsListView = (ListView) findViewById(R.id.records_list_view);

        // Create adapter for ListView
        adapter = new ExerciseRecordAdapter(this, exerciseRecords);

        //adapter.setNotifyOnChange(true);

        // Set Adapter on ListView so the list item will be automatically created
        recordsListView.setAdapter(adapter);

        recordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Record Details", Toast.LENGTH_SHORT).show();

                ExerciseRecord r = exerciseRecords.get(position);

                Intent detailsIntent = new Intent(MainActivity.this, RecordDetailsActivity.class);

                detailsIntent.putExtra("record", r);

                startActivity(detailsIntent);
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab_add);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            fab.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "New Record Added", Toast.LENGTH_SHORT).show();
//                exerciseRecords.add(new ExerciseRecord("0.0h", "dd/mm/yyyy", "0.0kg", ""));
//                Log.d("Insert: ", "" + exerciseRecords.size());

//                DatabaseHandler database = new DatabaseHandler(getApplicationContext());
                // Inserting record
                Date currentDate = new Date();
                SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy");
                String dateToString = dateFormater.format(currentDate);
                Log.d("Insert: ", "Inserting ..");
                database.addRecord(new ExerciseRecord("0.0", dateToString, "0.0", "Body parts", "0.0", ""));
                Log.d("Insert: ", "" + exerciseRecords.size());
                loadDatabase();
                Log.d("Insert: ", "" + exerciseRecords.size());

                //exerciseRecords.add(new ExerciseRecord(100, "a", "b", "c", "d"));
                adapter.notifyDataSetChanged();

                Log.d("Insert: ", "Notify adapter ...");
//                // Reading all record
//                Log.d("Reading: ", "Reading all records ...");
//                exerciseRecords = database.getAllRecords();
//                for (ExerciseRecord r : records) {
//                    String log = "Id: "+ r.getID()
//                            + " ,Duration: " + r.getmDuration()
//                            + " ,Date: " + r.getmDate()
//                            + " ,Weight: " + r.getmWeight();
//                    // Writing Contacts to log
//                    Log.d("Name: ", log);
//                }
//
//                Log.d("Deleting: ", "Deleting all records ...");
//                for (ExerciseRecord r : exerciseRecords){
//                    database.deleteRecord(r);
//                }

//                DBHandler db = new DBHandler(getApplicationContext());
//                // Inserting students
//                Log.d("Insert: ", "Inserting ..");
//                db.addStudent(new Student("Mat", "43540"));
//
//                // Reading all students
//                Log.d("Reading: ", "Reading all students..");
//                List<Student> students = db.getAllStudents();
//                for (Student cn : students) {
//                    String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: "
//                            + cn.getPhone();
//                    // Writing Contacts to log
//                    Log.d("Name: ", log);
//                }
//
//                Log.d("Deleting: ", "Deleting all students..");
//                for (Student cn : students){
//                    db.deleteStudent(cn);
//                }
//
//                getApplicationContext().deleteDatabase("studentsManager");
//                String[] dbs = getApplicationContext().databaseList();
//                try
//                {
//                    dbs[0].isEmpty();
//                }
//                catch (Exception e){
//                    Log.d("list db: ", "No db");
//                }

            }
        });
    }

    //Automatic request permission for camera
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                fab.setEnabled(true);
            }
        }
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

            case R.id.action_delete_all:

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(this);
                }
                builder.setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete all the records?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                deleteAllRecords();

                                Intent back_to_main = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(back_to_main);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void loadDatabase()
    {
        Log.d("Reading: ", "Reading all records ...");
        exerciseRecords.clear();
        records = database.getAllRecords();
        exerciseRecords.addAll(records);
        for (ExerciseRecord r : exerciseRecords) {
            String log = "Id: "+ r.getID()
                    + ", Duration: " + r.getmDuration()
                    + ", Date: " + r.getmDate()
                    + ", Weight: " + r.getmDate()
                    + ", Body parts: " + r.getmBodyParts()
                    + ", Body fat: " + r.getmBodyFatPercentage()
                    + ", Photo path: " + r.getmPhotoPath();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }
    }

    public void deleteAllRecords()
    {
        Log.d("Deleting: ", "Deleting all records ...");
            for (ExerciseRecord r : exerciseRecords){
                database.deleteRecord(r);
            }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDatabase();
        adapter.notifyDataSetChanged();
    }

}
