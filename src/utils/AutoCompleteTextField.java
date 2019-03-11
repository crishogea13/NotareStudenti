package utils;

import domain.Student;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableSet;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import service.StudentService;

import javax.sound.midi.Soundbank;
import java.util.*;
import java.util.stream.StreamSupport;

public class AutoCompleteTextField implements Observer<StudentChangeEvent>{

    private TextField textField;
    private SortedSet<String> studenti;
    private ContextMenu meniuStudenti;
    private ComboBox<String> comboBox;

    public AutoCompleteTextField(TextField numeStudent, ContextMenu menu, StudentService serviceStudenti) {
        this.textField = numeStudent;
        serviceStudenti.addObserver(this);
        studenti = new TreeSet<>();
        populeazaListaSugestii(serviceStudenti);
        meniuStudenti = menu;
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                populeazaMeniuStudenti(new ArrayList<>(studenti));
                meniuStudenti.show(numeStudent, Side.BOTTOM, 0, 0);
            }
            else if(!studenti.contains(textField.getText()))
                textField.setText("INCORECT");
        });
        textField.textProperty().addListener( (observable, oldValue, newValue) -> {
                if (textField.getText().length() == 0)
                    meniuStudenti.hide();
                else {
                    List<String> cautaStudenti = new LinkedList<>();
                    studenti.forEach(student->{
                        if (student.contains(textField.getText()))
                            cautaStudenti.add(student);

                    });
                    //cautaStudenti.addAll(studenti.subSet(textField.getText(),textField.getText()+Character.MAX_VALUE));
                    if (cautaStudenti.size() > 0) {
                        populeazaMeniuStudenti(cautaStudenti);
                        meniuStudenti.show(numeStudent, Side.BOTTOM,0,0);
                    }
                    else
                        meniuStudenti.hide();
                }
            });
    }

    private void populeazaListaSugestii(StudentService serviceStudenti) {
        serviceStudenti.findAll().forEach(student -> studenti.add(student.getID()+". "+student.getNume()+" - "+student.getGrupa()));
    }

    private void populeazaMeniuStudenti(List<String> cautaStudenti) {
        List<MenuItem> menuItems = new LinkedList<>();
        cautaStudenti.forEach(student -> {
            MenuItem item = new MenuItem(student);
            item.setOnAction(x -> {
                textField.setText(student);
                meniuStudenti.hide();
            });
            menuItems.add(item);
        });
        meniuStudenti.getItems().clear();
        meniuStudenti.getItems().addAll(menuItems);
    }

    @Override
    public void update(StudentChangeEvent eveniment) {
        System.out.println("Modificare in studenti");
        switch (eveniment.getType()){
            case ADD:{
                studenti.add(eveniment.getData().getID()+". "+eveniment.getData().getNume()+" - "+eveniment.getData().getGrupa());
                break;
            }
            case DELETE:{
                studenti.remove(eveniment.getData().getID()+". "+eveniment.getData().getNume()+" - "+eveniment.getData().getGrupa());
                break;
            }
            case UPDATE:{
                studenti.remove(eveniment.getOldData().getID()+". "+eveniment.getOldData().getNume()+" - "+eveniment.getOldData().getGrupa());
                studenti.add(eveniment.getData().getID()+". "+eveniment.getData().getNume()+" - "+eveniment.getData().getGrupa());
                break;
            }
        }
    }
}
