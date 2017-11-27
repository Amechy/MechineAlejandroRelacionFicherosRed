package com.example.amechy.mechinealejandroejerciciosficheros.ejercicio1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amechy.mechinealejandroejerciciosficheros.Memoria;
import com.example.amechy.mechinealejandroejerciciosficheros.R;
import com.example.amechy.mechinealejandroejerciciosficheros.Resultado;

public class ListarAgenda extends AppCompatActivity {

    TextView contactos;
    Resultado resultado;
    Memoria miMemoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_agenda);

        contactos = (TextView)findViewById(R.id.txvContactos);
        miMemoria = new Memoria(this);
        ListarContactos();

    }
    private void ListarContactos() {
        resultado = miMemoria.leerInterna(AnadirContacto.nombreFichero, AnadirContacto.CODIFICACION);
        if (resultado.getCodigo()) {
            contactos.setText(resultado.getContenido());
            Toast.makeText(this, "Se han listado los contactos", Toast.LENGTH_SHORT).show();
        }
        else
        {
            contactos.setText("");
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

}
