package com.tripmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * This class lists all the properties of selected hotel. Moreover, user can display the comments made for this page.
 *
 * @author Ömer Faruk Akgül, Berke Ceran
 * @version 21.12.19
 */

public class Particular_Hotel_page extends AppCompatActivity {

    private static ArrayList<String> hotelContent;
    private Button viewCommentButton, addCommentButton;
    public TextView hotelName;
    private FirebaseFirestore db;
    private ListView lstView;

    /**
     * This method initializes all the parameters ands sets the city images.
     */
    public void init()
    {
        viewCommentButton = (Button) findViewById( R.id.view_comment_button_hotels);
        addCommentButton = ( Button) findViewById( R.id.add_rate_button);
        hotelName = (TextView) findViewById( R.id._hotel_name_hint);
        db = FirebaseFirestore.getInstance();
        hotelContent = new ArrayList<String>();
    }

    /**
     * This method is called whenever this page is restarted so that its content is kept up to date.
     *
     */
    @Override
    protected void onRestart(){
        super.onRestart();
        if(Hotels_page.returnChosenHotel() == null)
            hotelName.setText(getIntent().getStringExtra("place"));
        else
            hotelName.setText( Hotels_page.returnChosenHotel());
        lstView = (ListView) findViewById(R.id.list_of_information_hotel);




        db.collection("cities")
                .document( Main_Page.returnCity())
                .collection( Main_Page.returnCity() + "_hotels")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<String> content = new ArrayList<String>();
                        for( QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                        {
                            if( documentSnapshot.getData().get( "name").equals(getIntent().getStringExtra("place")) || documentSnapshot.getData().get( "name").equals(Hotels_page.returnChosenHotel())) {
                                String data = "Hotel Name: " + (String) documentSnapshot.getData().get("name") + "\n" + "Rate: " + documentSnapshot.getData().get("rate") + "\n" + "Address: " + (String) documentSnapshot.getData().get("address") + "\n" + "Facilities: " + (String) documentSnapshot.getData().get("facilities") + "\n" + "Phone:  " + (String) documentSnapshot.getData().get("phone") + "\n";
                                content.add(data);
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>( Particular_Hotel_page.this, android.R.layout.simple_list_item_1, content);
                        lstView.setAdapter(adapter);
                        hotelContent.addAll( content);
                    }
                });





        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, hotelContent);
        lstView.setAdapter(adapter);

        viewCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewCommentIntent = new Intent( Particular_Hotel_page.this, Comment_Rate_page.class);
                viewCommentIntent.putExtra("type","hotel");
                viewCommentIntent.putExtra("place",returnChosenHotel());
                startActivity( viewCommentIntent);
            }
        });

        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCommentIntent = new Intent( Particular_Hotel_page.this, Add_comment_rate_page.class);
                addCommentIntent.putExtra("type","hotel");
                addCommentIntent.putExtra("place",returnChosenHotel());
                startActivity( addCommentIntent);
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
        setContentView(R.layout.activity_particular__hotel_page);
        init();

        if(Hotels_page.returnChosenHotel() == null)
            hotelName.setText(getIntent().getStringExtra("place"));
        else
            hotelName.setText( Hotels_page.returnChosenHotel());
        lstView = (ListView) findViewById(R.id.list_of_information_hotel);




            db.collection("cities")
                    .document( Main_Page.returnCity())
                    .collection( Main_Page.returnCity() + "_hotels")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            ArrayList<String> content = new ArrayList<String>();
                            for( QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                            {
                                if( documentSnapshot.getData().get( "name").equals(getIntent().getStringExtra("place")) || documentSnapshot.getData().get( "name").equals(Hotels_page.returnChosenHotel())) {
                                    String data = "Hotel Name: " + (String) documentSnapshot.getData().get("name") + "\n" + "Rate: " + documentSnapshot.getData().get("rate") + "\n" + "Address: " + (String) documentSnapshot.getData().get("address") + "\n" + "Facilities: " + (String) documentSnapshot.getData().get("facilities") + "\n" + "Phone:  " + (String) documentSnapshot.getData().get("phone") + "\n";
                                    content.add(data);
                                }
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>( Particular_Hotel_page.this, android.R.layout.simple_list_item_1, content);
                            lstView.setAdapter(adapter);
                            hotelContent.addAll( content);
                        }
                    });





        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, hotelContent);
        lstView.setAdapter(adapter);

        viewCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewCommentIntent = new Intent( Particular_Hotel_page.this, Comment_Rate_page.class);
                viewCommentIntent.putExtra("type","hotel");
                viewCommentIntent.putExtra("place",returnChosenHotel());
                startActivity( viewCommentIntent);
            }
        });

        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCommentIntent = new Intent( Particular_Hotel_page.this, Add_comment_rate_page.class);
                addCommentIntent.putExtra("type","hotel");
                addCommentIntent.putExtra("place",returnChosenHotel());
                startActivity( addCommentIntent);
            }
        });
    }


    /**
     * This method returns the chosen restaurant
     *
     * @return chosen restaurant
     */
    public static String returnChosenHotel() {
        int index1 = hotelContent.get(0).indexOf(':') + 2;
        int index2 = hotelContent.get(0).indexOf("\n");
        return hotelContent.get(0).substring( index1, index2);
    }

}
