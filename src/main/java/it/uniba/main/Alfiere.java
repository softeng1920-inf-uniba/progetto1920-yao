package it.uniba.main;

public class Alfiere extends Pezzo{
    Alfiere(int colore ,  Posizione pos){
        super(8 , colore , 'B' , pos );
        if (colore == 0) {
            super.setSimbolo('\u265D');
        }
        else{
            super.setSimbolo('\u2657');
        }
    }

}


