package it.uniba.main;

public class Pedone extends Pezzo{
    Pedone(int colore ,  Posizione pos){
        super(2 , colore , 'p' , pos );
        super.setEmpoissonTrue();
        if (colore == 0) {
            super.setSimbolo('\u265F');
        }
        else{
            super.setSimbolo('\u2659');
        }
    }
}
