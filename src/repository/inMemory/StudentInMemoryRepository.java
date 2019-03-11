package repository.inMemory;

import domain.Student;
import validator.Validator;

/**
 * Clasa ce defineste un repository care stocheaza entitati de tip Student avand un ID de tip String
 */
public class StudentInMemoryRepository extends AbstractInMemoryRepository<String, Student> {

    /**
     * Constructorul cu parametri al clasei
     * @param validator - valideaza entitatile de tip Student pe care dorim sa le stocam in repository
     */
    public StudentInMemoryRepository(Validator<Student> validator) {
        super(validator);
    }
}
