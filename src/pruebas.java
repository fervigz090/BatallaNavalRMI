public class pruebas {

    public static void main(String[] args) {
        char fila = 'A';
        char columna = '9';
        char orientacion = 'H';

        int filaIndex = fila - 'A' + 1;
        int columnaIndex = Character.getNumericValue(columna);

        StringBuilder stCoordenadas = new StringBuilder();
        String coordenadasBarco = "";

        coordenadasBarco = coordenadasBarco + fila + columna + orientacion;

        stCoordenadas.append(coordenadasBarco);
        stCoordenadas.append("\n");
        coordenadasBarco = "B2V";
        stCoordenadas.append(coordenadasBarco);

        System.out.println(stCoordenadas.toString());
        System.out.println(stCoordenadas.charAt(1));

        filaIndex = stCoordenadas.charAt(0) - 'A' + 1;
        columnaIndex = Character.getNumericValue(stCoordenadas.charAt(1));

        System.out.println(filaIndex + " " + columnaIndex + " " + orientacion);

        stCoordenadas.delete(0, 4);

        System.out.println(filaIndex + " " + columnaIndex + " " + orientacion);

        System.out.println(stCoordenadas.charAt(1));
    }
    
}
