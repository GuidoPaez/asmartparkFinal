package com.example.gamer.smartpark;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PagarEstacionamiento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar_estacionamiento);
    }

    public void webpay (View view){
        Toast.makeText(getApplicationContext(),"Redirigiendo a WebPay para Pago Seguro",Toast.LENGTH_LONG).show();
    }

    public void volver (View view){
        finish();
    }
}
