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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Helps to add comments to forum database
 *
 * @author: Berke Ceran, Ömer Faruk Akgül
 * @date: 20.12.19
 */
public class Forum_comment_page extends AppCompatActivity {

    private ImageButton rateFive;
    private EditText commentText;
    private FirebaseFirestore db;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String user = auth.getCurrentUser().getEmail();

    /**
     * This method initializes all the parameters ands sets the city images.
     */
    public void init()
    {
        rateFive = ( ImageButton) findViewById( R.id.ratefive);
        commentText = (EditText) findViewById(R.id.text_area_comment2);
        db = FirebaseFirestore.getInstance();
    }

    /**
     * adds the given comment to database
     *
     * @param v is default parameter of the method.
     */
    public void addComment(View v){
        String comment = commentText.getText().toString();
        if(comment.isEmpty()){
            Toast.makeText(getApplication(), "Enter your comment", Toast.LENGTH_SHORT).show();
            return;
        }
        final HashMap<String, String> a = new HashMap<String, String>();
        a.put("user", user );
        a.put("comment", comment);
        db.collection("cities")
                .document(Main_Page.returnCity())
                .collection(City_Content.returnChosen())
                .add(a).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Intent intentList = new Intent(Forum_comment_page.this, Forum_page.class);
                intentList.putExtra("place", getIntent().getStringExtra("place"));
                startActivity(intentList);
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
        setContentView(R.layout.activity_forum_comment_page);
        init();
    }
}
