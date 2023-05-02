package com.anas.firebasepdffile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.Manifest;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class UploadActivity extends AppCompatActivity {


    Button btnUpload,btnBrowse;
    ImageButton btnPdf_logo,btnPdf_cross;
    EditText ePdf_name;

    Uri uri;
    DatabaseReference db_ref;
    StorageReference storage_ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btnUpload=findViewById(R.id.btnUpload);
        btnPdf_logo=findViewById(R.id.btnPdf_logo);
        btnPdf_cross=findViewById(R.id.btnPdf_cross);
        ePdf_name=findViewById(R.id.ePdf_name);
        btnBrowse=findViewById(R.id.btnBrowse);

        btnPdf_logo.setVisibility(View.GONE);
        btnPdf_cross.setVisibility(View.GONE);
        btnBrowse.setVisibility(View.VISIBLE);


        db_ref= FirebaseDatabase.getInstance().getReference().child("Pdfs");
        storage_ref= FirebaseStorage.getInstance().getReference();



        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UploadActivity.this, "Select a Pdf", Toast.LENGTH_SHORT).show();
                Dexter.withActivity(UploadActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                                i.setType("application/pdf");
                                startActivityForResult(i,100);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                //on reopen of app , again asks for permission
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });
        btnUpload.setOnClickListener(v -> {

            uploading();
        });

        btnPdf_cross.setOnClickListener(v -> {
            btnPdf_cross.setVisibility(View.INVISIBLE);
            btnPdf_logo.setVisibility(View.INVISIBLE);
            btnBrowse.setVisibility(View.VISIBLE);
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){
            if (requestCode==100){

                uri=data.getData();
                btnPdf_cross.setVisibility(View.VISIBLE);
                btnPdf_logo.setVisibility(View.VISIBLE);
                btnBrowse.setVisibility(View.GONE);
            }
        }
        else {
            Toast.makeText(this, "failed to provide result", Toast.LENGTH_SHORT).show();
        }
    }


    private void uploading() {

        ProgressDialog dialog = new ProgressDialog(UploadActivity.this);
        dialog.setTitle("Pdf Uploader");
        dialog.show();

        StorageReference pdf_location = storage_ref.child("Pdfs/"+"anas"+System.currentTimeMillis()+".pdf");
        pdf_location.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> {

                    pdf_location.getDownloadUrl().addOnSuccessListener(uri -> {

                        dialog.dismiss();

                        Model_Pdf model_obj= new Model_Pdf(ePdf_name.getText().toString(),uri.toString(),0,0,0);
                        db_ref.child(db_ref.push().getKey()).setValue(model_obj);

                        btnPdf_cross.setVisibility(View.INVISIBLE);
                        btnPdf_logo.setVisibility(View.INVISIBLE);
                        btnBrowse.setVisibility(View.VISIBLE);
                        ePdf_name.setText("");

                        Toast.makeText(this, "pdf uploaded", Toast.LENGTH_SHORT).show();
                    });
                })
                .addOnProgressListener(snapshot -> {
                    float percent = (100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    dialog.setMessage((int)percent+"% Uploaded");
                });
    }
}