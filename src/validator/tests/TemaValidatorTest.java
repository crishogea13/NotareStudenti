package validator.tests;

import domain.Tema;
import org.junit.jupiter.api.Test;
import validator.TemaValidator;
import exceptions.ValidationException;

import static org.junit.jupiter.api.Assertions.*;

class TemaValidatorTest {
    private TemaValidator validator = new TemaValidator();
    @Test
    void validate() {
        Tema t = null;
        assertThrows(IllegalArgumentException.class,()->{
            validator.validate(t);
        });
        assertThrows(ValidationException.class,()->{
            validator.validate(new Tema(0,"bla",13,14));
        });
        assertThrows(ValidationException.class,()->{
            validator.validate(new Tema(1,"",13,14));
        });
        assertThrows(ValidationException.class,()->{
            validator.validate(new Tema(5,"bla",15,14));
        });
        assertThrows(ValidationException.class,()->{
            validator.validate(new Tema(0,"bla",13,14));
        });
        assertDoesNotThrow(()->{
            validator.validate(new Tema(2,"bla",13,13));
        });
    }
}