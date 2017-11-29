package com.example.amechy.mechinealejandroejerciciosficheros;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;


public class Ejercicio4 extends AppCompatActivity implements View.OnClickListener{

    EditText etxUrl;
    WebView wbvWeb;
    RadioButton rbtnVolley;
    RadioButton rbtnJava;
    RadioButton rbtnAAHC;

    Resultado resultado = null;
    Button btnConectar;

    RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio4);
        etxUrl = (EditText) findViewById(R.id.etxURL);
        wbvWeb = (WebView) findViewById(R.id.wbvWeb);
        rbtnJava = (RadioButton) findViewById(R.id.rbtnJava);
        rbtnAAHC = (RadioButton) findViewById(R.id.rbtnAAHC);
        rbtnVolley = (RadioButton) findViewById(R.id.rbtnVolley);
        btnConectar = (Button)findViewById(R.id.btnConectar);
        btnConectar.setOnClickListener(this);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
    }

    @Override
    public void onClick(View view) {
        if(view==btnConectar)
            establecerConexion();
    }




    private void conexionJava()
    {
        resultado = Conectar.conectarJava(etxUrl.getText().toString());
        if (resultado.getCodigo())
            wbvWeb.loadDataWithBaseURL(null, resultado.getContenido(), "text/html", "UTF-8", null);
        else
            wbvWeb.loadDataWithBaseURL(null, resultado.getMensaje(), "text/html", "UTF-8", null);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    private void conexionAAHC() {
        final String texto = etxUrl.getText().toString();
        final ProgressDialog progreso = new ProgressDialog(Ejercicio4.this);
        RestClient.get(texto, new TextHttpResponseHandler() {
            @Override
            public void onStart() {
                //called before request is started
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Conectando...");
                //progreso.setCancelable(false);
                progreso.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        RestClient.cancelRequests(getApplicationContext(), true);
                    }
                });
                progreso.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                // called when response HTTP status is "200 OK"
                progreso.dismiss();
                wbvWeb.loadDataWithBaseURL(null, response, "text/html", "UTF-8", null);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                progreso.dismiss();
                wbvWeb.loadDataWithBaseURL(null, "Error: " + response + " ", "text/html", "UTF-8", null);
            }
        });
    }

    public void conexionVolley() {
        final String enlace = etxUrl.getText().toString();
        // Instantiate the RequestQueue.
        mRequestQueue = Volley.newRequestQueue(this);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Conectando...");
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                mRequestQueue.cancelAll("tag");
            }
        });

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, enlace, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                wbvWeb.loadDataWithBaseURL(enlace, response, "text/html", "UTF-8", null);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String mensaje = "Error";
                if (error instanceof TimeoutError || error instanceof NoConnectionError)
                    mensaje = "Timeout Error: " + error.getMessage();
                else {
                    NetworkResponse errorResponse = error.networkResponse;
                    if (errorResponse != null && errorResponse.data != null) {
                        try {
                            mensaje = "Error: " + errorResponse.statusCode + " " + "\n " +
                                    new String(errorResponse.data, "UTF-8");

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
                progressDialog.dismiss();
                wbvWeb.loadDataWithBaseURL(null, mensaje, "text/html", "UTF-8", null);
            }
        });
        // Set the tag on the request.
        stringRequest.setTag("tag");
        // Set retry policy
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, 1, 1));
        // Add the request to the RequestQueue.
        mRequestQueue.add(stringRequest);
    }
    private void establecerConexion() {
        if (isNetworkAvailable()) {
            if (rbtnJava.isChecked())
                conexionJava();
            if (rbtnAAHC.isChecked())
                conexionAAHC();
            if (rbtnVolley.isChecked())
                conexionVolley();
        }
        else
        {
            Toast.makeText(this, "Sin conexion a la red", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll("tag");
        }
    }


}
