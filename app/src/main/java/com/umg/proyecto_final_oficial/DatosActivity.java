package com.umg.proyecto_final_oficial;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.umg.proyecto_final_oficial.BaseDatos.DbHelper;

public class DatosActivity extends AppCompatActivity {
    private DbHelper dbHelper;
    private EditText textxNombre, textxTelefono, textxEmail, editTextDireccion;
    private Button buttonGuardar, btnSeguir; // Cambiamos los nombres de los botones

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        // Inicialización de views
        dbHelper = new DbHelper(this);
        textxNombre = findViewById(R.id.TextxNombre);
        textxTelefono = findViewById(R.id.TextxTelefono);
        textxEmail = findViewById(R.id.TextxEmail);
        editTextDireccion = findViewById(R.id.editTextDireccion);
        buttonGuardar = findViewById(R.id.buttonGuardar); // Botón para guardar datos
        btnSeguir = findViewById(R.id.btnSeguir); // Botón para continuar a Factura

        buttonGuardar.setOnClickListener(v -> crearCliente()); // Guardar datos
        btnSeguir.setOnClickListener(v -> irAFactura()); // Pasar a la actividad
    }

    private void crearCliente() {
        String nombre = textxNombre.getText().toString();
        String telefono = textxTelefono.getText().toString();
        String email = textxEmail.getText().toString();
        String direccion = editTextDireccion.getText().toString();

        // Aquí debes asegurarte de que dbHelper tenga el método insertCliente implementado
        dbHelper.insertCliente(nombre, telefono, email, direccion);
        Toast.makeText(this, "Cliente agregado", Toast.LENGTH_SHORT).show();

    }


    private void irAFactura() {
        // Pasamos directamente a la actividad de ActivityMenu
        Intent intent = new Intent(DatosActivity.this, MenuActivity.class);
        startActivity(intent);
    }


}
