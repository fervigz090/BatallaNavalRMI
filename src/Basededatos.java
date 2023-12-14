import javax.swing.*;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * Servidor Base de datos
 */
public class Basededatos {

    public static void main(String[] args) {
        try {
            //Iniciamos el registro RMI en el puerto 1099
            LocateRegistry.createRegistry(1099);

            //Creamos una instancia de la base de datos e indicamos su url
            ServicioDatosInterface servicioDatos = new ServicioDatosImpl();
            Naming.rebind("servicioDatos", servicioDatos);

            System.out.println("Servidor de Base de datos listo.");

            SwingUtilities.invokeLater(() -> new GUIBaseDeDatos());

        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e);
        }
    }



}
