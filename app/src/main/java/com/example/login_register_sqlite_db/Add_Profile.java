package com.example.login_register_sqlite_db;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

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
                imagePickDialog();
            }
        });
    }

    private void imagePickDialog() {
        String [] options={"Camera","Gallery"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Pick Image From ");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    }else {
                        pickFromCamera();
                    }
                }else if(which==1){
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }else {
                        pickFromGallery();
                    }
                }
            }
        });
        builder.create().show();
    }

    private void pickFromCamera() {
        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Image Title");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Image Description");
        imageUri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,IMAGE_PICK_CAMERA_CODE);

    }
    private void pickFromGallery() {
        Intent GalleryIntent=new Intent(Intent.ACTION_PICK);
        GalleryIntent.setType("image/*");
        startActivityForResult(GalleryIntent,IMAGE_PICK_GALLERY_CODE);
    }
    private boolean checkStoragePermission(){
        //set storage allow permission from mobile device
     boolean result=ContextCompat.checkSelfPermission(this,Manifest
             .permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
     return result;
    }
    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_REQUEST_CODE);
    }
    //set camera allow permission from mobile device
    private boolean checkCameraPermission(){
        boolean result=ContextCompat.checkSelfPermission(this,Manifest
                .permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean result1=ContextCompat.checkSelfPermission(this,Manifest
                .permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }
    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this,cameraPermission,STORAGE_REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if(grantResults.length>0){
                    boolean cameraAccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted=grantResults[1]==PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted){
                        pickFromCamera();
                    }else {
                        Toast.makeText(this, "Camera and Gallery permission are required ", Toast.LENGTH_SHORT).show();
                    }
                }


            }break;
            case STORAGE_REQUEST_CODE:{
                if (grantResults.length>0){
                    boolean storageAccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted){
                        pickFromGallery();
                    }else {
                        Toast.makeText(this, "Storage Permission Required", Toast.LENGTH_SHORT).show();
                    }
                }

            }break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode==RESULT_OK){
            if (requestCode==IMAGE_PICK_GALLERY_CODE){

            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}