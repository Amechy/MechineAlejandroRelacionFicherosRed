package com.example.amechy.mechinealejandroejerciciosficheros.ejercicio3;

import android.app.Application;

import com.example.amechy.mechinealejandroejerciciosficheros.Memoria;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by amechy on 28/11/17.
 */

public class Fechas {

    String[] diasFestivosArray = {"02-01-2017", "06-01-2017", "28-02-2017", "13-04-2017", "14-04-2017", "15-08-2017", "19-08-2017", "08-09-2017", "12-10-2017", "01-11-2017", "06-12-2017", "08-12-2017", "25-12-2017"};
    List<String> diasFestivos = Arrays.asList(diasFestivosArray);
    Memoria miMemoria;
    public void DeterminarNoLectivos(Date fechaInicio, Date fechaFin){

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(fechaInicio);

        while (fechaInicio.before(fechaFin)){
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                diasFestivos.add(new SimpleDateFormat("dd-MM-yyyy").format(fechaInicio));
            }
            calendar.add(Calendar.DAY_OF_YEAR,1);
        }


    }







}

