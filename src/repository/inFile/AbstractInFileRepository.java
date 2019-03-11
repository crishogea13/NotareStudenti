package repository.inFile;

import domain.HasID;
import repository.inMemory.AbstractInMemoryRepository;
import exceptions.ValidationException;
import validator.Validator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractInFileRepository<ID, E extends HasID<ID>> extends AbstractInMemoryRepository<ID,E> {
    private Path path;
    public AbstractInFileRepository(String fileName, Validator<E> validator) {
        super(validator);
        this.path = Paths.get(fileName);
        loadData();
    }

    private void loadData() {
//        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
//            String linie;
//            while((linie=bufferedReader.readLine())!=null){
//                List<String> atribute = Arrays.asList(linie.split("\\|"));
//                E entity = extractEntity(atribute);
//                super.save(entity);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            //System.out.println(e);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Stream<String> lines;
        try {
            lines = Files.lines(path);
            lines.forEach(linie -> super.save(extractEntity(Arrays.asList(linie.split("\\|")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract E extractEntity(List<String> atribute);
    protected abstract String toString(E entity);

    private void writeOneToFile(E entity){
//        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName,true))) {
//            bufferedWriter.write(toString(entity));
//            bufferedWriter.newLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(toString(entity));
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeAllToFile(){
//        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
//            for(E entity:super.findAll()){
//                bufferedWriter.write(toString(entity));
//                bufferedWriter.newLine();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try (PrintWriter printWriter = new PrintWriter(path.toString())) {
            super.findAll().forEach(entity->{
                    printWriter.println(toString(entity));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public E save(E entity) throws ValidationException {
        E e = super.save(entity);
        if(e==null)
            writeOneToFile(entity);
        return e;
    }

    @Override
    public E delete(ID id) {
        E entityDeleted = super.delete(id);
        if(entityDeleted!=null)
            writeAllToFile();
        return entityDeleted;
    }

    @Override
    public E update(E entity) {
        E e = super.update(entity);
        if(e==null)
            writeAllToFile();
        return e;
    }
}
