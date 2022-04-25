package com.example.greekmovielist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    //Variables storing data to display for this example
    private final String[] titles = {"Η Αλίκη στο Ναυτικό", "Ο Μπακαλόγατος", "Ελληνικη Ταινία 3", "Ελληνική Ταινία 4",
            "Ελληνικη Ταινία 5", "Ελληνικη Ταινία 6", "Ελληνικη Ταινία 7", "Ελληνικη Ταινία 8"};
    private final String[] details = {"Η Αλικη στο παει στο Ναυτικο?", "Ενα παντοπωλείο.", "Ελληνικη Ταινία 3 details",
            "Ελληνική Ταινία 4 details", "Ελληνική Ταινία 5 details", "Ελληνική Ταινία 6 details", "Ελληνική Ταινία 7 details",
            "Ελληνική Ταινία 8 details"};
    private final int[] images = { R.drawable.android_image_1, R.drawable.android_image_2,
            R.drawable.android_image_3, R.drawable.android_image_4, R.drawable.android_image_5,
            R.drawable.android_image_6, R.drawable.android_image_7, R.drawable.android_image_8 };

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
        holder.itemTitle.setText(titles[position]);
        holder.itemDetail.setText(details[position]);
        holder.itemImage.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
