package scacchi;

public class Posizione {
	   private int riga;
	   private int colonna;

	   Posizione(int riga, int colonna) {
	       this.riga = riga;
	       this.colonna = colonna;
	   }

	   public void setColonna(int colonna) {
	       this.colonna = colonna;
	   }

	   public void setRiga(int riga) {
	       this.riga = riga;
	   }

	   public int getColonna() {
	       return colonna;
	   }

	   public int getRiga() {
	       return riga;
	   }

}