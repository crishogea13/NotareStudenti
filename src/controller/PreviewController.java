package controller;

import domain.NotaDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.NotaAddEvent;
import utils.NotaEventType;
import utils.Observable;
import utils.Observer;

public class PreviewController implements Observable<NotaAddEvent> {

    private Observer<NotaAddEvent> observer;
    @FXML
    private Button ok;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField idStudent;

    @FXML
    private TextField nume;

    @FXML
    private TextField grupa;

    @FXML
    private TextField idTema;

    @FXML
    private TextField descriere;

    @FXML
    private TextField deadline;

    @FXML
    private TextField nota;

    @FXML
    private TextField penalizare;

    @FXML
    private TextField prezenta;

    public void setIdStudent(String idStudent) {
        this.idStudent.setText(idStudent);
        this.idStudent.setEditable(false);
    }

    public void setNume(String nume) {
        this.nume.setText(nume);
        this.nume.setEditable(false);
    }

    public void setGrupa(String grupa) {
        this.grupa.setText(grupa);
        this.grupa.setEditable(false);
    }

    public void setIdTema(String idTema) {
        this.idTema.setText(idTema);
    }

    public void setDescriere(String descriere) {
        this.descriere.setText(descriere);
        this.descriere.setEditable(false);
    }

    public void setDeadline(String deadline) {
        this.deadline.setText(deadline);
        this.deadline.setEditable(false);
    }

    public void setNota(String nota) {
        this.nota.setText(nota);
        this.nota.setEditable(false);
    }

    public void setPenalizare(String penalizare) {
        this.penalizare.setText(penalizare);
        this.penalizare.setEditable(false);
    }

    public void setPrezenta(String prezenta) {
        this.prezenta.setText(prezenta);
        this.prezenta.setEditable(false);
    }

    public void handleOK(ActionEvent actionEvent) {
        notifyAllObservers(new NotaAddEvent(NotaEventType.NOTA,new NotaDTO(idStudent.getText(),nume.getText(),grupa.getText(),Integer.parseInt(idTema.getText()),descriere.getText(),Double.parseDouble(nota.getText()))));
        handleCancel(actionEvent);
    }

    public void handleCancel(ActionEvent actionEvent) {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    @Override
    public void addObserver(Observer<NotaAddEvent> observer) {
        this.observer=observer;
    }

    @Override
    public void removeObserver(Observer<NotaAddEvent> observer) {
        return;
    }

    @Override
    public void notifyAllObservers(NotaAddEvent eveniment) {
        observer.update(eveniment);
    }
}
