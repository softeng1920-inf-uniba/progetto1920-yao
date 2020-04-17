package it.uniba.main;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class AppMain {
	public static void main(String args[]) throws UnsupportedEncodingException {
		System.out.println("scrivi /play  per iniziare una nuova partita");
		String comando;
		boolean esito = true;
		while(esito){
			Scanner input = new Scanner(System.in);
			comando = input.nextLine();
			if(comando.length() == 0){
				System.out.println("comando non valido");
			}
			else if (comando.equals("/play") || comando.equals("/Play") || (comando.equals("/PLAY"))){
				esito = false;
			}
			else{
				System.out.println("comando non  valido");
			}
		}
		System.out.println("scrivi /help per visualizzare i comandi disponibili");
		Tabella tabella = new Tabella(8 , 8);
		for (int i = 0; true; i++){
			tabella.muovicondomanda();
		}
	}
}