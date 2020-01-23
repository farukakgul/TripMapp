package com.tripmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class adds the entered comment and rates and updates the database.
 *
 *
 * @author: Ömer Faruk Akgül
 * @date: 20.12.19
 */
public class Add_comment_rate_page extends AppCompatActivity {

    private ImageButton rateOne, rateTwo, rateThree, rateFour, rateFive;
    private EditText commentText;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private String user;

    /**
     * This method initializes all the parameters.
     */
    public void init()
    {
        rateOne = ( ImageButton) findViewById( R.id.rate1);
        rateTwo = ( ImageButton) findViewById( R.id.rate2);
        rateThree = ( ImageButton) findViewById( R.id.rate3);
        rateFour = ( ImageButton) findViewById( R.id.rate4);
        rateFive = ( ImageButton) findViewById( R.id.rate5);
        commentText = (EditText) findViewById(R.id.text_area_comment);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser().getEmail();
    }

    /**
     * This method helps us adding comments on database.
     * @param rate: is the entered rate by user.
     */
    public void addComment(String rate){
        String comment = commentText.getText().toString();
        if(comment.isEmpty()){
            Toast.makeText(getApplication(), "Enter your comment", Toast.LENGTH_SHORT).show();
            return;
        }
        final HashMap<String, String> a = new HashMap<String, String>();
        a.put("user", user );
        a.put("comment", comment);
        a.put("rate", rate);
        db.collection("cities")
                .document(Main_Page.returnCity())                               // tries to reach the particular location of the database.
                .collection(City_Content.returnChosen())
                .document(getIntent().getStringExtra("place"))
                .collection(getIntent().getStringExtra("place") + "_comments")
                .add(a).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                db.collection("users")
                        .whereEqualTo("email" , user)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {                           //checks whether the task is completed
                                for( final QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                                {
                                    a.put("name", getIntent().getStringExtra("place"));
                                    a.put("type", getIntent().getStringExtra("type"));
                                    documentSnapshot.getReference().collection( "Past_actions").add(a).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference queryDocumentSnapshots) {
                                            db.collection("cities")
                                                    .document(Main_Page.returnCity())
                                                    .collection(City_Content.returnChosen())
                                                    .document(getIntent().getStringExtra("place"))
                                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    long placeRate = (Long)documentSnapshot.getData().get("rate");
                                                    long commentCount = (Long)documentSnapshot.getData().get("comment");
                                                    long lastRate = placeRate*commentCount + Integer.parseInt(a.get("rate"));
                                                    commentCount++;
                                                    lastRate = lastRate / commentCount;
                                                    HashMap<String, Object> b = new HashMap<String, Object>();
                                                    b.put("rate", lastRate);
                                                    b.put("comment", commentCount);
                                                    db.collection("cities")
                                                            .document(Main_Page.returnCity())
                                                            .collection(City_Content.returnChosen())
                                                            .document(getIntent().getStringExtra("place"))
                                                            .set(b,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            finish();
                                                        }
                                                    });
                                                }
                                            });
                                                }
                                            });
                                        }
                                }
                            });
            }
        });
    }

    /**
     * Helps to initialize all tasks done by this  class.
     * @param savedInstanceState is taken as a parameter always. It is default.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment_rate_page);
        init();

        rateOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment("1");
            }
        });

        rateTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment("2");
            }
        });

        rateThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment("3");
            }
        });

        rateFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment("4");
            }
        });

        rateFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment("5");
            }
        });
    }
}
