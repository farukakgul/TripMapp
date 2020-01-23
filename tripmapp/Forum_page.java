
package com.tripmapp;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import android.content.Intent;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ListView;

        import androidx.appcompat.widget.Toolbar;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.google.firebase.firestore.DocumentReference;
        import com.google.firebase.firestore.FirebaseFirestore;
        import com.google.firebase.firestore.QueryDocumentSnapshot;
        import com.google.firebase.firestore.QuerySnapshot;
        import com.miguelcatalan.materialsearchview.MaterialSearchView;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Map;

/**
 * Displays the content of forum page and enables users to cross to the other profile page
 *
 * @author: Ömer Faruk Akgül
 * @version: 20.12.19
 *
  */

public class Forum_page extends AppCompatActivity {

    private ListView listView;
    private Button forumButton;
    private String[] commentsForum = { "comment1", "comment2", "comment3", "comment4", "comment5", "comment6", "comment7", "comment8"};
    private ArrayList<String> emailList = new ArrayList<String>();
    private ListView lstView;
    private FirebaseFirestore db ;
    private static final String TAG = "....";
    private static ArrayList<String> commentsList;


    /**
     * This method initializes all the parameters ands sets the city images.
     */
    public void init()
    {
        db = FirebaseFirestore.getInstance();
        commentsList = new ArrayList<String>();
        forumButton = ( Button) findViewById( R.id.add_comment_button);
    }


    /**
     * Helps to initialize all tasks done by this  class.
     * @param savedInstanceState is taken as a parameter always. It is default.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_page);
        init();

        lstView = (ListView) findViewById(R.id.commented_users_list);


        db.collection("cities")
                .document( Main_Page.returnCity())
                .collection( Main_Page.returnCity() + "_forum")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<String> comments = new ArrayList<String>();
                        for( QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                        {
                            String data = "User: " + (String) documentSnapshot.getData().get("user") + "\n\n" + "Comment: " + documentSnapshot.getData().get("comment");
                            comments.add(data);
                            emailList.add((String) documentSnapshot.getData().get("user"));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>( Forum_page.this, android.R.layout.simple_list_item_1, comments);
                        lstView.setAdapter(adapter);
                        commentsList.addAll( comments);
                    }
                });



        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String tempListView = commentsList.get(position).toString();
                Intent intentList = new Intent(Forum_page.this, Other_Profile.class);
                intentList.putExtra("ListViewClickValue", emailList.get(position));
                intentList.putExtra("email", emailList.get(position));
                startActivity(intentList);

            }
        });


        forumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forumPageIntent = new Intent( Forum_page.this, Forum_comment_page.class);
                forumPageIntent.putExtra("type","forum");
                forumPageIntent.putExtra("place", Main_Page.returnCity());
                startActivity( forumPageIntent);
            }
        });


    }

}

