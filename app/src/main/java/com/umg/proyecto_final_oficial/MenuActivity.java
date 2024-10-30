package com.umg.proyecto_final_oficial;

import android.content.ComponentName;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    private Button btnPlatos, btnBebidas, btnGaleria, btnEmpresa, btnMapa, btnPedido, btnUnete, btnfin;
    private SQLiteDatabase DbHelper;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_menu);

        // Inicializar cada botón y asignar su listener por separado
        btnPlatos = findViewById(R.id.btnPlatos);
        btnPlatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PlatosActivity.class));
            }
        });

        btnBebidas = findViewById(R.id.btnBebidas);
        btnBebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BebidasActivity.class));
            }
        });

        btnGaleria = findViewById(R.id.btnGaleria);
        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), GaleriaActivity.class));
            }
        });

        btnEmpresa = findViewById(R.id.btnEmpresa);
        btnEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EmpresaActivity.class));
            }
        });

        btnMapa = findViewById(R.id.btnMapa);
        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Coordenadas de la ubicación que deseas mostrar
                double latitud = 14.6349;  // Cambia esto por tu latitud
                double longitud = -90.5069; // Cambia esto por tu longitud

                // Crear el URI para abrir Google Maps en esa ubicación
                Uri gmmIntentUri = Uri.parse("geo:" + latitud + "," + longitud + "?q=" + latitud + "," + longitud + "(Nombre de la Ubicación)");

                // Crear el Intent
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps"); // Asegúrate de que se abra en la app de Google Maps

                // Iniciar la actividad
                startActivity(mapIntent);
            }
        });

        btnPedido = findViewById(R.id.btnPedido);
        btnPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toNumber = "502" + "58103302";
                Intent sendIntent = new Intent(Intent.ACTION_MAIN);
                sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(toNumber) + "@s.whatsapp.net");
                startActivity(sendIntent);
            }
        });

        btnUnete = findViewById(R.id.btnUnete);
        btnUnete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistroActivity.class));
            }
        });

        //
        btnfin = findViewById(R.id.btnfin);
        btnfin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FacturaActivity.class));
            }


        });

    }
}


