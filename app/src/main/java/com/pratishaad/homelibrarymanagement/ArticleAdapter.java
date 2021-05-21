package com.pratishaad.homelibrarymanagement;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    List<Book>articleLists;
    Context ct;

    public ArticleAdapter(List<Book> articleLists, Context ct) {
        this.articleLists = articleLists;
        this.ct = ct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        ArticleList articleList=articleLists.get(position);
        Book articleList=articleLists.get(position);
        Glide.with(ct)
                .load(articleList.getImageFirebaseURI())
                .into(holder.articleimg);

        holder.articlename.setText("Title: "+articleList.getBookTitle());
        holder.articledetails.setText("By: "+articleList.getBookAuthor()+
                "\nGenre: "+articleList.getBookGenre()+
                "\nISBN: "+articleList.getBookISBN());
        holder.articlelent.setTextSize(14);

        holder.articlelent.setText(articleList.getLendBookBool());
        String a= (String) holder.articlelent.getText();
        try {
            if (a.equals("This book has not been lent") || a.isEmpty()){
                holder.articlelent.setText("");
            }
            else {

                holder.articlelent.setText("Book lent to "+articleList.getLendLendeeName());
                holder.articlelent.setBackgroundColor(Color.RED);
                holder.articlelent.setTextColor(Color.WHITE);
                holder.articlelent.setTextSize(16);
            }
        }
        catch (Exception e){
            Toast.makeText(ct.getApplicationContext(), e.toString(),Toast.LENGTH_SHORT).show();
        }

        /*if(articleList.getLendBookBool().equals("This book has not been lent")){
            holder.articlelent.setText("Book Lent to "+articleList.getLendLendeeName());
            /*holder.articlelent.setHighlightColor(Color.RED);
            holder.articlelent.setTextColor(Color.WHITE);
        }
        else{
            holder.articlelent.setText("Book not lent");
        }*/
    }

    @Override
    public int getItemCount() {
        return articleLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView articleimg;
        TextView articlename;
        TextView articledetails;
        TextView articlelent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            articleimg=itemView.findViewById(R.id.lend_book_image);
            articlename=itemView.findViewById(R.id.lend_book_name);
            articledetails=itemView.findViewById(R.id.lend_book_details);
            articlelent=itemView.findViewById(R.id.article_lent_to);

        }
    }
}