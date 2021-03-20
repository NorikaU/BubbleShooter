import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class GameEnd {
    //when the game is over
    private boolean gameOver;//checks whether the game is over

    private Image losebg;//shows different images baed on whether user won or lost
    private Image winbg;
    private Image bg;//stores the correct background

    private Image mainImg;//button image
    private Image mainClick;//button tamge when user hovers on it
    private Rectangle mainRect;//rectangle of where the button is
    private Image highImg;//highscores button
    private Image highClick;//highscores button when hovered
    private Rectangle highRect;//rectangle with high score button

    public GameEnd(){
        gameOver = false;//if game is over
        losebg = new ImageIcon("gameOver.png").getImage();
        winbg = new ImageIcon("win.png").getImage();

        mainImg = new ImageIcon("main.png").getImage();
        mainClick = new ImageIcon("mainClick.png").getImage();
        mainRect = new Rectangle(40,440,100,100);

        highImg = new ImageIcon("high.png").getImage();
        highClick = new ImageIcon("highClick.png").getImage();
        highRect = new Rectangle(200,440,100,100);
    }

    public void reset(){//back to deafault
        gameOver = false;
    }

    public boolean isGameOver(){//returns whether the game is over or not
        return gameOver;
    }

    public void lose(Score score) {//for when user loses, score as a parameter to find highscore
        gameOver = true;
        bg = losebg;
        score.highScore();
    }

    public void win(Score score) {//for when user wins, score as a parameter to find highscore
        gameOver = true;
        bg = winbg;
        score.highScore();
    }

    public void draw(Graphics g, int mx, int my){//display buttons and background
        g.drawImage(bg, 0,0,null);

        if(mainRect.contains(mx,my)){
            g.drawImage(mainClick, 40,440,null);
        }
        else{
            g.drawImage(mainImg, 40,440,null);
        }

        if(highRect.contains(mx,my)){
            g.drawImage(highClick, 200,440,null);
        }
        else{
            g.drawImage(highImg, 200,440,null);
        }
    }
}
