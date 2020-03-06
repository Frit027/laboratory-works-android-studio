package com.mirea.lab2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    private ArrayList<HashMap<String, String>> list;
    private LayoutInflater mInflater;
    private ViewPager2 viewPager2;

    ViewPagerAdapter(Context context, ArrayList<HashMap<String, String>> data, ViewPager2 viewPager2) {
        this.mInflater = LayoutInflater.from(context);
        this.list = data;
        this.viewPager2 = viewPager2;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap<String, String> obj = list.get(position);

        String urlImage = "https://raw.githubusercontent.com/wesleywerner/ancient-tech/" +
                "02decf875616dd9692b31658d92e64a20d99f816/src/images/tech/";

        String url = urlImage + obj.get("graphic");


        holder.text.setText(obj.get("helptext"));

        Glide.with(holder.itemView.getContext())
                .load(url)
                .placeholder(R.drawable.infinite)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textFragment);
            image = itemView.findViewById(R.id.imageFragment);
        }
    }

}
