package mayrarodriguez.sqliteexample;

import android.provider.BaseColumns;

/**
 * Clase para realizar la estructura de la tabla PERSONS
 *
 * Created by mayrarodriguez1709@gmail.com on 30/7/2016.
 */
public final class PersonContract {

    public PersonContract(){ }

    public static abstract class PersonEntry implements BaseColumns{

        public static final String TABLE_NAME = "persons";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_LASTNAME = "lastname";
        public static final String COLUMN_NAME_PERSON_NUMBER = "person_number";
    }

}
