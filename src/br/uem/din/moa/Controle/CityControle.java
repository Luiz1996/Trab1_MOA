package br.uem.din.moa.Controle;

import br.uem.din.moa.Model.City;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class CityControle {
    Scanner input = new Scanner(System.in);

    //este método tem como objetivo realizar a impressão das cidades e suas respectivas distancias
    public void printCities(List<City> myCities){
        //imprimindo cabeçalho
        System.out.print(" ------ ");
        for(int i = 0; i < myCities.size(); i++){
            if(i < 10){
                System.out.print(" C00" + i + " ||");
            }else {
                if(i < 100){
                    System.out.print(" C0" + i + " ||");
                }else{
                    System.out.print(" C" + i + " ||");
                }
            }
        }
        System.out.print("\n");

        //imprimindo distâncias
        for(int i = 0; i < myCities.size(); i++){
            if(i < 10){
                System.out.print("| C00" + i + " |");
            }else {
                if(i < 100){
                    System.out.print("| C0" + i + " |");
                }else{
                    System.out.print("| C" + i + " |");
                }
            }

            for(int j = 0; j < myCities.size(); j++){
                if(myCities.get(i).getDistancias().get(j) < 10){
                    System.out.print("    " + myCities.get(i).getDistancias().get(j) + " ||");
                }else {
                    if(myCities.get(i).getDistancias().get(j) < 100){
                        System.out.print("   " + myCities.get(i).getDistancias().get(j) + " ||");
                    }else{
                        if(myCities.get(i).getDistancias().get(j) < 1000){
                            System.out.print("  " + myCities.get(i).getDistancias().get(j) + " ||");
                        }else{
                            System.out.print(" " + myCities.get(i).getDistancias().get(j) + " ||");
                        }
                    }
                }
            }
            System.out.print("\n");
        }
    }

    public void makeCitiesFile() throws IOException {

        input = new Scanner(System.in);
        int numberOfCities;
        int maxDistanceValue;
        int distanceRandom;
        Random random = new Random();
        String pathFile;
        StringBuilder textFile = new StringBuilder();
        JFileChooser selectPathFile = new JFileChooser();

        System.out.print("Quantas cidades há no problema?(> 0 e < 1000)\nR:");

        try {
            numberOfCities = input.nextInt();
        } catch (InputMismatchException e) {
            JOptionPane.showMessageDialog(null, "O valor informado é inválido.", "Falha", JOptionPane.ERROR_MESSAGE);
            return;
        }

        System.out.print("\n\nQual a distância máxima entre as cidades?(> 0 e < 10.000)\nR:");

        try {
            maxDistanceValue = input.nextInt();
        } catch (InputMismatchException e) {
            JOptionPane.showMessageDialog(null, "O valor informado é inválido.", "Falha", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //realizando validações para garantir que algum dado será gerado, contudo, que não atrapalhe a formatação
        if(numberOfCities > 999 || maxDistanceValue > 9999 || numberOfCities <= 0 || maxDistanceValue <= 0){
            JOptionPane.showMessageDialog(null, "O número de cidades ou a distância entre as cidades não são válidos.", "Falha", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //declarando matriz
        int[][] matriz =  new int[numberOfCities][numberOfCities];

        //gerando dados das novas cidades
        for(int  i = 0; i < numberOfCities; i++){
            for(int j = (i + 1); j < numberOfCities; j++){
                if(i != j){
                    distanceRandom = random.nextInt(maxDistanceValue);
                    matriz[i][j] = distanceRandom;
                    matriz[j][i] = distanceRandom;
                }else{
                    matriz[i][j] = 0;
                }
            }
        }

        //setando extensão dos filtros
        selectPathFile.setFileFilter(new FileNameExtensionFilter("Texto (*.txt)", "txt"));

        //selecionando caminho e nome do arquivo .txt
        if (selectPathFile.showDialog(null, "Salvar") == JFileChooser.APPROVE_OPTION) {
            pathFile = (selectPathFile.getSelectedFile().getPath()).trim();
            pathFile = pathFile.replaceAll(".txt", "");
            pathFile = pathFile.concat(".txt");
        } else {
            JOptionPane.showMessageDialog(null, "As novas cidades não foram salvas em arquivo .txt.", "Falha", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //escrevendo o arquivo no caminho pré selecionado
        try (FileWriter fl = new FileWriter(pathFile.trim())) {
            PrintWriter saveFile = new PrintWriter(fl);

            for(int i = 0; i < numberOfCities; i++){
                for(int j = 0; j < numberOfCities; j++){
                    if(j == (numberOfCities - 1)){
                        textFile.append(matriz[i][j]).append("\n");
                    }else{
                        textFile.append(matriz[i][j]).append(", ");
                    }
                }
            }
            saveFile.printf(textFile.toString());
        }

        JOptionPane.showMessageDialog(null, "As cidades foram geradas com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
}
