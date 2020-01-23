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
 * Displays the content of rest. page and enables users to cross to the rest. hotel page
 *
 * @author: Ömer Faruk Akgül, Berke Ceran
 * @version: 20.12.19
 *
 */
public class restaurants_page extends AppCompatActivity {

    private ListView lstView;
    private FirebaseFirestore db ;
    private static final String TAG = "....";
    private static ArrayList<String> restaurantList;
    private static int pos = 0;
    private static int index = 0;
    private static int index2 = 0;

    /**
     * This method initializes all the parameters ands sets the city images.
     */
    public void init()
    {
        db = FirebaseFirestore.getInstance();
        restaurantList = new ArrayList<String>();
    }

    /**
     * Carries out the required operations on each restarting of this page.
     *
     */
    @Override
    protected void onRestart(){
        super.onRestart();

        lstView = (ListView) findViewById(R.id.list_all_Restaurants);


        db.collection("cities")
                .document( Main_Page.returnCity())
                .collection( Main_Page.returnCity() + "_restaurants")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<String> restaurants = new ArrayList<String>();
                        for( QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                        {
                            String data = "Restaurant Name: " + (String) documentSnapshot.getData().get("name") + "\n" + "Rate: " + documentSnapshot.getData().get("rate");
                            restaurants.add(data);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>( restaurants_page.this, android.R.layout.simple_list_item_1, restaurants);
                        lstView.setAdapter(adapter);
                        restaurantList.addAll( restaurants);
                    }
                });



        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                pos = position;
                index = restaurantList.get( position).indexOf(':');
                index2= restaurantList.get( position).indexOf("\n");
                String tempListView = restaurantList.get(position).toString();
                Intent intentList = new Intent(restaurants_page.this, Particular_restaurant_page.class);
                intentList.putExtra("ListViewClickValue", tempListView);
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
        setContentView(R.layout.activity_restaurants_page);
        init();

        lstView = (ListView) findViewById(R.id.list_all_Restaurants);


        db.collection("cities")
                .document( Main_Page.returnCity())
                .collection( Main_Page.returnCity() + "_restaurants")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<String> restaurants = new ArrayList<String>();
                        for( QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                        {
                            String data = "Restaurant Name: " + (String) documentSnapshot.getData().get("name") + "\n" + "Rate: " + documentSnapshot.getData().get("rate");
                            restaurants.add(data);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>( restaurants_page.this, android.R.layout.simple_list_item_1, restaurants);
                        lstView.setAdapter(adapter);
                        restaurantList.addAll( restaurants);
                    }
                });



        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                pos = position;
                index = restaurantList.get( position).indexOf(':');
                index2= restaurantList.get( position).indexOf("\n");
                String tempListView = restaurantList.get(position).toString();
                Intent intentList = new Intent(restaurants_page.this, Particular_restaurant_page.class);
                intentList.putExtra("ListViewClickValue", tempListView);
                startActivity(intentList);

            }
        });
    }
    /**
     * Given the chosen rest. so that its content will be used when demanded
     *
     * @return: name of the chosen rest.
     */
    public static String returnChosenRestaurant() {
        return restaurantList.get( pos).substring( index + 2, index2);
    }

}
