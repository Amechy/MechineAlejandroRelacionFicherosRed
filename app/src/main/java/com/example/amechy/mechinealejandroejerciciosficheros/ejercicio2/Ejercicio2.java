package com.example.amechy.mechinealejandroejerciciosficheros.ejercicio2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.amechy.mechinealejandroejerciciosficheros.Memoria;
import com.example.amechy.mechinealejandroejerciciosficheros.R;

import java.util.ArrayList;
import java.util.List;

public class Ejercicio2 extends AppCompatActivity implements View.OnClickListener{

    EditText etxMensaje;

    Button btnInicio, btnAnadir;
    Spinner spnTiempo;
    String alarma;
    ArrayList<String> mensajes = new ArrayList<String>()   ;
    ArrayList<Integer> tiempos = new ArrayList<Integer>()  ;

    int contador = 0;



    Memoria miMemoria;

    Bundle bnd;

    private static final String FICHERO = "alarmas.txt";

    private static final String CODIFICACION = "UTF-8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio2);
        etxMensaje = (EditText) findViewById(R.id.etxMensaje);
        btnInicio = (Button)findViewById(R.id.btnInicio);
        btnInicio.setOnClickListener(this);
        btnAnadir = (Button)findViewById(R.id.btnAnadir);
        btnAnadir.setOnClickListener(this);
        spnTiempo = (Spinner)findViewById(R.id.spnTiempo);
        miMemoria = new Memoria(getApplicationContext());
        rellenarSpinner(this);
    }

    public void rellenarSpinner(Context contexto) {
        ArrayList<String> minutos = new ArrayList<String>();
        for (int i = 1;i<60;i++){
            minutos.add( String.valueOf(i));
        }


        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(contexto,
                android.R.layout.simple_spinner_item,
                minutos);

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTiempo.setAdapter(spinnerArrayAdapter);

    }


    @Override
    public void onClick(View view) {
        if(view ==btnInicio)
        {

            Intent intent = new Intent(Ejercicio2.this,ContadorAlarma.class);
            intent.putStringArrayListExtra("mensajes", mensajes);
            intent.putIntegerArrayListExtra("tiempos", tiempos);
            startActivity(intent);

        }else if(view == btnAnadir) {
            AnadirAlarma();

        }
    }

    private void AnadirAlarma() {
        if (etxMensaje.getText().toString().length() > 0){
            mensajes.add(contador,etxMensaje.getText().toString());
            tiempos.add(contador, Integer.parseInt(spnTiempo.getSelectedItem().toString()));
            alarma = tiempos.get(contador)+","+mensajes.get(contador);
            contador++;
            miMemoria.escribirExterna(FICHERO,alarma,true, CODIFICACION);

            btnInicio.setEnabled(true);
            etxMensaje.setText("");
            spnTiempo.setSelection(0);


            if (contador == 4)
            {
                btnAnadir.setEnabled(false);
            }
            Toast.makeText(this, "Alarma guardada con exito", Toast.LENGTH_LONG).show();



        }else{
            Toast.makeText(this, "Tiene que rellenar el mensaje", Toast.LENGTH_LONG).show();

        }
    }


}
