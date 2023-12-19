import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;

public class GUIBaseDeDatos extends JFrame {

    private ServicioDatosInterface servicioDatos;
    private JTextArea textArea;

    public GUIBaseDeDatos() {
        setTitle("Menú de Base de Datos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1)); // Cambiado a 4 para incluir el JTextArea


        JButton btnInfoBD = new JButton("Información de la base de datos");
        JButton btnListarJugadores = new JButton("Listar jugadores registrados (Puntuaciones)");
        JButton btnSalir = new JButton("Salir");
        textArea = new JTextArea();
        textArea = new JTextArea(10, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1));

        add(scrollPane);
        add(btnInfoBD);
        add(btnListarJugadores);
        add(btnSalir);

        try {
            servicioDatos = (ServicioDatosInterface) Naming.lookup("rmi://localhost/servicioDatos");
        } catch (Exception e) {
            System.err.println("Error en referencia al servicio de datos: " + e.toString());
            textArea.append("Error conectando al servicio de datos.\n");
        }

        btnInfoBD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setText(""); // Limpia area de texto antes de mostrar mas datos
                try {
                    int jugadores = servicioDatos.size();
                    int partidas = servicioDatos.numeroPartidas();
                    textArea.append("Numero de jugadores registrados: " + jugadores + "\n");
                    textArea.append("Numero de partidas: " + partidas + "\n");
                } catch (Exception ex) {
                    textArea.append("Error al obtener la información de la base de datos.\n");
                }
            }
        });

        btnListarJugadores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Listar jugadores registrados (Puntuaciones)");
            }
        });

        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setSize(500, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

}


