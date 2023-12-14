import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIServidor extends JFrame {

    private JTextArea textArea;

    public GUIServidor() {
        setTitle("Menú del Servidor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textArea = new JTextArea(20, 40);
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

        btnInfoServidor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.append("Mostrando información del servidor...\n");
                // Aquí puedes agregar la lógica real para mostrar información del servidor
            }
        });

        btnEstadoPartidas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.append("Mostrando estado de las partidas actuales...\n");
                // Aquí puedes agregar la lógica real para mostrar estado de las partidas
            }
        });

        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setSize(700, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}

