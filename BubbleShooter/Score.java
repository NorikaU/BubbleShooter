import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class Score {
    //class that deals with the score
    private int score;//the score

    public Score(){
        score = 0;//score starts at 0
    }

    public void reset(){//set score back to 0
        score = 0;
    }

    public void addScore (ArrayList<int[]> popPoints){//add score based on how many popped
        double increase = 1;
        for (int []p: popPoints) {
            score += 50 * increase;//each bubble is worth 50
            increase += 0.025;//more bubbles get higher score
        }
    }

    public void highScore() {//find high score of player
        String name = JOptionPane.showInputDialog("Name:");//get name
        try {
            File oldHighs = new File("highScore.txt");//previous highscore
            File highs = File.createTempFile("blankHS", ".txt");//blank file to write highscore and can insert a line in the middle if necessary

            BufferedReader reader = new BufferedReader(new FileReader(oldHighs));
            BufferedWriter output = new BufferedWriter(new FileWriter(highs, true));

            String line = reader.readLine();//go through each line
            while (line != null) {
                if (Integer.parseInt(line.split(" ")[1]) < score) {//if user's score is greater than a score in the file
                    output.append(name + " " + score);
                    output.newLine();
                } else {//if it doesn't insert a line just write the next
                    output.append(line);
                    output.newLine();
                    line = reader.readLine();//go to next line
                }
            }

            reader.close();
            output.close();
            oldHighs.delete();//get rid of old highscore
            highs.renameTo(oldHighs);//the new one becomes the old one

        } catch (Exception e) {
            System.out.println("error");
        }
    }

    public void drawHS(Graphics g, Font fnt) {//display high scores
        Image bg = new ImageIcon("hs.png").getImage();//background image
        g.drawImage(bg, 0,0,null);
        g.setFont(fnt);//font

        try {
            File highs = new File("highScore.txt");
            BufferedReader reader = new BufferedReader(new FileReader(highs));

            g.setColor(Color.blue);

            String line = reader.readLine();//go through each line and display it
            for (int i = 0; i < 5; i++) {
                String name = line.split(" ")[0];
                String score = line.split(" ")[1];
                g.drawString(name, 20, i * 90 + 130);//display name on top of its score
                g.drawString(score, 20, i * 90 + 170);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("error");
        }

    }

    public void draw(Graphics g, Font fnt) {//display score during game
        g.setFont(fnt);
        g.setColor(Color.blue);

        g.drawString("Score", 20, 560);
        g.drawString(""+score, 20, 580);
    }
}