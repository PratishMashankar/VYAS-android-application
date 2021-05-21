package com.pratishaad.homelibrarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LendBookDetails extends AppCompatActivity {

    TextView lendTitle,lendAuthor,textGiveBook,textReceiveBook;
    ImageView lendCoverImage;
    EditText lendeeName, lendGiveDate, lendReceiveDate;
    Button confirmButtonLend;
    FirebaseAuth fAuth;
    DatabaseReference databaseRef;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend_book_details);
        lendTitle=(TextView)findViewById(R.id.lendBookName);
        lendAuthor=(TextView)findViewById(R.id.lendAuthorName);
        textGiveBook=(TextView)findViewById(R.id.textGiveDate);
        textReceiveBook=(TextView)findViewById(R.id.textReceiveDate);
        lendCoverImage=(ImageView)findViewById(R.id.lendImageView);
        lendeeName=(EditText)findViewById(R.id.lendeeName);
        lendGiveDate=(EditText)findViewById(R.id.lendGiveDate);
        lendReceiveDate=(EditText)findViewById(R.id.lendReceiveDate);
        confirmButtonLend=(Button)findViewById(R.id.confirmButtonLend);

        final Bundle extras = getIntent().getExtras();

        fAuth = FirebaseAuth.getInstance();
        if(extras!=null) databaseRef= FirebaseDatabase.getInstance().getReference().child(fAuth.getUid()).child("AllBooks").child(extras.getString("LendBookID"));
        lendTitle.setText(extras.getString("TitleLend").trim().replace("\n"," "));
        lendAuthor.setText(extras.getString("AuthorLend").trim().replace("\n"," "));
        Glide.with(getApplicationContext()).load(extras.getString("ImageLend")).into(lendCoverImage);

        confirmButtonLend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mLendee=lendeeName.getText().toString();
                String mGiveDate=lendGiveDate.getText().toString();
                String mReceiveDate=lendReceiveDate.getText().toString();
                if(!mLendee.isEmpty() && !mGiveDate.isEmpty() && !mReceiveDate.isEmpty()){
                    databaseRef.child("lendBookBool").setValue("Yes");
                    databaseRef.child("lendLendeeName").setValue(mLendee);
                    databaseRef.child("lendGiveDate").setValue(mGiveDate);
                    databaseRef.child("lendReceiveDate").setValue(mReceiveDate);
                    Toast.makeText(getApplicationContext(), "The book has been lent to "+mLendee, Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(),ViewBooks.class);
                    startActivity(intent);
                }
                else if(mLendee.isEmpty()) Toast.makeText(getApplicationContext(),"Enter Lendee Name",Toast.LENGTH_SHORT).show();
                else if(mGiveDate.isEmpty()) Toast.makeText(getApplicationContext(),"Enter Give Date",Toast.LENGTH_SHORT).show();
                else if(mReceiveDate.isEmpty()) Toast.makeText(getApplicationContext(),"Enter Receive Date",Toast.LENGTH_SHORT).show();
                else Toast.makeText(getApplicationContext(),"Book Failed To Lend",Toast.LENGTH_SHORT).show();
            }
        });

    }
}


        /*//Calendar selection for lending date
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        textGiveBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpd1 = new DatePickerDialog(
                        getApplicationContext(),android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        setListener,year,month,day);
                dpd1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dpd1.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=dayOfMonth+"/"+month+"/"+year;
                lendGiveDate.setText(date);
            }
        };
       *//* lendGiveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpd1 = new DatePickerDialog(
                        getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        lendGiveDate.setText(date);
                    }
                }, year, month, day);
                dpd1.show();
            }
        });*//*

        //Calendar selection for receiving date
        textReceiveBook.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   DatePickerDialog dpd2 = new DatePickerDialog(
                                                           getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
                                                       @Override
                                                       public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                                           month = month + 1;
                                                           String date = day + "/" + month + "/" + year;
                                                           lendReceiveDate.setText(date);
                                                       }
                                                   }, year, month, day);
                                                   dpd2.show();
                                               }
                                           });


            *//*final Calendar myCalendar = Calendar.getInstance();
            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String myFormat = "dd/mm/yy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    lendGiveDate.setText(sdf.format(myCalendar.getTime()));
                }
            };
            lendGiveDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new DatePickerDialog(getApplicationContext(), date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

        //Calendar selection for receiving date
        final Calendar myCalendar2 = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat2 = "dd/mm/yy"; //In which you need put here
                SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.US);
                lendGiveDate.setText(sdf2.format(myCalendar2.getTime()));
            }
        };
        lendReceiveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getApplicationContext(), date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });*//*
*/


