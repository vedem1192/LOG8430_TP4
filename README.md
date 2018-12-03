RESTService 

# Démarer MongoDB (à faire sur la VM aussi)
1. Avoir mongoDB installé sur l'ordinateur

2. Lancer mongo dans un terminal avec la commande
	$ mongod
    
    Vous devriez voir la ligne : [initandlisten] waiting for connections on port 27017
    Sinon, vérifié que mongo est bien intallé 
    
3. Dans un second terminal, lancer un shell mongo. Il sera utile pour confirmer que les factures ont bien été ajoutées à la BD
	$ mongo
    
4. Pour vérifier que la facture a bien été ajoutée, aller dans le shell mongo et tapper les commandes suivantes :
       $ show dbs
       $ use LOG8430
       $ db.receipts.find( )
       
    Vous verrez alors l'ensemble des factures. Chaque article inséré dans la table contient un "_id" qui lui est unique, de même que l'identifiant unique de la facture "receiptID" à laquelle il est associé, ainsi que son nom, son prix et la quantité acheté au moment de l'achat.

# Démarrer le service REST locallement
1. Ouvrir le projet dans Eclipse (import project)   

2. Clique droit sur le folder du projet (TP4) > Run As > Run on Server

3. Vous pouvez maintenant enregistrer vos factures à partir du client node.js

# Démarrer le service REST sur la VM
1. Ouvrir le projet dans Eclipse (import project)

2. Clique droit sur le projet (TP4) > Export > WAR file

3. Deployer à http://<IPVM>:8080/manager

# Démarer le client
1. Voir README.md dans le dossier /RestClient/client
