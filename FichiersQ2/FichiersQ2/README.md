Équipe Sierra - TP4 - LOG8430

Instructions et commentaires pour la question 2

Lien Github: https://github.com/vedem1192/LOG8430_TP4
Lien vidéo explicatif: https://www.youtube.com/watch?v=RRCIk_m9GQk

Veuillez lire les explications supplémentaires concernant le code à la fin de ce readme, suite aux instructions concernant la VM.

Voici les étapes à suivre afin de recréer l'environnement de test de notre VM, une fois la VM créer avec les explications fournies dans le cadre du TP:
1. Installer JDK8 via le site d'oracle 
2. Installer Cassandra: 
    - Créer un dossier opt dans lequel tout se trouvera: 
        mkdir -p ~/opt/packages/cassandra/
    - Dans ce répertoire, installer cassandra en utilisant les instructions pour Desbian:
    https://cassandra.apache.org/download/

3. Préparation de la base de données Cassandra
    - Dans ce même répertoire, lancer Cassandra: 
        cassandra -f 
    - Aller dans bin et lancer cqlsh:
        ./cqlsh
    - Faites les commandes suivantes dans cqlsh:
        create keyspace dev with replication = {'class':'SimpleStrategy','replication_factor':1};
        use dev;
        CREATE TABLE facture ( receiptID UUID PRIMARY KEY, name text, price double, quantity int);
        INSERT INTO facture (receiptID, name, price, quantity) VALUES (123e4567-e89b-12d3-a456-426655440000, 'banana', 20.0, 2);
        INSERT INTO facture (receiptID, name, price, quantity) VALUES (123e4567-e89b-12d3-a456-426655440001, 'apple', 2.0, 4);
    - Vous pouvez ajouter le nombre d'items que vous désirez.
4. Installer Spark:
    - Télécharger les fichiers dans le dossier opt:
        curl -O http://apache.mirror.gtcomm.net/spark/spark-2.4.0/spark-2.4.0-bin-hadoop2.7.tgz
    - Dans le sous-dossier de spark obtenu, sous conf, créer le fichier spark-env.sh et y mettre les instructions suivantes:
        export SPARK_MASTER_HOST=127.0.0.1
        export SPARK_LOCAL_IP=127.0.0.1
    - Dans ce même sous-dossier, créer le fichier spark-defaults.conf, et y mettre les instructions suivantes: 
        spark.master    spark://127.0.0.1:7077
        spark.jars.packages                 datastax:spark-cassandra-connector:2.0.0-s_2.11
        spark.cassandra.connection.host     127.0.0.1
    - Retourner dans le dossier précédent, puis entrer dans sbin. De là, modifier le fichier start-slave.sh pour y ajouter cette ligne
    dans les variables d'environnement:
        SPARK_WORKER_INSTANCES=2
    -Note: Tout ces fichiers sont disponibles dans le github.
    - Démarrer le master:
        start-master.sh
    - Démarrer les slaves:
        start-slave.sh spark://127.0.0.1/7077
    -Retourner dans opt puis télécharger le cassandra-spark connector:
        curl -O http://dl.bintray.com/spark-packages/maven/datastax/spark-cassandra-connector/2.4.0-s_2.11/spark-cassandra-connector-2.4.0-s_2.11.jar
5. Finalement, exécutez le service spark dans l'installation de spark sous bin/spark-shell:
    ./spark-shell --packages datastax:spark-cassandra-connector:2.4.0-s_2.11 --conf spark.cassandra.connection.host=127.0.0.1
    -Vous pourrez ainsi tester les commandes du script scala nommé app.scala.
6. Compiler le script scala pour l'exécuter avec spark-submit:
    - Simplement installer sbt sur la VM:
        install sbt
        echo "deb https://dl.bintray.com/sbt/debian /" | sudo tee -a /etc/apt/sources.list.d/sbt.list
        sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823
        sudo apt-get update
        sudo apt-get install sbt
    - Créer un fichier build.sbt, dont le contenu est le suivant:
        name         := "SparkMe Project"
        version      := "1.0"
        organization := "pl.japila"

        scalaVersion := "2.11.12"

        libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.0"
        resolvers += Resolver.mavenLocal
    - Note: Ce dernier est disponible sur le github.
    -Compiler le fichier app.scala en exécutant sbt assembly pour obtenir le .jar
    -Soumettre ce fichier à spark-submit comme premier argument, avec le packet en deuxième:
        ./spark-submit app.jar --packages datastax:spark-cassandra-connector:2.4.0-s_2.11



INFORMATIONS SUPPLÉMENTAIRE:
Vous trouverez dans l'entrepot une image démontrant sur l'interface web de Spark les workers et slave bien démarré.
Au niveau de la question 2, voici quelques commentaires sur le travail à faire, tel que démontré dans le vidéo:

Installez et configurez une grappe Spark avec un master et deux slaves. : FAIT
(b) Installez et configurez une instance de la base des données Cassandra. : FAIT
    Définissez la table pour stocker les factures: FAIT 
    Connectez la base des données avec le service de la question 1 et la grappe de Spark. (10pts) : FAIT pour la grappe Spark
(c) Développez un travail Spark pour identifier les produits fréquents. : FAIT en 3 versions, une en scala, et 2 en python utilisant 
    des méthodes de connexion à Spark différentes. Tout le détail de ces travaux sont disponibles dans les fichiers app.scala,
    cluster.py et cluster2.py. Le détail des connexions s'y trouve, certaines fonctionnant avec spark-submit, et d'autres uniquement
    avec spark-shell ou python3. Des problèmes d'importations de librairie nous ont causés bien des problèmes, à la fois
    au niveau des libraires pour la connexions qu'au niveau des librairies pour les algorithmes de tri. Étant donnée
    que la connexion a été établie tel que le démontre la vidéo, et que c'est plutôt au niveau du traitement des données
    avec FPGrowth que nous avons eu la majorité de nos problèmes, puisque cette partie ne concerne pas vraiment la matière
    vue en cours, nous avons laissé tomber.
(d) Déployez les composantes de l’architectures aux machines virtuelles: FAIT en local
    Connectez les composantes pour créer un flux de travail: FAIT partiellement


Liens utiles:
https://opencredo.com/blogs/deploy-spark-apache-cassandra/
https://spark.apache.org/docs/latest/spark-standalone.html
https://spark.apache.org/docs/latest/spark-standalone.html
https://www.datastax.com/dev/blog/kindling-an-introduction-to-spark-with-cassandra-part-1
https://jaceklaskowski.gitbooks.io/mastering-apache-spark/exercises/spark-first-app.html
https://docs.datastax.com/en/cql/3.3/cql/cql_using/useCreateTable.html


    Merci et bonne correction.