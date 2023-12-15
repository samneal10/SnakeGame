import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GamePanelTest {
    private GamePanel gamePanel;

    @BeforeEach
    public void setUp() {
        gamePanel = new GamePanel();
    }

    @Test
    public void testRestartsGame() {

        // set initial conditions
        gamePanel.setApplesEaten(3);
        gamePanel.checkCollisions();

        gamePanel.restartGame();

        //asserting that it runs correctly after restart
        assertTrue(gamePanel.isRunning());
        assertEquals(6, gamePanel.getBodyParts());
        assertEquals(0, gamePanel.getApplesEaten());
    }


    @Test
    public void testAppleEatenIncreasesBodyParts() {
        // get initial
        int initialBodyParts = gamePanel.getBodyParts();
        int initialApplesEaten = gamePanel.getApplesEaten();

        //simulate eating an apple
        gamePanel.setApplesEaten(initialApplesEaten + 1);
        gamePanel.checkApple();

        //assert taht the body parts increased by 1
        int newBodyParts = gamePanel.getBodyParts();
        assertEquals(initialBodyParts + 1, newBodyParts);
    }

    @Test
    public void testNewAppleNotOnSnake() {
        //set snake coordinates
        gamePanel.setX(new int[]{0, 25, 50});
        gamePanel.setY(new int[]{0, 0, 0});

        //generate new apple
        gamePanel.newApple();

        //assert that the new apple is not on the snake
        assertFalse(gamePanel.appleIsOnSnake());
    }

    // testing that it can recognise when the apple is on the snake
    @Test
    public void testAppleIsOnSnake() {
        // set snake coordinates
        gamePanel.setX(new int[]{0, 25, 50});
        gamePanel.setY(new int[]{0, 0, 0});

        // set apple to overlap with snake
        gamePanel.setAppleX(25);
        gamePanel.setAppleY(0);

        // assert that the apple is on the snake.
        assertTrue(gamePanel.appleIsOnSnake());
    }
}
