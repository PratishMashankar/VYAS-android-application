package com.pratishaad.homelibrarymanagement.bibliophilecompanion;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.camera2.TotalCaptureResult;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pratishaad.homelibrarymanagement.Book;
import com.pratishaad.homelibrarymanagement.R;
import com.pratishaad.homelibrarymanagement.viewbooks.ArticleAdapter;

import java.util.List;

public class HighlightAdapter extends RecyclerView.Adapter<HighlightAdapter.ViewHolder> {

    List<Highlights>articleLists;
    Context ct;
    OnRecyclerViewItemClickListener monRecyclerViewItemClickListener;
    OnRecyclerViewItemLongClickListener monRecyclerViewItemLongClickListener;
    //buttonOnClickListener buttonOnClickListener;

    public HighlightAdapter(){}

    public HighlightAdapter(List<Highlights> articleLists, Context ct, OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener, OnRecyclerViewItemLongClickListener monRecyclerViewItemLongClickListener) {
        this.articleLists = articleLists;
        this.ct = ct;
        this.monRecyclerViewItemClickListener=mOnRecyclerViewItemClickListener;
        this.monRecyclerViewItemLongClickListener=monRecyclerViewItemLongClickListener;
    }

    public HighlightAdapter(List<Highlights> articleLists, Context applicationContext) {
        this.articleLists = articleLists;
        this.ct = applicationContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_highlight_list,parent,false);
        return new ViewHolder(view, monRecyclerViewItemClickListener, monRecyclerViewItemLongClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Highlights articleList=articleLists.get(position);


        holder.highlightURL.setText("From webpage: "+articleList.getUrl());
        holder.highlightText.setText(articleList.getHighlight());

    }

    @Override
    public int getItemCount() {
        return articleLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
        TextView highlightURL;
        TextView highlightText;
        OnRecyclerViewItemLongClickListener onRecyclerViewItemLongClickListener;



        public ViewHolder(@NonNull View itemView,
                          OnRecyclerViewItemClickListener onRecyclerViewItemClickListener,
                          OnRecyclerViewItemLongClickListener monRecyclerViewItemLongClickListener){
            super(itemView);
            highlightURL = itemView.findViewById(R.id.link_details);
            highlightText = itemView.findViewById(R.id.highlight_details);

            this.onRecyclerViewItemClickListener=onRecyclerViewItemClickListener;

            this.onRecyclerViewItemLongClickListener=monRecyclerViewItemLongClickListener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }


        @Override
        public void onClick(View view) {
            onRecyclerViewItemClickListener.onRecyclerViewItemClicked(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            onRecyclerViewItemLongClickListener.onRecyclerViewItemLongClicked(getAdapterPosition());
            return true;
        }
    }

    public interface OnRecyclerViewItemClickListener
    {
        //public void onRecyclerViewItemClicked(View v, int position);
        void onRecyclerViewItemClicked(int position);
    }
    public interface OnRecyclerViewItemLongClickListener
    {
        //public void onRecyclerViewItemClicked(View v, int position);
        void onRecyclerViewItemLongClicked(int position);
    }


    }

