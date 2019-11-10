package com.example.gamer.smartpark;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class mapaestacionamientos extends AppCompatActivity {

    private static final String ETIQUETA = MainActivity.class.getSimpleName();
    private static final int WHAT = 1;
    private static final int TIEMPO_REPETICION = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread tr = new Thread(){
            @Override
            public void run() {
                iniciarObtencionEstados();
            }
        };
        tr.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        regularHandler.sendEmptyMessageDelayed(WHAT, TIEMPO_REPETICION);
    }

    @Override
    public void onPause() {
        super.onPause();
        regularHandler.removeMessages(WHAT);
    }

    Handler regularHandler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            Log.d(ETIQUETA,"handleMessage() start");
            Thread tr = new Thread(){
                @Override
                public void run() {
                    final ArrayList<View> ListaBotones =((LinearLayout) findViewById(R.id.LinLay1)).getTouchables();
                    final String resultado = obtenerEstadosEstacionamientos();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONArray JSONARRAY_estacionamientos = new JSONArray(resultado.toString());

                                for(int i=0; i<JSONARRAY_estacionamientos.length();i++){
                                    JSONObject JSONOBJECT_estacionamiento = JSONARRAY_estacionamientos.getJSONObject(i);

                                    for (int j = 0; j < ListaBotones.size(); j++) {
                                        Button boton = (Button) ListaBotones.get(j);

                                        if(boton.getId()== getResources().getIdentifier("estacionamiento"+JSONOBJECT_estacionamiento.getString("n_estacionamiento"), "id", getPackageName()))
                                        {
                                            if (JSONOBJECT_estacionamiento.getString("estado").equals("0")){
                                                //ESTACIONAMIENTO LIBRE
                                                int id_string_libre = getResources().getIdentifier("libre","string",getPackageName());
                                                int id_color_libre = getResources().getIdentifier("libre","color",getPackageName());
                                                int id_color_blanco = getResources().getIdentifier("WHITE","color",getPackageName());
                                                boton.setText(id_string_libre);
                                                boton.setTextColor(ColorStateList.valueOf(getResources().getColor(id_color_blanco)));
                                                boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(id_color_libre)));
                                                //boton.setBackground(id_color_libre);

                                            }
                                            else
                                            {
                                                //ESTACIONAMIENTO OCUPADO
                                                int id_string_ocupado = getResources().getIdentifier("ocupado","string",getPackageName());
                                                int id_color_ocupado = getResources().getIdentifier("ocupado","color",getPackageName());
                                                int id_color_blanco = getResources().getIdentifier("WHITE","color",getPackageName());
                                                boton.setText(id_string_ocupado);
                                                boton.setTextColor(ColorStateList.valueOf(getResources().getColor(id_color_blanco)));
                                                boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(id_color_ocupado)));
                                            }
                                        }
                                    }
                                }
                                Log.d(ETIQUETA,"handleMessage() end");
                            } catch (JSONException e) {
                                Log.d(ETIQUETA,"Error en JSON: "+e);
                            }
                        }
                    });
                }
            };
            tr.start();

            regularHandler.sendEmptyMessageDelayed(msg.what, TIEMPO_REPETICION);

            return true;
        }
    });

    public void cambiarPiso (View boton_piso){
        Log.d(ETIQUETA,"cambiarPiso()");
        final ArrayList<View> BotonesPisos =findViewById(R.id.LinLayPisos).getTouchables();
        for (int i=0; i < BotonesPisos.size(); i++)
        {
            Button piso = (Button)BotonesPisos.get(i);
            piso.setTextColor(ColorStateList.valueOf(getResources().getColor(getResources().getIdentifier("WHITE","color",getPackageName()))));
        }
        Button boton = (Button) boton_piso;
        boton.setTextColor(ColorStateList.valueOf(getResources().getColor(getResources().getIdentifier("libre","color",getPackageName()))));
        String piso= boton.getText().toString();
        switch (piso){
            case "1er Piso":
                findViewById(R.id.LinLay1).setVisibility(LinearLayout.VISIBLE);
                findViewById(R.id.LinLay2).setVisibility(LinearLayout.GONE);
                findViewById(R.id.LinLay3).setVisibility(LinearLayout.GONE);
                break;
            case "2do Piso":
                findViewById(R.id.LinLay1).setVisibility(LinearLayout.GONE);
                findViewById(R.id.LinLay2).setVisibility(LinearLayout.VISIBLE);
                findViewById(R.id.LinLay3).setVisibility(LinearLayout.GONE);
                break;
            case "3er Piso":
                findViewById(R.id.LinLay1).setVisibility(LinearLayout.GONE);
                findViewById(R.id.LinLay2).setVisibility(LinearLayout.GONE);
                findViewById(R.id.LinLay3).setVisibility(LinearLayout.VISIBLE);
                break;
            default:
                Toast.makeText(getApplicationContext(),"Ese Piso aún no está desarrollado",Toast.LENGTH_LONG).show();
                break;
        }
    }

    public void cancelar (View view){
        startActivity(new Intent(getApplicationContext(),PagarEstacionamiento.class));
    }

    public void volver (View view){
        finish();
    }

    private String obtenerEstadosEstacionamientos (){
        URL url;
        String linea;
        int respuesta;
        StringBuilder resul= new StringBuilder();
        try {
            //CAMBIAR URL A REPOSITORIOMAX.NET
            //url = new URL("http://smartpark.repositoriomax.net/estacionamientos.php");
            url = new URL("http://spp.cyberls.net/sensores/recuperar_datos.php");
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            respuesta= connection.getResponseCode();

            if(respuesta==HttpURLConnection.HTTP_OK){
                InputStream in= new BufferedInputStream(connection.getInputStream());
                BufferedReader reader= new BufferedReader(new InputStreamReader(in));

                while ((linea=reader.readLine())!= null){
                    resul.append(linea);
                }
            }
        }catch (IOException e) {
            Log.d("ERROR EN EL void","Error en el VOID: "+e);
        }
        return resul.toString();
    }

    private void iniciarObtencionEstados(){
        Log.i(ETIQUETA, "iniciarObtencionEstados()");

        if ( checkPermission() )
        {
            Log.d(ETIQUETA,"El permiso está aceptado con anterioridad");
            //Log.d(ETIQUETA,"JSON: "+obtenerEstadosEstacionamientos());
        }
        else askPermission();
    }

    private final int REQ_PERMISSION = 999;

    private boolean checkPermission() {
        Log.d(ETIQUETA, "checkPermission()");
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                == PackageManager.PERMISSION_GRANTED );
    }

    // Asks for permission
    private void askPermission() {
        Log.d(ETIQUETA, "askPermission()");
        ActivityCompat.requestPermissions(
                this,
                new String[] { Manifest.permission.INTERNET },
                REQ_PERMISSION
        );
    }

    // Verify user's response of the permission requested
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(ETIQUETA, "onRequestPermissionsResult()");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch ( requestCode ) {
            case REQ_PERMISSION: {
                if ( grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                    // Permission granted
                    Log.d(ETIQUETA,"Permiso entregado");
                    Log.d(ETIQUETA,"JSON: "+obtenerEstadosEstacionamientos());

                } else {
                    // Permission denied
                    permissionsDenied();
                }
                break;
            }
        }
    }

    // App cannot work without the permissions
    private void permissionsDenied() {
        Log.w(ETIQUETA, "permissionsDenied()");
        // TODO close app and warn user
    }
}
