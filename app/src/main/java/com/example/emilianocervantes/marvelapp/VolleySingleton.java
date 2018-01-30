package com.example.emilianocervantes.marvelapp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by ovman on 30/01/2018.
 */


/*Creamos la concexion y se lo mandamos a la cola de peticiones y solo creamos una instancia.
 */
public class VolleySingleton {
    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;

    private VolleySingleton(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    /*es static synchronized porque es para el manejo de hilos (threads)
    Para sincronizar entre los threads; hasta que un thread lo acabe de usar, otro no puede entrar.
    Crea la instancia y nos regresa el objeto
     */
    public static synchronized VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }

        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
