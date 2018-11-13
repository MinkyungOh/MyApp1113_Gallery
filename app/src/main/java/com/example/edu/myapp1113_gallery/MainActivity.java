package com.example.edu.myapp1113_gallery;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final int PERMISSION_REQUEST_CODE = 10;
    final int IMAGE_FROM_GALLERY = 20;
    final int IMAGE_CAPTURE = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requirespermission();

        ((Button)findViewById(R.id.button1)).setOnClickListener(this);
        ((Button)findViewById(R.id.button2)).setOnClickListener(this);

    }

    void requirespermission() {
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length>0 &&
                        grantResults[0]==PackageManager.PERMISSION_GRANTED ) {
                    //System.out.println("******** Permission has been granted by user");
                }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.button1:
                intent = new Intent(v.getContext(), FromGalleryActivity.class);
                startActivityForResult(intent, IMAGE_FROM_GALLERY);
                break;
            case R.id.button2:
                intent = new Intent(v.getContext(), CaptureActivity.class);
                startActivityForResult(intent, IMAGE_CAPTURE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
