package service.tests;

import domain.Student;
import exceptions.ServiceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.inMemory.StudentInMemoryRepository;
import service.StudentService;
import validator.StudentValidator;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
    private StudentService service;

    @BeforeEach
    void setUp() {
        service = new StudentService(new StudentInMemoryRepository(new StudentValidator()));
        service.save(new Student("12318","Hogea Cristina","224","c@yahoo.com","Sipos Roxana"));
        service.save(new Student("12319","Anton Maria","221","a@yahoo.com","Sipos Roxana"));
    }

    @AfterEach
    void tearDown() {
        service = null;
    }

    @Test
    void findOne() {
        assertNotNull(service.findOne("12318"));
    }

    @Test
    void findAll() {
        int count = 0;
        for(Student s:service.findAll())
            count++;
        assertEquals(count,2);
    }

    @Test
    void save() {
        assertThrows(ServiceException.class,()->{
            service.save(new Student("12318","Bla Bla","222","a@yahoo.com","Serban Camelia"));
        });
        assertDoesNotThrow(()->{
            service.save(new Student("12320","Bla Bla","222","a@yahoo.com","Serban Camelia"));
        });
    }

    @Test
    void delete() {
        assertThrows(ServiceException.class,()->{
            service.delete("12230");
        });
        assertDoesNotThrow(()->{
            service.delete("12318");
        });
    }

    @Test
    void update() {
        assertThrows(ServiceException.class,()->{
            service.update(new Student("12378","Bla Bla","222","a@yahoo.com","Serban Camelia"));
        });
        assertDoesNotThrow(()->{
            service.update(new Student("12318","Bla Bla","222","a@yahoo.com","Serban Camelia"));
        });
    }
}