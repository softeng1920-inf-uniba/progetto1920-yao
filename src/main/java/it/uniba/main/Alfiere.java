package it.uniba.main;

/* classe di tipo entity
 * responsabilita: rappresenta il pezzo di tipo alfiere
 */

public class Alfiere extends Pezzo {
  public Alfiere(final int colore, final Posizione pos) {
    super(8, colore, 'B', pos);
    if (colore == 0) {
      super.setSimbolo('\u265D'); //alfiere nero
    } else {
      super.setSimbolo('\u2657'); //alfiere bianco
    }
  }

}
