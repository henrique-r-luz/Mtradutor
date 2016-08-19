package classes.mtradutor;

//import android.app.AlertDialog;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

//import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.String;

import classes.mtradutor.dao.DaoTraducao;
import classes.mtradutor.modelo.DatabaseHelper;
import classes.mtradutor.modelo.Traducao;

public class MainActivity extends TamplateMtradutor {

    private List<String> listGroup;
    private HashMap<String, List<String>> listData;
    private TextView texto;
    private DatabaseHelper helper;
    private EditText palavra;
    private ListView lView;
    private ArrayList<String> pesquisa = new ArrayList<String>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        init();
        helper = new DatabaseHelper(this);

        // lView.setAdapter(fraseAdapter);
        // lView.setAdapter(new ItensFrases(this,
        //       android.R.layout.simple_list_item_1, listarViagens()));


    }


    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        lView.setAdapter(null);

        //Refresh your stuff here


    }


    public void init(){
        texto = (TextView) findViewById(R.id.texto);
        palavra = (EditText) findViewById(R.id.palavra);
        lView = (ListView) findViewById(R.id.palavrasList);
        //eventos
        this.setEventEnter(palavra);
    }


    @Override
    //menu superioor
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    /*
       dispara esse evento quando pessiona o botão enter
    */
    //abre a tela de visualizaçao de significado da palavra
    public void viewSignificado(Traducao frase) {
        DaoTraducao daoTraducao = new DaoTraducao();
        frase.setNumAcessos(frase.getNumAcessos()+1);
        daoTraducao.update(helper,frase,this);
        Intent intent = new Intent();
        intent.setClass(this, DescricaoActivity.class);
        intent.putExtra("myFrase", frase);
        startActivity(intent);
        //new AlertDialog.Builder(this).setTitle("Argh").setMessage("Watch out!").setNeutralButton("Close", null).show();
    }




    /*
    verifica qual tecla foi pessinada
     */
    public void setEventEnter(EditText pesquisa) {

        pesquisa.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                    if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) ||
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        eventEnter();
                        //Toast.makeText(getApplicationContext(), "teste setting", Toast.LENGTH_SHORT).show();
                        // new AlertDialog.Builder(v).setTitle("Argh").setMessage("Watch out!").setNeutralButton("Close", null).show();
                        return true;
                    }
                return false;
            }
        });

    }

    // dispara evento quando se pesquisa uma palavra
    private void eventEnter() {

        //new AlertDialog.Builder(this).setTitle("texto").setMessage(palavra.getText()).setNeutralButton("Close", null).show();
        List<Traducao> frase = gerarFrases(palavra.getText().toString());

        if (frase.isEmpty()) {
            new AlertDialog.Builder(this).setTitle("Messagem").setMessage("Não existe esta palavra").setNeutralButton("Close", null).show();
        } else {
            final ItensFrases fraseAdapter = new ItensFrases(this, frase);
            lView.setAdapter(fraseAdapter);
            lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {

                    TextView objtexto = (TextView) view.findViewById(R.id.titulo);
                    Traducao frase =  (Traducao) arg0.getAdapter().getItem(position);
                    viewSignificado(frase);
                    //   new AlertDialog.Builder(MainActivity.this).setTitle("Argh").setMessage("Watch out!").setNeutralButton("Close", null).show();

                }
            });
        }
        ///  new AlertDialog.Builder(this).setTitle("Argh").setMessage("Watch out!").setNeutralButton("Close", null).show();
    }


    private List<Traducao> gerarFrases(String pesquisa) {

        DaoTraducao daoTraducao = new DaoTraducao();
        return daoTraducao.listaTodos(helper,pesquisa);

    }






    protected void onDestroy() {
        helper.close();
        super.onDestroy();
    }


}
