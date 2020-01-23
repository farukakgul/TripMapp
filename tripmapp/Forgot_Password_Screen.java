package com.tripmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Basically, displays the forgot password page
 *
 * @author: Ömer Faruk Akgül
 * @version:12.12.19
 */
public class Forgot_Password_Screen extends AppCompatActivity {

    private String [] questions =  { "1) What is your name of first teacher?", "2) What is your favourite film?", "3) What is your best meal?"};
    private ListView lstView;
    private Button changeButton;

    /**
     * This method initializes all the parameters ands sets the city images.
     */
    public void init()
    {
        changeButton = (Button) findViewById( R.id.change_button);
    }


    /**
     * Helps to initialize all tasks done by this  class.
     * @param savedInstanceState is taken as a parameter always. It is default.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password__screen);
        init();

        lstView = (ListView) findViewById(R.id.listQuestion3);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, questions);
        lstView.setAdapter(adapter);

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChangePassword = new Intent( Forgot_Password_Screen.this, MainActivity.class);
                startActivity( intentChangePassword);
            }
        });

    }


}
