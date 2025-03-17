import javax.swing.*;

import java.awt.Color;
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

        // Skapa knappen innan den används
        button = new JButton("Start");
        
        // Ställ in egenskaper för knappen
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

            panel.requestFocusInWindow();
        }
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
            panel.setBackground(new Color(200));
        
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

    public static void startGame(JPanel panel) {

        panel.removeAll();
        panel.revalidate();
        panel.repaint();
        panel.setBackground(new Color(200));
    }
}
