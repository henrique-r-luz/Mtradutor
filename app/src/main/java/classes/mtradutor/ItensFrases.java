package classes.mtradutor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import classes.mtradutor.modelo.Traducao;

import java.util.List;

/**
 * Created by henrique on 28/02/16.
 */
public class ItensFrases extends ArrayAdapter<Traducao> {

    private Context context;
    private List<Traducao> objFrase = null;





    public ItensFrases (Context context,  List<Traducao> frase) {
        super(context,0, frase);
        this.objFrase = frase;
        this.context = context;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
         Traducao frase = objFrase.get(position);

        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.itens_frases, null);

        //ImageView imageViewZombie = (ImageView) view.findViewById(R.id.image_view_zombie);
        //imageViewZombie.setImageResource(zombie.getImagem());

        TextView textTitulo = (TextView) view.findViewById(R.id.titulo);
        TextView textAcessos = (TextView) view.findViewById(R.id.acessos);
        textTitulo.setText(frase.getIngles());
        String num = Integer.toString(frase.getNumAcessos());
        textAcessos.setText(Integer.toString(frase.getNumAcessos()));

      //  TextView textViewIdade = (TextView)view.findViewById(R.id.text_view_idade_zombie);
      //  String textoIdade = String.valueOf(zombie.getIdade()) + " " + context.getString(R.string.anos_idade);
      //  textViewIdade.setText(textoIdade);

        return view;
    }


    /**
     * Created by henrique on 21/08/16.
     */

}