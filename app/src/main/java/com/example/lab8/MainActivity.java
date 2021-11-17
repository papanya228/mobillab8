package com.example.lab8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button buttonAdd,buttonRead,buttonClear,buttonDelete,buttonUpdate;
    EditText etId,etName,etPhone;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAdd = (Button) findViewById(R.id.addButton);
        buttonAdd.setOnClickListener(this::Add);

        buttonRead = (Button) findViewById(R.id.readButton);
        buttonRead.setOnClickListener(this::Read);

        buttonDelete = (Button) findViewById(R.id.deleteButton);
        buttonDelete.setOnClickListener(this::Del);

        etId = (EditText) findViewById(R.id.idTextView);
        etName = (EditText) findViewById(R.id.nameTextView);
        etPhone = (EditText) findViewById(R.id.PhoneTextView);

        dbHelper = new DBHelper(this);

    }

    private void Read(View view) {
        String ID = etId.getText().toString();
        String name = etName.getText().toString();
        String email =  etPhone.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Cursor cursor = database.query(DBHelper.TABLE_USERS, null, null, null,
                null, null, null); // все поля без сортировки и группировки

        if (cursor.moveToFirst())
        {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int emailIndex = cursor.getColumnIndex(DBHelper.KEY_PHONE);
            do {
                Log.d("mLog", "ID =" + cursor.getInt(idIndex) +
                        ", name = " + cursor.getString(nameIndex) +
                        ", email = " + cursor.getString(emailIndex));

            } while (cursor.moveToNext());
        } else
            Log.d("mLog", "0 rows");

        cursor.close();

        dbHelper.close();

    }

    private void Add(View view) {
        String ID = etId.getText().toString();
        String name = etName.getText().toString();
        String email = etPhone.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_NAME, name);
        contentValues.put(DBHelper.KEY_PHONE, email);
        database.insert(DBHelper.TABLE_USERS, null, contentValues);

        dbHelper.close();

    }

    private void Del(View view){
        String ID = etId.getText().toString();
        String name = etName.getText().toString();
        String email = etPhone.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        if (!ID.equalsIgnoreCase(""))
        {
            int delCount = database.delete(DBHelper.TABLE_USERS, DBHelper.KEY_ID + "= " + ID, null);
            Log.d("mLog", "Удалено строк = " + delCount);
        }

        dbHelper.close();

    }




}