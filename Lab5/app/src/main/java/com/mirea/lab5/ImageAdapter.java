package com.mirea.lab5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImageAdapter extends ArrayAdapter<String> {
    private LayoutInflater inflater;
    private int layout;
    private List<String> images;

    public ImageAdapter(Context context, int resource, List<String> images) {
        super(context, resource, images);
        this.images = images;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String url = images.get(position);

        Glide.with(getContext())
                .load(url)
                .into(viewHolder.imageView);

        return convertView;
    }

    private class ViewHolder {
        final ImageView imageView;

        ViewHolder(View view){
            imageView = view.findViewById(R.id.imageInList);
        }
    }
}
