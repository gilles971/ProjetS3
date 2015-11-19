
package IA;

public class IAa {
private String[][] labyrinth;
private int currentX;
private int currentY;
private int arrows;

public IAa(int originX, int originY, int boundX, int boundY) {
	labyrinth = new String[boundX][boundY]();
	currentX = originX;
	currentY = originY;
}

public String play(String danger, posX, posY) {
	MAJ(danger, posX, posY);
	Process
}

private void MAJ(String danger, posX, posY) {
	currentX = posX;
	currentY = posY;
	labyrinth[currentX][currentY] = danger;
}

}
