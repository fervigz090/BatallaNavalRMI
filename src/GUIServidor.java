import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class GUIServidor extends JFrame {

    private ServicioAutenticacionInterface servicioAutenticacion;
    private ServicioGestorInterface servicioGestor;
    private ServicioDatosInterface servicioDatos;
    private JTextArea textArea;

    public GUIServidor() {
        setTitle("Menú del Servidor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textArea = new JTextArea(10, 40);
        textArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1));

        JButton btnInfoServidor = new JButton("Información del servidor");
        JButton btnEstadoPartidas = new JButton("Estado de las partidas actuales");
        JButton btnSalir = new JButton("Salir");

        panelBotones.add(btnInfoServidor);
        panelBotones.add(btnEstadoPartidas);
        panelBotones.add(btnSalir);

        add(scrollPane, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);

        try {
            servicioAutenticacion = (ServicioAutenticacionInterface) Naming.lookup("rmi://localhost/servicioAutenticacion");
        } catch (Exception e) {
            System.err.println("Error en referencia al servicio de autenticación: " + e.toString());
            textArea.append("Error conectando al servicio de autenticación.\n");
        }

        try {
            servicioGestor = (ServicioGestorInterface) Naming.lookup("rmi://localhost/servicioGestor");
        } catch (Exception e) {
            System.err.println("Error en referencia al servicio gestor: " + e.toString());
            textArea.append("Error conectando al servicio gestor.\n");
        }

        try {
            servicioDatos = (ServicioDatosInterface) Naming.lookup("rmi://localhost/servicioDatos");
        } catch (Exception e) {
            System.err.println("Error en referencia al servicio de datos: " + e.toString());
            textArea.append("Error conectando al servicio de datos");
        }

        btnInfoServidor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
                textArea.append("Información del servidor\n\n");
                // Formato de la cabecera y las filas de la tabla
                String headerFormat = "%-20s %-30s %-10s %n";
                String rowFormat = "%-20s %-30s %-10s %n";

                // Encabezado de la tabla
                textArea.append(String.format(headerFormat, "Servicio", "Nombre", "Puerto\n"));

                // Filas de datos
                textArea.append(String.format(rowFormat, "Autenticacion", "servicioAutenticacion", "1097"));
                textArea.append(String.format(rowFormat, "Gestor", "servicioGestor", "1095"));

            }
        });

        btnEstadoPartidas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Partida> list = null;
                String estado = "";
                try {
                    list = servicioDatos.listaPartidas();
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
                textArea.setText("");
                textArea.append("Lista de partidas\n");
                String nombre = "null";
                // Formato de la cabecera y las filas de la tabla
                String headerFormat = "%-20s %-15s %-15s %-15s %n";
                String rowFormat = "%-20d %-15s %-15s %-15s %n";
                // Encabezado de la tabla
                textArea.append(String.format(headerFormat, "Identificador", "Jugador1", "Jugador2","Estado\n"));

                for (int i=0; i<list.size(); i++){
                    estado = String.valueOf(list.get(i).getEstadoActual());
                    estado = estado.toLowerCase();
                    if (!estado.equals("en_espera")){
                        nombre = list.get(i).getJugador2().getName();
                    }
                    // Filas de datos
                    textArea.append(String.format(rowFormat, list.get(i).getId(),
                                                             list.get(i).getJugador1().getName(),
                                                             nombre,
                                                             estado));
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

