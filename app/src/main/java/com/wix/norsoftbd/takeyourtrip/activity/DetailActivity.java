package com.wix.norsoftbd.takeyourtrip.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.devspark.robototextview.widget.RobotoTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.wix.norsoftbd.takeyourtrip.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity {
    Button Email,call;
   String number,mail;
    RobotoTextView Title,location,validity,user,discription;
    CircleImageView icon;
    FirebaseDatabase database;
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Email=findViewById(R.id.contact_mail_button);
        call=findViewById(R.id.contact_call_button);
        database=FirebaseDatabase.getInstance();
        db=database.getReference("Users");

        Intent i =getIntent();
        Bundle b =i.getExtras();
        Title =findViewById(R.id.detail_title);
        icon=findViewById(R.id.delail_icon_image);
        location=findViewById(R.id.detail_location);
        validity=findViewById(R.id.detail_validity);

        discription=findViewById(R.id.detail_description);
      //  number=b.get("Phone").toString();
         mail=b.get("Email").toString();
        Title.setText(b.get("Title").toString());
        discription.setText(b.get("Discription").toString());
        location.setText(b.get("Location").toString());
        validity.setText(b.get("Validity").toString());

        db.child(b.get("User").toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("imageUrl").exists()){
                    Picasso.with(getApplicationContext()).load(dataSnapshot.child("imageUrl").getValue().toString()).into(icon);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail();
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call();
            }
        });

    }

    private void Call() {

        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        callIntent.setData(Uri.parse("tel:" +number));
        startActivity(callIntent);
    }

    private void mail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
            "mailto",mail, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "TakeyourTrip");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi I interested In your Deals");
        startActivity(Intent.createChooser(emailIntent, "Send A Mail To Dealer"));

    }
}
