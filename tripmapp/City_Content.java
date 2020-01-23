package com.tripmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This class helps to generate the city content page. City content page displays hotels, rests, etc.
 *
 * @author: Ömer Faruk Akgül
 * @version: 17.12.19
 */
public class City_Content extends AppCompatActivity {

    private Button hotel_button, restaurant_button, forum_button, places_button;
    private TextView city_text;
    private ImageView city_image;
    private static int chosen;

    /**
     * This method initializes all the parameters ands sets the city images.
     */
    public void init()
    {
        String city = getIntent().getStringExtra("city");
        hotel_button = (Button) findViewById( R.id.hotel_button);
        restaurant_button = (Button) findViewById( R.id.Restaurant_Button);
        forum_button = (Button) findViewById( R.id.Forum_button);
        places_button = (Button) findViewById( R.id.place_button);
        city_text = (TextView) findViewById(R.id.citytext);
        city_image = (ImageView) findViewById(R.id.city_map_image);
        switch (city){
            case "Istanbul":{
                city_image.setImageResource(R.drawable.istanbul_map);
            }

            case "Yozgat":{
            }
            case "Ankara":{
            }
            case "Mardin":{

            }
            case "Samsun":{

            }
            case "Zonguldak":{

            }
        }
        city_text.setText(city);
        chosen = 0;
    }

    /**
     * Helps to initialize all tasks done by this  class.
     * @param savedInstanceState is taken as a parameter always. It is default.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city__content);
        init();

        hotel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosen = 1;
                Intent intentHotel = new Intent( City_Content.this, Hotels_page.class);
                startActivity( intentHotel);
            }
        });

        restaurant_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosen = 2;
                Intent intentRestaurant = new Intent( City_Content.this, restaurants_page.class);
                startActivity( intentRestaurant);
            }
        });

        places_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosen = 3;
                Intent intentPlace = new Intent( City_Content.this, Places_page.class);
                startActivity( intentPlace);
            }
        });

        forum_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosen = 4;
                Intent intentForum = new Intent( City_Content.this, Forum_page.class);
                startActivity( intentForum);
            }
        });
    }

    /**
     * This method returns the chosen option by using the returned city in the main page.
     *
     * @return: it decides which database sublist will be used.
     */
    public static String returnChosen()
    {
        if( chosen == 1)
        {
            return Main_Page.returnCity()+"_hotels";
        }
        else if ( chosen == 2)
        {
            return Main_Page.returnCity()+"_restaurants";
        }
        else if ( chosen == 3)
        {
            return Main_Page.returnCity()+"_places";
        }
        return Main_Page.returnCity()+"_forum";
    }
}
