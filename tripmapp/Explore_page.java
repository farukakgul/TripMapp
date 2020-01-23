package com.tripmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class illustrates the social interactions between users like following
 *
 * @author: Ömer Faruk Akgül
 * @date: 20.12.19
 */
public class Explore_page extends AppCompatActivity {

    private ListView list;
    private ArrayList<String> users = new ArrayList<String>();
    private ArrayList<String> userMails = new ArrayList<String>();

    // bu sayfada users- dan followed users ı çekip listeye atmak gerekiyor


    private FirebaseAuth auth;
    private FirebaseFirestore db ;
    private String user;
    private static int pos, index, index2;

    /**
     * This method initializes all the parameters ands sets the city images.
     */
    public void init(){
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = auth.getCurrentUser().getEmail();
    }

    /**
     * Helps to initialize all tasks done by this  class.
     * @param savedInstanceState is taken as a parameter always. It is default.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_page);
        init();

        list = (ListView)findViewById(R.id.listOfUsers);

        db.collection( "users")                                                 //reaches the desired location in database and cheks the existing users
                .whereEqualTo( "email", user)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for( final QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                        {
                            final ArrayList<String> user = new ArrayList<String>();
                            documentSnapshot.getReference().collection( "Followed_users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for( QueryDocumentSnapshot documentSnapshot1: queryDocumentSnapshots) {
                                        String data = "User: " + (String) documentSnapshot1.getData().get("user") + "\n" ;
                                        user.add(data);
                                        userMails.add((String) documentSnapshot1.getData().get("user"));
                                    }
                                    ArrayAdapter adapter = new ArrayAdapter( Explore_page.this, android.R.layout.simple_list_item_1, users);
                                    list.setAdapter( adapter);
                                    users.addAll( user);
                                }
                            });
                        }
                    }
                });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                pos = position;
                index = users.get( position).indexOf(':') + 2;
                index2= users.get( position).indexOf("\n");
                String tempListView = users.get(position).toString();
                Intent intentList = new Intent(Explore_page.this, Other_Profile.class);
                intentList.putExtra("ListViewClickValue", tempListView);
                intentList.putExtra("email", userMails.get(pos));
                startActivity(intentList);
            }
        });
    }
}
