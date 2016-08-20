package classes.mtradutor.modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by henrique on 31/07/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String BANCO_DADOS = "Mtradutor";
    private static int VERSAO = 2;



    public static class traducao {
        public static final String TABELA = "traducao";
        public static final String id = "_id";
        public static final String ingles = "ingles";
        public static final String portugues = "portugues";
        public static final String numacesso = "numacesso";
        public static final String refIndex  = "refindex";
    }

    public DatabaseHelper(Context context) {
        super(context, BANCO_DADOS, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE traducao (" +
                "_id INTEGER PRIMARY KEY  AUTOINCREMENT ," +
                "ingles TEXT, " +
                "portugues TEXT, " +
                "numacesso INTERGER);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("ALTER TABLE traducao ADD COLUMN refindex TEXT");
    }





}
