package com.example.gamer.smartpark;

import android.Manifest;
import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.example.gamer.smartpark.ofertas;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import Room.BaseDeDatos.BaseDeDatosApp;
import Room.DAO.CategoriaDAO;
import Room.DAO.EmpresaDAO;
import Room.DAO.OfertaDAO;

public class estacionamientos extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private Marker marcador;
    double lat = 0.0;
    double lng = 0.0;
    public Marker markerLider;
    public Marker markerAlvi;
    public Marker markerSI;
    public Marker markerSantaIsabelLC;
    public Marker markermallPlaza;

    ArrayList<Marker> Listadeestacionamientos = new ArrayList();
    ArrayList<String>Ofertas = new ArrayList();



    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estacionamientos);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        if (status == ConnectionResult.SUCCESS) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } else {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, (Activity) getApplicationContext(), 10);
            dialog.show();
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        miUbicacion();

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-29.9364559, -71.2525471)));

//        //Mi ubicación actual:
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            return;
//        }
//        mMap.setMyLocationEnabled(true);

        //SELECCIÓN TIPO DE MAPA:

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //ACTIVAR UTILIDADES DE GOOGLE MAPS:

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);

//
        //Add a marker in Sydney and move the camera
        LatLng Santaisabel = new LatLng(-29.978027, -71.346459);
        //mMap.addMarker(new MarkerOptions().position(Santaisabel).title("Estacionamiento Santa Isabel").snippet("Estacionamiento No Techado").icon(BitmapDescriptorFactory.fromResource(R.drawable.estacionamiento)));
        markerSI = googleMap.addMarker(new MarkerOptions().position(Santaisabel).title("Estacionamiento Santa Isabel").snippet("Estacionamiento No Techado").icon(BitmapDescriptorFactory.fromResource(R.drawable.estacionamiento)));

        LatLng Alvi = new LatLng(-29.959001,-71.338918);
       // mMap.addMarker(new MarkerOptions().position(Alvi).title("Estacionamiento Alvi Mayorista").snippet("Estacionamiento No Techado").icon(BitmapDescriptorFactory.fromResource(R.drawable.estacionamiento)));
        markerAlvi = googleMap.addMarker(new MarkerOptions().position(Alvi).title("Estacionamiento Alvi Mayorista").snippet("Estacionamiento No Techado").icon(BitmapDescriptorFactory.fromResource(R.drawable.estacionamiento)));

//        LatLng Lider = new LatLng(-29.9644,-71.33028409999997);
//        mMap.addMarker(new MarkerOptions().position(Lider).title("Estacionamiento Lider").snippet("Estacionamiento Techado Disponible").icon(BitmapDescriptorFactory.fromResource(R.drawable.estatechado)));

        LatLng SantaIsabelCompañias = new LatLng(-29.8884388,-71.24318629999999);
        //mMap.addMarker(new MarkerOptions().position(SantaIsabelCompañias).title("Estacionamiento Santa Isabel Las Compañías").snippet("Estacionamiento No Techado").snippet("Estacionamiento No Techado").icon(BitmapDescriptorFactory.fromResource(R.drawable.estacionamiento)));
        markerSantaIsabelLC = googleMap.addMarker(new MarkerOptions().position(SantaIsabelCompañias).title("Estacionamiento Santa Isabel Las Compañías").snippet("Estacionamiento No Techado").snippet("Estacionamiento No Techado").icon(BitmapDescriptorFactory.fromResource(R.drawable.estacionamiento)));

        LatLng MallPlaza = new LatLng(-29.9125806,-71.2582802);
       // mMap.addMarker(new MarkerOptions().position(MallPlaza).title("Estacionamiento Mall Plaza La Serena").snippet("Estacionamiento Techado Disponible").icon(BitmapDescriptorFactory.fromResource(R.drawable.estatechado)));
        markermallPlaza = googleMap.addMarker(new MarkerOptions().position(MallPlaza).title("Estacionamiento Mall Plaza La Serena").snippet("Estacionamiento Techado Disponible").icon(BitmapDescriptorFactory.fromResource(R.drawable.estatechado)));

        LatLng Lider = new LatLng(-29.9644,-71.33028409999997);
        markerLider = googleMap.addMarker(new MarkerOptions().position(Lider).title("Estacionamiento Lider").snippet("Estacionamiento Techado Disponible").icon(BitmapDescriptorFactory.fromResource(R.drawable.estatechado)));
        //markerprueba = googleMap.addMarker(new MarkerOptions().position(prueba).title("prueba"));

        mMap.setTrafficEnabled(true);
        //mMap.setInfoWindowAdapter(this);

        googleMap.setOnMarkerClickListener(this);
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(SantaIsabel));

        Listadeestacionamientos.add(markerAlvi);
        Listadeestacionamientos.add(markerLider);
        Listadeestacionamientos.add(markerSI);
        Listadeestacionamientos.add(markerSantaIsabelLC);
        Listadeestacionamientos.add(markermallPlaza);
        Arraycontroller.getInstance().setMarker(Listadeestacionamientos);
    }


    //Creación método para agergar un marcador en el map, creación de un objeto LATLTN, en el cual utilizando el elemento camera update
    private void agregarMarcador(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (marcador != null)
            marcador.remove(); //Si el marcador es diferente de null se deberá remover.
        marcador = mMap.addMarker(new MarkerOptions().position(coordenadas).title("Posición Actual").icon(BitmapDescriptorFactory.fromResource(R.drawable.pointerazul)));
        mMap.animateCamera(miUbicacion);

    }

    //Método para obtener la latitud y longitud de la posición actual.
    private void actualizarUbicacion(Location location) {
        if (location != null) { //Comprobar si la localización recibidad es diferente de null, antes de asginar el valor a las variables así evitar que la app se cierre al ejecutarla.
            lat = location.getLatitude();
            lng = location.getLongitude();
            agregarMarcador(lat, lng);
        }
    }


    //Implementación objeto LocationListener, tiene la función de estar atento a cualquier cambio de localidad recibido por el GPS.

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) { //Se lanza cada vez que recibe una actualización de la posición, llamamos al método actualizarUbicacion(), para poder refrescar la posición actual en el mapa.

            actualizarUbicacion(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

    };

    //Creación método referencial a la clase LocationManager utilizada para obetner servicios de geo posicionamiento en el dispositivo.
    //getLastKnowLocation obtendremos la última posición conocida.
    //requestLocationUpdates solicita al gps actualizaciones de posición cada 10 segundos.
    private void miUbicacion() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10000,0,locationListener);
     }


    @Override
    public boolean onMarkerClick(Marker marker) {

        for (final Marker MARKER : Listadeestacionamientos) {
            if (marker.equals(MARKER)) {
                Toast.makeText(estacionamientos.this, "PRESIONE PARA GENERAR LA RUTA MÁS RÁPIDA ---------------------" +
                        "-------------------" +
                        "-- ↓↓ ", Toast.LENGTH_LONG).show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        Intent intent = new Intent(estacionamientos.this, ofertas.class);
                        intent.putExtra("Bienvenido",MARKER.getTitle().toString());
                        startActivity(intent);

                        // Actions to do after 10 seconds
                    }
                }, 4000);





               // Toast.makeText(this, MARKER.getTitle(), Toast.LENGTH_LONG).show();

            }
        }
        return false;
    }

    public String enviarDatosGET(String nombremarker){
        URL url = null;
        String linea= "";
        int respuesta= 0;
        StringBuilder resul=null;


        try {
            url = new URL("http://smartpark.repositoriomax.net/get_data_oferta.php?");
            HttpURLConnection  connection = (HttpURLConnection)url.openConnection();
            respuesta= connection.getResponseCode();

            resul= new StringBuilder();

            if(respuesta== HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader= new BufferedReader(new InputStreamReader(in));

                while ((linea=reader.readLine())!=null){
                    resul.append(linea);
                }
            }


        }catch (IOException e){
            Toast.makeText(this,"IOException:"+e.getMessage() , Toast.LENGTH_SHORT).show();
        }
        return resul.toString();

    }









}
