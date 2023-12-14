import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIBaseDeDatos extends JFrame {

    public GUIBaseDeDatos() {
        setTitle("Menú de Base de Datos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        JButton btnInfoBD = new JButton("Información de la base de datos");
        JButton btnListarJugadores = new JButton("Listar jugadores registrados (Puntuaciones)");
        JButton btnSalir = new JButton("Salir");

        add(btnInfoBD);
        add(btnListarJugadores);
        add(btnSalir);

        btnInfoBD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para el botón "Información de la base de datos"
                JOptionPane.showMessageDialog(null, "Mostrar información de la base de datos");
            }
        });

        btnListarJugadores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para el botón "Listar jugadores registrados (Puntuaciones)"
                JOptionPane.showMessageDialog(null, "Listar jugadores registrados (Puntuaciones)");
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

}

