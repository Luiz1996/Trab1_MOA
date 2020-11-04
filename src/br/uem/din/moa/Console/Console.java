package br.uem.din.moa.Console;

import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Console {

    public static void cleanDisplay() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    public static int showMenu() {
        Scanner in = new Scanner(System.in);
        System.out.println("+--- Menu do Problema do Caixeiro Viajante -----+");
        System.out.println("|  01) Importar Cidades                         |");
        System.out.println("|  02) Imprimir Cidades                         |");
        System.out.println("|  03) Calcular Vizinho Mais Próximo            |");
        System.out.println("|  04) Inserção Mais Próxima                    |");
        System.out.println("|  05) Resetar Informações                      |");
        System.out.println("|  06) Gerar cidades (Randômico)                |");
        System.out.println("|  00) Sair                                     |");
        System.out.println("+-----------------------------------------------+");
        System.out.print("Opcao: ");
        return in.nextInt();
    }
}
