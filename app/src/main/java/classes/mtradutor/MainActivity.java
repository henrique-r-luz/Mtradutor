package classes.mtradutor;

//import android.app.AlertDialog;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

//import android.support.v7.app.AlertDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.String;

import classes.mtradutor.dao.DaoTraducao;
import classes.mtradutor.modelo.DatabaseHelper;
import classes.mtradutor.modelo.Traducao;

public class MainActivity extends TamplateMtradutor implements AdapterView.OnItemClickListener {

    private List<String> listGroup;
    private HashMap<String, List<String>> listData;
    private TextView texto;
    private DatabaseHelper helper;
    private ListView lView;
    private String queryPesq = "";
    private FloatingActionButton myFab;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent searchIntent = getIntent();
        if (Intent.ACTION_SEARCH.equals(searchIntent.getAction())) {
            String query = searchIntent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
        }

        init();
        helper = new DatabaseHelper(this);


        client3 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

  /*  @Override
    //menu superioor
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }*/

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        item.setVisible(true);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Lucien", "Buscando " + query);
                MainActivity.this.queryPesq = query;
                eventEnter(MainActivity.this.queryPesq);
                //Toast.makeText(MainActivity.this, "ola", Toast.LENGTH_SHORT).show();
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        return true;
    }




    public void onResume() {  // After a pause OR at startup
        super.onResume();
        List<Traducao> frase = gerarFrases(this.queryPesq);
        if(this.queryPesq!=""){
            if (!(frase.isEmpty()) ){
                povoaLista(frase);
            }
        }

        // lView.setAdapter(null);

        //Refresh your stuff here


    }


    public void init() {
       // texto = (TextView) findViewById(R.id.texto);
        lView = (ListView) findViewById(R.id.palavrasList);
        myFab = (FloatingActionButton) findViewById(R.id.fab);

        setEventFloat(myFab);
        //eventos
      ///  this.setEventEnter(palavra);
    }


    //abre a tela de visualizaçao de significado da palavra
    public void viewSignificado(Traducao frase) {
        DaoTraducao daoTraducao = new DaoTraducao();
        frase.setNumAcessos(frase.getNumAcessos() + 1);
        daoTraducao.update(helper, frase, this);
        Intent intent = new Intent();
        intent.setClass(this, DescricaoActivity.class);
        intent.putExtra("myFrase", frase);
        startActivity(intent);
        //new AlertDialog.Builder(this).setTitle("Argh").setMessage("Watch out!").setNeutralButton("Close", null).show();
    }

    // seta evento do botão flutuante
    public void setEventFloat(FloatingActionButton fab) {
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, CadastraPalavraActivity.class);
                //intent.putExtra("titulo", str);
                startActivity(intent);
            }
        });
    }




    // dispara evento quando se pesquisa uma palavra
    private void eventEnter(String pesquisaText) {
       // Toast.makeText(MainActivity.this, pesquisaText, Toast.LENGTH_SHORT).show();
        //new AlertDialog.Builder(this).setTitle("texto").setMessage(palavra.getText()).setNeutralButton("Close", null).show();
        List<Traducao> frase = gerarFrases(pesquisaText);
        if (frase.isEmpty() ){//|| palavra.getText().toString().trim().isEmpty()) {
            new AlertDialog.Builder(this).setTitle("Messagem").setMessage("Não existe esta palavra").setNeutralButton("Close", null).show();
        } else {
            povoaLista(frase);

        }
        ///  new AlertDialog.Builder(this).setTitle("Argh").setMessage("Watch out!").setNeutralButton("Close", null).show();
    }


    private void povoaLista(List<Traducao> frase) {
        final ItensFrases fraseAdapter = new ItensFrases(this, frase);
        lView.setAdapter(fraseAdapter);
        lView.setOnItemClickListener(this);

    }


    private List<Traducao> gerarFrases(String pesquisa) {

        DaoTraducao daoTraducao = new DaoTraducao();
        return daoTraducao.listaTodos(helper, pesquisa);

    }

    protected void onDestroy() {
        helper.close();
        super.onDestroy();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView objtexto = (TextView) view.findViewById(R.id.titulo);
        Traducao frase = (Traducao) adapterView.getAdapter().getItem(i);
        viewSignificado(frase);
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client3.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://classes.mtradutor/http/host/path")
        );
        AppIndex.AppIndexApi.start(client3, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://classes.mtradutor/http/host/path")
        );
        AppIndex.AppIndexApi.end(client3, viewAction);
        client3.disconnect();
    }
}