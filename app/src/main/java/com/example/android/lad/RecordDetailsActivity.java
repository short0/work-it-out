package com.example.android.lad;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RecordDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    double bodyFatPercentage;
    int id = -1;

    TextView dateEditText;
    EditText weightEditText;
    EditText durationEditText;
    TextView bodyFatPercentageEditText;
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

    private FloatingActionButton fab;
    private Uri file;

    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_details);

        database = new DatabaseHandler(getApplicationContext());

        record = (ExerciseRecord) getIntent().getSerializableExtra("record");

        id = record.getID();

        dateEditText = (TextView) findViewById(R.id.date_edit_text);
        dateEditText.setText(record.getmDate());
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        bodyFatPercentageEditText = (TextView) findViewById(R.id.body_fat_percentage_edit_text);
        bodyFatPercentageEditText.setText(record.getmBodyFatPercentage());
        bodyFatPercentageEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Body Fat Calculator", Toast.LENGTH_SHORT).show();
                Intent bodyFatPercentageIntent = new Intent(RecordDetailsActivity.this, BodyFatCalculatorActivity.class);
                bodyFatPercentageIntent.putExtra("record", record);
                startActivity(bodyFatPercentageIntent);
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

        ImageView searchShoulders = (ImageView) findViewById(R.id.search_shoulders);
        searchShoulders.setOnClickListener(this);
        ImageView searchBiceps = (ImageView) findViewById(R.id.search_biceps);
        searchBiceps.setOnClickListener(this);
        ImageView searchTriceps = (ImageView) findViewById(R.id.search_triceps);
        searchTriceps.setOnClickListener(this);
        ImageView searchLegs = (ImageView) findViewById(R.id.search_legs);
        searchLegs.setOnClickListener(this);
        ImageView searchChest = (ImageView) findViewById(R.id.search_chest);
        searchChest.setOnClickListener(this);
        ImageView searchBack = (ImageView) findViewById(R.id.search_back);
        searchBack.setOnClickListener(this);
        ImageView searchCardio = (ImageView) findViewById(R.id.search_cardio);
        searchCardio.setOnClickListener(this);

        fabSave = (FloatingActionButton) findViewById(R.id.fab_save);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Record Saved", Toast.LENGTH_SHORT).show();

                String bodyParts = "";
                if (shoulders.isChecked()) bodyParts += ", Shoulders";
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

                String checkNullBodyFatPercentage = bodyFatPercentageEditText.getText().toString();
                if (checkNullBodyFatPercentage.isEmpty()){
                    record.setmBodyFatPercentage("0.0");
                } else {
                    record.setmBodyFatPercentage(bodyFatPercentageEditText.getText().toString());
                }

                Log.d("Photopath", "" + mCurrentPhotoPath);
                record.setmPhotoPath(mCurrentPhotoPath);
                Log.d("Photopath", "" + mCurrentPhotoPath);

                database.updateRecord(record);
                Intent return_home = new Intent(RecordDetailsActivity.this, MainActivity.class);
                startActivity(return_home);
            }
        });

        //----------------------------------------------------------------------------------------
        //FOR CAMERA

        imageView = (ImageView) findViewById(R.id.image_view);

        if ((record.getmPhotoPath() != null) && (record.getmPhotoPath() != "")) {
            File imgFile = new File(record.getmPhotoPath());

            if (imgFile.exists()) {
                mCurrentPhotoPath = record.getmPhotoPath();
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                Bitmap temp = rotateBitmap(myBitmap);
                if (temp != null) {
                    imageView.setImageBitmap(temp);
                }
            }
        }
        fab = (FloatingActionButton) findViewById(R.id.fab_take_picture);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                selectImage();
            }
        });
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Work It Out");

        if(!storageDir.exists()){
            storageDir.mkdir();
        }

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.d("mylog", "Path: " + mCurrentPhotoPath);
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
            Log.d("mylog", "Exception while creating file: " + ex.toString());
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Log.d("mylog", "Photofile not null");
            Uri photoURI;
            if(Build.VERSION_CODES.N <= Build.VERSION.SDK_INT) {
                photoURI = FileProvider.getUriForFile(RecordDetailsActivity.this,
                        "com.example.provider.FileProvider",
                        photoFile);
            }else {
                photoURI = Uri.fromFile(photoFile);
            }
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_CAMERA);
        }
    }

    public void selectImage()
    {

        final CharSequence[] options = {"Use Camera", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(RecordDetailsActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Use Camera")) {
                    dispatchTakePictureIntent();
                    galleryAddPic();

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        Bitmap rotatedBitmap = rotateBitmap(bitmap);
        imageView.setImageBitmap(rotatedBitmap);

    }

@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK) {
        if (requestCode == 1) {
            setPic();
            Toast.makeText(getApplicationContext(), "Photo Saved ", Toast.LENGTH_LONG).show();

        } else if (requestCode == 2) {
            File imageFile;
            try {
                Uri selectedImage = data.getData();
                mCurrentPhotoPath = getRealPathFromURI(selectedImage);

                Bitmap bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);


                saveImage(bmp);
                    /*Bitmap bit = BitmapFactory.decodeStream(imageStream);*/
                Bitmap rotatedBitmapGallery = rotateBitmap(bmp);
                imageView.setImageBitmap(rotatedBitmapGallery);
                Toast.makeText(getApplicationContext(), "Photo Saved! ", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(RecordDetailsActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }
}

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId())
        {
            case R.id.search_shoulders:
                intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, "Shoulders exercises");
                startActivity(intent);
                break;
            case R.id.search_biceps:
                intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, "Biceps exercises");
                startActivity(intent);
                break;
            case R.id.search_triceps:
                intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, "Triceps exercises");
                startActivity(intent);
                break;
            case R.id.search_legs:
                intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, "Legs exercises");
                startActivity(intent);
                break;
            case R.id.search_chest:
                intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, "Chest exercises");
                startActivity(intent);
                break;
            case R.id.search_back:
                intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, "Back exercises");
                startActivity(intent);
                break;
            case R.id.search_cardio:
                intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, "Cardio exercises");
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (id != -1) {
            record = database.getRecord(id);
            if (Double.parseDouble(record.getmBodyFatPercentage()) > 0)
            {
                bodyFatPercentageEditText.setText(record.getmBodyFatPercentage());
            }
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + "/Work It Out");

        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public  Bitmap rotateBitmap(Bitmap bitmap) {
        ExifInterface exifInterface = null;
        try{
            exifInterface = new ExifInterface(mCurrentPhotoPath);
        }catch (IOException e){
            e.printStackTrace();
        }
        int orientation1 = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

        Matrix matrix = new Matrix();
        switch (orientation1) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }
}
