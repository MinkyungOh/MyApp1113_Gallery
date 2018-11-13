package com.example.edu.myapp1113_gallery;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CaptureActivity extends AppCompatActivity implements View.OnClickListener {

    final int IMAGE_CAPTURE = 102;
    final int IMAGE_GALLERY = 103;
    final String IMAGE_FILE_NAME = "sampleimage.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        ((Button)findViewById(R.id.captureButton)).setOnClickListener(this);
        ((Button)findViewById(R.id.galleryButton)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.captureButton:
                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, IMAGE_CAPTURE);
                }
                break;
            case R.id.galleryButton:
                try {
                    FileInputStream fileInputStream = openFileInput(IMAGE_FILE_NAME);
                    byte[] buffer = new byte[fileInputStream.available()];
                    fileInputStream.read(buffer);

                    Bitmap bitmap = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
                    ((ImageView)findViewById(R.id.fileImageView)).setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==IMAGE_CAPTURE && data!=null) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap)bundle.get("data");
            ((ImageView)findViewById(R.id.captureImageView)).setImageBitmap(bitmap);

            try {
                FileOutputStream fileOutputStream = openFileOutput(IMAGE_FILE_NAME, Context.MODE_PRIVATE);
                fileOutputStream.write(bitmapToByteArray(bitmap));
                fileOutputStream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
