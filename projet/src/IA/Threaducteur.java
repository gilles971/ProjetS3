package IA;

import java.awt.Robot;
import java.awt.AWTException;

public class Threaducteur extends Thread {

private Robot returner;

public Threaducteur(String name) {
	super(name);
	try {
		returner = new Robot();
		}
	catch (AWTException e) {
		System.out.println(e.getMessage());
		}
	}

public void run() {
	returner.keyPress(13);
	returner.keyRelease(13);
	String newLine = System.getProperty("line.separator");
	while(true) {
		System.out.println(newLine+newLine+newLine);
		//System.out.println("d n"+newLine);
		/*returner.keyPress(13);
                returner.keyRelease(13);
		returner.keyPress(13);
                returner.keyRelease(13);*/
		try {
			sleep(500);
			}
		catch (InterruptedException e) {
			System.out.println(e.getMessage());
			}/*
		System.out.println("d e\n");
                returner.keyPress(13);
                returner.keyRelease(13);
                returner.keyPress(13);
                returner.keyRelease(13);
                try {
                        sleep(500);
                        }
                catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                        }
		System.out.println("d s\n");
                returner.keyPress(13);
                returner.keyRelease(13);
                returner.keyPress(13);
                returner.keyRelease(13);
                try {
                        sleep(500);
                        }
                catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                        }
		System.out.println("d o\n");
                returner.keyPress(13);
                returner.keyRelease(13);
                returner.keyPress(13);
                returner.keyRelease(13);
                try {
                        sleep(500);
                        }
                catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                        }*/
		}
	}
}
