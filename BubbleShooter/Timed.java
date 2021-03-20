import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class Timed {
    //timed mode
    private double time;//how much time has passed
    private double width;//width of timer

    public Timed(){
        restart();
    }

    public void restart(){//restart timer
        time = 0;//time starts at 0
        width = 200;//full length is 200
    }

    public void increase(){//increase time
        time++;
    }

    public double get(){//returns value of time
        return time;
    }

    public void countDown(){//finds what the width of the countdown should be
        width = 200 - (time/1150000.0)*100*200;//find % of 200
    }

    public void draw(Graphics g,Font fnt){//displays countdown
        g.setFont(fnt);
        g.setColor(Color.blue);
        g.drawString("TIME", 40, 628);
        if(width <= 30){
            g.setColor(Color.red);
        }
        else {
            g.setColor(Color.blue);
        }
        g.fillRect(100, 610, (int)(width), 20);
    }
}