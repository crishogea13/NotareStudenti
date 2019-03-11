package domain;

/**
 * Interfata generica implementata de entitatile care au un identificator unic
 * @param <ID> - identificator unic
 */
public interface HasID<ID> {
    /**
     *
     * @return - identificatorul unic al entitatii
     */
    ID getID();

    /**
     * Seteaza un nou identificator pentru entitate
     * @param id - noul identificator unic
     */
    void setID(ID id);
}
