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
        pezzo.setPosizione(toGo); //mette la poiszione di pezzo uguali al posto in cui è ora
        setTabella(pezzo , toGo); // mette pezzo nel posto della tabella
        System.out.println("pedone mangiato con en passant");
    }

    private boolean controlMovimento(Pezzo pezzo , Comando comando){
        Posizione toGo = comando.posizioneTradotta;
        switch (pezzo.getNome()) {
            case 'p':
                if (pezzo.getColore() == bianco) {
                    isEnpassant(comando);
                    if (pezzo.getPosizione().getRiga() != rigaPedoniBianchi) {
                        pezzo.giaMosso();
                    } // controllo gittata x , y sulla posizione di arrivo                    and  ((controllo per vedera se è nella stessa colonna                         and  la posizuione è nulla  )                          e si vuole caturare   e  la gittata è quella giusta            oppure   (la differenza in valiore asoluto tera le colonne è 1          and   ci deve essere una pedina                    and    il colore de essere uuale al nero                               e   la differenza di righe è 1                                  e si sta catturando       cattura con en passant
                    if ((toGo.getRiga() - pezzo.getPosizione().getRiga() <= pezzo.getGittata()) && (((toGo.getColonna() - pezzo.getPosizione().getColonna() == 0) && (tabella[toGo.getRiga()][toGo.getColonna()] == null) && (!comando.getCattura()) && controlloGittata(pezzo , toGo)) || ((Math.abs(toGo.getColonna() - pezzo.getPosizione().getColonna()) == 1) && (tabella[toGo.getRiga()][toGo.getColonna()] != null) && (tabella[toGo.getRiga()][toGo.getColonna()].getColore() == nero)  && (toGo.getRiga() - pezzo.getPosizione().getRiga() == 1  ) && comando.getCattura()) || ((Math.abs(toGo.getColonna() - pezzo.getPosizione().getColonna()) == 1) && (tabella[toGo.getRiga()][toGo.getColonna()] == null) && (tabella[toGo.getRiga() - 1][toGo.getColonna()] != null) && (tabella[toGo.getRiga() - 1][toGo.getColonna()].getColore() == nero) && (tabella[toGo.getRiga() - 1][toGo.getColonna()].getCatturabileE()))) && (toGo.getRiga() - pezzo.getPosizione().getRiga() != 0)  && (toGo.getRiga() - pezzo.getPosizione().getRiga() > 0)){
                        isEnpassant(comando);
                        return true;
                    }

                }
                else {
                    if (pezzo.getPosizione().getRiga() != rigaPedoniNeri) {
                        pezzo.giaMosso();
                    }
                    if ((toGo.getRiga() - pezzo.getPosizione().getRiga() >= -pezzo.getGittata()) && (((toGo.getColonna() - pezzo.getPosizione().getColonna() == 0) && (tabella[toGo.getRiga()][toGo.getColonna()] == null) && (!comando.getCattura()) && controlloGittata(pezzo , toGo)) || ((Math.abs(toGo.getColonna() - pezzo.getPosizione().getColonna()) == 1) && (tabella[toGo.getRiga()][toGo.getColonna()] != null) && (tabella[toGo.getRiga()][toGo.getColonna()].getColore() == bianco)  && (Math.abs(toGo.getRiga() - pezzo.getPosizione().getRiga()) == 1  )) || ((Math.abs(toGo.getColonna() - pezzo.getPosizione().getColonna()) == 1) && (tabella[toGo.getRiga()][toGo.getColonna()] == null) && (tabella[toGo.getRiga() + 1][toGo.getColonna()] != null) && (tabella[toGo.getRiga() + 1][toGo.getColonna()].getColore() == bianco) && (tabella[toGo.getRiga() + 1][toGo.getColonna()].getCatturabileE()))) && (toGo.getRiga() - pezzo.getPosizione().getRiga() != 0) && (toGo.getRiga() - pezzo.getPosizione().getRiga() < 0)){
                        isEnpassant(comando);
                        return true;
                    }

                }
            default:
                return false;
        }
    }//controlla se per ogni tipo di pezzo è possibile lo spostamento

    private boolean controlloGittata(Pezzo pezzo , Posizione toGo){
        switch (pezzo.getNome()){
            case 'p':
                if (pezzo.getColore() == bianco){
                    for (int i = 1; i <= pezzo.getGittata(); i++){
                        if (pezzo.getPosizione().getRiga() + i > 7){
                            return false;
                        }
                        else if (tabella[pezzo.getPosizione().getRiga() + i][pezzo.getPosizione().getColonna()] != null){
                            return false;
                        }
                    }
                }
                else{
                    for (int i = 1; i <= pezzo.getGittata(); i++){
                        if (pezzo.getPosizione().getRiga() - i < 0){
                            return false;
                        }
                        else if (tabella[pezzo.getPosizione().getRiga() - i][pezzo.getPosizione().getColonna()] != null){
                            return false;
                        }
                    }
                }

            default:
                return true;
        }
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
        private Posizione posizioneTradotta;//essendo il comando un vettore di char trasformiamo i char in int , la a minuscola è il 97 esimo carattere e ha valore 97 invece l'1 ha valore 49
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
        private String[] comandiSistema = {"help" , "boards" , "captures" , "moves" , "quit" , "play"};

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
                        }//cicla  i pedoni e vede quale di questi si può muovere
                        if (!esito) {
                            System.out.println("mossa illegale");
                        }
                    }//controllano tutti i pedoni e si vede si potrebbe implementare un algoritmo di ricerca migliore che cerca  a partire dalla colonna iniziale del pedone perchè si ha piu probabilità di torvarlo vicino che lontano però dato che i dati non sono ocsi grossi e massicci possiamo lasiare questa
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
                }//le funzioni cmabiano a seconda se si sta giocando nero o bianco qunid c'è un if grosso alla inizio
            } else {
                inizialeTradotta = null;
            }//dato che non hai scirtto bene il comando acnhe la posizione finale è nulla
        }//qusta funzione si occupa solo di tradurre il movimento qualunque esso sia poi il contorlo se lo può fare o meno si farà in seguito

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
            }//comando di movimento con mangiata senza ambiguità che prevede anche l'ambiguità dei pedoni ne verifica la correttezza sintattica e assegna la posizione tradotta alla varibaile
            else if ((comando.length() == 6) && (((comando.charAt(0) - 97 >= 0) && (comando.charAt(0) - 97 <= 7)) || ((comando.charAt(0) == regina) || (comando.charAt(0) == re) || (comando.charAt(0) == alfiere) || (comando.charAt(0) == torre) || (comando.charAt(0) == cavallo)) && (comando.charAt(1) == 'x') && ((comando.charAt(2) - 97 >= 0) && (comando.charAt(2) - 97 <= 7)) && ((comando.charAt(3) - 49 >= 0) && (comando.charAt(3) - 49 <= 7) && (comando.charAt(4) == 'e') && (comando.charAt(5) == 'p')))) {
                posizioneTradotta = new Posizione(comando.charAt(3) - 49, comando.charAt(2) - 97);
                cattura = true;
            }
            else {
                posizioneTradotta = null;
            }//altrimenit la posizione tradotta è nulla e se è nulla significa che la roba non l'hai scirtta bene
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
                System.out.println("help   stampa elenco comandi");
                System.out.println("board   mostra la schacchiera");
                System.out.println("captures   mostra i pezzi mangiati dell avversario");
                System.out.println("moves     mossa le mosse effettuate finora");
                System.out.println("quit      esci dal gioco");
                System.out.println("play       inizia una nuova aprtita");
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


