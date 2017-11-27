package com.example.amechy.mechinealejandroejerciciosficheros.ejercicio1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amechy.mechinealejandroejerciciosficheros.Memoria;
import com.example.amechy.mechinealejandroejerciciosficheros.R;
import com.example.amechy.mechinealejandroejerciciosficheros.Resultado;

public class AnadirContacto extends AppCompatActivity implements View.OnClickListener {
    EditText etxNombre, etxApellido,etxTelefono,etxEmail;
    Button btnListar,btnAnadir;
    String persona;
    public Memoria miMemoria;
    public static final String nombreFichero = "agenda.txt";


    public static final String CODIFICACION= "utf-8";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_contacto);

        etxNombre = (EditText)findViewById(R.id.etxName);
        etxApellido = (EditText)findViewById(R.id.etxApellidos);
        etxEmail = (EditText)findViewById(R.id.etxEmail);
        etxTelefono = (EditText)findViewById(R.id.etxTelefono);

        btnAnadir = (Button)findViewById(R.id.btnGuardar);
        btnListar = (Button)findViewById(R.id.btnListar);
        btnListar.setOnClickListener(this);
        btnAnadir.setOnClickListener(this);
        miMemoria = new Memoria(this);


    }

    @Override
    public void onClick(View view) {
        if (view == btnListar){
            startActivity(new Intent(AnadirContacto.this,ListarAgenda.class));
        }
        else if(view == btnAnadir){
            anadirContacto();
        }
    }



    private void anadirContacto()
    {

        if (etxNombre.getText().length() == 0 || etxTelefono.getText().length() == 0 || etxEmail.getText().length() == 0) {
            Toast.makeText(this, "Tienes que rellenar todos los campos", Toast.LENGTH_SHORT).show();
        }
        else {
            persona = etxNombre.getText().toString()+";"+ etxApellido.getText().toString()+";"+etxEmail.getText().toString()+";"+etxTelefono.getText().toString()+"\n";
            miMemoria.escribirInterna(nombreFichero, persona, true, CODIFICACION);
            Toast.makeText(this, "Guardando contacto", Toast.LENGTH_SHORT).show();
        }
    }
}
