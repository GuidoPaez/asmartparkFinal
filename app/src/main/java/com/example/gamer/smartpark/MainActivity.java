package com.example.gamer.smartpark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

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

import Room.BaseDeDatos.BaseDeDatosApp;
import Room.DAO.CategoriaDAO;
import Room.DAO.EmpresaDAO;
import Room.DAO.EstacionamientoDAO;
import Room.DAO.OfertaDAO;
import Room.DAO.StripcenterDAO;
import Room.DAO.TipoEstacionamientoDAO;
import Room.Entidades.Categoria;
import Room.Entidades.Empresa;
import Room.Entidades.Estacionamiento;
import Room.Entidades.Oferta;
import Room.Entidades.Stripcenter;
import Room.Entidades.TipoEstacionamiento;

public class MainActivity extends AppCompatActivity {

    private final int DURACION_SPLASH = 4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread tr = new Thread(){
            @Override
            public void run() {
                verificarPrimerUso();
            }
        };
        tr.start();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(MainActivity.this, estacionamientos.class);
                startActivity(intent);
                finish();
            };
        }, DURACION_SPLASH);
        setContentView(R.layout.activity_main);

        //Intent intent = new Intent(MainActivity.this,estacionamientos.class);

    }

    private void verificarPrimerUso() {

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;

        // Obtener código de la versión actual
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Obtener código de la versión guardada
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Verificar si es primera vez que usa la app o un upgrade
        if (currentVersionCode == savedVersionCode) {

            // TODO Este es un uso normal de la app
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {

            // TODO Esta es una nueva instalación (o el usuario eliminó las preferencias compartidas)

            //CREAR Y DECLARAR BD
            final BaseDeDatosApp database = BaseDeDatosApp.recuperarBaseDatosApp(getApplicationContext());

            EmpresaDAO empresaDAO = database.getEmpresaDAO();
            OfertaDAO ofertaDAO = database.getOfertaDAO();
            CategoriaDAO categoriaDAO = database.getCategoriaDAO();
            EstacionamientoDAO estacionamientoDAO = database.getEstacionamientoDAO();
            TipoEstacionamientoDAO tipoEstacionamientoDAO = database.getTipoEstacionamientoDAO();
            StripcenterDAO stripcenterDAO = database.getStripcenterDAO();

            //CONSULTAR AWS POR BS Y RECUPERAR EL JSON

            URL url;
            String linea;
            int respuesta;
            StringBuilder resul= new StringBuilder();
            try {
                url = new URL("http://marketing.repositoriomax.net/exportarbd.php");
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


            //POBLAR BASE DE DATOS SEGÚN LOS DATOS DEL JSON RECUPERADO
            try {
                JSONArray JSONARRAY_bd = new JSONArray(resul.toString());
                JSONArray JSONARRAY_empresas = JSONARRAY_bd.getJSONArray(0);
                JSONArray JSONARRAY_ofertas = JSONARRAY_bd.getJSONArray(1);
                JSONArray JSONARRAY_categoria = JSONARRAY_bd.getJSONArray(2);
                JSONArray JSONARRAY_estacionamientos = JSONARRAY_bd.getJSONArray(3);
                JSONArray JSONARRAY_stripcenters = JSONARRAY_bd.getJSONArray(4);
                JSONArray JSONARRAY_tipoestacionamientos = JSONARRAY_bd.getJSONArray(5);

                for(int i=0; i<JSONARRAY_empresas.length();i++){
                    JSONObject JSONOBJempresa = JSONARRAY_empresas.getJSONObject(i);
                    Empresa empresa = new Empresa();
                    empresa.setId(JSONOBJempresa.getInt("id"));
                    empresa.setNombre(JSONOBJempresa.getString("nombre"));
                    empresa.setRUT(JSONOBJempresa.getString("RUT"));
                    empresa.setDV(JSONOBJempresa.getString("DV"));
                    empresaDAO.insert(empresa);
                    Log.d("DEBUG","Empresa "+empresa.getId()+": "+empresa.getNombre()+" insertada con exito.");
                }

                for(int i=0; i<JSONARRAY_categoria.length();i++){
                    JSONObject JSONOBJcategoria = JSONARRAY_categoria.getJSONObject(i);
                    Categoria categoria = new Categoria();
                    categoria.setId(JSONOBJcategoria.getInt("id"));
                    categoria.setDescripcion(JSONOBJcategoria.getString("descripcion"));
                    categoriaDAO.insert(categoria);
                    Log.d("DEBUG","Categoria "+categoria.getId()+": "+categoria.getDescripcion()+" insertada con exito.");
                }

                for(int i=0; i<JSONARRAY_ofertas.length();i++){
                    JSONObject JSONOBJoferta = JSONARRAY_ofertas.getJSONObject(i);
                    Oferta oferta = new Oferta();
                    oferta.setId(JSONOBJoferta.getInt("id"));
                    oferta.setDescuento(Float.parseFloat(JSONOBJoferta.getString("descuento")));
                    oferta.setId_categoria(JSONOBJoferta.getInt("id_categoria"));
                    oferta.setId_empresa(JSONOBJoferta.getInt("id_empresa"));
                    ofertaDAO.insert(oferta);
                    Log.d("DEBUG","Oferta "+oferta.getId()+": "+oferta.getDescuento()+" insertada con exito.");
                }

                for(int i=0; i<JSONARRAY_tipoestacionamientos.length();i++){
                    JSONObject JSONOBJtipoestacionamiento = JSONARRAY_tipoestacionamientos.getJSONObject(i);
                    TipoEstacionamiento tipoestacionamiento = new TipoEstacionamiento();
                    tipoestacionamiento.setId(JSONOBJtipoestacionamiento.getInt("id"));
                    tipoestacionamiento.setDescripcion(JSONOBJtipoestacionamiento.getString("descripcion"));
                    tipoEstacionamientoDAO.insert(tipoestacionamiento);
                    Log.d("DEBUG","TipoEstacionamiento "+tipoestacionamiento.getId()+": "+tipoestacionamiento.getDescripcion()+" insertado con exito.");
                }

                for(int i=0; i<JSONARRAY_estacionamientos.length();i++){
                    JSONObject JSONOBJestacionamiento = JSONARRAY_estacionamientos.getJSONObject(i);
                    Estacionamiento estacionamiento = new Estacionamiento();
                    estacionamiento.setId(JSONOBJestacionamiento.getInt("id"));
                    estacionamiento.setNombre(JSONOBJestacionamiento.getString("nombre"));
                    estacionamiento.setLatitud(Double.parseDouble(JSONOBJestacionamiento.getString("latitud")));
                    estacionamiento.setLongitud(Double.parseDouble(JSONOBJestacionamiento.getString("longitud")));
                    estacionamiento.setId_tipo(JSONOBJestacionamiento.getInt("id_tipo"));
                    estacionamientoDAO.insert(estacionamiento);
                    Log.d("DEBUG","Estacionamiento "+estacionamiento.getId()+": "+estacionamiento.getNombre()+" insertado con exito.");
                }

                for(int i=0; i<JSONARRAY_stripcenters.length();i++){
                    JSONObject JSONOBJstripcenter = JSONARRAY_stripcenters.getJSONObject(i);
                    Stripcenter stripcenter = new Stripcenter();
                    stripcenter.setId(JSONOBJstripcenter.getInt("id"));
                    stripcenter.setId_empresa(JSONOBJstripcenter.getInt("id_empresa"));
                    stripcenter.setId_estacionamiento(JSONOBJstripcenter.getInt("id_estacionamiento"));
                    stripcenterDAO.insert(stripcenter);
                    Log.d("DEBUG","Stripcenter "+stripcenter.getId()+": "+stripcenter.getId_empresa()+", "+stripcenter.getId_estacionamiento()+" insertado con exito.");
                }

            } catch (JSONException e) {
                Log.d("DEBUG","Error en JSON: "+e);
            }





        } else if (currentVersionCode > savedVersionCode) {

            // TODO Esta es una upgrade (cambio de versión de la app)
        }

        // Actualizar las preferencias compartidas con el código de la versión actual
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }
}
