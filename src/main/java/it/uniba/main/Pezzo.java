package it.uniba.main;

public abstract class Pezzo {
    private  boolean enpassant;
    private int gittata;
    private Posizione posizione;
    private char nome;
    private int colore;
    private char simbolo;
    private  boolean catturabileE;
    Pezzo(int gittata , int colore , char nome , Posizione posizione){
        this.gittata = gittata;
        this.colore = colore;
        this.nome = nome;
        this.posizione = posizione;
    }

    public void setSimbolo(char simbolo){
        this.simbolo = simbolo;
    }

    public int getColore() {
        return colore;
    }

    public Posizione getPosizione() {
        return posizione;
    }

    public void setPosizione(Posizione posizione) {
        this.posizione = posizione;
    }

    public char getNome() {
        return nome;
    }

    public void setGittata(int gittata) {
        this.gittata = gittata;
    }

    public int getGittata() {
        return gittata;
    }

    public void giaMosso(){
        if (enpassant){
            enpassant = !enpassant;
            setGittata(1);
        }
        else if (catturabileE){
            catturabileE = !catturabileE;
        }
    }

    public void setCatturabileE() {
        this.catturabileE = true;
    }

    public boolean getCatturabileE(){
        return  catturabileE;
    }

    public  boolean getEnpassant(){
        return enpassant;
    }

    public void setEmpoissonTrue() {
        enpassant = true;
    }

    public char getSimbolo(){
        return simbolo;
    }

}
