package classes.mtradutor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


/**
 * Created by henrique on 11/01/16.
 */
public class DescricaoActivity extends TamplateMtradutor {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descricao);
        TextView unitTexto = (TextView) findViewById(R.id.unitTexto);
        TextView descricaoTexto = (TextView) findViewById(R.id.descricaoTexto);

        Intent myIntent = getIntent();
        String titulo = myIntent.getStringExtra("titulo");
        String descricao = myIntent.getStringExtra("descricao");
        unitTexto.setText(titulo);
        descricaoTexto.setText(descricao);


    }
}
