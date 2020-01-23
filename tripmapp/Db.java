package com.tripmapp;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * This class was created to carry out all required operations on database but in later stages of the project it made our job harder so we did not use it as aimed.
 *
 * @author: Ömer Faruk Akgül
 * @version: 18.12.19
 */
public class Db {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "....";

    //static FirebaseAuth auth = FirebaseAuth.getInstance();


    /**
     * Adds user on database.
     *
     * @param user: User object has name, surname, etc.
     */
    public static void addUser(User user){
        //auth.sendPasswordResetEmail("d8033046@urhen.com");
        /*auth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Log.i("UYELIK", "UYELIK EKLENDI YENI YOL");
            }6.
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("EXCEPTIONN", e.getMessage());
            }
        });*/

        db.collection("users").add(user.toMap()).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.i("UYELIK", "UYE OLDUN");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("UYELIK", "UYE OLAMADIN");

            }
        });
    }

   /* public static void getCities(final ArrayList<String> empty){
        db.collection("cities")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<String> city = new ArrayList<String>();
                        for( QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                        {
                            Log.i( "citttttty", ""+ documentSnapshot.getData().get("name"));
                            String data =   "\n" + (String) documentSnapshot.getData().get("name") + "\n" ;
                            city.add(data);
                        }
                        empty.addAll( city);
                    }
                });

    }*/

    /**
     * Checks existing emails and return whether the given mail exists.
     *
     * @param email: given mail
     * @return: boolean true or false
     */
    public static boolean checkEmail(String email){
        Task<QuerySnapshot> a =  db.collection("users").whereEqualTo("email", email).get();

        final boolean[] value = {false};
        a.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Log.i("SIZE", "" + queryDocumentSnapshots.size());
                if(( queryDocumentSnapshots.size() + "").equals("1"))
                    value[0] = true;
            }
        });
        if(value[0])
            return true;
        return false;
    }

    /**
     * Gives all the hotel in the city.
     *
     * @return: list of hotels.
     */
    public static ArrayList< String> getHotels()
    {
        final ArrayList<String> hotelList = new ArrayList<String>();
        db.collection("cities")
                .document( "Ankara")
                .collection( "Ankara_hotels")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d(TAG, "MESSAGE");
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d( TAG,  "1.---"+ document.getData().get( "name"));
                                hotelList.add((String) document.getData().get( "name"));
                            }
                            Log.d( TAG, "ooooo" +  hotelList.toString());
                        }
                        else {
                            hotelList.add( "nothing to display");
                        }
                    }
                });
        return hotelList;
    }

}
