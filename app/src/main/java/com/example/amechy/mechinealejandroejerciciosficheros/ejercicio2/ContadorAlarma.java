package com.example.amechy.mechinealejandroejerciciosficheros.ejercicio2;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amechy.mechinealejandroejerciciosficheros.R;

import java.util.ArrayList;

public class ContadorAlarma extends AppCompatActivity {

    ArrayList<String> mensajes = new ArrayList<String>();
    ArrayList<Integer> tiempos = new ArrayList<Integer>();
    TextView txvTiempoRestante, txvMensajeAlarma;
    MediaPlayer aviso;
    int alarmaActual = 0;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador_alarma);

        mensajes = getIntent().getExtras().getStringArrayList("mensajes");
        tiempos = getIntent().getExtras().getIntegerArrayList("tiempos");
        txvTiempoRestante = (TextView) findViewById(R.id.txvTiempo);
        txvMensajeAlarma = (TextView)findViewById(R.id.txvMensaje);
        aviso = MediaPlayer.create(this, R.raw.audio);

        siguienteAlarma();
    }

    private void siguienteAlarma() {
        if (alarmaActual < tiempos.size()) {
            iniciarContador(tiempos.get(alarmaActual), mensajes.get(alarmaActual));
            alarmaActual++;
        }else {
            Toast.makeText(ContadorAlarma.this, "Todas las alarmas finalizadas...", Toast.LENGTH_SHORT).show();
        }
    }



    public void iniciarContador(int minutes, final String message) {
        txvMensajeAlarma.setText(message);
        new CountDownTimer(minutes * 60 * 1000, 1000) {


            long _minutos;
            long _segundos;



            @Override
            public void onTick(long l) {
                _minutos = (l / 1000) / 60;
                _segundos = (l / 1000) % 60;

                if (_segundos < 10) {
                    txvTiempoRestante.setText(_minutos + ":0" + _segundos);
                } else {
                    txvTiempoRestante.setText(_minutos + ":" + _segundos);
                }
            }

            @Override
            public void onFinish() {


                Toast.makeText(ContadorAlarma.this, "Terminó esta alarma", Toast.LENGTH_SHORT).show();
                aviso.start();
                siguienteAlarma();

            }


        }.start();
    }

}
