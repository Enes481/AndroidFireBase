package com.enestigli.instaclonejava.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.enestigli.instaclonejava.R;
import com.enestigli.instaclonejava.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        auth = FirebaseAuth.getInstance(); // we need initialize for signout
        getData();
    }
    private void getData(){
        //DocumentReference documentReference = firebaseFirestore.collection("posts").document("asdas");  //document name(asdas) is not necessary
        //CollectionReference documentReference = firebaseFirestore.collection("posts");                //if we get any error. we can use code line

        firebaseFirestore.collection("post").addSnapshotListener(new EventListener<QuerySnapshot>() {//we can get everything under the collection with addSnapshoListener
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) { //Nullable means it can be null.
                if(error != null){
                    Toast.makeText(FeedActivity.this, error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
                if(value!=null){
                    for(DocumentSnapshot snapshot : value.getDocuments()){
                        Map<String,Object> data = snapshot.getData();

                        String usermail =(String) data.get("email");
                        String comment = (String) data.get("comment");
                        String downloadurl = (String) data.get("downloadurl");

                        Post post = new Post(usermail,comment,downloadurl);

                        System.out.print(comment);

                    }
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_post){

            //Upload Activity
            Intent intentUpload = new Intent(FeedActivity.this,uploadActivity.class);
            startActivity(intentUpload);

        }
        else if(item.getItemId() == R.id.signout){
            Intent intenttoMain = new Intent(FeedActivity.this,MainActivity.class);
            startActivity(intenttoMain);
            auth.signOut();

        }
        return super.onOptionsItemSelected(item);
    }
}