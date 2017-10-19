package com.example.android.lad;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity {

    ImageView beforePhotoView;
    ImageView afterPhotoView;
    TextView totalDaysView;
    TextView totalHoursView;
    TextView weightLossView;
    TextView bodyFatChangeView;

    DatabaseHandler database;
    ArrayList<ExerciseRecord> exerciseRecords;

    int totalDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        database = new DatabaseHandler(getApplicationContext());
        exerciseRecords = database.getAllRecords();

        totalDays = exerciseRecords.size();

        if (totalDays > 1) {
            double totalHours = calculateTotalHours(exerciseRecords);

            ExerciseRecord afterRecord = exerciseRecords.get(0);
            ExerciseRecord beforeRecord = exerciseRecords.get(exerciseRecords.size() - 1);

            beforePhotoView = (ImageView) findViewById(R.id.before_photo_view);
            showPhoto(beforeRecord, beforePhotoView);

            afterPhotoView = (ImageView) findViewById(R.id.after_photo_view);
            showPhoto(afterRecord, afterPhotoView);

            totalDaysView = (TextView) findViewById(R.id.total_days_view);
            totalDaysView.setText("" + totalDays);

            totalHoursView = (TextView) findViewById(R.id.total_hours_view);
            totalHoursView.setText("" + totalHours);

            weightLossView = (TextView) findViewById(R.id.weight_loss_view);
            weightLossView.setText("" + calculateWeightLoss(beforeRecord, afterRecord));

            bodyFatChangeView = (TextView) findViewById(R.id.body_fat_change_view);
            bodyFatChangeView.setText("" + calculateBodyFatChange(beforeRecord, afterRecord) + "%");
        }
    }

    public double calculateTotalHours(ArrayList<ExerciseRecord> records)
    {
        int totalHours = 0;
        for (ExerciseRecord e : exerciseRecords)
        {
            totalHours += Double.parseDouble(e.getmDuration());
        }
        return totalHours;
    }

    public void showPhoto(ExerciseRecord record, ImageView imageView)
    {
        String photoPath = record.getmPhotoPath();

        if ((photoPath != null) && (photoPath != "")) {
            File imgFile = new File(photoPath);

            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                Bitmap temp = rotateBitmap(myBitmap, photoPath);
                if (temp != null) {
                    imageView.setImageBitmap(temp);
                }
            }
        }
    }

    public  Bitmap rotateBitmap(Bitmap bitmap, String photoPath) {
        ExifInterface exifInterface = null;
        try{
            exifInterface = new ExifInterface(photoPath);
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

    public double calculateWeightLoss(ExerciseRecord before, ExerciseRecord after)
    {
        double beforeWeight = Double.parseDouble(before.getmWeight());
        double afterWeight = Double.parseDouble(after.getmWeight());

        return -(afterWeight - beforeWeight);
    }

    public double calculateBodyFatChange(ExerciseRecord before, ExerciseRecord after)
    {
        double beforeBodyFat = Double.parseDouble(before.getmBodyFatPercentage());
        double afterBodyFat = Double.parseDouble(after.getmBodyFatPercentage());

        return afterBodyFat - beforeBodyFat;
    }
}
