package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import repository.inXML.StudentXMLRepository;
import service.StudentService;
import validator.StudentValidator;
import view.StudentView;

import javax.swing.border.Border;

public class MainMenuController {

    private Stage primaryStage;
    private BorderPane meniuStudenti;
    private Pane meniuNote;
    private DropShadow shadow = new DropShadow();

    @FXML
    private Button butonStudenti;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setMeniuStudenti(BorderPane meniuStudenti) {
        this.meniuStudenti = meniuStudenti;
    }

    public void setMeniuNote(Pane meniuNote) {
        this.meniuNote = meniuNote;
    }

    @FXML
    public void handleButonStudenti(ActionEvent actionEvent) {

        this.primaryStage.getScene().setRoot(this.meniuStudenti);
    }

    public void handleMouseEntered(MouseEvent mouseEvent) {
        ((Button) mouseEvent.getSource()).setEffect(shadow);
    }

    public void handleMouseExited(MouseEvent mouseEvent) {
        ((Button) mouseEvent.getSource()).setEffect(null);
    }

    public void handleButonNote(ActionEvent actionEvent) {

        this.primaryStage.getScene().setRoot(this.meniuNote);
    }
}
