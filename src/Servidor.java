import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Servidor {
    public static void main(String[] args) {

        try {
            //Iniciamos el registro RMI en el puerto 1098
            LocateRegistry.createRegistry(1098);

            //Creamos una instancia de la base de datos e indicamos su url
            ServicioGestorInterface servicioGestor = new ServicioGestorImpl();
            Naming.rebind("servicioGestor", servicioGestor);

            System.out.println("Servicio Gestor listo.");
        } catch (Exception e) {
            System.err.println("Error en el servicio gestor: " + e);
        }

        try {
            //Iniciamos el registro RMI en el puerto 1097
            LocateRegistry.createRegistry(1097);

            //Creamos una instancia de la base de datos e indicamos su url
            ServicioAutenticacionInterface servicioAutenticacion = new ServicioAutenticacionImpl();
            Naming.rebind("servicioAutenticacion", servicioAutenticacion);

            System.out.println("Servicio Autenticacion listo.");
        } catch (Exception e) {
            System.err.println("Error en el servicio de autenticacion: " + e);
        }

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


            /* TEST */
            // registro de jugadores
            System.out.println("Registramos 2 jugadores" +
                    "   Maria - 1234" +
                    "   Rafa - 5678");
            servicioAutenticacion.registrar("Maria", "1234");
            servicioAutenticacion.registrar("Rafa", "5678");

            System.out.println("Comprobamos que estan en la base de datos");
            System.out.println(servicioDatos.existe("Maria"));
            System.out.println(servicioDatos.existe("Rafa"));

            System.out.println("Creamos una partida con estos dos jugadores");
//            servicioDatos.setPartida(new Jugador("Maria", "1234"),
//                                     new Jugador("Rafa", "5678"),
//                                     servicioGestor.crearTablero(),
//                                     servicioGestor.crearTablero());


        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}

