package com.umg.proyecto_final_oficial;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.umg.proyecto_final_oficial.BaseDatos.DbHelper;

import java.util.ArrayList;

public class FacturaActivity extends AppCompatActivity {

    private DbHelper dbHelper;
    private ListView listViewFactura;
    private TextView txtTotalFactura;
    private Button buttonListo; // Botón "Listo"
    private TextView txtClienteInfo; // TextView para información del cliente

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);

        dbHelper = new DbHelper(this);

        listViewFactura = findViewById(R.id.listViewFactura);
        txtTotalFactura = findViewById(R.id.txtTotalFactura);
        buttonListo = findViewById(R.id.buttonListo); // Inicializar botón
        txtClienteInfo = findViewById(R.id.txtClienteInfo); // Inicializar TextView para cliente

        // Recibir los datos del Intent
        String nombreCliente = getIntent().getStringExtra("nombre");
        String telefonoCliente = getIntent().getStringExtra("telefono");
        String emailCliente = getIntent().getStringExtra("email");
        String direccionCliente = getIntent().getStringExtra("direccion");

        // Verifica y asigna valores a la información del cliente
        String clienteInfo = "Nombre: " + (nombreCliente != null ? nombreCliente : "No disponible") +
                "\nTeléfono: " + (telefonoCliente != null ? telefonoCliente : "No disponible") +
                "\nEmail: " + (emailCliente != null ? emailCliente : "No disponible") +
                "\nDirección: " + (direccionCliente != null ? direccionCliente : "No disponible");
        txtClienteInfo.setText(clienteInfo);

        // Configurar el botón "Listo"
        buttonListo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Limpiar la base de datos de la factura
                dbHelper.clearSelectedItems(); // Método para limpiar los elementos seleccionados
                finish(); // Cerrar la actividad
            }
        });

        // Cargar los datos de la factura
        cargarFactura();
    }

    private void cargarFactura() {
        ArrayList<String> productos = new ArrayList<>();
        double total = 0.0;

        // Consulta la base de datos
        Cursor cursor = dbHelper.getSelectedItems();

        // Comprobar si el cursor es nulo o vacío
        if (cursor != null && cursor.moveToFirst()) {
            // Imprimir el número de columnas
            int columnCount = cursor.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                String columnName = cursor.getColumnName(i);
                Log.d("FacturaActivity", "Columna: " + columnName);
            }

            do {
                //
                int nameIndex = cursor.getColumnIndex(DbHelper.COLUMN_ITEM_NAME);
                int priceIndex = cursor.getColumnIndex(DbHelper.COLUMN_ITEM_PRICE);

                //
                if (nameIndex != -1 && priceIndex != -1) {
                    String nombreProducto = cursor.getString(nameIndex);
                    double precioProducto = cursor.getDouble(priceIndex);
                    productos.add(nombreProducto + " - Q" + precioProducto);
                    total += precioProducto; // Sumar precio al total
                } else {
                    Log.e("FacturaActivity", "Error: Column index is -1 for name or price.");
                }
            } while (cursor.moveToNext());
        } else {
            Log.e("FacturaActivity", "Error: No se encontraron elementos o cursor es nulo.");
        }

        if (cursor != null) {
            cursor.close(); // Cerrar el cursor si no es nulo
        }

        // Configurar el ListView con los productos
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productos);
        listViewFactura.setAdapter(adapter);

        // Mostrar el total en el TextView
        txtTotalFactura.setText("Total: Q" + total);
    }
}
