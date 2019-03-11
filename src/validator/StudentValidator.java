package validator;

import domain.Student;
import exceptions.ValidationException;

/**
 * Clasa ce defineste un validator pentru studenti
 */
public class StudentValidator implements Validator<Student> {

    /**
     * Valideaza un student
     * @param entity - entitatea de tip Student care se doreste a fi validata
     * @throws IllegalArgumentException
     * daca studentul este null
     * @throws ValidationException
     * daca id-ul, numele, grupa, email-ul, cadrul didactic sunt invalide
     */
    @Override
    public void validate(Student entity) throws ValidationException {
        if(entity==null)
            throw new IllegalArgumentException("Studentul dat este null!");

        String errors = "";
        errors = errors.concat(validateStudentID(entity.getID()));
        errors = errors.concat(validateNume(entity.getNume(),"studentului"));
        errors = errors.concat(validateStudentGrupa(entity.getGrupa()));
        errors = errors.concat(validateStudentEmail(entity.getEmail()));
        errors = errors.concat(validateNume(entity.getCadruDidactic(),"cadrului didactic"));
        if(!errors.isEmpty())
            throw new ValidationException(errors);
    }

    /**
     * Valideaza id-ul unui student
     * @param id - String
     * @return String
     * String-ul vid daca id-ul este valid
     * un String ce contine eroarea intalnita la validarea id-ului
     */
    private String validateStudentID(String id){
        if(id==null)
            return "ID-ul introdus este null!\n";
        if(id.equals(""))
            return "ID-ul introdus este vid!\n";
        if(!id.matches("^[1-9][0-9]{4}$"))
            return "ID-ul introdus nu respecta tiparul numarului matricol (nu incepe cu cifra nenula sau nu contine exact 5 cifre)!\n";
        return "";
    }

    /**
     * Valideaza numele unui student / cadru didactic
     * @param nume - String
     * @param atribut - String
     *                - valori posibile: student / cadru didactic
     * @return String
     * String-ul vid daca numele este valid
     * un String ce contine eroarea intalnita la validarea numelui
     */
    private String validateNume(String nume, String atribut){
        if(nume==null)
            return "Numele "+atribut+" introdus este null!\n";
        if(nume.equals(""))
            return "Numele "+atribut+" introdus este vid!\n";
        if(!nume.matches("^[A-Z][a-z]+ [A-Z][a-z]+$"))
            return "Numele "+atribut+" introdus nu este corect!\n";
        return "";
    }

    /**
     * Valideaza grupa unui student
     * @param grupa - String
     * @return String
     * String-ul vid daca grupa este valida
     * un String ce contine eroarea intalnita la validarea grupei
     */
    private String validateStudentGrupa(String grupa){
        if(grupa==null)
            return "Grupa introdusa este null!\n";
        if(grupa.equals(""))
            return "Grupa introdusa este vid!\n";
        if(!grupa.matches("^22[1-7]$"))
            return "Grupa introdusa nu este corecta! (grupele posibile sunt: 221, 222, 223, 224, 225, 226, 227)\n";
        return "";
    }

    /**
     * Valideaza email-ul unui student
     * @param email - String
     * @return String
     * String-ul vid daca email-ul este valid
     * un String ce contine eroarea intalnita la validarea emailului
     *
     */
    private String validateStudentEmail(String email){
        if(email==null)
            return "Email-ul introdus este null!\n";
        if(email.equals(""))
            return "Email-ul introdus este vid!\n";
        if(!email.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-z]+\\.[a-z]{2,3}$"))
            return "Email-ul introdus nu este corect!\n";
        return "";
    }
}
