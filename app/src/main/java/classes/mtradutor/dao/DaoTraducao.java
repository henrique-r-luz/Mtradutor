package classes.mtradutor.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import classes.mtradutor.modelo.DatabaseHelper;
import classes.mtradutor.modelo.Traducao;

/**
 * Created by henrique on 18/08/16.
 */
public class DaoTraducao {

    public  List<Traducao> listaTodos(DatabaseHelper helper){

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor =
                db.rawQuery("SELECT *" +
                                " FROM traducao",
                        null);
        cursor.moveToFirst();
        List<Traducao> frase = new ArrayList<Traducao>();
        for (int i = 0; i < cursor.getCount(); i++) {

            Traducao item = new Traducao();

            item.setId(cursor.getInt(0));
            item.setIngles(cursor.getString(1));
            item.setPortugues(cursor.getString(2));
            item.setNumAcessos(cursor.getInt(3));

            frase.add(item);
            cursor.moveToNext();

        }
        cursor.close();

        return frase;


    }


    public String excluir(int id, DatabaseHelper helper){

        SQLiteDatabase db = helper.getWritableDatabase();
        String where [] = new String[]{ Integer.toString(id)};
        db.delete("traducao", "_id = ?", where);
        return "1";

    }


    public void salvar(DatabaseHelper helper, Traducao frase, Context contexto){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("portugues", frase.getPortugues());
        values.put("ingles", frase.getIngles());
        values.put("numacesso", 0);
        long resultado = db.insert("traducao", null, values);

        if (resultado == -1) {
            Toast.makeText(contexto, "ocorreu um erro ao salvar os dados",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(contexto, "O dados foram salvos",
                    Toast.LENGTH_SHORT).show();

        }
    }
}
