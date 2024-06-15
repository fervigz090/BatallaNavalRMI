import javax.swing.*;


import java.io.Serializable;
import java.net.ServerSocket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Jugador implements Serializable {
    private String name;
    private String password;
    private int numJugador;
    private int Puntuacion = 0;
    private static int port = 0;
    private Tablero tablero;
    private Tablero tableroContrincante;
    private final String[] coordenadasAtaque;
    private ServicioAutenticacionInterface servicioAutenticacion;
    private ServicioGestorInterface servicioGestor;

    public Jugador (String name, String password){
        this.name = name;
        this.password = password;
        this.numJugador = 1;
        this.tablero = new Tablero(10, 10);
        this.tableroContrincante = new Tablero(10, 10);
        this.coordenadasAtaque = new String[1];
        try {
            servicioAutenticacion = (ServicioAutenticacionInterface) Naming.lookup("rmi://localhost/servicioAutenticacion");
            servicioGestor = (ServicioGestorInterface) Naming.lookup("rmi://localhost/servicioGestor");
        } catch (Exception e) {
            e.printStackTrace(); // Manejar adecuadamente la excepción
        }

        //Levanta el servicio CallBackJugador
        try {
            // Buscamos un puerto libre para el servicio
            int puertoLibre = encontrarPuertoLibre();

            // Asigna el puerto
            LocateRegistry.createRegistry(puertoLibre);

            //Usamos el nombre de jugador como identificador para cada instancia de CallBack
            CallbackJugadorInterface cBack = new CallbackJugadorImpl();
            cBack.setJugador(this);
            Naming.rebind("rmi://localhost/cBackJugador/" + this.name, cBack);

            System.out.println("Servicio CallBack listo.");
        } catch (Exception e) {
            System.err.println("Error en el servicio CallBack: " + e);
        }

    }

    public static int encontrarPuertoLibre() {
        try (ServerSocket socket = new ServerSocket(0)){
            port = socket.getLocalPort();
            return port;
        } catch (Exception e) {
            throw new RuntimeException("No se ha encontrado un puerto libre: " + e);
        }
    }

    public boolean registrar() {
        try {
            servicioAutenticacion.registrar(this);
            return true; // Registro exitoso
        } catch (Exception e) {
            return false; // Manejo de error
        }
    }

    public boolean iniciarSesion() {
        try {
            return servicioAutenticacion.login(name, password);
        } catch (Exception e) {
            return false; // Manejo de error
        }
    }

    public void unirsePartida (Partida p) throws RemoteException {
        this.setNumJugador(2);
        p.setJugador2(this);
        Jugador jugador1 = p.getJugador1();
        jugador1.setNumJugador(1);
        p.setJugador1(jugador1);
        servicioGestor.actualizarPartida(p.getId(), p);
    }

    public Jugador esperarContrincante(Partida p){

        System.out.println("Esperando contrincante...");

        while (p.getJugador2() == null){
            try {
                Thread.sleep(1000); // Espera 1 segundo
                p = actualizarPartida(p.getId());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restablece el estado de interrupción
                System.out.println("La espera fue interrumpida");
                return p.getJugador2();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        return p.getJugador2();
    }

    public Partida actualizarPartida(int idPartida) throws RemoteException {
        return servicioGestor.obtenerPartida(idPartida);
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public String getCoordenadas() {
        return coordenadasAtaque[0];
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadasAtaque[0] = coordenadas;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPuntuacion() {
        return Puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        Puntuacion = puntuacion;
    }

    public boolean colocarBarcos(Partida p, StringBuilder st) throws RemoteException {
        
        // Colocar los barcos basados en las coordenadas proporcionadas
        for (int j = 0; j < 2; j++){
            int x = 0;
            int filaIndex = st.charAt(x) - 'A' + 1 - 1;
            int columnaIndex = Character.getNumericValue(st.charAt(x + 1)) - 1;
            char orientacion = st.charAt(x + 2);
            st.delete(0, 4);

            // Colocar barco horizontal
            if (orientacion == 'H'){
                for (int i = 0; i < 3; i++){
                    if (!tablero.establecerCasilla(filaIndex, columnaIndex + i, 'O')) {
                        return false; // Si la colocación falla, retornar false
                    }
                }
            // Colocar barco vertical
            } else if (orientacion == 'V'){
                for (int i = 0; i < 3; i++){
                    if (!tablero.establecerCasilla(filaIndex + i, columnaIndex, 'O')) {
                        return false; // Si la colocación falla, retornar false
                    }   
                }
            }
        }

        System.out.println(this.getName() + this.getNumJugador() + " ha colocado sus barcos.");
        if (this.getNumJugador() == 1) {
            tablero.setListo(true);
            this.setTablero(tablero);
            p.setJugador1(this);
            p.setTablero1(this.tablero);
            servicioGestor.actualizarPartida(p.getId(), p);
        } else if (this.getNumJugador() == 2) {
            while (!p.getJugador1().getTablero().isListo() || !p.getJugador1().getTableroContrincante().isListo()){
                tablero.setListo(true);
                this.setTablero(tablero);
                Jugador jugador1 = servicioGestor.obtenerPartida(p.getId()).getJugador1();
                jugador1.setTableroContrincante(tablero);
                p.setJugador1(jugador1);
                p.setJugador2(this);
                p.setTablero2(tablero);
                servicioGestor.actualizarPartida(p.getId(), p);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    p.getJugador1().iniciarPartida(p);
                    System.out.println("Ejecutando sentencia en segundo plano...");
                }
            });
            thread.start();


            // Runnable tarea = new Runnable() {
            //     @Override
            //     public void run() {
            //         p.getJugador1().iniciarPartida(p);
            //         System.out.println("Ejecutando sentencia en segundo plano...");
            //     }
            // };
            // tarea.run();
            
            
        }

        System.out.println("Tableros listos: " + p.getTablero1().isListo() + " " + p.getTablero2().isListo());
        
        return true;
    }

    public void iniciarPartida(Partida p) {
        // setNumJugador(1);
        p.setJugador1(this);
        System.out.println("Iniciando partida en Jugador: " + p.getJugador1().getName() + " vs " + p.getJugador2().getName());
        p.set_en_curso();

        System.out.println(tablero.mostrarTablero().toString() + " " + tableroContrincante.mostrarTablero().toString());


        while (!tablero.isListo() || !tableroContrincante.isListo()){
            System.out.println("Esperando a que ambos jugadores coloquen sus barcos...");
            System.out.println(tablero.mostrarTablero().toString() + " " + tableroContrincante.mostrarTablero().toString());
            try {
                Thread.sleep(1000); // Espera 1 segundo
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restablece el estado de interrupción
                System.out.println("La espera fue interrumpida");
            }
            try {
                servicioGestor.actualizarPartida(p.getId(), p);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            tableroContrincante = p.getJugador2().getTablero();
        }

        try {
            p.setTablero1(tablero);
            p.setTablero2(tableroContrincante);
            servicioGestor.actualizarPartida(p.getId(), p);
            servicioGestor.Rondas(p);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        // Runnable tarea = new Runnable() {
        //     @Override
        //     public void run() {
        //         System.out.println("Ejecutando sentencia en segundo plano...");
        //         try {
        //             servicioGestor.Rondas(p);
        //         } catch (RemoteException e) {
        //             e.printStackTrace();
        //         }
        //     }
        // };
        // tarea.run();

        // try {
        //     servicioGestor.Rondas(p);
        // } catch (RemoteException e) {
        //     e.printStackTrace();
        // }
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setTableroContrincante(Tablero tableroContrincante) {
        this.tableroContrincante = tableroContrincante;
    }

    public Tablero getTableroContrincante() {
        return tableroContrincante;
    }

    public void setNumJugador(int numJugador) {
        this.numJugador = numJugador;
    }

    public int getNumJugador() {
        return numJugador;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new GUIJugador());

    }

}
