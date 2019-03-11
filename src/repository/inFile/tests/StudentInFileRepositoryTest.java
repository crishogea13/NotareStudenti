package repository.inFile.tests;

import domain.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.inFile.StudentInFileRepository;
import validator.StudentValidator;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentInFileRepositoryTest {
    private static final File file = new File("./data/StudentiTest");
    private static final StudentValidator validator = new StudentValidator();

    @BeforeEach
    void setUp() {
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StudentInFileRepository repository = new StudentInFileRepository(file.toString(),validator);
        repository.save(new Student("12318","Hogea Cristina","224","cris@yahoo.com","Sipos Roxana"));
    }

    @AfterEach
    void tearDown() {
        file.delete();
    }

    @Test
    void toStringTest() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            assertEquals(bufferedReader.readLine(),"12318|Hogea Cristina|224|cris@yahoo.com|Sipos Roxana");
            assertNull(bufferedReader.readLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void save() {
        StudentInFileRepository studenti = new StudentInFileRepository(file.toString(),validator);
        assertNull(studenti.save(new Student("12319","Popescu Ana","223","a@yahoo.com","Bla Bla")));
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            int count=0;
            while(bufferedReader.readLine()!=null)
                count++;
            assertEquals(count,2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void delete() {
        StudentInFileRepository studenti = new StudentInFileRepository(file.toString(),validator);
        assertNotNull(studenti.delete("12318"));
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
        StudentInFileRepository studenti = new StudentInFileRepository(file.toString(),validator);
        assertNull(studenti.update(new Student("12318","Bla Bla","222","c@yahoo.com","Bla Bla")));
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String linie;
            while((linie=bufferedReader.readLine())!=null){
                List<String> atribute = Arrays.asList(linie.split("\\|"));
                assertEquals(atribute.get(0),"12318");
                assertEquals(atribute.get(1),"Bla Bla");
                assertEquals(atribute.get(2),"222");
                assertEquals(atribute.get(3),"c@yahoo.com");
                assertEquals(atribute.get(4),"Bla Bla");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void findOne() {
        StudentInFileRepository repository = new StudentInFileRepository(file.toString(),validator);
        assertThrows(IllegalArgumentException.class,()->{
            String id = null;
            Student s = repository.findOne(id);
        });
        Student s = repository.findOne("12318");
        assertNotNull(s);
        s = repository.findOne("12319");
        assertNull(s);
    }

    @Test
    void findAll() {
        StudentInFileRepository repository = new StudentInFileRepository(file.toString(),validator);
        int count = 0;
        for(Student s:repository.findAll())
            count++;
        assertEquals(count,1);
    }
}