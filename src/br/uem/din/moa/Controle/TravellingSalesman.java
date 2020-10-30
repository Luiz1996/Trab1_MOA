package br.uem.din.moa.Controle;

import br.uem.din.moa.Model.City;

import java.util.List;

public class TravellingSalesman {

    public void nearestNeighbor(List<City> myCities){
        //variáveis auxiliares
        int actual = 0;
        int[] route =  new int[(myCities.size() + 1)];
        int totalDistance = 0;

        //percorrendo todas as cidades
        for(int allNeighbors = 1; allNeighbors < (route.length - 1); allNeighbors++){
            //possui todos os vizinhos da cidade atual, partindo da cidade C000
            List<Integer> neighbors = myCities.get(actual).getDistancias();

            //variáveis auxiliares
            int bestNeighbor = 0;
            int bestDistance = Integer.MAX_VALUE;

            //para cada cidade vizinha da cidade atual, partindo de C000
            for(int currentNeighbor = 0; currentNeighbor < neighbors.size(); currentNeighbor++){

                int actualDistance = neighbors.get(currentNeighbor);

                if(
                        (!existsOnTheRoute(route, currentNeighbor)) &&
                        (actualDistance > 0) &&
                        (actualDistance < bestDistance)
                ){
                    bestNeighbor = currentNeighbor;
                    bestDistance = actualDistance;
                }
            }

            //atualizando informações
            actual = bestNeighbor;
            route[allNeighbors] = actual;
            totalDistance += bestDistance;
        }

        //finalizando cálculo das distâncias acumuladas
        totalDistance += myCities.get(actual).getDistancias().get(0);

        //Imprimindo resultados
        printRoute(route, totalDistance);
    }

    public boolean existsOnTheRoute(int[] route, int actualCity){
        for (int i : route) {
            if (i == actualCity)
                return true;
        }
        return false;
    }

    public void printRoute(int[] route, int totalDistance){
        System.out.println("Distância Total: " + totalDistance);
        System.out.print("Rota realizada.: [");

        for(int i = 0; i < route.length; i++){
            //tratando formatação das informações
            if(route[i] < 10){
                System.out.print("C00" + route[i]);
            }else {
                if(route[i] < 100){
                    System.out.print("C0" + route[i]);
                }else{
                    System.out.print("C" + route[i]);
                }
            }

            if(i != route.length-1){
                System.out.print(", ");
            }
        }
        System.out.print("]\n");
    }

    public void closestInsertion(List<City> myCities){
        System.out.println("inserção mais proxima");
    }
}
