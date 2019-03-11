package view;

import controller.StudentController;
import domain.Student;
import exceptions.RepoFromFileException;
import exceptions.ServiceException;
import exceptions.ValidationException;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.File;
import java.util.function.Predicate;

public class StudentView {
    private BorderPane borderPane;
    private TextField idStudentText,numeText,emailText,cadruDidacticText;
    private ChoiceBox<String> grupaChoiceBox;
    private Button adaugaStudent;
    private Button stergeStudent;
    private Button modificaStudent;
    private Button cancel;
    private Button back;
    private StudentController controllerStudenti;
    private TableView<Student> tabelStudenti = new TableView<>();
    private Stage primaryStage;
    private Pane meniuPrincipal;

    public StudentView(StudentController controllerStudenti) {
        this.controllerStudenti = controllerStudenti;
        initializeView();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setMeniuPrincipal(Pane meniuPrincipal) {
        this.meniuPrincipal = meniuPrincipal;
    }

    private void initializeView() {
        borderPane = new BorderPane();
        borderPane.setRight(initRight());
        borderPane.setCenter(createTable());
        borderPane.setStyle("-fx-background-color: linear-gradient(from 50% 50% to 100% 100%, #dc3e6e, #661e41)");
    }

    private VBox initRight(){
        VBox vBox = new VBox();
        vBox.getChildren().addAll(fixImage(),fixTitleStudent(),createStudent(),fixButtons(),fixBackButton());
        return vBox;
    }

    private AnchorPane fixImage(){
        AnchorPane imagineAnchorPane = new AnchorPane();
        ImageView imagine = new ImageView(new Image(new File("./src/view/imagine.jpg").toURI().toString()));
        imagine.setFitHeight(250);
        imagine.setPreserveRatio(true);
        imagineAnchorPane.getChildren().add(imagine);
        AnchorPane.setLeftAnchor(imagine,4d);
        AnchorPane.setRightAnchor(imagine,4d);
        AnchorPane.setTopAnchor(imagine,4d);
        AnchorPane.setBottomAnchor(imagine,50d);
        return imagineAnchorPane;
    }

    private AnchorPane fixTitleStudent(){
        Text student = new Text("STUDENT");
        student.setFont(Font.font("Jokerman", FontWeight.NORMAL, 25));
        student.setFill(Color.PINK);
        AnchorPane studentAnchorPane = new AnchorPane();
        studentAnchorPane.getChildren().add(student);
        AnchorPane.setLeftAnchor(student,130d);
        return studentAnchorPane;
    }

    public BorderPane getViewStudenti(){
        return borderPane;
    }

    private StackPane createTable(){
        StackPane stackPane=new StackPane();
        initializeStudentsView();
        stackPane.getChildren().add(tabelStudenti);
        return stackPane;
    }

    private void initializeStudentsView(){
        TableColumn<Student, String> idColoana = new TableColumn<>("ID");
        TableColumn<Student, String> numeColoana = new TableColumn<>("NUME");
        TableColumn<Student, String> grupaColoana = new TableColumn<>("GRUPA");
        TableColumn<Student, String> emailColoana = new TableColumn<>("EMAIL");
        TableColumn<Student, String> profesorColoana = new TableColumn<>("CADRU DIDACTIC");

        tabelStudenti.getColumns().addAll(idColoana,numeColoana,grupaColoana,emailColoana,profesorColoana);

        idColoana.setCellValueFactory(new PropertyValueFactory<Student, String>("ID")); //
        numeColoana.setCellValueFactory(new PropertyValueFactory<Student, String>("Nume"));
        grupaColoana.setCellValueFactory(new PropertyValueFactory<Student, String>("Grupa"));
        emailColoana.setCellValueFactory(new PropertyValueFactory<Student, String>("Email"));
        profesorColoana.setCellValueFactory(new PropertyValueFactory<Student, String>("CadruDidactic"));

        tabelStudenti.setItems(controllerStudenti.getModelStudenti());

        tabelStudenti.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tabelStudenti.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldvalue,newValue)->showStudentDetails(newValue) );

        tabelStudenti.getStylesheets().add(getClass().getResource("stylingTableView.css").toExternalForm());
    }

    private void showStudentDetails(Student value) {
        if (value==null) {
            clearFields();
            stergeStudent.setDisable(true);
            modificaStudent.setDisable(true);
        }
        else{
            idStudentText.setText(value.getID());
            numeText.setText(value.getNume());
            grupaChoiceBox.getSelectionModel().select(value.getGrupa());
            emailText.setText(value.getEmail());
            cadruDidacticText.setText(value.getCadruDidactic());

            stergeStudent.setDisable(false);
            modificaStudent.setDisable(false);
        }
    }

    private GridPane createStudent(){
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(5, 15, 20, 15));

        Label idStudent = new Label("ID:");
        idStudent.getStylesheets().add(getClass().getResource("stylingLabels.css").toExternalForm());
        gridPane.add(idStudent, 0, 1);
        idStudentText = new TextField();
        idStudentText.requestFocus();
        gridPane.add(idStudentText, 1, 1);

        Label nume = new Label("NUME:");
        nume.getStylesheets().add(getClass().getResource("stylingLabels.css").toExternalForm());
        gridPane.add(nume, 0, 2);
        numeText = new TextField();
        gridPane.add(numeText, 1, 2);

        Label grupa = new Label("GRUPA:");
        grupa.getStylesheets().add(getClass().getResource("stylingLabels.css").toExternalForm());
        gridPane.add(grupa, 0, 3);
        grupaChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(Student.grupe()));
        grupaChoiceBox.getSelectionModel().selectFirst();
        gridPane.add(grupaChoiceBox, 1, 3);

        Label email = new Label("EMAIL:");
        email.getStylesheets().add(getClass().getResource("stylingLabels.css").toExternalForm());
        gridPane.add(email, 0, 4);
        emailText = new TextField();
        gridPane.add(emailText, 1, 4);

        Label cadruDidactic = new Label("CADRU DIDACTIC:");
        cadruDidactic.getStylesheets().add(getClass().getResource("stylingLabels.css").toExternalForm());
        gridPane.add(cadruDidactic, 0, 5);
        cadruDidacticText = new TextField();
        gridPane.add(cadruDidacticText, 1, 5);

        return gridPane;
    }

    private void stilizeazaButoane(){
        adaugaStudent.getStylesheets().add(getClass().getResource("stylingButtons.css").toExternalForm());
        stergeStudent.getStylesheets().add(getClass().getResource("stylingButtons.css").toExternalForm());
        modificaStudent.getStylesheets().add(getClass().getResource("stylingButtons.css").toExternalForm());
        cancel.getStylesheets().add(getClass().getResource("stylingButtons.css").toExternalForm());
    }

    private void dezactiveazaButoane(){
        BooleanBinding bindForCancel = idStudentText.textProperty().isEmpty()
                .and(numeText.textProperty().isEmpty())
                .and(emailText.textProperty().isEmpty())
                .and(cadruDidacticText.textProperty().isEmpty());

        BooleanBinding bindForAdd= idStudentText.textProperty().isEmpty()
                .or(numeText.textProperty().isEmpty())
                .or(emailText.textProperty().isEmpty())
                .or(cadruDidacticText.textProperty().isEmpty());

        adaugaStudent.disableProperty().bind(bindForAdd);
        stergeStudent.setDisable(true);
        modificaStudent.setDisable(true);
        cancel.disableProperty().bind(bindForCancel);
    }

    private void actiuniButoane(){
        adaugaStudent.setOnAction(x -> handleAddStudent());
        stergeStudent.setOnAction(x -> handleDeleteStudent());
        modificaStudent.setOnAction(x -> handleUpdateStudent());
        cancel.setOnAction(x -> handleCancelButton());
    }
    private AnchorPane fixBackButton(){
        back = new Button("BACK to Main Menu");
        back.getStylesheets().add(getClass().getResource("stylingButtons.css").toExternalForm());
        back.setOnAction(x -> handleBackButton());

        AnchorPane anchorPane = new AnchorPane(back);
        AnchorPane.setTopAnchor(back,12.0);
        AnchorPane.setLeftAnchor(back,122.0);
        return anchorPane;
    }

    private void handleBackButton() {
        this.primaryStage.getScene().setRoot(this.meniuPrincipal);
    }

    private HBox fixButtons(){
        adaugaStudent=new Button("ADAUGA");
        stergeStudent=new Button("STERGE");
        modificaStudent=new Button("MODIFICA");
        cancel=new Button("CANCEL");

        stilizeazaButoane();
        dezactiveazaButoane();
        actiuniButoane();

        HBox hBoxButoane = new HBox(10);
        hBoxButoane.setAlignment(Pos.BOTTOM_RIGHT);
        hBoxButoane.getChildren().addAll(adaugaStudent,stergeStudent,modificaStudent,cancel);
        hBoxButoane.setAlignment(Pos.CENTER);

        return hBoxButoane;
    }

    private void handleUpdateStudent(){
        int index = tabelStudenti.getSelectionModel().getSelectedIndex();
        if (index<0){
            showErrorMessage("EROARE LA MODIFICARE!", "Trebuie sa selectati un student!");
            return;
        }
        Student studentVechi = tabelStudenti.getSelectionModel().getSelectedItem();
        String idStudent = idStudentText.getText();
        String nume = numeText.getText();
        String grupa = grupaChoiceBox.getValue();
        String email = emailText.getText();
        String cadruDidactic = cadruDidacticText.getText();

        try{
            controllerStudenti.modificaStudent(studentVechi,idStudent,nume,grupa,email,cadruDidactic);
            tabelStudenti.getSelectionModel().clearSelection();
        } catch (IllegalArgumentException e) {
            showErrorMessage("EROARE LA MODIFICARE!",e.getMessage());
        } catch (ValidationException e) {
            showErrorMessage("EROARE LA MODIFICARE!",e.getMessage());
        } catch (RepoFromFileException e) {
            showErrorMessage("EROARE LA MODIFICARE!",e.getMessage());
        } catch (ServiceException e) {
            showErrorMessage("EROARE LA MODIFICARE!",e.getMessage());
        }
    }

    private void handleDeleteStudent() {
        int index=tabelStudenti.getSelectionModel().getSelectedIndex();
        if (index<0) {
            showErrorMessage("EROARE LA STERGERE", "Trebuie sa selectati un student!");
            return;
        }
        Student student = tabelStudenti.getSelectionModel().getSelectedItem();
        controllerStudenti.stergeStudent(student);
        tabelStudenti.getSelectionModel().clearSelection();
    }

    private void handleAddStudent() {
        String idStudent = idStudentText.getText();
        String nume = numeText.getText();
        String grupa = grupaChoiceBox.getValue();
        String email = emailText.getText();
        String cadruDidactic = cadruDidacticText.getText();

        try {
            controllerStudenti.adaugaStudent(idStudent, nume, grupa, email, cadruDidactic);
            clearFields();
        } catch (IllegalArgumentException e) {
            showErrorMessage("EROARE LA ADAUGARE!",e.getMessage());
        } catch (ValidationException e) {
            showErrorMessage("EROARE LA ADAUGARE!",e.getMessage());
        } catch (RepoFromFileException e) {
            showErrorMessage("EROARE LA ADAUGARE!",e.getMessage());
        } catch (ServiceException e) {
            showErrorMessage("EROARE LA ADAUGARE!",e.getMessage());
        }
    }

    private void handleCancelButton(){
        tabelStudenti.getSelectionModel().clearSelection();
        clearFields();
    }

    private void clearFields(){
        idStudentText.setText("");
        numeText.setText("");
        grupaChoiceBox.getSelectionModel().selectFirst();
        emailText.setText("");
        cadruDidacticText.setText("");
    }

    private void showErrorMessage(String headerError, String textError){
        Alert messageError = new Alert(Alert.AlertType.ERROR);
        messageError.setResizable(true);
        messageError.setTitle("MESAJ EROARE");
        messageError.setHeaderText(headerError);
        messageError.setContentText(textError);
        messageError.getDialogPane().getStylesheets().add(getClass().getResource("stylingError.css").toExternalForm());
        messageError.showAndWait();
    }
}
