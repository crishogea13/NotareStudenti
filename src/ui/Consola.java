package ui;

import java.util.Scanner;

public class Consola {

    void afisareMeniu(){
        System.out.println("\n----------MENIU PRINCIPAL----------\n");
        System.out.println("1. MENIU STUDENTI");
        System.out.println("2. MENIU TEME");
        System.out.println("3. MENIU NOTE");
        System.out.println("0. IESIRE");
    }

    void meniuStudenti(){
        System.out.println("\n----------MENIU STUDENTI----------\n");
        System.out.println("1. Adauga un student");
        System.out.println("2. Modifica un student");
        System.out.println("3. Sterge un student");
        System.out.println("4. Afiseaza toti studentii");
        System.out.println("0. Revenire la meniul principal");
        //System.out.println("0. Iesire din aplicatie");
    }

    void meniuTeme(){
        System.out.println("\n----------MENIU TEME----------\n");
        System.out.println("1. Adauga o tema");
        System.out.println("2. Modifica o tema");
        System.out.println("3. Sterge o tema");
        System.out.println("4. Afiseaza toate temele");
        System.out.println("5. Prelungirea deadline-ului unei teme");
        System.out.println("0. Revenire la meniul principal");
        //System.out.println("7. Iesire din aplicatie");
    }

    void meniuNote(){
        System.out.println("\n----------MENIU NOTE----------\n");
        System.out.println("1. Adauga o nota pentru un anumit student la o tema de laborator");
        System.out.println("2. Afiseaza toate notele");
        System.out.println("0. Revenire la meniul principal");
    }

    String citesteString(String mesajDeAfisat){
        Scanner scanner = new Scanner(System.in);
        System.out.print(mesajDeAfisat);
        return scanner.nextLine();
    }
}
