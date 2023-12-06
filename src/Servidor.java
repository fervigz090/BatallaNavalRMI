import java.rmi.Naming;

public class Servidor {
    public static void main(String[] args) {
        try {
            // Obtener referencia al servicio de la base de datos remota
            ServicioDatosInterface sD = (ServicioDatosInterface) Naming.lookup("rmi://localhost/sD");

            // referencia al servicio gestor
            ServicioGestorInterface sG = (ServicioGestorInterface) Naming.lookup("rmi://localhost/sG");

            // referencia al servicio de autenticación.
            ServicioAutenticacionInterface sA = (ServicioAutenticacionInterface) Naming.lookup("rmi://localhost/sA");

            // referencia al servicio de comunicacion con el cliente
            CallbackJugadorInterface cBack = (CallbackJugadorInterface) Naming.lookup("rmi://localhost/cBack");


            // Test Servicio de Datos
            // CRUD Jugadores
            System.out.println("Tamaño de la base de datos: " + sD.size());
            sD.setUser("Pepe", "contrasena");
            System.out.println("Tamaño de la base de datos: " + sD.size());
            System.out.println("contrasena de 'Pepe': " + sD.getPass("Pepe"));
            System.out.println("Eliminamos el jugador Pepe");
            sD.deleteUser("Pepe");
            System.out.println("Tamaño de la base de datos: " + sD.size());

            // CRUD Partidas
            System.out.println("Numero de partidas creadas: " + sD.numeroPartidas());
            System.out.println("Creamos dos jugadores y una partida nueva..");
            Jugador j1 = new Jugador("Alejandro", "1234");
            Jugador j2 = new Jugador("Maria", "5677");
            sD.setUser(j1.getName(), j1.getPassword());
            sD.setUser(j2.getName(), j2.getPassword());
            Partida partida = new Partida(j1, j2, sG.crearTablero(), sG.crearTablero());


            System.out.println("Visualizamos a continuacion el tablero del jugador 1:");
            partida.getTablero1().mostrarTablero();

            System.out.println("Visualizamos a continuacion el tablero del jugador 2:");
            partida.getTablero2().mostrarTablero();

            // Test

        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}

