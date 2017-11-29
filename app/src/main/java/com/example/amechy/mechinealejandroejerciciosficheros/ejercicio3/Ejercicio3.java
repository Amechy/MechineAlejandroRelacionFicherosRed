package com.example.amechy.mechinealejandroejerciciosficheros.ejercicio3;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amechy.mechinealejandroejerciciosficheros.Memoria;
import com.example.amechy.mechinealejandroejerciciosficheros.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Ejercicio3 extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    EditText etxFechaInicio, etxFechaFin, etxDiasFestivos;

    Date dateInicio;
    Date dateFin;
    DatePickerDialog dpkInicio;
    DatePickerDialog dpkFin;
    Button btnVerLaborales;
    Memoria miMemoria;
    private static final String FICHERO = "diasLectivos.txt";

    private static final String CODIFICACION = "UTF-8";

    String[] diasFestivosArray = {"12-10-2017", "01-11-2017", "06-12-2017", "08-12-2017", "25-12-2017", "26-12-2017", "27-12-2017",
            "28-12-2017", "29-12-2017", "01-01-2018", "02-01-2018", "03-01-2018", "04-01-2018", "05-01-2018", "26-02-2018", "27-02-2018",
            "28-02-2018", "01-03-2018", "02-03-2018", "26-03-2018", "27-03-2018", "28-03-2018", "29-03-2018", "30-03-2018", "01-04-2018",};

    ArrayList<String> diasFestivos = new ArrayList<String>(Arrays.asList(diasFestivosArray));
    ArrayList<String> diasLaborales = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio3);

        etxFechaInicio = (EditText) findViewById(R.id.etxFechaInicio);
        etxFechaInicio.setOnClickListener(this);

        etxFechaFin = (EditText) findViewById(R.id.etxFechaFin);
        etxFechaFin.setOnClickListener(this);

        etxDiasFestivos = (EditText) findViewById(R.id.etxDiasLectivos);

        btnVerLaborales = (Button) findViewById(R.id.btnVerLaborales);
        btnVerLaborales.setOnClickListener(this);



        miMemoria = new Memoria(this);


    }

    @Override
    public void onClick(View v) {
        if (v == etxFechaInicio) {
            Calendar calendar = new GregorianCalendar();
            dpkInicio = DatePickerDialog.newInstance(
                    this,

                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );

            dpkInicio.show(getFragmentManager(), "Datepickerdialog");

        } else if (v == etxFechaFin) {
            Calendar calendar = new GregorianCalendar();

            dpkFin = DatePickerDialog.newInstance(
                    this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            dpkFin.show(getFragmentManager(), "Datepickerdialog");

        } else if (v == btnVerLaborales) {
            if ((dateInicio != null && dateFin != null) && dateInicio.before(dateFin)) {
                DeterminarNoLectivos(dateInicio, dateFin);


            } else {

                Toast.makeText(Ejercicio3.this, "La fecha de inicio tiene que ser anterior a la fecha de fin", Toast.LENGTH_LONG);
            }


        }
    }


    public void DeterminarNoLectivos(Date fechaInicio, Date fechaFin) {

        String tmp = "";
        Date dateActual = new Date();
        Calendar calendarActual = new GregorianCalendar();
        calendarActual.setTime(dateActual);


        String fechaActual = new SimpleDateFormat("dd-MM-yyyy").format(dateActual);


        if (calendarActual.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && calendarActual.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            boolean esFestivo1 = false;
            for (int i = 0; i < diasFestivos.size(); i++) {
                if (diasFestivos.get(i).equals(fechaActual)) {
                    esFestivo1 = true;
                }
            }
            if (!esFestivo1) {
                etxDiasFestivos.setText("Hoy es un dia lectivo, tira pa clase");
            }else{
                etxDiasFestivos.setText("Hoy es un dia festivo");
            }
        }


        Calendar calendarInicio = new GregorianCalendar();


        calendarInicio.setTime(fechaInicio);

        Calendar calendarFin = new GregorianCalendar();
        calendarFin.setTime(fechaFin);
        calendarFin.add(Calendar.DAY_OF_YEAR, 1);


        while (calendarInicio.getTime().before(calendarFin.getTime())) {
            Boolean esFestivo = false;
            String fechaString = new SimpleDateFormat("dd-MM-yyyy").format(calendarInicio.getTime());
            if (calendarInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && calendarInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {

                for (int i = 0; i < diasFestivos.size(); i++) {
                    if (diasFestivos.get(i).equals(fechaString)) {
                        esFestivo = true;
                    }
                }
                if (!esFestivo) {
                    tmp += fechaString + "\n";
                    if (fechaString.equals(fechaActual)) {
                        etxDiasFestivos.setText("Hoy es un dia lectivo, tira pa clase");
                    }
                }


            }
            calendarInicio.add(Calendar.DAY_OF_YEAR, 1);


        }
        try {
            miMemoria.escribirExterna(FICHERO, tmp, true, CODIFICACION);
            Toast.makeText(Ejercicio3.this, "Dias lectivos guardados con exito en diasLectivos.txt", Toast.LENGTH_LONG);
        } catch (Exception e) {
            Toast.makeText(Ejercicio3.this, "Problema al guardar el fichero de dias lectivos", Toast.LENGTH_LONG);
        }


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        if (view == dpkInicio) {
            dateInicio = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
            etxFechaInicio.setText(new SimpleDateFormat("dd-MM-yyyy").format(dateInicio));
        }
        if (view == dpkFin) {
            dateFin = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
            etxFechaFin.setText(new SimpleDateFormat("dd-MM-yyyy").format(dateFin));
        }
    }


}
