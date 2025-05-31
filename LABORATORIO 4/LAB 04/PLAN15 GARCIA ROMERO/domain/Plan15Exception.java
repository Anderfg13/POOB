package domain;
import java.lang.Exception;

/**
 *Esta clase es la clase de errores del proyecto.
 * @authors Christian Romero y Anderson Garcia
 * @1.0
 */
public class Plan15Exception extends Exception
{
    // instance variables - replace the example below with your own
    public static final String IMPOSSIBLE = "El nucleo de formación no tiene algun curso o los cursos tienen problemas con los creditos";
    public static final String CREDITS_ERROR ="Los creditos ingresados son negativos";
    public static final String CREDITS_UNKNOWN = "No se ingreso algun valor de los creditos";
    public static final String IN_PERSON_ERROR = "La cantidad de personas no pueden ser negativas";
    public static final String IN_PERSON_UNKNOWN = "Las personas indicadas son desconocida";
    public static final String INVALID_NAME = "El curso debe tener un nombre";
    public static final String INVALID_CREDITS_FORMAT = "Los créditos deben ser un número entero positivo";
    public static final String INVALID_HOURS_FORMAT = "Las horas presenciales deben ser un número entero positivo";
    public static final String CREDITS_HOURS_INCONSISTENT = "Las horas presenciales no pueden superar 3 veces los créditos";
    public static final String INVALID_PERCENTAGE = "El porcentaje debe estar entre 0 y 100";
    public static final String INVALID_CODE_LENGTH = "Las siglas deben tener máximo 6 caracteres";
    public static final String NO_RESULTS_FOUND = "No se encontraron unidades con ese criterio de búsqueda";
    /**
     * Constructor de la clase Exceptions
     */
    public Plan15Exception(String message){
        super(message);
    }
}
