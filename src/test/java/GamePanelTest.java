import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class GamePanelTest {
    private GamePanel gamePanel;

    @BeforeEach
    public void setUp() {
        gamePanel = new GamePanel();
    }

    @Test
    public void testAppleEatenIncreasesBodyParts() {
        int initialBodyParts = gamePanel.getBodyParts();
        int initialApplesEaten = gamePanel.getApplesEaten();

        gamePanel.setApplesEaten(initialApplesEaten + 1);
        gamePanel.checkApple();

        int newBodyParts = gamePanel.getBodyParts();
        assertEquals(initialBodyParts + 1, newBodyParts);
    }

    @Test
    public void testNewAppleNotOnSnake() {
        gamePanel.setX(new int[]{0, 25, 50});
        gamePanel.setY(new int[]{0, 0, 0});

        gamePanel.newApple();

        assertFalse(gamePanel.appleIsOnSnake());
    }

    @Test
    public void testAppleIsOnSnake() {
        gamePanel.setX(new int[]{0, 25, 50});
        gamePanel.setY(new int[]{0, 0, 0});

        gamePanel.setAppleX(25);
        gamePanel.setAppleY(0);

        assertTrue(gamePanel.appleIsOnSnake());
    }
}
