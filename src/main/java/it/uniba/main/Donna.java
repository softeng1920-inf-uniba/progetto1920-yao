package it.uniba.main;

/**
 * classe di tipo entity
 * responsabilita: rappresenta il pezzo di tipo donna
 */
public class Donna extends Pezzo {
  public Donna(final int colore, final Posizione pos) {
    super(8, colore, 'Q', pos);
    if (colore == 0) {
      super.setSimbolo('\u265B'); // donna nera
    } else {
      super.setSimbolo('\u2655'); // donna bianca
    }
  }
}
