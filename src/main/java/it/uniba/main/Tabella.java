package it.uniba.main;

import java.awt.*;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.Vector;

/* classe di tipo boundary
 * responsabilita:
 * - permette l'esecuzione di una mossa
 * - permette lo svolgimento della partita (avvio, chiusura, cambio turno, richiesta comandi)
 * - prepara la scacchiera per la partita
 * - richiede all'utente l'inserimento di un comando
 * - permette di visualizzare la scacchiera e le informazioni della partita
 * -
 */

public class Tabella {
	private int righe;
	private int colonne;
	private Pezzo[][] tabella;
	private Pezzo[] pedoniBianchi;
	private Pezzo[] pedoniNeri;
	private Cavallo[] cavalliBianchi;
	private Cavallo[] cavalliNeri;
	private Alfiere[] alfieriBianchi;
	private Alfiere[] alfieriNeri;
	private Torre[] torreBianche;
	private Torre[] torreNere;
	private Donna donnaNera;
	private Donna donnaBianca;
	private Re reBianco;
	private Re reNero;
	private Mangiati bianchiMangiati;
	private Mangiati neriMangiati;
	private int rigaPedoniBianchi = 1;
	private int rigaPedoniNeri = 6;
	private int bianco = 0;
	private int nero = 1;
	private int turno;
	private Vector<String> comandi;

	Tabella(int righe, int colonne) {
		this.righe = righe;
		this.colonne = colonne;
		turno = bianco;
		tabella = new Pezzo[8][8];
		neriMangiati = new Mangiati(16);
		bianchiMangiati = new Mangiati(16);
		pedoniBianchi = new Pezzo[8];
		pedoniNeri = new Pezzo[8];
		cavalliBianchi = new Cavallo[2];
		cavalliNeri = new Cavallo[2];
		alfieriBianchi = new Alfiere[2];
		alfieriNeri = new Alfiere[2];
		torreBianche = new Torre[2];
		torreNere = new Torre[2];
		comandi = new Vector<String>();
		for (int i = 0; i < 8; i++) {
			Posizione pos = new Posizione(rigaPedoniBianchi, i);
			tabella[rigaPedoniBianchi][i] = new Pedone(0, pos);
			pedoniBianchi[i] = tabella[rigaPedoniBianchi][i];
			if (i == 1) {
				Posizione posCB = new Posizione(0, 1);
				tabella[0][1] = new Cavallo(0, posCB);
				cavalliBianchi[0] = (Cavallo) tabella[0][i];
			} else if (i == 6) {
				Posizione posCB = new Posizione(0, i);
				tabella[0][i] = new Cavallo(0, posCB);
				cavalliBianchi[1] = (Cavallo) tabella[0][i];
			} else if (i == 0) {
				Posizione posTB = new Posizione(0, i);
				tabella[0][i] = new Torre(0, posTB);
				torreBianche[0] = (Torre) tabella[0][i];
			} else if (i == 7) {
				Posizione posTB = new Posizione(0, i);
				tabella[0][i] = new Torre(0, posTB);
				torreBianche[1] = (Torre) tabella[0][i];
			} else if (i == 2) {
				Posizione posAB = new Posizione(0, i);
				tabella[0][i] = new Alfiere(0, posAB);
				alfieriBianchi[0] = (Alfiere) tabella[0][i];
			} else if (i == 5) {
				Posizione posAB = new Posizione(0, i);
				tabella[0][i] = new Alfiere(0, posAB);
				alfieriBianchi[1] = (Alfiere) tabella[0][i];
			} else if (i == 3) {
				Posizione posDB = new Posizione(0, i);
				tabella[0][i] = new Donna(0, posDB);
				donnaBianca = (Donna) tabella[0][i];
			} else if (i == 4) {
				Posizione posRB = new Posizione(4, i);
				tabella[4][i] = new Re(0, posRB);
				reBianco = (Re) tabella[4][i];
			}
		}
		for (int j = 0; j < 8; j++) {
			Posizione pos = new Posizione(rigaPedoniNeri, j);
			tabella[rigaPedoniNeri][j] = new Pedone(1, pos);
			pedoniNeri[j] = tabella[rigaPedoniNeri][j];
			if (j == 1) {
				Posizione posCN = new Posizione(7, j);
				tabella[7][j] = new Cavallo(1, posCN);
				cavalliNeri[0] = (Cavallo) tabella[7][j];
			} else if (j == 6) {
				Posizione posCN = new Posizione(7, j);
				tabella[7][j] = new Cavallo(1, posCN);
				cavalliNeri[1] = (Cavallo) tabella[7][j];
			} else if (j == 0) {
				Posizione posTN = new Posizione(7, j);
				tabella[7][j] = new Torre(1, posTN);
				torreNere[0] = (Torre) tabella[7][j];
			} else if (j == 7) {
				Posizione posTN = new Posizione(7, j);
				tabella[7][j] = new Torre(1, posTN);
				torreNere[1] = (Torre) tabella[7][j];
			} else if (j == 2) {
				Posizione posAN = new Posizione(7, j);
				tabella[7][j] = new Alfiere(1, posAN);
				alfieriNeri[0] = (Alfiere) tabella[7][j];
			} else if (j == 5) {
				Posizione posAN = new Posizione(7, j);
				tabella[7][j] = new Alfiere(1, posAN);
				alfieriNeri[1] = (Alfiere) tabella[7][j];
			} else if (j == 3) {
				Posizione posDN = new Posizione(7, j);
				tabella[7][j] = new Donna(1, posDN);
				donnaNera = (Donna) tabella[7][j];
			} else if (j == 4) {
				Posizione posRN = new Posizione(7, j);
				tabella[7][j] = new Re(1, posRN);
				reNero = (Re) tabella[7][j];
			}
		}
	}

	public int getRighe() {
		return righe;
	}

	public int getColonne() {
		return colonne;
	}

	public void setTabella(Pezzo pezzo, Posizione toGo) {
		tabella[toGo.getRiga()][toGo.getColonna()] = pezzo;
	}

	public Pezzo getTabella(Posizione toGet) {
		return tabella[toGet.getRiga()][toGet.getColonna()];
	}

	public void move(Posizione toGo, Pezzo pezzo) {
		if ((this.getTabella(toGo) != null)) {
			mangiaPedina(toGo, pezzo);
			System.out.println("Pezzo Mangiato");
			pezzo.giaMosso();
		} else if ((tabella[pezzo.getPosizione().getRiga()][toGo.getColonna()] != null)
				&& (tabella[pezzo.getPosizione().getRiga()][toGo.getColonna()].getCatturabileE())
				&& (toGo.getColonna() != pezzo.getPosizione().getColonna() && pezzo.getNome() == 'p')) {
			mangiaPedinaEnpassant(toGo, pezzo);
			pezzo.giaMosso();
		} else {
			Posizione pos1 = pezzo.getPosizione();
			setTabella(pezzo, toGo);
			setTabella(null, pos1);
			pezzo.setPosizione(toGo);
			pezzo.giaMosso();
			
		}
	}

	public void stampaTurno() {
		if (turno == bianco) {
			System.out.println("muove il bianco:");
		} else {
			System.out.println("muove il nero:");
		}
	}

	public void muovicondomanda() throws UnsupportedEncodingException {
		stampaTurno();
		String moss1;
		Scanner input = new Scanner(System.in);
		moss1 = input.nextLine();
		if (moss1.length() == 0) {
			System.out.println("mossa illegale");
		}
		Comando comando = new Comando(moss1);
		if (comando.getSistema()) {
			comando.getIstruzioni();
		} else if (comando.scrittaBene()) {
			isEnpassant(comando);
			if (comando.isArrocco()) {
				boolean esito = false;
				if (turno == bianco) {
					if ((comando.getComando().equals("0-0") || comando.getComando().equals("O-O"))
							&& controlMovimento(tabella[0][4], comando)) {
						esito = true;
						move(new Posizione(0, 6), tabella[0][4]);
						tabella[0][6].giaMosso();
						comandi.add(comando.getComando());
						move(new Posizione(0, 5), tabella[0][7]);
						System.out.println("Pezzo Mosso");
						tabella[0][5].giaMosso();
						cambiaTurno();
					} else {
						if (controlMovimento(tabella[0][4], comando)) {
							esito = true;
							move(new Posizione(0, 1), tabella[0][4]);
							tabella[0][1].giaMosso();
							comandi.add(comando.getComando());
							move(new Posizione(0, 2), tabella[0][0]);
							System.out.println("Pezzo Mosso");
							tabella[0][2].giaMosso();
							cambiaTurno();
						}
					}
				} else {
					if ((comando.getComando().equals("0-0") || comando.getComando().equals("O-O"))
							&& controlMovimento(tabella[7][4], comando)) {
						esito = true;
						move(new Posizione(7, 6), tabella[7][4]);
						tabella[7][6].giaMosso();
						comandi.add(comando.getComando());
						move(new Posizione(7, 5), tabella[7][7]);
						System.out.println("Pezzo Mosso");
						tabella[7][5].giaMosso();
						cambiaTurno();
					} else {
						if (controlMovimento(tabella[7][4], comando)) {
							esito = true;
							move(new Posizione(7, 1), tabella[7][4]);
							tabella[7][1].giaMosso();
							comandi.add(comando.getComando());
							move(new Posizione(7, 2), tabella[7][0]);
							System.out.println("Pezzo Mosso");
							tabella[7][2].giaMosso();
							cambiaTurno();
						}
					}
				}
				if (!esito)
					System.out.println("Mossa illegale");
			} else {
				move(comando.posizioneTradotta,
						tabella[comando.inizialeTradotta.getRiga()][comando.inizialeTradotta.getColonna()]);
				comandi.add(comando.getComando());
				System.out.println("Pezzo Mosso");
				cambiaTurno();
			}

		} else if (!comando.scrittaBene()) {

			System.out.println("mossa illegale");
		}
	}

	public void stampaComandi() {
		for (int i = 0; i < comandi.size(); i++) {
			System.out.print(comandi.get(i) + ";  ");
			if (i % 2 == 1) {
				System.out.println();
			}
		}
	}

	private void mangiaPedina(Posizione toGo, Pezzo pezzo) {
		if (tabella[toGo.getRiga()][toGo.getColonna()].getColore() == bianco) {
			bianchiMangiati.Set(tabella[toGo.getRiga()][toGo.getColonna()]);
		} else {
			neriMangiati.Set(tabella[toGo.getRiga()][toGo.getColonna()]);
		}
		Posizione pos1 = pezzo.getPosizione();
		setTabella(null, pos1);
		pezzo.setPosizione(toGo);
		setTabella(pezzo, toGo);

	}

	private void mangiaPedinaEnpassant(Posizione toGo, Pezzo pezzo) {
		if (tabella[pezzo.getPosizione().getRiga()][toGo.getColonna()].getColore() == bianco) {
			bianchiMangiati.Set(tabella[pezzo.getPosizione().getRiga()][toGo.getColonna()]);
		} else {
			neriMangiati.Set(tabella[pezzo.getPosizione().getRiga()][toGo.getColonna()]);
		}
		Posizione pos1 = pezzo.getPosizione(); // posizione del pezzo iniziale
		Posizione pos2 = new Posizione(pezzo.getPosizione().getRiga(), toGo.getColonna()); // pezzo da mangiare
		setTabella(null, pos1); // mette null la posizione precedente di pezzo
		setTabella(null, pos2); // mette null la posizione dove stava la pedina mangiata
		pezzo.setPosizione(toGo); // mette la poiszione di pezzo uguali al posto in cui � ora
		setTabella(pezzo, toGo); // mette pezzo nel posto della tabella
		System.out.println("pedone mangiato con en passant");
	}

	private boolean controlMovimento(Pezzo pezzo, Comando comando) {
		Posizione toGo = comando.posizioneTradotta;
		if (comando.isArrocco()) {
			if (pezzo.getColore() == bianco) {
				if (comando.getComando().equals("0-0") || comando.getComando().equals("O-O")) {
					if (tabella[0][7].getNome() == 'R' && tabella[0][7].getEnpassant() && tabella[0][4].getNome() == 'K'
							&& tabella[0][4].getEnpassant() && tabella[0][5] == null && tabella[0][6] == null
							&& controlloGittata(pezzo, toGo))
						return true;
				} else {
					if (tabella[0][0].getNome() == 'R' && tabella[0][0].getEnpassant() && tabella[0][4].getNome() == 'K'
							&& tabella[0][4].getEnpassant() && tabella[0][1] == null && tabella[0][2] == null
							&& tabella[0][3] == null && controlloGittata(pezzo, toGo))
						return true;
				}
			} else {
				if (comando.getComando().equals("0-0") || comando.getComando().equals("O-O")) {
					if (tabella[7][7].getNome() == 'R' && tabella[7][7].getEnpassant() && tabella[7][4].getNome() == 'K'
							&& tabella[7][4].getEnpassant() && tabella[7][5] == null && tabella[7][6] == null
							&& controlloGittata(pezzo, toGo))
						return true;
				} else {
					if (tabella[7][0].getNome() == 'R' && tabella[7][0].getEnpassant() && tabella[7][4].getNome() == 'K'
							&& tabella[7][4].getEnpassant() && tabella[7][1] == null && tabella[7][2] == null
							&& tabella[7][3] == null && controlloGittata(pezzo, toGo))
						return true;
				}
			}
		} else if (pezzo.getNome() == 'p') {
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
						&& (toGo.getRiga() - pezzo.getPosizione().getRiga() < 0)) {
					isEnpassant(comando);
					return true;
				}

			}
		} else if (pezzo.getNome() == 'N') {
			return ((pezzo.getGittata() >= Math.abs(pezzo.getPosizione().getRiga() - toGo.getRiga()))
					&& (pezzo.getGittata() >= Math.abs(pezzo.getPosizione().getColonna() - toGo.getColonna()))
					&& (Math.abs(toGo.getColonna() - pezzo.getPosizione().getColonna()) != Math
							.abs(toGo.getRiga() - pezzo.getPosizione().getRiga()))
					&& ((tabella[toGo.getRiga()][toGo.getColonna()] == null)
							|| (tabella[toGo.getRiga()][toGo.getColonna()].getColore() != pezzo.getColore()))
					&& (toGo.getColonna() != pezzo.getPosizione().getColonna())
					&& (toGo.getRiga() != pezzo.getPosizione().getRiga()));
		} else if (pezzo.getNome() == 'R') {
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
		} else if (pezzo.getNome() == 'B') {
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
		} else if (pezzo.getNome() == 'Q') { // vede se non sta catturando oppure se sta catturando e se il colore �
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
		} else if (pezzo.getNome() == 'K') {
			return ((((Math.abs(toGo.getRiga() - pezzo.getPosizione().getRiga()) == pezzo.getGittata())
					&& (Math.abs(toGo.getColonna() - pezzo.getPosizione().getColonna()) == pezzo.getGittata())
					&& (tabella[toGo.getRiga()][toGo.getColonna()] == null) && controlloGittata(pezzo, toGo)
					&& (!comando.getCattura()))
					|| ((tabella[toGo.getRiga()][toGo.getColonna()] != null)
							&& (tabella[toGo.getRiga()][toGo.getColonna()].getColore() != pezzo.getColore())
							&& controlloGittata(pezzo, toGo) && comando.getCattura()))
					|| (((toGo.getRiga() - pezzo.getPosizione().getRiga() == 0
							&& (Math.abs(toGo.getColonna() - pezzo.getPosizione().getColonna()) == pezzo.getGittata()))
							|| (toGo.getColonna() - pezzo.getPosizione().getColonna() == 0 && (Math.abs(toGo.getRiga() - pezzo.getPosizione().getRiga()) == pezzo.getGittata())))
							&& ((tabella[toGo.getRiga()][toGo.getColonna()] == null) && controlloGittata(pezzo, toGo)
									&& (!comando.getCattura()))
							|| ((tabella[toGo.getRiga()][toGo.getColonna()] != null)
									&& (tabella[toGo.getRiga()][toGo.getColonna()].getColore() != pezzo.getColore())
									&& controlloGittata(pezzo, toGo) && comando.getCattura())));
		}

		return false;
	}// controlla se per ogni tipo di pezzo � possibile lo spostamento

	private boolean controlloGittata(Pezzo pezzo, Posizione toGo) {
		if (pezzo.getNome() == 'p') {
			if (pezzo.getColore() == bianco) {
				for (int i = 1; i <= pezzo.getGittata(); i++) {
					if (pezzo.getPosizione().getRiga() + i > 7) {
						return false;
					} else if (tabella[pezzo.getPosizione().getRiga() + i][pezzo.getPosizione().getColonna()] != null) {
						return false;
					}
				}
			} else {
				for (int i = 1; i <= pezzo.getGittata(); i++) {
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
			}
			/* destra */
			else if (toGo.getRiga() == pezzo.getPosizione().getRiga()
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
		} else if (pezzo.getNome() == 'B') {
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
		} else if (pezzo.getNome() == 'Q') {
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
			}
			/* destra */
			else if (toGo.getRiga() == pezzo.getPosizione().getRiga()
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

	private void isEnpassant(Comando comando) {
		if (comando.pezzoMosso != null && comando.pezzoMosso.getNome() == 'p') {
			if ((comando.pezzoMosso.getColore() == bianco) && (comando.inizialeTradotta.getRiga() == rigaPedoniBianchi)
					&& (comando.posizioneTradotta.getRiga() - comando.inizialeTradotta.getRiga() == 2)) {
				comando.pezzoMosso.setCatturabileE();
			} else if ((comando.pezzoMosso.getColore() == nero)
					&& (comando.inizialeTradotta.getRiga() == rigaPedoniNeri)
					&& (comando.inizialeTradotta.getRiga() - comando.posizioneTradotta.getRiga() == 2)) {
				comando.pezzoMosso.setCatturabileE();
			}
		}
	}

	public void display() throws UnsupportedEncodingException {
		char[] nomiColonne = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
		System.out.print(" ");
		for (int i = 0; i < colonne; i++) {
			System.out.print(" " + nomiColonne[i]);
		}
		System.out.println();
		for (int i = righe - 1; i >= 0; i--) {
			System.out.print((i + 1) + " ");
			for (int j = 0; j < colonne; j++) {
				if (tabella[i][j] != null) {
					Font font = new Font("NSimSun", Font.ITALIC, 14);
					System.setOut(new PrintStream(System.out, false, "UTF-8"));
					System.out.print(tabella[i][j].getSimbolo() + " ");

				} else {
					System.out.print("x ");
				}
			}
			System.out.println((i + 1) + " ");
		}
		System.out.print(" ");
		for (int i = 0; i < colonne; i++) {
			System.out.print(" " + nomiColonne[i]);
		}
	}

	public void cambiaTurno() {
		if (turno == bianco) {
			turno = nero;
		} else {
			turno = bianco;
		}
	}

	public void restart() {
		turno = bianco;
		tabella = new Pezzo[8][8];
		neriMangiati = new Mangiati(16);
		bianchiMangiati = new Mangiati(16);
		pedoniBianchi = new Pezzo[8];
		pedoniNeri = new Pezzo[8];
		cavalliBianchi = new Cavallo[2];
		cavalliNeri = new Cavallo[2];
		alfieriBianchi = new Alfiere[2];
		alfieriNeri = new Alfiere[2];
		torreBianche = new Torre[2];
		torreNere = new Torre[2];
		comandi = new Vector<String>();
		for (int i = 0; i < 8; i++) {
			Posizione pos = new Posizione(rigaPedoniBianchi, i);
			tabella[rigaPedoniBianchi][i] = new Pedone(0, pos);
			pedoniBianchi[i] = tabella[rigaPedoniBianchi][i];
			if (i == 1) {
				Posizione posCB = new Posizione(0, 1);
				tabella[0][1] = new Cavallo(0, posCB);
				cavalliBianchi[0] = (Cavallo) tabella[0][i];
			} else if (i == 6) {
				Posizione posCB = new Posizione(0, i);
				tabella[0][i] = new Cavallo(0, posCB);
				cavalliBianchi[1] = (Cavallo) tabella[0][i];
			} else if (i == 0) {
				Posizione posTB = new Posizione(0, i);
				tabella[0][i] = new Torre(0, posTB);
				torreBianche[0] = (Torre) tabella[0][i];
			} else if (i == 7) {
				Posizione posTB = new Posizione(0, i);
				tabella[0][i] = new Torre(0, posTB);
				torreBianche[1] = (Torre) tabella[0][i];
			} else if (i == 2) {
				Posizione posAB = new Posizione(0, i);
				tabella[0][i] = new Alfiere(0, posAB);
				alfieriBianchi[0] = (Alfiere) tabella[0][i];
			} else if (i == 5) {
				Posizione posAB = new Posizione(0, i);
				tabella[0][i] = new Alfiere(0, posAB);
				alfieriBianchi[1] = (Alfiere) tabella[0][i];
			} else if (i == 3) {
				Posizione posDB = new Posizione(0, i);
				tabella[0][i] = new Donna(0, posDB);
				donnaBianca = (Donna) tabella[0][i];
			} else if (i == 4) {
				Posizione posRB = new Posizione(0, i);
				tabella[0][i] = new Re(0, posRB);
				reBianco = (Re) tabella[0][i];
			}
		}
		for (int j = 0; j < 8; j++) {
			Posizione pos = new Posizione(rigaPedoniNeri, j);
			tabella[rigaPedoniNeri][j] = new Pedone(1, pos);
			pedoniNeri[j] = tabella[rigaPedoniNeri][j];
			if (j == 1) {
				Posizione posCN = new Posizione(7, j);
				tabella[7][j] = new Cavallo(1, posCN);
				cavalliNeri[0] = (Cavallo) tabella[7][j];
			} else if (j == 6) {
				Posizione posCN = new Posizione(7, j);
				tabella[7][j] = new Cavallo(1, posCN);
				cavalliNeri[1] = (Cavallo) tabella[7][j];
			}
			if (j == 0) {
				Posizione posTN = new Posizione(7, j);
				tabella[7][j] = new Torre(1, posTN);
				torreNere[0] = (Torre) tabella[7][j];
			} else if (j == 7) {
				Posizione posTN = new Posizione(7, j);
				tabella[7][j] = new Torre(1, posTN);
				torreNere[1] = (Torre) tabella[7][j];
			} else if (j == 2) {
				Posizione posAN = new Posizione(7, j);
				tabella[7][j] = new Alfiere(1, posAN);
				alfieriNeri[0] = (Alfiere) tabella[7][j];
			} else if (j == 5) {
				Posizione posAN = new Posizione(7, j);
				tabella[7][j] = new Alfiere(1, posAN);
				alfieriNeri[1] = (Alfiere) tabella[7][j];
			} else if (j == 3) {
				Posizione posDN = new Posizione(7, j);
				tabella[7][j] = new Donna(1, posDN);
				donnaNera = (Donna) tabella[7][j];
			} else if (j == 4) {
				Posizione posRN = new Posizione(7, j);
				tabella[7][j] = new Re(1, posRN);
				reNero = (Re) tabella[7][j];
			}
		}
	}

	public void esciDalGioco() {
		System.exit(0);
	}

	public class Comando {
		private Posizione posizioneTradotta;// essendo il comando un vettore di char trasformiamo i char in int , la a
											// minuscola � il 97 esimo carattere e ha valore 97 invece l'1 ha valore 49
		private Posizione inizialeTradotta;
		private Pezzo pezzoMosso;
		private boolean cattura;
		private boolean sistema;
		private String comando;
		private char regina = 'D';
		private char re = 'R';
		private char alfiere = 'A';
		private char torre = 'T';
		private char cavallo = 'C';
		private String[] comandidArrocco = { "0-0-0", "O-O-O", "0-0", "O-O" };
		private String[] comandiSistema = { "help", "board", "captures", "moves", "quit", "play" };

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

		private boolean isSys() {
			boolean esito = false;
			for (int i = 0; i < comandiSistema.length && !esito; i++) {
				if (comandiSistema[i].equals(comando)) {
					esito = true;
				}
			}
			return esito;
		}

		private int cavalloAmbiguo() {
			int esito = 0; // 0 se non ambiguo 1 se ambiguo sulle riche quindi sulla stessa colonna, 2 se
							// ambiguo sulla colonne quindi sulla stessa riga
			int cont = 0;
			if (comando.charAt(0) == cavallo) {
				if (turno == bianco) {
					if (cavalliBianchi.length < 2) {
						return esito;
					} else {
						for (int i = 0; i < cavalliBianchi.length; i++) {
							if (controlMovimento(cavalliBianchi[i], this)) {
								cont++;
							}
						}
						if (cont == 2) {
							if (cavalliBianchi[0].getPosizione().getColonna() == cavalliBianchi[1].getPosizione()
									.getColonna()) {
								esito = 1;
							} else {
								esito = 2;
							}
						}
					}
				} else {
					if (cavalliNeri.length < 2) {
						return esito;
					} else {
						for (int i = 0; i < cavalliNeri.length; i++) {
							if (controlMovimento(cavalliNeri[i], this)) {
								cont++;
							}
						}
						if (cont == 2) {
							if (cavalliNeri[0].getPosizione().getColonna() == cavalliNeri[1].getPosizione()
									.getColonna()) {
								esito = 1;
							} else {
								esito = 2;
							}
						}
					}
				}
			} else if (comando.charAt(0) == torre) {
				if (turno == bianco) {
					if (torreBianche.length < 2) {
						return esito;
					} else {
						for (int i = 0; i < torreBianche.length; i++) {
							if (controlMovimento(torreBianche[i], this)) {
								cont++;
							}
						}
						if (cont == 2) {
							if (torreBianche[0].getPosizione().getRiga() == torreBianche[1].getPosizione().getRiga()) {
								esito = 2;
							} else if (torreBianche[0].getPosizione().getColonna() == torreBianche[1].getPosizione()
									.getColonna()) {
								esito = 1;
							} else {
								esito = 3; // se sono su righe e colonne diverse ma vogliono andare sullo stesso punto
							}
						}
					}
				} else {
					if (torreNere.length < 2) {
						return esito;
					} else {
						for (int i = 0; i < torreNere.length; i++) {
							if (controlMovimento(torreNere[i], this)) {
								cont++;
							}
						}
						if (cont == 2) {
							if (torreNere[0].getPosizione().getRiga() == torreNere[1].getPosizione().getRiga()) {
								esito = 2;
							} else if (torreNere[0].getPosizione().getColonna() == torreNere[1].getPosizione()
									.getColonna()) {
								esito = 1;
							} else {
								esito = 3; // se sono su righe e colonne diverse ma vogliono andare sullo stesso punto
							}
						}
					}
				}
			}

			return esito;
		}

		public void traduzionePosIniziale() {
			boolean esito = false;
			if (inizialeTradotta != null) {
				return;
			}
			if (posizioneTradotta != null) {
				if (turno == bianco) {
					if ((comando.charAt(0) - 97 >= 0) && (comando.charAt(0) - 97 <= 7)) {
						for (int i = 0; i < pedoniBianchi.length && !esito; i++) {
							if (controlMovimento(pedoniBianchi[i], this)) {
								inizialeTradotta = pedoniBianchi[i].getPosizione();
								pezzoMosso = pedoniBianchi[i];
								esito = true;
							}
						} // cicla i pedoni e vede quale di questi si pu� muovere
						if (!esito) {
							System.out.println("mossa illegale");
						}
					} // controllano tutti i pedoni e si vede si potrebbe implementare un algoritmo di
						// ricerca migliore che cerca a partire dalla colonna iniziale del pedone perch�
						// si ha piu probabilit� di torvarlo vicino che lontano per� dato che i dati non
						// sono ocsi grossi e massicci possiamo lasiare questa
					else if (comando.charAt(0) == cavallo) {
						for (int i = 0; i < cavalliBianchi.length && !esito; i++) {
							if (controlMovimento(cavalliBianchi[i], this)) {
								inizialeTradotta = cavalliBianchi[i].getPosizione();
								pezzoMosso = cavalliBianchi[i];
								esito = true;
							}
						}
						if (!esito) {
							System.out.println("mossa illegale");
						}
					} else if (comando.charAt(0) == torre) {
						for (int i = 0; i < torreBianche.length && !esito; i++) {
							if (controlMovimento(torreBianche[i], this)) {
								inizialeTradotta = torreBianche[i].getPosizione();
								pezzoMosso = torreBianche[i];
								esito = true;
							}
						}
					} else if (comando.charAt(0) == alfiere) {
						for (int i = 0; i < alfieriBianchi.length && !esito; i++) {
							if (controlMovimento(alfieriBianchi[i], this)) {
								inizialeTradotta = alfieriBianchi[i].getPosizione();
								pezzoMosso = alfieriBianchi[i];
								esito = true;
							}
						}
					} else if (comando.charAt(0) == regina) {
						if (donnaBianca != null) {
							if (controlMovimento(donnaBianca, this)) {
								inizialeTradotta = donnaBianca.getPosizione();
								pezzoMosso = donnaBianca;
								esito = true;
							}
						}
					} else if (comando.charAt(0) == re) {
						if (reBianco != null) {
							if (controlMovimento(reBianco, this)) {
								inizialeTradotta = reBianco.getPosizione();
								pezzoMosso = reBianco;
								esito = true;
							}
						}
					}

					else if (!esito) {
						System.out.println("mossa illegale");
					}
				} // controlla se si sta muovendo un pedone, e traduce il movimento
				else {
					if ((comando.charAt(0) - 97 >= 0) && (comando.charAt(0) - 97 <= 7)) {
						for (int i = 0; i < pedoniNeri.length && !esito; i++) {
							if (controlMovimento(pedoniNeri[i], this)) {
								inizialeTradotta = pedoniNeri[i].getPosizione();
								pezzoMosso = pedoniNeri[i];
								esito = true;
							}
						}

					} else if (comando.charAt(0) == cavallo) {
						for (int i = 0; i < cavalliNeri.length && !esito; i++) {
							if (controlMovimento(cavalliNeri[i], this)) {
								inizialeTradotta = cavalliNeri[i].getPosizione();
								pezzoMosso = cavalliNeri[i];
								esito = true;
							}
						}
					} // le funzioni cmabiano a seconda se si sta giocando nero o bianco qunid c'� un
						// if grosso alla inizio
					else if (comando.charAt(0) == torre) {
						for (int i = 0; i < torreNere.length && !esito; i++) {
							if (controlMovimento(torreNere[i], this)) {
								inizialeTradotta = torreNere[i].getPosizione();
								pezzoMosso = torreNere[i];
								esito = true;
							}
						}
					} else if (comando.charAt(0) == alfiere) {
						for (int i = 0; i < alfieriNeri.length && !esito; i++) {
							if (controlMovimento(alfieriNeri[i], this)) {
								inizialeTradotta = alfieriNeri[i].getPosizione();
								pezzoMosso = alfieriNeri[i];
								esito = true;
							}
						}
					} else if (comando.charAt(0) == regina) {
						if (donnaNera != null) {
							if (controlMovimento(donnaNera, this)) {
								inizialeTradotta = donnaNera.getPosizione();
								pezzoMosso = donnaNera;
								esito = true;
							}
						}
					} else if (comando.charAt(0) == re) {
						if (reNero != null) {
							if (controlMovimento(reNero, this)) {
								inizialeTradotta = reNero.getPosizione();
								pezzoMosso = reNero;
								esito = true;
							}
						}
					} else if (!esito) {
						System.out.println("mossa illegale");
					}
				}

			} else {
				inizialeTradotta = null;
			} // dato che non hai scirtto bene il comando acnhe la posizione finale � nulla
				// qusta funzione si occupa solo di tradurre il movimento qualunque esso sia poi
				// il contorlo se lo pu� fare o meno si far� in seguito
		}

		private boolean isArrocco() {
			boolean esito = false;
			for (int i = 0; i < comandidArrocco.length; i++) {
				if (comando.equals(comandidArrocco[i])) {
					return true;
				}
			}
			return false;
		}

		public void traduzionePosFinale() {
			if (isArrocco()) {
				if (turno == bianco) {
					if ((comando.equals(comandidArrocco[0]) || comando.equals(comandidArrocco[1]))
							&& (reBianco.getEnpassant()
									&& (torreBianche[0] != null && torreBianche[0].getEnpassant()))) {
						inizialeTradotta = reBianco.getPosizione();
						pezzoMosso = reBianco;
						posizioneTradotta = new Posizione(0, 6);
					} else if ((comando.equals(comandidArrocco[2]) || comando.equals(comandidArrocco[3]))
							&& (reBianco.getEnpassant()
									&& (torreBianche[1] != null && torreBianche[1].getEnpassant()))) {
						inizialeTradotta = reBianco.getPosizione();
						pezzoMosso = reBianco;
						posizioneTradotta = new Posizione(0, 1);
					}
				} else {
					if ((comando.equals(comandidArrocco[0]) || comando.equals(comandidArrocco[1]))
							&& (reNero.getEnpassant() && (torreNere[0] != null && torreNere[0].getEnpassant()))) {
						inizialeTradotta = reNero.getPosizione();
						pezzoMosso = reNero;
						posizioneTradotta = new Posizione(7, 1);
					} else if ((comando.equals(comandidArrocco[2]) || comando.equals(comandidArrocco[3]))
							&& (reNero.getEnpassant() && (torreNere[1] != null && torreNere[1].getEnpassant()))) {
						inizialeTradotta = reBianco.getPosizione();
						pezzoMosso = reNero;
						posizioneTradotta = new Posizione(7, 6);
					}
				}
			}
			if (comando.length() == 0) {
				posizioneTradotta = null;
				inizialeTradotta = null;
			} else if ((comando.length() == 2) && (comando.charAt(0) - 97 >= 0) && (comando.charAt(0) - 97 <= 7)
					&& (comando.charAt(1) - 49 >= 0) && (comando.charAt(1) - 49 <= 7)) {
				posizioneTradotta = new Posizione(comando.charAt(1) - 49, comando.charAt(0) - 97);
				cattura = false;
			} // comando di movimento semplice per pedone
			else if ((comando.length() == 4) && (((comando.charAt(0) - 97 >= 0) && (comando.charAt(0) - 97 <= 7))
					|| ((comando.charAt(0) == regina) || (comando.charAt(0) == re) || (comando.charAt(0) == alfiere))
							&& (comando.charAt(1) == 'x')
							&& ((comando.charAt(2) - 97 >= 0) && (comando.charAt(2) - 97 <= 7))
							&& ((comando.charAt(3) - 49 >= 0) && (comando.charAt(3) - 49 <= 7)))) {
				posizioneTradotta = new Posizione(comando.charAt(3) - 49, comando.charAt(2) - 97);
				cattura = true;
			} // comando di movimento con mangiata senza ambiguit� che prevede anche
				// l'ambiguit� dei pedoni ne verifica la correttezza sintattica e assegna la
				// posizione tradotta alla varibaile
			else if ((comando.length() == 6) && (((comando.charAt(0) - 97 >= 0) && (comando.charAt(0) - 97 <= 7))
					&& (comando.charAt(1) == 'x') && ((comando.charAt(2) - 97 >= 0) && (comando.charAt(2) - 97 <= 7))
					&& ((comando.charAt(3) - 49 >= 0) && (comando.charAt(3) - 49 <= 7) && (comando.charAt(4) == 'e')
							&& (comando.charAt(5) == 'p')))) {
				posizioneTradotta = new Posizione(comando.charAt(3) - 49, comando.charAt(2) - 97);
				cattura = true;
			} else if ((comando.length() == 3)
					&& ((comando.charAt(0) == regina) || (comando.charAt(0) == re) || (comando.charAt(0) == alfiere))
					&& ((comando.charAt(1) - 97 >= 0) && (comando.charAt(1) - 97 <= 7))
					&& ((comando.charAt(2) - 49 >= 0) && (comando.charAt(2) - 49 <= 7))) {
				posizioneTradotta = new Posizione(comando.charAt(2) - 49, comando.charAt(1) - 97);
			}

			// cavallo contorllo di ambiguita
			else if ((comando.charAt(0) == cavallo)) { // controlla l'ambiguit� del cavallo
				if ((comando.length() == 3) && (((comando.charAt(1) - 97 >= 0) && (comando.charAt(1) - 97 <= 7))
						&& ((comando.charAt(2) - 49 >= 0) && (comando.charAt(2) - 49 <= 7)))) {
					posizioneTradotta = new Posizione(comando.charAt(2) - 49, comando.charAt(1) - 97);
					if (cavalloAmbiguo() != 0) {
						posizioneTradotta = null;
					}
				} else if ((comando.length() == 4) && ((comando.charAt(1) == 'x')
						&& ((comando.charAt(2) - 97 >= 0) && (comando.charAt(2) - 97 <= 7))
						&& ((comando.charAt(3) - 49 >= 0) && (comando.charAt(3) - 49 <= 7)))) {
					posizioneTradotta = new Posizione(comando.charAt(3) - 49, comando.charAt(2) - 97);
					cattura = true;
					if (cavalloAmbiguo() != 0) {
						posizioneTradotta = null;
					}
				} else if ((comando.length() == 5)
						&& (((comando.charAt(1) - 97 >= 0) && (comando.charAt(1) - 97 <= 7))
								|| ((comando.charAt(1) - 49 >= 0) && (comando.charAt(1) - 49 <= 7)))
						&& (comando.charAt(2) == 'x')
						&& ((comando.charAt(3) - 97 >= 0) && (comando.charAt(3) - 97 <= 7))
						&& ((comando.charAt(4) - 49 >= 0) && (comando.charAt(4) - 49 <= 7))) {
					posizioneTradotta = new Posizione(comando.charAt(4) - 49, comando.charAt(3) - 97);
					if (cavalloAmbiguo() == 0) {
						posizioneTradotta = null;
						inizialeTradotta = null;
					} else if (cavalloAmbiguo() == 1) {
						if ((comando.charAt(1) - 49 >= 0) && (comando.charAt(1) - 49 <= 7)) {
							if (turno == bianco) {
								for (int i = 0; i < cavalliBianchi.length; i++) {
									if (cavalliBianchi[i].getPosizione().getRiga() == comando.charAt(1) - 49) {
										pezzoMosso = cavalliBianchi[i];
									}
								}
							} else {
								for (int i = 0; i < cavalliNeri.length; i++) {
									if (cavalliBianchi[i].getPosizione().getRiga() == comando.charAt(1) - 49) {
										pezzoMosso = cavalliNeri[i];
									}
								}
							}
							inizialeTradotta = pezzoMosso.getPosizione();
						} else {
							System.out.println("mossa illegale");
						}
					} else if (cavalloAmbiguo() == 2) {
						if ((comando.charAt(1) - 97 >= 0) && (comando.charAt(1) - 97 <= 7)) {
							if (turno == bianco) {
								for (int i = 0; i < cavalliBianchi.length; i++) {
									if (cavalliBianchi[i].getPosizione().getColonna() == comando.charAt(1) - 97) {
										pezzoMosso = cavalliBianchi[i];
									}
								}
							} else {
								for (int i = 0; i < cavalliNeri.length; i++) {
									if (cavalliBianchi[i].getPosizione().getColonna() == comando.charAt(1) - 97) {
										pezzoMosso = cavalliNeri[i];
									}
								}
							}
							inizialeTradotta = pezzoMosso.getPosizione();
						} else {
							System.out.println("mossa illegale");
							posizioneTradotta = null;
						}
					}
				} else if ((comando.length() == 4)
						&& (((comando.charAt(1) - 97 >= 0) && (comando.charAt(1) - 97 <= 7))
								|| ((comando.charAt(1) - 49 >= 0) && (comando.charAt(1) - 49 <= 7)))
						&& ((comando.charAt(2) - 97 >= 0) && (comando.charAt(2) - 97 <= 7))
						&& ((comando.charAt(3) - 49 >= 0) && (comando.charAt(3) - 49 <= 7))) {
					posizioneTradotta = new Posizione(comando.charAt(3) - 49, comando.charAt(2) - 97);
					if (cavalloAmbiguo() == 0) {
						posizioneTradotta = null;
						inizialeTradotta = null;
					} else if (cavalloAmbiguo() == 1) {
						if ((comando.charAt(1) - 49 >= 0) && (comando.charAt(1) - 49 <= 7)) {
							if (turno == bianco) {
								for (int i = 0; i < cavalliBianchi.length; i++) {
									if (cavalliBianchi[i].getPosizione().getRiga() == comando.charAt(1) - 49) {
										pezzoMosso = cavalliBianchi[i];
									}
								}
							} else {
								for (int i = 0; i < cavalliNeri.length; i++) {
									if (cavalliBianchi[i].getPosizione().getRiga() == comando.charAt(1) - 49) {
										pezzoMosso = cavalliNeri[i];
									}
								}
							}
							inizialeTradotta = new Posizione(comando.charAt(1) - 49,
									pezzoMosso.getPosizione().getColonna());
						} else {
							System.out.println("mossa illegale");
							posizioneTradotta = null;
						}
					} else if (cavalloAmbiguo() == 2) {
						if ((comando.charAt(1) - 97 >= 0) && (comando.charAt(1) - 97 <= 7)) {
							if (turno == bianco) {
								for (int i = 0; i < cavalliBianchi.length; i++) {
									if (cavalliBianchi[i].getPosizione().getColonna() == comando.charAt(1) - 97) {
										pezzoMosso = cavalliBianchi[i];
									}
								}
							} else {
								for (int i = 0; i < cavalliNeri.length; i++) {
									if (cavalliBianchi[i].getPosizione().getColonna() == comando.charAt(1) - 97) {
										pezzoMosso = cavalliNeri[i];
									}
								}
							}
							inizialeTradotta = pezzoMosso.getPosizione();
						} else {
							posizioneTradotta = null;
							System.out.println("mossa illegale");
						}
					}
				}
			} else if ((comando.charAt(0) == torre)) { // controlla se l'ambiguit� della torre
				if ((comando.length() == 3) && (((comando.charAt(1) - 97 >= 0) && (comando.charAt(1) - 97 <= 7))
						&& ((comando.charAt(2) - 49 >= 0) && (comando.charAt(2) - 49 <= 7)))) {
					posizioneTradotta = new Posizione(comando.charAt(2) - 49, comando.charAt(1) - 97);
					if (cavalloAmbiguo() != 0) {
						posizioneTradotta = null;
					}
				} else if ((comando.length() == 4) && ((comando.charAt(1) == 'x')
						&& ((comando.charAt(2) - 97 >= 0) && (comando.charAt(2) - 97 <= 7))
						&& ((comando.charAt(3) - 49 >= 0) && (comando.charAt(3) - 49 <= 7)))) {
					posizioneTradotta = new Posizione(comando.charAt(3) - 49, comando.charAt(2) - 97);
					cattura = true;
					if (cavalloAmbiguo() != 0) {
						posizioneTradotta = null;
					}
				} else if ((comando.length() == 5) && (comando.charAt(2) == 'x')
						&& ((comando.charAt(3) - 97 >= 0) && (comando.charAt(3) - 97 <= 7))
						&& ((comando.charAt(4) - 49 >= 0) && (comando.charAt(4) - 49 <= 7))) {
					posizioneTradotta = new Posizione(comando.charAt(4) - 49, comando.charAt(3) - 97);
					cattura = true;
					if (cavalloAmbiguo() == 0) {
						posizioneTradotta = null;
						inizialeTradotta = null;
					} else if (cavalloAmbiguo() == 1) {
						if ((comando.charAt(1) - 49 >= 0) && (comando.charAt(1) - 49 <= 7)) {
							inizialeTradotta = new Posizione(comando.charAt(1) - 49, comando.charAt(3) - 97);
							pezzoMosso = tabella[inizialeTradotta.getRiga()][inizialeTradotta.getColonna()];
						} else {
							System.out.println("mossa illegale");
						}
					} else if (cavalloAmbiguo() == 2) {
						if ((comando.charAt(1) - 97 >= 0) && (comando.charAt(1) - 97 <= 7)) {
							inizialeTradotta = new Posizione(comando.charAt(4) - 49, comando.charAt(1) - 97);
							pezzoMosso = tabella[inizialeTradotta.getRiga()][inizialeTradotta.getColonna()];
						} else {
							System.out.println("mossa illegale");
						}
					} else if (cavalloAmbiguo() == 3) {
						if ((comando.charAt(1) - 97 >= 0) && (comando.charAt(1) - 97 <= 7)) {
							if (comando.charAt(1) == comando.charAt(3)) {
								if (turno == bianco) {
									for (int i = 0; i < torreBianche.length; i++) {
										if (torreBianche[i].getPosizione().getColonna() == posizioneTradotta
												.getColonna()) {
											inizialeTradotta = torreBianche[i].getPosizione();
											pezzoMosso = tabella[inizialeTradotta.getRiga()][inizialeTradotta
													.getColonna()];
										}
									}
								} else {
									for (int i = 0; i < torreNere.length; i++) {
										if (torreNere[i].getPosizione().getColonna() == posizioneTradotta
												.getColonna()) {
											inizialeTradotta = torreNere[i].getPosizione();
											pezzoMosso = tabella[inizialeTradotta.getRiga()][inizialeTradotta
													.getColonna()];
										}
									}
								}
							} else if (comando.charAt(1) - 97 != posizioneTradotta.getColonna()) {
								inizialeTradotta = new Posizione(comando.charAt(4) - 49, comando.charAt(1) - 97);
								pezzoMosso = tabella[inizialeTradotta.getRiga()][inizialeTradotta.getColonna()];
							}
						} else {
							posizioneTradotta = null;
							System.out.println("mossa illegale");
						}
					} else {
						posizioneTradotta = null;
						System.out.println("mossa illegale");
					}
				} else if ((comando.length() == 4)
						&& (((comando.charAt(1) - 97 >= 0) && (comando.charAt(1) - 97 <= 7))
								|| ((comando.charAt(1) - 49 >= 0) && (comando.charAt(1) - 49 <= 7)))
						&& ((comando.charAt(2) - 97 >= 0) && (comando.charAt(2) - 97 <= 7))
						&& ((comando.charAt(3) - 49 >= 0) && (comando.charAt(3) - 49 <= 7))) {
					posizioneTradotta = new Posizione(comando.charAt(3) - 49, comando.charAt(2) - 97);
					if (cavalloAmbiguo() == 0) {
						posizioneTradotta = null;
						inizialeTradotta = null;
					} else if (cavalloAmbiguo() == 1) {
						if ((comando.charAt(1) - 49 >= 0) && (comando.charAt(1) - 49 <= 7)) {
							inizialeTradotta = new Posizione(comando.charAt(1) - 49, comando.charAt(2) - 97);
							pezzoMosso = tabella[inizialeTradotta.getRiga()][inizialeTradotta.getColonna()];
						} else {
							System.out.println("mossa illegale");
						}
					} else if (cavalloAmbiguo() == 2) {
						if ((comando.charAt(1) - 97 >= 0) && (comando.charAt(1) - 97 <= 7)) {
							inizialeTradotta = new Posizione(comando.charAt(3) - 49, comando.charAt(1) - 97);
							pezzoMosso = tabella[inizialeTradotta.getRiga()][inizialeTradotta.getColonna()];
						} else {
							System.out.println("mossa illegale");
						}
					} else if (cavalloAmbiguo() == 3) {
						if ((comando.charAt(1) - 97 >= 0) && (comando.charAt(1) - 97 <= 7)) {
							if (comando.charAt(1) == comando.charAt(2)) {
								if (turno == bianco) {
									for (int i = 0; i < torreBianche.length; i++) {
										if (torreBianche[i].getPosizione().getColonna() == posizioneTradotta
												.getColonna()) {
											inizialeTradotta = torreBianche[i].getPosizione();
											pezzoMosso = tabella[inizialeTradotta.getRiga()][inizialeTradotta
													.getColonna()];
										}
									}
								} else {
									for (int i = 0; i < torreNere.length; i++) {
										if (torreNere[i].getPosizione().getColonna() == posizioneTradotta
												.getColonna()) {
											inizialeTradotta = torreNere[i].getPosizione();
											pezzoMosso = tabella[inizialeTradotta.getRiga()][inizialeTradotta
													.getColonna()];
										}
									}
								}
							} else if (comando.charAt(1) - 97 != posizioneTradotta.getColonna()) {
								inizialeTradotta = new Posizione(comando.charAt(3) - 49, comando.charAt(1) - 97);
								pezzoMosso = tabella[inizialeTradotta.getRiga()][inizialeTradotta.getColonna()];
							}
						}
					} else {
						posizioneTradotta = null;
						System.out.println("mossa illegale");
					}
				} else {
					posizioneTradotta = null;
					System.out.println("mossa illegale");
				} // altrimenit la posizione tradotta � nulla e se � nulla significa che la roba
					// non l'hai scirtta bene
			}
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
			} else {
				System.out.println("comando non riconosciuto, inserire /help per vedere elenco comand disponibili");
			}
			System.out.println();
			posizioneTradotta = null;
		}
	}

	protected class Mangiati{
		private int numeroMangiati = 0;
		private int pedoniMangiati = 0;
		private int torriMangiate = 0;
		private int alfieriMangiati = 0;
		private int regineMangiate = 0;
		private int cavalliMangiati = 0;
		private Pezzo[] mangiati;
		private char[] simboliBianchi = {'\u265F' , '\u265D' , '\u265C' , '\u265B' , '\u265E'};
		private char[] simboliNeri = {'\u2659' , '\u2657' , '\u2656' , '\u2655' , '\u2658'};
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
			else if (pezzo.getNome() == 'N'){
				cavalliMangiati++;
			}
			else if (pezzo.getNome() == 'R'){
				torriMangiate++;
			}
			else if (pezzo.getNome() == 'B'){
				alfieriMangiati++;
			}
			else if (pezzo.getNome() == 'Q'){
				regineMangiate++;
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
		public void displayMangiati()throws UnsupportedEncodingException{

			Font font = new Font("NSimSun" , Font.ITALIC , 14);
			System.setOut(new PrintStream(System.out, false, "UTF-8"));
			if (turno == bianco){
				System.out.println(simboliNeri[0] + " x " + pedoniMangiati);
				System.out.println(simboliNeri[1] + " x " + alfieriMangiati);
				System.out.println(simboliNeri[2] + " x " + torriMangiate);
				System.out.println(simboliNeri[3] + " x " + regineMangiate);
				System.out.println(simboliNeri[4] + " x " + cavalliMangiati);
			}
			else {
				System.out.println(simboliBianchi[0] + " x " + pedoniMangiati);
				System.out.println(simboliBianchi[1] + " x " + alfieriMangiati);
				System.out.println(simboliBianchi[2] + " x " + torriMangiate);
				System.out.println(simboliBianchi[3] + " x " + regineMangiate);
				System.out.println(simboliBianchi[4] + " x " + cavalliMangiati);
			}
		}
	}
}
