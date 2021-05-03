package com.pratishaad.homelibrarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DirectAction;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;

import java.util.List;

public class AddBook extends AppCompatActivity {

    EditText title,author,ifbn,desc;
    ImageView coverimg,titleimg,authimg,ifbnimg,descimg;
    Spinner genre;
    Button addbook, addanotherbook, clearbtn;
    final int REQUEST_IMAGE_CAPTURE = 1;
    //DatabaseReference
    DatabaseReference databaseBooks;
    String userID;

    FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        title=(EditText)findViewById(R.id.title);
        author=(EditText)findViewById(R.id.author);
        ifbn=(EditText)findViewById(R.id.ifbn);
        desc=(EditText)findViewById(R.id.desc);

        coverimg=(ImageView)findViewById(R.id.coverimg);
        titleimg=(ImageView)findViewById(R.id.titleimg);
        authimg=(ImageView)findViewById(R.id.authimg);
        ifbnimg=(ImageView)findViewById(R.id.ifbnimg);
        descimg=(ImageView)findViewById(R.id.descimg);

        genre=(Spinner)findViewById(R.id.genre);

        addbook=(Button)findViewById(R.id.addbook);
        clearbtn=(Button)findViewById(R.id.clear);

        coverimg.setImageResource(R.drawable.camera);
        titleimg.setImageResource(R.drawable.camera);
        authimg.setImageResource(R.drawable.camera);
        ifbnimg.setImageResource(R.drawable.camera);
        descimg.setImageResource(R.drawable.camera);

        //authenticated user
        fAuth = FirebaseAuth.getInstance();



        //getting user ID under which books are to be added
        databaseBooks= FirebaseDatabase.getInstance().getReference();



        //check app level permission given for camera
        if(checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CAMERA},101);
        }

        //Clear Button
        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title.setText("");
                author.setText("");
                ifbn.setText("");
                desc.setText("");
                coverimg.setImageResource(R.drawable.camera);
                titleimg.setImageResource(R.drawable.camera);
                authimg.setImageResource(R.drawable.camera);
                ifbnimg.setImageResource(R.drawable.camera);
                descimg.setImageResource(R.drawable.camera);
            }
        });

        //Add Book to Firebase Database
        addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBook();
            }
        });
    }


    //IMAGE TO TEXT CODE
    public void captureImage(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if((view.toString()).equals(findViewById(R.id.coverimg).toString())) startActivityForResult(intent, 105);
        else if((view.toString()).equals(findViewById(R.id.titleimg).toString())) startActivityForResult(intent, 101);
        else if((view.toString()).equals(findViewById(R.id.authimg).toString())) startActivityForResult(intent, 102);
        else if((view.toString()).equals(findViewById(R.id.ifbnimg).toString())) startActivityForResult(intent, 103);
        else if((view.toString()).equals(findViewById(R.id.descimg).toString())) startActivityForResult(intent, 104);
    }
    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        //extract image from bundle
        Bitmap bitmap = (Bitmap) bundle.get("data");
        //set image to image view
        if(requestCode==105) {
            coverimg.setImageBitmap(bitmap);
        }
        else if(requestCode==101) titleimg.setImageBitmap(bitmap);
        else if(requestCode==102) authimg.setImageBitmap(bitmap);
        else if(requestCode==103) ifbnimg.setImageBitmap(bitmap);
        else if(requestCode==104) descimg.setImageBitmap(bitmap);
        //process the image to extract image
        InputImage image = InputImage.fromBitmap(bitmap, 0);
        TextRecognizer recognizer = TextRecognition.getClient();
        Task<Text> result =
                recognizer
                        .process(image)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text visionText) {
                                //Toast.makeText(getApplicationContext(),visionText.getText(),Toast.LENGTH_SHORT).show();
                                if(requestCode==101) title.setText(visionText.getText());
                                else if(requestCode==102) author.setText(visionText.getText());
                                else if(requestCode==103) ifbn.setText(visionText.getText());
                                else if(requestCode==104) desc.setText(visionText.getText());

                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(),"Error!",Toast.LENGTH_SHORT).show();
                                    }
                                });
    }

    //DADD BOOK TO FIREBASE
    protected void addBook(){
        String mTitle=title.getText().toString().trim();
        String mAuthor=author.getText().toString().trim();
        String mISBN=ifbn.getText().toString().trim();
        String mDescription=desc.getText().toString().trim();
        String mGenre=genre.getSelectedItem().toString();
        String mCoverImageName="Error displaying image";

        if(!TextUtils.isEmpty(mTitle)){
            String uid=fAuth.getUid();
            String books="AllBooks";
            databaseBooks.child(uid).push();
            String bookID = databaseBooks.child(uid).push().getKey();
            Book book = new Book(bookID,mTitle,mAuthor,mISBN,mDescription,mGenre,mCoverImageName);
            databaseBooks.child(uid).child(books).child(bookID).setValue(book);
            Toast.makeText(getApplicationContext(),"This Book has been added ",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Title is mandatory",Toast.LENGTH_SHORT).show();
        }
    }



}