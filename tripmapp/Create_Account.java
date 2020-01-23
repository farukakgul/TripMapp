package com.tripmapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * This class helps to create account and save it on database.
 *
 * @author: Ömer Faruk Akgül
 * @version: 14.12.19
 */
public class Create_Account extends AppCompatActivity {

    private Button createButton;
    private String[] questions =  { "1) What is your name of first teacher?", "2) What is your favourite film?", "3) What is your best meal?"};
    //private ListView lstView2;
    private EditText txtName, txtSurname, txtMail, txtPassword;// txtPasswordAgain;
    private FirebaseAuth auth;
    //String passwordAgain = txtPasswordAgain.getText().toString();


    /**
     * This method initializes all the parameters ands sets the city images.
     */
    public void init()
    {

        createButton = ( Button) findViewById( R.id.create_account_button);
        txtName = ( EditText) findViewById( R.id.enter_name_text);
        txtSurname = ( EditText) findViewById( R.id.enter_surname_text);
        txtMail = ( EditText) findViewById( R.id.username_text);
        txtPassword = ( EditText) findViewById( R.id.password_text);
        //txtPasswordAgain = ( EditText) findViewById( R.id.password_again_text);

        auth = FirebaseAuth.getInstance();

    }

    /**
     * Helps to initialize all tasks done by this  class.
     * @param savedInstanceState is taken as a parameter always. It is default.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__account);
        init();

        /*lstView2 = (ListView)findViewById(R.id.listQuestion);
        ArrayAdapter adapter = new ArrayAdapter( this, android.R.layout.simple_list_item_1, questions);
        lstView2.setAdapter( adapter);
*/

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createNewAccount();

                /*User newuser = new User(
                ((TextView)(findViewById(R.id.enter_name_text))).getText().toString(),
                ((TextView)(findViewById(R.id.enter_surname_text))).getText().toString(),
                ((TextView)(findViewById(R.id.username_text))).getText().toString(),
                ((TextView)(findViewById(R.id.password_text))).getText().toString()
                );
        Db.addUser(newuser);*/


            }
        });
    }

    /**
     * The actual method, that does the creating process and gives an appropriate message based on the user actions.
     *
     */
    private void createNewAccount() {

        String name = txtName.getText().toString();
        String surname = txtSurname.getText().toString();
        String mail = txtMail.getText().toString();
        String password = txtPassword.getText().toString();

        final User user = new User( name, surname, mail, password);

        if( TextUtils.isEmpty( name) || TextUtils.isEmpty( surname) || TextUtils.isEmpty( mail) || TextUtils.isEmpty( password) )
        {
            Toast.makeText(this, " You should fill all the blocks.", Toast.LENGTH_LONG).show();
        }
        else if( !(android.util.Patterns.EMAIL_ADDRESS.matcher( mail).matches()))
        {
            Toast.makeText(this, " You should enter a valid mail address.", Toast.LENGTH_LONG).show();
        }
        /*else if( Db.checkEmail( mail))
        {
            Toast.makeText(this, " This email address has already been used.", Toast.LENGTH_LONG).show();
        }*/
        //else if( !(password.equals( passwordAgain)))
        //{
        //    Toast.makeText(this, " You should verify your password correctly.", Toast.LENGTH_LONG).show();

        //}
        else
        {
            auth.createUserWithEmailAndPassword( mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if( task.isSuccessful()) {
                        Db.addUser( user);
                        Toast.makeText( Create_Account.this, " Signed in successfully.", Toast.LENGTH_LONG).show();
                        Intent intentCreateProfile = new Intent(Create_Account.this, MainActivity.class);
                        startActivity(intentCreateProfile);
                        finish();
                    }
                    else
                    {
                        Toast.makeText( Create_Account.this, " An error occurred because of internet connection..", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}















