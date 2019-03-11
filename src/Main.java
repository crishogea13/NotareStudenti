import controller.MainMenuController;
import controller.NoteController;
import controller.StudentController;
import domain.Nota;
import domain.Student;
import domain.Tema;
import exceptions.RepoFromFileException;
import javafx.application.Application;
import javafx.css.StyleClass;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import repository.CrudRepository;
import repository.inFile.NotaInFileRepository;
import repository.inFile.StudentInFileRepository;
import repository.inFile.TemaInFileRepository;
import repository.inMemory.TemaInMemoryRepository;
import repository.inXML.NotaXMLRepository;
import repository.inXML.StudentXMLRepository;
import repository.inXML.TemaXMLRepository;
import service.NotaService;
import service.StudentService;
import service.TemaService;
import ui.Consola;
//import ui.UI;
import utils.Utils;
import validator.NotaValidator;
import validator.StudentValidator;
import validator.TemaValidator;
import validator.Validator;
import view.StudentView;

import javax.swing.text.DateFormatter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Stream;

public class Main extends Application {
    private CrudRepository<String,Student> repositoryStudenti;
    private StudentService serviceStudenti;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.repositoryStudenti = new StudentXMLRepository("./data/Studenti.xml",new StudentValidator());
        System.out.println("Repo studenti");
        this.serviceStudenti = new StudentService(this.repositoryStudenti);

        initializeStage(primaryStage);
    }

    private void initializeStage(Stage primaryStage) throws IOException{

        // meniul principal

        FXMLLoader loader = new FXMLLoader(getClass().getResource("./view/mainMenuView.fxml"));
        Pane pane = (Pane) loader.load();
        MainMenuController mainMenuController = loader.getController();
        Scene scene = new Scene(pane);
        primaryStage.setTitle("APLICATIE CATALOG");
        primaryStage.getIcons().add(new Image("file:./src/icon.png"));
        primaryStage.setScene(scene);
        mainMenuController.setPrimaryStage(primaryStage);

        mainMenuController.setMeniuStudenti(getViewStudenti(primaryStage,pane));

        FXMLLoader noteLoader = new FXMLLoader(getClass().getResource("./view/noteView.fxml"));
        Pane meniuNote = (Pane) noteLoader.load();
        NoteController noteController = noteLoader.getController();
        mainMenuController.setMeniuNote(meniuNote);
        noteController.setPrimaryStage(primaryStage);
        noteController.setMeniuPrincipal(pane);
        noteController.setServiceNote(getServiceNote());
        noteController.initializeAutoCompleteField(serviceStudenti);
        noteController.initFiltruNume(serviceStudenti);

        primaryStage.show();
    }

    private BorderPane getViewStudenti(Stage primaryStage, Pane meniuPrincipal) {
        StudentController controllerStudenti = new StudentController(this.serviceStudenti);
        StudentView viewStudenti = new StudentView(controllerStudenti);
        System.out.println("S-a creat un view studenti");
        viewStudenti.setPrimaryStage(primaryStage);
        viewStudenti.setMeniuPrincipal(meniuPrincipal);
        return viewStudenti.getViewStudenti();
    }

    private NotaService getServiceNote(){
        TemaValidator temaValidator = new TemaValidator();
        NotaValidator notaValidator = new NotaValidator();
        TemaXMLRepository repoTeme = new TemaXMLRepository("./data/Teme.xml", temaValidator);
        NotaXMLRepository repoNote = new NotaXMLRepository("./data/Catalog.xml",notaValidator);
        NotaService serviceNote = new NotaService(repoNote,this.repositoryStudenti,repoTeme);
        System.out.println("Service Note");
        return serviceNote;
    }

    public static void main(String[] args) {
        launch(args);
    }
}





//public class Main {
//    private static void f1(){
//        System.out.println("f1");
//    }
//    private static void f2(){
//        System.out.println("f2");
//    }
//    public static void main(String[] args) {
//        StudentValidator studentValidator = new StudentValidator();
//        TemaValidator temaValidator = new TemaValidator();
//        NotaValidator notaValidator = new NotaValidator();
//        try {
//            StudentXMLRepository repoStudenti = new StudentXMLRepository("./data/Studenti.xml",studentValidator);
//            TemaXMLRepository repoTeme = new TemaXMLRepository("./data/Teme.xml", temaValidator);
//            NotaXMLRepository repoNote = new NotaXMLRepository("./data/Catalog.xml",notaValidator);
//            StudentService serviceStudenti = new StudentService(repoStudenti);
//            TemaService serviceTeme = new TemaService(repoTeme);
//            NotaService serviceNote = new NotaService(repoNote,repoStudenti,repoTeme);
//            UI ui = new UI(serviceStudenti, serviceTeme,serviceNote);
//            ui.runUI();
//        } catch (RepoFromFileException e){
//            System.out.println(e);
//        }
//
//    }
//
//}
