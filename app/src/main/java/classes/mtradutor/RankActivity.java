package classes.mtradutor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import classes.mtradutor.dao.DaoTraducao;
import classes.mtradutor.modelo.DatabaseHelper;
import classes.mtradutor.modelo.Traducao;

/**
 * Created by henrique on 21/08/16.
 */
public class RankActivity  extends TamplateMtradutor implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener{


    public DatabaseHelper helper;
    public List<String> numTop = new ArrayList<String>();
    private ListView lView;


    protected void onCreate(Bundle savedInstanceState) {
        numTop.add("10");
        numTop.add("20");
        numTop.add("30");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rank);
        Spinner spinner = (Spinner) findViewById(R.id.top);
       // spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, numTop);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);
        helper = new DatabaseHelper(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
       String num =  (String) adapterView.getAdapter().getItem(i);
        this.lView = (ListView) findViewById(R.id.lView);
        List<Traducao> frase = gerarFrase(num);
        final ItensFrases fraseAdapter = new ItensFrases(this, frase);
        lView.setAdapter(fraseAdapter);
        lView.setOnItemClickListener(this);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public  List<Traducao> gerarFrase(String num){
        DaoTraducao daoTraducao = new DaoTraducao();
        return daoTraducao.rank(helper, num);
    }

    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here


    }



    protected void onDestroy() {
        helper.close();
        super.onDestroy();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
