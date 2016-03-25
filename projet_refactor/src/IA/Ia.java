package IA;

import java.util.ArrayList;

public abstract class Ia {
  private int arrows;
  private int boundX;
  private int boundY;

  public Ia(int bX, int bY, int arr) {
    arrows = arr;
    boundX = bX;
    boundY = bY;
  }

  public String jouer(int x, int y, ArrayList<String> message) {
    return "h";
  }
}
