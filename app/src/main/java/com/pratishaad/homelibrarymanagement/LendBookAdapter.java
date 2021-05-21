package com.pratishaad.homelibrarymanagement;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class LendBookAdapter extends RecyclerView.Adapter<LendBookAdapter.ViewHolder> {
    List<Book>bookLentLists;
    Context ct;

    public LendBookAdapter(List<Book> bookLentLists, Context ct) {
        this.bookLentLists = bookLentLists;
        this.ct = ct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.lend_book_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        ArticleList articleList=articleLists.get(position);
        Book bookLentList=bookLentLists.get(position);



        holder.articlelent.setText(bookLentList.getLendBookBool());
        String a= (String) holder.articlelent.getText();
        try {
            if (a.equals("No") || a.isEmpty()){
                holder.ll.setLayoutParams(holder.params);
            }
            else {

                Glide.with(ct)
                        .load(bookLentList.getImageFirebaseURI())
                        .into(holder.articleimg);

                holder.articlename.setText("Title: "+bookLentList.getBookTitle());
                holder.articledetails.setText("By: "+bookLentList.getBookAuthor()+
                        "\nGenre: "+bookLentList.getBookGenre()+
                        "\nISBN: "+bookLentList.getBookISBN());
                holder.articlelent.setTextSize(14);
                holder.articlelent.setText("Book lent to "+bookLentList.getLendLendeeName());
                holder.articlelent.setBackgroundColor(Color.RED);
                holder.articlelent.setTextColor(Color.WHITE);
                holder.articlelent.setTextSize(16);
            }
        }
        catch (Exception e){
            Toast.makeText(ct.getApplicationContext(), e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return bookLentLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView articleimg;
        TextView articlename;
        TextView articledetails;
        TextView articlelent;
        ViewGroup.LayoutParams params;
        LinearLayout ll;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            articleimg=itemView.findViewById(R.id.lend_book_image);
            articlename=itemView.findViewById(R.id.lend_book_name);
            articledetails=itemView.findViewById(R.id.lend_book_details);
            articlelent=itemView.findViewById(R.id.article_lent_to);

            //to hide empty containers of recycler view
            params=new LinearLayout.LayoutParams(0,0);
            ll=itemView.findViewById(R.id.ll);

        }
    }
}