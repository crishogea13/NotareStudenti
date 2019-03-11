package service;

import domain.Student;
import exceptions.ServiceException;
import repository.CrudRepository;
import exceptions.ValidationException;
import utils.ChangeEventType;
import utils.Observable;
import utils.Observer;
import utils.StudentChangeEvent;

import java.util.ArrayList;
import java.util.List;

public class StudentService implements Observable<StudentChangeEvent> {
    private CrudRepository<String, Student> studenti;
    private List<Observer<StudentChangeEvent>> observeri = new ArrayList<>();

    public StudentService(CrudRepository<String, Student> studenti) {
        System.out.println("service studenti nou");
        this.studenti = studenti;
    }

    public Student findOne(String idStudent){
        return studenti.findOne(idStudent);
    }

    public Iterable<Student> findAll(){
        return studenti.findAll();
    }

    public void save(Student s){
        if(studenti.save(s)!=null)
            throw new ServiceException("Studentul nu poate fi stocat! Exista deja un student care are ID-ul introdus!");
        notifyAllObservers(new StudentChangeEvent(ChangeEventType.ADD,s));
    }
    public void delete(String id){
        Student studentSters = studenti.delete(id);
        if(studentSters==null)
            throw new ServiceException("Nu exista niciun student cu ID-ul specificat, deci stergerea nu a avut loc!");
        notifyAllObservers(new StudentChangeEvent(ChangeEventType.DELETE,studentSters));
    }
    public void update(Student s){
        Student studentVechi = findOne(s.getID());
        if(studenti.update(s)!=null)
            throw new ServiceException("Nu exista niciun student cu ID-ul specificat, deci modificarea nu a avut loc!");
    }

    public void update(Student studentVechi, Student studentNou){
        update(studentNou);
        notifyAllObservers(new StudentChangeEvent(ChangeEventType.UPDATE,studentNou,studentVechi));
    }

    @Override
    public void addObserver(Observer<StudentChangeEvent> observer) {
        observeri.add(observer);
    }

    @Override
    public void removeObserver(Observer<StudentChangeEvent> observer) {
        observeri.remove(observer);
    }

    @Override
    public void notifyAllObservers(StudentChangeEvent eveniment) {
        observeri.forEach(observer -> observer.update(eveniment));
    }
}
