package domain;

/**
 * Representa un curso académico que extiende la clase abstracta {@link Unit}.
 * Un curso tiene un código, un nombre, una cantidad de créditos y horas presenciales. Esta clase
 * proporciona validación de los datos mediante excepciones personalizadas y calcula las horas
 * independientes con base en la fórmula estándar: {@code créditos * 3 - horas presenciales}.
 */
public class Course extends Unit {
    
    private Integer credits;
    private Integer inPerson;

    /**
     * Constructor que inicializa el curso solo con su código y nombre.
     * 
     * @param code el código único del curso.
     * @param name el nombre del curso.
     */
    public Course(String code, String name) {
        super(code, name);
    }

    /**
     * Constructor que inicializa el curso con código, nombre y créditos.
     * 
     * @param code el código del curso.
     * @param name el nombre del curso.
     * @param credits los créditos del curso.
     */
    public Course(String code, String name, int credits) {
        super(code, name);
        this.credits = credits;
    }

    /**
     * Constructor que inicializa el curso con código, nombre, créditos y horas presenciales.
     * 
     * @param code el código del curso.
     * @param name el nombre del curso.
     * @param credits la cantidad de créditos.
     * @param inPerson la cantidad de horas presenciales.
     */
    public Course(String code, String name, int credits, int inPerson) {
        this(code, name, credits);
        this.inPerson = inPerson;
    }

    /**
     * Devuelve la cantidad de créditos del curso.
     * 
     * @return los créditos como entero.
     * @throws Plan15Exception si los créditos son desconocidos, no válidos o si las horas
     *         presenciales superan las horas máximas permitidas para los créditos.
     */
    @Override
    public int credits() throws Plan15Exception {
        if (credits == null) throw new Plan15Exception(Plan15Exception.CREDITS_UNKNOWN);
        if (credits <= 0) throw new Plan15Exception(Plan15Exception.CREDITS_ERROR);
        if ((inPerson != null) && (inPerson > 3 * credits))
            throw new Plan15Exception(Plan15Exception.CREDITS_ERROR);
        return credits;
    }

    /**
     * Devuelve la cantidad de horas presenciales del curso.
     * 
     * @return las horas presenciales como entero.
     * @throws Plan15Exception si las horas presenciales son desconocidas, no válidas o si exceden el límite
     *         permitido por la cantidad de créditos.
     */
    @Override
    public int inPerson() throws Plan15Exception {
        if (inPerson == null) throw new Plan15Exception(Plan15Exception.IN_PERSON_UNKNOWN);
        if (inPerson <= 0) throw new Plan15Exception(Plan15Exception.IN_PERSON_ERROR);
        if ((credits != null) && (inPerson > 3 * credits))
            throw new Plan15Exception(Plan15Exception.CREDITS_ERROR);
        return inPerson;
    }

    /**
     * Devuelve una representación textual del curso, incluyendo el código, nombre,
     * créditos, horas presenciales y horas independientes.
     * 
     * @return una cadena de texto con los datos completos del curso.
     */
    @Override
    public String data() {
        String theData = code + ": " + name;
        try {
            theData = theData + ". Creditos: " + credits() + " [" + inPerson() + " + " + independent() + "]";
        } catch (Plan15Exception e) {
            // Si ocurre excepción, simplemente se omiten los datos adicionales.
        }
        return theData;
    }

    /**
     * Devuelve una representación corta del curso, con código, nombre y créditos.
     * 
     * @return representación textual del curso.
     */
    @Override
    public String toString() {
        return code + ": " + name + " (" + credits + " créditos)";
    }

    /**
     * Devuelve el nombre del curso.
     * 
     * @return el nombre de la unidad.
     */
    @Override
    public String getName() {
        return name;
    }
}
