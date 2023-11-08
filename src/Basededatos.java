import java.util.HashMap;

public class Basededatos {

    private HashMap<String, String> RegistroUsuarios = new HashMap<>();

    public Basededatos(){

    }

    /**
     * Devuelve el numero de usuarios registrados
     * @return int
     */
    public int size(){
        return RegistroUsuarios.size();
    }

    /**
     * Almacena un jugador en RegistroUsuarios
     * @param name String
     * @param password String
     */
    public void setUser(String name, String password){
        RegistroUsuarios.put(name, password);
    }

    /**
     * Devuelve True si el nombre introducido lo está usando ya otro jugador
     * @param name String
     * @return boolean
     */
    public boolean existe(String name){
        return RegistroUsuarios.containsKey(name);
    }

    /**
     * Devuelve la contraseña del usuario introducido como parametro.
     * @param name String
     * @return String
     */
    public String getPass(String name){
        if(existe(name)){
            return RegistroUsuarios.get(name);
        }else return null;
    }

    /**
     * Elimina el jugador de RegistroUsuarios.
     * @param name String
     */
    public void deleteUser(String name){
        if(existe(name)){
            RegistroUsuarios.remove(name);
        }
    }



}
