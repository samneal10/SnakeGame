import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

//Class representing the game panel
public class GamePanel extends JPanel implements ActionListener {

    //Constants for screen dimensions and the game units
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    //the delay is effectively the speed of the game, it's how often the timer 'ticks' and teh graphics update
    static final int DELAY = 75;
    //Arrays which store the coordinates of the snake body
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    //Instance variables declared (body parts, apples, x and y coordinates, direction, game status)
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    //Timer for game updates
    Timer timer;
    //Random number generator for where the apple will be.
    Random random;

    //Play again button
    private JButton playAgainButton;

    //Constructor for the game panel class
    GamePanel() {

        //initialise the random number generator
        random = new Random();
        //Set up the properties of the panel
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        //created play again button
        playAgainButton = new JButton("Play Again");
        playAgainButton.setFocusable(false);
        playAgainButton.addActionListener(e -> restartGame());

        //Initially the play again button is false so that it doesn't display while game is being played.
        playAgainButton.setVisible(false);

        this.add(playAgainButton);
        //add to panel and start the game
        startGame();
    }

    public void startGame() {
        // initialise new apple, set running to true so game starts and start the timer
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        //checks if the game is running
        if (running) {
            // if it's running then draw the apple
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            // and draw the snake
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    //colour of the head.
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    //colour of the rest of the body, as the snake grows.
                    g.setColor(new Color(45, 180, 0));
                    // if i uncomment the line below then the snake will have random colour for each body part
                    //g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            //colour font, and sizing for the score
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 25));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
        } else {
            // if the game isn't running then the game over screen will be displayed
            gameOver(g);
        }
    }

    public void newApple() {
        do {
            //random coordinates are generated for the apple
            appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
            appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
        } while (appleIsOnSnake());
    }

    //this method was used for testing whether an apple could appear where the snake was
    public boolean appleIsOnSnake() {
        for (int i = 0; i < bodyParts; i++) {
            if (appleX == x[i] && appleY == y[i]) {
                return true;
            }
        }
        return false;
    }

    //moving the snake
    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            //updating the coordinates of each body part
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            // here the head of the snake is being moved based on the current direction
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    // this method checks whether the snake has eaten the apple
    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            // if the apple is eaten, then body parts gets increased, as does the number of apples eaten, and new apple is generated
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions() {
        // check if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        // check if head touches left border
        if (x[0] < 0) {
            running = false;
        }
        // check if head touches right border
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }
        // check if head touches top border
        if (y[0] < 0) {
            running = false;
        }
        // check if head touches bottom border
        if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }

        // if the game isn't running, the timer will be stopped, the play again button appears, and the graphics will be produced again
        if (!running) {
            timer.stop();
            playAgainButton.setVisible(true);
            repaint();
        }
    }

    public void gameOver(Graphics g) {
        // game over message to display
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 50));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over :(", (SCREEN_WIDTH - metrics2.stringWidth("Game Over :(")) / 2, SCREEN_HEIGHT / 3);
        // score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten)) / 2, SCREEN_HEIGHT / 2);
        // play again button
        playAgainButton.setBounds((SCREEN_WIDTH - playAgainButton.getPreferredSize().width) / 2, SCREEN_HEIGHT / 2 + metrics2.getHeight(), playAgainButton.getPreferredSize().width, playAgainButton.getPreferredSize().height);
        playAgainButton.setVisible(true);
    }


    public void restartGame() {
        //resetting all the parameters for a new game
        bodyParts = 6;
        applesEaten = 0;
        direction = 'R';
        running = false;

        setInitialSnakePosition();
        newApple();

        timer.restart();
        running = true;

        playAgainButton.setVisible(false);

        repaint();
    }

    private void setInitialSnakePosition() {
        //calculating initial coordinates of the snake
        int initialX = SCREEN_WIDTH / 2;
        int initialY = SCREEN_HEIGHT / 2;

        // setting coordinates for the body parts
        for (int i = 0; i < bodyParts; i++) {
            x[i] = initialX - i * UNIT_SIZE;
            y[i] = initialY;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // if the game is running, move snake, check for apples, check for collisions
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public int getBodyParts() {
        return bodyParts;
    }

    public int getApplesEaten() {
        return applesEaten;
    }

    public void setApplesEaten(int applesEaten) {
        // set apples eaten and update body parts accordingly
        this.applesEaten = applesEaten;
        this.bodyParts = applesEaten + 6;
    }

    // set x coordinate of snake
    public void setX(int[] newX) {
        System.arraycopy(newX, 0, x, 0, Math.min(newX.length, x.length));
    }

    // set y coordinate of snake
    public void setY(int[] newY) {
        System.arraycopy(newY, 0, y, 0, Math.min(newY.length, y.length));
    }

    // set x coordinate of apple
    public void setAppleX(int newAppleX) {
        appleX = newAppleX;
    }

    // set y coordinate of apploe
    public void setAppleY(int newAppleY) {
        appleY = newAppleY;
    }

    public boolean isRunning() {
        return running;
    }

    // this class handles the keyboard input
    public class myKeyAdapter extends KeyAdapter {
        //updates direction of the snake based on pressing arrow keys
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
