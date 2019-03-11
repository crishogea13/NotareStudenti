package domain;

import javafx.util.Pair;

import java.time.LocalDateTime;
import java.util.Properties;

public class Nota implements HasID<Pair<String, Integer>> {
    private String idStudent;
    private Integer idTema;
    private Double valoare;
    private Integer saptamanaPredare;

    public Nota(String idStudent, Integer idTema, Double valoare, Integer saptamanaPredare) {
        this.idStudent = idStudent;
        this.idTema = idTema;
        this.valoare = valoare;
        this.saptamanaPredare = saptamanaPredare;
    }

    @Override
    public Pair<String, Integer> getID() {
        return new Pair<>(idStudent,idTema);
    }

    public String getIdStudent() {
        return idStudent;
    }

    public Integer getIdTema() {
        return idTema;
    }

    public Double getValoare() {
        return valoare;
    }

    public Integer getSaptamanaPredare() {
        return saptamanaPredare;
    }

    @Override
    public void setID(Pair<String, Integer> idStudentTema){}

    public void setValoare(Double valoare) {
        this.valoare = valoare;
    }

    @Override
    public String toString() {
        return  "Nr. matricol al studentului: " + idStudent + " | " +
                "ID-ul temei: " + idTema + " | " +
                "Nota obtinuta: " + valoare + " | " +
                "Saptamana in care a fost predata: " + saptamanaPredare;
    }
}
