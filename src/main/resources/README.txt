
Candidato: Riccardo Malara

Esercizio: DB Benchmarks

Database: MS SQL Server
IDE: NetBeans
Project Model: Maven
Connettore: jdbc 6.0
Il connettore al database é stato aggiunto a Maven Repositories -> Local
Main Class: com.mycompany.diennea1.DBBenchmark.java

Script table: dbScript.sql

***Commento al codice***
Fase 1: 
inizializzazione parametri di dimensione del test e connessione al DB
il test prevede l'esistenza della tabella Product con primary key ProductId 
L'AutoCommit della connessione al DB viene disabilitato 

Fase 2: 
Pulizia del DB: rimozione di tutti i record dalla tabella Product

Fase 3:
Benchmark del comando insert

Fase 4:
Benchmark del comando select

I comandi sql sono eseguiti utilizzando PreparedStatement

Le SQLException sono gestite nel metodo principale perchè non ha senso proseguire il test se si
verifica un errore.

***Considerazioni***
Uno dei fattori critici di questo processo di selezione (almeno dal mio punto di vista)
é capire quanto "doloroso" sarà il passaggio da .Net a Java. Qui di seguito trovate 
alcune considerazioni su come ho affrontato questo test. 

Premetto che ho utilizzato un computer su cui non era installato nessuno strumento di programmazione.
Ho installato: 
-SQLServer 2016 express e SSMS
-JavaSE JDK
-NetBeans

Non conoscevo Maven, ma ho notato che fa già parte di NetBeans

Il setup dei vari ambienti é andato bene. il primo impatto con NetBeans dopo anni é stato 
abbastanza buono. Non ho avuto grossi problemi nel creare il progetto e compilarlo e neanche 
nell'individuare le funzionalità di base del IDE.

Il passo successivo é stato quello di configurare il jdbc per SQL Server
con Maven. E qui le cose non sono andate molto bene: credo di aver impiegato almeno 3 ore 
per risolvere completamente la situazione. Nonostante la connessione al database funzioni,
c'é un messagio di warning che non sono riuscito a risolvere. E sinceramente l'apertura
della connessione mi sembra sospettosamente lenta.

La parte di scrittura del codice in Java é stata relativamente piacevole.
Non conoscevo l'oggetto PreparedStatement, ma la documentazione oracle é stata
sufficiente a rendermi operativo.

Mi sarebbe piaciuto utilizzare funzioni un po' più eleganti per il calcolo di min/max/avg
ho provato a sbirciare le possibilità offerte da List e Collection, ma non ho trovato
nulla di particolarmente attraente. Non ho approfondito perché mi sono reso conto che 
stavo cercando di tradurre .Net in Java e non credo sia l'approccio più corretto.

Nella gestione delle eccezioni ho scoperto con piacere il try with resources.

Build, Run, Debug: ok

Configurazione del progetto e capacità di maneggiarlo: direi che su questio aspetto
ho molto su cui lavorare. Mi sarebbe piaciuto aggiungere un file di configurazione
in cui fosse specificata la connectionString al database.

Queste più o meno sono le senzazioni a caldo. Spero che queste informazioni
siano utili.