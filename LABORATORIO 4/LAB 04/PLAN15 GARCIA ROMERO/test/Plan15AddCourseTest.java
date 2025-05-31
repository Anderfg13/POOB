package test;
import domain.*;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Plan15AddCourseTest {

    private Plan15 plan;

    @Before
    public void setUp() throws Plan15Exception {
        plan = new Plan15();
    }

    @After
    public void tearDown() {
        plan = null;
    }

    @Test
    public void shouldAddValidCourse() {
        try {
            int initialCount = plan.numberUnits();
            plan.addCourse("TEST1", "Curso Válido", "3", "2");
            assertEquals(initialCount + 1, plan.numberUnits());
            assertNotNull(plan.consult("TEST1"));
        } catch (Plan15Exception e) {
            fail("No debería lanzar excepción con datos válidos");
        }
    }

    @Test
    public void shouldFailWhenCodeExceeds6Characters() {
        try {
            plan.addCourse("CODIGOLARGO", "Nombre válido", "3", "2");
            fail("Debería haber lanzado excepción por código largo");
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.INVALID_CODE_LENGTH, e.getMessage());
        }
    }

    @Test
    public void shouldFailWhenNameIsEmpty() {
        try {
            plan.addCourse("C1", "", "3", "2");
            fail("Debería haber lanzado excepción por nombre vacío");
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.INVALID_NAME, e.getMessage());
        }
    }

    @Test
    public void shouldFailWhenCreditsAreNotPositive() {
        try {
            plan.addCourse("C1", "Curso", "0", "2");
            fail("Debería haber lanzado excepción por créditos no positivos");
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.INVALID_CREDITS_FORMAT, e.getMessage());
        }
    }

    @Test
    public void shouldFailWhenHoursAreNotPositive() {
        try {
            plan.addCourse("C1", "Curso", "3", "-1");
            fail("Debería haber lanzado excepción por horas no positivas");
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.INVALID_HOURS_FORMAT, e.getMessage());
        }
    }

    @Test
    public void shouldFailWhenHoursExceedThreeTimesCredits() {
        try {
            plan.addCourse("C1", "Curso", "1", "4"); // 4 > 3*1
            fail("Debería haber lanzado excepción por horas inconsistentes");
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.CREDITS_HOURS_INCONSISTENT, e.getMessage());
        }
    }



}
