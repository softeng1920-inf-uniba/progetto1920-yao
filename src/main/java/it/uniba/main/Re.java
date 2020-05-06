package it.uniba.main;

public class Re extends Pezzo {
    Re (int colore ,  Posizione pos){
        super(1 , colore , 'K' , pos );
        if (colore == 0) {
            super.setSimbolo('\u265A');
        }
        else{
            super.setSimbolo('\u2654');
        }
        setEmpoissonTrue();
    }

}
