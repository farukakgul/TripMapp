package com.tripmapp;

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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

/**
 * This class helps users to search cities and find one demanded, also, user can view followed profile or can view the settings of his her own profile.
 *
 * @author: Ömer Faruk Akgül, Berke Ceran
 * @date: 15.12.19
 */
public class Main_Page extends AppCompatActivity {
    private MaterialSearchView searchView;
    private static ArrayList<String > cities;
    private static ListView lstView;
    private Button personalButton;
    private Button exploreButton;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db ;
    private static int pos;



    /**
     * This method initializes all the parameters ands sets the city images.
     */
    public void init()
    {
        personalButton   = ( Button) findViewById( R.id.my_profile_button);
        exploreButton = ( Button) findViewById( R.id.explore_button);
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        cities = new ArrayList<String >();
        db = FirebaseFirestore.getInstance();
        pos = 0;
    }

    /**
     * Helps to initialize all tasks done by this  class.
     * @param savedInstanceState is taken as a parameter always. It is default.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__page);
        init();

        Log.i( "array", cities.toString());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Material Search");
        toolbar.setTitleTextColor(Color.parseColor( "#FFFFFF"));

        lstView = (ListView)findViewById(R.id.listView);


        db.collection("cities")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<String> c = new ArrayList<String>();
                        for( QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                        {
                            String data = "\n" + (String) documentSnapshot.getData().get("name") + "\n" ;
                            c.add(data);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>( Main_Page.this, android.R.layout.simple_list_item_1, c);
                        lstView.setAdapter(adapter);
                        cities.addAll( c);
                    }
                });


        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                pos = position;
                String tempListView = (String)lstView.getAdapter().getItem(position);
                Intent intentList = new Intent( Main_Page.this, City_Content.class);
                intentList.putExtra("city", tempListView.substring(1,tempListView.length()-1));
                intentList.putExtra( "ListViewClickValue", tempListView);
                //intentList.putExtra("sehir", city);
                startActivity( intentList);

            }
        });

        personalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPersonalSetting = new Intent( Main_Page.this, My_profile.class);
                startActivity( intentPersonalSetting);
            }
        });

        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentExplore = new Intent( Main_Page.this, Explore_page.class);
                startActivity( intentExplore);
            }
        });

        searchView = (MaterialSearchView)findViewById(R.id.search_view);

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

                // If closed search view, lstView return default
                lstView = (ListView)findViewById(R.id.listView);
                ArrayAdapter adapter = new ArrayAdapter( Main_Page.this, android.R.layout.simple_list_item_1, cities);
                lstView.setAdapter( adapter);
            }
        });
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if( newText != null && !newText.isEmpty())
                {
                    List<String> listFound = new ArrayList<String>();
                    for( String item: cities)
                    {
                        if( item.toLowerCase().contains( newText.toLowerCase()))
                            listFound.add(item);
                    }

                    ArrayAdapter adapter = new ArrayAdapter( Main_Page.this, android.R.layout.simple_list_item_1, listFound);
                    lstView.setAdapter( adapter);
                }
                else
                {
                    // if text search is null return default
                    ArrayAdapter adapter = new ArrayAdapter( Main_Page.this, android.R.layout.simple_list_item_1, cities);
                    lstView.setAdapter( adapter);
                }
                return true;
            }

        });

    }

    /**
     * Carries out the given operations on starting process
     *
     */
    @Override
    protected void onStart() {

        if( currentUser == null)
        {
            Intent nullIntent = new Intent( Main_Page.this, MainActivity.class);
            startActivity( nullIntent);
            finish();
        }

        super.onStart();
    }

    /**
     * Creates the options menu that means cities are included and given to the user as options
     *
     * @param menu is the menu of cities
     * @return is the boolean is process done successfully
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main ,menu);
        MenuItem item = menu.findItem( R.id.action_search);
        searchView.setMenuItem( item);
        return true;
    }

    /**
     * Returns the chosen city in order to do demanded operations on it in the following process of application.
     *
     * @return chosen city
     */
    public static String returnCity()
    {
        int index1 = cities.get(pos).indexOf( "\n");
        int index2 = cities.get(pos).indexOf( "\n", index1 + 1);
        if(lstView == null)
            return cities.get(pos).substring( index1 + 1, index2);
        else
            return ((String)lstView.getAdapter().getItem(pos)).substring(1, ((String)lstView.getAdapter().getItem(pos)).length()-1);
    }
}
