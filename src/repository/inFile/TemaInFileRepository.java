package repository.inFile;

import domain.Tema;
import exceptions.RepoFromFileException;
import exceptions.ValidationException;
import validator.Validator;

import java.util.List;

public class TemaInFileRepository extends AbstractInFileRepository<Integer, Tema> {

    public TemaInFileRepository(String fileName, Validator<Tema> validator) {
        super(fileName, validator);
    }

    @Override
    protected Tema extractEntity(List<String> atribute) {
        if (atribute.size() != 4)
            throw new RepoFromFileException("FISIER CORUPT! Una din liniile fisierului nu contine cele 4 atribute ale unei teme!");
        try {
            Integer id = Integer.parseInt(atribute.get(0));
            Integer deadline = Integer.parseInt(atribute.get(2));
            Integer saptPrimire = Integer.parseInt(atribute.get(3));
            return new Tema(id, atribute.get(1), deadline, saptPrimire);
        } catch (NumberFormatException e) {
            throw new RepoFromFileException("FISIER CORUPT! Una din liniile fisierului nu contine date valide pentru o tema!");
        }
    }

    @Override
    protected String toString(Tema entity) {
        return entity.getID()+"|"+entity.getDescriere()+"|"+entity.getDeadline()+"|"+entity.getSaptPrimire();
    }
}
