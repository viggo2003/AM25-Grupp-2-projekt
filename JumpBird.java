import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JumpBird extends JPanel implements ActionListener, KeyListener {
    private int birdY = 300, birdX = 200, velocity = 0, gravity = 2;
    private boolean isRunning = false;
    private Timer timer;
    private JButton startButton;

    public JumpBird(JFrame frame) {
        setBackground(new Color(100, 120, 150));
        setLayout(null);
        setFocusable(true);
        addKeyListener(this);

        // Create and configure start button
        startButton = new JButton("Start");
        startButton.setBounds(325, 350, 150, 50);
        startButton.addActionListener(this);
        add(startButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            System.out.println("Startknappen klickades!");
            startButton.setVisible(false); // Hide button after starting game
            requestFocusInWindow(); // Ensure key events are captured
            startGame();
        }
    }

    private void startGame() {
        isRunning = true;
        timer = new Timer(20, e -> gameLoop());
        timer.start();
    }

    private void gameLoop() {
        velocity += gravity;
        birdY += velocity;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.YELLOW);
        g.fillOval(birdX, birdY, 30, 30); // Draw bird
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && isRunning) {
            velocity = -10; // Jump effect
            System.out.println("Space tryckt!");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Jumpy Birb");
        JumpBird game = new JumpBird(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(game);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }
}
