package controller;

import domain.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import service.StudentService;
import utils.Observer;
import utils.StudentChangeEvent;

public class StudentController implements Observer<StudentChangeEvent> {
    private StudentService serviceStudenti;
    private ObservableList<Student> modelStudenti;

    public StudentController(StudentService serviceStudenti) {
        this.serviceStudenti = serviceStudenti;
        serviceStudenti.addObserver(this);
        modelStudenti = FXCollections.observableArrayList();
        populateModel();
    }

    private void populateModel() {
        serviceStudenti.findAll().forEach(student -> modelStudenti.add(student));
    }

    public ObservableList<Student> getModelStudenti(){
        return modelStudenti;
    }

    public void adaugaStudent(String idStudent, String nume, String grupa, String email, String cadruDidactic){
        Student student = new Student(idStudent,nume,grupa,email,cadruDidactic);
        serviceStudenti.save(student);
    }

    public void stergeStudent(Student student){
        serviceStudenti.delete(student.getID());
    }

    public void modificaStudent(Student studentVechi, String idStudent, String nume, String grupa, String email, String cadruDidactic){
        serviceStudenti.update(studentVechi,new Student(idStudent,nume,grupa,email,cadruDidactic));
    }

    @Override
    public void update(StudentChangeEvent eveniment) {
        switch (eveniment.getType()){
            case ADD:{
                modelStudenti.add(eveniment.getData());
                break;
            }
            case DELETE:{
                modelStudenti.remove(eveniment.getData());
                break;
            }
            case UPDATE:{
                modelStudenti.remove(eveniment.getOldData());
                modelStudenti.add(eveniment.getData());
                break;
            }
        }
    }
}
