package validator.tests;

import domain.Student;
import org.junit.jupiter.api.Test;
import validator.StudentValidator;
import exceptions.ValidationException;

import static org.junit.jupiter.api.Assertions.*;

class StudentValidatorTest {
    private StudentValidator validator = new StudentValidator();
    @Test
    void validate() {
        Student s=null;
        assertThrows(IllegalArgumentException.class,()->{
           validator.validate(s);
        });
        assertThrows(ValidationException.class,()->{
            validator.validate(new Student("","Hogea Cristina","224","c@yahoo.com","Sipos Roxana"));
        });
        assertThrows(ValidationException.class,()->{
            validator.validate(new Student("12318","","224","c@yahoo.com","Sipos Roxana"));
        });
        assertThrows(ValidationException.class,()->{
            validator.validate(new Student("12318","Hogea Cristina","220","c@yahoo.com","Sipos Roxana"));
        });
        assertThrows(ValidationException.class,()->{
            validator.validate(new Student("12318","Hogea Cristina","224","c@yahoo","Sipos Roxana"));
        });
        assertThrows(ValidationException.class,()->{
            validator.validate(new Student("12318","Hogea Cristina","224","c@yahoo.com","Sipos roxana"));
        });
        assertDoesNotThrow(()->{
            validator.validate((new Student("12318","Hogea Cristina","224","c@yahoo.com","Sipos Roxana")));
        });
    }
}