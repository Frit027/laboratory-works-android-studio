package com.mirea.lab5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class ListAdapter extends PagedListAdapter<String, ListAdapter.ViewHolder> {

    public ListAdapter(DiffUtil.ItemCallback<String> diffUtilCallback) {
        super(diffUtilCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scrolling_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListAdapter.ViewHolder holder, int position) {
        final String url = getItem(position);
        Glide.with(holder.itemView.getContext())
                .load(url)
                .into(holder.imageView);

        holder.buttonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(v.getContext());
                databaseHelper.insert(url);
                Toast.makeText(v.getContext(), "Изображение добавлено в понравившиеся фотографии.", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        holder.buttonDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Вам не понравилось изображение.", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final ImageButton buttonLike;
        final ImageButton buttonDislike;

        ViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.image);
            buttonLike = view.findViewById(R.id.buttonLike);
            buttonDislike = view.findViewById(R.id.buttonDislike);
        }
    }
}
