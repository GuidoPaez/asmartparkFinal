package com.example.gamer.smartpark;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import Room.BaseDeDatos.BaseDeDatosApp;
import Room.Entidades.Empresa;
import Room.Entidades.Oferta;

public class mostrarOfertas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final TextView titulo;
        final ImageView logo;
        final TextView oferta;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_ofertas);


        titulo = (TextView) findViewById(R.id.txttitulo);
        logo = (ImageView) findViewById(R.id.logo);
        oferta = (TextView) findViewById(R.id.txtoferta);

        final int id_empresa=getIntent().getIntExtra("id_empresa",0);
        final BaseDeDatosApp database = BaseDeDatosApp.recuperarBaseDatosApp(getApplicationContext());
        oferta.setText("");

        Thread tr = new Thread(){
            @Override
            public void run() {

                Empresa empresa = database.getEmpresaDAO().getEmpresaPorId(id_empresa);
                final List<Oferta> ListaOfertas = database.getOfertaDAO().getOfertasPorEmpresa(id_empresa);

                titulo.setText("Ofertas Exclusivas "+empresa.getNombre());
                for (int i = 0 ; i < ListaOfertas.size() ; i++)
                {
                    Float porcentaje = ListaOfertas.get(i).getDescuento();
                    String categoria = database.getCategoriaDAO().getCategoriaPorId(ListaOfertas.get(i).getId_categoria()).getDescripcion();
                    oferta.setText(oferta.getText()+""+porcentaje+"% Descuento en "+categoria+"\n");

                    //oferta.setText(ListaOfertas.get(i).getDescuento()+"% Descuento en "+database.getCategoriaDAO().getCategoriaPorId(ListaOfertas.get(i).getId_categoria()).getDescripcion()+"\n");
                }
                switch (empresa.getId()){
                    case 1:
                        logo.setImageResource(R.drawable.lider);
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        logo.setImageResource(R.drawable.alvi);
                        break;
                    case 5:
                        logo.setImageResource(R.drawable.santaisabel);
                        break;
                    case 6:
                        logo.setImageResource(R.drawable.mall);
                        break;
                    default:
                        break;
                }


            }
        };
        tr.start();

    }
}
