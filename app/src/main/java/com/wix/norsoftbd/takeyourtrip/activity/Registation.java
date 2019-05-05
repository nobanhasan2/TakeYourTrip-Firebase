package com.wix.norsoftbd.takeyourtrip.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wix.norsoftbd.takeyourtrip.R;
import com.wix.norsoftbd.takeyourtrip.model.user;

public class Registation extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference users;

    private MaterialEditText edituserName,edituserPassword,edituserEmail,editUserusername,reenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);
        Button b = findViewById(R.id.buttonUp);
        reenter=(MaterialEditText)findViewById(R.id.editUserPasswordReenter);

        database =FirebaseDatabase.getInstance();
        users =database.getReference("Users");

        editUserusername=(MaterialEditText)findViewById(R.id.editUserUserName);
        edituserName =(MaterialEditText)findViewById(R.id.editUsername);
        edituserPassword =(MaterialEditText)findViewById(R.id.editUserPassword);
        edituserEmail=(MaterialEditText)findViewById(R.id.editUserEmail);




        final Spinner spinner = (Spinner)findViewById(R.id.spinner2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplication(),
                R.array.user_array, R.layout.spinner);

        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

   b.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           if (!checkError(edituserName.getText().toString().trim(),editUserusername.getText().toString().trim(),edituserEmail.getText().toString(), edituserPassword.getText().toString(),reenter.getText().toString())) {

               final user user = new user(edituserName.getText().toString(), editUserusername.getText().toString(), edituserEmail.getText().toString(),
                       edituserPassword.getText().toString(), spinner.getSelectedItem().toString());

               users.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       if (dataSnapshot.child(user.getEmail().replace(".","")).exists() || dataSnapshot.child(user.getUserName()).exists()) {
                           Toast.makeText(getApplicationContext(),"User Already Exist!",Toast.LENGTH_LONG).show();
                           //    edituserName.setError("User Already Exist");
                       } else {
                           users.child(user.getEmail().replace(".","")).setValue(user);
                           Intent i=new Intent(getApplicationContext(),success.class);
                           startActivity(i);



                       }
                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });
           }
       }

       private boolean checkError(String username, String name, String email, String password,String reenterpass) {

           if(username.isEmpty()){
               edituserName.setError("username Required");
               return true;
           }
           if(name.isEmpty()){
               editUserusername.setError("Name required");
               return true;
           }
           if(email.isEmpty()){
               edituserEmail.setError("Email Required");
               return true;
           }
           if(password.isEmpty()){
               edituserPassword.setError("Password rewuired");
               return true;
           }
           if(reenterpass.isEmpty()){
               reenter.setError("Re enter pass");
               return true;
           }
           if(!password.equals(reenterpass)){
               edituserPassword.setError("Pass not mached");
               reenter.setError("Pass not mached");

               return true;
           }

          return false;
       }
   });
    }
}
