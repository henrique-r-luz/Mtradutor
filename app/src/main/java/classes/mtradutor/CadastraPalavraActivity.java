package classes.mtradutor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import classes.mtradutor.modelo.DatabaseHelper;

/**
 * Created by henrique on 29/02/16.
 */
public class CadastraPalavraActivity extends TamplateMtradutor {



    private DatabaseHelper helper;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastra_palavra);

        helper = new DatabaseHelper(this);

    }




}
