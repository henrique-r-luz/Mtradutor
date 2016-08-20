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

import classes.mtradutor.dao.DaoTraducao;
import classes.mtradutor.modelo.DatabaseHelper;
import classes.mtradutor.modelo.Traducao;

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

            Traducao traducao = new Traducao();
            traducao.setIngles(ingles.getText().toString());
            traducao.setPortugues(portugues.getText().toString());
            traducao.setRefIndex(ingles.getText().toString().trim());
            DaoTraducao daoTraducao = new DaoTraducao();
            daoTraducao.salvar(helper,traducao,this);



        }
    }


    public boolean valida() {


        String strPortugues = portugues.getText().toString().trim();
        String strIngles = ingles.getText().toString().trim();

       DaoTraducao daoTraducao = new DaoTraducao();
       return daoTraducao.validaDados(strPortugues,strIngles,this,helper);
        //return !isEmptyFields(strPortugues, strIngles);

    }



    protected void onDestroy() {
        helper.close();
        super.onDestroy();
    }


}
