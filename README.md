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
	java -cp bin/ jeuSocket.WumpusClientAiPrudente <hostname> 4444 <NombreDeTour>
ou
	java -cp bin/ jeuSocket.WumpusClientAiTemeraire <hostname> 4444 <NombreDeTour>
ou
	java -cp bin/ jeuSocket.WumpusClientAiLargeur <hostname> 4444 <NombreDeTour>

<NombreDeTour> Corresond au nombre de tour après lequel l'IA décidera de retourner à la sortie.
	
-Une fois le jeu lancé vous devriez recevoir un message:
"welcome to hunt the wumpus"
vous pouvez alors entrer des données pour configurer la partie sous la forme:

	<taille de la carte>	[<Nom de la classe>:<qantité>]

par exemple:
	10 Trou:3

Vous pouvez spécifier plusieurs couples objets/quantités en les séparants d'un espace:
	10 Wumpus:1 Trou:3 Sac:5

Attention, l'IA ne gère pas un cas où il y a plusieurs Wumpus.
