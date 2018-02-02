package com.example.emilianocervantes.marvelapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.emilianocervantes.marvelapp.R;
import com.example.emilianocervantes.marvelapp.VolleySingleton;
import com.example.emilianocervantes.marvelapp.pojo.MarvelDude;

import java.util.List;

/**
 * Created by ovman on 02/02/2018.
 */

public class MarvelAdapter extends ArrayAdapter<MarvelDude> {
    private Context contxt;
    public MarvelAdapter( Context context, int resource, List<MarvelDude> objects) {
        super(context, resource, objects);
        this.contxt = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //View view = null;
        MarvelDude marvelDude = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(contxt).inflate(R.layout.marvel_layout,parent,false);
        }

        TextView textView = (TextView)convertView.findViewById(R.id.characterName);
        NetworkImageView networkImageView = (NetworkImageView)convertView.findViewById(R.id.imageCharacter);
        textView.setText(marvelDude.name);

        //Lanzar otro request a volley para que agarre el personaje del servidor
        RequestQueue requestQueue = VolleySingleton.getInstance(contxt).getRequestQueue();
        ImageLoader imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String,Bitmap> cache = new LruCache<String, Bitmap>(10);

            @Override
            public Bitmap getBitmap(String url) {
                // Vamos por el bitmap (png, jpg, etc)
                // Bitmap lo puedo renderar y reutilizar
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });

        //Como esto se ejecuta y nos va a lanzar una imagen por renglon
        networkImageView.setImageUrl(marvelDude.url, imageLoader);

        //return view;
        return convertView;
    }
}
