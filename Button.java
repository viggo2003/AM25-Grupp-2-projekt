import javax.swing.*;
import java.awt.event.*;

public class Button implements ActionListener, KeyListener {
    int width = 800, height = 600;
    int birdY = height / 2, birdX = 200, velocity = 0, gravity = 2;

    JPanel panel;
    JButton button;
    JFrame frame;

    public Button(JFrame frame, JPanel panel) {
        this.frame = frame;
        this.panel = panel;

        button = new JButton("Start");
        button.setBounds(width / 2 - 75, height / 2 + 20, 150, 50);
        button.addActionListener(this);

        panel.setLayout(null);
        panel.add(button);
        panel.addKeyListener(this);
        panel.setFocusable(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            System.out.println("Startknappen klickades!");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println("Space tryckt!");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
