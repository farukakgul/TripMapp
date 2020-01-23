package com.tripmapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * This class helps us to change password on firebase authentication. To do so, sends mail to te given mail address.
 *
 * @author: Ömer Faruk Akgül
 * @version: 18.10.19
 */
public class Change_password extends AppCompatActivity {

    private Button sendButton;
    private EditText mailText;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    /**
     * This method initializes all the parameters.
     */
    public void init()
    {
        sendButton = ( Button) findViewById( R.id.send_button);
        mailText = ( EditText) findViewById( R.id.email_text);
        progressBar = ( ProgressBar) findViewById( R.id.progress_bar);
        auth = FirebaseAuth.getInstance();
        progressBar.setVisibility(View.INVISIBLE);

    }
    /**
     * Helps to initialize all tasks done by this  class.
     * @param savedInstanceState is taken as a parameter always. It is default.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();

        // when button  is clicked required info is sent to the mail.
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mailText.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {

                            @Override
                            public void onComplete( @NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Change_password.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Change_password.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }
}
