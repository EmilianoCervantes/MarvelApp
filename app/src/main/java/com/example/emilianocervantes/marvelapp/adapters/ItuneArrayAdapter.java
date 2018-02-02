package com.example.emilianocervantes.marvelapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.emilianocervantes.marvelapp.R;
import com.example.emilianocervantes.marvelapp.pojo.Itune;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ovman on 26/01/2018.
 */

//Esta clase toma los datos y reconstruye la lista
public class ItuneArrayAdapter extends ArrayAdapter<Itune> {
    //Give a collection from type Itune
    private ArrayList<Itune> arrayList;

    public ItuneArrayAdapter(Context context, int resource, List<Itune> objects) {
        super(context, resource, objects);
        arrayList = (ArrayList<Itune>)objects;
    }

    //Rescatar datos en el json

    //Regresa un renglon a la vez con los datos que tenemos
    //n datos = n rengloes
    //Position es donde estamos, convertView es para crear el renglon
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Primer forma, mandar al constructor la collection
        /*Itune itune = arrayList.get(position);
        //
        //Itune itune = getItem(position);
        if (convertView == null){
            //LayoutInflater reconstruye una vista
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.marvel_layout,parent, false);
        }

        TextView collectioName = (TextView)convertView.findViewById(R.id.CollectionName);
        TextView trackName = (TextView)convertView.findViewById(R.id.TrackName);
        TextView trackPrice = (TextView)convertView.findViewById(R.id.TrackPrice);
        collectioName.setText(itune.collectionName);
        trackName.setText(itune.trackName);
        trackPrice.setText(itune.trackPrice+"");

        //Return ese renglon
        return convertView;
        */
        return null;
    }
}
