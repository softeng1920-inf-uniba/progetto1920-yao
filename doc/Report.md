# RELAZIONE TECNICA FINALE 

## Introduzione 

Con la presente documentazione, si intende esporre lo sforzo realizzativo necessario allo sviluppo dell'applicazione software del giuoco di scacchi (gioco di probabile origine indiana che si svolge tra due giocatori che dispongono, nell'apposito quadrato detto scacchiera, di 16 pezzi ciascuno, in cui vince il giocatore che riesce a mettere il Re dell'avversario in condizioni di non potere difendersi dall'attacco portatogli.  

Il giuoco si svolge tra 2 giocatori, inizia la partita il giocatore che ha in possesso i pezzi bianchi, i turni successivi si fanno in modo alternato dove ogni giocatore può muovere un solo pezzo per volta con eccezione del arrocco dove si muovono 2 pezzi, la partita finisce quando il re sta sotto minaccia e questa situazione non può cambiare attraverso nessun movimento legale.  

Il progetto è stato realizzato dal gruppo di studio universitario "Yao", composto dal team di sviluppo, rappresentato dagli studenti del corso di laurea di Informatica dell'Università degli studi "Aldo Moro" di Bari, formatosi per perseguire nello svolgimento delle attività del corso di studio 
Ingegneria del Software, tenuto dal prof. Filippo Lanubile.

Di seguito si elencano i membri di tale gruppo:

* Andre Alvizuri
* Federico Chiaradia
* Daniela di Serio
* Nicolò Florio
* Niccolò Caldarulo
* Fabio Loguercio
* Pietro Conca

L'implementazione del progetto è stata eseguita attenendosi alle modalità di sviluppo, di rilascio e mantenimento dei prodotti complessi, 
sostenute nel framework agile dello Scrum, suddiviso in quattro sprint, aventi ognuno un obiettivo e un criterio di accettazione.

Come piattaforma di hosting del suddetto software è stato utilizzato Github.com,una piattaforma di sviluppo collaborativo che utilizza un sistema di control di versioni Git sviluppata da Linus Torvals.

Rispettando la Pipeline di progetto fornita, dopo che il codice realizzato passava i test automatici e i controlli di qualità, l'immagine del container veniva caricata su un repository dedicato solo alle immagini Docker, grazie a Github Actions.
Nel repository è presente il codice del software, realizzato in Java, e la relativa documentazione.
Di seguito viene fornita una illustrazione degli strumenti utilizzati per la realizzazione del progetto.



| Categoria                                   | Scelta                                                       |
| :------------------------------------------ | :----------------------------------------------------------- |
| Linguaggio di programmazione                | Java                                                         |
| Modellazione UML                            | Visual Paradigm Community Edition e Visual Paradigm Online Express |
| IDE                                         | Eclipse                                                      |
| Gestione dipendenze e build automation      | Gradle                                                       |
| Controllo di versione                       | Git                                                          |
| Cloud-based hosting                         | GitHub Classroom                                             |
| Unit test automatizzato                     | JUnit                                                        |
| Copertura del test                          | Jacoco, Coveralls                                            |
| Analisi statica per QA                      | Checkstyle, SpotBugs                                         |
| Continuous integration e deployment (CI/CD) | GitHub CI                                                    |
| Containerization                            | Docker                                                       |
| Coordinamento                               | Issue tracking e project board di GitHub                     |
| Comunicazione                               | Slack                                                        |



## Modello di Dominio



![](./res/img/report/Modello_di_domi.PNG)

## Requisiti Specifici

- Requisiti funzionali(rappresentati tramite tabella **MoSCoW** ):

  | Descrizione                                               | MosCow    |
  | --------------------------------------------------------- | :-------- |
  | mostrare elenco comandi                                   | Must have |
  | iniziare una nuova partita                                | Must have |
  | chiudere il gioco                                         | Must have |
  | mostrare la scacchiera                                    | Must have |
  | muovere un Pedone (include cattura semplice e en passant) | Must have |
  | mostrare le mosse giocate                                 | Must have |
  | muovere un Cavallo                                        | Must have |
  | muovere un Alfiere                                        | Must have |
  | muovere una Torre                                         | Must have |
  | muovere la Donna                                          | Must have |
  | muovere il Re                                             | Must have |
  | arroccare corto                                           | Must have |
  | arroccare lungo                                           | Must have |




- Requisiti non funzionali:

  | Descrizione                                                  | MosCow      |
  | ------------------------------------------------------------ | ----------- |
  | Portabilità                                                  | Should have |
  | L'applicazione accetta mosse in notazione algebrica abbreviata italiana | Should have |
  | Rappresentazione pezzi in formato Unicode(UTF-8)             | Should have |
  | Esecuzione applicazione tramite Docker                       | Should have |
  | Promuovere un pedone                                         | Won't have  |
  | Mostrare punteggio materiale                                 | Won't have  |
  | Avere patta automatica                                       | Won't have  |
  | Impostare il tempo di gioco                                  | Won't have  |
  | Mostrare il tempo di gioco                                   | Won't have  |
  | Chiudere la partita con scacco matto                         | Won't have  |
  | Accettare la patta                                           | Won't have  |
  | Offrire la patta                                             | Won't have  |
  | Mettere il Re sotto scacco                                   | Won't have  |
  | Visualizzare le mosse possibili                              | Won't have  |
  | Abbandonare la partita                                       | Won't have  |
  | Importare una partita                                        | Won't have  |
  | Esportare una partita                                        | Won't have  |
  | Continuare una partita                                       | Won't have  |

  ​	

## System Design

Non è stato adottato alcun stile architetturale, in quanto la complessità di distribuzione del progetto non è stata elevata, dato che le user story richieste da realizzare rappresentavano un numero gestibile di entità all'interno del sistema.



## Design

DI seguito si riportano il diagramma delle classi e di sequenza del progetto:

![](res/img/report/Diagramma classi.PNG)

Avviare una partita :

![](res/img/report/Avviare una partita(US).PNG)



Muovere un pezzo:

![](res/img/report/ULTImATE MUOVERE PEZZO.PNG)





Moves (Stesso comportamento per qualsiasi altra istruzione di sistema):  

![](res/img/report/ULTIMATE MOVES.PNG)



Nella stesura del codice sono stati applicati alcuni design pattern. Di seguito vengono descritti:

Builder: ogni pezzo(Cavallo,Alfiere,Donna,ecc..) avente una propria classe, è costruito come estensione della classe Pezzo, e prevede al suo interno specifici attributi e metodi che ne garantiscono la specificità, infatti, nonostante queste classi presentano molte caratteristiche simili, ogni costruttore è diverso, e permette di distinguerli gli uni dagli altri.

Mediator: è stato utilizzato l'oggetto Tabella, per incapsulare gli altri oggetti di tipo Pezzo, in modo da garantire interazione tra loro, ma non il loro accoppiamento.

Observer: è presente la classe Mangiati, che lavora dipendendo dalla classe Tabella, aggiornando i propri attributi subito dopo aver ricevuto una notifica da quest'ultima.

Subito dopo lo sprint 1, ci siamo accorti di avere adottato un design architetturale non ottimale per la definizione della struttura del sistema, che di conseguenza ha portato a complicazioni che si sono mostrate nel tempo. E' stato deciso di non effettuare una manutenzione preventiva per la correzione dell'architettura del progetto, in quanto si è valutato il fatto che sarebbe costato al gruppo risorse temporali, che avrebbero avuto conseguenze sul completamento del lavoro. 



Di seguito vengono riportati i report di checkstyle, rispettivamente prima e dopo aver modificato il codice:

![](res/img/report/checkstyle prima.PNG)



![](res/img/report/reportcheckstyledopo.png)



I warning rilevati da checkstyle, che sono stati ignorati sono i seguenti:

-I magic number rilevati all'interno dei metodi costruttore, sono stati lasciati invariati, perché altrimenti avrebbero generato altri errori;

-Non sono stati inseriti commenti javadoc nei punti del codice che non erano richiesti negli sprint;

-Era presente un metodo della classe Tabella che superava il limite delle righe massime consentito da checkstyle; non è stato risolto perché si sarebbe dovuto effettuare una manutenzione straordinaria, che avrebbe potuto compromettere la consegna del progetto;

Di seguito vengono riportati i risultati di Spotbugs:

![](res/img/report/Spotbug html.PNG)

I warning rilevati da spotbugs riguardano rispettivamente la procedura di uscita dal programma, che porta all'arresto del sistema e ciò viene visto come procedura che va avviata in circostanze controllate. E' stato ignorato perché questa procedura era richiesta nelle user story "Chiudere il gioco".

Mentre gli altri warning vanno a segnalare che viene utilizzato più volte lo scanner per l'input, e questo non viene mai chiuso. Questo warning non è stato risolto perché il gruppo non è stato in grado di trovare una soluzione, nonostante i vari tentativi svolti.

Riepilogo test

![](res/img/report/coveralls.PNG)

Link a coveralls per visionare la pagina dei test: https://coveralls.io/github/softeng1920-inf-uniba/progetto1920-yao?branch=master

## Manuale Utente

###### Descrizione

Attraverso questa applicazione, è possibile interagire con il gioco degli Scacchi, permettendo a due giocatori di sfidarsi uno contro uno.
Per avviare una partita, il comando richiesto è 'play'.
Il primo giocatore al quale viene richiesto un comando è il bianco, quindi ha inizio l'alternarsi dei turni dei giocatori, ai quali si chiede un istruzione per muovere/mangiare un pezzo 
o per usare i comandi di sistema.
Per visualizzare le istruzioni di sistema è necessario digitare il comando 'help', mentre per eseguire una mossa, bisogna scrivere quest'ultima in notazione algebrica abbreviata italiana.
In caso di inserimento di comando sintatticamente e semanticamente non corretto, viene mostrato un messaggio di errore all'utente , per tanto quest'ultimo è tenuto a reinserire l'istruzione.
I comandi accettati per eseguire mosse, sono quelli stabiliti dalla notazione algebrica abbreviata italiana, e quindi anche i comandi 'speciali' devono rispettarla, pertanto per eseguire:

* Cattura en passant: il comando di cattura va scritto normalmente, e va aggiunto " ep" subito dopo;
* Arrocco corto: O-O oppure 0-0;
* Arrocco lungo: O-O-O oppure 0-0-0;

I comandi di sistema accettati sono i seguenti:

- help

- board 

- play

- quit

- captures

- moves

- mosse (che rispettano la notazione algebrica abbreviata italiana)

  ######  Requisiti di sistema

Elenco dei terminali supportati
Di seguito l’elenco dei terminali su cui poter eseguire il container dell’applicazione scacchi:

Linux:

   * Terminal

Mac OS:

   *  Terminal

Windows:

   * Terminal del sottosistema Windows per Linux
   * Git Bash (in questo caso il comando Docker ha come prefisso winpty; es: winpty docker -it ....)
   * Windows Terminal
   * Windows Terminal (preview)

###### Installazione (step by step)

Per eseguire l'applicazione, è necessario avviare l'immagine tramite Docker e svolgere le seguenti operazioni:

- avviare Docker localmente (una volta aperta l’applicazione, bisogna attendere che nel menu di Docker compaia la scritta “Docker is running”);

- se si utilizza Windows selezionare `Switch to Linux containers` nel menu di Docker;

- aprire il terminale

- digitare infine i comandi:

- ```
  docker pull docker.pkg.github.com/softeng1920-inf-uniba/docker_1920/yao:latest  
  ```

###### Ingresso alla applicazione

Una volta avviata l'immagine con Docker, viene mostrata all'utente la console, che chiede all'utente di digitare il comando play per avviare la partita.

![](res/img/report/terminale_con_play.png)

Dopodiché la partita viene avviata, e si richiede all'utente di digitare un nuovo comando:

![](res/img/report/terminale_con_help.png)

funzioni (specifica tutto ciò che si può fare al interno con immagini)

  - help: visualizza la lista dei comandi di sistema

    ![](res/img/report/terminale_con_help.png)

    

  - board: l'applicazione mostra la scacchiera e i pezzi posizionati al suo interno

    ![](res/img/report/board.png)

  - play: viene avviata una nuova partita

  - quit: viene terminata la partita corrente,l'applicazione si chiude e compare il prompt del sistema operativo

  - captures: vengono mostrati i propri pezzi catturati 

    ![](res/img/report/captures.png)

    

  - moves: vengono mostrate le mosse effettuate da entrambi i giocatori

  ![](res/img/report/moves.png)



## Processo di sviluppo e organizzazione del lavoro

Dopo la creazione del gruppo, e dopo aver familiarizzato con la piattaforma Slack, il team ha creato dei nuovi canali di comunicazione per lo svolgimento del progetto, tramite Microsoft Teams.

Microsoft Teams è stato utilizzato prima di tutto per la fase di pianificazione, nella quale siamo riusciti a riassumere le nostre skills e capacità operative, per poi capire come suddividere il lavoro.

Abbiamo stabilito le condizioni per il Daily Scrum e per i meeting operativi, in base alla disponibilità e alle assegnazioni dei task personali. Durante le fasi di analisi dei requisiti e di progettazione, la presenza di ogni membro del gruppo è stata favorevole per la plasmazione del progetto, delineato dalla project board e dagli issues in essa inseriti, e per le fasi successive di elaborazione del  codice.

Mentre nella fase di stesura del codice, si sono svolte riunioni indipendenti, seguite dai diversi sottogruppi che sono stati creati in base alle esigenze trovate, in modo tale che ci fosse una equa suddivisione del lavoro e che si potesse applicare, ove possibile, la tecnica di pair programming.

La fase di collaudo era svolta principalmente da alcuni membri del gruppo prestabiliti, che avevano il compito di osservare l'andamento del lavoro e di assicurarsi che si stesse procedendo in modo tale che  i criteri di accettazione stabiliti dal product owner nelle user story fossero rispettati e che si potessero accettare le pull request.

Dopo ogni sprint review effettuati con il product owner, sono state effettuati degli sprint retrospective, in modo da  organizzare le manutenzioni che dovevano essere svolte per risolvere gli errori commessi, o discutere dei miglioramenti da applicare negli sprint successivi.



## Analisi retrospettiva

Siamo rimasti compiaciuti per Il fatto di essere migliorati tutti in maniera progressiva durante il percorso, dopo aver avuto un avvio un po'turbolento, ed essere riusciti ad adattarsi alle esigenze del progetto in una maniera del tutto nuova per noi, in quanto abbiamo trovato innovativo il metodo di lavoro adottato.

Abbiamo notato che questa esperienza ci è servita anche a migliorare le nostre capacità di programmazione e di comunicazione, ed è stata anche utile e interessante per capire il mondo che c'è dietro lo sviluppo del software.

Abbiamo trovato molto utile il canale di assistenza di Slack, dato che abbiamo reperito informazioni che si sono poi dimostrate vantaggiose per lo svolgimento del progetto.

Considerando che contavamo di 7 integranti nel gruppo, abbiamo trovato difficoltà in alcuni momenti nella fase di organizzazione per la suddivisione del lavoro e per la pianificazione di riunioni.

Tenendo presente che questa è stata la nostra prima collaborazione per un progetto universitario , avremmo voluto svolgerlo con più interazione umana, cosa che non è stata resa possibile a causa delle circostanze contemporanee(contesto sociale ristretto).

In alcuni casi abbiamo riscontrato difficoltà nella fase di programmazione, in quanto ci siamo trovati dinanzi  a circostanze di programmazione che non avevamo mai affrontato.

In certi momenti eravamo dubbiosi nell'interpretare alcune direttive che erano ambigue, e ci hanno portato a prendere decisioni che si sono dimostrate non corrette.
