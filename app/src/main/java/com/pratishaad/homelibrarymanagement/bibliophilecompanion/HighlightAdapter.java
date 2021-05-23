package com.pratishaad.homelibrarymanagement.bibliophilecompanion;

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
import com.pratishaad.homelibrarymanagement.Book;
import com.pratishaad.homelibrarymanagement.R;

import java.util.List;

public class HighlightAdapter extends RecyclerView.Adapter<HighlightAdapter.ViewHolder> {

    List<Highlights>articleLists;
    Context ct;
    OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;

    public HighlightAdapter(){}

    public HighlightAdapter(List<Highlights> articleLists, Context ct, OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener) {
        this.articleLists = articleLists;
        this.ct = ct;
        this.mOnRecyclerViewItemClickListener=mOnRecyclerViewItemClickListener;
    }

    public HighlightAdapter(List<Highlights> articleLists, Context applicationContext) {
        this.articleLists = articleLists;
        this.ct = ct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_highlight_list,parent,false);
        return new ViewHolder(view, mOnRecyclerViewItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Highlights articleList=articleLists.get(position);

        holder.highlightURL.setText("From webpage: "+articleList.getUrl());
        holder.highlightText.setText(articleList.getHighlight());
    }

    @Override
    public int getItemCount() {
        return articleLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView highlightURL;
        TextView highlightText;

        OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;


        public ViewHolder(@NonNull View itemView, OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener) {
            super(itemView);
            highlightURL=itemView.findViewById(R.id.link_details);
            highlightText=itemView.findViewById(R.id.highlight_details);
            this.onRecyclerViewItemClickListener=onRecyclerViewItemClickListener;

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            onRecyclerViewItemClickListener.onRecyclerViewItemClicked(getAdapterPosition());
        }
    }

    public interface OnRecyclerViewItemClickListener
    {
        void onRecyclerViewItemClicked(int position);
    }
}