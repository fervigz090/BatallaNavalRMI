import javax.swing.*;
import java.io.Serializable;
import java.rmi.Naming;

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
