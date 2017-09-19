package com.example.android.lad;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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

    //VARIABLES FOR CAMERA
    ImageView imageView;
    Integer REQUEST_CAMERA=1, SELECT_FILE=0;


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

        //----------------------------------------------------------------------------------------
        //FOR CAMERA

        imageView = (ImageView) findViewById(R.id.image_view);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_take_picture);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                SelectImage();
            }
        });
    }

    private void SelectImage(){

        final CharSequence[] items={"Camera","Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(RecordDetailsActivity.this);
        builder.setTitle("Add Image");

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (items[i].equals("Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent, SELECT_FILE);

                } else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();

    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);

        if(resultCode== Activity.RESULT_OK){

            if(requestCode==REQUEST_CAMERA){
                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                imageView.setImageBitmap(bmp);

            }else if(requestCode==SELECT_FILE){

                Uri selectedImageUri = data.getData();
                imageView.setImageURI(selectedImageUri);
            }
        }
    }

    //This will show the toolbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

}
