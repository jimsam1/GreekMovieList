package com.example.greekmovielist;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Movie> movies;
    private List<String> movieTitles = new ArrayList<>();
    private List<String> movieDetails = new ArrayList<>();
    private List<Integer> moviePosters = new ArrayList<>();
    private Context context;

    public RecyclerAdapter(List<Movie> movies, Context context){
        this.movies = movies;
        this.context = context;
        for(int i=0; i<this.movies.size(); i++){
            this.movieTitles.add(this.movies.get(i).getTitle());
            this.moviePosters.add(this.context.getResources().
                    getIdentifier(this.movies.get(i).getImageName(), "drawable", this.context.getPackageName()));
            this.movieDetails.add(this.movies.get(i).getDescription());
        }
    }

    //Class that holds the items to be displayed (Views in card_layout)
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemTitle;
        TextView itemDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemDetail = itemView.findViewById(R.id.item_detail);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent i = new Intent(v.getContext(), MovieDetailsActivity.class);
                    i.putExtra("movieid", movies.get(position).getId());
                    context.startActivity(i);
                }
            });
        }
    }

    //Methods that must be implemented for a RecyclerView.Adapter
    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        holder.itemTitle.setText(movieTitles.get(position));
        holder.itemDetail.setText(movieDetails.get(position));
        holder.itemImage.setImageResource(moviePosters.get(position));
    }

    @Override
    public int getItemCount() {
        return movieTitles.size();
    }
}
