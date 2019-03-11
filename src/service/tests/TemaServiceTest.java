package service.tests;

import domain.Tema;
import exceptions.ServiceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.inMemory.TemaInMemoryRepository;
import service.TemaService;
import validator.TemaValidator;

import static org.junit.jupiter.api.Assertions.*;

class TemaServiceTest {
    private TemaService service;

    @BeforeEach
    void setUp() {
        service = new TemaService(new TemaInMemoryRepository(new TemaValidator()));
        service.save(new Tema(1,"bla",3,2));
        service.save(new Tema(2,"faina",10,7));

    }

    @AfterEach
    void tearDown() {
        service=null;
    }

    @Test
    void findOne() {
        assertNotNull(service.findOne(1));
    }

    @Test
    void findAll() {
        int count = 0;
        for(Tema tema:service.findAll())
            count++;
        assertEquals(count,2);
    }

    @Test
    void save() {
        assertThrows(ServiceException.class,()->{
            service.save(new Tema(1,"grea",9,6));
        });
        assertDoesNotThrow(()->{
            service.save(new Tema(4,"usoara",7,4));
        });
    }

    @Test
    void delete() {
        assertThrows(ServiceException.class,()->{
            service.delete(10);
        });
        assertDoesNotThrow(()->{
            service.delete(1);
        });
    }

    @Test
    void update() {
        assertThrows(ServiceException.class,()->{
            service.update(new Tema(10,"interfete",5,4));
        });
        assertDoesNotThrow(()->{
            service.update(new Tema(1,"clase",2,1));
        });
    }

    @Test
    void prelungireDeadline() {
        assertThrows(ServiceException.class,()->{
            service.prelungireDeadline(10,10);
        });
        assertThrows(ServiceException.class,()->{
            service.prelungireDeadline(1,15);
        });
        assertDoesNotThrow(()->{
            service.prelungireDeadline(2,11);
        });
    }
}