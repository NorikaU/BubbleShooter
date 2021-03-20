import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class Ball {
    //class for the ball
    private double x,y;//x and y of the ball
    private int colour;//colour of the ball
    private Image colourImg;//the image of the ball based on colour
    private int nextBall;//upcoming ball
    private Image nextImg;//image for upcoming ball
    private double chngx, chngy;//how much x and y will change
    private boolean launch;//check if launched
    private boolean bounce;//check if it hit a wall and should bounce
    private Random rand;//to make random number

    public Ball(){
        rand = new Random();
        nextBall = rand.nextInt(6)+1;//choosing random colour
        reset();
    }

    public void reset(){
        x = 155;//x in middle
        y = 550;//y at bottom
        launch = false;
        bounce = false;

        colour = nextBall;
        colourImg = new ImageIcon("bubble"+ colour +".png").getImage();//choosing a random ball colour
        nextBall = rand.nextInt(6)+1;
        nextImg = new ImageIcon("next"+ nextBall +".png").getImage();//colour of next ball
    }

    public int getColour(){//return colour of ball
        return colour;
    }

    public double getX(){//return x coordinate of ball
        return x;
    }

    public void launch(int mx,int my) {//when it first launches
        if (launch == false && mx > 15 && mx<325 && my > 15 && my<525) {//if it's not currently launching and the mouse position is in bounds
            launch = true;
            double dist = Math.sqrt(Math.pow(mx-170, 2)+Math.pow(my-565, 2));//find distance
            chngx = (mx-170)/dist*5;//how much x and y much change to get to that position *5 to make it smoother
            chngy = (my-565)/dist*5;
        }
    }

    public void move() {//move ball after it is launched
        if (launch) {
            if (x+chngx < 15) {//bounce against wall
                x = 15 + (x+chngx);
                chngx *= -1;
            }
            else if (x+chngx > 295) {
                x = 295 * 2 - (x+chngx);
                chngx *= -1;
            }
            else {
                x += chngx;
            }

            y += chngy;
        }
    }

    public Rectangle getRect(){//return rectangle of ball
        return new Rectangle((int)(x),(int)(y),30,30);
    }

    public void draw(Graphics g){//display ball and upcoming ball
        g.drawImage(colourImg, (int)(x), (int)(y), null);
        g.drawImage(nextImg, 250, 550, null);
    }
}