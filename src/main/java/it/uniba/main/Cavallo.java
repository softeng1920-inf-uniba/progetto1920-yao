package it.uniba.main;

public class Cavallo extends Pezzo{
    Cavallo(int colore , Posizione pos){
        super(2 , colore , 'N' , pos );
        if (colore == 0) {
            super.setSimbolo('\u265E');
        }
        else{
            super.setSimbolo('\u2658');
        }
    }
}
