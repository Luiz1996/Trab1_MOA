package br.uem.din.moa.Controller;

import br.uem.din.moa.Model.City;

import java.util.List;

public class CityController {
    final int DEZ = 10;
    final int CEM = 100;
    final int MIL = 1000;

    //este método tem como objetivo realizar a impressão das cidades e suas respectivas distancias
    public void printCities(List<City> myCities){
        //imprimindo cabeçalho
        System.out.print(" ------ ");
        for(int i = 0; i < myCities.size(); i++){
            if(i < DEZ){
                System.out.print(" C00" + i + " ||");
            }else {
                if(i < CEM){
                    System.out.print(" C0" + i + " ||");
                }else{
                    System.out.print(" C" + i + " ||");
                }
            }
        }
        System.out.print("\n");

        //imprimindo distâncias
        for(int i = 0; i < myCities.size(); i++){
            if(i < DEZ){
                System.out.print("| C00" + i + " |");
            }else {
                if(i < CEM){
                    System.out.print("| C0" + i + " |");
                }else{
                    System.out.print("| C" + i + " |");
                }
            }

            for(int j = 0; j < myCities.size(); j++){
                if(myCities.get(i).getDistancias().get(j) < DEZ){
                    System.out.print("    " + myCities.get(i).getDistancias().get(j) + " ||");
                }else {
                    if(myCities.get(i).getDistancias().get(j) < CEM){
                        System.out.print("   " + myCities.get(i).getDistancias().get(j) + " ||");
                    }else{
                        if(myCities.get(i).getDistancias().get(j) < MIL){
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
}
