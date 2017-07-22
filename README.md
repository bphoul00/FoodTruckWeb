## Prérequis

- Java 1.8+
- Maven 3.0+
- PostgreSQL 9.4+ avec PostGIS 2.0+

## PostgreSQL

- [Installation de PostgreSQL et de PostGIS pour Windows](http://www.bostongis.com/PrinterFriendly.aspx?content_name=postgis_tut01)
- [PostgreSQL.app pour OSX](http://postgresapp.com/)

## Compilation et exécution

    $ mvn spring-boot:run

Le projet est alors disponible à l'adresse [http://localhost:8080/](http://localhost:8080/)

## Routes disponibles

- [http://localhost:8080/](http://localhost:8080/)
- [http://localhost:8080/activites-375e](http://localhost:8080/activites-375e)
- [http://localhost:8080/stations-bixi](http://localhost:8080/stations-bixi)

## Guide étape par étape pour lancer le projet

1. Pour commencer, vous aurez besoin d'installer les logiciels Listés dans la section Prérequis .

2. After installing all the software, you will have to initialize your database.

3. Vous devez d'abord créer un utilisateur avec le serveur d'installation suivant

Server [localhost]
Database [postgres]
Port [5432]
Username [postgres]
Password [postgres]

4. Vous devrez d'abord créer la base de données. Entrez ce qui suit pour créer la base de dates. Cela va également se connecter automatiquement à la base de données pour la prochaine étape.

    \i 'C:/Users/Bernard/Desktop/INF4375_Project/migrations/create-database.sql'

5. Ensuite, copiez et collez la prochaine ligne de commande pour créer le schéma de la base de données.

    \i 'C:/Users/Bernard/Desktop/INF4375_Project/migrations/create-schema.sql'

6. Maintenant, vous devriez avoir les résultats de la base de données postgres avec le tableau suivant: activites, lieu, dates et bixi.

7. Ensuite, vous naviguez de votre terminal vers le dossier du projet. Le dossier, vous devriez voir dans cet endroit. Entrez la commande suivante sans le signe dollar à l'avant.

    $ mvn clean install

8. Ensuite, entrez le code suivant pour démarrer le serveur qui accueillera le projet.

    $ mvn spring-boot

9. Attendez quelques secondes, puis entrez l'adresse suivant dans votre navigateur.

    http://localhost:8080/
