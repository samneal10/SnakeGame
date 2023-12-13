import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
