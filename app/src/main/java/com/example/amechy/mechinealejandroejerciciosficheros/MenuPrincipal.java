package com.example.amechy.mechinealejandroejerciciosficheros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.amechy.mechinealejandroejerciciosficheros.ejercicio1.AnadirContacto;

public class MenuPrincipal extends AppCompatActivity implements View.OnClickListener{

    Button btnEjercicio1,btnEjercicio2,btnEjercicio3,btnEjercicio4,btnEjercicio5,btnEjercicio6,btnEjercicio7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        btnEjercicio1 = (Button)findViewById(R.id.btnEjercicio1);
        btnEjercicio1.setOnClickListener(this);

        btnEjercicio2 = (Button)findViewById(R.id.btnEjercicio2);
        btnEjercicio2.setOnClickListener(this);

        btnEjercicio3= (Button)findViewById(R.id.btnEjercicio3);
        btnEjercicio3.setOnClickListener(this);

        btnEjercicio4 = (Button)findViewById(R.id.btnEjercicio4);
        btnEjercicio4.setOnClickListener(this);

        btnEjercicio5 = (Button)findViewById(R.id.btnEjercicio5);
        btnEjercicio5.setOnClickListener(this);

        btnEjercicio6 = (Button)findViewById(R.id.btnEjercicio6);
        btnEjercicio6.setOnClickListener(this);

        btnEjercicio7 = (Button)findViewById(R.id.btnEjercicio7);
        btnEjercicio7.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnEjercicio1:
                startActivity(new Intent(MenuPrincipal.this,AnadirContacto.class));
                break;
            case R.id.btnEjercicio2:
                startActivity(new Intent(MenuPrincipal.this,Ejercicio2.class));
                break;
            case R.id.btnEjercicio3:
                startActivity(new Intent(MenuPrincipal.this,Ejercicio3.class));
                break;
            case R.id.btnEjercicio4:
                startActivity(new Intent(MenuPrincipal.this,Ejercicio4.class));
                break;
            case R.id.btnEjercicio5:
                startActivity(new Intent(MenuPrincipal.this,Ejercicio5.class));
                break;
            case R.id.btnEjercicio6:
                startActivity(new Intent(MenuPrincipal.this,Ejercicio6.class));
                break;
            case R.id.btnEjercicio7:
                startActivity(new Intent(MenuPrincipal.this,Ejercicio7.class));
                break;
        }
    }
}
