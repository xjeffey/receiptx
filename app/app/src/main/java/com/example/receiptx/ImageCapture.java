package com.example.receiptx;


import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

/**
 * Activity for the Ocr Detecting app.  This app detects text and displays the value with the
 * rear facing camera. During detection overlay graphics are drawn to indicate the position,
 * size, and contents of each TextBlock.
 */
public final class ImageCapture extends AppCompatActivity {

    Button addImageButton;
    Button backButton;
    Button nextButton;
    Button cancelButton;

    //image holder from camera
    ImageView mPreviewIv;

    //codes to see which action occured
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;

    String cameraPermission[];
    String storagePermission[];

    //image path
    Uri image_uri;

    //previous screen data
    String receiptName;
    String category;
    String storeName;
    String street;
    String city;
    String state;
    String zipCode;
    String date;

    //uri to pass
    Uri resultUri;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_capture);

        //updating user id
        userID = getIntent().getStringExtra("userID");
        System.out.println(userID);

        //INITIAL SET UP
        backButton = findViewById(R.id.backButton);
        nextButton = findViewById(R.id.nextButton);
        cancelButton = findViewById(R.id.cancelButton);
        addImageButton = findViewById(R.id.addImageButton);

        //setting up image holder
        mPreviewIv = findViewById(R.id.receiptImageIV);

        //camera permission
        cameraPermission = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //TESTING DATA PASSING
        receiptName = getIntent().getStringExtra("receiptName");
        category = getIntent().getStringExtra("category");
        storeName = getIntent().getStringExtra("storeName");
        street = getIntent().getStringExtra("street");
        city = getIntent().getStringExtra("city");
        state = getIntent().getStringExtra("state");
        zipCode = getIntent().getStringExtra("zipCode");
        date = getIntent().getStringExtra("date");

        System.out.println("Data Received:");
        System.out.println(receiptName);
        System.out.println(category);
        System.out.println(storeName);
        System.out.println(street);
        System.out.println(city);
        System.out.println(state);
        System.out.println(zipCode);
        System.out.println(date);
    }

    //BUTTON CLICKS
    public void onAddImageButtonClick(View view){
        showImageImportDialog();
    }

    //clicking on cancel button
    public void onCancelButtonClick(View view){
        startActivity(new Intent(ImageCapture.this, Home.class));
    }

    //clicking on next button
    public void onNextButtonClick(View view){
        //checking if image has been added
        if(resultUri != null && !resultUri.equals(Uri.EMPTY)){
            //SEND INFORMATION OVER TO CONFIRMATION
            Intent intent = new Intent(ImageCapture.this, ImageCaptureConfirm.class);
            intent.putExtra("receiptName", receiptName);
            intent.putExtra("category", category);
            intent.putExtra("storeName", storeName);
            intent.putExtra("street", street);
            intent.putExtra("city", city);
            intent.putExtra("state", state);
            intent.putExtra("zipCode", zipCode);
            intent.putExtra("date", date);
            intent.putExtra("image", resultUri.toString());
            intent.putExtra("userID", userID);
            startActivity(intent);
        }else{
            onErrorDialog();
        }
    }

    //error dialog for missing fields
    public void onErrorDialog(){
        //creating error dialog
        androidx.appcompat.app.AlertDialog.Builder errorBuilder = new androidx.appcompat.app.AlertDialog.Builder(ImageCapture.this);
        //updating the message
        errorBuilder.setMessage("Please add an image!");
        //updating the title
        errorBuilder.setTitle("Missing Field(s)");
        errorBuilder.setIcon(R.drawable.error);
        //creating the dialog
        androidx.appcompat.app.AlertDialog errorDialog = errorBuilder.create();
        //showing the dialog
        errorDialog.show();
    }

    //CAMERA AND GALLERY PERMISSIONS
    private void showImageImportDialog() {
        //items to display in dialog
        String[] items = {"Camera", "Gallery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        //set title
        dialog.setTitle("Select Image");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    //camera option clicked
                    if(!checkedCameraPermission()){
                        //camera permission not allowed, request it
                        requestCameraPermission();
                    }
                    else{
                        //permission allowed, take picture
                        pickCamera();
                    }
                }
                if(which == 1){
                    //gallery option clicked
                    if(!checkedStoragePermission()){
                        //camera permission not allowed, request it
                        requestStoragePermission();
                    }
                    else{
                        //permission allowed
                        pickGallery();
                    }
                }
            }
        });
        dialog.create().show(); //showing dialog
    }

    //choosing gallery option
    private void pickGallery() {
        //INTENT TO PICK IMAGE FROM GALLERY
        Intent intent = new Intent(Intent.ACTION_PICK);
        //set intent type to image
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    //choosing camera option
    private void pickCamera() {
        //INTENT TO TAKE IMAGE WITH CAMERA, SAVE TO STORAGE FOR HIGH QUALITY
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "NewPic"); //title of picture
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image To text"); //description
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    //accessing the gallery
    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE);
    }

    //looking to see if there is enough memory
    private boolean checkedStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        
        return result;
    }

    //accessing the camera
    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE);
    }

    //camera options
    private boolean checkedCameraPermission(){
        //checking for camera permission
        //saves image to device for better quality
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }

    //HANDLE PERMISSIONS
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case CAMERA_REQUEST_CODE:
                if(grantResults.length > 0){
                    boolean cameraAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted && writeStorageAccepted){
                        pickCamera();
                    }
                    else{
                        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case STORAGE_REQUEST_CODE:
                if(grantResults.length > 0){
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;

                    if(writeStorageAccepted){
                        pickGallery();
                    }
                    else{
                        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
             default:
                 Toast.makeText(this, "Please press allow next time", Toast.LENGTH_SHORT).show();
        }
    }

    //handle image result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //got image from camera
        if(resultCode == RESULT_OK){
            if(requestCode == IMAGE_PICK_CAMERA_CODE){
                //got image from camera, crop it
                CropImage.activity(image_uri)
                        .setGuidelines(CropImageView.Guidelines.ON) //enable image guidlines
                        .start(this);
            }
            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                //Got image from gallery, crop it
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON) //enable image guidlines
                .start(this);
            }
        }

        //get cropped image
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == RESULT_OK){
                resultUri = result.getUri(); //get image uri
                //set image to image view
                mPreviewIv.setImageURI(resultUri);
            }
            else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){ {
                Exception error = result.getError();
                Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

