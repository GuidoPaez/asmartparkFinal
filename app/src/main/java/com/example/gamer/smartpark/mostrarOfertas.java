package com.example.gamer.smartpark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class mostrarOfertas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        TextView titulo;
        ImageView logo;
        TextView oferta;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_ofertas);


        titulo = (TextView) findViewById(R.id.txttitulo);
        logo = (ImageView) findViewById(R.id.logo);
        oferta = (TextView) findViewById(R.id.txtoferta);

        String titulomarker = getIntent().getStringExtra("Titulo");
            if (titulomarker.equals("Estacionamiento Alvi Mayorista")) {
                titulo.setText("Ofertas Exclusivas Alvi \uD83D\uDED2");
                oferta.setText("50% Descuento en carnes mostrando Licencia de conducir ☺");
                logo.setImageResource(R.drawable.alvi);

            } else if (titulomarker.equals("Estacionamiento Lider")) {
                titulo.setText("Ofertas Exclusivas Lider! \uD83D\uDED2 \uD83E\uDD64 ");
                oferta.setText("2x1 Lacteos y cereales mostrando su Ubicación dentro del estacionamiento en caja ☺");
                logo.setImageResource(R.drawable.lider);

            } else if (titulomarker.equals("Estacionamiento Santa Isabel Las Compañías")) {
                titulo.setText("Ofertas Exclusivas Santa Isabel Las Compañías ♫");
                oferta.setText("30% descuento al final de su compra, mostrando su Ubicación dentro del estacionamiento en caja");
                logo.setImageResource(R.drawable.santaisabel);

            } else if (titulomarker.equals("Estacionamiento Mall Plaza La Serena")) {
                titulo.setText("Ofertas Exclusivas Mall Plaza! \uD83D\uDCB5 \uD83D\uDECD ");
                oferta.setText("50% Descuento en carnes mostrando Licencia de conducir ☺");
                logo.setImageResource(R.drawable.mall);
            } else{
                titulo.setText("Ofertas Exclusivas Santa Isabel Coquimbo! \uD83D\uDED2 \uD83D\uDED2 \uD83D\uDCB5  ");
                oferta.setText("3x2 en bebidas, todas las marcas, mostrando su Ubicación dentro del estacionamiento en caja ♥");
                logo.setImageResource(R.drawable.santaisabel);
       }

    }
}
