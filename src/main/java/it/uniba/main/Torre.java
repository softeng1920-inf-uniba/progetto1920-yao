package it.uniba.main;

/* classe di tipo entity
 * responsabilita: rappresenta il pezzo di tipo torre
 */

public class Torre extends Pezzo {
  public Torre(int colore, Posizione pos) {
    super(8, colore, 'R', pos);
    if (colore == 0) {
      super.setSimbolo('\u265C'); //torre nera
    } else {
      super.setSimbolo('\u2656'); //torre bianca
    }
    setEmpoissonTrue();
  }
}
