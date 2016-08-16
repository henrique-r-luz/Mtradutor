package classes.mtradutor;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;



import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import classes.mtradutor.modelo.DatabaseHelper;

/**
 * Created by henrique on 29/02/16.
 */
public class CadastraPalavraActivity extends TamplateMtradutor {


    private DatabaseHelper helper;
    private TextView portugues;
    private TextView ingles;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastra_palavra);

        this.portugues = (TextView) findViewById(R.id.portuguestext);
        this.ingles = (TextView) findViewById(R.id.inglesText);


        helper = new DatabaseHelper(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }


    public void salvar(View view) {
        if(valida()) {
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("portugues", portugues.getText().toString());
            values.put("ingles", ingles.getText().toString());
            values.put("numacesso", 0);
            long resultado = db.insert("traducao", null, values);

            if (resultado == -1) {
                Toast.makeText(this, "ocorreu um erro ao salvar os dados",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "O dados foram salvos",
                        Toast.LENGTH_SHORT).show();
                portugues.setText("");
                ingles.setText("");
            }
        }
    }


    public boolean valida() {


        String strPortugues = portugues.getText().toString().trim();
        String strIngles = ingles.getText().toString().trim();

        if(TextUtils.isEmpty(strPortugues)){
            Toast.makeText(this, "Palavra em portugues é obrigatório",
                    Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(strIngles)){
            Toast.makeText(this, "Palavra em ingles é obrigatório",
                    Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(strIngles) && TextUtils.isEmpty(strPortugues)){
            return false;
        }
        else{
            return true;
        }
        //return !isEmptyFields(strPortugues, strIngles);

    }



    protected void onDestroy() {
        helper.close();
        super.onDestroy();
    }


}
