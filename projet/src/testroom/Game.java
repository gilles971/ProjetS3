package testroom;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class Game {

private String addr;

public Game() {
	super();
	output = System.getProperty("user.dir") + "/"+"tempG2T";
	input = System.getProperty("user.dir") + "/"+"tempT2G";
	}

public void play() {
	Scanner sc = new Scanner(input);
	if(sc.hasNext()) {
	if(sc.next() == "ok") {
		try {
			FileWriter f = new FileWriter(output, true);
			BufferedWriter w = new BufferedWriter(f);
			w.write("d s");
			w.flush();
			w.close();
			}
		catch (IOException ioe) {
			System.out.print("Erreur : ");
			ioe.printStackTrace();
			}
		}
		}
	sc.close();
	}	
}
