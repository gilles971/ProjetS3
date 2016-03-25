# ProjetS3

pour lancer les IA:
-lancez un terminal
-rendez vous dans ProjetS3/projet_refactor
-entrez:
	bash compilandlauchserv.sh

-lancez un autre terminal
-rendez vous dans ProjetS3/projet_refactor
-entrez:
	hostname
-copiez le résultat de hostname
-entrez en insérant le résultat de hostname:
	java -cp bin/ jeuSocket.WumpusClientAI3 <hostname> 4444

-Une fois le jeu lancé vous devriez recevoir un message:
"welcome to hunt the wumpus"
vous pouvez alors entrer des données pour configurer la partie sous la forme:

	<taille de la carte>	[<Nom de la classe>:<qantité>]

par exemple:
	10 Trou:3

Vous pouvez spécifier plusieurs couples objets/quantités en les séparants d'un espace:
	10 Wumpus:2 Trou:3
