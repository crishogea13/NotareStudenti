package repository.inFile;

import domain.Nota;
import domain.Student;
import domain.Tema;
import exceptions.RepoFromFileException;
import exceptions.ValidationException;
import javafx.util.Pair;
import repository.CrudRepository;
import validator.Validator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class NotaInFileRepository extends AbstractInFileRepository<Pair<String, Integer>, Nota> {

    public NotaInFileRepository(String fileName, Validator<Nota> validator) {
        super(fileName, validator);
    }

    @Override
    protected Nota extractEntity(List<String> atribute) {
        if (atribute.size() != 4)
            throw new RepoFromFileException("FISIER CORUPT! Una din liniile fisierului nu contine cele 5 atribute ale unei note!");
        try {
            String idStudent = atribute.get(0);
            Integer idTema = Integer.parseInt(atribute.get(1));
            Double valoare = Double.parseDouble(atribute.get(2));
            Integer saptamanaPredare = Integer.parseInt(atribute.get(3));
            return new Nota(idStudent,idTema,valoare,saptamanaPredare);
        } catch (NumberFormatException e) {
            throw new RepoFromFileException("FISIER CORUPT! Una din liniile fisierului nu contine date valide pentru o nota!");
        }
    }

    @Override
    protected String toString(Nota entity) {
        return  entity.getID().getKey() + "|" +
                entity.getID().getValue() + "|" +
                entity.getValoare() + "|" +
                entity.getSaptamanaPredare();
    }
}
