package com.mirea.lab2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<HashMap<String, String>> technology;

    DataAdapter(Context context,  ArrayList<HashMap<String, String>> technology) {
        this.technology = technology;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        String urlImage = "https://raw.githubusercontent.com/wesleywerner/ancient-tech/" +
                "02decf875616dd9692b31658d92e64a20d99f816/src/images/tech/";

        HashMap<String, String> obj = technology.get(position);

        String url = urlImage + obj.get("graphic");

        Glide.with(holder.itemView.getContext())
                .load(url)
                .placeholder(R.drawable.infinite)
                .into(holder.imageView);

        holder.nameView.setText(obj.get("name"));
    }

    @Override
    public int getItemCount() {
        return technology.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameView;

        ViewHolder(View view){
            super(view);

            imageView = view.findViewById(R.id.image);
            nameView = view.findViewById(R.id.name);
        }
    }
}
