package classes.mtradutor.modelo;

import java.io.Serializable;

/**
 * Created by henrique on 31/07/16.
 */
public class Traducao implements Serializable {



    private int id;
    private String ingles;
    private String portugues;
    private String refIndex;
    private int numAcessos;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIngles() {
        return ingles;
    }

    public void setIngles(String ingles) {
        this.ingles = ingles;
    }

    public String getPortugues() {
        return portugues;
    }

    public void setPortugues(String portugues) {
        this.portugues = portugues;
    }

    public int getNumAcessos() {
        return numAcessos;
    }

    public void setNumAcessos(int numAcessos) {
        this.numAcessos = numAcessos;
    }

    public String getRefIndex() {
        return refIndex;
    }

    public void setRefIndex(String refIndex) {
        this.refIndex = refIndex;
    }
}
