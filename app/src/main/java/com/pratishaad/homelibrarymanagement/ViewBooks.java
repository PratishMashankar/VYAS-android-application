package com.pratishaad.homelibrarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ViewBooks extends AppCompatActivity {

    RecyclerView rv;
    List<ArticleList> articleLists;
    DatabaseReference databaseReference;
    Book book;

    ImageView image;
    TextView textView;

    FirebaseDatabase firebaseDatabase;
    FirebaseUser uid;
    StorageReference storageReference;
    DatabaseReference first;


    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_books);

        uid = book.getFirebaseUser();
        Log.i("uid",uid.toString());

        rv = findViewById(R.id.rec);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rv.addItemDecoration(decoration);
        articleLists = new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("myImages");
        getImageData();
    }

    private void getImageData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot di : dataSnapshot.getChildren()) {
                    ArticleList articleList = di.getValue(ArticleList.class);
                    articleLists.add(articleList);
                }
                ArticleAdapter adapter = new ArticleAdapter(articleLists, getApplicationContext());
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}

//        databaseReference= FirebaseDatabase.getInstance().getReference();
//
//        storageReference = FirebaseStorage.getInstance().getReference();

//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReferenceFromUrl("\"gs://homelibrarymanagement.appspot.com/myimages/JPEG_20210508_185150_.jpg\"");

//        uid=fAuth.getUid();
//        first=databaseReference.child("image");
//        StorageReference isLandRef=storageReference.child("myimages/JPEG_20210508_193648_.jpg");

//        image=(ImageView)findViewById(R.id.imageView);
//        textView=(TextView)findViewById(R.id.textView2);


//        try {
//            final File localFile = File.createTempFile("images", "jpg");
//            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
//                    image.setImageBitmap(bitmap);
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                }
//            });
//        } catch (IOException e ) {}
//        final long ONE_MEGABYTE = 1024 * 1024;
//        isLandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                Picasso.get().load(bytes[0]).placeholder(R.drawable.camera).into(image);
//                // Data for "images/island.jpg" is returns, use this as needed
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle any errors
//            }
//        });

//        first.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String link = snapshot.getValue(String.class);
//
//                // loading that data into rImage
//                // variable which is ImageView
//                Picasso.get().load(link).placeholder(R.drawable.camera).into(image);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
////                Picasso.get().load("url").placeholder(R.drawable.camera).into(image);
//
//            }
//        });

//        String url="https://i.imgur.com/tGbaZCY.jpg"; // todo: Get URi from firebase instead
//        Picasso.get().load(url).placeholder(R.drawable.camera).into(image);

//    }

//    rImage = findViewById(R.id.rImage);

    // we will get the default FirebaseDatabase instance

//    @Override
//    protected void onStart() {
//        super.onStart();
//        first.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                String link=snapshot.getValue(String.class);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//}



/*
public class MainActivity extends AppCompatActivity {
           RecyclerView rv;
           List<ArticleList>articleLists;
           DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv=findViewById(R.id.rec);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rv.addItemDecoration(decoration);
        articleLists=new ArrayList<>();
  databaseReference= FirebaseDatabase.getInstance().getReference("myImages");
        getImageData();

    }

    private void getImageData() {
      databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              for (DataSnapshot di:dataSnapshot.getChildren()){
                  ArticleList articleList=di.getValue(ArticleList.class);
                  articleLists.add(articleList);
              }
              ArticleAdapter adapter=new ArticleAdapter(articleLists,getApplicationContext());
              rv.setAdapter(adapter);
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });
    }
}
 */