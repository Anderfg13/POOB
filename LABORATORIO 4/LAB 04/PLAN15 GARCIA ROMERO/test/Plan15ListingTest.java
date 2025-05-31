package test;
import domain.*;

import java.util.ArrayList; // Importa solo la clase ArrayList
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class Plan15ListingTest {

    private Plan15 plan;

    @Before
    public void setUp() throws Plan15Exception {
        plan = new Plan15(); // Inicializa con datos de prueba (PRI1, DDYA, MPIN, FCC)
    }

    @Test
    public void shouldListAllUnits() {
        String result = plan.toString();
        assertNotNull(result);
        assertTrue(result.contains("PRI1"));
        assertTrue(result.contains("DDYA"));
        assertTrue(result.contains("MPIN"));
        assertTrue(result.contains("FCC"));
        assertTrue(result.contains("unidades"));
    }

    @Test
    public void shouldReturnCorrectUnitCount() {
        assertEquals(4, plan.numberUnits());
    }

    @Test
    public void shouldSelectUnitsByPrefix() {
        ArrayList<Unit> result = plan.select("PRI");
        assertEquals(1, result.size());
        assertEquals("PRI1", result.get(0).code());
    }

    @Test
    public void shouldReturnEmptyListForNonMatchingPrefix() {
        ArrayList<Unit> result = plan.select("XYZ");
        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldFormatSelectedUnitsData() {
        ArrayList<Unit> selected = new ArrayList<>();
        selected.add(plan.consult("PRI1"));
        selected.add(plan.consult("DDYA"));
        
        String result = plan.data(selected);
        assertTrue(result.contains("PRI1"));
        assertTrue(result.contains("DDYA"));
        assertTrue(result.contains("Proyecto Integrador"));
        assertTrue(result.contains("Diseño de Datos"));
    }


    @Test
    public void shouldSearchUnitsByName() throws Plan15Exception {
        String result = plan.search("Proyecto");
        assertNotNull(result);
        assertTrue(result.contains("PRI1"));
        assertTrue(result.contains("Proyecto Integrador"));
    }

    @Test
    public void shouldReturnNullForNoSearchResults() throws Plan15Exception {
        String result = plan.search("NoExistente");
        assertNull(result);
    }

    @Test(expected = Plan15Exception.class)
    public void shouldThrowExceptionForEmptySearchQuery() throws Plan15Exception {
        plan.search("");
    }

    @Test
    public void shouldListAllCoursesInCore() throws Plan15Exception {
        Core fcc = (Core) plan.consult("FCC");
        String result = fcc.data();
        assertTrue(result.contains("PRI1"));
        assertTrue(result.contains("DDYA"));
        assertTrue(result.contains("MPIN"));
        assertTrue(result.contains("50%"));
    }
}