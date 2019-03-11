package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

/**
 * Clasa pentru utilitati
 */
public class Utils {
    private static LocalDate start = null;//de mutat in fisier

    /**
     *
     * @return numarul saptamanii curente din cadrul semestrului I
     */
    private static LocalDate dataInceputSemestru(){
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("./data/InceputSemestrul1"))) {
            String linie = bufferedReader.readLine();
            List<String> atributeData = Arrays.asList(linie.split("-"));
            return LocalDate.of(Integer.parseInt(atributeData.get(0)),Integer.parseInt(atributeData.get(1)),Integer.parseInt(atributeData.get(2)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static LocalDate getStart(){
        if(start==null) {
            start = dataInceputSemestru();
        }
        return start;
    }

    public static int getSaptamanaCurenta(){
        LocalDate dataCurenta = LocalDate.now();
        return (int) ChronoUnit.WEEKS.between(getStart(), dataCurenta) + 1;
    }

    public static int getSaptamana(LocalDate data){
        return (int) ChronoUnit.WEEKS.between(getStart(), data) + 1;
    }

    public static int getSaptamanaCurentaDinSemestru(){
        int saptamanaCurenta= getSaptamanaCurenta();
        if(saptamanaCurenta==13||saptamanaCurenta==14)
            saptamanaCurenta=12;
        else if(saptamanaCurenta==15||saptamanaCurenta==16)
            saptamanaCurenta-=2;
        return saptamanaCurenta;
    }
    public static int getSaptamanaCurentaDinSemestru(int saptamana){
        if(saptamana==13||saptamana==14)
            saptamana=12;
        else if(saptamana==15||saptamana==16)
            saptamana-=2;
        return saptamana;
    }

}
