package com.umg.proyecto_final_oficial;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.umg.proyecto_final_oficial.BaseDatos.DbHelper;

public class RegistroActivity extends AppCompatActivity {
    private DbHelper dbHelper;
    private EditText editTextName, editTextPhone, editTextEmail, editTextEducation;
    private Button btnCrear, btnLeer, btnActualizar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Inicialización de views
        dbHelper = new DbHelper(this);
        editTextName = findViewById(R.id.TextNombre);
        editTextPhone = findViewById(R.id.TextNumero);
        editTextEmail = findViewById(R.id.TexEmail);
        editTextEducation = findViewById(R.id.TextEscolar);
        btnCrear = findViewById(R.id.btnCrear);
        btnLeer = findViewById(R.id.btnLeer);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);

        btnCrear.setOnClickListener(v -> crearPersonal());
        btnLeer.setOnClickListener(v -> leerPersonal());
        btnActualizar.setOnClickListener(v -> actualizarPersonal());
        btnEliminar.setOnClickListener(v -> eliminarPersonal());
    }

    private void crearPersonal() {
        String name = editTextName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String email = editTextEmail.getText().toString();
        String education = editTextEducation.getText().toString();
        dbHelper.insertPersonal(name, phone, email, education);
        Toast.makeText(this, "Personal agregado", Toast.LENGTH_SHORT).show();
        limpiarCampos();
    }

    private void leerPersonal() {
        Cursor cursor = dbHelper.readPersonal();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No hay datos", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuilder builder = new StringBuilder();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String phone = cursor.getString(2);
            String email = cursor.getString(3);
            String education = cursor.getString(4);
            builder.append("ID: ").append(id).append(", Nombre: ").append(name).append(", Teléfono: ").append(phone)
                    .append(", Correo: ").append(email).append(", Educación: ").append(education).append("\n");
        }
        cursor.close();
        Toast.makeText(this, builder.toString(), Toast.LENGTH_LONG).show();
    }

    private void actualizarPersonal() {
        // Aquí puedes usar un diálogo o formulario para obtener el ID del registro a actualizar
        // Por simplicidad, asumiremos que el ID es 1 (puedes mejorar esto según tus necesidades)
        int id = 1; // Cambia esto para probar otros registros
        String name = editTextName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String email = editTextEmail.getText().toString();
        String education = editTextEducation.getText().toString();
        dbHelper.updatePersonal(id, name, phone, email, education);
        Toast.makeText(this, "Personal actualizado", Toast.LENGTH_SHORT).show();
        limpiarCampos();
    }

    private void eliminarPersonal() {
        // Aquí también puedes usar un diálogo para obtener el ID a eliminar
        int id = 1; // Cambia esto para probar otros registros
        dbHelper.deletePersonal(id);
        Toast.makeText(this, "Personal eliminado", Toast.LENGTH_SHORT).show();
    }

    private void limpiarCampos() {
        editTextName.setText("");
        editTextPhone.setText("");
        editTextEmail.setText("");
        editTextEducation.setText("");
    }
}
