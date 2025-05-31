package domain;

import java.util.ArrayList;

/**
 * Representa una unidad académica de tipo núcleo (Core), la cual agrupa varios cursos.
 * El núcleo define un porcentaje sugerido de presencialidad que se aplica a los cursos 
 * asociados en caso de que no se disponga de información explícita.
 */
public class Core extends Unit {

    private int inPersonPercentage;
    private ArrayList<Course> courses;
    public static int creditsPerHour = 3;

    /**
     * Crea una nueva unidad núcleo con código, nombre y porcentaje de presencialidad sugerido.
     *
     * @param code el código del núcleo.
     * @param name el nombre del núcleo.
     * @param inPersonPercentage el porcentaje sugerido de horas presenciales (entre 0 y 100).
     */
    public Core(String code, String name, int inPersonPercentage) {
        super(code, name);
        this.inPersonPercentage = inPersonPercentage;
        courses = new ArrayList<>();
    }

    /**
     * Agrega un curso al núcleo.
     *
     * @param c el curso a agregar.
     */
    public void addCourse(Course c) {
        courses.add(c);
    }

    /**
     * Calcula el total de créditos de todos los cursos asociados al núcleo.
     *
     * @return la suma total de créditos.
     * @throws Plan15Exception si no hay cursos o si algún curso tiene créditos inválidos.
     */
    @Override
    public int credits() throws Plan15Exception {
        if (courses.isEmpty()) {
            throw new Plan15Exception(Plan15Exception.IMPOSSIBLE);
        }

        int totalCredits = 0;
        for (Course c : courses) {
            if (c.credits() < 0) {
                throw new Plan15Exception(Plan15Exception.CREDITS_UNKNOWN);
            }
            totalCredits += c.credits();
        }

        return totalCredits;
    }

    /**
     * Método sobrescrito que retorna 0 ya que la unidad núcleo no tiene horas presenciales propias.
     *
     * @return 0.
     * @throws Plan15Exception nunca se lanza en esta implementación.
     */
    @Override
    public int inPerson() throws Plan15Exception {
        return 0;
    }

    /**
     * Calcula los créditos estimados de los cursos del núcleo.
     * Si un curso presenta error al obtener sus créditos, se asume por defecto que vale 3 créditos.
     *
     * @return la suma estimada de créditos.
     * @throws Plan15Exception si no hay cursos.
     */
    public int creditsEstimated() throws Plan15Exception {
        if (courses.isEmpty()) {
            throw new Plan15Exception(Plan15Exception.IMPOSSIBLE);
        }

        int totalCredits = 0;
        for (Course c : courses) {
            try {
                totalCredits += c.credits();
            } catch (Plan15Exception e) {
                System.out.println(e.getMessage());
                totalCredits += 3; // Valor por defecto
            }
        }

        return totalCredits;
    }

    /**
     * Calcula las horas presenciales estimadas para los cursos del núcleo.
     * <ul>
     *   <li>Si un curso tiene horas presenciales válidas, se usan directamente.</li>
     *   <li>Si las horas presenciales no se conocen, se estiman con base en el porcentaje del núcleo.</li>
     *   <li>Si hay error con los créditos, se asume 9 horas presenciales (3 créditos * 3).</li>
     * </ul>
     *
     * @return el total estimado de horas presenciales.
     * @throws Plan15Exception si no hay cursos válidos.
     */
    public int inPersonEstimated() throws Plan15Exception {
        if (courses.isEmpty()) {
            throw new Plan15Exception(Plan15Exception.IMPOSSIBLE);
        }

        int totalHours = 0;
        boolean hasValidCourse = false;

        for (Course course : courses) {
            try {
                int credits = course.credits();
                try {
                    totalHours += course.inPerson();
                    hasValidCourse = true;
                } catch (Plan15Exception e) {
                    totalHours += (int) Math.round(credits * 3 * (inPersonPercentage / 100.0));
                    hasValidCourse = true;
                }
            } catch (Plan15Exception e) {
                totalHours += 9; // Asume curso de 3 créditos con 100% presencialidad
                hasValidCourse = true;
            }
        }

        if (!hasValidCourse) {
            throw new Plan15Exception(Plan15Exception.IMPOSSIBLE);
        }

        return totalHours;
    }

    /**
     * Retorna una representación detallada de la unidad núcleo y sus cursos asociados.
     *
     * @return una cadena con el código, nombre, porcentaje de presencialidad y detalle de los cursos.
     * @throws Plan15Exception si ocurre error al generar los datos de algún curso.
     */
    @Override
    public String data() throws Plan15Exception {
        StringBuffer answer = new StringBuffer();
        answer.append(code + ": " + name + ". [" + inPersonPercentage + "%]");
        for (Course c : courses) {
            answer.append("\n\t" + c.data());
        }
        return answer.toString();
    }

    /**
     * Representación corta de la unidad núcleo (código y nombre).
     *
     * @return representación textual del núcleo.
     */
    @Override
    public String toString() {
        return code + ": " + name;
    }

    /**
     * Devuelve el nombre de la unidad núcleo.
     *
     * @return el nombre.
     */
    @Override
    public String getName() {
        return name;
    }
}
