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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private List<String> listGroup;
    private HashMap<String, List<String>> listData;
    private TextView texto;
    private DatabaseHelper helper;
    private EditText palavra;
    private ListView lView;
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

        // lView.setAdapter(fraseAdapter);
        // lView.setAdapter(new ItensFrases(this,
        //       android.R.layout.simple_list_item_1, listarViagens()));


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client3 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    //menu superioor
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "ola", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        return true;
    }


        @Override
        //menu superior
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.nova_palavra) {
                // viewCadastraPalavar();
                //return true;
                // Toast.makeText(getApplicationContext(), "teste setting", Toast.LENGTH_SHORT).show();
            }
            if (id == R.id.estatistica) {
                //viewEstatistica();
            }
            if (id == R.id.inicio) {
                //viewInicio();
            }
       /* if (id == R.id.menu_search) {
            SearchView searchView = (SearchView) menu.findItem(R.id.menu_search)
              Toast.makeText(MainActivity.this, pesquisa.get, Toast.LENGTH_SHORT).show();
        }*/


            return super.onOptionsItemSelected(item);
        }


    public void onResume() {  // After a pause OR at startup
        super.onResume();
        List<Traducao> frase = gerarFrases(palavra.getText().toString());
        if (!(frase.isEmpty() || palavra.getText().toString().trim().isEmpty())) {
            povoaLista(frase);
        }

        // lView.setAdapter(null);

        //Refresh your stuff here


    }


    public void init() {
        texto = (TextView) findViewById(R.id.texto);
        palavra = (EditText) findViewById(R.id.palavra);
        lView = (ListView) findViewById(R.id.palavrasList);
        myFab = (FloatingActionButton) findViewById(R.id.fab);

        setEventFloat(myFab);
        //eventos
        this.setEventEnter(palavra);
    }


    //menu superioor
   /* public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;

    }*/


    /*
       dispara esse evento quando pessiona o botão enter
    */
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
        if (frase.isEmpty() || palavra.getText().toString().trim().isEmpty()) {
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