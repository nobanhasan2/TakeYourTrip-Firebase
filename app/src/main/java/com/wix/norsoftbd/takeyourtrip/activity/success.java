package com.wix.norsoftbd.takeyourtrip.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wix.norsoftbd.takeyourtrip.R;

public class success extends AppCompatActivity implements View.OnClickListener {

    Button buttonLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        buttonLogin=findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
