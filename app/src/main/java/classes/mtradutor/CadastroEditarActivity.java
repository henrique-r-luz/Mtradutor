package classes.mtradutor;

import android.content.ContentValues;
import android.content.Intent;
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
public class CadastroEditarActivity extends TamplateMtradutor {


    private DatabaseHelper helper;
    private TextView portugues;
    private TextView ingles;
    private Traducao frase;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_editar);
        Intent myIntent = getIntent();
        this.frase = (Traducao) myIntent.getSerializableExtra("myFrase");
        this.portugues = (TextView) findViewById(R.id.portuguestext);
        this.ingles = (TextView) findViewById(R.id.inglesText);
        this.portugues.setText(this.frase.getPortugues());
        this.ingles.setText(this.frase.getIngles());

        helper = new DatabaseHelper(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }


    public void salvar(View view) {
        if(valida()) {

            Traducao traducao = new Traducao();
            traducao.setId(frase.getId());
            traducao.setIngles(ingles.getText().toString());
            traducao.setPortugues(portugues.getText().toString());
            traducao.setNumAcessos(frase.getNumAcessos());
            traducao.setRefIndex(ingles.getText().toString().trim());
            DaoTraducao daoTraducao = new DaoTraducao();
            daoTraducao.update(helper,traducao,this);
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Os dados foram salvos",Toast.LENGTH_SHORT).show();



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
