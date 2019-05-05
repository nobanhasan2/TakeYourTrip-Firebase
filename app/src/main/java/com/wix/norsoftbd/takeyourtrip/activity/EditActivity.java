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

public class EditActivity extends AppCompatActivity {
   String unicode;
    MaterialEditText title, location, validity, email, phone, description;

    Button update, remove;
    FirebaseDatabase database;
    DatabaseReference reference, user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String t,l,v,e,p,d,u;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        title = findViewById(R.id.editTitle2);
        location = findViewById(R.id.editpostLoaction2);
        validity = findViewById(R.id.editpostValidity2);
        email = findViewById(R.id.editpostEmail2);
        phone = findViewById(R.id.editpostPhone2);
        description = findViewById(R.id.editpostDescription2);
        update = findViewById(R.id.buttonupdate);
        remove = findViewById(R.id.buttonDlt);
        database=FirebaseDatabase.getInstance();
        reference = database.getReference("Posts");
        user = database.getReference("Users");
        Intent i = getIntent();
       Bundle b = i.getExtras();
       t=b.getString("Title");
       v=b.get("Validity").toString();
       l=b.get("Location").toString();
       e=b.get("Email").toString();

     p=b.get("Phone").toString();
       d=b.get("Discription").toString();
        phone.setText(p);
        email.setText(e);
        title.setText(t);
        description.setText(d);
        location.setText(l);
        validity.setText(v);
         unicode=b.get("unicode").toString();

   update.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           posts();
       }
   });
   remove.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           removed();
       }
   });
    }

    private void removed() {
        reference.child(unicode).removeValue();
        Intent i = new Intent(getApplicationContext(), Home.class);
        startActivity(i);
    }

    String tiTle, eMail, PhoNe, descripTion, valiDity, locaTion;
    boolean error = false;

    public void posts() {

        tiTle = title.getText().toString().trim();
        eMail = email.getText().toString().trim();
        PhoNe = phone.getText().toString().trim();
        descripTion = description.getText().toString().trim();
        valiDity = validity.getText().toString().trim();
        locaTion = location.getText().toString().trim();
        if (tiTle.isEmpty()) {
            error = true;
            title.setError("Title Missing");

        } else {

        }
        if (eMail.isEmpty()) {
            error = true;
            email.setError("Email Missing");
        } else {

        }
        if (PhoNe.isEmpty()) {
            error = true;
            phone.setError("Number Missing");
        } else {

        }
        if (descripTion.isEmpty()) {
            error = true;
            description.setError("Description Missing");
        }
        if (locaTion.isEmpty()) {
            error = true;
            location.setError("Location Missing");
        }
        if (valiDity.isEmpty()) {
            error = true;
            validity.setError("Give Validity");
        }


        if (!error) {
            final post post = new post(common.currentUser, title.getText().toString(), email.getText().toString(), phone.getText().toString(),
                    description.getText().toString(), validity.getText().toString(), location.getText().toString(), unicode);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // reference.child(common.currentUser+System.currentTimeMillis()).setValue(post);
                    reference.child(unicode).setValue(post);
                    Toast.makeText(getApplicationContext(), "updated", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), Home.class);
                    startActivity(i);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }
}
