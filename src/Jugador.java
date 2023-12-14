import javax.swing.*;
import java.rmi.Naming;

public class Jugador {
    private String name;
    private String password;

    public Jugador (String name, String password){
        this.name = name;
        this.password = password;
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

    public static void main(String[] args) {



        try {
            //Busca el objeto remoto en el servidor
            ServicioAutenticacionImpl servicioAutenticacion = (ServicioAutenticacionImpl) Naming.lookup("rmi://localhost/servicioAutenticacion");

        } catch (Exception e) {
            System.err.println("Error de comunicacion con el servidor: " + e.toString());
        }

        SwingUtilities.invokeLater(() -> new GUIJugador());


    }

}
