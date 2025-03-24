import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagLayout;
import java.awt.Rectangle;

class App extends javax.swing.JFrame {

    public static void main(String[] args) {
        
        JFrame frame = new JFrame("Demo");
        frame.setLayout(new CardLayout());
        JTextField textField = new JTextField();
        frame.add(textField);
        frame.add(textField, BorderLayout.CENTER);
        frame.setSize(600, 1000);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    
        

    }
}