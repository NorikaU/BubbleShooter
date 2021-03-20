import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Classic {
    //classic mode
    private int lives;//number of lives
    private Image life;//life image

    public Classic(){
        lives = 11;
        life = new ImageIcon("empty.png").getImage();
    }

    public void reset(){//set to default
        lives = 11;
    }

    public void foul(){//when user misses they lose a life
        lives--;
    }

    public void lifeLost(GameEnd gameEnd, Score score) {//if all lives lost game ends
        gameEnd.lose(score);

    }

    public int getLives(){//returns number of lives
        return lives;
    }

    public void draw(Graphics g) {//displays lives based on how many lives are left
        for(int i=0; i<lives; i++){
            g.drawImage(life,i*30+15,610,null);
        }
    }
}
