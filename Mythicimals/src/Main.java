import javax.swing.*;

/**
 * The Main class serves as the entry point of the program. The JFrame is created here, which will be the window for the program that the user interacts with.
 */
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Mythicimals");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setResizable(false);

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);

        frame.setVisible(true);
    }
}
