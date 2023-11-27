import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

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
            Servidor servidor = (Servidor) Naming.lookup("rmi://localhost/Servidor");
        } catch (Exception e){
            System.out.println("Error en el cliente: " + e.toString());
        }
    }

}
