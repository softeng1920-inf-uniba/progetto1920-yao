package it.uniba.main;

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

