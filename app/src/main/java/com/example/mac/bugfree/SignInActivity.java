package com.example.mac.bugfree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {
    protected EditText loginText;
    private UserList userList = new UserList();
    private String signInName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_signin);
        setSupportActionBar(toolbar);

        Button signInButton = (Button) findViewById(R.id.login_button);
        loginText = (EditText) findViewById(R.id.login_edit);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                do{
                    signInName = loginText.getText().toString();
                } while(!validateUser(signInName));
                intent.putExtra("loginName",loginText.getText().toString());
                startActivity(intent);
            }
        });
        Button signUpButton = (Button) findViewById(R.id.link_signup);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
    //Validate user
    private boolean validateUser (String name){
        int i,n;
        n=userList.getUserListSize();
        for (i=0;i<n;i++){
            if(userList.getUser(i).getUsr().equals(name)){
                storeTextFile(name);
                userList.setCurrentUserID(i);
                return true;
            }
        }
        Toast.makeText(getApplicationContext(),
                name+"does not exist.",
                Toast.LENGTH_SHORT).show();
        return false;
    }

    //TODO store user
    private void storeTextFile(String name){

    }
}
