//Popper.java
//Norika Upadhyaya

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.MouseInfo;

public class Popper extends JFrame {

    public Popper() {
        super("Bubble Popper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PopperPanel game = new PopperPanel();
        add(game);
        pack();
        setLocation(300,0);
        //setResizable(false);
        setVisible(true);
    }

    public static void main(String[] arguments) {
        Popper frame = new Popper();
    }
}

class PopperPanel extends JPanel implements KeyListener, MouseListener, ActionListener{
    private boolean []keys;//which keys are pressed
    private Timer myTimer;//controls frame rate
    private Bubbles bubs;//bubbles at top
    private Ball ball;//ball at bottom
    private String mode = "main";//what mode to show
    private Image gamebg;//background for the game
    private Font fnt;//general font
    private Font scoreFnt;//font for high scores, its is bigger
    private Classic classic;//classic mode gameplay
    private Timed time;//timed gameplay
    private Timer gameTime;//how long user has to complete game in timed mode
    private Score score;//score
    private BallCollide bc;//for when the ball collides with the bubbles on top
    private GameEnd gameEnd;//for when the game ends

    private Image mainbg;//main menu background
    private Image classicImg;//classic button
    private Image classicClick;//classic button when user hovers over it
    private Rectangle classicRect;//rectangle for the classic button
    private Image timed;//timed button
    private Image timedClick;//timed button when user hovers over it
    private Rectangle timedRect;//rectangle for the timed button
    private Image mainImg;//main menu button
    private Image mainClick;//main menu button when user hovers over it
    private Rectangle HSmainRect;//rectangle containing main menu button in high scores mode
    private Rectangle mainRect;//rectangle containing main menu button mode
    private Rectangle highRect;//high score rectangle

    public Image load(String name){
        return new ImageIcon(name).getImage();
    }

    public PopperPanel(){
        keys = new boolean[KeyEvent.KEY_LAST+1];
        myTimer = new Timer(25, this);

        classic = new Classic();
        time = new Timed();

        mainbg = load("mainbg.png");

        classicImg = load("classic.png");
        classicClick = load("classicClicked.png");
        classicRect = new Rectangle(40,440,100,100);

        timed = load("timed.png");
        timedClick = load("timedClicked.png");
        timedRect = new Rectangle(200,440,100,100);

        mainImg = new ImageIcon("main.png").getImage();
        mainClick = new ImageIcon("mainClick.png").getImage();
        HSmainRect = new Rectangle(220,530,100,100);

        mainRect = new Rectangle(40,440,100,100);
        highRect = new Rectangle(200,440,100,100);

        gamebg = load("game_bg.png");

        bubs = new Bubbles();
        ball = new Ball();
        score = new Score();
        bc = new BallCollide(bubs.getBubbles());
        gameEnd = new GameEnd();

        fnt = new Font("Comic Sans", Font.BOLD,20);
        scoreFnt = new Font("Comic Sans", Font.BOLD,40);
        setPreferredSize(new Dimension(340, 650));
        addKeyListener(this);
        addMouseListener(this);
    }

    // addNotify triggers when the Panel gets added to the frame.
    // Using this avoids null-pointer exceptions.
    // x.y() - if x is null, we get null-pointer exception
    @Override
    public void addNotify() {
        super.addNotify();
        setFocusable(true);
        requestFocus();
        myTimer.start();
    }

    public void updateGame() {
        if (mode == "classic" || mode == "timed") {
            ball.move();
            bc.collide(ball, bubs, gameEnd, score, classic);

            if(bubs.isEmpty()){
                gameEnd.win(score);
            }

            if (mode == "classic") {
                if (classic.getLives() <= 0) {
                    gameEnd.lose(score);
                }
            }

            if (mode == "timed") {
                time.increase();
                time.countDown();
            }
        }

        if (gameEnd.isGameOver()) {
            mode = "gameOver";
            bubs.reset();
            ball.reset();
            classic.reset();
            time.restart();
            score.reset();
        }
    }

    @Override
    public void paint(Graphics g){
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        Point offset = getLocationOnScreen();
        int mx = mouse.x - offset.x;
        int my = mouse.y - offset.y;

        if(mode=="main"){
            g.drawImage(mainbg, 0, 0, null);
            if(classicRect.contains(mx,my)){
                g.drawImage(classicClick, 40,440,null);
            }
            else{
                g.drawImage(classicImg, 40,440,null);
            }

            if(timedRect.contains(mx,my)){
                g.drawImage(timedClick, 200,440,null);
            }
            else{
                g.drawImage(timed, 200,440,null);
            }
        }

        if(mode == "classic" || mode == "timed") {
            g.drawImage(gamebg, 0, 0, null);

            bubs.draw(g, score, bc);
            ball.draw(g);
            score.draw(g, fnt);

            if (mode == "classic") {
                classic.draw(g);
            }

            if (mode == "timed") {
                time.draw(g, fnt);
            }
        }

        if (mode == "gameOver") {
            gameEnd.draw(g, mx, my);
        }

        if (mode == "high") {
            score.drawHS(g,scoreFnt);
            if(HSmainRect.contains(mx,my)){
                g.drawImage(mainClick, 220,530,null);
            }
            else{
                g.drawImage(mainImg, 220,530,null);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e){}

    @Override
    public void mouseEntered(MouseEvent e){}

    @Override
    public void mouseExited(MouseEvent e){}

    @Override
    public void mousePressed(MouseEvent e){
        if (mode == "classic" || mode == "timed") {
            ball.launch(e.getX(), e.getY());
        }

        else if (mode == "main") {
            if (classicRect.contains(e.getPoint())) {
                mode = "classic";
            }

            else if (timedRect.contains(e.getPoint())) {
                mode = "timed";
                gameTime = new Timer(300000, this);//5 minutes to do round
                gameTime.start();
            }
        }

        else if (mode == "high") {
            if (HSmainRect.contains(e.getPoint())) {
                mode = "main";//go back to main
            }
        }

        else if (gameEnd.isGameOver()) {
            if (mainRect.contains(e.getPoint())) {
                mode = "main";//go back to main
                gameEnd.reset();
            } else if (highRect.contains(e.getPoint())) {
                mode = "high";//display high scores mode
                gameEnd.reset();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e){}

    @Override
    public void actionPerformed(ActionEvent evt){
        if(mode == "timed") {
            if (evt.getSource() == gameTime) {//if time goes up then user loses life and has to start from beginning
                gameEnd.lose(score);
                mode = "gameOver";
                time.restart();
            }
        }
        updateGame();
        repaint();   // Asks the JVM to indirectly call paint
    }

}