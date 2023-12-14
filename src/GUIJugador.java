import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIJugador extends JFrame {

    public GUIJugador() {
        setTitle("GUI Jugador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        JButton btnRegistrarse = new JButton("Registrarse");
        JButton btnLogin = new JButton("Login");
        JButton btnSalir = new JButton("Salir");

        add(btnRegistrarse);
        add(btnLogin);
        add(btnSalir);

        btnRegistrarse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para el botón "Registrarse"
                JOptionPane.showMessageDialog(null, "Implementar la lógica para Registrarse");
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para el botón "Login"
                JOptionPane.showMessageDialog(null, "Implementar la lógica para Login");
            }
        });

        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para el botón "Salir"
                System.exit(0);
            }
        });

        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUIJugador());
    }
}


