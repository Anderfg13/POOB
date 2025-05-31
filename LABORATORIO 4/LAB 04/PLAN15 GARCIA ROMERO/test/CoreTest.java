package test;
import domain.*;


import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CoreTest{
   
 
    @Test
    public void shouldCalculateTheCreditsOfACoreCostume(){
        Core c = new Core("NFCC", "Nucleo de Formacion Comun por Campo", 50);
        c.addCourse(new Course("PRI1","Proyecto Integrador 1", 3, 3));
        c.addCourse(new Course("DDYA", "Diseño de Datos y Algoritmos", 4, 4));
        c.addCourse(new Course("MPIN", "Matematicas para Informatica", 3, 4));
        try {
           assertEquals(10,c.credits());
        } catch (Plan15Exception e){
            fail("Threw a exception");
        }    
    }    
    
    
    @Test
    public void shouldThrowExceptionIfCoreHasNoCourse(){
           Core c = new Core("NFCC", "Nucleo de Formacion Comun por Campo", 50);
        try { 
           int price=c.credits();
           fail("Did not throw exception");
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.IMPOSSIBLE,e.getMessage());
        }    
    }    
    
    
   @Test
    public void shouldThrowExceptionIfThereIsErrorInCredits(){
        Core c = new Core("NFCC", "Nucleo de Formacion Comun por Campo", 50);
        c.addCourse(new Course("PRI1","Proyecto Integrador 1", -3, 3));
        c.addCourse(new Course("DDYA", "Diseño de Datos y Algoritmos", 4, 4));
        c.addCourse(new Course("MPIN", "Matematicas para Informatica", 3, 4));
        try { 
           int price=c.credits();
           fail("Did not throw exception");
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.CREDITS_ERROR,e.getMessage());
        }    
    }     
    
   @Test
    public void shouldThrowExceptionIfCreditsIsNotKnown(){
        Core c = new Core("NFCC", "Nucleo de Formacion Comun por Campo", 50);
        c.addCourse(new Course("PRI1","Proyecto Integrador 1", 3, 3));
        c.addCourse(new Course("DDYA", "Diseño de Datos y Algoritmos", 4, 4));
        c.addCourse(new Course("MPIN", "Matematicas para Informatica"));
        try { 
           int price=c.credits();
           fail("Did not throw exception");
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.CREDITS_UNKNOWN,e.getMessage());
        }    
    }  
    
   

    
    /**
     * Verifica que el cálculo de horas presenciales estimadas se realice correctamente
     * cuando todos los cursos tienen datos válidos.
     */
    @Test
    public void shouldCalculateInPersonHoursWithValidCourses() {
        Core c = new Core("NFCC", "Nucleo válido", 50);
        c.addCourse(new Course("PRI1", "Proyecto Integrador 1", 3, 3));
        c.addCourse(new Course("DDYA", "Diseño de Datos y Algoritmos", 4, 4));
        try {
            assertEquals(7, c.inPersonEstimated());
        } catch (Plan15Exception e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }

    /**
     * Verifica que se lance una excepción al intentar estimar horas presenciales
     * en un núcleo sin cursos.
     */
    @Test
    public void shouldThrowExceptionWhenEstimatingEmptyCore() {
        Core c = new Core("NFCC", "Nucleo vacío", 50);
        try {
            c.inPersonEstimated();
            fail("Debería lanzar excepción");
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.IMPOSSIBLE, e.getMessage());
        }
    }

    /**
     * Verifica que el método {@code data()} devuelva el formato correcto
     * para la representación textual de un núcleo y sus cursos.
     * 
     * @throws Plan15Exception si ocurre un error inesperado al obtener los datos.
     */
    @Test
    public void shouldFormatCoreDataCorrectly() throws Plan15Exception {
        Core c = new Core("NFCC", "Nucleo Test", 50);
        c.addCourse(new Course("C1", "Curso 1", 3, 3));
        c.addCourse(new Course("C2", "Curso 2", 4, 4));

        String expected = "NFCC: Nucleo Test. [50%]\n" +
                          "\tC1: Curso 1. Creditos:3[3+6]\n" +
                          "\tC2: Curso 2. Creditos:4[4+8]";
        assertEquals(expected, c.data());
    }
    
    
   @Test
    public void shouldFailWhenAddingUnitWithEmptyName() {
        try {
            // 1. Crear Plan15 manejando posible excepción
            Plan15 plan = new Plan15();
            
            // 2. Intentar agregar curso con nombre vacío
            try {
                plan.addCourse("C1", "", "3", "2");
                fail("Debería haber lanzado Plan15Exception por nombre vacío");
            } catch (Plan15Exception e) {
                assertEquals(Plan15Exception.INVALID_NAME, e.getMessage());
            } catch (Exception unexpected) {
                fail("Lanzó " + unexpected.getClass().getSimpleName() + 
                     " en lugar de Plan15Exception");
            }
            
        } catch (Plan15Exception initEx) {
            fail("Falló la inicialización de Plan15: " + initEx.getMessage());
        }
    }
   

    @Test
    public void shouldFailWhenAddingCourseWithNonIntegerCredits() {
        try {
            Plan15 plan = new Plan15();
            
            try {
                plan.addCourse("C1", "Curso válido", "abc", "2");
                fail("Debería haber lanzado Plan15Exception por créditos no numéricos");
            } catch (Plan15Exception e) {
                assertEquals(Plan15Exception.INVALID_CREDITS_FORMAT, e.getMessage());
            } catch (Exception unexpected) {
                fail("Lanzó " + unexpected.getClass().getSimpleName() + 
                     " en lugar de Plan15Exception");
            }
            
        } catch (Plan15Exception initEx) {
            fail("Falló la inicialización de Plan15: " + initEx.getMessage());
        }
    }



    @Test
    public void shouldFailWhenHoursExceedThreeTimesCredits() {
        try {
            Plan15 plan = new Plan15();
            
            try {
                plan.addCourse("C1", "Curso válido", "3", "10");
                fail("Debería haber lanzado Plan15Exception por horas excesivas");
            } catch (Plan15Exception e) {
                assertEquals(Plan15Exception.CREDITS_HOURS_INCONSISTENT, e.getMessage());
            } catch (Exception unexpected) {
                fail("Lanzó " + unexpected.getClass().getSimpleName() + 
                     " en lugar de Plan15Exception");
            }
            
        } catch (Plan15Exception initEx) {
            fail("Falló la inicialización de Plan15: " + initEx.getMessage());
        }
    }

    @Test
    public void shouldAcceptValidCourse() {
        try {
            Plan15 plan = new Plan15();
            
            try {
                plan.addCourse("C1", "Curso válido", "3", "2");
                // Si llega aquí sin excepciones, la prueba pasa
                assertTrue(true);
            } catch (Plan15Exception unexpected) {
                fail("No debería lanzar Plan15Exception con datos válidos");
            } catch (Exception unexpected) {
                fail("Lanzó excepción inesperada: " + unexpected.getClass().getSimpleName());
            }
            
        } catch (Plan15Exception initEx) {
            fail("Falló la inicialización de Plan15: " + initEx.getMessage());
        }
    }
    
    @Test
    public void shouldFailWhenPercentageIsBelowZero() {
        try {
            Plan15 plan = new Plan15();
            try {
                plan.addCore("NC1", "Núcleo Inválido", "-10", "PRI1");
                fail("Debería haber lanzado excepción por porcentaje negativo");
            } catch (Plan15Exception e) {
                assertEquals(Plan15Exception.INVALID_PERCENTAGE, e.getMessage());
            }
        } catch (Plan15Exception initEx) {
            fail("Falló la inicialización: " + initEx.getMessage());
        }
    }
    
    @Test
    public void shouldFailWhenPercentageIsAbove100() {
        try {
            Plan15 plan = new Plan15();
            try {
                plan.addCore("NC1", "Núcleo Inválido", "150", "PRI1");
                fail("Debería haber lanzado excepción por porcentaje > 100");
            } catch (Plan15Exception e) {
                assertEquals(Plan15Exception.INVALID_PERCENTAGE, e.getMessage());
            }
        } catch (Plan15Exception initEx) {
            fail("Falló la inicialización: " + initEx.getMessage());
        }
    }

        @Test
    public void shouldFailWhenCodeExceeds6Characters() {
        try {
            Plan15 plan = new Plan15();
            try {
                plan.addCourse("CODIGOLARGO", "Nombre válido", "3", "2");
                fail("Debería haber lanzado excepción por código demasiado largo");
            } catch (Plan15Exception e) {
                assertEquals(Plan15Exception.INVALID_CODE_LENGTH, e.getMessage());
            }
        } catch (Plan15Exception initEx) {
            fail("Falló la inicialización: " + initEx.getMessage());
        }
    }
    
    @Test
    public void shouldFindExistingCourse() {
        try {
            Plan15 plan = new Plan15();
            // code, name, credits, inPerson
            plan.addCourse("INF111", "Programación I", "3", "3");
    
            String result = plan.search("Programación");
    
            assertNotNull("La búsqueda no debería devolver null para un curso existente", result);
            assertTrue("El resultado debería contener el nombre del curso",
                       result.contains("Programación I"));
        } catch (Plan15Exception e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }
    
    @Test
    public void shouldFindExistingCore() {
        try {
            Plan15 plan = new Plan15();
            
            // Primero se agrega un curso que se usará en el núcleo
            plan.addCourse("MAT101", "Matemáticas Básicas", "3", "3");
            
            // Luego, se agrega un núcleo que contiene ese curso
            String cursosEnNucleo = "MAT101";  // separados por \n si son más
            plan.addCore("NUC001", "Núcleo de Ciencias", "100", cursosEnNucleo);
    
            String result = plan.search("Ciencias");
    
            assertNotNull("La búsqueda no debería devolver null para un núcleo existente", result);
            assertTrue("El resultado debería contener el nombre del núcleo",
                       result.contains("Núcleo de Ciencias"));
        } catch (Plan15Exception e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }



    
}