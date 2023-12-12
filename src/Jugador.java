import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
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
            //Iniciamos el registro RMI en el puerto 1096
            LocateRegistry.createRegistry(1096);

            //Creamos una instancia de la base de datos e indicamos su url
            CallbackJugadorInterface cBack = new CallbackJugadorImpl();
            Naming.rebind("cBack", cBack);

            System.out.println("Servicio CallBack listo.");
        } catch (Exception e) {
            System.err.println("Error en el servicio CallBack: " + e);
        }
    }

}
