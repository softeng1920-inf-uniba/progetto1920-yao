package it.uniba.main;

public class Re extends Pezzo {
    Re (int colore ,  Posizione pos){
        super(8 , colore , 'K' , pos );
        super.setEmpoissonTrue();
        if (colore == 0) {
            super.setSimbolo('\u2654');
        }
        else{
            super.setSimbolo('\u265A');
        }
    }
}