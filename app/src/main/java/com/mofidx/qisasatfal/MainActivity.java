package com.mofidx.qisasatfal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;
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

import java.io.InputStream;


public class MainActivity extends AppCompatActivity {
    String rollt, qissanamet;
    Uri filepath,filepdfpath;
    Bitmap bitmap;
    ImageView img;
SharedPreferences sharedPreferences = null;
SharedPreferences.Editor editor;
String qissassnum,dounloadURL,dounloadPDFURL;
Button savebtn,btn_upload,btn_upload_pdf,nextbtn;
TextView txtsonqissanum;
    EditText roll,qissaname,imglink,pdflink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            sharedPreferences = getSharedPreferences("mModidxQisass",0);

            qissassnum = sharedPreferences.getString("qissasnum","0");


        roll = findViewById(R.id.roll);
        qissaname = findViewById(R.id.qissaname);
        imglink = findViewById(R.id.imglink);
        pdflink = findViewById(R.id.pdflink);
        savebtn = findViewById(R.id.savebtn);
        nextbtn = findViewById(R.id.nextbtn);
        btn_upload = findViewById(R.id.btn_upload);
        btn_upload_pdf = findViewById(R.id.btn_upload_pdf);
        img = findViewById(R.id.image_view);

            txtsonqissanum = findViewById(R.id.txtsonqissanum);

            txtsonqissanum.setText(qissassnum);

            nextbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this,MainActivity2.class);
                    startActivity(i);
                }
            });

            savebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                    rollt = roll.getText().toString().trim();
                    qissanamet = qissaname.getText().toString().trim();
//                    String imglinkt = imglink.getText().toString().trim();
//                    String pdflinkt = pdflink.getText().toString().trim();
                                                                        //||img==null
                    if (rollt.equals("")||qissanamet.equals("")||filepath==null||filepdfpath==null){
                        Toast.makeText(MainActivity.this, "املئ المكان الفارغ!!", Toast.LENGTH_SHORT).show();
                    }else {


                    //اخر رقم قصه
                    editor = sharedPreferences.edit();
                    editor.putString("qissasnum",rollt);
                    editor.commit();


                        uploadPdftoFirebase(rollt);









                    }
                }
            });

//


        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(MainActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"اختر صورة"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();

            }
        });


//
        btn_upload_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Dexter.withActivity(MainActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent intent=new Intent();
                                intent.setType("application/pdf");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent,"اختر ملف pdf"),101);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();


            }
        });







        // OnCreate
    }

    private void uploadPdftoFirebase(String roolttt) {

        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("PDF Uploader");
        dialog.show();

        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference uploader=storage.getReference("Pdfs/").child(roolttt+".pdf");

        uploader.putFile(filepdfpath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)

                    {



                        Task<Uri> dounloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {


                                dounloadPDFURL= task.getResult().toString();
                                uploadtofirebase(rollt ,qissanamet , dounloadPDFURL);


                            }
                        });


                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "تم بنجاح اضافة PDF رقم "+roolttt, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        float percent=(100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                        dialog.setMessage("Uploaded :"+(int)percent+" %");
                    }
                });



    }

    private void uploadtofirebase(String rooltt,String qissanamet , String pdfURL) {

        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("File Uploader");
        dialog.show();


        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference uploader=storage.getReference("images/").child(rooltt);
        uploader.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)

                    {



                        Task<Uri> dounloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {


                                dounloadURL= task.getResult().toString();

                                dataholder obj = new dataholder(qissanamet,dounloadURL,pdfURL);


                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                DatabaseReference nood = firebaseDatabase.getReference("Qisass");


                                nood.child(rooltt).setValue(obj);
                                roll.setText("");
                                qissaname.setText("");
                                filepath=null;
                                filepdfpath=null;
//                                filepdfpath=null;
//                        imglink.setText("");
//                        pdflink.setText("");

                                Toast.makeText(MainActivity.this, "تم بنجاح اضافة القصة رقم "+rooltt, Toast.LENGTH_SHORT).show();

                                img.setImageBitmap(null);

                            }
                        });


                        dialog.dismiss();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        float percent=(100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                        dialog.setMessage("Uploaded :"+(int)percent+" %");
                    }
                });



    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode==1 && resultCode==RESULT_OK)
        {
            filepath=data.getData();
            try
            {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
            }catch (Exception ex)
            {

            }
        }


        if (requestCode==101 && resultCode==RESULT_OK){

            filepdfpath = data.getData();


        }






        super.onActivityResult(requestCode, resultCode, data);
    }









}