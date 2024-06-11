import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JOptionPane;

public class CallbackJugadorImpl extends UnicastRemoteObject implements CallbackJugadorInterface{

    protected CallbackJugadorImpl() throws RemoteException {
        super();
    }

    @Override
    public void partidaIniciada() throws RemoteException {
        System.out.println("La partida ha comenzado!");

        //Añadir logica del juego para manejar inicio de partida en el cliente.

    }

    @Override
    public void turnoParaDisparar() throws RemoteException {
        System.out.println("Es tu turno para disparar!");
        String coordenadas = JOptionPane.showInputDialog(null, "Ingrese las coordenadas para disparar (e.g., A5):");
        //Añadir logica del juego para permitir al jugador disparar en su turno

    }

    @Override
    public void resultadoDisparoOponente(boolean impacto, boolean hundido, char fila, int columna) throws RemoteException {
        if(impacto){
            System.out.println("Has dado en el blanco! " + fila + columna);
            if(hundido){
                System.out.println("Has hundido un barco del oponente!");
            }
        } else {
            System.out.println("Agua!" + fila + columna);
        }

        //logica del resultado del disparo del oponente en el cliente

    }

    @Override
    public void hasGanado() throws RemoteException {
        System.out.println("Has ganado la partida!");

        //Logica para cuando el jugador gana la partida

    }

    @Override
    public void hasPerdido() throws RemoteException {
        System.out.println("Has perdido la partida!");

        //Logica para cuando el jugador pierde la partida.

    }
}
