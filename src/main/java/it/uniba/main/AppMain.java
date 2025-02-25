package it.uniba.main;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * classe di tipo boundary
 * responsabilita:
 * - permette all'utente di avviare una partita
 * -
 */
public class AppMain {
  public static void main(final String[]args) throws UnsupportedEncodingException {
    System.out.println("scrivi play  per iniziare una nuova partita");
    String comando;
    boolean esito = true;
    while (esito) {
      Scanner input = new Scanner(System.in);
      comando = input.nextLine();
      if (comando.length() == 0) {
        System.out.println("comando non valido");
      } else if (comando.equals("play") || comando.equals("Play")
          || (comando.equals("PLAY"))) {
        esito = false;
      } else {
        System.out.println("comando non  valido");
      }
    }
    System.out.println("scrivi help per visualizzare i comandi disponibili");
    final int righe = 8;
    final int colonne = 8;
    Tabella tabella = new Tabella(righe, colonne);
    while (true) {
      tabella.stampaTurno();
      tabella.muovicondomanda();
    }
  }
}
