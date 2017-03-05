package com.example.mac.bugfree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    protected EditText signUpText;
    private String signUpName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_signup);
        setSupportActionBar(toolbar);

        Button signUpButton = (Button) findViewById(R.id.signup_button);
        signUpText = (EditText) findViewById(R.id.signup_edit);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpName = signUpText.getText().toString();
                Toast.makeText(getApplicationContext(),
                    "clicked",
                    Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
