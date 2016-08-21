package classes.mtradutor;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import classes.mtradutor.dao.DaoTraducao;
import classes.mtradutor.modelo.DatabaseHelper;
import classes.mtradutor.modelo.Traducao;


/**
 * Created by henrique on 11/01/16.
 */
public class DescricaoActivity extends TamplateMtradutor {


    private DatabaseHelper helper;
    private TextView unitTexto;
    private TextView descricaoTexto;
    private TextView numAcesso;
    private int id;
    private Traducao frase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descricao);
        unitTexto = (TextView) findViewById(R.id.unitTexto);
        descricaoTexto = (TextView) findViewById(R.id.descricaoTexto);
        numAcesso =  (TextView) findViewById(R.id.numeroAcesso);

        Intent myIntent = getIntent();
        this.frase = (Traducao) myIntent.getSerializableExtra("myFrase");
        this.id = frase.getId();
        unitTexto.setText(frase.getIngles());
        descricaoTexto.setText(frase.getPortugues());
        numAcesso.setText("Visualizações: "+frase.getNumAcessos());
        helper = new DatabaseHelper(this);

    }


    public void editar(View view){
        Intent intent = new Intent();
        intent.setClass(this, CadastroEditarActivity.class);
        intent.putExtra("myFrase", this.frase);
        startActivity(intent);


    }



    public void excluir(View view){


        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Você realmente quer excluir os dados?.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Sim",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       // daoTraducao.excluir(id,helper);
                        eventExcluir();
                    }
                });

        builder1.setNegativeButton(
                "Não",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }


    public void eventExcluir(){
        DaoTraducao daoTraducao = new DaoTraducao();
        daoTraducao.excluir(this.id,helper);
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Dados excluidos",Toast.LENGTH_SHORT).show();
    }



    protected void onDestroy() {
        helper.close();
        super.onDestroy();
    }
}
