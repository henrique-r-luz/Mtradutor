package classes.mtradutor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by henrique on 28/02/16.
 */
public class ItensFrases extends ArrayAdapter<Frases> {

    private Context context;
    private List<Frases> zombies = null;





    public ItensFrases (Context context,  List<Frases> frase) {
        super(context,0, frase);
        this.zombies = frase;
        this.context = context;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Frases frase = zombies.get(position);

        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.itens_frases, null);

        //ImageView imageViewZombie = (ImageView) view.findViewById(R.id.image_view_zombie);
        //imageViewZombie.setImageResource(zombie.getImagem());

        TextView textTitulo = (TextView) view.findViewById(R.id.titulo);
        textTitulo.setText(frase.getTituloIngles());

      //  TextView textViewIdade = (TextView)view.findViewById(R.id.text_view_idade_zombie);
      //  String textoIdade = String.valueOf(zombie.getIdade()) + " " + context.getString(R.string.anos_idade);
      //  textViewIdade.setText(textoIdade);

        return view;
    }





}