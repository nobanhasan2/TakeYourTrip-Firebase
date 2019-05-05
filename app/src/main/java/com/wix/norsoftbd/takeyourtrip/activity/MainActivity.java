package com.wix.norsoftbd.takeyourtrip.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wix.norsoftbd.takeyourtrip.R;
import com.wix.norsoftbd.takeyourtrip.common.common;
import com.wix.norsoftbd.takeyourtrip.model.user;
import com.wix.norsoftbd.takeyourtrip.utils.saveLoginSession;

public class MainActivity extends AppCompatActivity {


    private MaterialEditText edituserName,edituserPassword,edituserEmail,editUserusername;
    private  MaterialEditText editName,editPassword;
    Button signup,signin;
    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database =FirebaseDatabase.getInstance();
        users =database.getReference("Users");


        editName=(MaterialEditText)findViewById(R.id.editUser);
        editPassword=(MaterialEditText)findViewById(R.id.editPassword);
        signin=(Button)findViewById(R.id.buttonlogin);
        signup=(Button)findViewById(R.id.buttonSignup);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showSignupDialogue();
                Intent i  =new Intent(MainActivity.this,Registation.class);
                startActivity(i);
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn(editName.getText().toString().replace(".",""),editPassword.getText().toString());

            }
        });

    }

    private void signIn(final String uname, final String password)

    {

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(uname).exists()){
                    if(!uname.isEmpty()){
                        user login=dataSnapshot.child(uname).getValue(user.class);
                        if(login.getPassWord().equals(password)){
                            common.currentUser=uname;
                            common.userType=dataSnapshot.child(uname).child("userType").getValue().toString();
                            common.username=dataSnapshot.child(uname).child("name").getValue().toString();
                           // Toast.makeText(getApplicationContext(),common.userType.toString(),Toast.LENGTH_LONG).show();
//                            if(dataSnapshot.child("imageUrl").exists()){
//                                common.userIcon= dataSnapshot.child("imageUrl").getValue().toString();
                           saveLoginSession.saveSharedSetting(MainActivity.this, "ClipCodes", "false");
                            saveLoginSession.SharedPrefesSAVE(getApplicationContext(), common.currentUser.toString(),common.userType.toString(),common.username.toString());

                            Intent ImLoggedIn = new Intent(getApplicationContext(), Home.class);
                            startActivity(ImLoggedIn);
                            finish();

//                            Intent homActivity =new Intent(MainActivity.this,Home.class);
//                            startActivity(homActivity);
//                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Wrong Password",Toast.LENGTH_LONG).show();
                        }

                    }
                    else {

                        Toast.makeText(getApplicationContext(),"Please Enter Username",Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(),"User Is not Exist",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void showSignupDialogue(){
        final AlertDialog.Builder alartdialog= new AlertDialog.Builder(MainActivity.this);
        alartdialog.setTitle("Sign UP");
        alartdialog.setMessage("Please Fill Up form");

        LayoutInflater inflater = this.getLayoutInflater();

        final View signup_layout= inflater.inflate(R.layout.sign_up,null);

        final Spinner spinner = (Spinner)signup_layout.findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplication(),
                R.array.user_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        //reenter=signup_layout.findViewById(R.id.editUserPasswordreenter);
        editUserusername=(MaterialEditText)signup_layout.findViewById(R.id.editUserUserName);
        edituserName =(MaterialEditText)signup_layout.findViewById(R.id.editUsername);
        edituserPassword =(MaterialEditText)signup_layout.findViewById(R.id.editUserPassword);
        edituserEmail=(MaterialEditText)signup_layout.findViewById(R.id.editUserEmail);

        alartdialog.setView(signup_layout);
        alartdialog.setIcon(R.drawable.ic_account_circle_black_24dp);
        alartdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alartdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialogInterface, int i) {





            }

        });

        alartdialog.show();
    }
    public boolean checkError(String name,String username,String email,String password){
            if(name.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty()){

                Toast.makeText(getApplicationContext(), "All Field Requird! Registration Faild", Toast.LENGTH_LONG).show();

                return  true;
            }


       else
        return false;
    }

    @Override
    public void onBackPressed() {
      Intent i = new Intent(MainActivity.this,MainActivity.class);
      startActivity(i);
      Toast.makeText(getApplicationContext(),"You must Loged in",Toast.LENGTH_LONG).show();
    }
}
