package domain;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Clase que representa un plan académico que incluye cursos y núcleos de formación.
 * Permite agregar, consultar, buscar y listar unidades académicas.
 * 
 * @author POOB
 * @version ECI 2025
 */
public class Plan15 {
    private ArrayList<Unit> units;
    private TreeMap<String, Course> courses;

    /**
     * Crea un nuevo plan académico e inicializa con algunas unidades por defecto.
     * 
     * @throws Plan15Exception si hay errores al agregar las unidades iniciales.
     */
    public Plan15() throws Plan15Exception {
        units = new ArrayList<Unit>();
        courses = new TreeMap<String, Course>();
        addSome();
    }

    /**
     * Agrega cursos y núcleos predeterminados al plan.
     * 
     * @throws Plan15Exception si alguno de los cursos o núcleos tiene datos inválidos.
     */
    private void addSome() throws Plan15Exception {
        String [][] courses = {{"PRI1", "Proyecto Integrador","9","3"},
                              {"DDYA", "Diseño de Datos y Algoritmos","4","4"},
                              {"MPIN", "Matematicas para Informatica","3","4"}};
        for (String [] c: courses){
            addCourse(c[0],c[1],c[2],c[3]);
        }
        String [][] Core = {{"FCC","Nucleo formacion por comun por campo", "50", "PRI1\nDDYA\nMPIN"}};
        for (String [] c: Core){
            addCore(c[0],c[1],c[2],c[3]);
        }
    }

    /**
     * Consulta una unidad por su código.
     * 
     * @param name Código de la unidad a consultar.
     * @return La unidad encontrada o null si no existe.
     */
    public Unit consult(String name) {
        Unit c = null;
        for(int i = 0; i < units.size() && c == null; i++) {
            if (units.get(i).code().compareToIgnoreCase(name) == 0) {
                c = units.get(i);
            }
        }
        return c;
    }

    /**
     * Agrega un nuevo curso al plan.
     * 
     * @param code Código del curso.
     * @param name Nombre del curso.
     * @param credits Créditos del curso (como cadena).
     * @param inPerson Horas presenciales del curso (como cadena).
     * @throws Plan15Exception si los parámetros son inválidos.
     */
    public void addCourse(String code, String name, String credits, String inPerson) throws Plan15Exception {
        if (code == null || code.length() > 6) {
            throw new Plan15Exception(Plan15Exception.INVALID_CODE_LENGTH);
        }

        if (name == null || name.trim().isEmpty()) {
            throw new Plan15Exception(Plan15Exception.INVALID_NAME);
        }

        try {
            int creditos = Integer.parseInt(credits);
            if (creditos <= 0) {
                throw new Plan15Exception(Plan15Exception.INVALID_CREDITS_FORMAT);
            }

            int horas = Integer.parseInt(inPerson);
            if (horas <= 0) {
                throw new Plan15Exception(Plan15Exception.INVALID_HOURS_FORMAT);
            }

            if (horas > 3 * creditos) {
                throw new Plan15Exception(Plan15Exception.CREDITS_HOURS_INCONSISTENT);
            }

            Course nc = new Course(code, name, creditos, horas);
            units.add(nc);
            courses.put(code.toUpperCase(), nc);

        } catch (NumberFormatException e) {
            throw new Plan15Exception(Plan15Exception.INVALID_CREDITS_FORMAT);
        }
    }

    /**
     * Agrega un nuevo núcleo (Core) al plan.
     * 
     * @param code Código del núcleo.
     * @param name Nombre del núcleo.
     * @param percentage Porcentaje del núcleo (como cadena).
     * @param theCourses Lista de códigos de cursos separados por salto de línea.
     * @throws Plan15Exception si los parámetros son inválidos.
     */
    public void addCore(String code, String name, String percentage, String theCourses) throws Plan15Exception {
        if (code == null || code.length() > 6) {
            throw new Plan15Exception(Plan15Exception.INVALID_CODE_LENGTH);
        }
        try {
            int porcentaje = Integer.parseInt(percentage);
            if (porcentaje < 0 || porcentaje > 100) {
                throw new Plan15Exception(Plan15Exception.INVALID_PERCENTAGE);
            }

            Core c = new Core(code, name, porcentaje);
            String[] aCourses = theCourses.split("\n");
            for (String b : aCourses) {
                c.addCourse(courses.get(b.toUpperCase()));
            }
            units.add(c);

        } catch (NumberFormatException e) {
            throw new Plan15Exception("El porcentaje debe ser un número entero");
        }
    }

    /**
     * Selecciona las unidades cuyo código comienza con un prefijo dado.
     * 
     * @param prefix Prefijo de los códigos a buscar.
     * @return Lista de unidades que coinciden con el prefijo.
     */
    public ArrayList<Unit> select(String prefix) {
        ArrayList<Unit> answers = new ArrayList<Unit>();
        prefix = prefix.toUpperCase();
        for (int i = 0; i < units.size(); i++) {
            if (units.get(i).code().toUpperCase().startsWith(prefix)) {
                answers.add(units.get(i));
            }
        }
        return answers;
    }

    /**
     * Retorna la representación textual de una lista de unidades seleccionadas.
     * 
     * @param selected Lista de unidades seleccionadas.
     * @return Texto descriptivo de las unidades.
     */
    public String data(ArrayList<Unit> selected) {
        StringBuffer answer = new StringBuffer();
        answer.append(units.size() + " unidades\n");
        for (Unit p : selected) {
            try {
                answer.append('>' + p.data());
                answer.append("\n");
            } catch (Plan15Exception e) {
                answer.append("**** " + e.getMessage());
            }
        }
        return answer.toString();
    }

    /**
     * Busca unidades cuyo nombre contenga el texto proporcionado.
     * 
     * @param query Texto a buscar en los nombres de las unidades.
     * @return Texto con las unidades encontradas o null si no hay coincidencias.
     * @throws Plan15Exception si la consulta es vacía o nula.
     */
    public String search(String query) throws Plan15Exception {
        if (query == null || query.trim().isEmpty()) {
            throw new Plan15Exception("Consulta vacía");
        }

        StringBuilder result = new StringBuilder();
        String queryLower = query.toLowerCase();

        for (Unit unit : units) {
            if (unit.getName().toLowerCase().contains(queryLower)) {
                result.append(unit.toString()).append("\n");
            }
        }

        return result.length() == 0 ? null : result.toString().trim();
    }

    /**
     * Retorna la representación textual de todas las unidades.
     * 
     * @return Texto descriptivo de todas las unidades.
     */
    public String toString() {
        return data(units);
    }

    /**
     * Consulta el número total de unidades registradas en el plan.
     * 
     * @return Número de unidades.
     */
    public int numberUnits() {
        return units.size();
    }
}
