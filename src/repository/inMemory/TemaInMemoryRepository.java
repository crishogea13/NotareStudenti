package repository.inMemory;

import domain.Tema;
import validator.Validator;

/**
 * Clasa ce defineste un repository care stocheaza entitati de tip Tema avand un ID de tip Integer
 */
public class TemaInMemoryRepository extends AbstractInMemoryRepository<Integer, Tema> {

    /**
     * Constructorul cu parametri al clasei
     * @param validator - valideaza entitatile de tip Tema pe care dorim sa le stocam in repository
     */
    public TemaInMemoryRepository(Validator<Tema> validator) {
        super(validator);
    }
}
