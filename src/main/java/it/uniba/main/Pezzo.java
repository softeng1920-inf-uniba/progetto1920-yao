package it.uniba.main;

/**
 * classe di tipo entity
 * responsabilita: include le informazioni di ogni pezzo,
 * tra cui la posizione, il colore, il simbolo, la gittata
 */
public abstract class Pezzo {
  private boolean enpassant;
  private int gittata;
  private Posizione posizione;
  private char nome;
  private int colore;
  private char simbolo;
  private boolean catturabileE;

  Pezzo(final int git, final int col, final char nom,
       final Posizione pos) {
    gittata = git;
    colore = col;
    nome = nom;
    posizione = pos;
  }

  public void setSimbolo(final char simb) {
    simbolo = simb;
  }

  public int getColore() {
    return colore;
  }

  public Posizione getPosizione() {
    return posizione;
  }

  public void setPosizione(final Posizione pos) {
    posizione = pos;
  }

  public char getNome() {
    return nome;
  }

  public void setGittata(final int git) {
    gittata = git;
  }

  public int getGittata() {
    return gittata;
  }

  public void setColore(final int col) {
    colore = col;
  }

  public void giaMosso() {
    if (enpassant) {
      enpassant = !enpassant;
    } else if (catturabileE && nome == 'p') {
      catturabileE = !catturabileE;
    }
  }

  public void setCatturabileE() {
    this.catturabileE = true;
  }

  public boolean getCatturabileE() {
    return catturabileE;
  }

  public boolean getEnpassant() {
    return enpassant;
  }

  public void setEmpoissonTrue() {
    enpassant = true;
  }

  public char getSimbolo() {
    return simbolo;
  }

}
