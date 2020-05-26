package it.uniba.main;

/**
 * classe di tipo entity
 * responsabilita: rappresenta le coordinate di un
 * pezzo nella scacchiera
 */
public class Posizione {
  private int riga;
  private int colonna;

  public Posizione(final int rig, final int colon) {
    riga = rig;
    colonna = colon;
  }

  public void setColonna(final int colon) {
    colonna = colon;
  }

  public void setRiga(final int rig) {
    riga = rig;
  }

  public int getColonna() {
    return colonna;
  }

  public int getRiga() {
    return riga;
  }

}
