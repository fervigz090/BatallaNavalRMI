import java.rmi.Naming;

public class Servidor {
    public static void main(String[] args) {
        try {
            // Obtener referencia al servicio de la base de datos remota
            ServicioDatosInterface servicioDatos = (ServicioDatosInterface) Naming.lookup("rmi://localhost/servicioDatos");

            // Ahora puedes usar el servicio de base de datos
            // Por ejemplo, obtener el tamaño de la base de datos

            System.out.println("Tamaño de la base de datos: " + servicioDatos.size());

            servicioDatos.setUser("nombre", "contrasena");

            System.out.println("Tamaño de la base de datos: " + servicioDatos.size());

            System.out.println("contrasena de 'nombre': " + servicioDatos.getPass("nombre"));

            // Puedes realizar otras operaciones con el servicio de la base de datos aquí
            // servicioDatos.setUser(...);
            // servicioDatos.existe(...);
            // servicioDatos.getPass(...);
            // servicioDatos.deleteUser(...);

        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}

