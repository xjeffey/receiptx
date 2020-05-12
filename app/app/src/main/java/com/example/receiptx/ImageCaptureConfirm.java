package com.example.receiptx;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ImageCaptureConfirm extends AppCompatActivity {
    //edit text
    EditText receiptNameEditText;
    EditText storeNameEditText;
    EditText streetEditText;
    EditText cityEditText;
    EditText zipCodeEditText;
    EditText totalEditText;

    //buttons
    Button confirmButton;
    Button cancelButton;
    Button selectDateButton;
    Button changeImageButton;

    //text view
    TextView dateTextView;

    //dropdown menu
    Spinner categorySpinner;
    Spinner stateSpinner;

    //image of receipt
    ImageView receiptImage;

    //codes to see which action occured
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;

    String cameraPermission[];
    String storagePermission[];

    //image path
    Uri image_uri;

    //choosing the date
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    //previous screen data
    String receiptName;
    String category;
    String storeName;
    String street;
    String city;
    String state;
    String zipCode;
    String date;
    Uri resultUri;

    String userID;
    String catID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_capture_confirm);

        //updating user id
        userID = getIntent().getStringExtra("userID");
//        System.out.println(userID);

        //TESTING DATA PASSING
        receiptName = getIntent().getStringExtra("receiptName");
        category = getIntent().getStringExtra("category");
        storeName = getIntent().getStringExtra("storeName");
        street = getIntent().getStringExtra("street");
        city = getIntent().getStringExtra("city");
        state = getIntent().getStringExtra("state");
        zipCode = getIntent().getStringExtra("zipCode");
        date = getIntent().getStringExtra("date");
        resultUri = Uri.parse(getIntent().getStringExtra("image"));

        convertCategory(category);

        //initializing edit text
        receiptNameEditText = findViewById(R.id.receiptNameEditText);
        storeNameEditText = findViewById(R.id.storeNameEditText);
        streetEditText = findViewById(R.id.streetEditText);
        cityEditText = findViewById(R.id.cityEditText);
        zipCodeEditText = findViewById(R.id.zipCodeEditText);
        totalEditText = findViewById(R.id.totalEditText);

        //setting values to edit text if availiable
        receiptNameEditText.setText(receiptName);
        storeNameEditText.setText(storeName);
        streetEditText.setText(street);
        cityEditText.setText(city);
        zipCodeEditText.setText(zipCode);

        //setting up buttons
        confirmButton = findViewById(R.id.confirmButton);
        cancelButton = findViewById(R.id.cancelButton);
        selectDateButton = findViewById(R.id.dateButton);
        changeImageButton = findViewById(R.id.changeImageButton);

        //the image
        receiptImage = findViewById(R.id.receiptImageIV);
        receiptImage.setImageURI(resultUri);

        //setting up textviews
        dateTextView = findViewById(R.id.dateTextView);
        dateTextView.setText(date);

        //setting up the drop down menu  (spinner) for category choices
        categorySpinner = findViewById(R.id.categorySpinner);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(ImageCaptureConfirm.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.categories));
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        //setting the selected category before
        categorySpinner.setSelection(categoryAdapter.getPosition(category));

        stateSpinner = findViewById(R.id.stateSpinner);
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(ImageCaptureConfirm.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.states));
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);

        //setting the selected category before
        stateSpinner.setSelection(stateAdapter.getPosition(state));
    }


    //error message dialog
    //error dialog for missing fields
    public void onErrorDialog(String message){
        //creating error dialog
        androidx.appcompat.app.AlertDialog.Builder errorBuilder = new androidx.appcompat.app.AlertDialog.Builder(ImageCaptureConfirm.this);
        //updating the message
        errorBuilder.setMessage(message);
        //updating the title
        errorBuilder.setTitle("Missing Field(s)");
        errorBuilder.setIcon(R.drawable.error);
        //creating the dialog
        androidx.appcompat.app.AlertDialog errorDialog = errorBuilder.create();
        //showing the dialog
        errorDialog.show();
    }
    //button clicks

    //clicking on confrim button
    public void onConfirmButtonClick(View view){
        //1 piece of final information
       String total;

       //getting updated information
        receiptName = receiptNameEditText.getText().toString();
        category = categorySpinner.getSelectedItem().toString();
        storeName = storeNameEditText.getText().toString();
        street = streetEditText.getText().toString();
        city = cityEditText.getText().toString();
        state = stateSpinner.getSelectedItem().toString();
        zipCode = zipCodeEditText.getText().toString();
        total = totalEditText.getText().toString();

        //checking for missing fields
        if (receiptName.isEmpty() || category.equalsIgnoreCase("Select a Category") || storeName.isEmpty() ||
        street.isEmpty() || city.isEmpty() || state.equalsIgnoreCase("Select State") || zipCode.isEmpty() || zipCode.length() < 5 ||
        date.isEmpty() || resultUri == null || resultUri.equals(Uri.EMPTY) || total.isEmpty()) {
            //error message
            String errorMessage = "Please enter the following fields:\n";
            if(receiptName.isEmpty()){
                errorMessage += "\t- Receipt Name\n";
            }

            if(category.equalsIgnoreCase("Select a Category")){
                errorMessage += "\t- Category\n";
            }

            //adding certain errors
            if(storeName.isEmpty()){
                errorMessage += "\t- Store Name\n";
            }

            if(street.isEmpty()){
                errorMessage += "\t- Street Address\n";
            }
            if(city.isEmpty()){
                errorMessage += "\t- City\n";
            }
            if(state.equalsIgnoreCase("Select State")){
                errorMessage += "\t- State\n";
            }

            if(zipCode.isEmpty() || zipCode.length() < 5){
                errorMessage += "\t- Zip Code\n";
            }

            if(date.isEmpty()){
                errorMessage += "\t- Date\n";
            }

            if(resultUri == null || resultUri.equals(Uri.EMPTY)){
                errorMessage += "\t- Image\n";
            }

            if(total.isEmpty()){
                errorMessage += "\t- Total Amount\n";
            }

            //showing eror to user
            onErrorDialog(errorMessage);

        }else{
            insertReceipt();
            //ADD DATA TO DATABASE
        }
    }

    //clicking on the cancel button
    public void onCancelButtonClick(View view){
        startActivity(new Intent(ImageCaptureConfirm.this, Home.class));
    }

    //clicking on the date button
    public void onDateButtonClick(View view){
        //getting current date
        String[] dateInfo = dateTextView.getText().toString().split("-");
        int year = Integer.parseInt(dateInfo[0]);
        int month = Integer.parseInt(dateInfo[1]);
        int day = Integer.parseInt(dateInfo[2]);

        //setting up the dialog menu
        DatePickerDialog dialog = new DatePickerDialog(
                ImageCaptureConfirm.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);

        //showing the date picking menu
        dialog.show();

        //looking for the selected date
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                //adds one because starts at 0
                month += 1;

                //updating the text view
                String selectedDate = year + "-" + month + "-" + day;
                dateTextView.setText(selectedDate);

                //update new date
                date = selectedDate;
            }
        };
    }

    //converting category name to id
    public void convertCategory(String catName){
        switch (catName){
            case "Food":
                catID = "2";
                break;
            case "Retail":
                catID = "5";
                break;
            case "Entertainment":
                catID = "1";
                break;
            case "Transportation":
                catID = "6";
                break;
            case "Housing":
                catID = "3";
                break;
            case "Misc":
                catID = "4";
                break;
        }
    }

    //clicking on change image
    public void onChangeImageButtonClick(View view){
        showImageImportDialog();
    }

    //inserting receipt
    public void insertReceipt(){
        String dbConnect = "http://cgi.soic.indiana.edu/~team43/receiptx/insertReceipt.php";

        //getting access to link
        OkHttpClient client = new OkHttpClient();

        //giving the api paraments to work with
        RequestBody formBody = new FormBody.Builder()
                .add("categoryID", catID)
                .add("usersID", userID)
                .add("title", receiptName)
                .add("receiptDate", date)
                .add("street", street)
                .add("city", city)
                .add("st", state)
                .add("zipCode", zipCode)
                .add("total", totalEditText.getText().toString())
                .add("vendor", storeName)
                .add("imagePath", resultUri.toString())
                .build();

        Request request = new Request.Builder()
                .url(dbConnect)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()){
                    throw new IOException("Unexpected code " + response);
                }else {
                    final String responseData = response.body().string();
//                    System.out.println(responseData);

                    //going to next screen
                    ImageCaptureConfirm.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(ImageCaptureConfirm.this, AfterImageCaptureConfirm.class));
                        }
                    });

                    //checking if we got data back
                    if (responseData.equalsIgnoreCase("0 results")){
                        //setting home screen to nothing
                    }else{

                    }
                }
            }
        });
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
                receiptImage.setImageURI(resultUri);

                //get drawable bitmap for text recognition
                BitmapDrawable bitmapDrawable = (BitmapDrawable)receiptImage.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();

                if(!recognizer.isOperational()){
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }
                else{
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = recognizer.detect(frame);
                    StringBuilder sb = new StringBuilder();

                    String blocks = "";
                    String lines = "";
                    String words = "";

                    //looping over the block of text
                    for(int i = 0; i < items.size(); i++){
                        //looking a specific block
                        TextBlock tBlock = items.valueAt(i);
                        blocks += tBlock.getValue() +"\n\n";

                        //looking at each line
                        for(Text line : tBlock.getComponents()){
                            lines += line.getValue() + "\n";

                            //looking at each word
                            for(Text word : line.getComponents()){
                                words += word + ", ";
                            }
                        }
                    }

//                    System.out.println(lines);
                }
            }
            else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){ {
                Exception error = result.getError();
                Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
            }
            }
        }
    }


}
