package com.wix.norsoftbd.takeyourtrip.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wix.norsoftbd.takeyourtrip.R;
import com.wix.norsoftbd.takeyourtrip.common.common;
import com.wix.norsoftbd.takeyourtrip.model.post;

public class PostDeals extends AppCompatActivity {

    private MaterialEditText Title,email,phone,description,validity,location;
    Button postbtn;
    FirebaseDatabase database ;
    DatabaseReference reference,user;
    String imageUrl;
    String tiTle,eMail,PhoNe,descripTion,valiDity,locaTion;
    boolean error=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_deals);
        Title=findViewById(R.id.editTitle);
        email=findViewById(R.id.editpostEmail);
        phone=findViewById(R.id.editpostPhone);
        description=findViewById(R.id.editpostDescription);
        validity=findViewById(R.id.editpostValidity);
        location=findViewById(R.id.editpostLoaction);
        postbtn=findViewById(R.id.buttonPost);
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Posts");
        user=database.getReference("Users");


        postbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

               posts();
                }
        });
        user.child(common.currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("imageUrl").exists()) {
                    imageUrl = dataSnapshot.child("imageUrl").getValue().toString();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(),"Something Wrong",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void posts(){

        tiTle=Title.getText().toString().trim();
        eMail=email.getText().toString().trim();
        PhoNe=phone.getText().toString().trim();
        descripTion=description.getText().toString().trim();
        valiDity=validity.getText().toString().trim();
        locaTion=location.getText().toString().trim();
        if(tiTle.isEmpty()){
            error=true;
            Title.setError("Title Missing");

        }
        else {

        }
        if(eMail.isEmpty()){
            error=true;
            email.setError("Email Missing");
        }
        else {

        }
        if(PhoNe.isEmpty()){
            error=true;
            phone.setError("Number Missing");
        }
        else {

        }
        if(descripTion.isEmpty()){
            error=true;
            description.setError("Description Missing");
        }
        if(locaTion.isEmpty()){
            error=true;
            location.setError("Location Missing");
        }
        if(valiDity.isEmpty()){
            error=true;
            validity.setError("Give Validity");
        }

   final String unicode=common.currentUser+System.currentTimeMillis();
        if(!error) {
            final post post = new post(common.currentUser, Title.getText().toString(), email.getText().toString(), phone.getText().toString(),
                    description.getText().toString(), validity.getText().toString(), location.getText().toString(),unicode.toString());
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    reference.child(unicode).setValue(post);
                    Toast.makeText(getApplicationContext(), "Posted", Toast.LENGTH_LONG).show();
                    Intent i =new Intent(getApplicationContext(),Home.class);
                    startActivity(i);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }






    }
}
