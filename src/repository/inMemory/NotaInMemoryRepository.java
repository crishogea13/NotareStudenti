package repository.inMemory;

import domain.Nota;
import javafx.util.Pair;
import validator.Validator;

public class NotaInMemoryRepository extends AbstractInMemoryRepository<Pair<String, Integer>, Nota> {

    public NotaInMemoryRepository(Validator<Nota> validator) {
        super(validator);
    }
}
