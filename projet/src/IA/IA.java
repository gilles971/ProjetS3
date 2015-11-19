
package IA;

public abstract class IA {
private String[][] labyrinth;
private int currentX;
private int currentY;
private int arrows;

public String play(String danger, int posX, int posY);

private void MAJ(String danger, int posX, int posY);

}
