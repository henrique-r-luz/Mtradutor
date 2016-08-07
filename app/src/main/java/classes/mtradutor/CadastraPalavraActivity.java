package classes.mtradutor;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import classes.mtradutor.modelo.DatabaseHelper;

/**
 * Created by henrique on 29/02/16.
 */
public class CadastraPalavraActivity extends TamplateMtradutor {



    private DatabaseHelper helper;
    private TextView portugues;
    private TextView ingles;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastra_palavra);

        this.portugues  = (TextView) findViewById(R.id.portuguestext);
        this.ingles = (TextView) findViewById(R.id.inglesText);


        helper = new DatabaseHelper(this);

    }


    public void salvar(View view){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("portugues", portugues.getText().toString());
        values.put("ingles", ingles.getText().toString());
        values.put("numacesso", 0);
        long resultado = db.insert("traducao", null, values);

        if(resultado==-1){
            Toast.makeText(this, "ocorreu um erro ao salvar os dados",
                    Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "O dados foram salvos",
                    Toast.LENGTH_SHORT).show();
        }
    }




}
