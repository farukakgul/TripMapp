package com.tripmapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * This class displays the main page, which is confronted when user first entered to the application.
 *
 * @author Ömer Faruk Akgül
 * @date: 13.12.19
 */
public class MainActivity extends AppCompatActivity {

    private Button forgotPasswordButton, loginButton, createNewAccountButton;
    private EditText txtMail, txtPassword;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth;
    private FirebaseUser  currentUser;

    /**
     * This method initializes all the parameters ands sets the city images.
     */
    public void init()
    {
        forgotPasswordButton   = ( Button) findViewById( R.id.forgot_password_button);
        loginButton            = ( Button) findViewById( R.id.login_button);
        createNewAccountButton = ( Button) findViewById( R.id.create_account_button);

        txtMail = ( EditText) findViewById( R.id.username_text);
        txtPassword = ( EditText) findViewById( R.id.password_text1);
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
    }

    /**
     *
     * @return the current user is returned
     */
    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    /**
     * Helps to initialize all tasks done by this  class.
     * @param savedInstanceState is taken as a parameter always. It is default.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentForgotPassword = new Intent( MainActivity.this, Change_password.class);
                startActivity( intentForgotPassword);
            }
        }
        );

        createNewAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCreateAccount = new Intent( MainActivity.this, Create_Account.class);
                startActivity( intentCreateAccount);
            }
        }
        );

        /*loginButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               Intent intentMainPage = new Intent( MainActivity.this, Main_Page.class);
                                               startActivity( intentMainPage);
                                               Db.checkEmail("muratusernameee");
                                           }
                                       }
        );*/

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginUser();
            }
        });
    }

    /**
     * Login method takes the given info and checks whether user can enter or not.
     *
     */
    private void loginUser() {

        String mail = txtMail.getText().toString();
        String password = txtPassword.getText().toString();

        if( !(android.util.Patterns.EMAIL_ADDRESS.matcher( mail).matches()))
        {
            Toast.makeText(MainActivity.this, " You should enter a valid mail address.", Toast.LENGTH_LONG).show();
        }
        else if( TextUtils.isEmpty( mail))
        {
            Toast.makeText( MainActivity.this, " You should enter a mail address!..", Toast.LENGTH_LONG).show();
        }
        else if( TextUtils.isEmpty( password))
        {
            Toast.makeText( MainActivity.this, " You should enter your password!..", Toast.LENGTH_LONG).show();
        }
        else
        {
            auth.signInWithEmailAndPassword( mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if( task.isSuccessful())
                    {
                        Toast.makeText( MainActivity.this, " Login successful..", Toast.LENGTH_LONG).show();
                        Intent intentMainPage = new Intent( MainActivity.this, Main_Page.class);
                        startActivity( intentMainPage);
                        finish();
                    }
                    else
                    {
                        Toast.makeText( MainActivity.this, " Login unsuccessful!..", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

}
















