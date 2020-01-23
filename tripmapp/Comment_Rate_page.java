package com.tripmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * This class helps us to display comments and rates of the selected location.
 *
 * @author: Ömer Faruk Akgül
 * @date:  18.12.19
 */
public class Comment_Rate_page extends AppCompatActivity {

    private static ArrayList<String> comments;
    private ArrayList<String> commentEmail;
    private Button addComment;
    private ListView listview;
    private FirebaseFirestore db;
    private static int pos, index, index2;
    static boolean isEntered;
    private String placeName;


    /**
     * This method initializes all the parameters ands sets the city images.
     */
    public void init() {
        placeName = getIntent().getStringExtra("place");
        addComment = (Button) findViewById(R.id.add_comment_rate_button_at_comment_page);
        db = FirebaseFirestore.getInstance();
        comments = new ArrayList<String>();
        commentEmail = new ArrayList<String>();
        listview = (ListView) findViewById(R.id.comment_users_list_at_comment_page);
    }

    /**
     * This method updates the content on each restart activity.
     *
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        db.collection( "cities")                                    // given sequence( used in the code of all pages) tries to reach desired location in database.
                .document( Main_Page.returnCity())
                .collection( City_Content.returnChosen())
                .document( placeName)
                .collection(placeName + "_comments" )
                .orderBy("rate")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    ArrayList<String> comment = new ArrayList<String>();
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                        {
                            final QueryDocumentSnapshot a = documentSnapshot;
                            db.collection("users")
                                    .whereEqualTo("email" , documentSnapshot.getData().get( "user"))
                                    .limit(1)
                                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    String data =  "User: " + (String) queryDocumentSnapshots.getDocuments().get(0).getData().get("name") + " " + (String) queryDocumentSnapshots.getDocuments().get(0).getData().get("surname")+ "\n" + "Comment: " + (String) a.getData().get( "comment") + "\n" + "Rate: " + a.getData().get( "rate");
                                    commentEmail.add((String)a.getData().get("user"));
                                    comment.add(data);
                                    ArrayAdapter<String > adapter = new ArrayAdapter<String>( Comment_Rate_page.this, android.R.layout.simple_list_item_1, comment);
                                    listview.setAdapter( adapter);
                                    comments.addAll( comment);
                                    isEntered = true;
                                }
                            });
                        }
                    }
                });


        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddComment = new Intent( Comment_Rate_page.this, Add_comment_rate_page.class);
                intentAddComment.putExtra("type",getIntent().getStringExtra("type"));
                intentAddComment.putExtra("place", placeName);
                startActivity( intentAddComment);
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                pos = position;
                index = comments.get( position).indexOf(':') + 2;
                index2= comments.get( position).indexOf("\n");
                String tempListView = comments.get(position).toString();
                Intent intentList = new Intent(Comment_Rate_page.this, Other_Profile.class);
                intentList.putExtra("ListViewClickValue", commentEmail.get(pos));
                intentList.putExtra("email", commentEmail.get(pos));
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
        setContentView(R.layout.activity_comment__rate_page);
        init();


        db.collection( "cities")
                .document( Main_Page.returnCity())
                .collection( City_Content.returnChosen())
                .document( placeName)
                .collection(placeName + "_comments" )
                .orderBy("rate")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    ArrayList<String> comment = new ArrayList<String>();
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                        {
                            final QueryDocumentSnapshot a = documentSnapshot;
                            db.collection("users")
                                    .whereEqualTo("email" , documentSnapshot.getData().get( "user"))
                                    .limit(1)
                                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    String data =  "User: " + (String) queryDocumentSnapshots.getDocuments().get(0).getData().get("name") + (String) queryDocumentSnapshots.getDocuments().get(0).getData().get("surname")+ "\n" + "Comment: " + (String) a.getData().get( "comment") + "\n" + "Rate: " + a.getData().get( "rate");
                                    commentEmail.add((String)a.getData().get("user"));
                                    comment.add(data);
                                    ArrayAdapter<String > adapter = new ArrayAdapter<String>( Comment_Rate_page.this, android.R.layout.simple_list_item_1, comment);
                                    listview.setAdapter( adapter);
                                    comments.addAll( comment);
                                    isEntered = true;
                                }
                            });
                        }
                    }
                });


        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddComment = new Intent( Comment_Rate_page.this, Add_comment_rate_page.class);
                intentAddComment.putExtra("type",getIntent().getStringExtra("type"));
                intentAddComment.putExtra("place", placeName);
                startActivity( intentAddComment);
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                pos = position;
                index = comments.get( position).indexOf(':') + 2;
                index2= comments.get( position).indexOf("\n");
                String tempListView = comments.get(position).toString();
                Intent intentList = new Intent(Comment_Rate_page.this, Other_Profile.class);
                intentList.putExtra("ListViewClickValue", commentEmail.get(pos));
                intentList.putExtra("email", commentEmail.get(pos));
                startActivity(intentList);

            }
        });

    }

    /**
     * This method returns the selected user in order to do required operations on that user.
     *
     * @return: selected user
     */
    public static String returnSelectedUser()
    {
        return comments.get(pos).substring( index, index2);
    }

    /**
     *
     * @return: whether this userr entered
     */
    public static boolean checkEntrance()
    {
        return isEntered;
    }
}























