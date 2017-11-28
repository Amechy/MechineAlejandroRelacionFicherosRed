package com.example.amechy.mechinealejandroejerciciosficheros.ejercicio3;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amechy.mechinealejandroejerciciosficheros.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Ejercicio3 extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener{

    EditText etxFechaInicio, etxFechaFin, etxDiasFestivos;
    Date dateCalendar;
    Date dateInicio;
    Date dateFin;
    DatePickerDialog dpkInicio;
    DatePickerDialog dpkFin;
    Button btnVerFestivos;
    ArrayList<Date> intervaloFechas;
    ArrayList<Date> diasLectivos;
    File diasFestivos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio3);

        File ruta_sd = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        new File(ruta_sd.getAbsolutePath(), "DiasFestivos.txt").delete();
        diasFestivos = new File(ruta_sd.getAbsolutePath(), "DiasFestivos.txt");

        etxFechaInicio = (EditText)findViewById(R.id.etxFechaInicio);
        etxFechaInicio.setOnClickListener(this);

        etxFechaFin = (EditText)findViewById(R.id.etxFechaFin);
        etxFechaFin.setOnClickListener(this);

        etxDiasFestivos = (EditText)findViewById(R.id.etxDiasLectivos);

        btnVerFestivos = (Button)findViewById(R.id.btnVerFestivos);
        btnVerFestivos.setOnClickListener(this);

        /*try {
            Fechas.CrearFicheroFestivos(diasFestivos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    @Override
    public void onClick(View v) {
        if(v== etxFechaInicio){
            Calendar calendar = new GregorianCalendar();
            dpkInicio = DatePickerDialog.newInstance(
                    this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            dpkInicio.show(getFragmentManager(), "Datepickerdialog");
        }else

        if(v== etxFechaFin){
            Calendar calendar = new GregorianCalendar();
            dpkFin = DatePickerDialog.newInstance(
                    this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            dpkFin.show(getFragmentManager(), "Datepickerdialog");
        }else

        if(v==btnVerFestivos) {

            /*try {
                intervaloFechas = Fechas.margenDeFechas(dateInicio, dateFin);
                if(dateInicio == null || dateFin == null)
                    Toast.makeText(this,"Debes introducir las dos fechas",Toast.LENGTH_LONG).show();
                else if(intervaloFechas.size() == 0){
                    Toast.makeText(this,"El margen de fechas no es válido",Toast.LENGTH_LONG).show();

                }
                else{
                    String texto = "";
                    Fechas.ComparaFechasFichero(diasFestivos, intervaloFechas);
                    for (int i = 0; i < intervaloFechas.size(); i++) {
                        texto += (new SimpleDateFormat("dd-MM-yyyy").format(intervaloFechas.get(i))+"\n");
                    }
                    etxDiasFestivos.setText(texto);
                }
            } catch (IOException e) {
                Toast.makeText(this,"Error: "+e.getMessage(),Toast.LENGTH_LONG).show();
            } catch (ParseException e) {
                Toast.makeText(this,"Error: "+e.getMessage(),Toast.LENGTH_LONG).show();
            } catch (NullPointerException e){
                Toast.makeText(this,"Debes introducir un margen de fechas",Toast.LENGTH_LONG).show();
            } catch (Exception e){
                Toast.makeText(this,"Error: "+e.getMessage(),Toast.LENGTH_LONG).show();
            }
            */
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        if(view == dpkInicio){
            dateInicio = new Date(year,monthOfYear,dayOfMonth);
            etxFechaInicio.setText(new SimpleDateFormat("dd-MM-yyyy").format(dateInicio));
        }
        if(view == dpkFin){
            dateFin = new Date(year,monthOfYear,dayOfMonth);
            etxFechaFin.setText(new SimpleDateFormat("dd-MM-yyyy").format(dateFin));
        }
    }
}
