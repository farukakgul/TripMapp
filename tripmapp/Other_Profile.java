package com.tripmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * This class helps us to demonstrate the chosen user's past actions.
 *
 * @author: Ömer Faruk Akgül, Berke Ceran
 * @date: 20.12.19
 */

public class Other_Profile extends AppCompatActivity {

    private ListView list;
    private ArrayList<String> actions;
    private FirebaseFirestore db;
    private TextView name, surname, mail;
    private String user;
    private Button followButton;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String currentUser = auth.getCurrentUser().getEmail();

    /**
     * This method initializes all the parameters ands sets the city images.
     */
    public void init()
    {
        actions = new ArrayList<String>();
        name = findViewById( R.id.name_text2);
        surname = findViewById( R.id.surname_text2);
        mail = findViewById( R.id.username_uneditable2);
        db = FirebaseFirestore.getInstance();
        user = getIntent().getStringExtra("email");
        followButton = (Button) findViewById(R.id.follow_button);
        if(user.equals(currentUser))
            followButton.setVisibility(View.INVISIBLE);

        db.collection("users").whereEqualTo("email", currentUser).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(final QuerySnapshot queryDocumentSnapshots) {
                queryDocumentSnapshots.getDocuments().get(0).getReference().collection("Followed_users").whereEqualTo("user", user).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        if(querySnapshot.size() != 0 )
                            followButton.setText("Unfollow");
                        else
                            followButton.setText("Follow");
                    }
                });
            }
        });
    }

    /**
     * This method helps us to add the displayed user into our followed list.
     *
     * @param v is the default parameter of that method.
     */
    public void followMethod(View v){
        if(followButton.getText().equals("Follow")){
            final HashMap<String, String> a = new HashMap<String, String>();
            a.put("name", (String)name.getText());
            a.put("surname", (String)surname.getText());
            a.put("user", user);
            db.collection("users").whereEqualTo("email", currentUser).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                 queryDocumentSnapshots.getDocuments().get(0).getReference().collection("Followed_users").add(a).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            followButton.setText("Unfollow");
                        }
                    });
                }
            });
        }else{
            db.collection("users").whereEqualTo("email", currentUser).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    queryDocumentSnapshots.getDocuments().get(0).getReference().collection("Followed_users").whereEqualTo("user", user).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot querySnapshot) {
                            querySnapshot.getDocuments().get(0).getReference().delete();
                            followButton.setText("Follow");
                        }
                    });
                }
            });
        }
    }

    /**
     * Helps to initialize all tasks done by this  class.
     * @param savedInstanceState is taken as a parameter always. It is default.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other__profile);
        init();

        list = (ListView)findViewById(R.id.list_actions);

        mail.setText(user);

        db.collection( "users")
                .whereEqualTo( "email" , user)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                        {
                            name.setText( (String) documentSnapshot.getData().get( "name"));
                            surname.setText( (String) documentSnapshot.getData().get( "surname"));

                            final ArrayList<String> action = new ArrayList<String >();
                            documentSnapshot.getReference().collection( "Past_actions").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for( QueryDocumentSnapshot documentSnapshot1: queryDocumentSnapshots) {
                                        String data =  "\n" + (String) documentSnapshot1.getData().get("name") + "\n" + "Comment: " + (String) documentSnapshot1.getData().get("comment") + "\n" + "Given Rate: " + documentSnapshot1.getData().get("rate") + "\n" ;
                                        action.add(data);
                                    }
                                    ArrayAdapter adapter = new ArrayAdapter( Other_Profile.this, android.R.layout.simple_list_item_1, action);
                                    list.setAdapter( adapter);
                                    actions.addAll( action);
                                }
                            });
                        }
                    }
                });

        ArrayAdapter adapter = new ArrayAdapter( this, android.R.layout.simple_list_item_1, actions);
        list.setAdapter( adapter);
    }
}
