package com.example.recepti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    public void setContentView(int layoutResID) {
        ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout frameLayout = constraintLayout.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        progressBar = constraintLayout.findViewById(R.id.progress_bar);
        
        super.setContentView(layoutResID);
    }

    protected  void showProgressBar(boolean visibility){
        progressBar.setVisibility(visibility ? View.VISIBLE: View.GONE);
    }
}