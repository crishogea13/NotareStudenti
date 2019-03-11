package validator;

import domain.Nota;
import exceptions.ValidationException;

public class NotaValidator implements Validator<Nota> {

    @Override
    public void validate(Nota entity) throws ValidationException {
        if(entity==null)
            throw new IllegalArgumentException("Nota este null!");
        String eroare = validareValoareNota(entity.getValoare());
        if(!eroare.isEmpty())
            throw new ValidationException(eroare);
    }

    private String validareValoareNota(Double valoare){
        if(valoare>10)
            return "Nota maxima este 10!";
        else if(valoare<0)
            return "Nota minima este 0!";
        return "";
    }
}
