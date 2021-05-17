package com.pratishaad.homelibrarymanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

        holder.articlename.setText("Title: "+articleList.getBookTitle() +"\n"+"By: "+articleList.getBookAuthor());

    }

    @Override
    public int getItemCount() {
        return articleLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView articleimg;
        TextView articlename;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            articleimg=itemView.findViewById(R.id.article_image);
            articlename=itemView.findViewById(R.id.article_name);
        }
    }
}