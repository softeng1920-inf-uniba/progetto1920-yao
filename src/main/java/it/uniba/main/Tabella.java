package it.uniba.main;


import java.awt.*;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.Vector;
public class Tabella {
    private int righe;
    private int colonne;
    private Pezzo[][] tabella;
    private Pezzo[] pedoniBianchi;
    private Pezzo[] pedoniNeri;
    private Mangiati bianchiMangiati;
    private Mangiati neriMangiati;
    private char[] altriPezzibiachi = {'\u2656' , '\u2658' , '\u2657' , '\u2655' , '\u2654' , '\u2657' , '\u2658' , '\u2656' };
    private char[] altriPezzineri= {'\u265C' , '\u265E' , '\u265D' , '\u265B' , '\u265A' , '\u265D' , '\u265E' , '\u265C' };
    private int rigaPedoniBianchi = 1;
    private int rigaPedoniNeri = 6;
    private int bianco = 0;
    private int nero = 1;
    private int turno;
    private Vector<String> comandi;
    Tabella(int righe , int colonne){
        this.righe = righe;
        this.colonne = colonne;
        turno = bianco;
        tabella = new Pezzo[8][8];
        neriMangiati = new Mangiati(16);
        bianchiMangiati = new Mangiati(16);
        pedoniBianchi = new Pezzo[8];
        pedoniNeri = new Pezzo[8];
        comandi = new Vector<String>();
        for (int i = 0; i < 8; i++){
            Posizione pos = new Posizione(rigaPedoniBianchi , i);
            tabella[rigaPedoniBianchi][i] = new Pedone(0 , pos );
            pedoniBianchi[i] = tabella[rigaPedoniBianchi][i];
        }
        for (int j = 0; j < 8; j++){
            Posizione pos = new Posizione(rigaPedoniNeri , j);
            tabella[rigaPedoniNeri][j] = new Pedone(1 , pos );
            pedoniNeri[j] = tabella[rigaPedoniNeri][j];
        }
    }

    public int getRighe(){
        return righe;
    }
    public int getColonne(){
        return colonne;
    }

    public void setTabella(Pezzo pezzo , Posizione toGo) {
        tabella[toGo.getRiga()][toGo.getColonna()] = pezzo;
    }
    public Pezzo getTabella(Posizione toGet){
        return tabella[toGet.getRiga()][toGet.getColonna()];
    }

    public void move(Posizione toGo , Pezzo pezzo){
        if ((this.getTabella(toGo) != null )){
            mangiaPedina(toGo , pezzo);
            System.out.println("pedone mangiato");
        }
        else if ((tabella[pezzo.getPosizione().getRiga()][toGo.getColonna()] != null) && (tabella[pezzo.getPosizione().getRiga()][toGo.getColonna()].getCatturabileE()) && (toGo.getColonna() != pezzo.getPosizione().getColonna())){
            mangiaPedinaEnpassant(toGo , pezzo);
        }
        else{
            Posizione pos1 = pezzo.getPosizione();
            setTabella(pezzo , toGo);
            setTabella(null , pos1);
            pezzo.setPosizione(toGo);
            System.out.println("pedone spostato");
        }
    }

    public void stampaTurno(){
        if (turno == bianco ){
            System.out.println("muove il bianco:");
        }
        else{
            System.out.println("muove il nero:");
        }
    }

    public void muovicondomanda() throws UnsupportedEncodingException {
        stampaTurno();
        String moss1;
        Scanner input = new Scanner(System.in);
        moss1 = input.nextLine();
        if (moss1.length() == 0){
            System.out.println("hai scritto male il comando");
        }
        Comando comando = new Comando(moss1);
        if (comando.getSistema()){
            comando.getIstruzioni();
        }
        else if (comando.scrittaBene()){
            isEnpassant(comando);
            move(comando.posizioneTradotta , tabella[comando.inizialeTradotta.getRiga()][comando.inizialeTradotta.getColonna()]);
            comandi.add(comando.getComando());
            cambiaTurno();
        }
        else if (!comando.scrittaBene()){
            System.out.println("hai scritto male il comando");
        }
    }

    public void stampaComandi(){
        for (int i = 0; i < comandi.size(); i++){
            System.out.print(comandi.get(i) + ";  ");
            if (i % 2 == 1){
                System.out.println();
            }
        }
    }


    private void mangiaPedina(Posizione toGo, Pezzo pezzo){
        if (tabella[toGo.getRiga()][toGo.getColonna()].getColore() == bianco){
            bianchiMangiati.Set(tabella[toGo.getRiga()][toGo.getColonna()]);
        }
        else{
            neriMangiati.Set(tabella[toGo.getRiga()][toGo.getColonna()]);
        }
        Posizione pos1 = pezzo.getPosizione();
        setTabella(null , pos1);
        pezzo.setPosizione(toGo);
        setTabella(pezzo , toGo);

    }

    private void mangiaPedinaEnpassant(Posizione toGo , Pezzo pezzo){
        if (tabella[pezzo.getPosizione().getRiga()][toGo.getColonna()].getColore() == bianco){
            bianchiMangiati.Set(tabella[pezzo.getPosizione().getRiga()][toGo.getColonna()]);
        }
        else{
            neriMangiati.Set(tabella[pezzo.getPosizione().getRiga()][toGo.getColonna()]);
        }
        Posizione pos1 = pezzo.getPosizione(); //posizione del pezzo iniziale
        Posizione pos2 = new Posizione(pezzo.getPosizione().getRiga() , toGo.getColonna()); //pezzo da mangiare
        setTabella(null , pos1); //mette null la posizione precedente di pezzo
        setTabella(null , pos2); //mette null la posizione dove stava la pedina mangiata
        pezzo.setPosizione(toGo); //mette la poiszione di pezzo uguali al posto in cui Ã¨ ora
        setTabella(pezzo , toGo); // mette pezzo nel posto della tabella
        System.out.println("pedone mangiato con en passant");
    }

    private boolean controlMovimento(Pezzo pezzo , Comando comando){
        Posizione toGo = comando.posizioneTradotta;
        if (pezzo.getNome() == 'p') {
			if (pezzo.getColore() == bianco) {
				isEnpassant(comando);
				if (pezzo.getPosizione().getRiga() != rigaPedoniBianchi) {
					pezzo.giaMosso();
				} // controllo gittata x , y sulla posizione di arrivo and ((controllo per vedera
					// se � nella stessa colonna and la posizuione � nulla ) e si vuole caturare e
					// la gittata � quella giusta oppure (la differenza in valiore asoluto tera le
					// colonne � 1 and ci deve essere una pedina and il colore de essere uuale al
					// nero e la differenza di righe � 1 e si sta catturando cattura con en passant
				if ((toGo.getRiga() - pezzo.getPosizione().getRiga() <= pezzo.getGittata())
						&& (((toGo.getColonna() - pezzo.getPosizione().getColonna() == 0)
						&& (tabella[toGo.getRiga()][toGo.getColonna()] == null) && (!comando.getCattura())
						&& controlloGittata(pezzo, toGo))
						|| ((Math.abs(toGo.getColonna() - pezzo.getPosizione().getColonna()) == 1)
						&& (tabella[toGo.getRiga()][toGo.getColonna()] != null)
						&& (tabella[toGo.getRiga()][toGo.getColonna()].getColore() == nero)
						&& (toGo.getRiga() - pezzo.getPosizione().getRiga() == 1)
						&& comando.getCattura())
						|| ((Math.abs(toGo.getColonna() - pezzo.getPosizione().getColonna()) == 1)
						&& (tabella[toGo.getRiga()][toGo.getColonna()] == null)
						&& (tabella[toGo.getRiga() - 1][toGo.getColonna()] != null
						&& tabella[toGo.getRiga() - 1][toGo.getColonna()].getNome() == 'p')
						&& (tabella[toGo.getRiga() - 1][toGo.getColonna()].getColore() == nero)
						&& (tabella[toGo.getRiga() - 1][toGo.getColonna()].getCatturabileE())))
						&& (toGo.getRiga() - pezzo.getPosizione().getRiga() != 0)
						&& (toGo.getRiga() - pezzo.getPosizione().getRiga() > 0)) {
					isEnpassant(comando);
					return true;
				}

			} else {
				isEnpassant(comando);
				if (pezzo.getPosizione().getRiga() != rigaPedoniNeri) {
					pezzo.giaMosso();
				}
				if ((toGo.getRiga() - pezzo.getPosizione().getRiga() >= -pezzo.getGittata())
						&& (((toGo.getColonna() - pezzo.getPosizione().getColonna() == 0)
						&& (tabella[toGo.getRiga()][toGo.getColonna()] == null) && (!comando.getCattura())
						&& controlloGittata(pezzo, toGo))
						|| ((Math.abs(toGo.getColonna() - pezzo.getPosizione().getColonna()) == 1)
						&& (tabella[toGo.getRiga()][toGo.getColonna()] != null)
						&& (tabella[toGo.getRiga()][toGo.getColonna()].getColore() == bianco)
						&& (Math.abs(toGo.getRiga() - pezzo.getPosizione().getRiga()) == 1))
						|| ((Math.abs(toGo.getColonna() - pezzo.getPosizione().getColonna()) == 1)
						&& (tabella[toGo.getRiga()][toGo.getColonna()] == null)
						&& (tabella[toGo.getRiga() + 1][toGo.getColonna()] != null
						&& tabella[toGo.getRiga() + 1][toGo.getColonna()].getNome() == 'p')
					    && (tabella[toGo.getRiga() + 1][toGo.getColonna()].getColore() == bianco)
						&& (tabella[toGo.getRiga() + 1][toGo.getColonna()].getCatturabileE())))
						&& (toGo.getRiga() - pezzo.getPosizione().getRiga() != 0)
						&& (toGo.getRiga() - pezzo.getPosizione().getRiga() < 0)) 
				{
					isEnpassant(comando);
					return true;
				}

			}
		}
else if (pezzo.getNome() == 'N') {
			return ((pezzo.getGittata() >= Math.abs(pezzo.getPosizione().getRiga() - toGo.getRiga()))
					&& (pezzo.getGittata() >= Math.abs(pezzo.getPosizione().getColonna() - toGo.getColonna()))
					&& (Math.abs(toGo.getColonna() - pezzo.getPosizione().getColonna()) != Math
							.abs(toGo.getRiga() - pezzo.getPosizione().getRiga()))
					&& ((tabella[toGo.getRiga()][toGo.getColonna()] == null)
							|| (tabella[toGo.getRiga()][toGo.getColonna()].getColore() != pezzo.getColore()))
					&& (toGo.getColonna() != pezzo.getPosizione().getColonna())
					&& (toGo.getRiga() != pezzo.getPosizione().getRiga()));
		}
		else if (pezzo.getNome() == 'B') {
			return ((Math.abs(toGo.getRiga() - pezzo.getPosizione().getRiga()) <= pezzo.getGittata())
					&& (Math.abs(toGo.getColonna() - pezzo.getPosizione().getColonna()) <= pezzo.getGittata())
					&& (((toGo.getColonna() - pezzo.getPosizione().getColonna() != 0)
							&& (toGo.getRiga() - pezzo.getPosizione().getRiga() != 0)
							&& (tabella[toGo.getRiga()][toGo.getColonna()] == null) && (!comando.getCattura()))
							|| ((tabella[toGo.getRiga()][toGo.getColonna()] != null)
									&& (tabella[toGo.getRiga()][toGo.getColonna()].getColore() != pezzo.getColore())
									&& comando.getCattura())
									&& (Math.abs(toGo.getColonna() - pezzo.getPosizione().getColonna()) == Math
											.abs(toGo.getRiga() - pezzo.getPosizione().getRiga())))
					&& (Math.abs(toGo.getColonna() - pezzo.getPosizione().getColonna()) == Math
							.abs(toGo.getRiga() - pezzo.getPosizione().getRiga()))
					&& controlloGittata(pezzo, toGo));
		} 
        else if (pezzo.getNome() == 'Q') { // vede se non sta catturando oppure se sta catturando e se il colore �
                                                // diverso e se la cattura � true contorlli di movimento su stessa riga
                                                // o su stessa colonna ma diversa riga o sulle diagonali quindi si vede
                                                // se la distanza tra colonna e righe sono uguali in termini di
                                                // differenza controllo sulla gittata per vedere se cis ono pezzi in
                                                // mezzo
            return ((Math.abs(toGo.getRiga() - pezzo.getPosizione().getRiga()) <= pezzo.getGittata())
                    && (Math.abs(toGo.getColonna() - pezzo.getPosizione().getColonna()) <= pezzo.getGittata()
                            && (((tabella[toGo.getRiga()][toGo.getColonna()] == null) && (!comando.getCattura()))
                                    || ((tabella[toGo.getRiga()][toGo.getColonna()] != null)
                                            && (tabella[toGo.getRiga()][toGo.getColonna()].getColore() != pezzo
                                                    .getColore())
                                            && (comando.getCattura())))
                            && (((toGo.getRiga() - pezzo.getPosizione().getRiga() == 0
                                    && toGo.getColonna() - pezzo.getPosizione().getColonna() != 0)
                                    || (toGo.getColonna() - pezzo.getPosizione().getColonna() == 0
                                            && toGo.getRiga() - pezzo.getPosizione().getRiga() != 0))
                                    || (Math.abs(toGo.getColonna() - pezzo.getPosizione().getColonna()) == Math
                                            .abs(toGo.getRiga() - pezzo.getPosizione().getRiga()))))
                    && controlloGittata(pezzo, toGo) && (toGo.getRiga() != pezzo.getPosizione().getRiga()
                            || toGo.getColonna() != pezzo.getPosizione().getColonna()));
        }    else if (pezzo.getNome() == 'R') {
			return ((pezzo.getGittata() >= Math.abs(pezzo.getPosizione().getRiga() - toGo.getRiga()))
					&& (pezzo.getGittata() >= Math.abs(toGo.getColonna() - pezzo.getPosizione().getColonna()))
					&& (((toGo.getRiga() - pezzo.getPosizione().getRiga() == 0
					&& toGo.getColonna() - pezzo.getPosizione().getColonna() != 0)
					|| (toGo.getColonna() - pezzo.getPosizione().getColonna() == 0
					&& toGo.getRiga() - pezzo.getPosizione().getRiga() != 0))
					&& (((tabella[toGo.getRiga()][toGo.getColonna()] == null) && (!comando.getCattura()))
					|| ((tabella[toGo.getRiga()][toGo.getColonna()] != null)
					&& (tabella[toGo.getRiga()][toGo.getColonna()].getColore() != pezzo.getColore())
					&& comando.getCattura())))
					&& controlloGittata(pezzo, toGo));
		}else if (pezzo.getNome() == 'K') {
			return ((((Math.abs(toGo.getRiga() - pezzo.getPosizione().getRiga()) == pezzo.getGittata())
					&& (Math.abs(toGo.getColonna() - pezzo.getPosizione().getColonna()) == pezzo.getGittata())
					&& (tabella[toGo.getRiga()][toGo.getColonna()] == null) && controlloGittata(pezzo, toGo)
					&& (!comando.getCattura()))
					|| ((tabella[toGo.getRiga()][toGo.getColonna()] != null)
							&& (tabella[toGo.getRiga()][toGo.getColonna()].getColore() != pezzo.getColore())
							&& controlloGittata(pezzo, toGo) && comando.getCattura()))
					|| (((toGo.getRiga() - pezzo.getPosizione().getRiga() == 0
							&& (Math.abs(toGo.getColonna() - pezzo.getPosizione().getColonna()) == pezzo.getGittata()))
							|| (toGo.getColonna() - pezzo.getPosizione().getColonna() == 0 && (Math
									.abs(toGo.getRiga() - pezzo.getPosizione().getRiga()) == pezzo.getGittata())))
							&& ((tabella[toGo.getRiga()][toGo.getColonna()] == null) && controlloGittata(pezzo, toGo)
									&& (!comando.getCattura()))
							|| ((tabella[toGo.getRiga()][toGo.getColonna()] != null)
									&& (tabella[toGo.getRiga()][toGo.getColonna()].getColore() != pezzo.getColore())
									&& controlloGittata(pezzo, toGo) && comando.getCattura())));
		} 
                return false;
        }
    //controlla se per ogni tipo di pezzo Ã¨ possibile lo spostamento

    private boolean controlloGittata(Pezzo pezzo , Posizione toGo){
    	if (pezzo.getNome() == 'p') {
			if (pezzo.getColore() == bianco) {
				for (int i = 1; i <= toGo.getRiga() - pezzo.getGittata(); i++) {
					if (pezzo.getPosizione().getRiga() + i > 7) {
						return false;
					} else if (tabella[pezzo.getPosizione().getRiga() + i][pezzo.getPosizione().getColonna()] != null) {
						return false;
					}
				}
			} else {
				for (int i = 1; i <= pezzo.getGittata() - toGo.getRiga(); i++) {
					if (pezzo.getPosizione().getRiga() - i < 0) {
						return false;
					} else if (tabella[pezzo.getPosizione().getRiga() - i][pezzo.getPosizione().getColonna()] != null) {
						return false;
					}
				}
			}
			 } else if (pezzo.getNome() == 'N') {
			return true;
			 } else if (pezzo.getNome() == 'R') {
			if (toGo.getRiga() > pezzo.getPosizione().getRiga()
					&& (toGo.getColonna() == pezzo.getPosizione().getColonna())) {
				for (int i = 1; i < Math.abs(pezzo.getPosizione().getRiga() - toGo.getRiga()); i++) { 
					if (tabella[pezzo.getPosizione().getRiga() + i][pezzo.getPosizione().getColonna()] != null)
						return false;
				}
			}
			/* giu */
			else if (toGo.getRiga() < pezzo.getPosizione().getRiga()
					&& (toGo.getColonna() == pezzo.getPosizione().getColonna())) {
				for (int i = 1; i < Math.abs(pezzo.getPosizione().getRiga() - toGo.getRiga()); i++) {
					if (tabella[pezzo.getPosizione().getRiga() - i][pezzo.getPosizione().getColonna()] != null)
						return false;
				}
			}
			/* destra */
			else if (toGo.getRiga() == pezzo.getPosizione().getRiga()
					&& (toGo.getColonna() > pezzo.getPosizione().getColonna())) {
				for (int i = 1; i < Math.abs(pezzo.getPosizione().getColonna() - toGo.getColonna()); i++) { 
					if (tabella[pezzo.getPosizione().getRiga()][pezzo.getPosizione().getColonna() + i] != null)
						return false;
				}
			}
			/* sinistra */
			else if (toGo.getRiga() == pezzo.getPosizione().getRiga()
					&& (toGo.getColonna() < pezzo.getPosizione().getColonna())) {
				for (int i = 1; i < Math.abs(pezzo.getPosizione().getColonna() - toGo.getColonna()); i++) {																										
					if (tabella[pezzo.getPosizione().getRiga()][pezzo.getPosizione().getColonna() - i] != null)
						return false;
				}
			}
		}else if (pezzo.getNome() == 'B') {
			// sopra sinistra
			if (toGo.getRiga() > pezzo.getPosizione().getRiga()
					&& toGo.getColonna() < pezzo.getPosizione().getColonna()) {
				for (int i = 1; i < Math.abs(pezzo.getPosizione().getColonna() - toGo.getColonna()); i++) {
					if (tabella[pezzo.getPosizione().getRiga() + 1][pezzo.getPosizione().getColonna() - 1] != null) {
						return false;
					}
				}
			} // sopra destra
			else if (toGo.getRiga() > pezzo.getPosizione().getRiga()
					&& toGo.getColonna() > pezzo.getPosizione().getColonna()) {
				for (int i = 1; i < Math.abs(pezzo.getPosizione().getColonna() - toGo.getColonna()); i++) {
					if (tabella[pezzo.getPosizione().getRiga() + i][pezzo.getPosizione().getColonna() + i] != null) {
						return false;
					}
				}
			} // sotto sinistra
			else if (toGo.getRiga() < pezzo.getPosizione().getRiga()
					&& toGo.getColonna() < pezzo.getPosizione().getColonna()) {
				for (int i = 1; i < Math.abs(pezzo.getPosizione().getColonna() - toGo.getColonna()); i++) {
					if (tabella[pezzo.getPosizione().getRiga() - 1][pezzo.getPosizione().getColonna() - 1] != null) {
						return false;
					}
				}
			} else if (toGo.getRiga() < pezzo.getPosizione().getRiga()
					&& toGo.getColonna() > pezzo.getPosizione().getColonna()) {
				for (int i = 1; i < Math.abs(pezzo.getPosizione().getColonna() - toGo.getColonna()); i++) {
					if (tabella[pezzo.getPosizione().getRiga() - 1][pezzo.getPosizione().getColonna() + 1] != null) {
						return false;
					}
				}
			}
		}else if (pezzo.getNome() == 'Q') {
            /* sopra */
            if (toGo.getRiga() > pezzo.getPosizione().getRiga()
                    && (toGo.getColonna() == pezzo.getPosizione().getColonna())) {
                for (int i = 1; i < Math.abs(pezzo.getPosizione().getRiga() - toGo.getRiga()); i++) { // senza + i
                                                                                                        // perch� la
                                                                                                        // colonna
                                                                                                        // rimane la
                                                                                                        // stessa
                    if (tabella[pezzo.getPosizione().getRiga() + i][pezzo.getPosizione().getColonna()] != null)
                        return false;
                }
            }
            /* giu */
            else if (toGo.getRiga() < pezzo.getPosizione().getRiga()
                    && (toGo.getColonna() == pezzo.getPosizione().getColonna())) {
                for (int i = 1; i < Math.abs(pezzo.getPosizione().getRiga() - toGo.getRiga()); i++) { // - i perch� vado
                                                                                                        // indietro
                    if (tabella[pezzo.getPosizione().getRiga() - i][pezzo.getPosizione().getColonna()] != null)
                        return false;
                }
            }else if (toGo.getRiga() == pezzo.getPosizione().getRiga()
                    && (toGo.getColonna() > pezzo.getPosizione().getColonna())) {
                for (int i = 1; i < Math.abs(pezzo.getPosizione().getColonna() - toGo.getColonna()); i++) { // la riga
                                                                                                            // rimane la
                                                                                                            // stessa
                    if (tabella[pezzo.getPosizione().getRiga()][pezzo.getPosizione().getColonna() + i] != null)
                        return false;
                }
            }
            /* sinistra */
            else if (toGo.getRiga() == pezzo.getPosizione().getRiga()
                    && (toGo.getColonna() < pezzo.getPosizione().getColonna())) {
                for (int i = 1; i < Math.abs(pezzo.getPosizione().getColonna() - toGo.getColonna()); i++) { // - i
                                                                                                            // perch�
                                                                                                            // vado a
                                                                                                            // sinistra
                    if (tabella[pezzo.getPosizione().getRiga()][pezzo.getPosizione().getColonna() - i] != null)
                        return false;
                }
            }
            // sopra sinistra
            else if (toGo.getRiga() > pezzo.getPosizione().getRiga()
                    && toGo.getColonna() < pezzo.getPosizione().getColonna()) {
                for (int i = 1; i < pezzo.getPosizione().getColonna() - toGo.getColonna(); i++) {
                    if (tabella[pezzo.getPosizione().getRiga() + 1][pezzo.getPosizione().getColonna() - 1] != null) {
                        return false;
                    }
                }
            } // sopra destra
            else if (toGo.getRiga() > pezzo.getPosizione().getRiga()
                    && toGo.getColonna() > pezzo.getPosizione().getColonna()) {
                for (int i = 1; i < pezzo.getPosizione().getColonna() - toGo.getColonna(); i++) {
                    if (tabella[pezzo.getPosizione().getRiga() + i][pezzo.getPosizione().getColonna() + i] != null) {
                        return false;
                    }
                }
            } // sotto sinistra
            else if (toGo.getRiga() < pezzo.getPosizione().getRiga()
                    && toGo.getColonna() < pezzo.getPosizione().getColonna()) {
                for (int i = 1; i < pezzo.getPosizione().getColonna() - toGo.getColonna(); i++) {
                    if (tabella[pezzo.getPosizione().getRiga() - 1][pezzo.getPosizione().getColonna() - 1] != null) {
                        return false;
                    }
                }
            } else if (toGo.getRiga() < pezzo.getPosizione().getRiga()
                    && toGo.getColonna() > pezzo.getPosizione().getColonna()) {
                for (int i = 1; i < pezzo.getPosizione().getColonna() - toGo.getColonna(); i++) {
                    if (tabella[pezzo.getPosizione().getRiga() - 1][pezzo.getPosizione().getColonna() + 1] != null) {
                        return false;
                    }
                }
            }
        } else if (pezzo.getNome() == 'K') {
			// controlloscacco
			// bordi
			int bsx = toGo.getColonna() - 2;
			int balt = 7 - toGo.getRiga();
			int bdx = 7 - toGo.getColonna();
			int bbas = toGo.getRiga() - 2;

			/* pedone */ if (pezzo.getColore() == bianco) {
				if (balt > 0) {
					if (bdx > 0)
						if (tabella[toGo.getRiga() + 1][toGo.getColonna() + 1] != null
								&& tabella[toGo.getRiga() + 1][toGo.getColonna() + 1].getNome() == 'p'
								&& tabella[toGo.getRiga() + 1][toGo.getColonna() + 1].getColore() == nero)
							return false;
					if (bsx > 0)
						if (tabella[toGo.getRiga() + 1][toGo.getColonna() - 1] != null
								&& tabella[toGo.getRiga() + 1][toGo.getColonna() - 1].getNome() == 'p'
								&& tabella[toGo.getRiga() + 1][toGo.getColonna() - 1].getColore() == nero)
							return false;
				}
			} else {
				/* nero */ if (bbas > 0) {
					if (bdx > 0)
						if (tabella[toGo.getRiga() - 1][toGo.getColonna() + 1] != null
								&& tabella[toGo.getRiga() - 1][toGo.getColonna() + 1].getNome() == 'p'
								&& tabella[toGo.getRiga() - 1][toGo.getColonna() + 1].getColore() == bianco)
							return false;
					if (bsx > 0)
						if (tabella[toGo.getRiga() - 1][toGo.getColonna() - 1] != null
								&& tabella[toGo.getRiga() - 1][toGo.getColonna() - 1].getNome() == 'p'
								&& tabella[toGo.getRiga() - 1][toGo.getColonna() - 1].getColore() == bianco)
							return false;
				}
			}

			/* alfiere, donna */
			int i = 1;

//altsx
			while (i < bsx && i < balt) {
				if (tabella[toGo.getRiga() + i][toGo.getColonna() - i] != null
						&& tabella[toGo.getRiga() + i][toGo.getColonna() - i].getColore() != pezzo.getColore()
						&& (tabella[toGo.getRiga() + i][toGo.getColonna() - i].getNome() == 'B'
								|| tabella[toGo.getRiga() + i][toGo.getColonna() - i].getNome() == 'Q'))
					return false;
				i++;
			}
//altdx
			i = 1;
			while (i < bdx && i < balt) {
				if (tabella[toGo.getRiga() + i][toGo.getColonna() + i] != null
						&& tabella[toGo.getRiga() + i][toGo.getColonna() + i].getColore() != pezzo.getColore()
						&& (tabella[toGo.getRiga() + i][toGo.getColonna() + i].getNome() == 'B'
								|| tabella[toGo.getRiga() + i][toGo.getColonna() + i].getNome() == 'Q'))
					return false;
				i++;
			}
//bassx
			i = 1;
			while (i < bsx && i < bbas) {
				if (tabella[toGo.getRiga() - i][toGo.getColonna() - i] != null
						&& tabella[toGo.getRiga() - i][toGo.getColonna() - i].getColore() != pezzo.getColore()
						&& (tabella[toGo.getRiga() - i][toGo.getColonna() - i].getNome() == 'B'
								|| tabella[toGo.getRiga() - i][toGo.getColonna() - i].getNome() == 'Q'))
					return false;
				i++;
			}
//bassdx
			i = 1;
			while (i < bdx && i < bbas) {
				if (tabella[toGo.getRiga() - i][toGo.getColonna() + i] != null
						&& tabella[toGo.getRiga() - i][toGo.getColonna() + i].getColore() != pezzo.getColore()
						&& (tabella[toGo.getRiga() - i][toGo.getColonna() + i].getNome() == 'B'
								|| tabella[toGo.getRiga() - i][toGo.getColonna() + i].getNome() == 'Q'))
					return false;
				i++;
			}

			/* torre, donna */
			i = 1;
			while (i < balt) {
				if (tabella[toGo.getRiga() + i][toGo.getColonna()] != null
						&& tabella[toGo.getRiga() + i][toGo.getColonna()].getColore() != pezzo.getColore()
						&& (tabella[toGo.getRiga() + i][toGo.getColonna()].getNome() == 'R'
								|| tabella[toGo.getRiga() + i][toGo.getColonna()].getNome() == 'Q'))
					return false;
				i++;
			}
//giu
			i = 1;
			while (i < bbas) {
				if (tabella[toGo.getRiga() - i][toGo.getColonna()] != null
						&& tabella[toGo.getRiga() - i][toGo.getColonna()].getColore() != pezzo.getColore()
						&& (tabella[toGo.getRiga() - i][toGo.getColonna()].getNome() == 'R'
								|| tabella[toGo.getRiga() - i][toGo.getColonna()].getNome() == 'Q'))
					return false;
				i++;
			}
//sx
			i = 1;
			while (i < bsx) {
				if (tabella[toGo.getRiga()][toGo.getColonna() - i] != null
						&& tabella[toGo.getRiga()][toGo.getColonna() - i].getColore() != pezzo.getColore()
						&& (tabella[toGo.getRiga()][toGo.getColonna() - i].getNome() == 'R'
								|| tabella[toGo.getRiga()][toGo.getColonna() - i].getNome() == 'Q'))
					return false;
				i++;
			}
//dx
			i = 1;
			while (i < bdx) {
				if (tabella[toGo.getRiga()][toGo.getColonna() + i] != null
						&& tabella[toGo.getRiga()][toGo.getColonna() + i].getColore() != pezzo.getColore()
						&& (tabella[toGo.getRiga()][toGo.getColonna() + i].getNome() == 'R'
								|| tabella[toGo.getRiga()][toGo.getColonna() + i].getNome() == 'Q'))
					return false;
				i++;
			}

			/* cavallo */ // sx
			if (bsx >= 2) {
				// alto
				if (balt > 0)
					if (tabella[toGo.getRiga() + 1][toGo.getColonna() - 2] != null
							&& tabella[toGo.getRiga() + 1][toGo.getColonna() - 2].getNome() == 'N'
							&& tabella[toGo.getRiga() + 1][toGo.getColonna() - 2].getColore() != pezzo.getColore())
						return false;
				if (balt >= 2)
					if (tabella[toGo.getRiga() + 2][toGo.getColonna() - 1] != null
							&& tabella[toGo.getRiga() + 2][toGo.getColonna() - 1].getNome() == 'N'
							&& tabella[toGo.getRiga() + 2][toGo.getColonna() - 1].getColore() != pezzo.getColore())
						return false;
				// basso
				if (bbas > 0)
					if (tabella[toGo.getRiga() - 1][toGo.getColonna() - 2] != null
							&& tabella[toGo.getRiga() - 1][toGo.getColonna() - 2].getNome() == 'N'
							&& tabella[toGo.getRiga() - 1][toGo.getColonna() - 2].getColore() != pezzo.getColore())
						return false;
				if (bbas >= 2)
					if (tabella[toGo.getRiga() - 2][toGo.getColonna() - 1] != null
							&& tabella[toGo.getRiga() - 2][toGo.getColonna() - 1].getNome() == 'N'
							&& tabella[toGo.getRiga() - 2][toGo.getColonna() - 1].getColore() != pezzo.getColore())
						return false;
			}
			if (bsx == 1) {
				// alto
				if (balt >= 2)
					if (tabella[toGo.getRiga() + 2][toGo.getColonna() - 1] != null
							&& tabella[toGo.getRiga() + 2][toGo.getColonna() - 1].getNome() == 'N'
							&& tabella[toGo.getRiga() + 2][toGo.getColonna() - 1].getColore() != pezzo.getColore())
						return false;
				// basso
				if (bbas >= 2)
					if (tabella[toGo.getRiga() - 2][toGo.getColonna() - 1] != null
							&& tabella[toGo.getRiga() - 2][toGo.getColonna() - 1].getNome() == 'N'
							&& tabella[toGo.getRiga() - 2][toGo.getColonna() - 1].getColore() != pezzo.getColore())
						return false;
			}
			// dx
			if (bdx >= 2) {
				// alto
				if (balt > 0)
					if (tabella[toGo.getRiga() + 1][toGo.getColonna() + 2] != null
							&& tabella[toGo.getRiga() + 1][toGo.getColonna() + 2].getNome() == 'N'
							&& tabella[toGo.getRiga() + 1][toGo.getColonna() + 2].getColore() != pezzo.getColore())
						return false;
				if (balt >= 2)
					if (tabella[toGo.getRiga() + 2][toGo.getColonna() + 1] != null
							&& tabella[toGo.getRiga() + 2][toGo.getColonna() + 1].getNome() == 'N'
							&& tabella[toGo.getRiga() + 2][toGo.getColonna() + 1].getColore() != pezzo.getColore())
						return false;
				// basso
				if (bbas > 0)
					if (tabella[toGo.getRiga() - 1][toGo.getColonna() + 2] != null
							&& tabella[toGo.getRiga() - 1][toGo.getColonna() + 2].getNome() == 'N'
							&& tabella[toGo.getRiga() - 1][toGo.getColonna() + 2].getColore() != pezzo.getColore())
						return false;
				if (bbas >= 2)
					if (tabella[toGo.getRiga() - 2][toGo.getColonna() + 1] != null
							&& tabella[toGo.getRiga() - 2][toGo.getColonna() + 1].getNome() == 'N'
							&& tabella[toGo.getRiga() - 2][toGo.getColonna() + 1].getColore() != pezzo.getColore())
						return false;
			}
			if (bdx == 1) {
				// alto
				if (balt >= 2)
					if (tabella[toGo.getRiga() + 2][toGo.getColonna() + 1] != null
							&& tabella[toGo.getRiga() + 2][toGo.getColonna() + 1].getNome() == 'N'
							&& tabella[toGo.getRiga() + 2][toGo.getColonna() + 1].getColore() != pezzo.getColore())
						return false;
				// basso
				if (bbas >= 2)
					if (tabella[toGo.getRiga() - 2][toGo.getColonna() + 1] != null
							&& tabella[toGo.getRiga() - 2][toGo.getColonna() + 1].getNome() == 'N'
							&& tabella[toGo.getRiga() - 2][toGo.getColonna() + 1].getColore() != pezzo.getColore())
						return false;
			}

			/* re */ /* alto */ if (toGo.getRiga() - pezzo.getPosizione().getRiga() == 1) {

				if (balt >= 1 && bdx >= 1 && bsx >= 1) {
					if ((tabella[toGo.getRiga() + 1][toGo.getColonna()] != null
							&& tabella[toGo.getRiga() + 1][toGo.getColonna()].getNome() == 'K'
							&& tabella[toGo.getRiga() + 1][toGo.getColonna()].getColore() != pezzo.getColore())
							|| (tabella[toGo.getRiga() + 1][toGo.getColonna() + 1] != null
									&& tabella[toGo.getRiga() + 1][toGo.getColonna() + 1].getNome() == 'K'
									&& tabella[toGo.getRiga() + 1][toGo.getColonna() + 1].getColore() != pezzo
											.getColore())
							|| (tabella[toGo.getRiga() + 1][toGo.getColonna() - 1] != null
									&& tabella[toGo.getRiga() + 1][toGo.getColonna() - 1].getNome() == 'K'
									&& tabella[toGo.getRiga() + 1][toGo.getColonna() - 1].getColore() != pezzo
											.getColore()))
						return false;
				}
				if (balt >= 1 && bsx >= 1 && bdx == 0) {
					if ((tabella[toGo.getRiga() + 1][toGo.getColonna()] != null
							&& tabella[toGo.getRiga() + 1][toGo.getColonna()].getNome() == 'K'
							&& tabella[toGo.getRiga() + 1][toGo.getColonna()].getColore() != pezzo.getColore())
							|| (tabella[toGo.getRiga() + 1][toGo.getColonna() - 1] != null
									&& tabella[toGo.getRiga() + 1][toGo.getColonna() - 1].getNome() == 'K'
									&& tabella[toGo.getRiga() + 1][toGo.getColonna() - 1].getColore() != pezzo
											.getColore()))
						return false;
				}
				if (balt >= 1 && bdx >= 1 && bsx == 0) {
					if ((tabella[toGo.getRiga() + 1][toGo.getColonna()] != null
							&& tabella[toGo.getRiga() + 1][toGo.getColonna()].getNome() == 'K'
							&& tabella[toGo.getRiga() + 1][toGo.getColonna()].getColore() != pezzo.getColore())
							|| (tabella[toGo.getRiga() + 1][toGo.getColonna() + 1] != null
									&& tabella[toGo.getRiga() + 1][toGo.getColonna() + 1].getNome() == 'K'
									&& tabella[toGo.getRiga() + 1][toGo.getColonna() + 1].getColore() != pezzo
											.getColore()))
						return false;
				}
			}
			/* basso */ if (toGo.getRiga() - pezzo.getPosizione().getRiga() == -1) {

				if (bbas >= 1 && bdx >= 1 && bsx >= 1) {
					if ((tabella[toGo.getRiga() - 1][toGo.getColonna()] != null
							&& tabella[toGo.getRiga() - 1][toGo.getColonna()].getNome() == 'K'
							&& tabella[toGo.getRiga() - 1][toGo.getColonna()].getColore() != pezzo.getColore())
							|| (tabella[toGo.getRiga() - 1][toGo.getColonna() - 1] != null
									&& tabella[toGo.getRiga() - 1][toGo.getColonna() - 1].getNome() == 'K'
									&& tabella[toGo.getRiga() - 1][toGo.getColonna() - 1].getColore() != pezzo
											.getColore())
							|| (tabella[toGo.getRiga() - 1][toGo.getColonna() + 1] != null
									&& tabella[toGo.getRiga() - 1][toGo.getColonna() + 1].getNome() == 'K'
									&& tabella[toGo.getRiga() - 1][toGo.getColonna() + 1].getColore() != pezzo
											.getColore()))
						return false;
				}
				if (bbas >= 1 && bsx >= 1 && bdx == 0) {
					if ((tabella[toGo.getRiga() - 1][toGo.getColonna()] != null
							&& tabella[toGo.getRiga() - 1][toGo.getColonna()].getNome() == 'K'
							&& tabella[toGo.getRiga() - 1][toGo.getColonna()].getColore() != pezzo.getColore())
							|| (tabella[toGo.getRiga() - 1][toGo.getColonna() - 1] != null
									&& tabella[toGo.getRiga() - 1][toGo.getColonna() - 1].getNome() == 'K'
									&& tabella[toGo.getRiga() - 1][toGo.getColonna() - 1].getColore() != pezzo
											.getColore()))
						return false;
				}
				if (bbas >= 1 && bdx >= 1 && bsx == 0) {
					if ((tabella[toGo.getRiga() - 1][toGo.getColonna()] != null
							&& tabella[toGo.getRiga() - 1][toGo.getColonna()].getNome() == 'K'
							&& tabella[toGo.getRiga() - 1][toGo.getColonna()].getColore() != pezzo.getColore())
							|| (tabella[toGo.getRiga() - 1][toGo.getColonna() + 1] != null
									&& tabella[toGo.getRiga() - 1][toGo.getColonna() + 1].getNome() == 'K'
									&& tabella[toGo.getRiga() - 1][toGo.getColonna() + 1].getColore() != pezzo
											.getColore()))
						return false;
				}
			}
			/* dx */ if (toGo.getColonna() - pezzo.getPosizione().getColonna() == 1) {
				if (bbas >= 1 && bdx >= 1 && balt >= 1) {
					if ((tabella[toGo.getRiga()][toGo.getColonna() + 1] != null
							&& tabella[toGo.getRiga()][toGo.getColonna() + 1].getNome() == 'K'
							&& tabella[toGo.getRiga()][toGo.getColonna() + 1].getColore() != pezzo.getColore())
							|| (tabella[toGo.getRiga() + 1][toGo.getColonna() + 1] != null
									&& tabella[toGo.getRiga() + 1][toGo.getColonna() + 1].getNome() == 'K'
									&& tabella[toGo.getRiga() + 1][toGo.getColonna() + 1].getColore() != pezzo
											.getColore())
							|| (tabella[toGo.getRiga() - 1][toGo.getColonna() + 1] != null
									&& tabella[toGo.getRiga() - 1][toGo.getColonna() + 1].getNome() == 'K'
									&& tabella[toGo.getRiga() - 1][toGo.getColonna() + 1].getColore() != pezzo
											.getColore()))
						return false;
				}
				if (bbas >= 1 && bdx >= 1 && balt == 0) {
					if ((tabella[toGo.getRiga()][toGo.getColonna() + 1] != null
							&& tabella[toGo.getRiga()][toGo.getColonna() + 1].getNome() == 'K'
							&& tabella[toGo.getRiga()][toGo.getColonna() + 1].getColore() != pezzo.getColore())
							|| (tabella[toGo.getRiga() - 1][toGo.getColonna() + 1] != null
									&& tabella[toGo.getRiga() - 1][toGo.getColonna() + 1].getNome() == 'K'
									&& tabella[toGo.getRiga() - 1][toGo.getColonna() + 1].getColore() != pezzo
											.getColore()))
						return false;
				}
				if (balt >= 1 && bdx >= 1 && bbas == 0) {
					if ((tabella[toGo.getRiga()][toGo.getColonna() + 1] != null
							&& tabella[toGo.getRiga()][toGo.getColonna() + 1].getNome() == 'K'
							&& tabella[toGo.getRiga()][toGo.getColonna() + 1].getColore() != pezzo.getColore())
							|| (tabella[toGo.getRiga() + 1][toGo.getColonna() + 1] != null
									&& tabella[toGo.getRiga() + 1][toGo.getColonna() + 1].getNome() == 'K'
									&& tabella[toGo.getRiga() + 1][toGo.getColonna() + 1].getColore() != pezzo
											.getColore()))
						return false;
				}
			}
			/* sx */ if (toGo.getColonna() - pezzo.getPosizione().getColonna() == -1) {
				if (bbas >= 1 && bsx >= 1 && balt >= 1) {
					if ((tabella[toGo.getRiga()][toGo.getColonna() - 1] != null
							&& tabella[toGo.getRiga()][toGo.getColonna() - 1].getNome() == 'K'
							&& tabella[toGo.getRiga()][toGo.getColonna() - 1].getColore() != pezzo.getColore())
							|| (tabella[toGo.getRiga() + 1][toGo.getColonna() - 1] != null
									&& tabella[toGo.getRiga() + 1][toGo.getColonna() - 1].getNome() == 'K'
									&& tabella[toGo.getRiga() + 1][toGo.getColonna() - 1].getColore() != pezzo
											.getColore())
							|| (tabella[toGo.getRiga() - 1][toGo.getColonna() - 1] != null
									&& tabella[toGo.getRiga() - 1][toGo.getColonna() - 1].getNome() == 'K'
									&& tabella[toGo.getRiga() - 1][toGo.getColonna() - 1].getColore() != pezzo
											.getColore()))
						return false;
				}
				if (balt >= 1 && bsx >= 1 && bbas == 0) {
					if ((tabella[toGo.getRiga()][toGo.getColonna() - 1] != null
							&& tabella[toGo.getRiga()][toGo.getColonna() - 1].getNome() == 'K'
							&& tabella[toGo.getRiga()][toGo.getColonna() - 1].getColore() != pezzo.getColore())
							|| (tabella[toGo.getRiga() + 1][toGo.getColonna() - 1] != null
									&& tabella[toGo.getRiga() + 1][toGo.getColonna() - 1].getNome() == 'K'
									&& tabella[toGo.getRiga() + 1][toGo.getColonna() - 1].getColore() != pezzo
											.getColore()))
						return false;
				}
				if (bbas >= 1 && bsx >= 1 && balt == 0) {
					if ((tabella[toGo.getRiga()][toGo.getColonna() - 1] != null
							&& tabella[toGo.getRiga()][toGo.getColonna() - 1].getNome() == 'K'
							&& tabella[toGo.getRiga()][toGo.getColonna() - 1].getColore() != pezzo.getColore())
							|| (tabella[toGo.getRiga() - 1][toGo.getColonna() - 1] != null
									&& tabella[toGo.getRiga() - 1][toGo.getColonna() - 1].getNome() == 'K'
									&& tabella[toGo.getRiga() - 1][toGo.getColonna() - 1].getColore() != pezzo
											.getColore()))
						return false;
				}
			}
		}


        return true;  
    }

    private void isEnpassant(Comando comando){
        if (comando.pezzoMosso != null){
            if ((comando.pezzoMosso.getColore() == bianco) && (comando.inizialeTradotta.getRiga() == rigaPedoniBianchi) && (comando.posizioneTradotta.getRiga() - comando.inizialeTradotta.getRiga() == 2)){
                comando.pezzoMosso.setCatturabileE();
            }
            else if ((comando.pezzoMosso.getColore() == nero) && (comando.inizialeTradotta.getRiga() == rigaPedoniNeri) && (comando.inizialeTradotta.getRiga() - comando.posizioneTradotta.getRiga() == 2)){
                comando.pezzoMosso.setCatturabileE();
            }
        }
    }

    public void display() throws UnsupportedEncodingException {
        char[] nomiColonne = {'a' , 'b' , 'c' , 'd' , 'e' , 'f' , 'g' , 'h'};
        System.out.print(" ");
        for (int i  = 0; i < colonne; i++)
        {
            System.out.print(" " + nomiColonne[i]);
        }
        System.out.println();
        for (int i = righe - 1; i >= 0; i--){
            System.out.print((i + 1) + " ");
            for (int j = 0; j < colonne; j++){
                if (tabella[i][j] != null){
                    Font font = new Font("NSimSun" , Font.ITALIC , 14);
                    System.setOut(new PrintStream(System.out, false, "UTF-8"));
                    System.out.print( tabella[i][j].getSimbolo() + " " );

                }
                else{
                    if (i == 0){
                        System.setOut(new PrintStream(System.out, false, "UTF-8"));
                        System.out.print(altriPezzineri[j] + " ");
                    }
                    else if (i == 7){
                        System.setOut(new PrintStream(System.out, false, "UTF-8"));
                        System.out.print(altriPezzibiachi[j] + " ");
                    }
                    else{
                        System.out.print("x ");
                    }
                }
            }
            System.out.println((i + 1) + " ");
        }
        System.out.print(" ");
        for (int i  = 0; i < colonne; i++)
        {
            System.out.print(" " + nomiColonne[i]);
        }
    }

    public void cambiaTurno(){
        if (turno == bianco){
            turno = nero;
        }
        else{
            turno = bianco;
        }
    }

    public void restart(){
        turno = bianco;
        tabella = new Pezzo[8][8];
        neriMangiati = new Mangiati(16);
        bianchiMangiati = new Mangiati(16);
        pedoniBianchi = new Pezzo[8];
        pedoniNeri = new Pezzo[8];
        comandi = new Vector<String>();
        for (int i = 0; i < 8; i++){
            Posizione pos = new Posizione(rigaPedoniBianchi , i);
            tabella[rigaPedoniBianchi][i] = new Pedone(0 , pos );
            pedoniBianchi[i] = tabella[rigaPedoniBianchi][i];
        }
        for (int j = 0; j < 8; j++){
            Posizione pos = new Posizione(rigaPedoniNeri , j);
            tabella[rigaPedoniNeri][j] = new Pedone(1 , pos );
            pedoniNeri[j] = tabella[rigaPedoniNeri][j];
        }
    }

    public void esciDalGioco(){
        System.exit(0);
    }



    public class Comando {
        private Posizione posizioneTradotta;//essendo il comando un vettore di char trasformiamo i char in int , la a minuscola Ã¨ il 97 esimo carattere e ha valore 97 invece l'1 ha valore 49
        private Posizione inizialeTradotta;
        private Pezzo pezzoMosso;
        private boolean cattura;
        private boolean sistema;
        private String comando;
        private char regina = 'Q';
        private char re = 'K';
        private char alfiere = 'B';
        private char torre = 'R';
        private char cavallo = 'N';
        private String[] comandiSistema = {"help" , "board" , "captures" , "moves" , "quit" , "play"};

        Comando(String commands) {
            this.comando = commands;
            if (comando.length() == 0) {

            } else if (isSys()) {
                sistema = true;
            } else {
                traduzionePosFinale();
                traduzionePosIniziale();
            }
        }

        private boolean isSys(){
            boolean esito = false;
            for (int i = 0; i < comandiSistema.length && !esito; i++){
                if (comandiSistema[i].equals(comando)){
                    esito = true;
                }
            }
            return esito;
        }

        public void traduzionePosIniziale() {
            boolean esito = false;
            if (posizioneTradotta != null) {
                if (turno == bianco) {
                    if ((comando.charAt(0) - 97 >= 0) && (comando.charAt(0) - 97 <= 7)) {
                        for (int i = 0; i < pedoniBianchi.length && !esito; i++) {
                            if (controlMovimento(pedoniBianchi[i], this)) {
                                inizialeTradotta = pedoniBianchi[i].getPosizione();
                                pezzoMosso = pedoniBianchi[i];
                                esito = true;
                            }
                        }//cicla  i pedoni e vede quale di questi si puÃ² muovere
                        if (!esito) {
                            System.out.println("mossa illegale");
                        }
                    }//controllano tutti i pedoni e si vede si potrebbe implementare un algoritmo di ricerca migliore che cerca  a partire dalla colonna iniziale del pedone perchÃ¨ si ha piu probabilitÃ  di torvarlo vicino che lontano perÃ² dato che i dati non sono ocsi grossi e massicci possiamo lasiare questa
                }//controlla se si sta muovendo un pedone, e traduce il movimento
                else {
                    if ((comando.charAt(0) - 97 >= 0) && (comando.charAt(0) - 97 <= 7)) {
                        for (int i = 0; i < pedoniNeri.length && !esito; i++) {
                            if (controlMovimento(pedoniNeri[i], this)) {
                                inizialeTradotta = pedoniNeri[i].getPosizione();
                                pezzoMosso = pedoniNeri[i];
                                esito = true;
                            }
                        }
                        if (!esito) {
                            System.out.println("mossa illegale");
                        }
                    }
                }//le funzioni cmabiano a seconda se si sta giocando nero o bianco qunid c'Ã¨ un if grosso alla inizio
            } else {
                inizialeTradotta = null;
            }//dato che non hai scirtto bene il comando acnhe la posizione finale Ã¨ nulla
        }//qusta funzione si occupa solo di tradurre il movimento qualunque esso sia poi il contorlo se lo puÃ² fare o meno si farÃ  in seguito

        public void traduzionePosFinale() {
            if (comando.length() == 0) {
                posizioneTradotta = null;
                inizialeTradotta = null;
            } else if ((comando.length() == 2) && (comando.charAt(0) - 97 >= 0) && (comando.charAt(0) - 97 <= 7) && (comando.charAt(1) - 49 >= 0) && (comando.charAt(1) - 49 <= 7)) {
                posizioneTradotta = new Posizione(comando.charAt(1) - 49, comando.charAt(0) - 97);
                cattura = false;
            }//comando di movimento semplice per pedone
            else if ((comando.length() == 4) && (((comando.charAt(0) - 97 >= 0) && (comando.charAt(0) - 97 <= 7)) || ((comando.charAt(0) == regina) || (comando.charAt(0) == re) || (comando.charAt(0) == alfiere) || (comando.charAt(0) == torre) || (comando.charAt(0) == cavallo)) && (comando.charAt(1) == 'x') && ((comando.charAt(2) - 97 >= 0) && (comando.charAt(2) - 97 <= 7)) && ((comando.charAt(3) - 49 >= 0) && (comando.charAt(3) - 49 <= 7)))) {
                posizioneTradotta = new Posizione(comando.charAt(3) - 49, comando.charAt(2) - 97);
                cattura = true;
            }//comando di movimento con mangiata senza ambiguitÃ  che prevede anche l'ambiguitÃ  dei pedoni ne verifica la correttezza sintattica e assegna la posizione tradotta alla varibaile
            else if ((comando.length() == 6) && (((comando.charAt(0) - 97 >= 0) && (comando.charAt(0) - 97 <= 7)) || ((comando.charAt(0) == regina) || (comando.charAt(0) == re) || (comando.charAt(0) == alfiere) || (comando.charAt(0) == torre) || (comando.charAt(0) == cavallo)) && (comando.charAt(1) == 'x') && ((comando.charAt(2) - 97 >= 0) && (comando.charAt(2) - 97 <= 7)) && ((comando.charAt(3) - 49 >= 0) && (comando.charAt(3) - 49 <= 7) && (comando.charAt(4) == 'e') && (comando.charAt(5) == 'p')))) {
                posizioneTradotta = new Posizione(comando.charAt(3) - 49, comando.charAt(2) - 97);
                cattura = true;
            }
            else {
                posizioneTradotta = null;
            }//altrimenit la posizione tradotta Ã¨ nulla e se Ã¨ nulla significa che la roba non l'hai scirtta bene
        }


        public Posizione getPosizioneTradotta() {
            return posizioneTradotta;
        }


        public boolean scrittaBene() {
            if ((posizioneTradotta != null) && (inizialeTradotta != null)) {
                return true;
            } else {
                return false;
            }
        }

        public String getComando() {
            return comando;
        }

        public boolean getCattura() {
            return cattura;
        }

        public boolean getSistema() {
            return sistema;
        }

        public void getIstruzioni() throws UnsupportedEncodingException {
            if ((comando.equals("help")) || (comando.equals("Help")) || (comando.equals("HELP"))) {
                System.out.println("help      stampa elenco comandi");
                System.out.println("board     mostra la schacchiera");
                System.out.println("captures  mostra i pezzi mangiati dell'avversario");
                System.out.println("moves     mostra le mosse effettuate finora");
                System.out.println("quit      esci dal gioco");
                System.out.println("play      inizia una nuova partita");
            } else if (comando.equals("board")) {
                display();
            } else if (comando.equals("quit")) {
                esciDalGioco();
            } else if (comando.equals("captures")) {
                if (turno == bianco) {
                    neriMangiati.displayMangiati();
                } else {
                    bianchiMangiati.displayMangiati();
                }
            } else if (comando.equals("moves")) {
                stampaComandi();
            } else if (comando.equals("play")) {
                restart();
                System.out.println("nuova partita iniziata, inserire help per vededere elenco comandi disponibili");
            }
            else {
                System.out.println("comando non riconosciuto, inserire /help per vedere elenco comand disponibili");
            }
            System.out.println();
            posizioneTradotta = null;
        }
    }


    protected class Mangiati{
        private int numeroMangiati = 0;
        private int pedoniMangiati = 0;
        private Pezzo[] mangiati;
        Mangiati(int grandezza){
            mangiati = new Pezzo[grandezza];
        }

        public int getNumeroMangiati() {
            return numeroMangiati;
        }

        public void incrase(Pezzo pezzo) {
            numeroMangiati++;
            if (pezzo.getNome() == 'p'){
                pedoniMangiati++;
            }
        }
        public void Set(Pezzo pezzo){
            if (numeroMangiati < mangiati.length){
                mangiati[numeroMangiati] = pezzo;
                incrase(pezzo);
            }
            else{
                System.out.println("array pieno li hai mangiati tutti");
            }
        }
        public void displayMangiati(){
            if (numeroMangiati == 0){
                System.out.println("nessun pezzo mangiato");
            }
            else{
                System.out.println(mangiati[0].getSimbolo() + " x " + numeroMangiati);
            }
        }
    }
}

