package it.uniba.main;

/**
 * classe di tipo entity
 * responsabilita: rappresenta il pezzo di tipo re
 */
public class Re extends Pezzo {
  Re(final int colore, final Posizione pos) {
    super(1, colore, 'K', pos);
    if (colore == 0) {
      super.setSimbolo('\u265A'); //re nero
    } else {
      super.setSimbolo('\u2654'); //re bianco
    }
    setEmpoissonTrue();
  }

}
