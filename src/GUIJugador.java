import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class GUIJugador extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;

    private JPanel panelNoAutenticado;
    private JPanel panelAutenticado;
    private JPanel panelJugarPartida;
    private JPanel panelColocarBarcos;
    private JLabel labelEsperaContrincante;


    private JButton btnRegistrar;
    private JButton btnLogin;
    private JButton btnSalirNoAutenticado;
    private JButton btnInfoJugador;
    private JButton btnIniciarPartida;
    private JButton btnListarPartidas;
    private JButton btnUnirsePartida;
    private JButton btnSalirAutenticado;
    private JButton btnPausa;

    private JTextArea textArea;

    private Jugador jugador;
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

        // Paneles partida
        panelJugarPartida = new JPanel(new BorderLayout());

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

        btnIniciarPartida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Partida p;
                StringBuilder sb = new StringBuilder();
                textArea.setText("");
                try {
                    p = servicioGestor.iniciarPartida(jugador, servicioGestor.crearTablero(), servicioGestor.crearTablero());
                    sb.append(servicioGestor.obtenerTablero(p, jugador.getName()));
                    textArea.append(sb.toString());
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
                
                p = esperarContrincante(p);

                mostrarPanelColocarBarcos(p);
            }
        });

        btnListarPartidas.addActionListener(e -> {
            try {
                ArrayList<Partida> l = servicioGestor.listarPartidas();
                textArea.setText("");
                textArea.append("Información del servidor\n\n");
                // Formato de la cabecera y las filas de la tabla
                String headerFormat = "%-20s %-15s %-15s %-15s %n";
                String rowFormat = "%-20s %-15s %-15s %-15s %n";
                String estado;
                String nombre = "null";

                // Encabezado de la tabla
                textArea.append(String.format(headerFormat, "Identificador", "Jugador1", "Jugador2","Estado\n"));

                // Filas de datos
                for (int i=0; i<l.size(); i++) {
                    estado = String.valueOf(l.get(i).getEstadoActual());
                    estado = estado.toLowerCase();
                    if (!estado.equals("en_espera")){
                        nombre = l.get(i).getJugador2().getName();
                    }
                    textArea.append(String.format(rowFormat, l.get(i).getId(),
                                                  l.get(i).getJugador1().getName(),
                                                  nombre,
                                                  estado));
                }

            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }

        });

        btnUnirsePartida.addActionListener(e -> {
            String idPartida = JOptionPane.showInputDialog(this, "Ingrese id partida:");
            Partida p;
            textArea.setText("");
            int id = Integer.parseInt(idPartida);
            try {
                p = jugador.unirsePartida(id);
                mostrarPanelColocarBarcos(p);
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
        cardPanel.add(panelJugarPartida, "Jugar partida");
    }

    // Métodos para cambiar entre paneles
    public void mostrarPanelNoAutenticado() {
        cardLayout.show(cardPanel, "NoAutenticado");
    }

    public void mostrarPanelAutenticado() {
        setTitle(jugador.getName() + " - Batalla Naval");
        cardLayout.show(cardPanel, "Autenticado");
    }

    public void mostrarPanelColocarBarcos(Partida p) {
        panelColocarBarcos = new JPanel(new GridLayout(2, 1));
        panelColocarBarcos.add(textArea, BorderLayout.NORTH);
        cardPanel.add(panelColocarBarcos, "Colocar barcos");
        cardLayout.show(cardPanel, "Colocar barcos");
        StringBuilder stCoordenadas = new StringBuilder();
        String coordenadasBarco;

        for(int i=0; i<2; i++) {
            coordenadasBarco = JOptionPane.showInputDialog(this, "Introduzca:" +
                    " - fila de la proa (A-J)" +
                    " - columna de la proa (1-10)" +
                    " - orienzacion (V/H)");

            stCoordenadas.append(coordenadasBarco);
            stCoordenadas.append("\n");
        }
        if (jugador.colocarBarcos(p, stCoordenadas)){
            JOptionPane.showMessageDialog(this, "Barcos colocados correctamente", "Colocar barcos", JOptionPane.INFORMATION_MESSAGE);
            mostrarPanelPartida(p);
        } else {
            JOptionPane.showMessageDialog(this, "Error al colocar los barcos", "Colocar barcos", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void mostrarPanelPartida(Partida p) {
        labelEsperaContrincante = new JLabel("A la espera de contrincante...", SwingConstants.CENTER);
        JTextArea textAreaPartida = new JTextArea();
        JScrollPane scrollPanelJP = new JScrollPane(textAreaPartida);
        btnPausa = new JButton("Pausar partida");
        panelJugarPartida.add(scrollPanelJP, BorderLayout.CENTER);
        panelJugarPartida.add(textAreaPartida, BorderLayout.CENTER);
        panelJugarPartida.add(btnPausa, BorderLayout.PAGE_END);
        textAreaPartida.setEditable(false);
        textAreaPartida.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textAreaPartida.setBackground(new Color (42, 42, 42));
        textAreaPartida.setForeground(new Color(145, 248, 160));
        textAreaPartida.setFont(new Font("Courier New", Font.BOLD, 32));
        textAreaPartida.setText("");
        if (p.getJugador1().getName().equals(jugador.getName())){
            textAreaPartida.append(p.getTablero1().mostrarTablero().toString());
        } else {
            textAreaPartida.append(p.getTablero2().mostrarTablero().toString());
        }
        cardLayout.show(cardPanel, "Jugar partida");
    }

    private void realizarRegistro() {
        String pass = "";
        String nombre = JOptionPane.showInputDialog(this, "Ingrese su nombre:");
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
            "Ingrese su contraseña:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            char[] password = passwordField.getPassword();
            pass = new String(password);
        } else {
            System.out.println("Login cancelado");
        }

        Jugador jugador = new Jugador(nombre, pass);
        if (jugador.registrar()) {
            JOptionPane.showMessageDialog(this, "Registro exitoso.", "Registro", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void realizarLogin() {
        String pass = "";
        String nombre = JOptionPane.showInputDialog(this, "Ingrese su nombre:");
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
            "Ingrese su contraseña", passwordField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            char[] password = passwordField.getPassword();
            pass = new String(password);
        } else {
            System.out.println("Login cancelado");
        }

        Jugador jugador = new Jugador(nombre, pass);
        if (jugador.iniciarSesion()) {
            this.jugador = jugador;
            JOptionPane.showMessageDialog(this, "Login exitoso.", "Login", JOptionPane.INFORMATION_MESSAGE);
            mostrarPanelAutenticado();
        } else {
            JOptionPane.showMessageDialog(this, "Login fallido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Partida esperarContrincante(Partida p) {

        jugador.esperarContrincante(p);

        SwingUtilities.invokeLater(() -> {
            labelEsperaContrincante.setText("¡Contrincante encontrado! Hora de colocar los barcos...");
            try {
                Thread.sleep(3000); // Espera 3 segundo
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return p;
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
