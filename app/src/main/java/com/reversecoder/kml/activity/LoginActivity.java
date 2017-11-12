package com.reversecoder.kml.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.reversecoder.kml.R;
import com.reversecoder.kml.guillotinemenu.ParentActivity;


public class LoginActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ((LinearLayout)findViewById(R.id.btnRegistration)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegistration=new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intentRegistration);
            }
        });
    }

}
