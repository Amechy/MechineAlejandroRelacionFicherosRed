package com.example.amechy.mechinealejandroejerciciosficheros;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.Header;

public class Ejercicio6 extends AppCompatActivity implements View.OnClickListener {
    final String url = "http://alumno.mobi/~alumno/superior/mechine/conversion.txt";

    double Euro = 0d;
    double Dolar = 0d;
    double valorDivisa;
    Button convertirDivisa;
    EditText etxEuro;
    EditText etxDolar;
    RadioButton rbtnDolar;
    RadioButton rbtnEuro;
    RadioGroup rgpConversor;
    String resultado;

    private static final int MAX_TIMEOUT = 2500;
    private static final int RETRIES = 1;
    private static final int TIMEOUT_BETWEEN_RETRIES = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio6);
        convertirDivisa = (Button) findViewById(R.id.btnConvertir);
        etxEuro = (EditText) findViewById(R.id.etxEuro);
        etxDolar = (EditText) findViewById(R.id.etxDolar);
        rbtnDolar = (RadioButton) findViewById(R.id.rbtnDolar);
        rbtnEuro = (RadioButton) findViewById(R.id.rbtnEuro);
        rgpConversor = (RadioGroup) findViewById(R.id.rgpConversor);
        etxDolar.setText("0");
        etxEuro.setText("0");
        leerCambio();


        rgpConversor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (rbtnDolar.isChecked()) {
                    etxDolar.setEnabled(true);
                    etxEuro.setEnabled(false);
                    etxDolar.setText("0");
                    etxEuro.setText("0");
                } else {
                    etxDolar.setEnabled(false);
                    etxEuro.setEnabled(true);
                    etxDolar.setText("0");
                    etxEuro.setText("0");
                }
            }
        });

    }

    @Override
    public void onClick(View view) {

        if (rbtnDolar.isChecked()) {
            dolarAEuro(view);


        } else {
            if (rbtnEuro.isChecked()) {
                euroADolar(view);
            }
        }

    }

    private void euroADolar(View view) {
        etxDolar.setEnabled(false);
        if (etxEuro.getText().length() > 0) {
            try {
                Euro = Double.parseDouble(etxEuro.getText().toString());
                Dolar = Double.parseDouble(etxDolar.getText().toString());
                resultado = String.valueOf(Math.round(Euro * valorDivisa * 100d) / 100d);
                etxDolar.setText(resultado);
            } catch (Exception e) {
                Snackbar.make(view, "Número inválido", Snackbar.LENGTH_LONG).show();
            }
        } else {
            etxEuro.setText("0");
            etxDolar.setText("0");

        }
    }

    private void dolarAEuro(View view) {
        if (etxDolar.getText().length() > 0) {
            try {
                Dolar = Double.parseDouble(etxDolar.getText().toString());
                Euro = Double.parseDouble(etxEuro.getText().toString());
                resultado = String.valueOf(Math.round(Dolar / valorDivisa * 100d) / 100d);
                etxEuro.setText(resultado);
            } catch (Exception e) {
                Snackbar.make(view, "Número inválido", Snackbar.LENGTH_LONG).show();
            }

        } else {
            etxDolar.setText("0");
            etxEuro.setText("0");
        }
    }

    private void leerCambio() {
        final ProgressDialog progreso = new ProgressDialog(this);
        final AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.setTimeout(MAX_TIMEOUT);
        cliente.setMaxRetriesAndTimeout(RETRIES, TIMEOUT_BETWEEN_RETRIES);

        cliente.get(url, new FileAsyncHttpResponseHandler(this) {

            @Override
            public void onStart() {
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Obteniendo datos necesarios...");
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
                Toast.makeText(Ejercicio6.this, "No se ha podido obtener el valor del cambio", Toast.LENGTH_SHORT).show();
                valorDivisa = 0.0;
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                try {
                    FileInputStream fis = new FileInputStream(file);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr);


                    valorDivisa = Double.parseDouble(br.readLine());


                } catch (FileNotFoundException e) {
                    Toast.makeText(Ejercicio6.this, "No se ha encontrado el archivo en la red", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(Ejercicio6.this, "Error de entrada/salida", Toast.LENGTH_SHORT).show();
                }catch (NumberFormatException e){
                    Toast.makeText(Ejercicio6.this, "Error al leer el cambio de divisas ", Toast.LENGTH_SHORT).show();
                }
                progreso.dismiss();
            }
        });
    }

}
