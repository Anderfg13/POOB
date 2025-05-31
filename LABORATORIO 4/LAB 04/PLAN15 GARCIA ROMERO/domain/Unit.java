package domain;

/**
 * Clase abstracta que representa una unidad académica dentro del sistema Plan15.
 * <p>
 * Una unidad puede representar una materia, seminario, práctica, etc., y se identifica
 * por un código y un nombre. Define métodos abstractos para obtener información como
 * los créditos, las horas presenciales y la representación en forma de texto.
 * </p>
 */
public abstract class Unit {
    
    /**
     * Código identificador de la unidad.
     */
    protected String code;
    
    /**
     * Nombre de la unidad.
     */
    protected String name;
    
    /**
     * Constructor para inicializar la unidad con su código y nombre.
     * 
     * @param code el código único de la unidad.
     * @param name el nombre de la unidad.
     */
    public Unit(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    /**
     * Devuelve el código de la unidad.
     * 
     * @return el código como cadena.
     */
    public String code() {
        return code;
    }
    
    /**
     * Devuelve el nombre de la unidad.
     * 
     * @return el nombre como cadena.
     */
    public String name() {
        return name;
    }

    /**
     * Devuelve la cantidad de créditos de la unidad.
     * 
     * @return los créditos como entero.
     * @throws Plan15Exception si los créditos son desconocidos o hay un error al obtenerlos.
     */
    public abstract int credits() throws Plan15Exception;

    /**
     * Devuelve la cantidad de horas presenciales asociadas a la unidad.
     * 
     * @return las horas presenciales como entero.
     * @throws Plan15Exception si las horas presenciales no están disponibles o hay un error.
     */
    public abstract int inPerson() throws Plan15Exception;

    /**
     * Calcula y devuelve las horas de trabajo independiente.
     * 
     * @return las horas independientes como entero.
     * @throws Plan15Exception si ocurre un error al calcular los créditos o las horas presenciales.
     */
    public final int independent() throws Plan15Exception {
        return credits() * 3 - inPerson();
    }

    /**
     * Devuelve una representación textual completa de la unidad.
     * 
     * @return una cadena con los datos de la unidad.
     * @throws Plan15Exception si los datos no están completos o hay errores en créditos o horas.
     */
    public abstract String data() throws Plan15Exception;

    /**
     * Devuelve el nombre de la unidad. Este método puede ser sobrescrito por subclases.
     * 
     * @return el nombre de la unidad o {@code null} si no está implementado.
     */
    public String getName() {
        return null;
    }
}
