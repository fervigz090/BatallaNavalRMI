import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class GUIJugador extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;

    private JPanel panelNoAutenticado;
    private JPanel panelAutenticado;

    private JButton btnRegistrar;
    private JButton btnLogin;
    private JButton btnSalirNoAutenticado;

    private JButton btnInfoJugador;
    private JButton btnIniciarPartida;
    private JButton btnListarPartidas;
    private JButton btnUnirsePartida;
    private JButton btnSalirAutenticado;

    private JTextArea textArea;

    private Jugador jugador;
    private ServicioDatosInterface servicioDatos;
    private ServicioGestorInterface servicioGestor;

    public GUIJugador() {
        setTitle("Jugador - Cliente");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        inicializarComponentes();
        configurarPaneles();

        setContentPane(cardPanel);
        setLocationRelativeTo(null);
        setVisible(true);

        try {
            servicioDatos = (ServicioDatosInterface) Naming.lookup("rmi://localhost/servicioDatos");
        } catch (Exception e) {
            e.printStackTrace(); // Manejar adecuadamente la excepción
        }

        try {
            servicioGestor = (ServicioGestorInterface) Naming.lookup("rmi://localhost/servicioGestor");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void inicializarComponentes() {

        // Panel No Autenticado
        panelNoAutenticado = new JPanel(new GridLayout(3, 1));
        btnRegistrar = new JButton("Registrar un nuevo jugador");
        btnLogin = new JButton("Hacer login");
        btnSalirNoAutenticado = new JButton("Salir");
        // Establecer margenes
        Insets margins = new Insets(6, 12, 6, 12); // Ejemplo de márgenes
        btnRegistrar.setMargin(margins);
        btnLogin.setMargin(margins);
        btnSalirNoAutenticado.setMargin(margins);

        // Panel Autenticado
        panelAutenticado = new JPanel(new BorderLayout());
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPanel = new JScrollPane(textArea);

        JPanel botonPanel = new JPanel(new GridLayout(5, 1));
        btnInfoJugador = new JButton("Información del jugador");
        btnIniciarPartida = new JButton("Iniciar una partida");
        btnListarPartidas = new JButton("Listar partidas a la espera");
        btnUnirsePartida = new JButton("Unirse a una partida iniciada");
        btnSalirAutenticado = new JButton("Salir (Logout)");

        // Establecer márgenes
        btnInfoJugador.setMargin(margins);
        btnIniciarPartida.setMargin(margins);
        btnListarPartidas.setMargin(margins);
        btnUnirsePartida.setMargin(margins);
        btnSalirAutenticado.setMargin(margins);

        botonPanel.add(btnInfoJugador);
        botonPanel.add(btnIniciarPartida);
        botonPanel.add(btnListarPartidas);
        botonPanel.add(btnUnirsePartida);
        botonPanel.add(btnSalirAutenticado);

        panelAutenticado.add(scrollPanel, BorderLayout.CENTER);
        panelAutenticado.add(botonPanel, BorderLayout.PAGE_END);

        // ActionListeners para los botones
        btnSalirNoAutenticado.addActionListener(e -> System.exit(0));
        btnRegistrar.addActionListener(e -> realizarRegistro());
        btnLogin.addActionListener(e -> realizarLogin());

        btnInfoJugador.addActionListener(e -> {
            try {
                textArea.append(servicioGestor.obtenerPuntuacion(jugador.getName()).toString());
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });

        btnIniciarPartida.addActionListener(e -> {
            Partida p;
            StringBuilder sb = new StringBuilder();
            try {
                p = servicioGestor.iniciarPartida(jugador, servicioGestor.crearTablero(), servicioGestor.crearTablero());
                sb.append(servicioGestor.obtenerTablero(p, jugador.getName()));
                textArea.append(sb.toString());
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });

        btnSalirAutenticado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                   realizarLogout();
            }
        });



        // Configuracion de colores y fuentes
        Color backgroundColor = new Color(42, 42, 42);
        Color buttonColor = new Color(120, 120, 120);
        Color textColor = new Color(145, 248, 160);
        Font retroFont = new Font("Courier New", Font.PLAIN, 14);
        // Aplicar estilos retro a los componentes
        panelNoAutenticado.setBackground(backgroundColor);
        panelAutenticado.setBackground(backgroundColor);

        textArea.setBackground(backgroundColor);
        textArea.setForeground(textColor);
        textArea.setFont(retroFont);


    }

    private void configurarPaneles() {
        // Panel No Autenticado
        panelNoAutenticado.add(btnRegistrar);
        panelNoAutenticado.add(btnLogin);
        panelNoAutenticado.add(btnSalirNoAutenticado);

        cardPanel.add(panelNoAutenticado, "NoAutenticado");
        cardPanel.add(panelAutenticado, "Autenticado");

    }

    // Métodos para cambiar entre paneles
    public void mostrarPanelNoAutenticado() {
        cardLayout.show(cardPanel, "NoAutenticado");
    }

    public void mostrarPanelAutenticado() {
        cardLayout.show(cardPanel, "Autenticado");
    }

    private void realizarRegistro() {
        String nombre = JOptionPane.showInputDialog(this, "Ingrese su nombre:");
        String pass = JOptionPane.showInputDialog(this, "Ingrese su contraseña:");

        Jugador jugador = new Jugador(nombre, pass);
        if (jugador.registrar()) {
            JOptionPane.showMessageDialog(this, "Registro exitoso.", "Registro", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void realizarLogin() {
        String nombre = JOptionPane.showInputDialog(this, "Ingrese su nombre:");
        String pass = JOptionPane.showInputDialog(this, "Ingrese su contraseña:");

        Jugador jugador = new Jugador(nombre, pass);
        if (jugador.iniciarSesion()) {
            this.jugador = jugador;
            JOptionPane.showMessageDialog(this, "Login exitoso.", "Login", JOptionPane.INFORMATION_MESSAGE);
            mostrarPanelAutenticado();
        } else {
            JOptionPane.showMessageDialog(this, "Login fallido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarTableros(Tablero tableroJugador, Tablero tableroOponente) {
        textArea.setText("Tu Tablero:\n");
        textArea.append(tableroJugador.toString()); // Asumiendo que Tablero tiene un método toString()
        textArea.append("\nTablero del Oponente:\n");
        textArea.append(tableroOponente.toString()); // Similar para el tablero del oponente
    }

    private void realizarLogout() {
        // Limpiar o resetear el estado del usuario
        limpiarEstadoUsuario();
        // Cambiar la vista al panel de no autenticado
        mostrarPanelNoAutenticado();
    }

    private void limpiarEstadoUsuario() {
        // Limpia el JTextArea, resetea variables, etc.
        textArea.setText("");
        jugador = null;
    }


}
