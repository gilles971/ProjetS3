import java.util.Scanner;
import java.awt.Robot;

public class Application {

	public static void main(String[] args) {
		Traducteur trad = new Traducteur();
		System.out.println("Vous sentez un courant d'air !!");
	//	Robot robot = new Robot();
		int test = trad.scanEntreeDeplacement();
	//	robot.keyPress(KeyEvent.VK_A);
		System.out.println(test);
}
}
