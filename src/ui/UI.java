//package ui;
//
//import domain.Nota;
//import domain.Student;
//import domain.Tema;
//import exceptions.RepoFromFileException;
//import exceptions.ServiceException;
//import exceptions.UIException;
//import exceptions.ValidationException;
//import service.NotaService;
//import service.StudentService;
//import service.TemaService;
//import utils.Utils;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//
//public class UI {
//    private StudentService studenti;
//    private TemaService teme;
//    private NotaService note;
//    private Consola consola;
//
//    public UI(StudentService studenti, TemaService teme, NotaService note) {
//        this.studenti = studenti;
//        this.teme = teme;
//        this.note = note;
//        this.consola = new Consola();
//    }
//
//    private void uiAdaugaStudent(){
//        String idStudent,nume,grupa,email,cadruDidactic;
//        idStudent=consola.citesteString("Dati ID-ul studentului: ");
//        nume=consola.citesteString("Dati numele si prenumele studentului: ");
//        grupa=consola.citesteString("Dati grupa din care face parte studentul: ");
//        email=consola.citesteString("Dati email-ul studentului: ");
//        cadruDidactic=consola.citesteString("Dati cadrul didactic coordonator al studentului: ");
//        studenti.save(new Student(idStudent,nume,grupa,email,cadruDidactic));
//        System.out.println("Studentul a fost salvat!\n");
//    }
//
//    private void uiModificaStudent(){
//        String idStudent,nume,grupa,email,cadruDidactic;
//        idStudent=consola.citesteString("Dati ID-ul studentului pe care doriti sa-l modificati: ");
//        nume=consola.citesteString("Dati noul nume al studentului: ");
//        grupa=consola.citesteString("Dati noua grupa a studentului: ");
//        email=consola.citesteString("Dati noul email al studentului: ");
//        cadruDidactic=consola.citesteString("Dati noul cadru didactic coordonator al studentului: ");
//        studenti.update(new Student(idStudent,nume,grupa,email,cadruDidactic));
//        System.out.println("Studentul a fost updatat!\n");
//    }
//
//    private void uiStergeStudent(){
//        String idStudent;
//        idStudent=consola.citesteString("Dati ID-ul studentului pe care doriti sa-l stergeti: ");
//        studenti.delete(idStudent);
//        System.out.println("Studentul cu ID-ul introdus a fost sters!\n");
//    }
//
//    private void uiAfiseazaStudenti(){
////        for(Student s:studenti.findAll())
////            System.out.println(s.toString());
//        studenti.findAll().forEach(System.out::println);
//    }
//
//    private void operatiiStudenti(){
//        Map<String, Command> commandsMap = new HashMap<>();
//        commandsMap.put("1",this::uiAdaugaStudent);
//        commandsMap.put("2",this::uiModificaStudent);
//        commandsMap.put("3",this::uiStergeStudent);
//        commandsMap.put("4",this::uiAfiseazaStudenti);
//        while(true){
//            consola.meniuStudenti();
//            String comanda = consola.citesteString("Introduceti comanda dorita: ");
//            if(comanda.equals("0"))
//                break;
//            executeCommand(comanda,commandsMap);
//        }
//    }
//
//    private Tema creeazaEntitateTema(String idTema, String descriere, String deadline, String saptPrimire){
//        Integer id, predare, primire;
//        String erori="";
//        ValidareNumere validator = new ValidareNumere();
//        erori = erori.concat(validator.StringToInt(idTema,"ID-ul temei").erori);
//        id = validator.numar;
//        erori = erori.concat(validator.StringToInt(deadline,"Deadline-ul temei").erori);
//        predare= validator.numar;
//        erori = erori.concat(validator.StringToInt(saptPrimire,"Saptamana de primire a temei").erori);
//        primire = validator.numar;
//        if(!erori.isEmpty())
//            throw new UIException(erori);
//        return new Tema(id, descriere, predare, primire);
//    }
//
//    private void uiAdaugaTema() {
//        String idTema,descriere,deadline,saptPrimire;
//        idTema=consola.citesteString("Dati ID-ul temei: ");
//        descriere=consola.citesteString("Dati o scurta descriere pentru tema: ");
//        deadline=consola.citesteString("Dati deadline-ul pentru tema: ");
//        saptPrimire=consola.citesteString("Dati saptamana in care a fost primita tema: ");
//        teme.save(creeazaEntitateTema(idTema,descriere,deadline,saptPrimire));
//        System.out.println("Tema a fost salvata!\n");
//    }
//
//    private void uiModificaTema(){
//        String idTema,descriere,deadline,saptPrimire;
//        idTema=consola.citesteString("Dati ID-ul temei pe care doriti sa o modificati: ");
//        descriere=consola.citesteString("Dati noua descriere pentru tema: ");
//        deadline=consola.citesteString("Dati noul deadline pentru tema: ");
//        saptPrimire=consola.citesteString("Dati noua saptamana de primire a temei: ");
//        teme.update(creeazaEntitateTema(idTema,descriere,deadline,saptPrimire));
//        System.out.println("Tema cu ID-ul introdus a fost modificata!\n");
//    }
//
//    private void uiStergeTema(){
//        String idTema;
//        idTema=consola.citesteString("Dati ID-ul temei pe care doriti sa o stergeti: ");
//        Integer id;
//        ValidareNumere validator = new ValidareNumere();
//        if(!validator.StringToInt(idTema,"Id-ul temei ").erori.isEmpty())
//            throw new UIException(validator.erori);
//        id=validator.numar;
//        teme.delete(id);
//        System.out.println("Tema a fost stearsa!\n");
//    }
//
//    private void uiAfiseazaTeme(){
////        for(Tema tema:teme.findAll())
////            System.out.println(tema.toString());
//        teme.findAll().forEach(System.out::println);
//    }
//
//    private void uiPrelungireDeadline(){
//        String idTema,newDeadline;
//        Integer id,newD;
//        idTema=consola.citesteString("Dati ID-ul temei pentru care doriti sa modificati deadline-ul: ");
//        newDeadline=consola.citesteString("Dati noul deadline al temei: ");
//        String erori = "";
//        ValidareNumere validator = new ValidareNumere();
//        erori = erori.concat(validator.StringToInt(idTema, "ID-ul temei").erori);
//        id = validator.numar;
//        erori = erori.concat(validator.StringToInt(newDeadline, "Noul deadline").erori);
//        newD = validator.numar;
//        if(!erori.isEmpty())
//            throw new UIException(erori);
//        teme.prelungireDeadline(id,newD);
//        System.out.println("Noul deadline a fost setat cu succes!\n");
//    }
//
//    private void operatiiTeme(){
//        Map<String, Command> commandsMap = new HashMap<>();
//        commandsMap.put("1",this::uiAdaugaTema);
//        commandsMap.put("2",this::uiModificaTema);
//        commandsMap.put("3",this::uiStergeTema);
//        commandsMap.put("4",this::uiAfiseazaTeme);
//        commandsMap.put("5",this::uiPrelungireDeadline);
//        while(true){
//            consola.meniuTeme();
//            String comanda = consola.citesteString("Introduceti comanda dorita: ");
//            if(comanda.equals("0"))
//                break;
//            executeCommand(comanda,commandsMap);
//        }
//    }
//
//    private Nota creeazaEntitateNota(String idStudent, String idTema, String valoareNota, Integer saptamanaPredare){
//        Integer idT;
//        Double valoare;
//        String erori="";
//        ValidareNumere validator = new ValidareNumere();
//        erori = erori.concat(validator.StringToInt(idTema,"ID-ul temei").erori);
//        idT = validator.numar;
//        erori = erori.concat(validator.StringToDouble(valoareNota,"Nota").erori);
//        valoare= validator.numarD;
//        if(!erori.isEmpty())
//            throw new UIException(erori);
//        return new Nota(idStudent,idT,valoare,saptamanaPredare);
//
//    }
//
//    private void uiAdaugaNota() {
//        String idStudent,idTema,valoareNota,feedback;
//        idStudent=consola.citesteString("Dati nr. matricol al studentului pe care doriti sa-l notati: ");
//        idTema=consola.citesteString("Dati ID-ul temei pentru care este notat studentul: ");
//        valoareNota=consola.citesteString("Dati nota: ");
//        feedback=consola.citesteString("Dati un scurt feedback referitor la rezolvarea aleasa: ");
//        Nota nota = creeazaEntitateNota(idStudent,idTema,valoareNota, Utils.getSaptamanaCurentaDinSemestru());
//        double notaMaximaPosibila = note.save(nota);
//        note.adaugaInFisierStudent(nota,feedback);
//        System.out.println("Nota maxima posibila: "+notaMaximaPosibila);
//        System.out.println("Nota a fost salvata!\n");
//    }
//
//    private void uiAfiseazaNote() {
////        for(Nota nota:note.findAll())
////            System.out.println(nota.toString());
//        note.findAll().forEach(System.out::println);
//    }
//
//    private void operatiiNote() {
//        Map<String, Command> commandsMap = new HashMap<>();
//        commandsMap.put("1",this::uiAdaugaNota);
//        commandsMap.put("2",this::uiAfiseazaNote);
//        while(true){
//            consola.meniuNote();
//            String comanda = consola.citesteString("Introduceti comanda dorita: ");
//            if(comanda.equals("0"))
//                break;
//            executeCommand(comanda,commandsMap);
//        }
//    }
//
//    private void executeCommand(String comanda, Map<String, Command> commandsMap){
//        if(!commandsMap.containsKey(comanda))
//            System.out.println("COMANDA INVALIDA! Introduceti o alta comanda\n");
//        else try {
//            commandsMap.get(comanda).execute();
//        } catch (IllegalArgumentException e){
//            System.out.println(e);
//        } catch (ValidationException e){
//            System.out.println(e);
//        } catch (RepoFromFileException e){
//            System.out.println(e);
//        } catch (ServiceException e){
//            System.out.println(e);
//        } catch (UIException e){
//            System.out.println(e);
//        }
//    }
//    /**
//     * Clasa interna pentru validarea unui numar intreg
//     */
//    private class ValidareNumere{
//        private Integer numar;
//        private Double numarD;
//        private String erori;
//
//        private ValidareNumere() {}
//
//        /**
//         *
//         * @param numar - String-ul pe care dorim sa-l convertim in Integer
//         * @param atribut - ce reprezinta numarul (Integer-ul) care ar trebui sa se obtina dupa convertire
//         * @return obiectul de tip ValidareInt care contine:
//         * mesajul de eroare daca conversia nu a reusit, iar numarul obtinut prin conversie va fi null
//         * numarul obtinut prin conversie, iar mesajul de eroare este vid
//         */
//        private ValidareNumere StringToInt(String numar, String atribut){
//            this.numar=null;
//            this.erori="";
//            try {
//                this.numar = Integer.parseInt(numar);
//            } catch (NumberFormatException e){
//                erori = erori.concat(atribut+" nu este un numar intreg!\n");
//            }
//            finally {
//                return this;
//            }
//        }
//        private ValidareNumere StringToDouble(String numar, String atribut){
//            this.numarD=null;
//            this.erori="";
//            try {
//                this.numarD = Double.parseDouble(numar);
//            } catch (NumberFormatException e){
//                erori = erori.concat(atribut+" nu este un numar real!\n");
//            }
//            finally {
//                return this;
//            }
//        }
//    }
//
//    public void runUI(){
//        Scanner scanner = new Scanner(System.in);
//        Map<String, Command> commandsMap = new HashMap<>();
//        commandsMap.put("1",this::operatiiStudenti);
//        commandsMap.put("2",this::operatiiTeme);
//        commandsMap.put("3",this::operatiiNote);
//        while(true){
//            consola.afisareMeniu();
//            System.out.print("Introduceti comanda dorita: ");
//            String comanda = scanner.nextLine();
//            if(comanda.equals("0")){
//                System.out.println("LA REVEDERE!\n");
//                break;
//            }
//            else if(!commandsMap.containsKey(comanda))
//                System.out.println("COMANDA INVALIDA! Introduceti o alta comanda\n");
//            else commandsMap.get(comanda).execute();
//        }
//        scanner.close();
//    }
//}
