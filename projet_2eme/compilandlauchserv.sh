clear
clear
javac -cp bin -d bin src/jeuSocket/*.java
javac -cp bin -d bin src/IA/*.java
java -cp bin/ jeuSocket.WumpusServer 4444
