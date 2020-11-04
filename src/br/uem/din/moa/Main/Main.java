package br.uem.din.moa.Main;

import br.uem.din.moa.Console.Console;
import br.uem.din.moa.Controle.TravellingSalesman;
import br.uem.din.moa.Controle.CityControle;
import br.uem.din.moa.Controle.File;
import br.uem.din.moa.Model.City;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        List<City> myCities = new ArrayList<>();
        File fl = new File();
        CityControle cCtrl = new CityControle();
        TravellingSalesman pcv = new TravellingSalesman();

        int option = Console.showMenu();
        while (option != 0) {
            Console.cleanDisplay();
            switch (option) {
                case 1:
                    if(myCities.size() > 0){
                        JOptionPane.showMessageDialog(null, "Para reimportar as cidades, use a Opção 5 e resete as informações.", "Falha", JOptionPane.ERROR_MESSAGE);
                    }else{
                        myCities = fl.importCities(myCities);
                        JOptionPane.showMessageDialog(null, "O arquivo foi importado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                case 2:
                    if(myCities.size() > 0){
                        cCtrl.printCities(myCities);
                    }else{
                        JOptionPane.showMessageDialog(null, "Use a Opção 1 para importar as cidades na aplicação.", "Falha", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case 3:
                    if(myCities.size() > 0){
                        pcv.nearestNeighbor(myCities);
                    }else{
                        JOptionPane.showMessageDialog(null, "Use a Opção 1 para importar as cidades na aplicação.", "Falha", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case 4:
                    if(myCities.size() > 0){
                        pcv.closestInsertion(myCities);
                    }else{
                        JOptionPane.showMessageDialog(null, "Use a Opção 1 para importar as cidades na aplicação.", "Falha", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case 5:
                    myCities = new ArrayList<>();
                    JOptionPane.showMessageDialog(null, "As cidades foram apagadas com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 6:
                    cCtrl.makeCitiesFile();
                    Console.cleanDisplay();
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
            option = Console.showMenu();
        }
        System.gc();
    }
}
