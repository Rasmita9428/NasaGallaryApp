package com.example.nasagalleryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SingleImageScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_image_screen); // Get intent data
        Intent i = getIntent();

        // Selected image id
        int position = i.getExtras().getInt("id");
        ImageView imageView = (ImageView) findViewById(R.id.SingleView);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        TextView tv_desc = (TextView) findViewById(R.id.tv_desc);
        String jsonFileString = Utils.getJsonFromAssets(getApplicationContext(), "data.json");

        Gson gson = new Gson();
        Type listUserType = new TypeToken<List<User>>() { }.getType();

        List<User> users = gson.fromJson(jsonFileString, listUserType);
        tv_title.setText(users.get(position).getTitle());
        tv_desc.setText(users.get(position).getExplanation());

        Glide.with(this)
                .load(users.get(position).getUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(imageView);
    }
}