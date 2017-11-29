package com.example.amechy.mechinealejandroejerciciosficheros;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;


public class Ejercicio5 extends AppCompatActivity implements View.OnClickListener{
    private static final String URL = "http://alumno.mobi/~alumno/superior/mechine/enlaces.txt";


    private EditText etxUrl;
    private Button btnDescargar;
    private Button btnSiguiente;
    private Button btnAnterior;
    private ImageView imgImagen;

    private ArrayList<String> listaUrl;
    private int imagenActual;

    private boolean exitoEnDescarga;



    private static final int MAX_TIMEOUT = 2500;
    private static final int RETRIES = 1;
    private static final int TIMEOUT_BETWEEN_RETRIES = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio5);

        exitoEnDescarga = false;
        listaUrl = new ArrayList<>();

        imgImagen = (ImageView) findViewById(R.id.imgImagen);
        etxUrl = (EditText) findViewById(R.id.etxUrl);


        btnDescargar = (Button) findViewById(R.id.btnDescargar);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        btnAnterior = (Button) findViewById(R.id.btnAnterior);
        btnDescargar.setOnClickListener(this);
        btnSiguiente.setOnClickListener(this);
        btnAnterior.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAnterior:
                if (exitoEnDescarga) {
                    if (imagenActual > 0) {
                        imagenActual--;
                    } else {
                        imagenActual = listaUrl.size() - 1;
                    }
                    Picasso.with(this)
                            .load(listaUrl.get(imagenActual % (listaUrl.size() - 1)))
                            .error(R.drawable.placeholder_error)
                            .placeholder(R.drawable.placeholder_error)
                            .into(imgImagen);
                }
                break;

            case R.id.btnDescargar:
                descargarImagenes();
                if (exitoEnDescarga) {
                    primeraImagen();
                    imagenActual = 0;
                }
                break;

            case R.id.btnSiguiente:
                if (exitoEnDescarga) {
                    imagenActual++;
                    //Cargamos la siguente imagen, en caso de que sea la ultima imagen, se cargara la primera
                    Picasso.with(this)
                            .load(listaUrl.get(imagenActual % (listaUrl.size() - 1)))
                            .error(R.drawable.placeholder_error)
                            .placeholder(R.drawable.placeholder_error)
                            .into(imgImagen);
                }
                break;
        }
    }

    private void primeraImagen() {
        if (!listaUrl.isEmpty()) {
            Picasso.with(Ejercicio5.this).load(listaUrl.get(0)).into(imgImagen);
        } else {
            Toast.makeText(Ejercicio5.this, "No se pueden obtener las imagenes", Toast.LENGTH_SHORT).show();
        }
    }

    private void descargarImagenes() {

        final ProgressDialog progreso = new ProgressDialog(this);

        final AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.setTimeout(MAX_TIMEOUT);
        cliente.setMaxRetriesAndTimeout(RETRIES, TIMEOUT_BETWEEN_RETRIES);

        if (URLUtil.isValidUrl(etxUrl.getText().toString())) {
            cliente.get(etxUrl.getText().toString(), new FileAsyncHttpResponseHandler(this) {

                @Override
                public void onStart() {
                    progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progreso.setMessage("Descargando imágenes...");
                    progreso.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            cliente.cancelAllRequests(true);
                        }
                    });
                    progreso.show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                    progreso.dismiss();
                    Toast.makeText(Ejercicio5.this, "No se puede descargar el archivo de enlaces... Código de error: " + statusCode, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, File file) {
                    String linea;
                    try {
                        FileInputStream fis = new FileInputStream(file);
                        InputStreamReader isr = new InputStreamReader(fis);
                        BufferedReader br = new BufferedReader(isr);
                        while ((linea = br.readLine()) != null) {
                            listaUrl.add(linea);
                        }
                    } catch (FileNotFoundException e) {
                        Toast.makeText(Ejercicio5.this, "Fichero no encontrado.", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(Ejercicio5.this, "Error de entrada/salida", Toast.LENGTH_SHORT).show();
                    }
                    exitoEnDescarga = true;
                    progreso.dismiss();
                }
            });

        } else {
            Toast.makeText(Ejercicio5.this, "Url del fichero invalida..", Toast.LENGTH_SHORT).show();
        }
    }

}
