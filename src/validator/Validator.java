package validator;

import exceptions.ValidationException;

/**
 * Interfata pentru validator
 * @param <E> tipul entitatilor care vor fi validate
 */
public interface Validator<E> {

    /**
     *
     * @param entity - entitatea de tip E care se doreste a fi validata
     * @throws ValidationException
     */
    void validate(E entity) throws ValidationException;
}
