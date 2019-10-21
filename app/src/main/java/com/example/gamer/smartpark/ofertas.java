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

        btnmapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ofertas.this,mapaestacionamientos.class));
            }
        });

        btnvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ofertas.this,estacionamientos.class));
            }
        });
        final String prueba;
        prueba= getIntent().getStringExtra("Bienvenido");
        btnoferta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),mostrarOfertas.class);
                intent.putExtra("Titulo",prueba);
                startActivity(intent);
            }
        });



        txtnombreofertas.setText(getIntent().getStringExtra("Bienvenido"));


    }



}


