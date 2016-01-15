package testroom;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class Trad {

private String addr;

public Trad() {
	super();
	output = System.getProperty("user.dir") + "/"+"tempT2G";
	input =System.getProperty("user.dir") + "/"+"tempG2T";
	}

public void trad() { 
	Scanner sc = new Scanner(input);
	try {
		FileWriter f = new FileWriter(output, false);
		BufferedWriter w = new BufferedWriter(f);
		w.write("ok");
		w.flush();
		w.close();
		}
	catch (IOException ioe) {
		System.out.print("Erreur : ");
		ioe.printStackTrace();
		}
	}

public static void main(String[] args) {
	Trad t = new Trad();
	Game g = new Game();
	
	t.trad();
	g.play();
	}

}
