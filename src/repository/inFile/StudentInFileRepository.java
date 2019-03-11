package repository.inFile;

import domain.Student;
import exceptions.RepoFromFileException;
import exceptions.ValidationException;
import validator.Validator;

import java.util.List;

public class StudentInFileRepository extends AbstractInFileRepository<String, Student> {

    public StudentInFileRepository(String fileName, Validator<Student> validator) {
        super(fileName, validator);
    }

    @Override
    protected Student extractEntity(List<String> atribute) {
        if(atribute.size()!=5)
            throw new RepoFromFileException("FISIER CORUPT! Una din liniile fisierului nu contine cele 5 atribute ale unui student!");
        return new Student(atribute.get(0),atribute.get(1),atribute.get(2),atribute.get(3),atribute.get(4));
    }

    @Override
    protected String toString(Student entity) {
        return entity.getID()+"|"+entity.getNume()+"|"+entity.getGrupa()+"|"+entity.getEmail()+"|"+entity.getCadruDidactic();
    }

}
