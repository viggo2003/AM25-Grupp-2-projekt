import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Start {
    JFrame frame = new JFrame("Jumpy Birb");
    JPanel panel = new JPanel();

    public Start() {

        int width = 800, height = 600;
        int birdY = height / 2, birdX = 200, velocity = 0, gravity = 2;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JButton button = new JButton("Start");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(panel);

            }
        });

        panel.add(button); // Knapp för interaktion
        button.setBounds(width / 2 - 75, height / 2 + 20, 150, 50);

        panel.setBackground(new Color(100, 120, 150)); // Bakrundsfärg
        panel.setFocusable(true); // Panelens input som tex tangentbord

        frame.add(panel);
        frame.setVisible(true);
        frame.setSize(800, 600); // Fönstrets storlek, bredden och höjden
        frame.setResizable(false); // Användaren kan inte ändra storleken på fönstret
        frame.setLocationRelativeTo(null); // Spelet startar i mitten av skärmen

    }

    public static void startGame(JPanel panel) {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
        panel.setBackground(new Color(200));
        

    }

    public static void main(String[] args) {
        new Start();
    }
}
