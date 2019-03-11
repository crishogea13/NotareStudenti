package service;

import domain.Nota;
import domain.NotaDTO;
import domain.Student;
import domain.Tema;
import exceptions.ServiceException;
import javafx.util.Pair;
import repository.CrudRepository;
import utils.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class NotaService implements Observable<NotaAddEvent> {
    private CrudRepository<Pair<String, Integer>, Nota> note;
    private CrudRepository<String, Student> studenti;
    private CrudRepository<Integer, Tema> teme;
    private List<Observer<NotaAddEvent>> observeri = new ArrayList<>();

    public NotaService(CrudRepository<Pair<String, Integer>, Nota> note, CrudRepository<String, Student> studenti, CrudRepository<Integer, Tema> teme) {
        this.note = note;
        this.studenti = studenti;
        this.teme = teme;
    }

    public Nota findOne(Pair<String, Integer> idNota){
        return note.findOne(idNota);
    }

    public Iterable<Nota> findAll(){
        return note.findAll();
    }

    public Map<Integer,String> getTemeMAP(){
        return StreamSupport.stream(teme.findAll().spliterator(),false)
                .collect(Collectors.toMap(tema->tema.getDeadline(),
                        tema->tema.getID()+") "+tema.getDescriere()+" - "+tema.getDeadline()));
    }

    public List<NotaDTO> getNotaDTOList(){
        return StreamSupport.stream(findAll().spliterator(),false)
                .map(nota -> {
                    String idStudent = nota.getIdStudent();
                    String numeStudent = studenti.findOne(idStudent).getNume();
                    String grupa = studenti.findOne(idStudent).getGrupa();
                    Integer idTema = nota.getIdTema();
                    String descriereTema = teme.findOne(idTema).getDescriere();
                    Double valoareNota = nota.getValoare();
                    return new NotaDTO(idStudent,numeStudent,grupa,idTema,descriereTema,valoareNota);
                })
                .collect(Collectors.toList());
    }

    public Double save(Nota nota, Boolean nemotivat,String feedback){
        validareIdNota(nota);
        if(aIntarziat(nota)&&nemotivat)
            calculeazaDepunctari(nota);
        if(note.save(nota)!=null)
            throw new ServiceException("Studentul cu nr. matricol "+nota.getIdStudent()+" a primit deja nota pentru tema cu ID-ul "+nota.getIdTema()+"!");
        notifyAllObservers(new NotaAddEvent(NotaEventType.ADD,new NotaDTO(nota.getIdStudent(),studenti.findOne(nota.getIdStudent()).getNume(),studenti.findOne(nota.getIdStudent()).getGrupa(),nota.getIdTema(),teme.findOne(nota.getIdTema()).getDescriere(),nota.getValoare())));
        adaugaInFisierStudent(nota,feedback);
        return notaMaximaPosibila(teme.findOne(nota.getIdTema()).getDeadline());
    }

    private void adaugaInFisierStudent(Nota nota, String feedback){
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./data/FisiereStudenti/".concat(nota.getIdStudent()),true))) {
            bufferedWriter.write(informatiiFisierStudent(nota,feedback));
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String informatiiFisierStudent(Nota nota, String feedback){
        return  "Tema: " + nota.getIdTema() + "\n" +
                "Nota: " + nota.getValoare() + "\n" +
                "Predata in saptamana: " + nota.getSaptamanaPredare() + "\n" +
                "Deadline: " + teme.findOne(nota.getIdTema()).getDeadline() + "\n" +
                "Feedback:\n" + feedback.replace(";","\n") + "\n";
    }

    private double notaMaximaPosibila(int deadline){
//        int diferentaSaptamani = Utils.getSaptamanaCurentaDinSemestru()-deadline;
//        if(diferentaSaptamani<=0)
//            return 10;
//        if(diferentaSaptamani>2)
//            return 0;
//        return 10-2.5*diferentaSaptamani;
        Function<Integer,Double> notaMaximaPosibila = saptDeadline -> {
            int diferentaSaptamani = Utils.getSaptamanaCurentaDinSemestru()-saptDeadline;
            return diferentaSaptamani<=0 ? 10 : (diferentaSaptamani>2 ? 0 : 10-2.5*diferentaSaptamani);
        };
        return notaMaximaPosibila.apply(deadline);
    }

    private void validareIdNota(Nota nota){
        String erori = "";
        if(studenti.findOne(nota.getIdStudent())==null)
            erori=erori.concat("Nu exista niciun student care sa aiba acest numar matricol!\n");
        if(teme.findOne(nota.getIdTema())==null)
            erori=erori.concat("Nu exista nicio tema care sa aiba ID-ul specificat!");
        if(!erori.isEmpty())
            throw new ServiceException(erori);
    }

    private boolean aIntarziat(Nota nota){
        Predicate<Nota> aIntarziat = grade -> grade.getSaptamanaPredare()>teme.findOne(grade.getIdTema()).getDeadline();
        return aIntarziat.test(nota);
    }
    private void calculeazaDepunctari(Nota nota){
        int diferentaPredareDeadline = nota.getSaptamanaPredare()-teme.findOne(nota.getIdTema()).getDeadline();
//        int deadlineTema = teme.findOne(nota.getIdTema()).getDeadline();
        Double valoare = nota.getValoare();
//        if(nota.getSaptamanaPredare()==deadlineTema+1)
//            nota.setValoare(nota.getValoare()-2.5);
//        else if(nota.getSaptamanaPredare()==deadlineTema+2)
//            nota.setValoare(nota.getValoare()-5);
//        else if(nota.getSaptamanaPredare()>deadlineTema+2)
//            nota.setValoare(0.0);
//        if(nota.getValoare()<0)
//            nota.setValoare(0.0);
        Consumer<Double> seteazaNotaFinala = valoareNota -> nota.setValoare(valoareNota);
        BiFunction<Integer,Double,Double> calculeazaNotaFinala = (diferentaPredarePrimire,valoareNota) ->
                diferentaPredarePrimire==1 ? valoareNota-2.5>=0 ? valoareNota-2.5 : 0
                        : diferentaPredareDeadline==2 ? valoareNota-5>=0 ? valoareNota-5 : 0
                        : 0;
        seteazaNotaFinala.accept(calculeazaNotaFinala.apply(diferentaPredareDeadline,valoare));
    }

    @Override
    public void addObserver(Observer<NotaAddEvent> observer) {
        observeri.add(observer);
    }

    @Override
    public void removeObserver(Observer<NotaAddEvent> observer) {
        observeri.remove(observer);
    }

    @Override
    public void notifyAllObservers(NotaAddEvent eveniment) {
        observeri.forEach(observer -> observer.update(eveniment));
    }
}
