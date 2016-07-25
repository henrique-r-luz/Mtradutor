package classes.mtradutor;

/**
 * Created by henrique on 28/02/16.
 */
public class Frases {


    private String tituloIngles;
    private String conteudo;


    public Frases(String titulo, String conteudo){
           this.setTituloIngles(titulo);
          this.setConteudo(conteudo);
    }

    public String getTituloIngles() {
        return tituloIngles;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setTituloIngles(String tituloIngles) {
        this.tituloIngles = tituloIngles;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }



}
