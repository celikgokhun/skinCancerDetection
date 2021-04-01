package com.celik.gokhun.skincancerdetection;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;

import org.tensorflow.lite.Interpreter;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class UploadActivity extends AppCompatActivity {

    Uri takenUri;
    Uri pickedUri;

    ImageView postImage;

    Button takeButton;
    Button resultButton;

    Interpreter tflite;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        postImage = findViewById(R.id.postImageView);

        takeButton = findViewById(R.id.take_btn);
        resultButton = findViewById(R.id.result_btn);

    }

    public void jumpToResult (View view) {

        startActivity(new Intent(UploadActivity.this, ResultActivity.class));
    }

    public void selectImage (View view) {

        CropImage.activity().start(UploadActivity.this);


        /*
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,2);
        }

         */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            try
            {
                pickedUri = data.getData();

                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                if (resultCode == RESULT_OK) {
                    takenUri =  result.getUri();
                    postImage.setImageURI(takenUri);

                    takeButton.setVisibility(View.GONE);
                    resultButton.setVisibility(View.VISIBLE);


                }

                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception e = result.getError();
                    System.out.println(e.getMessage());

                }
            }

            catch (NullPointerException npe) {npe.printStackTrace();}

        }

    }

}