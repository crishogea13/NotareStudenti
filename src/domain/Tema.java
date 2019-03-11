package domain;

import java.util.ArrayList;

/**
 * Clasa ce defineste entitatea TEMA
 * Implementeaza interfata generica HasID, fiecare tema avand ca ID unic un Integer
 */
public class Tema implements HasID<Integer> {
    private int idTema;
    private String descriere;
    private int deadline;
    private int saptPrimire;

    /**
     * Constructoul cu parametri al clasei
     * @param idTema - int
     *               - identificatorul unic pentru o tema
     * @param descriere - String
     *                  - o descriere pe scurt a temei de laborator
     * @param deadline - int
     *                 - saptamana de predare a temei de laborator
     *                 - valori posibile: {1, 2, ..., 14}
     * @param saptPrimire - int
     *      *             - saptamana in care a fost primita tema
     *      *             - valori posibile: {1, 2, ..., 14}
     */
    public Tema(int idTema, String descriere, int deadline, int saptPrimire) {
        this.idTema = idTema;
        this.descriere = descriere;
        this.deadline = deadline;
        this.saptPrimire = saptPrimire;
    }

    /**
     * Getter pentru descrierea temei
     * @return String - descrierea temei
     */
    public String getDescriere() {
        return descriere;
    }

    /**
     * Setter pentru desscrierea temei
     * @param descriere - String
     *                  - noua descriere ce va fi atribuita temei
     */
    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    /**
     * Getter pentru deadline-ul temei
     * @return int - saptamana de predare a temei
     */
    public int getDeadline() {
        return deadline;
    }

    /**
     * Setter pentru deadline-ul temei
     * @param deadline - int
     *                 - noul deadline ce va fi atribuit temei
     */
    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    /**
     * Getter pentru saptamana de primire a temei
     * @return int - saptamana in care a fost primita tema
     */
    public int getSaptPrimire() {
        return saptPrimire;
    }

    /**
     * Setter pentru saptamana de primire a temei
     * @param saptPrimire - int
     *                    - noua saptamana de primire ce va fi atribuita temei
     */
    public void setSaptPrimire(int saptPrimire) {
        this.saptPrimire = saptPrimire;
    }

    /**
     * Getter pentru id-ul temei
     * @return Integer - id-ul temei
     */
    @Override
    public Integer getID() {
        return this.idTema;
    }

    /**
     * Setter pentru id-ul temei
     * @param integer - Integer
     *                - noul id ce va fi atribuit temei
     */
    @Override
    public void setID(Integer integer) {
        this.idTema=integer;
    }

    @Override
    public String toString() {
        return "idTema: " + idTema + " | " +
                "descriere: " + descriere + " | " +
                "deadline: " + deadline + " | " +
                "saptamana primire: " + saptPrimire;
    }
}
