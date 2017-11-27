package com.example.amechy.mechinealejandroejerciciosficheros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Ejercicio2 extends AppCompatActivity {

    EditText edtMin1;
    EditText edtMen1;
    EditText edtMin2;
    EditText edtMen2;
    EditText edtMin3;
    EditText edtMen3;
    EditText edtMin4;
    EditText edtMen4;
    EditText edtMin5;
    EditText edtMen5;
    Button btnIniciar;

    String alarma1;
    String alarma2;
    String alarma3;
    String alarma4;
    String alarma5;

    Memoria miMemoria;

    Bundle bnd;

    private static final String RUTA = "alarmas.txt";

    private static final String CODIFICACION = "UTF-8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio2);
        inicializar();
    }
    void inicializar() {
        bnd = new Bundle();
        edtMin1 = (EditText) findViewById(R.id.edtMinAl1);
        edtMen1 = (EditText) findViewById(R.id.edtMenAl1);
        edtMin2 = (EditText) findViewById(R.id.edtMinAl2);
        edtMen2 = (EditText) findViewById(R.id.edtMenAl2);
        edtMin3 = (EditText) findViewById(R.id.edtMinAl3);
        edtMen3 = (EditText) findViewById(R.id.edtMenAl3);
        edtMin4 = (EditText) findViewById(R.id.edtMinAl4);
        edtMen4 = (EditText) findViewById(R.id.edtMenAl4);
        edtMin5 = (EditText) findViewById(R.id.edtMinAl5);
        edtMen5 = (EditText) findViewById(R.id.edtMenAl5);
        btnIniciar = (Button)findViewById(R.id.btnInicio);
        btnIniciar.setOnClickListener(this);
        miMemoria= new Memoria(getApplicationContext());
        miMemoria.escribirExterna(RUTA, "", false, CODIFICACION);
    }

    @Override
    public void onClick(View view) {
        if(view ==btnIniciar)
        {
            Guardar();
            Iniciar();
        }
    }

    private void Iniciar() {
        Intent intnt = new Intent(AlarmaActivity.this, ContadorActivity.class);
        intnt.putExtra("alarmas",bnd);
        startActivity(intnt);
    }

    private void Guardar()
    {
        if (edtMin1.getText().length() == 0 && edtMin2.getText().length() == 0 && edtMin3.getText().length() == 0 && edtMin4.getText().length() == 0 && edtMin5.getText().length() == 0) {
            Toast.makeText(this, "Rellene los campos incompletos", Toast.LENGTH_SHORT).show();
        }
        else {
            if(edtMin1.getText().length()>0&&edtMin1.getText().length()<3) {
                alarma1 = edtMin1.getText().toString() + ", paso por el primer kilómetro " + edtMen1.getText().toString() + "\n";
                miMemoria.escribirExterna(RUTA, alarma1, true, CODIFICACION);

                bnd.putInt("Alarma1",Integer.valueOf(edtMin1.getText().toString()));
                if (edtMen1.getText().length()>0)
                {
                    bnd.putString("Alarma1Men",edtMen1.getText().toString());
                }else{
                    bnd.putString("Alarma1Men","Mensaje alarma 1");
                }
            }
            if(edtMin2.getText().length()>0&&edtMin2.getText().length()<3) {
                alarma2 = edtMin2.getText().toString() +", paso por el segundo kilómetro "+ edtMen2.getText().toString()  + "\n";
                miMemoria.escribirExterna(RUTA, alarma2, true, CODIFICACION);
                bnd.putInt("Alarma2",Integer.valueOf(edtMin2.getText().toString()));
                if (edtMen2.getText().length()>0)
                {
                    bnd.putString("Alarma2Men",edtMen2.getText().toString());
                }else{
                    bnd.putString("Alarma2Men","Mensaje alarma 2");
                }
            }
            if(edtMin3.getText().length()>0&&edtMin3.getText().length()<3) {
                alarma3 = edtMin3.getText().toString() + ", paso por el tercer kilómetro " + edtMen3.getText().toString()  + "\n";
                miMemoria.escribirExterna(RUTA, alarma3, true, CODIFICACION);
                bnd.putInt("Alarma3",Integer.valueOf(edtMin3.getText().toString()));
                if (edtMen3.getText().length()>0)
                {
                    bnd.putString("Alarma3Men",edtMen3.getText().toString());
                }else{
                    bnd.putString("Alarma3Men","Mensaje alarma 3");
                }
            }
            if(edtMin4.getText().length()>0&&edtMin4.getText().length()<3) {
                alarma4 = edtMin4.getText().toString() + ", paso por el cuarto kilómetro " + edtMen4.getText().toString()  + "\n";
                miMemoria.escribirExterna(RUTA, alarma4, true, CODIFICACION);
                bnd.putInt("Alarma4",Integer.valueOf(edtMin4.getText().toString()));
                if (edtMen4.getText().length()>0)
                {
                    bnd.putString("Alarma4Men",edtMen4.getText().toString());
                }else{
                    bnd.putString("Alarma4Men","Mensaje alarma 4");
                }
            }
            if(edtMin5.getText().length()>0&&edtMin5.getText().length()<3) {
                alarma5 = edtMin5.getText().toString() + ", paso por el quinto kilómetro " + edtMen5.getText().toString()  + "\n";
                miMemoria.escribirExterna(RUTA, alarma5, true, CODIFICACION);
                bnd.putInt("Alarma5",Integer.valueOf(edtMin5.getText().toString()));
                if (edtMen5.getText().length()>0)
                {
                    bnd.putString("Alarma5Men",edtMen5.getText().toString());
                }else{
                    bnd.putString("Alarma5Men","Mensaje alarma 1");
                }
            }
            Toast.makeText(this, "Alarmas Guardadas", Toast.LENGTH_SHORT).show();

        }
    }
}
