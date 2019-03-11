package repository.inMemory;

import domain.HasID;
import repository.CrudRepository;
import exceptions.ValidationException;
import validator.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * Clasa abstracta generica ce defineste un repository cu ajutorul caruia se realizeaza persistenta datelor in memorie
 * Implementeaza interfata CrudRepository
 * @param <ID> - tipul identificatorul unic al entitatilor stocate in repository
 * @param <E> - tipul entitatilor stocate in repository; implementeaza interfata HasID intrucat
 *            - fiecare entitate din repository are un identificator (id) unic
 */
public abstract class AbstractInMemoryRepository<ID, E extends HasID<ID>> implements CrudRepository<ID, E> {
    private Map<ID, E> entities;
    private Validator<E> validator;

    /**
     * Constructorul cu parametri al clasei
     * @param validator - are rolul de a valida entitatile ce se doresc a fi stocate in repository
     */
    public AbstractInMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities = new HashMap<>();
    }

    /**
     *
     * @param id - id-ul entitatii pe care dorim sa o returnam
     * id-ul nu poate fi null
     * @return entitatea cu id-ul specificat
     * sau null - daca nu exista nicio entitate cu id-ul dat
     * @throws IllegalArgumentException
     * daca id-ul este null
     */
    @Override
    public E findOne(ID id) {
        if(id==null)
            throw new IllegalArgumentException("ID-ul introdus este null!");
        return entities.get(id);
    }

    /**
     *
     * @return toate entitatile din repository
     */
    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    /**
     *
     * @param entity
     * entitatea nu poate fi null
     * @return null- daca entitatea data a putut fi salvata
     * altfel returneaza entitatea (id-ul exista deja in repository)
     * @throws ValidationException
     * daca entitatea nu este valida
     * @throws IllegalArgumentException
     * daca entitatea data este null
     */
    @Override
    public E save(E entity) throws ValidationException {
        validator.validate(entity);
        return entities.putIfAbsent(entity.getID(),entity);
    }

    /**
     *
     * sterge entitatea cu id-ul specificat
     * @param id
     * id nu poate fi null
     * @return entitatea stearsa sau null daca nu exista nicio entitate cu id-ul dat
     * @throws IllegalArgumentException
     * daca id-ul dat este null
     */
    @Override
    public E delete(ID id) {
        if(id==null)
            throw new IllegalArgumentException("ID-ul introdus este null!");
        return entities.remove(id);
    }

    /**
     *
     * @param entity
     * entitatea nu poate fi null
     * @return null - daca entitatea a putut fi modificata,
     * altfel returneaza entitatea - (ex: id-ul nu exista).
     * @throws IllegalArgumentException
     * daca entitatea data este null
     * @throws ValidationException
     * daca entitatea data nu este valida
     */
    @Override
    public E update(E entity) {
        validator.validate(entity);
        if(entities.get(entity.getID())!=null){
            entities.put(entity.getID(),entity);
            return null;
        }
        return entity;
    }
}
