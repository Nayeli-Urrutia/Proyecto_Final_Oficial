package com.umg.proyecto_final_oficial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;
import com.umg.proyecto_final_oficial.BaseDatos.DbHelper;

public class BebidasActivity extends AppCompatActivity {

    private DbHelper dbHelper;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bebidas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DbHelper(this);

        // Aquí puedes añadir un Listener para cada tarjeta
        MaterialCardView cardView1 = findViewById(R.id.cardViewBebida1);
        cardView1.setOnClickListener(v -> {
            String itemName = getString(R.string.TextBebi);
            String itemPrice = getString(R.string.pricebebi1);
            saveSelection(itemName, itemPrice, "Bebida");
            showAddedMessage(itemName); // Mostrar mensaje de agregado
        });



        MaterialCardView cardView2 = findViewById(R.id.cardViewBebida2);
        cardView2.setOnClickListener(v -> {
            String itemName = getString(R.string.TextBebi2);
            String itemPrice = getString(R.string.pricebebi2);
            saveSelection(itemName, itemPrice, "Bebida");
            showAddedMessage(itemName); // Mostrar mensaje de agregado
        });

        MaterialCardView cardView3 = findViewById(R.id.cardViewBebida3);
        cardView3.setOnClickListener(v -> {
            String itemName = getString(R.string.TextBebi3);
            String itemPrice = getString(R.string.pricebebi3);
            saveSelection(itemName, itemPrice, "Bebida");
            showAddedMessage(itemName); // Mostrar mensaje de agregado
        });
    }

    private void saveSelection(String itemName, String itemPrice, String itemType) {
        try {
            // Convertir el precio de String a double
            double price = Double.parseDouble(itemPrice.replace("Q", "").trim()); // Asegúrate de ajustar según el formato real de precio
            dbHelper.insertSelectedItem(itemName, price, itemType); // Ahora pasamos el precio como double
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Error al agregar el precio de la bebida.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showAddedMessage(String itemName) {
        Toast.makeText(this, itemName + " ha sido agregado a la orden.", Toast.LENGTH_SHORT).show();
    }


}
