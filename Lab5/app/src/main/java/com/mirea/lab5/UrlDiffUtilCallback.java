package com.mirea.lab5;

import androidx.recyclerview.widget.DiffUtil;

public class UrlDiffUtilCallback extends DiffUtil.ItemCallback<String> {

      @Override
    public boolean areItemsTheSame(String oldItem, String newItem) {
        return oldItem.equals(newItem);
    }

    @Override
    public boolean areContentsTheSame(String oldItem, String newItem) {
        return oldItem.equals(newItem);
    }
}