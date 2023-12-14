import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIServidor extends JFrame {

    public GUIServidor() {
        setTitle("Menú del Servidor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        JButton btnInfoServidor = new JButton("Información del servidor");
        JButton btnEstadoPartidas = new JButton("Estado de las partidas actuales");
        JButton btnSalir = new JButton("Salir");

        add(btnInfoServidor);
        add(btnEstadoPartidas);
        add(btnSalir);

        btnInfoServidor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para el botón "Información del servidor"
                JOptionPane.showMessageDialog(null, "Mostrar información del servidor");
            }
        });

        btnEstadoPartidas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para el botón "Estado de las partidas actuales"
                JOptionPane.showMessageDialog(null, "Mostrar estado de las partidas actuales");
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
        SwingUtilities.invokeLater(() -> new GUIServidor());
    }
}

