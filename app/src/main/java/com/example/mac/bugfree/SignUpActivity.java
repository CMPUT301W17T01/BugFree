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
    private UserList userList= new UserList();
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
                if (exists(signUpText.getText().toString())){

                }
                do{
                signUpName = signUpText.getText().toString();
                } while(!isDuplicateName(signUpName));
                Toast.makeText(getApplicationContext(),
                        "User "+signUpName+"created.",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    //Check duplicate user name
    private boolean exists(String newName){
        int i,n;
        n=userList.getUserListSize();
        for (i=0;i<=n;i++){
            if(userList.getUser(i).getUsr().equals(newName)){
                Toast.makeText(getApplicationContext(),
                        signUpName+"already exists.",
                        Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
}
