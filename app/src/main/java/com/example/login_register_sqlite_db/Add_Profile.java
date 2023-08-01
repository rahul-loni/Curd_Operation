package com.example.login_register_sqlite_db;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.login_register_sqlite_db.databinding.ActivityAddProfileBinding;
import com.google.android.material.internal.ManufacturerUtils;

public class Add_Profile extends AppCompatActivity {

    ActivityAddProfileBinding binding;

    public static final int CAMERA_REQUEST_CODE=100;
    public static final int STORAGE_REQUEST_CODE=101;

    public static final int IMAGE_PICK_CAMERA_CODE=102;
    public static final int IMAGE_PICK_GALLERY_CODE=103;

    private String [] cameraPermission ;
    private String [] storagePermission ;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cameraPermission=new String[]{Manifest.permission.CAMERA
                ,Manifest.permission.WRITE_EXTERNAL_STORAGE,};
        storagePermission=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}