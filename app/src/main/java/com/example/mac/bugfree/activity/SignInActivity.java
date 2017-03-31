package com.example.mac.bugfree.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mac.bugfree.controller.ElasticsearchImageOfflineController;
import com.example.mac.bugfree.controller.ElasticsearchUserController;
import com.example.mac.bugfree.R;
import com.example.mac.bugfree.module.User;
import com.example.mac.bugfree.util.InternetConnectionChecker;
import com.example.mac.bugfree.util.SaveFile;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.google.common.collect.ComparisonChain.start;

/**
 * This is the Sign Up view class of the project, <br> In this class, user interaction and Elastic Query action are performed
 *<p>User input of user name is check by Elastic search, no duplicate is allowed.
 * User will back to Signin screen when Sign up is finished</p>
 * @author Zhi Li
 * @version 2.0
 * @see MainActivity
 */
public class SignInActivity extends AppCompatActivity {
    protected EditText loginText;
    private String signInName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Check internet connectivity first
        InternetConnectionChecker checker = new InternetConnectionChecker();
        Context context = getApplicationContext();
        final boolean isOnline = checker.isOnline(context);
        if (isOnline) {
            Toast.makeText(getApplicationContext(),
                    "This device is online",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    "This device is offline",
                    Toast.LENGTH_SHORT).show();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_signin);
        setSupportActionBar(toolbar);

        ImageView imageView = (ImageView) findViewById(R.id.title_image);
        imageView.setImageResource(R.drawable.umood);

        Button signInButton = (Button) findViewById(R.id.login_button);
        loginText = (EditText) findViewById(R.id.login_edit);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InternetConnectionChecker checker = new InternetConnectionChecker();
                Context context = getApplicationContext();
                final boolean isOnline = checker.isOnline(context);
                signInName = loginText.getText().toString();
                if(validUser(signInName) && isOnline){
                    storePreference(signInName);
                    Toast.makeText(getApplicationContext(),
                            signInName + " has just logged in on this device.",
                            Toast.LENGTH_SHORT).show();

                    // Save the user just logged in in Json file
                    ElasticsearchUserController.GetUserTask getUserTask = new ElasticsearchUserController.GetUserTask();
                    ElasticsearchImageOfflineController elasticsearchImageOfflineController = new ElasticsearchImageOfflineController();
                    getUserTask.execute(signInName);
                    try {
                        User user = getUserTask.get();
                        SaveFile s = new SaveFile(context, user);
                        //Clear the local upload,delete,online lists
                        elasticsearchImageOfflineController.prepImageOffline(context,user);

                    } catch (Exception e) {
                        Log.i("Error", "Failed to get the User out of the async object");
                    }

                    //setResult(RESULT_OK);
                    //finish();
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (!isOnline){
                    Toast.makeText(getApplicationContext(),
                            "Please check internet connectivity.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button signUpButton = (Button) findViewById(R.id.link_signup);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline) {
                    Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please check internet connectivity.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * This method validates if the user input of user name exist in Elastic search.
     * @param name String of user id
     * @return boolean
     */
    private boolean validUser (String name){
        ElasticsearchUserController.IsExist isExist = new ElasticsearchUserController.IsExist();
        isExist.execute(name);
        try {
            if(isExist.get()){
                return true;
            } else{
                Toast.makeText(getApplicationContext(),
                        name+" does not exist.",
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

    /**
     * This method stored the successfully login user's name in a local file
     * File explorer -> data -> data -> com.example.mac.bugfree -> sharef_prefs -> data.xml
     * @param name String of user name to be stored
     */
    private void storePreference(String name){
        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        editor.putString("currentUser",name);
        editor.apply();
    }
}
