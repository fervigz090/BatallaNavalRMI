import javax.swing.*;


import java.io.Serializable;
import java.net.ServerSocket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Jugador implements Serializable {
    private String name;
    private String password;
    private int Puntuacion = 0;
    private static int port = 0;
    private final String[] coordenadasAtaque;
    private ServicioAutenticacionInterface servicioAutenticacion;
    private ServicioGestorInterface servicioGestor;

    public Jugador (String name, String password){
        this.name = name;
        this.password = password;
        this.coordenadasAtaque = new String[1];
        try {
            servicioAutenticacion = (ServicioAutenticacionInterface) Naming.lookup("rmi://localhost/servicioAutenticacion");
            servicioGestor = (ServicioGestorInterface) Naming.lookup("rmi://localhost/servicioGestor");
        } catch (Exception e) {
            e.printStackTrace(); // Manejar adecuadamente la excepción
        }

        //Levanta el servicio CallBackJugador
        try {
            // Buscamos un puerto libre para el servicio
            int puertoLibre = encontrarPuertoLibre();

            // Asigna el puerto
            LocateRegistry.createRegistry(puertoLibre);

            //Usamos el nombre de jugador como identificador para cada instancia de CallBack
            CallbackJugadorInterface cBack = new CallbackJugadorImpl();
            cBack.setJugador(this);
            Naming.rebind("rmi://localhost/cBackJugador/" + this.name, cBack);

            System.out.println("Servicio CallBack listo.");
        } catch (Exception e) {
            System.err.println("Error en el servicio CallBack: " + e);
        }

    }

    public static int encontrarPuertoLibre() {
        try (ServerSocket socket = new ServerSocket(0)){
            port = socket.getLocalPort();
            return port;
        } catch (Exception e) {
            throw new RuntimeException("No se ha encontrado un puerto libre: " + e);
        }
    }

    public boolean registrar() {
        try {
            servicioAutenticacion.registrar(this);
            return true; // Registro exitoso
        } catch (Exception e) {
            return false; // Manejo de error
        }
    }

    public boolean iniciarSesion() {
        try {
            return servicioAutenticacion.login(name, password);
        } catch (Exception e) {
            return false; // Manejo de error
        }
    }

    public Partida unirsePartida (int idPartida) throws RemoteException {
        return servicioGestor.asignarJugador2(idPartida, this);
    }

    public Partida esperarContrincante(Partida p){

        System.out.println("Esperando contrincante...");

        while (p.getJugador2() == null){
            try {
                Thread.sleep(1000); // Espera 1 segundo
                p = actualizarPartida(p.getId());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restablece el estado de interrupción
                System.out.println("La espera fue interrumpida");
                return p;
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        return p;
    }

    public Partida actualizarPartida(int idPartida) throws RemoteException {
        return servicioGestor.obtenerPartida(idPartida);
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public String getCoordenadas() {
        return coordenadasAtaque[0];
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadasAtaque[0] = coordenadas;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPuntuacion() {
        return Puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        Puntuacion = puntuacion;
    }

    public boolean colocarBarcos(Partida p, StringBuilder st) {
        Tablero tablero = new Tablero(10, 10);
        char jugador;
        if (p.getJugador1().getName().equals(this.name)) {
            jugador = '1';
        } else {
            jugador = '2';
        }
    
        switch (jugador) {
            case '1':
                p.setTablero1(tablero);
                break;
            case '2':
                p.setTablero2(tablero);
                break;
        }
    
        // Colocar los barcos basados en las coordenadas proporcionadas
        for (int j = 0; j < 2; j++){
            int x = 0;
            int filaIndex = st.charAt(x) - 'A' + 1 - 1;
            int columnaIndex = Character.getNumericValue(st.charAt(x + 1)) - 1;
            char orientacion = st.charAt(x + 2);
            st.delete(0, 4);

            // Colocar barco horizontal
            if (orientacion == 'H'){
                for (int i = 0; i < 3; i++){
                    if (!tablero.establecerCasilla(filaIndex, columnaIndex + i, 'O')) {
                        return false; // Si la colocación falla, retornar false
                    }
                }
            // Colocar barco vertical
            } else if (orientacion == 'V'){
                for (int i = 0; i < 3; i++){
                    if (!tablero.establecerCasilla(filaIndex + i, columnaIndex, 'O')) {
                        return false; // Si la colocación falla, retornar false
                    }   
                }
            }
        }
        p.getTablero1().setListo(true);
        p.getTablero2().setListo(true);
        
        try {
            servicioGestor.actualizarTablero(p, tablero, jugador);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("Tablero en clase jugador(1): " + name + ": " + p.getTablero1().mostrarTablero());
        System.out.println("Tablero en clase jugador(2): " + name + ": " + p.getTablero2().mostrarTablero());
        return true;
    }

    public void iniciarPartida(Partida p) {
        p.set_en_curso();
        try {
            servicioGestor.Rondas(p);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new GUIJugador());

    }

}
