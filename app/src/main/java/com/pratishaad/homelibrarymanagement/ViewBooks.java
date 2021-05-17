package com.pratishaad.homelibrarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewBooks extends AppCompatActivity {

    EditText titleName;
    Button searchviewbtn;
    TextView bookDetails;
    DatabaseReference mDatabase;
    FirebaseAuth firebaseAuth;
    String uid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_books);
        titleName = (EditText) findViewById(R.id.titleName);
        searchviewbtn = (Button) findViewById(R.id.searchviewbtn);
        bookDetails = (TextView) findViewById(R.id.bookDetails);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        uid=firebaseAuth.getUid();

        searchviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child(uid).child("AllBooks").child(String.valueOf(titleName)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            //Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            bookDetails.setText(String.valueOf(task.getResult().getValue()));
                        }
                    }
                });
            }
        });

    }
}