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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descricao);
        unitTexto = (TextView) findViewById(R.id.unitTexto);
        descricaoTexto = (TextView) findViewById(R.id.descricaoTexto);
        numAcesso =  (TextView) findViewById(R.id.numeroAcesso);

        Intent myIntent = getIntent();
        Traducao frase = (Traducao) myIntent.getSerializableExtra("myFrase");
        unitTexto.setText(frase.getIngles());
        descricaoTexto.setText(frase.getPortugues());
        numAcesso.setText("Visualuzações: "+frase.getNumAcessos());
        helper = new DatabaseHelper(this);

    }


    public void editar(){

    }



    public void excluir(View view){


        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Você realmente quer excluir os dados?.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Sim",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
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


    protected void onDestroy() {
        helper.close();
        super.onDestroy();
    }
}
