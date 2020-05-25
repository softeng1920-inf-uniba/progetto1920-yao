package it.uniba.main;

/* classe di tipo entity
 * responsabilita: rappresenta il pezzo di tipo pedone
 */

public class Pedone extends Pezzo {
  public Pedone(int colore, Posizione pos) {
    super(2, colore, 'p', pos);
    super.setEmpoissonTrue();
    if (colore == 0) {
      super.setSimbolo('\u265F'); //pedone nero
    } else {
      super.setSimbolo('\u2659'); //pedone bianco
    }
  }

  @Override
  public void giaMosso() {
    super.giaMosso();
    setGittata(1);
  }
}
