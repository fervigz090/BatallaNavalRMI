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
            Jugador j1 = new Jugador("Alejandro", "1234");
            Jugador j2 = new Jugador("Maria", "5677");
            servicioDatos.setUser(j1.getName(), j1.getPassword());
            servicioDatos.setUser(j2.getName(), j2.getPassword());
            Partida partida = new Partida(j1, j2);


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

