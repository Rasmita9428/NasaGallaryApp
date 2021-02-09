package com.example.nasagalleryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(InternetConnection.checkConnection(MainActivity.this)==true) {
            gridView = findViewById(R.id.gridviewgallery);
            String jsonFileString = Utils.getJsonFromAssets(getApplicationContext(), "data.json");

            Gson gson = new Gson();
            Type listUserType = new TypeToken<List<User>>() {
            }.getType();

            List<User> users = gson.fromJson(jsonFileString, listUserType);

            gridView.setAdapter(new ImageAdapter(this, users));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent,
                                        View v, int position, long id) {
                    // Send intent to SingleViewActivity
                    Intent i = new Intent(getApplicationContext(), SingleImageScreen.class);
                    // Pass image index
                    i.putExtra("id", position);
                    startActivity(i);
                }
            });
        }else{
            Toast.makeText(MainActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }
    }

    public static class ImageAdapter extends BaseAdapter {
        public Context context;
        List<User> users;
        LayoutInflater inflter;

        public ImageAdapter(Context context, List<User> users) {
            this.users=users;
            this.context=context;
            inflter = (LayoutInflater.from(context));

        }

        @Override
        public int getCount() {
            return users.size();
        }

        @Override
        public Object getItem(int position) {
            return users.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            view = inflter.inflate(R.layout.customlist, null); // inflate the layout
            ImageView icon = (ImageView) view.findViewById(R.id.icon); // get the reference of ImageView
            TextView text = (TextView) view.findViewById(R.id.text); // get the reference of ImageView
            text.setText(users.get(position).getTitle());
            Glide.with(context)
                    .load(users.get(position).getUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .centerCrop()
                    .into(icon);
            return view;

        }
    }

}