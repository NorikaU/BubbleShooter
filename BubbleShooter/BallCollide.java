import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class BallCollide {
    //for when a ball collides with a bubble
    private int[][] bubbles;//int array of what bubbles are in what positions
    private ArrayList<int[]> popPoints;//points that should be popped

    public BallCollide(int[][] bub) {
        bubbles = bub;
    }

    public void collide(Ball ball, Bubbles bbl, GameEnd gameEnd, Score score, Classic classic) {
        Rectangle ballRect = ball.getRect();//rectangle of ball
        Rectangle[][] bubbleRects = bbl.getRects();//recatngles of all bubbles

        boolean collided = false;//so it only collides with one ball at a time
        for (int i = 0; i < bubbleRects.length; i++) {//go through each bubble to check intersection
            for (int j = 0; j < bubbleRects[i].length; j++) {
                if (bubbleRects[i][j].intersects(ballRect)) {
                    if (!collided) {//only one collision at a time
                        collided = true;
                        int[] stickCoord = stick(i, j, ball, gameEnd, score);//where the ball will stick

                        if (ball.getColour() == bubbles[i][j]) {//if the ball is the same colour as the bubble it intersected with
                            chain(i, j, ball.getColour(), stickCoord);//find all bubbles of the same colour connected with ball
                        }
                        else{
                            classic.foul();//if ball didn't hit same colour a life is lost
                        }

                        ball.reset();//put ball back to beginning
                    }
                }
            }
        }
    }

    public int[] stick(int x, int y, Ball ball, GameEnd gameEnd, Score score) {//returns where the ball will stick
        int bubbleX;//x coord of intersected bubble based on row
        if (y+1 > 17){
            gameEnd.lose(score);
            return(new int[]{0,0});
        }
        if (y % 2 == 0) {
            bubbleX = x * 30 + 27;
            if (ball.getX() - bubbleX <= 0) {//find what side of the bubble the ball should stick to
                bubbles[x][y + 1] = ball.getColour();
                return new int[]{x, y + 1};
            } else {
                bubbles[x + 1][y + 1] = ball.getColour();
                return new int[]{x + 1, y + 1};
            }
        } else {
            bubbleX = x * 30 + 12;
            if (ball.getX() - bubbleX >= 0) {//find what side of the bubble the ball should stick to
                bubbles[x][y + 1] = ball.getColour();
                return new int[]{x, y + 1};
            } else {
                bubbles[x - 1][y + 1] = ball.getColour();
                return new int[]{x - 1, y + 1};
            }
        }
    }

    public void chain(int x, int y, int colour, int[] stick) {
        popPoints = new ArrayList<int[]>();//ArrayList of all points that should be popped
        popPoints.add(stick);//pop ball
        popPoints.add(new int[]{x, y});//pop the bubble ball intersected with
        chain(x, y, popPoints, colour);
    }

    public void chain(int x, int y, ArrayList<int[]> points, int colour) {
        if (x - 1 >= 0 && y - 1 >= 0 && x + 1 <= 9 && y + 1 <= 9) {//in bounds
            if (colour == bubbles[x - 1][y]) {
                points.add(new int[] {x - 1, y});
                bubbles[x - 1][y] += 10;//add 10 so it is not the same and it only gets checked once
                chain(x - 1, y, points, colour);
            } else if (colour == bubbles[x][y - 1]) {
                points.add(new int[] {x, y - 1});
                bubbles[x][y - 1] += 10;
                chain(x, y - 1, points, colour);
            }

            if (colour == bubbles[x + 1][y]) {
                points.add(new int[] {x + 1, y});
                bubbles[x + 1][y] += 10;
                chain(x + 1, y, points, colour);
            } else if (colour == bubbles[x][y + 1]) {
                points.add(new int[] {x, y + 1});
                bubbles[x][y + 1] += 10;
                chain(x, y + 1, points, colour);
            }

            if (colour == bubbles[x + 1][y + 1]) {
                points.add(new int[] {x + 1, y + 1});
                bubbles[x + 1][y + 1] += 10;
                chain(x + 1, y + 1, points, colour);
            }

            if (colour == bubbles[x - 1][y + 1]) {
                points.add(new int[] {x - 1, y + 1});
                bubbles[x - 1][y + 1] += 10;
                chain(x - 1, y + 1, points, colour);
            }

            if (colour == bubbles[x + 1][y - 1]) {
                points.add(new int[] {x + 1, y - 1});
                bubbles[x + 1][y - 1] += 10;
                chain(x + 1, y - 1, points, colour);
            }

            if (colour == bubbles[x - 1][y - 1]) {
                points.add(new int[] {x - 1, y - 1});
                bubbles[x - 1][y - 1] += 10;
                chain(x - 1, y - 1, points, colour);
            }
        }
    }

    public int[][] getBubbles() {//returns what colour bubbles are where
        return bubbles;
    }

    public ArrayList<int[]> getPopPoints() {//return points that should be popped
        return popPoints;
    }
}


