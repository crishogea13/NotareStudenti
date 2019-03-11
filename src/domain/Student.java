package domain;

import java.util.ArrayList;

/**
 * Clasa ce defineste entitatea STUDENT
 * Implementeaza interfata generica HasID, fiecare student avand ca identificator unic un String
 */
public class Student implements HasID<String> {
    private String idStudent;
    private String nume;
    private String grupa;
    private String email;
    private String cadruDidactic;

    /**
     * Constructorul cu parametri al clasei
     * @param idStudent - String
     *                  - identificator unic pentru fiecare entitate de tip Student ce reprezinta numarul matricol al acestuia
     * @param nume - String
     *             - numele si prenumele studentului
     * @param grupa - String
     *              - grupa de care apartine studentul
     *              - valori posibile: 221, 222, 223, 224, 225, 226, 227
     * @param email - String
     *              - email-ul studentului
     * @param cadruDidactic - String
     *                      - numele si prenumele cadrului didactic ce coordoneaza laboratorul de MAP
     */
    public Student(String idStudent, String nume, String grupa, String email, String cadruDidactic){
        this.idStudent = idStudent;
        this.nume = nume;
        this.grupa = grupa;
        this.email = email;
        this.cadruDidactic = cadruDidactic;
    }

    /**
     * Getter pentru numele studentului
     * @return String - numele studentului
     */
    public String getNume() {
        return nume;
    }

    /**
     * Setter pentru numele studentului
     * @param nume - String
     *             - noul nume ce va fi atribuit studentului
     */
    public void setNume(String nume) {
        this.nume = nume;
    }

    /**
     * Getter pentru grupa studentului
     * @return String - grupa din care face parte studentul
     */
    public String getGrupa() {
        return grupa;
    }

    /**
     * Setter pentru grupa studentului
     * @param grupa - String
     *              - noua grupa ce va fi atribuita studentului
     */
    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    /**
     * Getter pentru email-ul studentului
     * @return String - email-ul studentului
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter pentru email-ul studentului
     * @param email - String
     *              - noul email ce va fi atribuit studentului
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter pentru cadrul didactic ce indruma studentul la laborator
     * @return String - numele cadrului didactic
     */
    public String getCadruDidactic() {
        return cadruDidactic;
    }

    /**
     * Setter pentru cadrul didactic indrumator
     * @param cadruDidactic - String
     *                      - noul cadru didactic ce va fi atribuit studentului
     */
    public void setCadruDidactic(String cadruDidactic) {
        this.cadruDidactic = cadruDidactic;
    }

    /**
     * Getter pentru numarul matricol al studentului
     * @return String - identificatorul unic al studentului
     */
    @Override
    public String getID() {

        return this.idStudent;
    }

    /**
     * Setter pentru numarul matricol al studentului
     * @param s - String
     *          - noul identificator unic ce va fi atribuit studentului
     */
    @Override
    public void setID(String s) {

        this.idStudent = s;
    }

    @Override
    public String toString() {
        return "idStudent: " + idStudent + " | " +
                "nume si prenume: " + nume + " | " +
                "grupa: " + grupa + " | " +
                "email: " + email + " | " +
                "cadru didactic: " + cadruDidactic;
    }

    public static ArrayList<String> grupe(){
        ArrayList<String> grupe = new ArrayList<>();
        for(int grupa=221; grupa<=227; grupa++){
            grupe.add(""+grupa);
        }
        return grupe;
    }

}
