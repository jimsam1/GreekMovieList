package com.example.greekmovielist;

import android.content.Context;
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
    //Variables storing data to display for this example
    /*private final String[] titles = {"Η Αλίκη στο Ναυτικό", "Ο Μπακαλόγατος", "Ελληνικη Ταινία 3", "Ελληνική Ταινία 4",
            "Ελληνικη Ταινία 5", "Ελληνικη Ταινία 6", "Ελληνικη Ταινία 7", "Ελληνικη Ταινία 8"};
    private final String[] details = {"Η Αλικη στο παει στο Ναυτικο?", "Ενα παντοπωλείο.", "Ελληνικη Ταινία 3 details",
            "Ελληνική Ταινία 4 details", "Ελληνική Ταινία 5 details", "Ελληνική Ταινία 6 details", "Ελληνική Ταινία 7 details",
            "Ελληνική Ταινία 8 details"};
    private final int[] images = { R.drawable.android_image_1, R.drawable.android_image_2,
            R.drawable.android_image_3, R.drawable.android_image_4, R.drawable.android_image_5,
            R.drawable.android_image_6, R.drawable.android_image_7, R.drawable.android_image_8 };
     */

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
            //PROBLEM
            Log.i("Testing", this.movies.get(i).getImageName());
            this.moviePosters.add(this.context.getResources().
                    getIdentifier(this.movies.get(i).getImageName() , "drawable", this.context.getPackageName()));
            Log.i("Testing", String.valueOf(this.moviePosters));
            this.movieDetails.add(this.movies.get(i).getDescription());
        }
    }

    //Class that holds the items to be displayed (Views in card_layout)
    static class ViewHolder extends RecyclerView.ViewHolder {
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

                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG).show();
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
