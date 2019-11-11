package com.example.gamer.smartpark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ofertas extends AppCompatActivity {

    TextView txtnombreofertas, txtdescripcion;
    Button btnvolver, btnoferta, btnmapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofertas);


        txtnombreofertas = (TextView) findViewById(R.id.txtnom);
        btnvolver = (Button) findViewById(R.id.btn_volver);
        btnoferta = (Button) findViewById(R.id.btn_oferta);
        btnmapa = (Button) findViewById(R.id.btn_mapa);

        final int id_empresa,id_estacionamiento;
        final String prueba;
        id_empresa=getIntent().getIntExtra("id_empresa",0);
        id_estacionamiento=getIntent().getIntExtra("id_estacionamiento",0);
        prueba= getIntent().getStringExtra("Bienvenido");

        btnmapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(ofertas.this,mapaestacionamientos.class).putExtra("id_estacionamiento",id_estacionamiento));
                startActivity(new Intent(getApplicationContext(),mapaestacionamientos.class));
            }
        });

        btnvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnoferta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),mostrarOfertas.class);
                intent.putExtra("id_empresa",id_empresa);
                intent.putExtra("Titulo",prueba);
                startActivity(intent);
            }
        });



        txtnombreofertas.setText(getIntent().getStringExtra("Bienvenido"));


    }



}


