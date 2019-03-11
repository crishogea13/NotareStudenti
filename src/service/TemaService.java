package service;

import domain.Tema;
import exceptions.ServiceException;
import repository.CrudRepository;
import utils.Utils;
import exceptions.ValidationException;

/**
 * Clasa ce defineste un service pentru teme
 */
public class TemaService {
    private CrudRepository<Integer, Tema> teme;

    /**
     * Constructorul cu parametri al clasei
     * @param teme - repository ce stocheaza entitati de tip Tema avand un ID de tip Integer
     */
    public TemaService(CrudRepository<Integer, Tema> teme) {
        this.teme = teme;
    }

    /**
     *
     * @param id - String
     *           - id-ul entitatii de tip Tema pe care dorim sa o returnam
     * @return entitatea Tema cu id-ul specificat
     * sau null - daca nu exista nicio entitate Tema cu id-ul dat
     * @throws ValidationException
     * daca id-ul dat nu este un numar intreg
     * @throws IllegalArgumentException
     * daca id-ul este null
     */
    public Tema findOne(Integer id){
        return teme.findOne(id);
    }

    public Iterable<Tema> findAll(){
        return teme.findAll();
    }

    /**
     *
     * @param idTema - String
     * @param descriere - String
     * @param deadline - String
     * @param saptPrimire - String
     * @throws ValidationException
     * daca idTema, deadline-ul si saptamana de primire a temei nu sunt numere intregi
     * @throws ValidationException
     * daca entitatea Tema pe care dorim sa o stocam nu este valida
     * @throws IllegalArgumentException
     * daca entitatea Tema pe care dorim sa o stocam este null
     * @throws ValidationException
     * daca exista deja o entitate Tema care are id-ul dat
     */
    public void save(Tema tema){
        if(teme.save(tema)!=null)
            throw new ServiceException("Exista deja o tema cu acest ID, deci adaugarea nu s-a efectuat!");
    }

    public void delete(Integer id){
        if(teme.delete(id)==null)
            throw new ServiceException("Nu exista nicio tema cu id-ul introdus, deci stergerea nu a avut loc!");
    }

    public void update(Tema t){
        if(teme.update(t)!=null)
            throw new ServiceException("Nu exista nicio tema cu ID-ul specificat, deci modificarea nu a avut loc!");
    }

    /**
     *
     * @param idTema - String
     *               - id-ul temei al carei deadline dorim sa-l prelungim
     * @param saptamaniAdaugate - String
     *                          - numarul de saptamani cu care dorim sa prelungim deadline-ul actual
     * @throws ValidationException
     * daca id-ul dat nu este numar intreg
     * daca nr. de sapt. cu care dorim sa prelungim deadline-ul nu este numar intreg
     * daca nr. de saptamani adaugate la deadline este mai mic decat 1
     * daca nu exista nicio tema cu id-ul introdus
     * daca saptamana curenta din semestru este mai mare decat deadline-ul temei intrucat asta inseamna ca tema deja a fost predata
     * daca nu se poate prelungi deadline-ul actual cu numarul de saptamani introduse deoarece acesta este prea mare
     */
    public void prelungireDeadline(Integer idTema, Integer newDeadline) {
        Tema tema = teme.findOne(idTema);
        if (tema == null)
            throw new ServiceException("Nu exista nicio tema cu ID-ul introdus!");
        if (newDeadline > 14)
            throw new ServiceException("Deadline-ul unei teme poate fi maxim saptamana 14!");
        int deadline = tema.getDeadline();
        if(newDeadline<=deadline)
            throw new ServiceException("Noul deadline trebuie sa fie mai tarziu decat deadline-ul actual!");
        int saptamanaCurenta= Utils.getSaptamanaCurenta();
        if (saptamanaCurenta == 14)
            saptamanaCurenta--;
        if (saptamanaCurenta > deadline)
            throw new ServiceException("Deadline-ul nu mai poate fi modificat! Aceasta tema a fost deja predata");
        //tema.setDeadline(newDeadline);
        teme.update(new Tema(idTema, tema.getDescriere(), newDeadline, tema.getSaptPrimire()));
    }
}
