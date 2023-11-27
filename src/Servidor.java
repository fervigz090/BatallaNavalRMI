import java.rmi.Naming;

public class Servidor {
    public static void main(String[] args) {
        try {
            // Obtener referencia al servicio de la base de datos remota
            ServicioDatosInterface servicioDatos = (ServicioDatosInterface) Naming.lookup("rmi://localhost/servicioDatos");

            // Test Servicio de Datos
            // CRUD Jugadores
            System.out.println("Tamaño de la base de datos: " + servicioDatos.size());
            servicioDatos.setUser("Pepe", "contrasena");
            System.out.println("Tamaño de la base de datos: " + servicioDatos.size());
            System.out.println("contrasena de 'Pepe': " + servicioDatos.getPass("Pepe"));
            System.out.println("Eliminamos el jugador Pepe");
            servicioDatos.deleteUser("Pepe");
            System.out.println("Tamaño de la base de datos: " + servicioDatos.size());

            // CRUD Partidas
            System.out.println("Numero de partidas creadas: " + servicioDatos.numeroPartidas());
            System.out.println("Creamos dos jugadores y una partida nueva..");
            servicioDatos.setUser("Alejandro", "1234");
            servicioDatos.setUser("Maria", "5677");


            System.out.println("Visualizamos a continuacion el tablero:");
            System.out.println(servicioDatos.getPartida(0).getTablero().toString());

            // Test

        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}

