import java.util.Scanner;
import java.util.Vector;
public class Tabella {
    private int righe;
    private int colonne;
    private Pezzo[][] tabella;
    private Pezzo[] pedoniBianchi;
    private Pezzo[] pedoniNeri;
    private char[] altriPezzibiachi = {'\u2656' , '\u2658' , '\u2657' , '\u2655' , '\u2654' , '\u2657' , '\u2658' , '\u2656' };
    private char[] altriPezzineri= {'\u265C' , '\u265E' , '\u265D' , '\u265B' , '\u265A' , '\u265D' , '\u265E' , '\u265C' };
    private int rigaPedoniBianchi = 1;
    private int rigaPedoniNeri = 6;
    private int bianco = 0;
    private int nero = 1;
    private int turno;
    private Vector<String> comandi = new Vector<String>();
    Tabella(int righe , int colonne){
        this.righe = righe;
        this.colonne = colonne;
        turno = bianco;
        tabella = new Pezzo[8][8];
        pedoniBianchi = new Pezzo[8];
        pedoniNeri = new Pezzo[8];
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
        if ((toGo.getRiga() >=this.getRighe()) || (toGo.getColonna() >= this.getColonne()) || (toGo.getRiga() < 0) || (toGo.getColonna() < 0)){
            System.out.println("mossa illegales");
        }
        else if (pezzo == null){
            System.out.println("mossa illegale");
        }
        else{
            if ((this.getTabella(toGo) != null )){
                if (this.getTabella(toGo).getColore() == pezzo.getColore()) {
                    System.out.println("mossa illegale");
                }
                else{
                    Posizione pos1 = pezzo.getPosizione();
                    mangiaPedina(toGo , pezzo);
                    setTabella(null , pos1);
                    pezzo.setPosizione(toGo);
                    System.out.println("pedone mangiato");
                }
            }
            else{
                Posizione pos1 = pezzo.getPosizione();
                setTabella(pezzo , toGo);
                setTabella(null , pos1);
                pezzo.setPosizione(toGo);
                System.out.println("pedone spostato");
            }
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

    public void muovicondomanda(){
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
            move(comando.posizioneTradotta , tabella[comando.inizialeTradotta.getRiga()][comando.inizialeTradotta.getColonna()]);
            comandi.add(comando.getComando());
            cambiaTurno();
        }
        else{
            System.out.println("Hai scritto male il comando");
        }
    }



    private void mangiaPedina(Posizione toGo, Pezzo pezzo){
        if (tabella[toGo.getRiga()][toGo.getColonna()].getColore() == bianco){
            bianchiMangiati.Set(tabella[toGo.getRiga()][toGo.getColonna()]);
        }
        else{
            neriMangiati.Set(tabella[toGo.getRiga()][toGo.getColonna()]);
        }
        setTabella(pezzo , toGo);

    }

    private boolean controlMovimento(Pezzo pezzo , Comando comando){
        Posizione toGo = comando.posizioneTradotta;
        switch (pezzo.getNome()) {
            case 'p':
                if (pezzo.getColore() == bianco) {
                    if (pezzo.getPosizione().getRiga() != rigaPedoniBianchi) {
                        pezzo.giaMosso();
                    } // controllo gittata x , y sulla posizione di arrivo                    and  ((controllo per vedera se è nella stessa colonna               and  la posizuione è nulla  )                            oppure   (la differenza in valiore asoluto tera le colonne è 1          and   ci deve essere una pedina la                      and    il colore de essere uguale al nero                          and    la fdifferenza tra le righe deve essere di uno) )         and           la pedina non sim muove sullo stesso punto)
                    return ((toGo.getRiga() - pezzo.getPosizione().getRiga() <= pezzo.getGittata()) && (((toGo.getColonna() - pezzo.getPosizione().getColonna() == 0) && (tabella[toGo.getRiga()][toGo.getColonna()] == null) && (!comando.getCattura()) && controlloGittata(pezzo , toGo)) || ((Math.abs(toGo.getColonna() - pezzo.getPosizione().getColonna()) == 1) && (tabella[toGo.getRiga()][toGo.getColonna()] != null) && (tabella[toGo.getRiga()][toGo.getColonna()].getColore() == nero)  && (toGo.getRiga() - pezzo.getPosizione().getRiga() == 1  ) && comando.getCattura())) && (toGo.getRiga() - pezzo.getPosizione().getRiga() != 0)  && (toGo.getRiga() - pezzo.getPosizione().getRiga() > 0));

                }
                else {
                    if (pezzo.getPosizione().getRiga() != rigaPedoniNeri) {
                        pezzo.giaMosso();
                    }
                    if ((toGo.getRiga() - pezzo.getPosizione().getRiga() >= -pezzo.getGittata()) && (((toGo.getColonna() - pezzo.getPosizione().getColonna() == 0) && (tabella[toGo.getRiga()][toGo.getColonna()] == null) && (!comando.getCattura()) && controlloGittata(pezzo , toGo)) || ((Math.abs(toGo.getColonna() - pezzo.getPosizione().getColonna()) == 1) && (tabella[toGo.getRiga()][toGo.getColonna()] != null) && (tabella[toGo.getRiga()][toGo.getColonna()].getColore() == bianco)  && (Math.abs(toGo.getRiga() - pezzo.getPosizione().getRiga()) == 1  ))) && (toGo.getRiga() - pezzo.getPosizione().getRiga() != 0) && (toGo.getRiga() - pezzo.getPosizione().getRiga() < 0)){
                        return  true;
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


    public void cambiaTurno(){
        if (turno == bianco){
            turno = nero;
        }
        else{
            turno = bianco;
        }
    }

    public void esciDalGioco(){
        System.exit(0);
    }



    public class Comando{
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
        private char sys = '/';
        Comando(String commands){
            this.comando = commands;
            if (comando.length() == 0){

            }
            else if (comando.charAt(0) == sys){
                sistema = true;
            }else{
                traduzionePosFinale();
                traduzionePosIniziale();
            }
        }

        public void traduzionePosIniziale() {
            boolean esito = false;
            if (posizioneTradotta != null){
                if (turno == bianco) {
                    if ((comando.charAt(0) - 97 >= 0) && (comando.charAt(0) - 97 <= 7)) {
                        for (int i = 0; i < pedoniBianchi.length && !esito; i++) {
                            if (controlMovimento(pedoniBianchi[i] , this)){
                                inizialeTradotta = pedoniBianchi[i].getPosizione();
                                pezzoMosso = pedoniBianchi[i];
                                esito = true;
                            }
                        }//cicla  i pedoni e vede quale di questi si può muovere
                        if(!esito){
                            System.out.println("mossa illegale");
                        }
                    }//controllano tutti i pedoni e si vede si potrebbe implementare un algoritmo di ricerca migliore che cerca  a partire dalla colonna iniziale del pedone perchè si ha piu probabilità di torvarlo vicino che lontano però dato che i dati non sono ocsi grossi e massicci possiamo lasiare questa
                }//controlla se si sta muovendo un pedone, e traduce il movimento
                else {
                    if ( (comando.charAt(0) - 97 >= 0) && (comando.charAt(0) - 97 <= 7)) {
                        for (int i = 0; i < pedoniNeri.length && !esito; i++) {
                            if (controlMovimento(pedoniNeri[i] , this)){
                                inizialeTradotta = pedoniNeri[i].getPosizione();
                                pezzoMosso = pedoniNeri[i];
                                esito = true;
                            }
                        }
                        if (!esito){
                            System.out.println("mossa illegale");
                        }
                    }
                }//le funzioni cmabiano a seconda se si sta giocando nero o bianco qunid c'è un if grosso alla inizio
            }
            else{
                inizialeTradotta = null;
            }//dato che non hai scirtto bene il comando acnhe la posizione finale è nulla
        }//qusta funzione si occupa solo di tradurre il movimento qualunque esso sia poi il contorlo se lo può fare o meno si farà in seguito

        public void traduzionePosFinale(){
            if (comando.length() == 0){
                System.out.println("inserisci un comando valido");
                posizioneTradotta = null;
                inizialeTradotta = null;
            }
            else if ((comando.length() == 2) && (comando.charAt(0) - 97 >= 0) && (comando.charAt(0) - 97 <=  7) && (comando.charAt(1) - 49 >= 0) && (comando.charAt(1) - 49 <= 7)) {
                posizioneTradotta = new Posizione(comando.charAt(1) - 49 , comando.charAt(0) - 97);
                cattura = false;
            }//comando di movimento semplice per pedone
            else if ((comando.length() == 4) && (((comando.charAt(0) - 97 >= 0) && (comando.charAt(0) - 97 <=  7)) || ((comando.charAt(0) == regina ) || (comando.charAt(0) == re) || (comando.charAt(0) == alfiere) || (comando.charAt(0) == torre) || (comando.charAt(0) == cavallo)) && (comando.charAt(1) == 'x') && ((comando.charAt(2) - 97 >= 0) && (comando.charAt(2) - 97 <=  7)) && ((comando.charAt(3) - 49 >= 0) && (comando.charAt(3) - 49 <= 7)))){
                posizioneTradotta = new Posizione(comando.charAt(3) - 49 , comando.charAt(2) - 97);
                cattura = true;
            }//comando di movimento con mangiata senza ambiguità che prevede anche l'ambiguità dei pedoni ne verifica la correttezza sintattica e assegna la posizione tradotta alla varibaile
            else{
                posizioneTradotta = null;
            }//altrimenit la posizione tradotta è nulla e se è nulla significa che la roba non l'hai scirtta bene
        }



        public Posizione getPosizioneTradotta() {
            return posizioneTradotta;
        }



        public boolean scrittaBene(){
            if ((posizioneTradotta != null) && (inizialeTradotta != null)){
                return true;
            }
            else{
                return  false;
            }
        }
        public String getComando(){
            return comando;
        }

        public boolean getCattura(){
            return cattura;
        }

        public boolean getSistema(){
            return sistema;
        }


    }

}


