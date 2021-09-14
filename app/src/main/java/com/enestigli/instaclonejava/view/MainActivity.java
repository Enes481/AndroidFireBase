package com.enestigli.instaclonejava.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.enestigli.instaclonejava.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        FirebaseUser CurrentUser =auth.getCurrentUser();
        if(CurrentUser!=null){
            Intent intent = new Intent(MainActivity.this,FeedActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void signinClicked(View view){
        String email = binding.edttxtmail.getText().toString();
        String password = binding.edttxtpasw.getText().toString();

        if(email.equals("") || password.equals("") ){
            Toast.makeText(this,"enter email and password" ,Toast.LENGTH_LONG).show();
        }
        else{
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent = new Intent(MainActivity.this,FeedActivity.class);
                    startActivity(intent);
                    finish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    public void singupClicked(View view){

        String email = binding.edttxtmail.getText().toString();
        String password = binding.edttxtpasw.getText().toString();

        if(email.equals("") || password.equals("") ){
            Toast.makeText(this,"enter email and password" ,Toast.LENGTH_LONG).show();
        }

        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() { //AddonSuccess arka planda kullanıcı arayüzünü bloklamadan çalışmaya yarıyor.Eğer email ve password doğru girilirse  oluşursa ne olacak
            @Override
            public void onSuccess(AuthResult authResult) {
                Intent intent = new Intent(MainActivity.this,FeedActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() { //Eğer email ve password yanlış girilirse  oluşursa ne olacak
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG);
            }
        });

    }
}