package repository.inMemory.tests;

import domain.Tema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.inMemory.TemaInMemoryRepository;
import validator.TemaValidator;

import static org.junit.jupiter.api.Assertions.*;

class TemaInMemoryRepositoryTest {
    private TemaInMemoryRepository teme;
    @BeforeEach
    void setUp() {
        TemaValidator validator = new TemaValidator();
        teme = new TemaInMemoryRepository(validator);
        teme.save(new Tema(1,"descriere",13,12));
    }

    @AfterEach
    void tearDown() {
        teme = null;
    }

    @Test
    void findOne() {
        assertThrows(IllegalArgumentException.class,()->{
            Integer id = null;
            Tema t = teme.findOne(id);
        });
        Tema t = teme.findOne(1);
        assertNotNull(t);
        t = teme.findOne(2);
        assertNull(t);
    }

    @Test
    void findAll() {
        int count = 0;
        for(Tema s:teme.findAll())
            count++;
        assertEquals(count,1);
    }

    @Test
    void save() {
        assertNull(teme.save(new Tema(2,"bla",10,9)));
        assertNotNull(teme.save(new Tema(1,"descriere",13,12)));
    }

    @Test
    void delete() {
        assertThrows(IllegalArgumentException.class,()->{
            teme.delete(null);
        });
        assertNull(teme.delete(2));
        assertNotNull(teme.delete(1));
    }

    @Test
    void update() {
        assertNull(teme.update(new Tema(1,"faina",13,12)));
        assertNotNull(teme.update(new Tema(2,"faina",13,12)));
    }
}