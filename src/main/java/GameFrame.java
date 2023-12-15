import javax.swing.*;
public class GameFrame extends JFrame {
//JFrame is the container for the application, represents the game window being used.
    GameFrame() {

        //Create an instance of GamePanel, represents the main panel where the game is played.
        GamePanel panel = new GamePanel();

        //Adds the game panel to the game frame
        this.add(panel);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Can't change the size of the window
        this.setResizable(false);
        //This means that the window is sized so that the game panel fits correctly
        this.pack();
        this.setVisible(true);
        //Centers the frame on screen
        this.setLocationRelativeTo(null);
    }
}
