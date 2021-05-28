package com.pratishaad.homelibrarymanagement.viewbooks;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pratishaad.homelibrarymanagement.R;

public class EditBook extends AppCompatActivity {

    EditText editTitle, editAuthor, editISBN, editGenre, editDescription, editCurrentlyReading;
    ImageView editImage;
    Button updateBtn,cancelBtn;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        View view =getSupportActionBar().getCustomView();

        editTitle= findViewById(R.id.editTitle);
        editAuthor= findViewById(R.id.editAuthor);
        editISBN= findViewById(R.id.editISBN);
        editGenre= findViewById(R.id.editGenre);
        editDescription= findViewById(R.id.editDescription);
        editCurrentlyReading= findViewById(R.id.editCurrentlyReading);

        editImage=findViewById(R.id.editBookImageView);

        updateBtn=findViewById(R.id.updateEditChanges);
        cancelBtn=findViewById(R.id.cancelEditChanges);

        final Bundle extras = getIntent().getExtras();

        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child(firebaseAuth.getUid()).child("AllBooks").child(extras.getString("BookID"));

        Glide.with(getApplicationContext()).load(extras.getString("Image")).into(editImage);

        editTitle.setText(extras.getString("Title"));
        editAuthor.setText(extras.getString("Author"));
        editDescription.setText(extras.getString("Description"));
        editGenre.setText(extras.getString("Genre"));
        editISBN.setText(extras.getString("ISBN"));
        editCurrentlyReading.setText(extras.getString("CurrentReadBool"));

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(editCurrentlyReading.getText().toString().equals("Yes") || editCurrentlyReading.getText().toString().equals("No"))){
                    //Toast.makeText(getApplicationContext(),editCurrentlyReading.getText(),Toast.LENGTH_SHORT).show();
                    editCurrentlyReading.setError("Enter either Yes or No");
                    return;
                }

                try {
                    if (!editTitle.getText().toString().equals(extras.getString("Title")))
                        databaseReference.child("bookTitle").setValue(editTitle.getText().toString());
                    if (!editAuthor.getText().toString().equals(extras.getString("Author")))
                        databaseReference.child("bookAuthor").setValue(editAuthor.getText().toString());
                    if (!editDescription.getText().toString().equals(extras.getString("Description")))
                        databaseReference.child("bookDescription").setValue(editDescription.getText().toString());
                    if (!editGenre.getText().toString().equals(extras.getString("Genre")))
                        databaseReference.child("bookGenre").setValue(editGenre.getText().toString());
                    if (!editISBN.getText().toString().equals(extras.getString("ISBN")))
                        databaseReference.child("bookISBN").setValue(editISBN.getText().toString());
                    if (!editCurrentlyReading.getText().toString().equals(extras.getString("CurrentReadBool")))
                        databaseReference.child("currentlyReadingBool").setValue(editCurrentlyReading.getText().toString());
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(getApplicationContext(),"Update Successful",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),ViewBooks.class);
                startActivity(intent);
            }
        });


    }
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), ViewBooks.class);
        startActivity(intent);
    }
}