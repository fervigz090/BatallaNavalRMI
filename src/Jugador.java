import javax.swing.*;
import java.io.Serializable;
import java.net.ServerSocket;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Jugador implements Serializable {
    private String name;
    private String password;
    private int Puntuacion = 0;
    private ServicioAutenticacionInterface servicioAutenticacion;

    public Jugador (String name, String password){
        this.name = name;
        this.password = password;
        try {
            servicioAutenticacion = (ServicioAutenticacionInterface) Naming.lookup("rmi://localhost/servicioAutenticacion");
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
            Naming.rebind("rmi://localhost/cBackJugador" + this.name, cBack);

            System.out.println("Servicio CallBack listo.");
        } catch (Exception e) {
            System.err.println("Error en el servicio CallBack: " + e);
        }

    }

    public static int encontrarPuertoLibre() {
        try (ServerSocket socket = new ServerSocket(0)){
            return socket.getLocalPort();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
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

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new GUIJugador());

    }

}
