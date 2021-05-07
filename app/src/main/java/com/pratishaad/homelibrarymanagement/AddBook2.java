package com.pratishaad.homelibrarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;

public class AddBook2 extends AppCompatActivity {

    EditText title,author,ifbn,desc;
    ImageView coverimg,titleimg,authimg,ifbnimg,descimg;
    Spinner genre;
    Button addbook, addanotherbook, clearbtn;
    final int REQUEST_IMAGE_CAPTURE = 1;
    //DatabaseReference
    DatabaseReference databaseBooks;
    StorageReference storageReference;
    String userID;
    Uri FilePathUri;
    String TaskSnapShot;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book2);

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
        databaseBooks= FirebaseDatabase.getInstance().getReference();//for realtime database reference
        storageReference = FirebaseStorage.getInstance().getReference("Images");//storage

        if(checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CAMERA},101);
        }

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

//        addbook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addbook();
//            }
//        });
//    }
//
//    private void addbook() {
//        Log.println(Log.INFO,"","in Add Book");
//    }
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
            FilePathUri = data.getData();//newly added
            FilePathUri = data.getData();//newly added
            System.out.println("data added");
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

    //ADD BOOK TO FIREBASE and a lot of newly added
    protected void addBook(){
        Toast.makeText(getApplicationContext(), "Checkpoint 0", Toast.LENGTH_SHORT).show();
        String mTitle=title.getText().toString().trim();
        String mAuthor=author.getText().toString().trim();
        String mISBN=ifbn.getText().toString().trim();
        String mDescription=desc.getText().toString().trim();
        String mGenre=genre.getSelectedItem().toString();
//        String mCoverImageName="Error displaying image";
        String uid=fAuth.getUid();
        String books="AllBooks";//same name in the Firebase RealTime Database
//        databaseBooks.child(uid).push();
        Toast.makeText(getApplicationContext(), FilePathUri.toString(), Toast.LENGTH_SHORT).show();

        if(!(TextUtils.isEmpty(mTitle)) && FilePathUri != null){

            Toast.makeText(getApplicationContext(), "Checkpoint 1", Toast.LENGTH_SHORT).show();
            StorageReference image= storageReference.child("Images/");
            image.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            TaskSnapShot=taskSnapshot.getUploadSessionUri().toString();
                            String TempImageName = title.getText().toString().trim();
//                            progressDialog.dismiss();
//                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
//                            @SuppressWarnings("VisibleForTests")
//                            Book imageUploadInfo = new Book(TempImageName, taskSnapshot.getUploadSessionUri().toString());
//                            String ImageUploadId = databaseBooks.child(fAuth.getUid()).push().getKey();
//                            databaseBooks.child(fAuth.getUid()).child("AllBooks").child(databaseBooks.child(fAuth.getUid()).push().getKey()).child(ImageUploadId).setValue(imageUploadInfo);
//                            databaseBooks.child(ImageUploadId).setValue(imageUploadInfo);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Nai aara bhaiyya", Toast.LENGTH_SHORT).show();
                        }
                    });

            String bookID = databaseBooks.child(uid).push().getKey();
            String Images="Images";
//            databaseBooks.child(uid).child(books).child(bookID).push();
            String ImageId=databaseBooks.child(uid).child(books).child(bookID).push().getKey();
            Book book = new Book(bookID,mTitle,mAuthor,mISBN,mDescription,mGenre, TaskSnapShot);
            databaseBooks.child(uid).child(books).child(bookID).setValue(book);
//            databaseBooks.child(uid).child(books).child(bookID).child(Images).setValue()
//            databaseBooks.child(uid).child(books).child(bookID).push().getKey();

            Toast.makeText(getApplicationContext(),"This Book has been added ",Toast.LENGTH_SHORT).show();
        }else{
            if(TextUtils.isEmpty(mTitle))
                Toast.makeText(getApplicationContext(),"Title is mandatory",Toast.LENGTH_SHORT).show();
        }


//        if (FilePathUri != null) {
//
////            progressDialog.setTitle("Image is Uploading...");
////            progressDialog.show();
//            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
//            storageReference2.putFile(FilePathUri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                            String TempImageName = title.getText().toString().trim();
////                            progressDialog.dismiss();
//                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
////                            @SuppressWarnings("VisibleForTests")
////                            uploadinfo imageUploadInfo = new uploadinfo(TempImageName, taskSnapshot.getUploadSessionUri().toString());
////                            String ImageUploadId = databaseBooks.push().getKey();
////                            databaseBooks.child(ImageUploadId).setValue(imageUploadInfo);
//                        }
//                    });
//        }
    }



}
