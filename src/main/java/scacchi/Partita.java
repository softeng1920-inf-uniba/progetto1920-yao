import java.util.Scanner;
public class Partita {
    public static void main(String args[]){
        System.out.println("scrivi /new  per iniziare una nuova partita");
        String comando;
        boolean esito = true;
        while(esito){
            Scanner input = new Scanner(System.in);
            comando = input.nextLine();
            if(comando.length() == 0){
                System.out.println("comando non valido");
            }
            else if (comando.equals("/new") || comando.equals("/New") || (comando.equals("/NEW"))){
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

