package classes.mtradutor.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import classes.mtradutor.modelo.DatabaseHelper;
import classes.mtradutor.modelo.Traducao;

/**
 * Created by henrique on 18/08/16.
 */
public class DaoTraducao {

    public  List<Traducao> listaTodos(DatabaseHelper helper, String pesquisa){

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor;
        if(pesquisa.equals("*")) {
            cursor = db.rawQuery("SELECT * FROM traducao",null);
        }else {
            cursor =
                    db.rawQuery("SELECT *" +
                                    " FROM traducao " +
                                    " where upper(ingles) like ?",
                            new String[]{"%" + pesquisa.toUpperCase().trim() + "%"});
        }
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
        values.put("ingles", frase.getIngles().toUpperCase());
       // values.put("refindex", frase.getRefIndex().toString());
        values.put("numacesso", 0);
        long resultado = db.insert("traducao", null, values);

        if (resultado == -1) {
            Toast.makeText(contexto, "ocorreu um erro ao salvar os dados",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(contexto, "O dados foram salvos",
                    Toast.LENGTH_SHORT).show();

        }
    }


    public void update(DatabaseHelper helper, Traducao frase, Context contexto){

        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("portugues", frase.getPortugues());
        values.put("ingles", frase.getIngles().toUpperCase());
        values.put("numacesso", frase.getNumAcessos());
        //values.put("refindex", frase.getRefIndex().toString());
        long resultado = db.update("traducao", values, "_id = ?", new String []{Integer.toString(frase.getId())});

        if (resultado == -1) {
            Toast.makeText(contexto, "ocorreu um erro ao salvar os dados",Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(contexto, "O dados foram salvos",
              //      Toast.LENGTH_SHORT).show();

        }

    }

    public boolean validaDados(String portugues , String ingles, Context contexto,DatabaseHelper helper){

        String strPortugues = portugues.trim();
        String strIngles = ingles.trim();


        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor =
//                db.rawQuery("SELECT *" +
  //                              " FROM traducao " +
   //                             " where upper(ingles) = '"+strIngles.toUpperCase()+"'",
                db.rawQuery("SELECT *" +
                                                      " FROM traducao " +
                                                     " where upper(ingles) =?",
                        new String []{strIngles.toUpperCase()});
        cursor.moveToFirst();
        Log.v("TAG", Integer.toString(cursor.getCount()));
        if(cursor.getCount()!=0){
            Toast.makeText(contexto, "Palavra já exite",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(strPortugues)){
            Toast.makeText(contexto, "Palavra em portugues é obrigatório",
                    Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(strIngles)){
            Toast.makeText(contexto, "Palavra em ingles é obrigatório",
                    Toast.LENGTH_SHORT).show();
        }



        if(TextUtils.isEmpty(strIngles) && TextUtils.isEmpty(strPortugues)){
            return false;
        }
        else{
            return true;
        }

    }

    public List<Traducao> rank(DatabaseHelper helper, String num){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor =
                db.rawQuery("SELECT *" +
                                " FROM traducao " +
                                " order by numacesso DESC limit "+num,
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

    public int contaPalavras(DatabaseHelper helper){

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor =
                db.rawQuery("SELECT count(*)" +
                                " FROM traducao ",
                        null);
        cursor.moveToFirst();
        int cont = cursor.getInt(0);
        cursor.close();
        return cont;

    }
}
