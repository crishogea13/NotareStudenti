package controller;

import domain.Nota;
import domain.NotaDTO;
import domain.Student;
import exceptions.ServiceException;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import service.NotaService;
import service.StudentService;
import utils.*;

import javax.naming.PartialResultException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NoteController implements Observer<NotaAddEvent> {

    @FXML
    private TableView<NotaDTO> tabelNote;

    @FXML
    private TableColumn coloanaNrMatricol;

    @FXML
    private TableColumn coloanaNumeStudent;

    @FXML
    private TableColumn grupaColoana;

    @FXML
    private TableColumn coloanaIDTema;

    @FXML
    private TableColumn coloanaDescriere;

    @FXML
    private TableColumn coloanaNota;

    @FXML
    private ComboBox<String> comboBoxTeme;

    @FXML
    private ToggleGroup toggleGroup1;

    @FXML
    private ToggleButton toggleMotivat;

    @FXML
    private ToggleButton toggleNemotivat;

    @FXML
    private TextField numeStudent;

    @FXML
    private ContextMenu meniuStudenti;

    @FXML
    private Button butonAdauga;

    @FXML
    private TextField notaTextField;

    @FXML
    private TextArea feedbackTextArea;

    @FXML
    private Label grupa;

    @FXML
    private ComboBox<String> temaFiltru;

    @FXML
    private TextField numeFiltru;

    @FXML
    private ComboBox grupaFiltru;

    @FXML
    private ContextMenu studentiFiltru;

    @FXML
    private Button cancel;

    @FXML
    private DatePicker from;

    @FXML
    private DatePicker to;

    private NotaService serviceNote;
    private ObservableList<NotaDTO> model;

    private Stage primaryStage;
    private Pane meniuPrincipal;

    @FXML
    public void initialize(){
        this.model = FXCollections.observableArrayList();
        System.out.println("model note");

        coloanaNrMatricol.setCellValueFactory(new PropertyValueFactory<NotaDTO,String>("IdStudent"));
        coloanaNumeStudent.setCellValueFactory(new PropertyValueFactory<NotaDTO,String>("NumeStudent"));
        grupaColoana.setCellValueFactory(new PropertyValueFactory<NotaDTO,String>("Grupa"));
        coloanaIDTema.setCellValueFactory(new PropertyValueFactory<NotaDTO,Integer>("IdTema"));
        coloanaDescriere.setCellValueFactory(new PropertyValueFactory<NotaDTO,String>("DescriereTema"));
        coloanaNota.setCellValueFactory(new PropertyValueFactory<NotaDTO,Double>("Valoare"));

        tabelNote.setItems(model);

        initializeNotaTextField();
        initToggles();
        initializeFeedback();
        initAddButton();
        initFiltruGrupa();
        initGrupaText();
        initDates();

        temaFiltru.valueProperty().addListener(o->handleFilter());
        numeFiltru.textProperty().addListener(o->handleFilter());
        grupaFiltru.valueProperty().addListener(o->handleFilter());
        from.valueProperty().addListener(o->handleFilter());
        to.valueProperty().addListener(o->handleFilter());
    }
    private void initDates(){
        Callback<DatePicker, DateCell> dayCellFactory = new Callback<>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isAfter(LocalDate.now()))
                            setDisable(true);
                    }
                };
            }
        };
        from.setDayCellFactory(dayCellFactory);
        to.setDayCellFactory(dayCellFactory);
    }

    public void handleCancel(){
        temaFiltru.getSelectionModel().clearSelection();
        numeFiltru.setText("");
        grupaFiltru.getSelectionModel().clearSelection();
        from.setValue(null);
        to.setValue(null);
    }
    private void handleFilter() {
        Predicate<NotaDTO> dupaTema = nota -> {
            if(temaFiltru.getValue()==null)
                return true;
            return nota.getIdTema()==Integer.parseInt(getIdTema(temaFiltru.getValue()));
        };
        Predicate<NotaDTO> dupaNume = nota -> {
            if(numeFiltru.getText().isEmpty())
                return true;
            if(numeFiltru.getText().equals("INCORECT")) {
                model.clear();
                return false;
            }
            return nota.getNumeStudent().contains(getNume(numeFiltru.getText()));
        };
        Predicate<NotaDTO> dupaGrupa = nota -> {
            if(grupaFiltru.getValue()==null)
                return true;
            return nota.getGrupa().equals(grupaFiltru.getValue());
        };

        Predicate<NotaDTO> dupaData = notaDTO -> {
            if(from.getValue()==null||to.getValue()==null)
                return true;
            if(from.getValue().isAfter(to.getValue())){
                showErrorMessage("EROARE FILTRU DATA!","Intervalul selectat nu este corect");
                to.setValue(null);
                return true;
            }
            Integer inceput = Utils.getSaptamanaCurentaDinSemestru(Utils.getSaptamana(from.getValue()));
            Integer sfarsit = Utils.getSaptamanaCurentaDinSemestru(Utils.getSaptamana(to.getValue()));
            Integer predare = serviceNote.findOne(new Pair<>(notaDTO.getIdStudent(),notaDTO.getIdTema())).getSaptamanaPredare();
            return predare>=inceput&&predare<=sfarsit;
        };

        model.setAll(serviceNote.getNotaDTOList()
                .stream()
                .filter(dupaTema.and(dupaNume).and(dupaGrupa).and(dupaData))
                .collect(Collectors.toList()));
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setMeniuPrincipal(Pane meniuPrincipal) {
        this.meniuPrincipal = meniuPrincipal;
    }

    public void setServiceNote(NotaService serviceNote) {
        this.serviceNote = serviceNote;
        serviceNote.addObserver(this);
        populateModel();
        initComboBoxTeme();
        initFiltruTeme();
    }
    private void initGrupaText(){
        numeStudent.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue&&!numeStudent.getText().equals("")&&!numeStudent.getText().equals("INCORECT")){
                grupa.setText(getGrupa(numeStudent.getText()));
                System.out.println("da");
            }
        });
    }
    private void initializeNotaTextField(){
        notaTextField.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue==false&&!notaTextField.getText().isEmpty()) {//textfield-ul nu e selectat
                try{
                    Double nota = Double.parseDouble(notaTextField.getText());
                    if(nota<0||nota>10) {
                        showErrorMessage("NOTA INCORECTA!", "Nota nu e din intervalul [1-10]!");
                        notaTextField.clear();
                    }
                } catch (NumberFormatException e){
                    showErrorMessage("NOTA INCORECTA!","Nota nu e numar real!");
                    notaTextField.clear();
                }
            }
        }));
    }
    private void initToggles(){
        toggleMotivat.setDisable(true);
        toggleNemotivat.setDisable(true);
        toggleNemotivat.setUserData(true);
        toggleMotivat.setUserData(false);
    }
    private void defaultFeedback(Integer deadline){
        Integer saptamanaCurenta = Utils.getSaptamanaCurentaDinSemestru();
        notaTextField.clear();
        notaTextField.setEditable(true);
        if(toggleNemotivat.isSelected()) {
            if (deadline == saptamanaCurenta - 1)
                feedbackTextArea.setText("NOTA A FOST DIMINUATA CU 2.5 PUNCTE DIN CAUZA INTARZIERII DE 1 SAPTAMANA\n");
            else if (deadline == saptamanaCurenta - 2)
                feedbackTextArea.setText("NOTA A FOST DIMINUATA CU 5 PUNCTE DIN CAUZA INTARZIERII DE 2 SAPTAMANI\n");
            else if (deadline < saptamanaCurenta - 2) {
                feedbackTextArea.setText("NOTA ESTE 0 DIN CAUZA CA AI INTARZIAT MAI MULT DE 2 SAPTAMANI\n");
                notaTextField.setText("0.0");
                notaTextField.setEditable(false);
            }
            else {
                feedbackTextArea.setText("");
            }
        }
        else {
            feedbackTextArea.setText("");
        }
    }
    private Integer getDeadline(String tema){
        String[] atribute = tema.split(" ");
        return Integer.parseInt(atribute[atribute.length-1]);
    }
    private void initializeFeedback(){
        comboBoxTeme.valueProperty().addListener((observable, oldValue, newValue) -> {
            numeStudent.setText("");
            grupa.setText("-");
            defaultFeedback(getDeadline(newValue));
        });
        toggleGroup1.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            defaultFeedback(getDeadline(comboBoxTeme.getValue()));
        });
    }
    public void initializeAutoCompleteField(StudentService serviceStudenti){
        new AutoCompleteTextField(numeStudent,meniuStudenti,serviceStudenti);
    }

    public void initFiltruNume(StudentService serviceStudenti){
        new AutoCompleteTextField(numeFiltru,studentiFiltru,serviceStudenti);
    }
    private void initFiltruGrupa(){
        grupaFiltru.setItems(FXCollections.observableArrayList(Student.grupe()));
    }
    private void populateModel() {
        model.setAll(serviceNote.getNotaDTOList());
    }

    private void initComboBoxTeme(){
        comboBoxTeme.getItems().addAll(serviceNote.getTemeMAP().values());
        comboBoxTeme.getSelectionModel().select(defaultValueComboBox());
        comboBoxTeme.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals(defaultValueComboBox())){
                toggleNemotivat.setDisable(true);
                toggleMotivat.setDisable(true);
            }
            else{
                toggleNemotivat.setDisable(false);
                toggleMotivat.setDisable(false);
                toggleNemotivat.setSelected(true);
            }
        });
    }

    private void initFiltruTeme(){
        temaFiltru.getItems().addAll(serviceNote.getTemeMAP().values());
    }

    private String defaultValueComboBox(){
        return serviceNote.getTemeMAP().get(Utils.getSaptamanaCurentaDinSemestru());
    }

    private void initAddButton(){
        butonAdauga.disableProperty().bind(numeStudent.textProperty().isEmpty()
                .or(notaTextField.textProperty().isEmpty()));
    }

    public void handleBackButton(ActionEvent actionEvent) {
        clearFields();
        handleCancel();
        this.primaryStage.getScene().setRoot(this.meniuPrincipal);
    }

    private String getNume(String student){
        return student.split(" - ")[0].split("\\. ")[1];
    }
    private String getIdStudent(String student){
        return student.split(". ")[0];
    }
    private String getGrupa(String student){
        return student.split(" - ")[1];
    }
    private String getIdTema(String tema){
        return String.valueOf(tema.charAt(0));
    }
    private String getDescriere(String tema){
        return tema.split("\\) ")[1].split(" -")[0];
    }
    private String getPenalizare(String tema,Boolean nemotivat){
        Integer saptCurenta = Utils.getSaptamanaCurentaDinSemestru();
        Integer deadline = getDeadline(tema);
        if(deadline>=saptCurenta)
            return "nu exista penalizari";
        if(!nemotivat)
            return "nu exista penalizari";
        if(deadline==saptCurenta-1)
            return "-2.5 puncte";
        if(deadline==saptCurenta-2)
            return "-5 puncte";
        return "NOTA 0! TEMA NU MAI POATE FI PREDATA!";
    }
    private String getPrezenta(Boolean nemotivat,String tema){
        if(tema.equals(defaultValueComboBox()))
            return "prezent";
        if(nemotivat==true)
            return "absent nemotivat";
        return "absent motivat";
    }

    private String getNotaFinala(String nota, String tema){
        Double notaFinala= Double.parseDouble(nota);
        Integer deadline = getDeadline(tema);
        Integer saptCurenta = Utils.getSaptamanaCurentaDinSemestru();
        if(deadline==saptCurenta-1)
            notaFinala-=2.5;
        if(deadline==saptCurenta-2)
            notaFinala-=5;
        if(notaFinala<0)
            return "0.0";
        nota=String.valueOf(notaFinala);
        return nota;
    }

    public void handleAdd(ActionEvent actionEvent) {
        String tema = comboBoxTeme.getValue();

        String student = numeStudent.getText();

        String nota= notaTextField.getText();

        Boolean nemotivat = false;
        Toggle selectat = toggleGroup1.getSelectedToggle();
        if(selectat!=null)
            nemotivat = (Boolean) selectat.getUserData();

        String feedback = feedbackTextArea.getText();

        if(serviceNote.findOne(new Pair<>(getIdStudent(student),Integer.parseInt(getIdTema(tema))))!=null) {
            showErrorMessage("NOTA EXISTENTA!", "Un student poate avea la o tema o singura nota!");
            clearFields();
        }
        else
            try {
                preview(getIdStudent(student),getNume(student),getGrupa(student),getIdTema(tema),getDescriere(tema),String.valueOf(getDeadline(tema)),getNotaFinala(nota,tema),getPenalizare(tema,nemotivat),getPrezenta(nemotivat,tema));
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    private void preview(String idStudent, String nume, String grupa, String idTema, String descriere, String deadline, String nota, String penalizare, String prezenta) throws IOException {
        Stage preview = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("./view/previewRoot.fxml"));
        VBox vBox = (VBox) loader.load();
        PreviewController previewController = loader.getController();
        previewController.setIdStudent(idStudent);
        previewController.setNume(nume);
        previewController.setGrupa(grupa);
        previewController.setIdTema(idTema);
        previewController.setDescriere(descriere);
        previewController.setDeadline(deadline);
        previewController.setNota(nota);
        previewController.setPenalizare(penalizare);
        previewController.setPrezenta(prezenta);
        previewController.addObserver(this);
        Scene scene = new Scene(vBox);
        primaryStage.setTitle("PREVIEW NOTA");
        preview.setScene(scene);
        preview.showAndWait();
    }


    private void showErrorMessage(String headerError, String textError){
        Alert messageError = new Alert(Alert.AlertType.ERROR);
        messageError.setResizable(true);
        messageError.setTitle("MESAJ EROARE");
        messageError.setHeaderText(headerError);
        messageError.setContentText(textError);
        messageError.showAndWait();
    }

    @Override
    public void update(NotaAddEvent eveniment) {
        switch (eveniment.getType()){
            case NOTA:
                serviceNote.save(new Nota(eveniment.getData().getIdStudent(), eveniment.getData().getIdTema(), eveniment.getData().getValoare(), Utils.getSaptamanaCurentaDinSemestru()), false,feedbackTextArea.getText());
                break;
            case ADD:
                model.add(eveniment.getData());
                clearFields();
                break;
        }
    }

    private void clearFields() {
        comboBoxTeme.setValue(defaultValueComboBox());
        numeStudent.setText("");
        grupa.setText("-");
        notaTextField.setText("");
        feedbackTextArea.setText("");
    }


}
