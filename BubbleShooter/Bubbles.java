import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class Bubbles {
    //all the bubbles on top
    private int[][] bubbles;//int array of what bubbles are in what positions
    private Random rand;//make random number
    private Rectangle[][] pos;//array of rectangles of all bubbles
    private final int BLUE = 1, GREEN = 2, CYAN = 3, PURPLE = 4, RED = 5, YELLOW = 6;//constants for all colours
    private int frame;//frame of pop animation
    private int popped;//checking if all bubbles at popPoints have shown pop animation

    public Bubbles() {
        bubbles = new int[10][18];//bubbles are in a rectangle 10x18
        rand = new Random();
        reset();
    }

    public void reset(){//bring everything to default
        frame = 0;
        popped = 0;

        for (int i = 0; i < bubbles.length; i++) {
            for (int j = 0; j < 9; j++) {
                bubbles[i][j] = rand.nextInt(6)+1;
            }
        }
    }

    public Rectangle[][] getRects() {//get rectangles for all bubbles
        Rectangle[][] bubblesRects = new Rectangle[10][18];//recatngle for every bubble
        for (int i = 0; i < bubblesRects.length; i++) {//go through each one
            for (int j = 0; j < bubblesRects[i].length; j++) {
                if (bubbles[i][j] != 0) {//if square is not empty
                    if (j % 2 == 0) {//even rows
                        bubblesRects[i][j] = new Rectangle(i * 30 + 27, j * 27 + 15, 30, 30);
                    } else {//odd rows
                        bubblesRects[i][j] = new Rectangle(i * 30 + 12, j * 27 + 15, 30, 30);
                    }
                } else if (bubbles[i][j] == 0) {//if square is empty rectangle is off screen
                    bubblesRects[i][j] = new Rectangle(-30, -30, 30, 30);//off screen
                }
            }
        }
        return bubblesRects;
    }

    public int[][] getBubbles(){//returns what colour bubbles are where
        return bubbles;
    }

    public boolean isEmpty(){//check if the are no bubbles left
        for (int[] row:bubbles){//go through each
            for(int b:row){
                if(b != 0){//if there is a colour it is not empty
                    return false;
                }
            }
        }
        return true;
    }

    public void draw(Graphics g, Score scr, BallCollide bc) {//display bubbles
        Image img = new ImageIcon("bubble0.png").getImage();//the image that will display
        bubbles = bc.getBubbles();//if any bubbles collided
        ArrayList<int[]> popPoints = bc.getPopPoints();//points that should pop

        for (int i = 0; i < bubbles.length; i++) {//go through each
            for (int j = 0; j < bubbles[i].length; j++) {

                if (popPoints != null) {//if ball hit something with same colour
                    boolean animation = false;//whether pop animated this (i,j)
                    for (int[] p : popPoints) {//checking if (i,j) is in popPoints
                        if (p[0] == i && p[1] == j) {
                            animation = true;
                            img = new ImageIcon("pop" + (bubbles[i][j] % 10) + (frame / 4 % 3 + 1) + ".png").getImage();
                            frame++;
                            popped++;
                        }
                    }

                    if (!animation) {
                        img = new ImageIcon("bubble" + bubbles[i][j] + ".png").getImage();
                    }

                    if (popped > popPoints.size() * 3) {//popping each bubble for three frames each
                        for (int[] p : popPoints) {
                            bubbles[p[0]][p[1]] = 0;//make all the popped bubbles empty
                        }

                        scr.addScore(popPoints);//add score

                        popPoints.clear();//empty popPoints
                        frame = 0;//start from beginning of animation next time
                        popped = 0;
                    }
                }

                else {
                    img = new ImageIcon("bubble" + bubbles[i][j] + ".png").getImage();//if there's no pop it displays noraml bubble
                }

                if (j % 2 == 0) {//even rows are offset
                    g.drawImage(img, i * 30 + 27, j * 27 + 15, null);
                } else {
                    g.drawImage(img, i * 30 + 12, j * 27 + 15, null);
                }
            }
        }
    }
}