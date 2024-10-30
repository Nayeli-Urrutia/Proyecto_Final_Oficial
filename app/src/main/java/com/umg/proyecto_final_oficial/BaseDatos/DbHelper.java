package com.umg.proyecto_final_oficial.BaseDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "restaurant.db";
    private static final int DATABASE_VERSION = 3; // Incrementamos la versión de la base de datos

//TABLA CLIENTES

    public static final String TABLE_CLIENTES = "clientes";
    public static final String COLUMN_CLIENTE_ID = "id";
    public static final String COLUMN_CLIENTE_NAME = "nombre";
    public static final String COLUMN_CLIENTE_PHONE = "telefono";
    public static final String COLUMN_CLIENTE_EMAIL = "email";
    public static final String COLUMN_CLIENTE_DIRECCION = "direccion";

    // Tabla para el personal
    public static final String TABLE_PERSONAL = "personal";
    public static final String COLUMN_PERSONAL_ID = "id";
    public static final String COLUMN_PERSONAL_NAME = "name";
    public static final String COLUMN_PERSONAL_PHONE = "phone";
    public static final String COLUMN_PERSONAL_EMAIL = "email";
    public static final String COLUMN_PERSONAL_EDUCATION = "education_level";

    // Tabla para elementos seleccionados
    public static final String TABLE_SELECTED_ITEMS = "selected_items";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ITEM_NAME = "item_name";
    public static final String COLUMN_ITEM_PRICE = "item_price";
    public static final String COLUMN_ITEM_TYPE = "item_type"; // Para distinguir entre platos y bebidas

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Crear tabla de clientes
        String CREATE_TABLE_CLIENTES = "CREATE TABLE " + TABLE_CLIENTES + " (" +
                COLUMN_CLIENTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CLIENTE_NAME + " TEXT NOT NULL, " +
                COLUMN_CLIENTE_PHONE + " TEXT NOT NULL, " +
                COLUMN_CLIENTE_EMAIL + " TEXT NOT NULL, " +
                COLUMN_CLIENTE_DIRECCION + " TEXT NOT NULL)"; // Incluir la columna de dirección y asegurarse de que no sea nula
        db.execSQL(CREATE_TABLE_CLIENTES);

// Crear tabla de personal
        String CREATE_TABLE_PERSONAL = "CREATE TABLE " + TABLE_PERSONAL + " (" +
                COLUMN_PERSONAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PERSONAL_NAME + " TEXT NOT NULL, " +
                COLUMN_PERSONAL_PHONE + " TEXT NOT NULL, " +
                COLUMN_PERSONAL_EMAIL + " TEXT NOT NULL, " +
                COLUMN_PERSONAL_EDUCATION + " TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_PERSONAL);

// Crear tabla de elementos seleccionados
        String CREATE_TABLE_SELECTED_ITEMS = "CREATE TABLE " + TABLE_SELECTED_ITEMS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEM_NAME + " TEXT NOT NULL, " +
                COLUMN_ITEM_PRICE + " REAL NOT NULL, " + // Cambiado a REAL para almacenar valores decimales
                COLUMN_ITEM_TYPE + " TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_SELECTED_ITEMS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SELECTED_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTES);
        onCreate(db);
    }

    // Métodos para CRUD en tabla personal
    public void insertPersonal(String name, String phone, String email, String education) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PERSONAL_NAME, name);
        values.put(COLUMN_PERSONAL_PHONE, phone);
        values.put(COLUMN_PERSONAL_EMAIL, email);
        values.put(COLUMN_PERSONAL_EDUCATION, education);
        db.insert(TABLE_PERSONAL, null, values);
        db.close();
    }

    public Cursor readPersonal() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PERSONAL, null);
    }

    public void updatePersonal(int id, String name, String phone, String email, String education) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PERSONAL_NAME, name);
        values.put(COLUMN_PERSONAL_PHONE, phone);
        values.put(COLUMN_PERSONAL_EMAIL, email);
        values.put(COLUMN_PERSONAL_EDUCATION, education);
        db.update(TABLE_PERSONAL, values, COLUMN_PERSONAL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deletePersonal(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PERSONAL, COLUMN_PERSONAL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Método para agregar un item en la tabla selected_items
    public void insertSelectedItem(String itemName, double itemPrice, String itemType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, itemName);
        values.put(COLUMN_ITEM_PRICE, itemPrice);
        values.put(COLUMN_ITEM_TYPE, itemType);
        db.insert(TABLE_SELECTED_ITEMS, null, values);
        db.close();
    }

    // Método para obtener todos los items seleccionados
    public Cursor getSelectedItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_SELECTED_ITEMS, null);
    }

    // Método para calcular el total de la factura
    public double getTotalPrice() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_ITEM_PRICE + ") FROM " + TABLE_SELECTED_ITEMS, null);
        double total = 0;
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        cursor.close();
        return total;
    }

    // Método para insertar un cliente
    public void insertCliente(String nombre, String telefono, String email, String direccion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLIENTE_NAME, nombre);
        values.put(COLUMN_CLIENTE_PHONE, telefono);
        values.put(COLUMN_CLIENTE_EMAIL, email);
        values.put(COLUMN_CLIENTE_DIRECCION, direccion);
        db.insert(TABLE_CLIENTES, null, values);
        db.close();
    }

    // Métodos para leer clientes
    public Cursor readClientes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CLIENTES, null);

    }

    // Método para limpiar la tabla selected_items después de generar la factura
    public void clearSelectedItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SELECTED_ITEMS, null, null);
        db.close();
    }

}
