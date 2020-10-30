package br.uem.din.moa.Controle;

import br.uem.din.moa.Model.City;

import java.util.List;

public class CityControle {

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
        System.out.println("");

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
            System.out.println("");
        }
    }
}
