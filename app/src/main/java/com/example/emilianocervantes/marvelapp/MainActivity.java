package com.example.emilianocervantes.marvelapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.emilianocervantes.marvelapp.adapters.ItuneArrayAdapter;
import com.example.emilianocervantes.marvelapp.pojo.Itune;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//ListActivity no tiene elmentos gráficos
//Activity sí
public class MainActivity extends Activity {

    private ListView listView;
    //Adapter es el que dice cómo se va a cargar
    private ArrayAdapter<String> arrayAdapter;
    private ItuneArrayAdapter ituneArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.Lista);

        ituneArrayAdapter = new ItuneArrayAdapter(this, R.layout.itunes_layout, new ArrayList<Itune>());
        listView.setAdapter(ituneArrayAdapter);
        //setListAdapter(arrayAdapter);

        new ProcesaJSON(ituneArrayAdapter).execute("https://itunes.apple.com/search?term=maroon+5");
    }

    public class ProcesaJSON extends AsyncTask<String,Integer,ArrayList<Itune>>{
        private ItuneArrayAdapter adapt;

        public ProcesaJSON(ItuneArrayAdapter adapter){
            this.adapt = adapter;
        }

        @Override
        protected ArrayList<Itune> doInBackground(String... urls) {
            //Se encarga de conectarse al servicio
            Json json = new Json();
            String jsonString = json.serviceCall(urls[0]);
            ArrayList<Itune> arrayList = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i<jsonArray.length(); i++){
                    JSONObject dato = jsonArray.getJSONObject(i);
                    Itune itune = new Itune();
                    //Los names son los del Json
                    itune.collectionName = dato.getString("collectionName");
                    itune.trackName = dato.getString("trackPrice");
                    itune.trackPrice = dato.getDouble("trackPrice");

                    arrayList.add(itune);
                }
            } catch (JSONException e){
                e.printStackTrace();
            }

            //arrayList.add(jsonString);
            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Itune> strings) {
            adapt.clear();
            adapt.addAll(strings);
            adapt.notifyDataSetChanged();
        }
    }
}
