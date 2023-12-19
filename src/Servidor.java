import javax.swing.*;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Servidor {
    public static void main(String[] args) {

        try {
            //Iniciamos el registro RMI en el puerto 1098
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

        //Levanta el servicio CallBackJugador
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

        try {
            // Obtener referencia al servicio de la base de datos remota
            ServicioDatosInterface servicioDatos = (ServicioDatosInterface) Naming.lookup("rmi://localhost/servicioDatos");

            // referencia al servicio gestor
            ServicioGestorInterface servicioGestor = (ServicioGestorInterface) Naming.lookup("rmi://localhost/servicioGestor");

            // referencia al servicio de autenticación.
            ServicioAutenticacionInterface servicioAutenticacion = (ServicioAutenticacionInterface) Naming.lookup("rmi://localhost/servicioAutenticacion");

            // referencia al servicio de comunicacion con el cliente
            CallbackJugadorInterface cBack = (CallbackJugadorInterface) Naming.lookup("rmi://localhost/cBack");

            // Lanzamiento de GUI
            SwingUtilities.invokeLater(() -> new GUIServidor());


            /* TEST */
            // registro de jugadores
            System.out.println("""
                    Registramos 2 jugadores  \s
                    Maria - 1234  \s
                    Rafa - 5678""");
            Jugador j1 = new Jugador("Maria", "1234");
            Jugador j2 = new Jugador("Rafa", "5678");
            servicioAutenticacion.registrar(j1);
            servicioAutenticacion.registrar(j2);

            System.out.println("Comprobamos que estan en la base de datos");
            System.out.println(servicioDatos.existe("Maria"));
            System.out.println(servicioDatos.existe("Rafa"));

            servicioDatos.listaJugadores();

        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}

