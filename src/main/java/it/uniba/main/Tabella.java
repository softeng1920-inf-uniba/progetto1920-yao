package it.uniba.main;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.Vector;

/**
 * classe di tipo boundary
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

  public Tabella(final int rig, final int colon) {
    righe = rig;
    colonne = colon;
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

  public void setTabella(final Pezzo pezzo, final Posizione toGo) {
    tabella[toGo.getRiga()][toGo.getColonna()] = pezzo;
  }

  public Pezzo getTabella(final Posizione toGet) {
    return tabella[toGet.getRiga()][toGet.getColonna()];
  }

  public void aggiungiComando(final String com) {
	  comandi.add(com);
  }

  public void move(final Posizione toGo, final Pezzo pezzo) {
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
      System.out.print("muove il bianco:\n");
    } else {
      System.out.print("muove il nero:\n");
    }
  }

  public void muovicondomanda() throws UnsupportedEncodingException {
    String moss1;
    Scanner input = new Scanner(System.in);
    moss1 = input.nextLine();
    if (moss1.length() == 0) {
      System.out.print("mossa illegale\n");
    }
    Comando comando = new Comando(moss1);
    if (comando.getSistema()) {
      comando.getIstruzioni();
    } else if (comando.scrittaBene()) {
      isEnpassant(comando);
      if (comando.isArrocco()) {
        boolean esito = false;
        if (turno == bianco) {
        	final int quattro = 4;
        	final int sette = 7;
        	final int sei = 6;
        	final int cinque = 5;
        	final int uno = 1;
        	final int due = 2;
          if ((comando.getComando().equals("0-0") || comando.getComando().equals("O-O"))
              && controlMovimento(tabella[0][quattro], comando)) {
            esito = true;
            move(new Posizione(0, sei), tabella[0][quattro]);
            tabella[0][sei].giaMosso();
            aggiungiComando(comando.getComando());
            move(new Posizione(0, cinque), tabella[0][sette]);
            System.out.print("Pezzo Mosso\n");
            tabella[0][cinque].giaMosso();
            cambiaTurno();
          } else {
            if (controlMovimento(tabella[0][quattro], comando)) {
              esito = true;
              move(new Posizione(0, uno), tabella[0][quattro]);
              tabella[0][uno].giaMosso();
              aggiungiComando(comando.getComando());
              move(new Posizione(0, due), tabella[0][0]);
              System.out.print("Pezzo Mosso\n");
              tabella[0][due].giaMosso();
              cambiaTurno();
            }
          }
        } else {
        	final int quattro = 4;
        	final int sette = 7;
        	final int sei = 6;
        	final int cinque = 5;
        	final int uno = 1;
        	final int due = 2;
          if ((comando.getComando().equals("0-0") || comando.getComando().equals("O-O"))
              && controlMovimento(tabella[sette][quattro], comando)) {
            esito = true;
            move(new Posizione(sette, sei), tabella[sette][quattro]);
            tabella[sette][sei].giaMosso();
            aggiungiComando(comando.getComando());
            move(new Posizione(sette, cinque), tabella[sette][sette]);
            System.out.print("Pezzo Mosso\n");
            tabella[sette][cinque].giaMosso();
            cambiaTurno();
          } else {
            if (controlMovimento(tabella[sette][quattro], comando)) {
              esito = true;
              move(new Posizione(sette, uno), tabella[sette][quattro]);
              tabella[sette][uno ].giaMosso();
              aggiungiComando(comando.getComando());
              move(new Posizione(sette, due), tabella[sette][0]);
              System.out.print("Pezzo Mosso\n");
              tabella[sette][due].giaMosso();
              cambiaTurno();
            }
          }
        }
        if (!esito) {
          System.out.println("Mossa illegale");
        }
      } else {
        move(comando.posizioneTradotta,
            tabella[comando.inizialeTradotta.getRiga()][comando.inizialeTradotta.getColonna()]);
        aggiungiComando(comando.getComando());
        System.out.print("Pezzo Mosso\n");
        cambiaTurno();
      }

    } else if (!comando.scrittaBene()) {

      System.out.println("mossa illegale");
    }
  }

  public void stampaComandi() {
    for (int i = 0; i < comandi.size(); i++) {
      System.out.print(comandi.get(i) + ";  ");
      if (Math.abs(i) % 2 == 1) {
        System.out.println();
      }
    }
  }

  private void mangiaPedina(final Posizione toGo, final Pezzo pezzo) {
    if (tabella[toGo.getRiga()][toGo.getColonna()].getColore() == bianco) {
      bianchiMangiati.set(tabella[toGo.getRiga()][toGo.getColonna()]);
    } else {
      neriMangiati.set(tabella[toGo.getRiga()][toGo.getColonna()]);
    }
    Posizione pos1 = pezzo.getPosizione();
    setTabella(null, pos1);
    pezzo.setPosizione(toGo);
    setTabella(pezzo, toGo);

  }

  private void mangiaPedinaEnpassant(final Posizione toGo, final Pezzo pezzo) {
    if (tabella[pezzo.getPosizione().getRiga()][toGo.getColonna()].getColore() == bianco) {
      bianchiMangiati.set(tabella[pezzo.getPosizione().getRiga()][toGo.getColonna()]);
    } else {
      neriMangiati.set(tabella[pezzo.getPosizione().getRiga()][toGo.getColonna()]);
    }
    Posizione pos1 = pezzo.getPosizione(); // posizione del pezzo iniziale
    Posizione pos2 = new Posizione(pezzo.getPosizione().getRiga(), toGo.getColonna());
    setTabella(null, pos1); // mette null la posizione precedente di pezzo
    setTabella(null, pos2); // mette null la posizione dove stava la pedina mangiata
    pezzo.setPosizione(toGo); // mette la poiszione di pezzo uguali al posto in cui ï¿½ ora
    setTabella(pezzo, toGo); // mette pezzo nel posto della tabella
    System.out.println("pedone mangiato con en passant");
  }

  private boolean controlMovimento(final Pezzo pezzo, final Comando comando) {
    Posizione toGo = comando.posizioneTradotta;
    if (comando.isArrocco()) {
      if (pezzo.getColore() == bianco) {
        if (comando.getComando().equals("0-0") || comando.getComando().equals("O-O")) {
        	final int sette = 7;
        	final int quattro = 4;
        	final int cinque = 5;
        	final int sei = 6;
          if (tabella[0][sette].getNome() == 'R' && tabella[0][sette].getEnpassant()
              && tabella[0][quattro].getNome() == 'K'
              && tabella[0][quattro].getEnpassant()
              && tabella[0][cinque] == null && tabella[0][sei] == null
              && controlloGittata(pezzo, toGo)) {
            return true;
          }
        } else {
          final int quattro = 4;
          final int uno = 1;
        	final int due = 2;
        	final int tre = 3;
					if (tabella[0][0].getNome() == 'R'
              && tabella[0][0].getEnpassant() && tabella[0][quattro].getNome() == 'K'
              && tabella[0][quattro].getEnpassant() && tabella[0][uno] == null
              && tabella[0][due] == null && tabella[0][tre] == null
              && controlloGittata(pezzo, toGo)) {
            return true;
          }
        }
      } else {
        if (comando.getComando().equals("0-0") || comando.getComando().equals("O-O")) {
          final int sette = 7;
        	final int quattro = 4;
        	final int cinque = 5;
        	final int sei = 6;
					if (tabella[sette][sette].getNome() == 'R'
              && tabella[sette][sette].getEnpassant()
              && tabella[sette][quattro].getNome() == 'K'
              && tabella[sette][quattro].getEnpassant() && tabella[sette][cinque] == null && tabella[sette][sei] == null
              && controlloGittata(pezzo, toGo)) {
            return true;
          }
        } else {
        	final int sette = 7;
        	final int quattro = 4;
        	final int uno = 1;
        	final int due = 2;
        	final int tre = 3;
          if (tabella[sette][0].getNome() == 'R' && tabella[sette][0].getEnpassant()
              && tabella[sette][quattro].getNome() == 'K'
              && tabella[sette][quattro].getEnpassant() && tabella[sette][uno] == null
              && tabella[sette][due] == null && tabella[sette][tre] == null
                  && controlloGittata(pezzo, toGo)) {
            return true;
          }
        }
      }
    } else if (pezzo.getNome() == 'p') {
      if (pezzo.getColore() == bianco) {
        isEnpassant(comando);
        if (pezzo.getPosizione().getRiga() != rigaPedoniBianchi) {
          pezzo.giaMosso();
        } // controllo gittata x , y sulla posizione di arrivo and ((controllo per vedera
        // se Ã¯Â¿Â½ nella stessa colonna and la posizuione Ã¯Â¿Â½ nulla ) e si vuole caturare
        // e
        // la gittata Ã¯Â¿Â½ quella giusta oppure (la differenza in valiore asoluto tera le
        // colonne Ã¯Â¿Â½ 1 and ci deve essere una pedina and il colore de essere uuale al
        // nero e la differenza di righe Ã¯Â¿Â½ 1 e si sta catturando cattura con en
        // passant
        if ((toGo.getRiga() - pezzo.getPosizione().getRiga() <= pezzo.getGittata())
            && (((toGo.getColonna() - pezzo.getPosizione().getColonna() == 0)
                && (tabella[toGo.getRiga()][toGo.getColonna()] == null)
                && (!comando.getCattura())
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
            && (toGo.getRiga() - pezzo.getPosizione().getRiga() > 0)
            && !contorlloPinnato(pezzo, comando)) {
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
                && (tabella[toGo.getRiga()][toGo.getColonna()] == null)
                && (!comando.getCattura())
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
            && (toGo.getRiga() - pezzo.getPosizione().getRiga() < 0)
            && !contorlloPinnato(pezzo, comando)) {
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
          && (toGo.getRiga() != pezzo.getPosizione().getRiga())
          && !contorlloPinnato(pezzo, comando));
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
          && controlloGittata(pezzo, toGo) && !contorlloPinnato(pezzo, comando));
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
          && controlloGittata(pezzo, toGo) && !contorlloPinnato(pezzo, comando));
    } else if (pezzo.getNome() == 'Q') {
      // vede se non sta catturando oppure se sta catturando e se il colore Ã¨
      // diverso e se la cattura Ã¯Â¿Â½ true contorlli di movimento su stessa riga
      // o su stessa colonna ma diversa riga o sulle diagonali quindi si vede
      // se la distanza tra colonna e righe sono uguali in termini di
      // differenza controllo sulla gittata per vedere se ci sono pezzi in
      // mezzo
      return ((Math.abs(toGo.getRiga() - pezzo.getPosizione().getRiga()) <= pezzo.getGittata())
          && (Math.abs(toGo.getColonna() - pezzo.getPosizione().getColonna()) <= pezzo.getGittata()
              && (((tabella[toGo.getRiga()][toGo.getColonna()] == null) && (!comando.getCattura()))
                  || ((tabella[toGo.getRiga()][toGo.getColonna()] != null)
              && (tabella[toGo.getRiga()][toGo.getColonna()].getColore() != pezzo.getColore())
                      && (comando.getCattura())))
              && (((toGo.getRiga() - pezzo.getPosizione().getRiga() == 0
                  && toGo.getColonna() - pezzo.getPosizione().getColonna() != 0)
                  || (toGo.getColonna() - pezzo.getPosizione().getColonna() == 0
                      && toGo.getRiga() - pezzo.getPosizione().getRiga() != 0))
                  || (Math.abs(toGo.getColonna() - pezzo.getPosizione().getColonna())
                      == Math.abs(toGo.getRiga() - pezzo.getPosizione().getRiga()))))
          && controlloGittata(pezzo, toGo) && (toGo.getRiga() != pezzo.getPosizione().getRiga()
              || toGo.getColonna() != pezzo.getPosizione().getColonna())
          && !contorlloPinnato(pezzo, comando));
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
              || (toGo.getColonna() - pezzo.getPosizione().getColonna() == 0
          && (Math.abs(toGo.getRiga() - pezzo.getPosizione().getRiga()) == pezzo.getGittata())))
          && ((tabella[toGo.getRiga()][toGo.getColonna()] == null) && controlloGittata(pezzo, toGo)
                  && (!comando.getCattura()))
              || ((tabella[toGo.getRiga()][toGo.getColonna()] != null)
                  && (tabella[toGo.getRiga()][toGo.getColonna()].getColore() != pezzo.getColore())
                  && controlloGittata(pezzo, toGo) && comando.getCattura())));
    }

    return false;
  } // controlla se per ogni tipo di pezzo Ã¯Â¿Â½ possibile lo spostamento
  //vede se un pezzo blocca lo scacco del re

  private boolean contorlloPinnato(final Pezzo pezzo, final Comando comando) {
    Posizione toGo = comando.posizioneTradotta;
    Posizione iniziale = new Posizione(pezzo.getPosizione().getRiga(),
        pezzo.getPosizione().getColonna());
    Pezzo pezzoAux = tabella[toGo.getRiga()][toGo.getColonna()];
    boolean esito = true;
    tabella[toGo.getRiga()][toGo.getColonna()] = pezzo;
    tabella[iniziale.getRiga()][iniziale.getColonna()] = null;
    pezzo.setPosizione(toGo);
    if (turno == bianco) {
      esito = controlloGittata(reBianco, reBianco.getPosizione());
    } else {
      esito = controlloGittata(reNero, reNero.getPosizione());
    }
    tabella[iniziale.getRiga()][iniziale.getColonna()] = pezzo;
    pezzo.setPosizione(iniziale);
    tabella[toGo.getRiga()][toGo.getColonna()] = pezzoAux;
    return !esito;
  }

  private boolean controlloGittata(final Pezzo pezzo, final Posizione toGo) {
    if (pezzo.getNome() == 'p') {
      if (pezzo.getColore() == bianco) {
        for (int i = 1; i <= pezzo.getGittata(); i++) {
        	final int sette = 7;
          if (pezzo.getPosizione().getRiga() + i > sette) {
            return false;
          } else if (tabella[pezzo.getPosizione().getRiga() + i]
              [pezzo.getPosizione().getColonna()] != null) {
            return false;
          }
        }
      } else {
        for (int i = 1; i <= pezzo.getGittata(); i++) {
          if (pezzo.getPosizione().getRiga() - i < 0) {
            return false;
          } else if (tabella[pezzo.getPosizione().getRiga() - i]
              [pezzo.getPosizione().getColonna()] != null) {
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
          if (tabella[pezzo.getPosizione().getRiga() + i]
              [pezzo.getPosizione().getColonna()] != null) {
            return false;
          }
        }
        /* giu */
      } else if (toGo.getRiga() < pezzo.getPosizione().getRiga()
          && (toGo.getColonna() == pezzo.getPosizione().getColonna())) {
        for (int i = 1; i < Math.abs(pezzo.getPosizione().getRiga() - toGo.getRiga()); i++) {
          if (tabella[pezzo.getPosizione().getRiga() - i]
              [pezzo.getPosizione().getColonna()] != null) {
            return false;
          }
        }
        /* destra */
      } else if (toGo.getRiga() == pezzo.getPosizione().getRiga()
          && (toGo.getColonna() > pezzo.getPosizione().getColonna())) {
        for (int i = 1; i < Math.abs(pezzo.getPosizione().getColonna() - toGo.getColonna()); i++) {
          if (tabella[pezzo.getPosizione().getRiga()]
              [pezzo.getPosizione().getColonna() + i] != null) {
            return false;
          }
        }
        /* sinistra */
      } else if (toGo.getRiga() == pezzo.getPosizione().getRiga()
          && (toGo.getColonna() < pezzo.getPosizione().getColonna())) {
        for (int i = 1; i < Math.abs(pezzo.getPosizione().getColonna() - toGo.getColonna()); i++) {
          if (tabella[pezzo.getPosizione().getRiga()]
              [pezzo.getPosizione().getColonna() - i] != null) {
            return false;
          }
        }
      }
    } else if (pezzo.getNome() == 'B') {
      // sopra sinistra
      if (toGo.getRiga() > pezzo.getPosizione().getRiga()
          && toGo.getColonna() < pezzo.getPosizione().getColonna()) {
        for (int i = 1; i < Math.abs(pezzo.getPosizione().getColonna() - toGo.getColonna()); i++) {
          if (tabella[pezzo.getPosizione().getRiga() + 1]
              [pezzo.getPosizione().getColonna() - 1] != null) {
            return false;
          }
        }
        // sopra destra
      } else if (toGo.getRiga() > pezzo.getPosizione().getRiga()
          && toGo.getColonna() > pezzo.getPosizione().getColonna()) {
        for (int i = 1; i < Math.abs(pezzo.getPosizione().getColonna() - toGo.getColonna()); i++) {
          if (tabella[pezzo.getPosizione().getRiga() + i]
              [pezzo.getPosizione().getColonna() + i] != null) {
            return false;
          }
        }
        // sotto sinistra
      } else if (toGo.getRiga() < pezzo.getPosizione().getRiga()
          && toGo.getColonna() < pezzo.getPosizione().getColonna()) {
        for (int i = 1; i < Math.abs(pezzo.getPosizione().getColonna() - toGo.getColonna()); i++) {
          if (tabella[pezzo.getPosizione().getRiga() - 1]
              [pezzo.getPosizione().getColonna() - 1] != null) {
            return false;
          }
        }
      } else if (toGo.getRiga() < pezzo.getPosizione().getRiga()
                            && toGo.getColonna() > pezzo.getPosizione().getColonna()) {
        for (int i = 1; i < Math.abs(pezzo.getPosizione().getColonna() - toGo.getColonna()); i++) {
          if (tabella[pezzo.getPosizione().getRiga() - 1]
              [pezzo.getPosizione().getColonna() + 1] != null) {
            return false;
          }
        }
      }
    } else if (pezzo.getNome() == 'Q') {
      /* sopra */
      if (toGo.getRiga() > pezzo.getPosizione().getRiga()
          && (toGo.getColonna() == pezzo.getPosizione().getColonna())) {
        for (int i = 1; i < Math.abs(pezzo.getPosizione().getRiga() - toGo.getRiga()); i++) {
          if (tabella[pezzo.getPosizione().getRiga() + i]
              [pezzo.getPosizione().getColonna()] != null) {
            return false;
          }
        }
        /* giu */
      } else if (toGo.getRiga() < pezzo.getPosizione().getRiga()
          && (toGo.getColonna() == pezzo.getPosizione().getColonna())) {
        for (int i = 1; i < Math.abs(pezzo.getPosizione().getRiga() - toGo.getRiga()); i++) {
          if (tabella[pezzo.getPosizione().getRiga() - i]
              [pezzo.getPosizione().getColonna()] != null) {
            return false;
          }
        }
        /* destra */
      } else if (toGo.getRiga() == pezzo.getPosizione().getRiga()
          && (toGo.getColonna() > pezzo.getPosizione().getColonna())) {
        for (int i = 1; i < Math.abs(pezzo.getPosizione().getColonna() - toGo.getColonna()); i++) {
          if (tabella[pezzo.getPosizione().getRiga()]
              [pezzo.getPosizione().getColonna() + i] != null) {
            return false;
          }
        }
        /* sinistra */
      } else if (toGo.getRiga() == pezzo.getPosizione().getRiga()
          && (toGo.getColonna() < pezzo.getPosizione().getColonna())) {
        for (int i = 1; i < Math.abs(pezzo.getPosizione().getColonna() - toGo.getColonna()); i++) {
          if (tabella[pezzo.getPosizione().getRiga()]
              [pezzo.getPosizione().getColonna() - i] != null) {
            return false;
          }
        }
        // sopra sinistra
      } else if (toGo.getRiga() > pezzo.getPosizione().getRiga()
          && toGo.getColonna() < pezzo.getPosizione().getColonna()) {
        for (int i = 1; i < pezzo.getPosizione().getColonna() - toGo.getColonna(); i++) {
          if (tabella[pezzo.getPosizione().getRiga() + 1]
              [pezzo.getPosizione().getColonna() - 1] != null) {
            return false;
          }
        }
        // sopra destra
      } else if (toGo.getRiga() > pezzo.getPosizione().getRiga()
          && toGo.getColonna() > pezzo.getPosizione().getColonna()) {
        for (int i = 1; i < pezzo.getPosizione().getColonna() - toGo.getColonna(); i++) {
          if (tabella[pezzo.getPosizione().getRiga() + i]
              [pezzo.getPosizione().getColonna() + i] != null) {
            return false;
          }
        }
        // sotto sinistra
      } else if (toGo.getRiga() < pezzo.getPosizione().getRiga()
          && toGo.getColonna() < pezzo.getPosizione().getColonna()) {
        for (int i = 1; i < pezzo.getPosizione().getColonna() - toGo.getColonna(); i++) {
          if (tabella[pezzo.getPosizione().getRiga() - 1]
              [pezzo.getPosizione().getColonna() - 1] != null) {
            return false;
          }
        }
      } else if (toGo.getRiga() < pezzo.getPosizione().getRiga()
                            && toGo.getColonna() > pezzo.getPosizione().getColonna()) {
        for (int i = 1; i < pezzo.getPosizione().getColonna() - toGo.getColonna(); i++) {
          if (tabella[pezzo.getPosizione().getRiga() - 1]
              [pezzo.getPosizione().getColonna() + 1] != null) {
            return false;
          }
        }
      }
    } else if (pezzo.getNome() == 'K') {
      // controlloscacco
      // bordi
    	final int sette = 7;
      int bsx = toGo.getColonna();
      int balt = sette - toGo.getRiga();
      int bdx = sette - toGo.getColonna();
      int bbas = toGo.getRiga();
      /* pedone */ if (pezzo.getColore() == bianco) {
        if (balt > 0) {
          if (bdx > 0) {
            if (tabella[toGo.getRiga() + 1][toGo.getColonna() + 1] != null
                && tabella[toGo.getRiga() + 1][toGo.getColonna() + 1].getNome() == 'p'
                    && tabella[toGo.getRiga() + 1][toGo.getColonna() + 1].getColore() == nero) {
              return false;
            }
          }
          if (bsx > 0) {
            if (tabella[toGo.getRiga() + 1][toGo.getColonna() - 1] != null
                && tabella[toGo.getRiga() + 1][toGo.getColonna() - 1].getNome() == 'p'
                    && tabella[toGo.getRiga() + 1][toGo.getColonna() - 1].getColore() == nero) {
              return false;
            }
          }
        }
      } else {
        /* nero */ if (bbas > 0) {
          if (bdx > 0) {
            if (tabella[toGo.getRiga() - 1][toGo.getColonna() + 1] != null
                && tabella[toGo.getRiga() - 1][toGo.getColonna() + 1].getNome() == 'p'
                    && tabella[toGo.getRiga() - 1][toGo.getColonna() + 1].getColore() == bianco) {
              return false;
            }
          }
          if (bsx > 0) {
            if (tabella[toGo.getRiga() - 1][toGo.getColonna() - 1] != null
                && tabella[toGo.getRiga() - 1][toGo.getColonna() - 1].getNome() == 'p'
                    && tabella[toGo.getRiga() - 1][toGo.getColonna() - 1].getColore() == bianco) {
              return false;
            }
          }
        }
      }

      /* alfiere, donna */
      int i = 1;

      //altsx
      while (i < bsx && i < balt) {
        if (tabella[toGo.getRiga() + i][toGo.getColonna() - i] != null) {
          if (tabella[toGo.getRiga() + i][toGo.getColonna() - i].getColore() != pezzo.getColore()
              && (tabella[toGo.getRiga() + i][toGo.getColonna() - i].getNome() == 'B'
                  || tabella[toGo.getRiga() + i][toGo.getColonna() - i].getNome() == 'Q')) {
            return false;
          } else if (tabella[toGo.getRiga() + i][toGo.getColonna() - i].getColore()
              == pezzo.getColore()
              || (tabella[toGo.getRiga() + i][toGo.getColonna() - i].getColore()
                  != pezzo.getColore()
                  && (tabella[toGo.getRiga() + i][toGo.getColonna() - i].getNome() != 'B'
                      || tabella[toGo.getRiga() + i][toGo.getColonna() - i].getNome() != 'Q'))) {
            break;
          }
        }
        i++;
      }
      //altdx
      i = 1;
      while (i <= bdx && i <= balt) {
        if (tabella[toGo.getRiga() + i][toGo.getColonna() + i] != null) {
          if (tabella[toGo.getRiga() + i][toGo.getColonna() + i].getColore() != pezzo.getColore()
              && (tabella[toGo.getRiga() + i][toGo.getColonna() + i].getNome() == 'B'
                  || tabella[toGo.getRiga() + i][toGo.getColonna() + i].getNome() == 'Q')) {
            return false;
          } else if (tabella[toGo.getRiga() + i][toGo.getColonna() + i].getColore()
              == pezzo.getColore()
              || (tabella[toGo.getRiga() + i][toGo.getColonna() + i].getColore()
                  != pezzo.getColore()
                  && (tabella[toGo.getRiga() + i][toGo.getColonna() + i].getNome() != 'B'
                      || tabella[toGo.getRiga() + i][toGo.getColonna() + i].getNome() != 'Q'))) {
            break;
          }
        }
        i++;
      }
      //bassx
      i = 1;
      while (i <= bsx && i <= bbas) {
        if (tabella[toGo.getRiga() - i][toGo.getColonna() - i] != null) {
          if (tabella[toGo.getRiga() - i][toGo.getColonna() - i].getColore() != pezzo.getColore()
              && (tabella[toGo.getRiga() - i][toGo.getColonna() - i].getNome() == 'B'
                  || tabella[toGo.getRiga() - i][toGo.getColonna() - i].getNome() == 'Q')) {
            return false;
          } else if (tabella[toGo.getRiga() - i][toGo.getColonna() - i].getColore()
              == pezzo.getColore()
              || (tabella[toGo.getRiga() - i][toGo.getColonna() - i].getColore()
                  != pezzo.getColore()
                  && (tabella[toGo.getRiga() - i][toGo.getColonna() - i].getNome() != 'B'
                      || tabella[toGo.getRiga() - i][toGo.getColonna() - i].getNome() != 'Q'))) {
            break;
          }
        }
        i++;
      }
      //bassdx
      i = 1;
      while (i <= bdx && i <= bbas) {
        if (tabella[toGo.getRiga() - i][toGo.getColonna() + i] != null) {
          if (tabella[toGo.getRiga() - i][toGo.getColonna() + i].getColore() != pezzo.getColore()
              && (tabella[toGo.getRiga() - i][toGo.getColonna() + i].getNome() == 'B'
                  || tabella[toGo.getRiga() - i][toGo.getColonna() + i].getNome() == 'Q')) {
            return false;
          } else if (tabella[toGo.getRiga() - i][toGo.getColonna() + i].getColore()
              == pezzo.getColore()
              || (tabella[toGo.getRiga() - i][toGo.getColonna() + i].getColore()
                  != pezzo.getColore()
                  && (tabella[toGo.getRiga() - i][toGo.getColonna() + i].getNome() != 'B'
                      || tabella[toGo.getRiga() - i][toGo.getColonna() + i].getNome() != 'Q'))) {
            break;
          }
        }
        i++;
      }

      /* torre, donna */
      i = 1;
      while (i <= balt) {
        if (tabella[toGo.getRiga() + i][toGo.getColonna()] != null) {
          if (tabella[toGo.getRiga() + i][toGo.getColonna()].getColore() != pezzo.getColore()
              && (tabella[toGo.getRiga() + i][toGo.getColonna()].getNome() == 'R'
                  || tabella[toGo.getRiga() + i][toGo.getColonna()].getNome() == 'Q')) {
            return false;
          } else if (tabella[toGo.getRiga() + i][toGo.getColonna()].getColore() == pezzo.getColore()
              || (tabella[toGo.getRiga() + i][toGo.getColonna()].getColore() != pezzo.getColore()
                  && (tabella[toGo.getRiga() + i][toGo.getColonna()].getNome() != 'R'
                      || tabella[toGo.getRiga() + i][toGo.getColonna()].getNome() != 'Q'))) {
            break;
          }
        }
        i++;
      }
      //giu
      i = 1;
      while (i <= bbas) {
        if (tabella[toGo.getRiga() - i][toGo.getColonna()] != null) {
          if (tabella[toGo.getRiga() - i][toGo.getColonna()].getColore() != pezzo.getColore()
              && (tabella[toGo.getRiga() - i][toGo.getColonna()].getNome() == 'R'
                  || tabella[toGo.getRiga() - i][toGo.getColonna()].getNome() == 'Q')) {
            return false;
          } else if (tabella[toGo.getRiga() - i][toGo.getColonna()].getColore() == pezzo.getColore()
              || (tabella[toGo.getRiga() - i][toGo.getColonna()].getColore() != pezzo.getColore()
                  && (tabella[toGo.getRiga() - i][toGo.getColonna()].getNome() != 'R'
                      || tabella[toGo.getRiga() - i][toGo.getColonna()].getNome() != 'Q'))) {
            break;
          }
        }
        i++;
      }
      //sx
      i = 1;
      while (i <= bsx) {
        if (tabella[toGo.getRiga()][toGo.getColonna() - i] != null) {
          if (tabella[toGo.getRiga()][toGo.getColonna() - i].getColore() != pezzo.getColore()
              && (tabella[toGo.getRiga()][toGo.getColonna() - i].getNome() == 'R'
                  || tabella[toGo.getRiga()][toGo.getColonna() - i].getNome() == 'Q')) {
            return false;
          } else if (tabella[toGo.getRiga()][toGo.getColonna() - i].getColore() == pezzo.getColore()
              || (tabella[toGo.getRiga()][toGo.getColonna() - i].getColore() != pezzo.getColore()
                  && (tabella[toGo.getRiga()][toGo.getColonna() - i].getNome() != 'R'
                      || tabella[toGo.getRiga()][toGo.getColonna() - i].getNome() != 'Q'))) {
            break;
          }
        }
        i++;
      }
      //dx
      i = 1;
      while (i <= bdx) {
        if (tabella[toGo.getRiga()][toGo.getColonna() + i] != null) {
          if (tabella[toGo.getRiga()][toGo.getColonna() + i].getColore() != pezzo.getColore()
              && (tabella[toGo.getRiga()][toGo.getColonna() + i].getNome() == 'R'
                  || tabella[toGo.getRiga()][toGo.getColonna() + i].getNome() == 'Q')) {
            return false;
          } else if (tabella[toGo.getRiga()][toGo.getColonna() + i].getColore() == pezzo.getColore()
              || (tabella[toGo.getRiga()][toGo.getColonna() + i].getColore() != pezzo.getColore()
                  && (tabella[toGo.getRiga()][toGo.getColonna() + i].getNome() != 'R'
                      || tabella[toGo.getRiga()][toGo.getColonna() + i].getNome() != 'Q'))) {
            break;
          }
        }
        i++;
      }

      /* cavallo */ // sx
      if (bsx >= 2) {
        // alto
        if (balt > 0) {
          if (tabella[toGo.getRiga() + 1][toGo.getColonna() - 2] != null
              && tabella[toGo.getRiga() + 1][toGo.getColonna() - 2].getNome() == 'N'
                  && tabella[toGo.getRiga() + 1][toGo.getColonna() - 2].getColore()
                  != pezzo.getColore()) {
            return false;
          }
        }
        if (balt >= 2) {
          if (tabella[toGo.getRiga() + 2][toGo.getColonna() - 1] != null
              && tabella[toGo.getRiga() + 2][toGo.getColonna() - 1].getNome() == 'N'
                  && tabella[toGo.getRiga() + 2][toGo.getColonna() - 1].getColore()
                  != pezzo.getColore()) {
            return false;
          }
        }
        // basso
        if (bbas > 0) {
          if (tabella[toGo.getRiga() - 1][toGo.getColonna() - 2] != null
              && tabella[toGo.getRiga() - 1][toGo.getColonna() - 2].getNome() == 'N'
                  && tabella[toGo.getRiga() - 1][toGo.getColonna() - 2].getColore()
                  != pezzo.getColore()) {
            return false;
          }
        }
        if (bbas >= 2) {
          if (tabella[toGo.getRiga() - 2][toGo.getColonna() - 1] != null
              && tabella[toGo.getRiga() - 2][toGo.getColonna() - 1].getNome() == 'N'
                  && tabella[toGo.getRiga() - 2][toGo.getColonna() - 1].getColore()
                  != pezzo.getColore()) {
            return false;
          }
        }
      }
      if (bsx == 1) {
        // alto
        if (balt >= 2) {
          if (tabella[toGo.getRiga() + 2][toGo.getColonna() - 1] != null
              && tabella[toGo.getRiga() + 2][toGo.getColonna() - 1].getNome() == 'N'
                  && tabella[toGo.getRiga() + 2][toGo.getColonna() - 1].getColore()
                  != pezzo.getColore()) {
            return false;
          }
        }
        // basso
        if (bbas >= 2) {
          if (tabella[toGo.getRiga() - 2][toGo.getColonna() - 1] != null
              && tabella[toGo.getRiga() - 2][toGo.getColonna() - 1].getNome() == 'N'
                  && tabella[toGo.getRiga() - 2][toGo.getColonna() - 1].getColore()
                  != pezzo.getColore()) {
            return false;
          }
        }
      }
      // dx
      if (bdx >= 2) {
        // alto
        if (balt > 0) {
          if (tabella[toGo.getRiga() + 1][toGo.getColonna() + 2] != null
              && tabella[toGo.getRiga() + 1][toGo.getColonna() + 2].getNome() == 'N'
                  && tabella[toGo.getRiga() + 1][toGo.getColonna() + 2].getColore()
                  != pezzo.getColore()) {
            return false;
          }
        }
        if (balt >= 2) {
          if (tabella[toGo.getRiga() + 2][toGo.getColonna() + 1] != null
              && tabella[toGo.getRiga() + 2][toGo.getColonna() + 1].getNome() == 'N'
                  && tabella[toGo.getRiga() + 2][toGo.getColonna() + 1].getColore()
                  != pezzo.getColore()) {
            return false;
          }
        }
        // basso
        if (bbas > 0) {
          if (tabella[toGo.getRiga() - 1][toGo.getColonna() + 2] != null
              && tabella[toGo.getRiga() - 1][toGo.getColonna() + 2].getNome() == 'N'
                  && tabella[toGo.getRiga() - 1][toGo.getColonna() + 2].getColore()
                  != pezzo.getColore()) {
            return false;
          }
        }
        if (bbas >= 2) {
          if (tabella[toGo.getRiga() - 2][toGo.getColonna() + 1] != null
              && tabella[toGo.getRiga() - 2][toGo.getColonna() + 1].getNome() == 'N'
              && tabella[toGo.getRiga() - 2][toGo.getColonna() + 1].getColore()
              != pezzo.getColore()) {
            return false;
          }
        }
      }
      if (bdx == 1) {
        // alto
        if (balt >= 2) {
          if (tabella[toGo.getRiga() + 2][toGo.getColonna() + 1] != null
              && tabella[toGo.getRiga() + 2][toGo.getColonna() + 1].getNome() == 'N'
              && tabella[toGo.getRiga() + 2][toGo.getColonna() + 1].getColore()
              != pezzo.getColore()) {
            return false;
          }
        }
        // basso
        if (bbas >= 2) {
          if (tabella[toGo.getRiga() - 2][toGo.getColonna() + 1] != null
              && tabella[toGo.getRiga() - 2][toGo.getColonna() + 1].getNome() == 'N'
              && tabella[toGo.getRiga() - 2][toGo.getColonna() + 1].getColore()
              != pezzo.getColore()) {
            return false;
          }
        }
      }

      /* re */ /* alto */ if (toGo.getRiga() - pezzo.getPosizione().getRiga() == 1) {

        if (balt >= 1 && bdx >= 1 && bsx >= 1) {
          if ((tabella[toGo.getRiga() + 1][toGo.getColonna()] != null
              && tabella[toGo.getRiga() + 1][toGo.getColonna()].getNome() == 'K'
              && tabella[toGo.getRiga() + 1][toGo.getColonna()].getColore() != pezzo.getColore())
              || (tabella[toGo.getRiga() + 1][toGo.getColonna() + 1] != null
                  && tabella[toGo.getRiga() + 1][toGo.getColonna() + 1].getNome() == 'K'
              && tabella[toGo.getRiga() + 1][toGo.getColonna() + 1].getColore()
              != pezzo.getColore())
              || (tabella[toGo.getRiga() + 1][toGo.getColonna() - 1] != null
                  && tabella[toGo.getRiga() + 1][toGo.getColonna() - 1].getNome() == 'K'
              && tabella[toGo.getRiga() + 1][toGo.getColonna() - 1].getColore()
              != pezzo.getColore())) {
            return false;
          }
        }
        if (balt >= 1 && bsx >= 1 && bdx == 0) {
          if ((tabella[toGo.getRiga() + 1][toGo.getColonna()] != null
              && tabella[toGo.getRiga() + 1][toGo.getColonna()].getNome() == 'K'
              && tabella[toGo.getRiga() + 1][toGo.getColonna()].getColore() != pezzo.getColore())
              || (tabella[toGo.getRiga() + 1][toGo.getColonna() - 1] != null
                  && tabella[toGo.getRiga() + 1][toGo.getColonna() - 1].getNome() == 'K'
              && tabella[toGo.getRiga() + 1][toGo.getColonna() - 1].getColore()
              != pezzo.getColore())) {
            return false;
          }
        }
        if (balt >= 1 && bdx >= 1 && bsx == 0) {
          if ((tabella[toGo.getRiga() + 1][toGo.getColonna()] != null
              && tabella[toGo.getRiga() + 1][toGo.getColonna()].getNome() == 'K'
              && tabella[toGo.getRiga() + 1][toGo.getColonna()].getColore() != pezzo.getColore())
              || (tabella[toGo.getRiga() + 1][toGo.getColonna() + 1] != null
                  && tabella[toGo.getRiga() + 1][toGo.getColonna() + 1].getNome() == 'K'
              && tabella[toGo.getRiga() + 1][toGo.getColonna() + 1].getColore()
              != pezzo.getColore())) {
            return false;
          }
        }
      }
      /* basso */ if (toGo.getRiga() - pezzo.getPosizione().getRiga() == -1) {

        if (bbas >= 1 && bdx >= 1 && bsx >= 1) {
          if ((tabella[toGo.getRiga() - 1][toGo.getColonna()] != null
              && tabella[toGo.getRiga() - 1][toGo.getColonna()].getNome() == 'K'
              && tabella[toGo.getRiga() - 1][toGo.getColonna()].getColore() != pezzo.getColore())
              || (tabella[toGo.getRiga() - 1][toGo.getColonna() - 1] != null
                  && tabella[toGo.getRiga() - 1][toGo.getColonna() - 1].getNome() == 'K'
              && tabella[toGo.getRiga() - 1][toGo.getColonna() - 1].getColore()
              != pezzo.getColore())
              || (tabella[toGo.getRiga() - 1][toGo.getColonna() + 1] != null
                  && tabella[toGo.getRiga() - 1][toGo.getColonna() + 1].getNome() == 'K'
              && tabella[toGo.getRiga() - 1][toGo.getColonna() + 1].getColore()
              != pezzo.getColore())) {
            return false;
          }
        }
        if (bbas >= 1 && bsx >= 1 && bdx == 0) {
          if ((tabella[toGo.getRiga() - 1][toGo.getColonna()] != null
              && tabella[toGo.getRiga() - 1][toGo.getColonna()].getNome() == 'K'
              && tabella[toGo.getRiga() - 1][toGo.getColonna()].getColore() != pezzo.getColore())
              || (tabella[toGo.getRiga() - 1][toGo.getColonna() - 1] != null
                  && tabella[toGo.getRiga() - 1][toGo.getColonna() - 1].getNome() == 'K'
              && tabella[toGo.getRiga() - 1][toGo.getColonna() - 1].getColore()
              != pezzo.getColore())) {
            return false;
          }
        }
        if (bbas >= 1 && bdx >= 1 && bsx == 0) {
          if ((tabella[toGo.getRiga() - 1][toGo.getColonna()] != null
              && tabella[toGo.getRiga() - 1][toGo.getColonna()].getNome() == 'K'
              && tabella[toGo.getRiga() - 1][toGo.getColonna()].getColore() != pezzo.getColore())
              || (tabella[toGo.getRiga() - 1][toGo.getColonna() + 1] != null
                  && tabella[toGo.getRiga() - 1][toGo.getColonna() + 1].getNome() == 'K'
              && tabella[toGo.getRiga() - 1][toGo.getColonna() + 1].getColore()
              != pezzo.getColore())) {
            return false;
          }
        }
      }
      /* dx */ if (toGo.getColonna() - pezzo.getPosizione().getColonna() == 1) {
        if (bbas >= 1 && bdx >= 1 && balt >= 1) {
          if ((tabella[toGo.getRiga()][toGo.getColonna() + 1] != null
              && tabella[toGo.getRiga()][toGo.getColonna() + 1].getNome() == 'K'
              && tabella[toGo.getRiga()][toGo.getColonna() + 1].getColore() != pezzo.getColore())
              || (tabella[toGo.getRiga() + 1][toGo.getColonna() + 1] != null
                  && tabella[toGo.getRiga() + 1][toGo.getColonna() + 1].getNome() == 'K'
              && tabella[toGo.getRiga() + 1][toGo.getColonna() + 1].getColore()
              != pezzo.getColore())
              || (tabella[toGo.getRiga() - 1][toGo.getColonna() + 1] != null
                  && tabella[toGo.getRiga() - 1][toGo.getColonna() + 1].getNome() == 'K'
              && tabella[toGo.getRiga() - 1][toGo.getColonna() + 1].getColore()
              != pezzo.getColore())) {
            return false;
          }
        }
        if (bbas >= 1 && bdx >= 1 && balt == 0) {
          if ((tabella[toGo.getRiga()][toGo.getColonna() + 1] != null
              && tabella[toGo.getRiga()][toGo.getColonna() + 1].getNome() == 'K'
              && tabella[toGo.getRiga()][toGo.getColonna() + 1].getColore() != pezzo.getColore())
              || (tabella[toGo.getRiga() - 1][toGo.getColonna() + 1] != null
                  && tabella[toGo.getRiga() - 1][toGo.getColonna() + 1].getNome() == 'K'
              && tabella[toGo.getRiga() - 1][toGo.getColonna() + 1].getColore()
              != pezzo.getColore())) {
            return false;
          }
        }
        if (balt >= 1 && bdx >= 1 && bbas == 0) {
          if ((tabella[toGo.getRiga()][toGo.getColonna() + 1] != null
              && tabella[toGo.getRiga()][toGo.getColonna() + 1].getNome() == 'K'
              && tabella[toGo.getRiga()][toGo.getColonna() + 1].getColore() != pezzo.getColore())
              || (tabella[toGo.getRiga() + 1][toGo.getColonna() + 1] != null
                  && tabella[toGo.getRiga() + 1][toGo.getColonna() + 1].getNome() == 'K'
              && tabella[toGo.getRiga() + 1][toGo.getColonna() + 1].getColore()
              != pezzo.getColore())) {
            return false;
          }
        }
      }
      /* sx */ if (toGo.getColonna() - pezzo.getPosizione().getColonna() == -1) {
        if (bbas >= 1 && bsx >= 1 && balt >= 1) {
          if ((tabella[toGo.getRiga()][toGo.getColonna() - 1] != null
              && tabella[toGo.getRiga()][toGo.getColonna() - 1].getNome() == 'K'
              && tabella[toGo.getRiga()][toGo.getColonna() - 1].getColore() != pezzo.getColore())
              || (tabella[toGo.getRiga() + 1][toGo.getColonna() - 1] != null
                  && tabella[toGo.getRiga() + 1][toGo.getColonna() - 1].getNome() == 'K'
              && tabella[toGo.getRiga() + 1][toGo.getColonna() - 1].getColore()
              != pezzo.getColore())
              || (tabella[toGo.getRiga() - 1][toGo.getColonna() - 1] != null
                  && tabella[toGo.getRiga() - 1][toGo.getColonna() - 1].getNome() == 'K'
              && tabella[toGo.getRiga() - 1][toGo.getColonna() - 1].getColore()
              != pezzo.getColore())) {
            return false;
          }
        }
        if (balt >= 1 && bsx >= 1 && bbas == 0) {
          if ((tabella[toGo.getRiga()][toGo.getColonna() - 1] != null
              && tabella[toGo.getRiga()][toGo.getColonna() - 1].getNome() == 'K'
              && tabella[toGo.getRiga()][toGo.getColonna() - 1].getColore() != pezzo.getColore())
              || (tabella[toGo.getRiga() + 1][toGo.getColonna() - 1] != null
                  && tabella[toGo.getRiga() + 1][toGo.getColonna() - 1].getNome() == 'K'
              && tabella[toGo.getRiga() + 1][toGo.getColonna() - 1].getColore()
              != pezzo.getColore())) {
            return false;
          }
        }
        if (bbas >= 1 && bsx >= 1 && balt == 0) {
          if ((tabella[toGo.getRiga()][toGo.getColonna() - 1] != null
              && tabella[toGo.getRiga()][toGo.getColonna() - 1].getNome() == 'K'
              && tabella[toGo.getRiga()][toGo.getColonna() - 1].getColore() != pezzo.getColore())
              || (tabella[toGo.getRiga() - 1][toGo.getColonna() - 1] != null
                  && tabella[toGo.getRiga() - 1][toGo.getColonna() - 1].getNome() == 'K'
              && tabella[toGo.getRiga() - 1][toGo.getColonna() - 1].getColore()
              != pezzo.getColore())) {
            return false;
          }
        }
      }
    }
    return true;
  }

  private void isEnpassant(final Comando comando) {
    if (comando.pezzoMosso != null && comando.pezzoMosso.getNome() == 'p') {
      if ((comando.pezzoMosso.getColore() == bianco)
          && (comando.inizialeTradotta.getRiga() == rigaPedoniBianchi)
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
    char[] nomiColonne = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    System.out.print(" ");
    for (int i = 0; i < colonne; i++) {
      System.out.print(" " + nomiColonne[i]);
    }
    System.out.println();
    for (int i = righe - 1; i >= 0; i--) {
      System.out.print((i + 1) + " ");
      for (int j = 0; j < colonne; j++) {
        if (tabella[i][j] != null) {
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
    System.out.println();
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
    final int otto = 8;
    final int sedici = 16;
		tabella = new Pezzo[otto][otto];
    neriMangiati = new Mangiati(sedici);
    bianchiMangiati = new Mangiati(sedici);
    pedoniBianchi = new Pezzo[otto];
    pedoniNeri = new Pezzo[otto];
    cavalliBianchi = new Cavallo[2];
    cavalliNeri = new Cavallo[2];
    alfieriBianchi = new Alfiere[2];
    alfieriNeri = new Alfiere[2];
    torreBianche = new Torre[2];
    torreNere = new Torre[2];
    comandi = new Vector<String>();
    for (int i = 0; i < otto; i++) {
      Posizione pos = new Posizione(rigaPedoniBianchi, i);
      tabella[rigaPedoniBianchi][i] = new Pedone(0, pos);
      pedoniBianchi[i] = tabella[rigaPedoniBianchi][i];
      final int sei = 6;
			final int sette = 7;
			final int cinque = 5;
			final int quattro = 4;
			final int tre = 3;
			if (i == 1) {
        Posizione posCB = new Posizione(0, 1);
        tabella[0][1] = new Cavallo(0, posCB);
        cavalliBianchi[0] = (Cavallo) tabella[0][i];
      } else if (i == sei) {
        Posizione posCB = new Posizione(0, i);
        tabella[0][i] = new Cavallo(0, posCB);
        cavalliBianchi[1] = (Cavallo) tabella[0][i];
      } else if (i == 0) {
        Posizione posTB = new Posizione(0, i);
        tabella[0][i] = new Torre(0, posTB);
        torreBianche[0] = (Torre) tabella[0][i];
      } else if (i == sette) {
        Posizione posTB = new Posizione(0, i);
        tabella[0][i] = new Torre(0, posTB);
        torreBianche[1] = (Torre) tabella[0][i];
      } else if (i == 2) {
        Posizione posAB = new Posizione(0, i);
        tabella[0][i] = new Alfiere(0, posAB);
        alfieriBianchi[0] = (Alfiere) tabella[0][i];
      } else if (i == cinque) {
        Posizione posAB = new Posizione(0, i);
        tabella[0][i] = new Alfiere(0, posAB);
        alfieriBianchi[1] = (Alfiere) tabella[0][i];
      } else if (i == tre) {
        Posizione posDB = new Posizione(0, i);
        tabella[0][i] = new Donna(0, posDB);
        donnaBianca = (Donna) tabella[0][i];
      } else if (i == quattro) {
        Posizione posRB = new Posizione(0, i);
        tabella[0][i] = new Re(0, posRB);
        reBianco = (Re) tabella[0][i];
      }
    }
    for (int j = 0; j < otto; j++) {
      Posizione pos = new Posizione(rigaPedoniNeri, j);
      tabella[rigaPedoniNeri][j] = new Pedone(1, pos);
      pedoniNeri[j] = tabella[rigaPedoniNeri][j];
      final int sei = 6;
			if (j == 1) {
        final int sette = 7;
				Posizione posCN = new Posizione(sette, j);
        tabella[sette][j] = new Cavallo(1, posCN);
        cavalliNeri[0] = (Cavallo) tabella[sette][j];
      } else if (j == sei) {
        final int sette = 7;
				Posizione posCN = new Posizione(sette, j);
        tabella[sette][j] = new Cavallo(1, posCN);
        cavalliNeri[1] = (Cavallo) tabella[sette][j];
      }
      final int sette = 7;
      final int quattro = 4;
      final int tre = 3;
      final int cinque = 5;

			if (j == 0) {
				Posizione posTN = new Posizione(sette, j);
        tabella[sette][j] = new Torre(1, posTN);
        torreNere[0] = (Torre) tabella[sette][j];
      } else if (j == sette) {
        Posizione posTN = new Posizione(sette, j);
        tabella[sette][j] = new Torre(1, posTN);
        torreNere[1] = (Torre) tabella[sette][j];
      } else if (j == 2) {
        Posizione posAN = new Posizione(sette, j);
        tabella[sette][j] = new Alfiere(1, posAN);
        alfieriNeri[0] = (Alfiere) tabella[sette][j];
      } else if (j == cinque) {
        Posizione posAN = new Posizione(sette, j);
        tabella[sette][j] = new Alfiere(1, posAN);
        alfieriNeri[1] = (Alfiere) tabella[sette][j];
      } else if (j == tre) {
        Posizione posDN = new Posizione(sette, j);
        tabella[sette][j] = new Donna(1, posDN);
        donnaNera = (Donna) tabella[sette][j];
      } else if (j == quattro) {
        Posizione posRN = new Posizione(sette, j);
        tabella[sette][j] = new Re(1, posRN);
        reNero = (Re) tabella[sette][j];
      }
    }
  }

  public void esciDalGioco() {
    String conferma;
    System.out.print("confermi di voler uscire?\n");
    boolean esito = true;
    while (esito) {
      Scanner input = new Scanner(System.in);
      conferma = input.nextLine();
      if (conferma.equals("si") || conferma.equals("Si") || conferma.equals("SI")) {
        System.exit(0);
      } else if (conferma.equals("no") || conferma.equals("No") || conferma.equals("NO")) {
        esito = false;
      } else {
        System.out.print("comando scritto male\n");
        System.out.print("confermi di voler uscire?\n");
      }
    }
  }

  /**
   * classe di tipo control responsabilita: codifica i comando inseriti
   * dall'utente (traduce la posizione inserita e verifica la computabilita)
   */
  public class Comando {
    private Posizione posizioneTradotta; // essendo il comando un vettore di char
    //trasformiamo i char in int , la a minuscola � il 97 esimo carattere
    //e ha valore 97 invece l'1 ha valore 49
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
    private String[] comandidArrocco = {"0-0-0", "O-O-O", "0-0", "O-O"};
    private String[] comandiSistema = {"help", "board", "captures", "moves", "quit", "play"};

    public Comando(String commands) {
      this.comando = commands;
      if (comando.length() == 0) {
        System.out.println("mossa illegale");
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
      final int uno = 1;
      final int due = 2;
      final int tre = 3;
      int esito = 0; // 0 se non ambiguo 1 se ambiguo sulle riche quindi sulla stessa colonna, 2 se
      // ambiguo sulla colonne quindi sulla stessa riga
      int cont = 0;
      if (comando.charAt(0) == cavallo) {
        if (turno == bianco) {
          if (cavalliBianchi.length < due) {
            return esito;
          } else {
            for (int i = 0; i < cavalliBianchi.length; i++) {
              if (controlMovimento(cavalliBianchi[i], this)) {
                cont++;
              }
            }
            if (cont == due) {
              if (cavalliBianchi[0].getPosizione().getColonna() == cavalliBianchi[uno].getPosizione()
                  .getColonna()) {
                esito = uno;
              } else {
                esito = due;
              }
            }
          }
        } else {
          if (cavalliNeri.length < due) {
            return esito;
          } else {
            for (int i = 0; i < cavalliNeri.length; i++) {
              if (controlMovimento(cavalliNeri[i], this)) {
                cont++;
              }
            }
            if (cont == due) {
              if (cavalliNeri[0].getPosizione().getColonna() == cavalliNeri[uno].getPosizione()
                  .getColonna()) {
                esito = uno;
              } else {
                esito = due;
              }
            }
          }
        }
      } else if (comando.charAt(0) == torre) {
        if (turno == bianco) {
          if (torreBianche.length < due) {
            return esito;
          } else {
            for (int i = 0; i < torreBianche.length; i++) {
              if (controlMovimento(torreBianche[i], this)) {
                cont++;
              }
            }
            if (cont == due) {
              if (torreBianche[0].getPosizione().getRiga()
                  == torreBianche[uno].getPosizione().getRiga()) {
                esito = due;
              } else if (torreBianche[0].getPosizione().getColonna()
                      == torreBianche[uno].getPosizione()
                         .getColonna()) {
                esito = uno;
              } else {
                esito = tre; //se sono su righe e colonne diverse
                //ma vogliono andare sullo stesso punto
              }
            }
          }
        } else {
          if (torreNere.length < due) {
            return esito;
          } else {
            for (int i = 0; i < torreNere.length; i++) {
              if (controlMovimento(torreNere[i], this)) {
                cont++;
              }
            }
            if (cont == due) {
              if (torreNere[0].getPosizione().getRiga() == torreNere[uno].getPosizione().getRiga()) {
                esito = due;
              } else if (torreNere[0].getPosizione().getColonna() == torreNere[uno].getPosizione()
                         .getColonna()) {
                esito = uno;
              } else {
                esito = tre; // se sono su righe e colonne diverse
                //ma vogliono andare sullo stesso punto
              }
            }
          }
        }
      }
      return esito;
    }

    public void traduzionePosIniziale() {
      final int novsette = 97;
      final int sette = 7;
      boolean esito = false;
      if (inizialeTradotta != null) {
        return;
      }
      if (posizioneTradotta != null) {
        if (turno == bianco) {
          if ((comando.charAt(0) - novsette >= 0) && (comando.charAt(0) - novsette <= sette)) {
            for (int i = 0; i < pedoniBianchi.length && !esito; i++) {
              if (controlMovimento(pedoniBianchi[i], this)) {
                inizialeTradotta = pedoniBianchi[i].getPosizione();
                pezzoMosso = pedoniBianchi[i];
                esito = true;
              }
            }
          } else if (comando.charAt(0) == cavallo) {
            for (int i = 0; i < cavalliBianchi.length && !esito; i++) {
              if (controlMovimento(cavalliBianchi[i], this)) {
                inizialeTradotta = cavalliBianchi[i].getPosizione();
                pezzoMosso = cavalliBianchi[i];
                esito = true;
              }
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
          } else if (!esito) {
            System.out.println("mossa illegale");
          }
        } else {
          if ((comando.charAt(0) - novsette >= 0) && (comando.charAt(0) - novsette <= sette)) {
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
          } else if (comando.charAt(0) == torre) {
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
      for (int i = 0; i < comandidArrocco.length; i++) {
        if (comando.equals(comandidArrocco[i])) {
          return true;
        }
      }
      return false;
    }

    public void traduzionePosFinale() {
    	final int uno = 1;
    	final int due = 2;
    	final int tre = 3;
    	final int quattro = 4;
    	final int cinque = 5;
    	final int sei = 6;
    	final int sette = 7;
    	final int novsette = 97;
    	final int quarnove = 49;
      if (isArrocco()) {
        if (turno == bianco) {
          if ((comando.equals(comandidArrocco[0]) || comando.equals(comandidArrocco[uno]))
              && (reBianco.getEnpassant()
                  && (torreBianche[0] != null && torreBianche[0].getEnpassant()))) {
            inizialeTradotta = reBianco.getPosizione();
            pezzoMosso = reBianco;
            posizioneTradotta = new Posizione(0, sei);
          } else if ((comando.equals(comandidArrocco[due]) || comando.equals(comandidArrocco[tre]))
                     && (reBianco.getEnpassant()
                         && (torreBianche[uno] != null && torreBianche[uno].getEnpassant()))) {
            inizialeTradotta = reBianco.getPosizione();
            pezzoMosso = reBianco;
            posizioneTradotta = new Posizione(0, uno);
          }
        } else {
          if ((comando.equals(comandidArrocco[0]) || comando.equals(comandidArrocco[uno]))
              && (reNero.getEnpassant() && (torreNere[0] != null && torreNere[0].getEnpassant()))) {
            inizialeTradotta = reNero.getPosizione();
            pezzoMosso = reNero;
            posizioneTradotta = new Posizione(sette, uno);
          } else if ((comando.equals(comandidArrocco[due]) || comando.equals(comandidArrocco[tre]))
                  && (reNero.getEnpassant()
                  && (torreNere[uno] != null && torreNere[uno].getEnpassant()))) {
            inizialeTradotta = reBianco.getPosizione();
            pezzoMosso = reNero;
            posizioneTradotta = new Posizione(sette, sei);
          }
        }
      }
      if (comando.length() == 0) {
        posizioneTradotta = null;
        inizialeTradotta = null;
      } else if ((comando.length() == due)
          && (comando.charAt(0) - novsette >= 0)
          && (comando.charAt(0) - novsette <= sette)
            && (comando.charAt(uno) - quarnove >= 0)
            && (comando.charAt(uno) - quarnove <= sette)) {
        posizioneTradotta = new Posizione(comando.charAt(uno) - quarnove, comando.charAt(0) - novsette);
        cattura = false;
      } else if ((comando.length() == quattro)
          && (((comando.charAt(0) - novsette >= 0)
            && (comando.charAt(0) - novsette <= sette))
              || ((comando.charAt(0) == regina)
              || (comando.charAt(0) == re)
          || (comando.charAt(0) == alfiere))
          && (comando.charAt(uno) == 'x')
          && ((comando.charAt(due) - novsette >= 0)
          && (comando.charAt(due) - novsette <= sette))
          && ((comando.charAt(tre) - quarnove >= 0)
          && (comando.charAt(tre) - quarnove <= sette)))) {
        posizioneTradotta = new Posizione(comando.charAt(tre) - quarnove, comando.charAt(due) - novsette);
        cattura = true;
      } else if ((comando.length() == sei)
          && (((comando.charAt(0) - novsette >= 0)
          && (comando.charAt(0) - novsette <= sette))
          && (comando.charAt(uno) == 'x')
          && ((comando.charAt(due) - novsette >= 0)
          && (comando.charAt(due) - novsette <= sette))
          && ((comando.charAt(tre) - quarnove >= 0)
          && (comando.charAt(tre) - quarnove <= sette)
          && (comando.charAt(quattro) == 'e')
                                               && (comando.charAt(cinque) == 'p')))) {
        posizioneTradotta = new Posizione(comando.charAt(tre) - quarnove, comando.charAt(due) - novsette);
        cattura = true;
      } else if ((comando.length() == tre)
                 && ((comando.charAt(0) == regina)
                 || (comando.charAt(0) == re)
                 || (comando.charAt(0) == alfiere))
                 && ((comando.charAt(uno) - novsette >= 0)
                 && (comando.charAt(uno) - novsette <= sette))
                 && ((comando.charAt(due) - quarnove >= 0)
                 && (comando.charAt(due) - quarnove <= sette))) {
        posizioneTradotta = new Posizione(comando.charAt(due) - quarnove, comando.charAt(uno) - novsette);
      } else if ((comando.charAt(0) == cavallo)) { // controlla l'ambiguit� del cavallo
        if ((comando.length() == tre)
            && (((comando.charAt(uno) - novsette >= 0)
            && (comando.charAt(uno) - novsette <= sette))
              && ((comando.charAt(due) - quarnove >= 0)
              && (comando.charAt(due) - quarnove <= sette)))) {
          posizioneTradotta = new Posizione(comando.charAt(due) - quarnove, comando.charAt(uno) - novsette);
          if (cavalloAmbiguo() != 0) {
            posizioneTradotta = null;
          }
        } else if ((comando.length() == quattro) && ((comando.charAt(uno) == 'x')
              && ((comando.charAt(due) - novsette >= 0)
              && (comando.charAt(due) - novsette <= sette))
              && ((comando.charAt(tre) - quarnove >= 0)
              && (comando.charAt(tre) - quarnove <= sette)))) {
          posizioneTradotta = new Posizione(comando.charAt(tre) - quarnove, comando.charAt(due) - novsette);
          cattura = true;
          if (cavalloAmbiguo() != 0) {
            posizioneTradotta = null;
          }
        } else if ((comando.length() == cinque)
                   && (((comando.charAt(uno) - novsette >= 0) && (comando.charAt(uno) - novsette <= sette))
                       || ((comando.charAt(uno) - quarnove >= 0) && (comando.charAt(uno) - quarnove <= sette)))
                   && (comando.charAt(due) == 'x')
                   && ((comando.charAt(tre) - novsette >= 0) && (comando.charAt(tre) - novsette <= sette))
                   && ((comando.charAt(quattro) - quarnove >= 0) && (comando.charAt(quattro) - quarnove <= sette))) {
          posizioneTradotta = new Posizione(comando.charAt(quattro) - quarnove, comando.charAt(tre) - novsette);
          if (cavalloAmbiguo() == 0) {
            posizioneTradotta = null;
            inizialeTradotta = null;
          } else if (cavalloAmbiguo() == uno) {
            if ((comando.charAt(uno) - quarnove >= 0) && (comando.charAt(uno) - quarnove <= sette)) {
              if (turno == bianco) {
                for (int i = 0; i < cavalliBianchi.length; i++) {
                  if (cavalliBianchi[i].getPosizione().getRiga() == comando.charAt(uno) - quarnove) {
                    pezzoMosso = cavalliBianchi[i];
                  }
                }
              } else {
                for (int i = 0; i < cavalliNeri.length; i++) {
                  if (cavalliBianchi[i].getPosizione().getRiga() == comando.charAt(uno) - quarnove) {
                    pezzoMosso = cavalliNeri[i];
                  }
                }
              }
              inizialeTradotta = pezzoMosso.getPosizione();
            } else {
              System.out.println("mossa illegale");
            }
          } else if (cavalloAmbiguo() == due) {
            if ((comando.charAt(uno) - novsette >= 0) && (comando.charAt(uno) - novsette <= sette)) {
              if (turno == bianco) {
                for (int i = 0; i < cavalliBianchi.length; i++) {
                  if (cavalliBianchi[i].getPosizione().getColonna() == comando.charAt(uno) - novsette) {
                    pezzoMosso = cavalliBianchi[i];
                  }
                }
              } else {
                for (int i = 0; i < cavalliNeri.length; i++) {
                  if (cavalliBianchi[i].getPosizione().getColonna() == comando.charAt(uno) - novsette) {
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
        } else if ((comando.length() == quattro)
                   && (((comando.charAt(uno) - novsette >= 0) && (comando.charAt(uno) - novsette <= sette))
                       || ((comando.charAt(uno) - quarnove >= 0) && (comando.charAt(uno) - quarnove <= sette)))
                   && ((comando.charAt(due) - novsette >= 0) && (comando.charAt(due) - novsette <= sette))
                   && ((comando.charAt(tre) - quarnove >= 0) && (comando.charAt(tre) - quarnove <= sette))) {
          posizioneTradotta = new Posizione(comando.charAt(tre) - quarnove, comando.charAt(due) - novsette);
          if (cavalloAmbiguo() == 0) {
            posizioneTradotta = null;
            inizialeTradotta = null;
          } else if (cavalloAmbiguo() == uno) {
            if ((comando.charAt(uno) - quarnove >= 0) && (comando.charAt(uno) - quarnove <= sette)) {
              if (turno == bianco) {
                for (int i = 0; i < cavalliBianchi.length; i++) {
                  if (cavalliBianchi[i].getPosizione().getRiga() == comando.charAt(uno) - quarnove) {
                    pezzoMosso = cavalliBianchi[i];
                  }
                }
              } else {
                for (int i = 0; i < cavalliNeri.length; i++) {
                  if (cavalliBianchi[i].getPosizione().getRiga() == comando.charAt(uno) - quarnove) {
                    pezzoMosso = cavalliNeri[i];
                  }
                }
              }
              inizialeTradotta = new Posizione(comando.charAt(uno) - quarnove,
                                               pezzoMosso.getPosizione().getColonna());
            } else {
              System.out.println("mossa illegale");
              posizioneTradotta = null;
            }
          } else if (cavalloAmbiguo() == due) {
            if ((comando.charAt(uno) - novsette >= 0) && (comando.charAt(uno) - novsette <= sette)) {
              if (turno == bianco) {
                for (int i = 0; i < cavalliBianchi.length; i++) {
                  if (cavalliBianchi[i].getPosizione().getColonna() == comando.charAt(uno) - novsette) {
                    pezzoMosso = cavalliBianchi[i];
                  }
                }
              } else {
                for (int i = 0; i < cavalliNeri.length; i++) {
                  if (cavalliBianchi[i].getPosizione().getColonna() == comando.charAt(uno) - novsette) {
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
        if ((comando.length() == tre) && (((comando.charAt(uno) - novsette >= 0)
            && (comando.charAt(uno) - novsette <= sette))
            && ((comando.charAt(due) - quarnove >= 0) && (comando.charAt(due) - quarnove <= sette)))) {
          posizioneTradotta = new Posizione(comando.charAt(due) - quarnove, comando.charAt(uno) - novsette);
          if (cavalloAmbiguo() != 0) {
            posizioneTradotta = null;
          }
        } else if ((comando.length() == quattro) && ((comando.charAt(uno) == 'x')
            && ((comando.charAt(due) - novsette >= 0) && (comando.charAt(due) - novsette <= sette))
            && ((comando.charAt(tre) - quarnove >= 0) && (comando.charAt(tre) - quarnove <= sette)))) {
          posizioneTradotta = new Posizione(comando.charAt(tre) - quarnove, comando.charAt(due) - novsette);
          cattura = true;
          if (cavalloAmbiguo() != 0) {
            posizioneTradotta = null;
          }
        } else if ((comando.length() == cinque) && (comando.charAt(due) == 'x')
                   && ((comando.charAt(tre) - novsette >= 0) && (comando.charAt(tre) - novsette <= sette))
                   && ((comando.charAt(quattro) - quarnove >= 0) && (comando.charAt(quattro) - quarnove <= sette))) {
          posizioneTradotta = new Posizione(comando.charAt(quattro) - quarnove, comando.charAt(tre) - novsette);
          cattura = true;
          if (cavalloAmbiguo() == 0) {
            posizioneTradotta = null;
            inizialeTradotta = null;
          } else if (cavalloAmbiguo() == uno) {
            if ((comando.charAt(uno) - quarnove >= 0) && (comando.charAt(uno) - quarnove <= sette)) {
              inizialeTradotta = new Posizione(comando.charAt(uno) - quarnove, comando.charAt(tre) - novsette);
              pezzoMosso = tabella[inizialeTradotta.getRiga()][inizialeTradotta.getColonna()];
            } else {
              System.out.println("mossa illegale");
            }
          } else if (cavalloAmbiguo() == due) {
            if ((comando.charAt(uno) - novsette >= 0) && (comando.charAt(uno) - novsette <= sette)) {
              inizialeTradotta = new Posizione(comando.charAt(quattro) - quarnove, comando.charAt(uno) - novsette);
              pezzoMosso = tabella[inizialeTradotta.getRiga()][inizialeTradotta.getColonna()];
            } else {
              System.out.println("mossa illegale");
            }
          } else if (cavalloAmbiguo() == tre) {
            if ((comando.charAt(uno) - novsette >= 0) && (comando.charAt(uno) - novsette <= sette)) {
              if (comando.charAt(uno) == comando.charAt(tre)) {
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
              } else if (comando.charAt(uno) - novsette != posizioneTradotta.getColonna()) {
                inizialeTradotta = new Posizione(comando.charAt(quattro) - quarnove, comando.charAt(uno) - novsette);
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
        } else if ((comando.length() == quattro)
                   && (((comando.charAt(uno) - novsette >= 0) && (comando.charAt(uno) - novsette <= sette))
                       || ((comando.charAt(uno) - quarnove >= 0) && (comando.charAt(uno) - quarnove <= sette)))
                   && ((comando.charAt(due) - novsette >= 0) && (comando.charAt(due) - novsette <= sette))
                   && ((comando.charAt(tre) - quarnove >= 0) && (comando.charAt(tre) - quarnove <= sette))) {
          posizioneTradotta = new Posizione(comando.charAt(tre) - quarnove, comando.charAt(due) - novsette);
          if (cavalloAmbiguo() == 0) {
            posizioneTradotta = null;
            inizialeTradotta = null;
          } else if (cavalloAmbiguo() == uno) {
            if ((comando.charAt(uno) - quarnove >= 0) && (comando.charAt(uno) - quarnove <= sette)) {
              inizialeTradotta = new Posizione(comando.charAt(uno) - quarnove, comando.charAt(due) - novsette);
              pezzoMosso = tabella[inizialeTradotta.getRiga()][inizialeTradotta.getColonna()];
            } else {
              System.out.println("mossa illegale");
            }
          } else if (cavalloAmbiguo() == due) {
            if ((comando.charAt(uno) - novsette >= 0) && (comando.charAt(uno) - novsette <= sette)) {
              inizialeTradotta = new Posizione(comando.charAt(tre) - quarnove, comando.charAt(uno) - novsette);
              pezzoMosso = tabella[inizialeTradotta.getRiga()][inizialeTradotta.getColonna()];
            } else {
              System.out.println("mossa illegale");
            }
          } else if (cavalloAmbiguo() == tre) {
            if ((comando.charAt(uno) - novsette >= 0) && (comando.charAt(uno) - novsette <= sette)) {
              if (comando.charAt(uno) == comando.charAt(due)) {
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
              } else if (comando.charAt(uno) - novsette != posizioneTradotta.getColonna()) {
                inizialeTradotta = new Posizione(comando.charAt(tre) - quarnove, comando.charAt(uno) - novsette);
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
      return ((posizioneTradotta != null) && (inizialeTradotta != null));
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
        System.out.print("help      stampa elenco comandi\n");
        System.out.print("board     mostra la schacchiera\n");
        System.out.print("captures  mostra i pezzi mangiati dell'avversario\n");
        System.out.print("moves     mostra le mosse effettuate finora\n");
        System.out.print("quit      esci dal gioco\n");
        System.out.print("play      inizia una nuova partita\n");
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
        System.out.print("nuova partita iniziata, "
            + "inserire help per vededere elenco comandi disponibili\n");
      } else {
        System.out.print("comando non riconosciuto, "
            + "inserire /help per vedere elenco comand disponibili\n");
      }
      posizioneTradotta = null;
    }
  }

  /**
   * classe di tipo entity
   * responsabilita: restituisce i pezzi mangiati durante la partita
   */
  protected class Mangiati {
    private int numeroMangiati = 0;
    private int pedoniMangiati = 0;
    private int torriMangiate = 0;
    private int alfieriMangiati = 0;
    private int regineMangiate = 0;
    private int cavalliMangiati = 0;
    private Pezzo[] mangiati;
    private char[] simboliBianchi = {'\u265F', '\u265D', '\u265C', '\u265B', '\u265E'}; //cod pezzin
    private char[] simboliNeri = {'\u2659', '\u2657', '\u2656', '\u2655', '\u2658'}; //cod pezzi b

    Mangiati(final int grandezza) {
      mangiati = new Pezzo[grandezza];
    }

    public void incrase(final Pezzo pezzo) {
      numeroMangiati++;
      if (pezzo.getNome() == 'p') {
        pedoniMangiati++;
      } else if (pezzo.getNome() == 'N') {
        cavalliMangiati++;
      } else if (pezzo.getNome() == 'R') {
        torriMangiate++;
      } else if (pezzo.getNome() == 'B') {
        alfieriMangiati++;
      } else if (pezzo.getNome() == 'Q') {
        regineMangiate++;
      }
    }

    public void set(final Pezzo pezzo) {
      if (numeroMangiati < mangiati.length) {
        mangiati[numeroMangiati] = pezzo;
        incrase(pezzo);
      } else {
        System.out.println("array pieno li hai mangiati tutti");
      }
    }

    public void displayMangiati()throws UnsupportedEncodingException {

      System.setOut(new PrintStream(System.out, false, "UTF-8"));
      int i = 0;
      if (turno == bianco) {
        System.out.println(simboliNeri[i] + " x " + pedoniMangiati);
        i++;
        System.out.println(simboliNeri[i] + " x " + alfieriMangiati);
        i++;
        System.out.println(simboliNeri[i] + " x " + torriMangiate);
        i++;
        System.out.println(simboliNeri[i] + " x " + regineMangiate);
        i++;
        System.out.println(simboliNeri[i] + " x " + cavalliMangiati);
      } else {
        i = 0;
        System.out.println(simboliBianchi[i] + " x " + pedoniMangiati);
        i++;
        System.out.println(simboliBianchi[i] + " x " + alfieriMangiati);
        i++;
        System.out.println(simboliBianchi[i] + " x " + torriMangiate);
        i++;
        System.out.println(simboliBianchi[i] + " x " + regineMangiate);
        i++;
        System.out.println(simboliBianchi[i] + " x " + cavalliMangiati);
      }
    }
  }
}
