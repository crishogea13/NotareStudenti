package domain;

public class NotaDTO {
    private String idStudent;
    private String numeStudent;
    private String grupa;
    private Integer idTema;
    private String descriereTema;
    private Double valoare;

    public NotaDTO(String idStudent, String numeStudent, String grupa, Integer idTema, String descriereTema, Double valoare) {
        this.idStudent = idStudent;
        this.numeStudent = numeStudent;
        this.grupa = grupa;
        this.idTema = idTema;
        this.descriereTema = descriereTema;
        this.valoare = valoare;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public String getNumeStudent() {
        return numeStudent;
    }

    public Integer getIdTema() {
        return idTema;
    }

    public String getGrupa() {
        return grupa;
    }

    public String getDescriereTema() {
        return descriereTema;
    }

    public Double getValoare() {
        return valoare;
    }
}
