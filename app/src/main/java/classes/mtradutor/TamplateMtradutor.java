package classes.mtradutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by henrique on 31/07/16.
 */
public class TamplateMtradutor  extends AppCompatActivity {


    @Override
    //menu superioor
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    //menu superior
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nova_palavra) {
            viewCadastraPalavar();
            //return true;
            // Toast.makeText(getApplicationContext(), "teste setting", Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.estatistica){
           viewEstatistica();
        }
        if(id==R.id.inicio){
            viewInicio();
        }
        return super.onOptionsItemSelected(item);
    }



    // abre tela de cadastro de palavra
    public void viewCadastraPalavar() {
        Intent intent = new Intent();
        intent.setClass(this, CadastraPalavraActivity.class);
        //intent.putExtra("titulo", str);
        startActivity(intent);
    }

    //abre a tela de estatistica

    public void viewEstatistica(){
        Intent intent = new Intent();
        intent.setClass(this, EstatisticaActivity.class);
        //intent.putExtra("titulo", str);
        startActivity(intent);
    }


    public void viewInicio(){
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        //intent.putExtra("titulo", str);
        startActivity(intent);
    }

}
