package repository.inMemory.tests;

import domain.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.CrudRepository;
import repository.inMemory.StudentInMemoryRepository;
import validator.StudentValidator;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StudentInMemoryRepositoryTest {
    private StudentInMemoryRepository studenti;
    @BeforeEach
    void setUp() {
        StudentValidator validator = new StudentValidator();
        studenti = new StudentInMemoryRepository(validator);
        studenti.save(new Student("12318","Hogea Cristina","224","c@yahoo.com","Sipos Roxana"));
    }

    @AfterEach
    void tearDown() {
        studenti = null;
    }

    @Test
    void findOne() {
        assertThrows(IllegalArgumentException.class,()->{
            String id = null;
            Student s = studenti.findOne(id);
        });
        Student s = studenti.findOne("12318");
        assertNotNull(s);
        s = studenti.findOne("12319");
        assertNull(s);
    }

    @Test
    void findAll() {
        int count = 0;
        for(Student s:studenti.findAll())
            count++;
        assertEquals(count,1);
    }

    @Test
    void save() {
        assertNull(studenti.save(new Student("12319","Puiut Frumos","222","a@yahoo.com","Bla Cla")));
        assertNotNull(studenti.save(new Student("12318","Hogea Cristina","224","c@yahoo.com","Sipos Roxana")));
    }

    @Test
    void delete() {
        assertThrows(IllegalArgumentException.class,()->{
            studenti.delete(null);
        });
        assertNull(studenti.delete("13456"));
        assertNotNull(studenti.delete("12318"));
    }

    @Test
    void update() {
        assertNull(studenti.update(new Student("12318","Bla Bla","222","c@yahoo.com","Bla Bla")));
        assertNotNull(studenti.update(new Student("12319","Bla Bla","222","c@yahoo.com","Bla Bla")));
    }
}