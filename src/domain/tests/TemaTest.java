package domain.tests;

import domain.Tema;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemaTest {
    private Tema t = new Tema(1,"Aplicatie",3,2);
    @Test
    void getID() {
        Integer i = 1;
        assertEquals(t.getID(),i);
    }

    @Test
    void setID() {
        t.setID(2);
        Integer i = 2;
        assertEquals(t.getID(),i);
        t.setID(1);
    }

    @Test
    void getDescriere() {
        assertEquals(t.getDescriere(),"Aplicatie");
    }

    @Test
    void setDescriere() {
        t.setDescriere("Program");
        assertEquals(t.getDescriere(),"Program");
        t.setDescriere("Aplicatie");
    }

    @Test
    void getDeadline() {
        assertEquals(t.getDeadline(),3);
    }

    @Test
    void setDeadline() {
        t.setDeadline(4);
        assertEquals(t.getDeadline(),4);
        t.setDeadline(3);
    }

    @Test
    void getSaptPrimire() {
        assertEquals(t.getSaptPrimire(),2);
    }

    @Test
    void setSaptPrimire() {
        t.setSaptPrimire(3);
        assertEquals(t.getSaptPrimire(),3);
        t.setSaptPrimire(2);
    }

    @Test
    void toStringTest() {
        assertEquals(t.toString(),"idTema: 1 | descriere: Aplicatie | deadline: 3 | saptamana primire: 2");
    }
}