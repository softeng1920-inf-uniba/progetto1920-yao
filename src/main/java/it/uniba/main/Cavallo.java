package it.uniba.main;

/* classe di tipo entity
 * responsabilita: rappresenta il pezzo di tipo cavallo
 */

public class Cavallo extends Pezzo {
  public Cavallo(int colore, Posizione pos) {
    super(2, colore, 'N', pos);
    if (colore == 0) {
      super.setSimbolo('\u265E'); //cavallo nero
    } else {
      super.setSimbolo('\u2658'); //cavallo bianco
    }
  }
}