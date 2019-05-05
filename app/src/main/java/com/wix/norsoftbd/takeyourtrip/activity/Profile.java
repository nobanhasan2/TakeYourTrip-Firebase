package com.wix.norsoftbd.takeyourtrip.activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;
import com.wix.norsoftbd.takeyourtrip.R;
import com.wix.norsoftbd.takeyourtrip.common.common;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    MaterialEditText EtName,Etpassword;
    TextView Etmail;
    String Storage_Path = "New/";
    private TextView profile_name;
    private final int SELECT_PHOTO = 1;
    private CircleImageView ProfileimageView;
    private ProgressDialog progressDialog;
    private Button btn_update,change;
    private Uri imageUri;
    private Bitmap bitmap;
    private StorageReference storageReference;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("Users");

        change=findViewById(R.id.button2);
        EtName=findViewById(R.id.update_name);
        Etmail=findViewById(R.id.update_email);
        Etpassword=findViewById(R.id.update_password);
        profile_name=findViewById(R.id.proile_name);

        storageReference = FirebaseStorage.getInstance().getReference();

        ProfileimageView=(CircleImageView) findViewById(R.id.profile_image);
        btn_update=findViewById(R.id.update_profile_btn);
        ProfileimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creating intent.
               showImagePicker();

            }
        });
      change.setOnClickListener(new View.OnClickListener() {
         @Override
     public void onClick(View view) {
         UploadImageFileToFirebaseStorage(bitmap);
     }
     });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              updateInfo();
            }
        });
        databaseReference.child(common.currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                profile_name.setText((dataSnapshot.child("userName").getValue()).toString());
                EtName.setText((dataSnapshot.child("name").getValue()).toString());
                Etmail.setText((dataSnapshot.child("email").getValue()).toString());
                Etpassword.setText((dataSnapshot.child("passWord").getValue()).toString());
                if((dataSnapshot.child("imageUrl").exists())) {
                    Picasso.with(getApplicationContext()).load((dataSnapshot.child("imageUrl").getValue()).toString()).into(ProfileimageView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void showImagePicker() {
        Intent intent = new Intent();

        // Setting intent type as image to select image from phone storage.
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);
    }


    private void updateInfo() {
        databaseReference.child(common.currentUser).child("name").setValue(EtName.getText().toString());
        databaseReference.child(common.currentUser).child("email").setValue(Etmail.getText().toString());
        databaseReference.child(common.currentUser).child("passWord").setValue(Etpassword.getText().toString());
        Toast.makeText(Profile.this, "saved Changes", Toast.LENGTH_LONG).show();
        Intent i =new Intent(this,Home.class);
        startActivity(i);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK) {
            imageUri=data.getData();
            try {

                  bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);


                  ProfileimageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String GetFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));

    }
    int Image_Request_Code = 1;

    private void UploadImageFileToFirebaseStorage(Bitmap bitmap) {

        // Checking whether FilePathUri Is empty or not.
        if(imageUri!=null)
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 15, baos);
            byte[] data = baos.toByteArray();


            progressDialog =new ProgressDialog(this);
            progressDialog.setTitle("Image Uploading......");
            progressDialog.show();
            FirebaseStorage storage=FirebaseStorage.getInstance();
            StorageReference reference=storage.getReferenceFromUrl("gs://takeyourtrip-be302.appspot.com");
            StorageReference imagesRef=reference.child("New/"+System.currentTimeMillis()+GetFileExtension(imageUri));
            UploadTask uploadTask = imagesRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                 progressDialog.dismiss();
                    Toast.makeText(Profile.this, "Error : "+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(Profile.this, "Uploading Done!!!", Toast.LENGTH_SHORT).show();
                    databaseReference.child(common.currentUser).child("imageUrl").setValue(taskSnapshot.getDownloadUrl().toString());
                    Intent i =new Intent(getApplicationContext(),Profile.class);
                    startActivity(i);
                }
            });
        }
        else {

            Toast.makeText(Profile.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }


}
