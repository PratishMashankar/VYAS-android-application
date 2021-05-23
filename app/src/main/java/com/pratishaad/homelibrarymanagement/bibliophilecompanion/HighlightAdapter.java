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

import java.util.List;

public class HighlightAdapter extends RecyclerView.Adapter<HighlightAdapter.ViewHolder> {

    List<Highlights>articleLists;
    Context ct;
    buttonOnClickListener buttonOnClickListener;

    public HighlightAdapter(){}

    public HighlightAdapter(List<Highlights> articleLists, Context ct, buttonOnClickListener buttonOnClickListener) {
        this.articleLists = articleLists;
        this.ct = ct;
        this.buttonOnClickListener=buttonOnClickListener;
    }

    public HighlightAdapter(List<Highlights> articleLists, Context applicationContext) {
        this.articleLists = articleLists;
        this.ct = applicationContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_highlight_list,parent,false);
        return new ViewHolder(view, buttonOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Highlights articleList=articleLists.get(position);


        holder.highlightURL.setText("From webpage: "+articleList.getUrl());
        holder.highlightText.setText(articleList.getHighlight());

        final String a=holder.highlightURL.toString();

        holder.openHighlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ct, "Gon gon", Toast.LENGTH_SHORT).show();
                try {
                    ct.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com")));
                }
                catch (Exception e)
                {
                    Toast.makeText(ct, "LUL "+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView highlightURL;
        TextView highlightText;
        Button copyHighlight, openHighlight, deleteHighlight;
        buttonOnClickListener mbuttonOnClickListener;

//        OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;


        public ViewHolder(@NonNull View itemView, HighlightAdapter.buttonOnClickListener mbuttonOnClickListener) {
            super(itemView);
            highlightURL=itemView.findViewById(R.id.link_details);
            highlightText=itemView.findViewById(R.id.highlight_details);
            copyHighlight=itemView.findViewById(R.id.copy_highlight);
            openHighlight=itemView.findViewById(R.id.open_highlight);
            deleteHighlight=itemView.findViewById(R.id.delete_highlight);

            mbuttonOnClickListener=(buttonOnClickListener)openHighlight;

            };

        @Override
        public void onClick(View view) {
            this.mbuttonOnClickListener = mbuttonOnClickListener;

        }

//            this.onRecyclerViewItemClickListener=onRecyclerViewItemClickListener;



        }

    public interface buttonOnClickListener
    {
        void onClickOpen(int position);
    }

    }

