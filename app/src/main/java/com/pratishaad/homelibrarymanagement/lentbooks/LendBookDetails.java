package com.pratishaad.homelibrarymanagement.lentbooks;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.pratishaad.homelibrarymanagement.MainActivity;
import com.pratishaad.homelibrarymanagement.R;
import com.pratishaad.homelibrarymanagement.viewbooks.ViewBooks;

import java.util.Calendar;

public class LendBookDetails extends AppCompatActivity {

    TextView lendTitle,lendAuthor,textGiveBook,textReceiveBook, lendGiveDate, lendReceiveDate;
    ImageView lendCoverImage;
    EditText lendeeName;
    Button confirmButtonLend;
    FirebaseAuth fAuth;
    DatabaseReference databaseRef;
    int year,month, day;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend_book_details);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);

        lendTitle=(TextView)findViewById(R.id.lendBookName);
        lendAuthor=(TextView)findViewById(R.id.lendAuthorName);
//        textGiveBook=(TextView)findViewById(R.id.textGiveDate);
//        textReceiveBook=(TextView)findViewById(R.id.textReceiveDate);
        lendCoverImage=(ImageView)findViewById(R.id.lendImageView);
        lendeeName=(EditText)findViewById(R.id.lendeeName);
        lendGiveDate=(TextView) findViewById(R.id.lendGiveDate);
        lendReceiveDate=(TextView) findViewById(R.id.lendReceiveDate);
        confirmButtonLend=(Button)findViewById(R.id.confirmButtonLend);

        final Bundle extras = getIntent().getExtras();

        fAuth = FirebaseAuth.getInstance();
        if(extras!=null) databaseRef= FirebaseDatabase.getInstance().getReference().child(fAuth.getUid()).child("AllBooks").child(extras.getString("LendBookID"));
        lendTitle.setText(extras.getString("TitleLend").trim().replace("\n"," "));
        lendAuthor.setText(extras.getString("AuthorLend").trim().replace("\n"," "));
        Glide.with(getApplicationContext()).load(extras.getString("ImageLend")).into(lendCoverImage);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);



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
                    Intent intent= new Intent(getApplicationContext(), ViewBooks.class);
                    startActivity(intent);
                }
                else if(mLendee.isEmpty()) Toast.makeText(getApplicationContext(),"Enter Lendee Name",Toast.LENGTH_SHORT).show();
                else if(mGiveDate.isEmpty()) Toast.makeText(getApplicationContext(),"Enter Give Date",Toast.LENGTH_SHORT).show();
                else if(mReceiveDate.isEmpty()) Toast.makeText(getApplicationContext(),"Enter Receive Date",Toast.LENGTH_SHORT).show();
                else Toast.makeText(getApplicationContext(),"Book Failed To Lend",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @SuppressWarnings("deprecation")
    public void setLendDate(View view) {
        showDialog(998);
    }

    @SuppressWarnings("deprecation")
    public void setReceiveDate(View view)
    {
        showDialog(999);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        this.id=id;
        // TODO Auto-generated method stub
        if (id == 998) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        else if(id == 999)
        {
            return new DatePickerDialog(this, myDateListener,year,month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        if (id==998) {
            lendGiveDate.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
        }
        else if (id == 999)
        {
            lendReceiveDate.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), ViewBooks.class);
        startActivity(intent);
    }

    public void onOptionsItemSelected(View view) {
        try {
            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "vyas.contact.in@gmail.com"));
            startActivity(intent);
        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), "Sorry...You don't have any mail app", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}


