package repository.inFile.tests;

import domain.Tema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.inFile.TemaInFileRepository;
import validator.TemaValidator;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TemaInFileRepositoryTest {
    private static final File file = new File("./data/TemeTest");
    private static final TemaValidator validator = new TemaValidator();

    @BeforeEach
    void setUp() {
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TemaInFileRepository repository = new TemaInFileRepository(file.toString(),validator);
        repository.save(new Tema(1,"tema faina",5,3));
    }

    @AfterEach
    void tearDown() {
        file.delete();
    }

    @Test
    void toStringTest() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            assertEquals(bufferedReader.readLine(),"1|tema faina|5|3");
            assertNull(bufferedReader.readLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void save() {
        TemaInFileRepository studenti = new TemaInFileRepository(file.toString(),validator);
        assertNull(studenti.save(new Tema(2,"tema usoara",10,9)));
        assertNull(studenti.save(new Tema(3,"tema grea",3,2)));
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            int count=0;
            while(bufferedReader.readLine()!=null)
                count++;
            assertEquals(count,3);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void delete() {
        TemaInFileRepository studenti = new TemaInFileRepository(file.toString(),validator);
        assertNotNull(studenti.delete(1));
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            assertNull(bufferedReader.readLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void update() {
        TemaInFileRepository studenti = new TemaInFileRepository(file.toString(),validator);
        assertNull(studenti.update(new Tema(1,"bla",7,5)));
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            assertEquals(bufferedReader.readLine(),"1|bla|7|5");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void findOne() {
        TemaInFileRepository repository = new TemaInFileRepository(file.toString(),validator);
        assertThrows(IllegalArgumentException.class,()->{
            Integer id = null;
            Tema t = repository.findOne(id);
        });
        Tema t = repository.findOne(1);
        assertNotNull(t);
        t = repository.findOne(2);
        assertNull(t);
    }

    @Test
    void findAll() {
        TemaInFileRepository repository = new TemaInFileRepository(file.toString(),validator);
        int count = 0;
        for(Tema t:repository.findAll())
            count++;
        assertEquals(count,1);
    }
}