package com.example.mac.bugfree;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends MainActivity {
    protected EditText loginText;
    //private UserList userList = new UserList();
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
                signInName = loginText.getText().toString();
                if(validUser(signInName)){
                    storePreference(signInName);
                    Toast.makeText(getApplicationContext(),
                            signInName + "has just logged in on this device.",
                            Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }
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
    private boolean validUser (String name){
        ElasticsearchUserController.IsExist isExist = new ElasticsearchUserController.IsExist();
        isExist.execute(name);
        try {
            if(isExist.get()){
                return true;
            } else{
                Toast.makeText(getApplicationContext(),
                        name+"does not exist.",
                        Toast.LENGTH_SHORT).show();
                return false;}
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
            Toast.makeText(getApplicationContext(),
                    "Can not verify signin info. Internet connection Error",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    //Stores current user name
    private void storePreference(String name){
        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        editor.putString("currentUser",name);
        editor.apply();
    }
}
