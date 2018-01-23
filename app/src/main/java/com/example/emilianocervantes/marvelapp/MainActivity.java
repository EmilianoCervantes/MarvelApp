package com.example.emilianocervantes.marvelapp;

import android.app.Activity;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class MainActivity extends ListActivity {

    private ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Json json = new Json();
        String jsonString = json.serviceCall("https://itunes.apple.com/search?term=maroon+5");
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new ArrayList<String>());

        arrayAdapter.add(jsonString);
        setListAdapter(arrayAdapter);
    }

    public class ProcesaJSON extends AsyncTask<Void,Integer,ArrayList<String>>{
        private ArrayAdapter<String> arrayAdapter;
        public ProcesaJSON(ArrayAdapter<String> adapter){
            this.arrayAdapter = adapter;
        }

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            return null;
        }
    }
}
