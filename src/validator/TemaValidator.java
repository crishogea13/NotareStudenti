package validator;

import domain.Tema;
import exceptions.ValidationException;

/**
 * Clasa ce defineste un validator pentru teme
 */
public class TemaValidator implements Validator<Tema> {
    /**
     * Valideaza o tema
     * @param entity - entitatea de tip Tema care se doreste a fi validata
     * @throws IllegalArgumentException
     * daca tema este null
     * @throws ValidationException
     * daca id-ul, descrierea, deadline-ul, saptamana de primire sunt invalide
     */
    @Override
    public void validate(Tema entity) throws ValidationException {
        if(entity==null)
            throw new IllegalArgumentException("Tema este null!");
        String errors="";
        errors = errors.concat(validateTemaID(entity.getID()));
        errors = errors.concat(validateDescriere(entity.getDescriere()));
        errors = errors.concat(validatePrimirePredare(entity.getSaptPrimire(),entity.getDeadline()));
        if(!errors.isEmpty())
            throw new ValidationException(errors);
    }

    /**
     * Valideaza o saptamana
     * @param saptamana
     * @param atribut - ce reprezinta saptamana pe care dorim sa o validam
     * @return String
     * String-ul vid daca saptamana este valida
     * mesajul erorii daca saptamana nu are una din urmatoarele valori {1, 2, ..., 14}
     */
    private String validateSaptamana(int saptamana, String atribut){
        if(saptamana<1||saptamana>14)
            return "Saptamana de "+atribut+" a temei este incorecta intrucat saptamanile semestrului sunt numerotate de la 1 la 14!\n";
        return "";
    }

    /**
     * Valideaza deadline-ul si saptamana de primire
     * @param saptPrimire
     * @param deadline
     * @return String
     * String-ul vid daca deadline si saptPrimire sunt valide
     * un String ce contine mesajul erorii intalnite la validare
     */
    private String validatePrimirePredare(int saptPrimire, int deadline) {
        String errors="";
        errors = errors.concat(validateSaptamana(saptPrimire,"primire"));
        errors = errors.concat(validateSaptamana(deadline,"predare"));
        if(!errors.contentEquals(""))
            return errors;
        if(deadline<saptPrimire)
            return "Deadline-ul trebuie sa fie mai mare sau egal decat saptamana de primire a temei!\n";
        return "";
    }

    /**
     * Valideaza descrierea
     * @param descriere
     * @return String
     * String-ul vid daca descrierea e valida
     * un String ce contine mesajul erorii intalnite la validare
     */
    private String validateDescriere(String descriere) {
        if(descriere==null)
            return "Descrierea temei este null!\n";
        if(descriere.contentEquals(""))
            return "Descrierea temei este vida!\n";
        return "";
    }

    /**
     * Valideaza id-ul temei
     * @param id
     * @return String
     * String-ul vid daca id-ul este valid
     * un String ce contine mesajul erorii intalnire la validare
     */
    private String validateTemaID(Integer id) {
        if(id==null)
            return "ID-ul temei este null!\n";
        if(id<=0)
            return "ID-ul temei nu este pozitiv nenul!\n";
        return "";
    }
}
