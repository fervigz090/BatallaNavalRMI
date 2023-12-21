import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class GUIBaseDeDatos extends JFrame {

    private ServicioDatosInterface servicioDatos;
    private JTextArea textArea;

    public GUIBaseDeDatos() {
        setTitle("Menú de Base de Datos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); // Cambiado a 4 para incluir el JTextArea


        JButton btnInfoBD = new JButton("Información de la base de datos");
        JButton btnListarJugadores = new JButton("Listar jugadores registrados (Puntuaciones)");
        JButton btnSalir = new JButton("Salir");
        textArea = new JTextArea();
        textArea = new JTextArea(10, 40);
        textArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setSize(100, 200);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1));

        panelBotones.add(btnInfoBD);
        panelBotones.add(btnListarJugadores);
        panelBotones.add(btnSalir);

        add(scrollPane, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);

        try {
            servicioDatos = (ServicioDatosInterface) Naming.lookup("rmi://localhost/servicioDatos");
        } catch (Exception e) {
            System.err.println("Error en referencia al servicio de datos: " + e.toString());
            textArea.append("Error conectando al servicio de datos.\n");
        }

        btnInfoBD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
                textArea.append("Información de la base de datos\n\n");
                // Formato de la cabecera y las filas de la tabla
                String headerFormat = "%-20s %-30s %-10s %n";
                String rowFormat = "%-20s %-30s %-10s %n";

                // Encabezado de la tabla
                textArea.append(String.format(headerFormat, "Servicio", "Nombre", "Puerto\n"));

                // Filas de datos
                textArea.append(String.format(rowFormat, "Datos", "servicioDatos", "1099"));

            }
        });

        btnListarJugadores.addActionListener(new ActionListener() {
            ArrayList<Jugador> list = new ArrayList<>();
            public void actionPerformed(ActionEvent e) {
                try {
                    list = servicioDatos.listaJugadores();
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
                textArea.setText("");
                textArea.append("Lista de jugadores - puntuaciones\n\n");
                // Formato de la cabecera y las filas de la tabla
                String headerFormat = "%-20s %-30s %n";
                String rowFormat = "%-20s %-30d %n";

                // Encabezado de la tabla
                textArea.append(String.format(headerFormat, "Jugador", "Puntuación\n"));

                // Filas de datos
                for (int i=0; i<list.size(); i++) {
                    textArea.append(String.format(rowFormat, list.get(i).getName(), list.get(i).getPuntuacion()));
                }
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


