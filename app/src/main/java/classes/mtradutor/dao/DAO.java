package classes.mtradutor.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import classes.mtradutor.modelo.DatabaseHelper;


/**
 * Created by henrique on 31/07/16.
 */

//vericar padrão singleton
public class DAO {

    private DatabaseHelper helper;

    private SQLiteDatabase db;

    public DAO(Context context){
        helper = new DatabaseHelper(context);
    }

    private SQLiteDatabase getDb() {
        if (db == null) {
            db = helper.getWritableDatabase();
        }
        return db;
    }

    public void close(){
        helper.close();
    }
}
