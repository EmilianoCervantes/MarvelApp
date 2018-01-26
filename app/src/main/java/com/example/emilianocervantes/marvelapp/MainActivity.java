package com.example.emilianocervantes.marvelapp;

import android.app.Activity;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.Lista);
        /*Json json = new Json();
        String jsonString = json.serviceCall("https://itunes.apple.com/search?term=maroon+5");
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new ArrayList<String>());

        arrayAdapter.add(jsonString);
        setListAdapter(arrayAdapter);*/

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new ArrayList<String>());
        listView.setAdapter(arrayAdapter);
        //setListAdapter(arrayAdapter);

        new ProcesaJSON(arrayAdapter).execute("https://itunes.apple.com/search?term=maroon+5");
    }

    public class ProcesaJSON extends AsyncTask<String,Integer,ArrayList<String>>{
        private ArrayAdapter<String> adapt;
        public ProcesaJSON(ArrayAdapter<String> adapter){
            this.adapt = adapter;
        }

        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            //Se encarga de conectarse al servicio
            Json json = new Json();
            String jsonString = json.serviceCall(urls[0]);
            ArrayList<String> arrayList = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i<jsonArray.length(); i++){
                    JSONObject dato = jsonArray.getJSONObject(i);
                    arrayList.add(dato.getString("collection"));
                }
            } catch (JSONException e){
                e.printStackTrace();
            }

            //arrayList.add(jsonString);
            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            arrayAdapter.clear();
            arrayAdapter.addAll(strings);
            arrayAdapter.notifyDataSetChanged();
        }
    }
}
