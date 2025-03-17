import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;


public class Start {
    JFrame frame = new JFrame("Jumpy Birb");
    JPanel panel = new JPanel();

    public Start() {
        int width = 800, height = 600;
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        panel.setBackground(new Color(100, 120, 150)); // Bakgrundsfärg
        panel.setFocusable(true);

        // Skapa en instans av Button och koppla den till frame och panel
        new Button(frame, panel);

        frame.add(panel);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new Start();
    }
}
