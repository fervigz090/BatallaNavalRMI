import javax.swing.*;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Servidor {
    public static void main(String[] args) {

        try {
            //Iniciamos el registro RMI en el puerto 1095
            LocateRegistry.createRegistry(1095);

            //Creamos una instancia del servicio gestor e indicamos su url
            ServicioGestorInterface servicioGestor = new ServicioGestorImpl();
            Naming.rebind("servicioGestor", servicioGestor);

            System.out.println("Servicio Gestor listo.");
        } catch (Exception e) {
            System.err.println("Error en el servicio gestor: " + e);
        }

        try {
            //Iniciamos el registro RMI en el puerto 1097
            LocateRegistry.createRegistry(1097);

            //Creamos una instancia del servicio de autenticacion e indicamos su url
            ServicioAutenticacionInterface servicioAutenticacion = new ServicioAutenticacionImpl();
            Naming.rebind("servicioAutenticacion", servicioAutenticacion);

            System.out.println("Servicio Autenticacion listo.");
        } catch (Exception e) {
            System.err.println("Error en el servicio de autenticacion: " + e);
        }

        try {
            // Obtener referencia al servicio de la base de datos remota
            ServicioDatosInterface servicioDatos = (ServicioDatosInterface) Naming.lookup("rmi://localhost/servicioDatos");

            // referencia al servicio gestor
            ServicioGestorInterface servicioGestor = (ServicioGestorInterface) Naming.lookup("rmi://localhost/servicioGestor");

            // referencia al servicio de autenticación.
            ServicioAutenticacionInterface servicioAutenticacion = (ServicioAutenticacionInterface) Naming.lookup("rmi://localhost/servicioAutenticacion");

            // Lanzamiento de GUI
            SwingUtilities.invokeLater(() -> new GUIServidor());

        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}

