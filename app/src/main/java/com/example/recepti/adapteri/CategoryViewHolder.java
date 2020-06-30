package com.example.recepti.adapteri;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recepti.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    CircleImageView catImage;
    TextView catTitle;
    OnRecipeListener listener;

    public CategoryViewHolder(@NonNull View itemView, OnRecipeListener listener) {
        super(itemView);
        this.listener = listener;
        this.catImage = itemView.findViewById(R.id.category_image);
        this.catTitle = itemView.findViewById(R.id.category_title);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
    listener.onCategoryClick(catTitle.getText().toString());
    }
}
