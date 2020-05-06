package it.uniba.main;

/* classe di tipo entity
 * responsabilita: rappresenta il pezzo di tipo torre
 */

public class Torre extends Pezzo{
    Torre (int colore ,  Posizione pos){
        super(8 , colore , 'R' , pos );
        if (colore == 0) {
            super.setSimbolo('\u265C');
        }
        else{
            super.setSimbolo('\u2656');
        }
        setEmpoissonTrue();
    }
}

