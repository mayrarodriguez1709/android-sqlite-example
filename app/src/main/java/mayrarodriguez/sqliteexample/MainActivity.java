package mayrarodriguez.sqliteexample;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText _etName = (EditText) findViewById(R.id.etName);
        final EditText _etLastname = (EditText) findViewById(R.id.etLastname);
        final EditText _etPersonNumber = (EditText) findViewById(R.id.etPersonNumber);
        Button _btSave = (Button) findViewById(R.id.btSave);
        Button _btDelete = (Button) findViewById(R.id.btDelete);
        Button _btSearch = (Button) findViewById(R.id.btSearch);
        Button _btEdit = (Button) findViewById(R.id.btEdit);


        /**
         * Boton para agregar datos (sin validaciones hasta ahora)
         *
         */
        _btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hacemos el llamado a los métodos de la DB
                PersonAdminDbHelper dbHelper = new PersonAdminDbHelper(getApplicationContext());

                //Creamos la DB
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                //Pasamos los datos que vamos a almacenar en la DB
                String _sName = _etName.getText().toString();
                String _sLastname = _etLastname.getText().toString();
                String _sPersonNumber = _etPersonNumber.getText().toString();

                //Creamos la clase para guardar los datos
                ContentValues personContent = new ContentValues();

                personContent.put("name", _sName);
                personContent.put("lastname", _sLastname);
                personContent.put("person_number", _sPersonNumber);

                //Guardamos el nuevo registro en la DB
                db.insert("persons", null, personContent);

                //Cerramos el proceso de la DB
                db.close();

                //limpiamos los campos
                _etName.setText("");
                _etLastname.setText("");
                _etPersonNumber.setText("");

                Toast.makeText(getApplicationContext(), "Datos guardados exitosamente", Toast.LENGTH_LONG).show();
            }
        });

        /**
         * Boton para buscar datos (sin validaciones hasta ahora)
         *
         */

        _btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hacemos el llamado a los métodos de la DB
                PersonAdminDbHelper dbHelper = new PersonAdminDbHelper(getApplicationContext());

                //Creamos la DB
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                //Pasamos los datos que vamos a consultar en la DB
                String _sPersonNumber = _etPersonNumber.getText().toString();

                //Seleccionamos las columnas que queremos retornar
                String[] projection = {
                    PersonContract.PersonEntry.COLUMN_NAME_NAME,
                    PersonContract.PersonEntry.COLUMN_NAME_LASTNAME,
                    PersonContract.PersonEntry.COLUMN_NAME_PERSON_NUMBER
                };

                //Seleccionamos la columna que evaluaremos
                String selection = PersonContract.PersonEntry.COLUMN_NAME_PERSON_NUMBER + " LIKE ?";

                //Seleccionamos el dato con el que queremos comparar
                String[] selectionArgs = {String.valueOf(_sPersonNumber)};

                //Creamos el cursor
                Cursor cursor = db.query(
                        PersonContract.PersonEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null

                );

                //Si el dato fué encontrado retorna un objeto
                if(cursor.moveToFirst()){
                    Toast.makeText(getApplicationContext(), "Persona encontrada", Toast.LENGTH_LONG).show();
                    _etName.setText(cursor.getString(0));
                    _etLastname.setText(cursor.getString(1));
                }else {
                    Toast.makeText(getApplicationContext(), "Persona NO encontrada", Toast.LENGTH_LONG).show();
                    _etName.setText("");
                    _etLastname.setText("");
                    _etPersonNumber.setText("");
                }

                /*long itemId = cursor.getLong(
                        cursor.getColumnIndexOrThrow(PersonContract.PersonEntry.COLUMN_NAME_NAME)
                );*/
            }
        });

        /**
         * Boton para Eliminar datos (sin validaciones hasta ahora)
         *
         */

        _btDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //Hacemos el llamado a los métodos de la DB
                PersonAdminDbHelper dbHelper = new PersonAdminDbHelper(getApplicationContext());

                //Creamos la DB
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                //Pasamos los datos que vamos a consultar en la DB
                String _sPersonNumber = _etPersonNumber.getText().toString();

                //Seleccionamos la columna que evaluaremos
                String selection = PersonContract.PersonEntry.COLUMN_NAME_PERSON_NUMBER + " LIKE ?";

                //Seleccionamos el dato con el que queremos comparar
                String[] selectionArgs = {String.valueOf(_sPersonNumber)};

                int cant = db.delete(PersonContract.PersonEntry.TABLE_NAME, selection, selectionArgs);

                _etName.setText("");
                _etLastname.setText("");
                _etPersonNumber.setText("");

                if (cant == 1){
                    Toast.makeText(getApplicationContext(), "Persona eliminada exitosamente", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Registro no encontrado", Toast.LENGTH_LONG).show();
                }
            }
        });

        _btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hacemos el llamado a los métodos de la DB
                PersonAdminDbHelper dbHelper = new PersonAdminDbHelper(getApplicationContext());

                //Creamos la DB
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                //Pasamos los datos que vamos a consultar en la DB
                String _sPersonNumber = _etPersonNumber.getText().toString();
                String _sName = _etName.getText().toString();
                String _sLastname = _etLastname.getText().toString();

                //Creamos la clase para guardar los nuevos datos
                ContentValues personNewContent = new ContentValues();
                personNewContent.put("name", _sName);
                personNewContent.put("lastname", _sLastname);

                //Seleccionamos la columna que evaluaremos
                String selection = PersonContract.PersonEntry.COLUMN_NAME_PERSON_NUMBER + " LIKE ?";

                //Seleccionamos el dato con el que queremos comparar
                String[] selectionArgs = {String.valueOf(_sPersonNumber)};

                int count = db.update(PersonContract.PersonEntry.TABLE_NAME, personNewContent, selection, selectionArgs);

                if (count == 1){
                    Toast.makeText(getApplicationContext(), "Persona actualizada exitosamente", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Registro no encontrado", Toast.LENGTH_LONG).show();
                }


            }
        });
    }




}
