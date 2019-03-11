package domain.tests;

import domain.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    private Student s = new Student("12318","Hogea Cristina","224","cris_hogea@yahoo.com","Sipos Roxana");

    @org.junit.jupiter.api.Test
    void getNume() {
        assertEquals(s.getNume(),"Hogea Cristina");
    }

    @org.junit.jupiter.api.Test
    void setNume() {
        s.setNume("Alistar Andrei");
        assertEquals(s.getNume(),"Alistar Andrei");
        s.setNume("Hogea Cristina");
    }

    @org.junit.jupiter.api.Test
    void getGrupa() {
        assertEquals(s.getGrupa(),"224");
    }

    @org.junit.jupiter.api.Test
    void setGrupa() {
        s.setGrupa("221");
        assertEquals(s.getGrupa(),"221");
        s.setGrupa("224");
    }

    @org.junit.jupiter.api.Test
    void getEmail() {
        assertEquals(s.getEmail(),"cris_hogea@yahoo.com");
    }

    @org.junit.jupiter.api.Test
    void setEmail() {
        s.setEmail("bla@yahoo.com");
        assertEquals(s.getEmail(),"bla@yahoo.com");
        s.setEmail("cris_hogea@yahoo.com");
    }

    @org.junit.jupiter.api.Test
    void getCadruDidactic() {
        assertEquals(s.getCadruDidactic(),"Sipos Roxana");
    }

    @org.junit.jupiter.api.Test
    void setCadruDidactic() {
        s.setCadruDidactic("Serban Camelia");
        assertEquals(s.getCadruDidactic(),"Serban Camelia");
        s.setCadruDidactic("Sipos Roxana");
    }

    @org.junit.jupiter.api.Test
    void getID() {
        assertEquals(s.getID(),"12318");
    }

    @org.junit.jupiter.api.Test
    void setID() {
        s.setID("12319");
        assertEquals(s.getID(),"12319");
        s.setID("12318");
    }

    @Test
    void toStringTest() {
        assertEquals(s.toString(),"idStudent: 12318 | nume si prenume: Hogea Cristina | grupa: 224 | email: cris_hogea@yahoo.com | cadru didactic: Sipos Roxana");
    }
}