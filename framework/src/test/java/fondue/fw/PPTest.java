package fondue.fw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public final class PPTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    static void assertT(boolean b) {
        assertEquals("T", b ? "T" : "F");
    }

    static void assertF(boolean b) {
        assertEquals("F", b ? "T" : "F");
    }

    @Test
    public void testPP() {
        // omit
    }

    @Test
    public void testValidateCurrentPage() {
        PP p = new PP();
        assertF(p.isValidCurrentPage());
        p.validateCurrentPage();
        assertT(p.isValidCurrentPage());
        p.validateCurrentPage();
        assertT(p.isValidCurrentPage());
    }

    @Test
    public void testIsValidCurrentPage() {
        PP p = new PP();
        assertF(p.isValidCurrentPage());
        p.setTotalCount(21);
        p.setCurrentPage(2);
        assertT(p.isValidCurrentPage());
        p.setDisabled(true);
        assertF(p.isValidCurrentPage());
    }

    @Test
    public void testIsFirst() {
        PP p = new PP();
        p.setCurrentPage(1);
        p.setTotalCount(20);
        assertT(p.isFirst());
        p.setTotalCount(21);
        p.setCurrentPage(2);
        assertF(p.isFirst());
        p.setCurrentPage(0);
        p.validateCurrentPage();
        assertT(p.isFirst());
    }

    @Test
    public void testIsLast() {
        PP p = new PP();
        p.setCurrentPage(1);
        p.setTotalCount(20);
        assertT(p.isLast());
        p.setTotalCount(21);
        p.setCurrentPage(2);
        assertT(p.isLast());
        p.setCurrentPage(1);
        assertF(p.isLast());
        p.setCurrentPage(0);
        p.validateCurrentPage();
        assertF(p.isLast());
    }

    @Test
    public void testGetCountPerPage() {
        PP p = new PP();
        assertEquals(20, p.getCountPerPage());
        p.setCountPerPage(1);
        assertEquals(1, p.getCountPerPage());
        p.setCountPerPage(50);
        assertEquals(50, p.getCountPerPage());
    }

    @Test
    public void testSetCountPerPage() {
        // omit
    }

    @Test
    public void testSetCountPerPageException1() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(Matchers.containsString("countPerPage = -1"));
        PP p = new PP();
        p.setCountPerPage(-1);
    }

    @Test
    public void testSetCountPerPageException2() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(Matchers.containsString("countPerPage = 0"));
        PP p = new PP();
        p.setCountPerPage(0);
    }

    @Test
    public void testGetLastPage() {
        PP p = new PP();
        p.setTotalCount(0);
        assertEquals(1, p.getLastPage());
        p.setTotalCount(20);
        assertEquals(1, p.getLastPage());
        p.setTotalCount(21);
        assertEquals(2, p.getLastPage());
        p.setTotalCount(40);
        assertEquals(2, p.getLastPage());
        p.setTotalCount(41);
        assertEquals(3, p.getLastPage());
        //
        p.setCountPerPage(7);
        p.setTotalCount(49);
        assertEquals(7, p.getLastPage());
    }

    @Test
    public void testOffset() {
        PP p = new PP();
        int currentPage = 0;
        p.setTotalCount(61);
        assertEquals(0, p.offset());
        p.setCurrentPage(++currentPage);
        assertEquals(0, p.offset());
        p.setCurrentPage(++currentPage);
        assertEquals(20, p.offset());
        p.setCurrentPage(++currentPage);
        assertEquals(40, p.offset());
        p.setCurrentPage(++currentPage);
        assertEquals(60, p.offset());
    }

    @Test
    public void testLimit() {
        PP p = new PP();
        int currentPage = 0;
        p.setTotalCount(61);
        assertEquals(20, p.limit());
        p.setCurrentPage(++currentPage);
        assertEquals(20, p.limit());
        p.setCurrentPage(++currentPage);
        assertEquals(20, p.limit());
        p.setCurrentPage(++currentPage);
        assertEquals(20, p.limit());
        p.setCurrentPage(++currentPage);
        assertEquals(20, p.limit());
    }

    @Test
    public void testIsDisabled() {
        PP p = new PP();
        assertF(p.isDisabled());
        p.setDisabled(true);
        assertT(p.isDisabled());
        p.setDisabled(false);
        assertF(p.isDisabled());
    }

    @Test
    public void testSetDisabled() {
        // omit
    }

    @Test
    public void testGetCurrentPage() {
        PP p = new PP();
        p.setCurrentPage(0);
        assertEquals(0, p.getCurrentPage());
        p.setCurrentPage(1);
        assertEquals(1, p.getCurrentPage());
        p.setCurrentPage(0);
        p.validateCurrentPage();
        assertEquals(1, p.getCurrentPage());
    }

    @Test
    public void testSetCurrentPage() {
        // omit
    }

    @Test
    public void testGetTotalCount() {
        PP p = new PP();
        p.setTotalCount(0);
        assertEquals(0, p.getTotalCount());
        p.setTotalCount(12);
        assertEquals(12, p.getTotalCount());
    }

    @Test
    public void testSetTotalCount() {
        PP p = new PP();
        p.setTotalCount(0L);
        p.setTotalCount(1L);
    }

    @Test
    public void testSetTotalCountException1() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(Matchers.containsString("totalCount = -1"));
        PP p = new PP();
        p.setTotalCount(-1L);
    }

    @Test
    public void testGetQuery() {
        PP p = new PP();
        assertEquals("", p.getQuery());
        p.setQuery("word");
        assertEquals("word", p.getQuery());
    }

    @Test
    public void testSetQuery() {
        // omit
    }

    @Test
    public void testToString() {
        PP p = new PP();
        assertEquals("PP(countPerPage=20, disabled=false, currentPage=0, totalCount=0, query=)", p.toString());
        p.setDisabled(true);
        assertEquals("PP(countPerPage=20, disabled=true, currentPage=0, totalCount=0, query=)", p.toString());
        p.setDisabled(false);
        p.setTotalCount(41);
        p.setCurrentPage(2);
        p.validateCurrentPage();
        p.setQuery("ABC");
        assertEquals("PP(countPerPage=20, disabled=false, currentPage=2, totalCount=41, query=ABC)", p.toString());
    }

}
