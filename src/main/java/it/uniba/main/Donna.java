package it.uniba.main;

public class Donna extends Pezzo {
    Donna(int colore, Posizione pos) {
        super(8, colore, 'Q', pos);
        //super.setEmpoissonTrue();
        if (colore == 0) {
            super.setSimbolo('\u265B');//donna nera
        } else {
            super.setSimbolo('\u2655');//donna bianca
        }
    }
}

