package com.example.emilianocervantes.marvelapp;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.emilianocervantes.marvelapp.adapters.ItuneArrayAdapter;
import com.example.emilianocervantes.marvelapp.pojo.Itune;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

//ListActivity no tiene elmentos gráficos
//Activity sí
public class MainActivity extends Activity {

    private ListView listView;
    //Adapter es el que dice cómo se va a cargar
    private ArrayAdapter<String> adapter;
    private ItuneArrayAdapter ituneArrayAdapter;

    private RequestQueue mQueue;


    String ts = Long.toString(System.currentTimeMillis() / 1000);
    String apikey = "26129e6bc202039ef9aedaab3960cef4";
    String hash = md5(ts + "3620ec531ec97f0f22c3c4c4205427b54aed575f" + "26129e6bc202039ef9aedaab3960cef4");
    final String CHARACTER_BASE_URL = "http://gateway.marvel.com/v1/public/characters";
    final String TIMESTAMP = "ts";
    final String API_KEY = "apikey";
    final String HASH = "hash";
    final String ORDER = "orderBy";
    private int Contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.Lista);
        /*ituneArrayAdapter = new ItuneArrayAdapter(this, R.layout.itunes_layout, new ArrayList<Itune>());
        listView.setAdapter(ituneArrayAdapter);*/

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        listView.setAdapter(adapter);

        mQueue = VolleySingleton.getInstance(this).getRequestQueue();
        //new MarvelJson(adapter).execute();
        jsonMarvel(getMarvelString(), adapter);

        //new ProcesaJSON(ituneArrayAdapter).execute("https://itunes.apple.com/search?term=maroon+5");
    }

    ///Pastebin
    private final String LOG_TAG = "MARVEL";

    private static char[] HEXCodes = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

    private void jsonMarvel(String url, final ArrayAdapter<String> adapter){
        //Limpie el adapatador
        adapter.clear();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Listener positivo
                //Si pudo consultar entra ahi
                try {
                    JSONObject data = response.getJSONObject("data");
                    JSONArray jsonArray = data.getJSONArray("results");
                    for (int i = 0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        adapter.add(jsonObject.getString("name"));
                    }
                    //Actualizar vista
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                /**/
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(request);
    }
    //Obtener los datos de Marvel
    private String getMarvelString(){
        /*String ts = Long.toString(System.currentTimeMillis() / 1000);
        String apikey = "26129e6bc202039ef9aedaab3960cef4";
        String hash = md5(ts + "3620ec531ec97f0f22c3c4c4205427b54aed575f" + "26129e6bc202039ef9aedaab3960cef4");
        //ArrayList<String> arrayList = new ArrayList<>();

        *//*
            Conexión con el getway de marvel
        *//*
        final String CHARACTER_BASE_URL = "http://gateway.marvel.com/v1/public/characters";

        *//*
            Configuración de la petición
        *//*
        //String characterJsonStr = null;
        final String TIMESTAMP = "ts";
        final String API_KEY = "apikey";
        final String HASH = "hash";
        final String ORDER = "orderBy";*/

        Uri builtUri;
        builtUri = Uri.parse(CHARACTER_BASE_URL+"?").buildUpon()
                .appendQueryParameter(TIMESTAMP, ts)
                .appendQueryParameter(API_KEY, apikey)
                .appendQueryParameter(HASH, hash)
                .appendQueryParameter(ORDER, "name")
                .appendQueryParameter("limit", "100")
                .appendQueryParameter("offset", "0")
                .build();
        return builtUri.toString();
    }

    public void getMarvelSiguiente(View view){
        Uri builtUri;
        if (Contador > 800){
            System.out.println("Llegaste al limite");
        } else {
            Contador+=100;
        }
        builtUri = Uri.parse(CHARACTER_BASE_URL+"?").buildUpon()
                .appendQueryParameter(TIMESTAMP, ts)
                .appendQueryParameter(API_KEY, apikey)
                .appendQueryParameter(HASH, hash)
                .appendQueryParameter(ORDER, "name")
                .appendQueryParameter("limit", "100")
                .appendQueryParameter("offset", Contador+"")
                .build();
        jsonMarvel(builtUri.toString(), adapter);
    }

    public void getMarvelAnterior(View view){
        Uri builtUri;
        if (Contador < 100){
            System.out.println("Llegaste al limite");
        } else {
            Contador-=100;
        }
        builtUri = Uri.parse(CHARACTER_BASE_URL+"?").buildUpon()
                .appendQueryParameter(TIMESTAMP, ts)
                .appendQueryParameter(API_KEY, apikey)
                .appendQueryParameter(HASH, hash)
                .appendQueryParameter(ORDER, "name")
                .appendQueryParameter("limit", "100")
                .appendQueryParameter("offset", Contador+"")
                .build();
        jsonMarvel(builtUri.toString(), adapter);
    }

    /*
        Investiga y reporta qué es md5:

    */
    public static String md5(String s) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            String hash = new String(hexEncode(digest.digest()));
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String hexEncode(byte[] bytes) {
        char[] result = new char[bytes.length*2];
        int b;
        for (int i = 0, j = 0; i < bytes.length; i++) {
            b = bytes[i] & 0xff;
            result[j++] = HEXCodes[b >> 4];
            result[j++] = HEXCodes[b & 0xf];
        }
        return new String(result);
    }
}
