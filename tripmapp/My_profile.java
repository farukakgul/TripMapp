package com.tripmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This class helps us to demonstrate the current user's past actions. Also user can change his/hr pass word by using the change password button on this page.
 *
 * @author: Ömer Faruk Akgül, Berke Ceran
 * @date: 20.12.19
 */
public class My_profile extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button passwordButton;
    private ListView list;
    private ArrayList<String> actions = new ArrayList<String>();
    private ArrayList<String> extra1 = new ArrayList<String>();
    private ArrayList<String> extra2 = new ArrayList<String>();
    private ArrayList<String> extra3 = new ArrayList<String>();
    private TextView name, surname, mail;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String user = auth.getCurrentUser().getEmail();

    /**
     * This method initializes all the parameters ands sets the city images.
     */
    public void init()
    {

        passwordButton = (Button) findViewById( R.id.change_password_demand);
        name = ( TextView) findViewById( R.id.name_text4);
        surname = ( TextView) findViewById( R.id.surname_text4);
        mail = ( TextView) findViewById( R.id.username_uneditable4);
    }

    /**
     * Helps to initialize all tasks done by this  class.
     * @param savedInstanceState is taken as a parameter always. It is default.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        init();

        list = (ListView)findViewById(R.id.list_users);

        db.collection( "users")
                .whereEqualTo( "email", user)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for( final QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                        {
                            final ArrayList<String> action = new ArrayList<String>();
                            documentSnapshot.getReference().collection( "Past_actions").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for( QueryDocumentSnapshot documentSnapshot1: queryDocumentSnapshots) {
                                        String data =  "\n" + (String) documentSnapshot1.getData().get("name") + "\n" + "Comment: " + (String) documentSnapshot1.getData().get("comment") + "\n" + "Given Rate: " + documentSnapshot1.getData().get("rate") + "\n" ;
                                        action.add(data);
                                        extra1.add((String)documentSnapshot1.getData().get("name"));
                                        extra2.add((String)documentSnapshot1.getData().get("type"));
                                        extra3.add((String)documentSnapshot1.getData().get("city"));
                                    }
                                    ArrayAdapter adapter = new ArrayAdapter( My_profile.this, android.R.layout.simple_list_item_1, actions);
                                    list.setAdapter( adapter);
                                    actions.addAll( action);
//                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                        @Override
//                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                            int pos = position;
//                                            if(extra2.get(pos).equals("hotel")){
//                                                Intent intentList = new Intent(My_profile.this, Particular_Hotel_page.class);
//                                                intentList.putExtra("place", extra1.get(pos));
//                                                intentList.putExtra("city", extra3.get(pos));
//                                                String tempListView = actions.get(position).toString();
//                                                intentList.putExtra("ListViewClickValue", tempListView);
//                                                startActivity(intentList);
//                                            }else if(extra2.get(pos).equals("place")){
//                                                Intent intentList = new Intent(My_profile.this, Particular_place_page.class);
//                                                intentList.putExtra("place", extra1.get(pos));
//                                                intentList.putExtra("city", extra3.get(pos));
//                                                String tempListView = actions.get(position).toString();
//                                                intentList.putExtra("ListViewClickValue", tempListView);
//                                                startActivity(intentList);
//                                            }else if(extra2.get(pos).equals("restaurant")){
//                                                Intent intentList = new Intent(My_profile.this, Particular_restaurant_page.class);
//                                                intentList.putExtra("place", extra1.get(pos));
//                                                intentList.putExtra("city", extra3.get(pos));
//                                                String tempListView = actions.get(position).toString();
//                                                intentList.putExtra("ListViewClickValue", tempListView);
//                                                startActivity(intentList);
//                                            }
//                                        }
//                                    });
                                }
                            });
                        }
                    }
                });


        db.collection( "users")                                                             // displays the current user's name  surname mail
                .whereEqualTo( "email", user)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(final QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                        {
                            documentSnapshot.getReference().get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    for( QueryDocumentSnapshot documentSnapshot2: queryDocumentSnapshots)
                                    {
                                        name.setText( (String) documentSnapshot2.getData().get( "name"));
                                        surname.setText( (String) documentSnapshot2.getData().get( "surname"));
                                        mail.setText( (String) documentSnapshot2.getData().get( "email"));
                                    }
                                }
                            });
                        }
                    }
                });



        passwordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChangePass = new Intent( My_profile.this, Change_password.class);
                startActivity( intentChangePass);
            }
        });

    }
}
