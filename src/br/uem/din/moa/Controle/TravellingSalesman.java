package br.uem.din.moa.Controle;

import br.uem.din.moa.Model.City;
import br.uem.din.moa.Model.Route;

import java.util.ArrayList;
import java.util.List;

public class TravellingSalesman {
    int totalDistance;

    //heurística do vizinho mais próximo
    public void nearestNeighborHeuristic_TSP(List<City> myCities){
        //variáveis auxiliares
        totalDistance = 0;
        int actual = 0;
        int[] route =  new int[(myCities.size() + 1)];

        System.out.println("Iniciando rota utilizando a Heurística do Vizinho Mais Próximo...");

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
        printRoute_nearestNeighborHeuristic_TSP(route, totalDistance);
    }

    //método responsável por validar se uma determinada cidade já existe na rota
    public boolean existsOnTheRoute(int[] route, int actualCity){
        for (int i : route) {
            if (i == actualCity)
                return true;
        }
        return false;
    }

    //imprimindo a rota do vizinho mais próximo respeitando a formatação adequada
    public void printRoute_nearestNeighborHeuristic_TSP(int[] route, int totalDistance){
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

    //heurística da inserção mais próxima
    public void nearestInsertionHeuristic_TSP(List<City> myCities){
        //variáveis auxiliares
        totalDistance = 0;
        List<Route> myRoute;

        //inicializando ciclo hamiltoniano com os três primeiros vértices
        myRoute = startRoute_0_1_2(myCities);

        System.out.println("Iniciando rota utilizando a Heurística da Inserção Mais Próxima...");
        System.out.println("O ciclo hamiltoniano inicial conterá as cidades C000, C001 e C002.");

        /*inicio*/

        /*fim*/

        //Imprimindo resultados
        printRoute_nearestInsertionHeuristic_TSP(myRoute);
    }

    //método responsável por iniciar o ciclo contendo as cidades C000, C001 e C002
    private List<Route> startRoute_0_1_2(List<City> myCities) {
        List<Route> myRoute = new ArrayList<>();

        //iniciando ciclo hamiltoniano com 3 vértices, iniciando em C000
        for(int i = 0; i <= 2; i++){
            Route rt = new Route();

            rt.setInitialVertex(i);

            if(i == 2){
                rt.setFinalVertec((0));
                totalDistance += myCities.get(i).getDistancias().get((0));
                rt.setVertexDistances(myCities.get(i).getDistancias().get((0)));
            }else{
                rt.setFinalVertec((i + 1));
                totalDistance += myCities.get(i).getDistancias().get((i + 1));
                rt.setVertexDistances(myCities.get(i).getDistancias().get((i + 1)));
            }

            myRoute.add(rt);
        }

        return myRoute;
    }

    //imprimindo a rota da inserção mais próxima com a formataçãoa adequada
    public void printRoute_nearestInsertionHeuristic_TSP(List<Route> myRoute){
        System.out.println("Distância Total: " + totalDistance);
        System.out.println("Rota realizada.: ");

        System.out.println("\t\t\t\t\t| INIT || DIST || FINL |");
        System.out.println("\t\t\t\t\t|------||------||------|");

        for (Route route : myRoute) {
            System.out.print("\t\t\t\t\t");

            if (route.getInitialVertex() < 10) {
                System.out.print("| C00" + route.getInitialVertex() + " ||");
            } else if (route.getInitialVertex() < 100) {
                System.out.print("| C0" + route.getInitialVertex() + " ||");
            } else if (route.getInitialVertex() < 1000) {
                System.out.print("| C" + route.getInitialVertex() + " ||");
            }

            if (route.getVertexDistances() < 10) {
                System.out.print("    " + route.getVertexDistances() + " ||");
            } else if (route.getVertexDistances() < 100) {
                System.out.print("   " + route.getVertexDistances() + " ||");
            } else if (route.getVertexDistances() < 1000) {
                System.out.print("  " + route.getVertexDistances() + " ||");
            } else if (route.getVertexDistances() < 10000) {
                System.out.print(" " + route.getVertexDistances() + " ||");
            }

            if (route.getFinalVertec() < 10) {
                System.out.println(" C00" + route.getFinalVertec() + " |");
            } else if (route.getFinalVertec() < 100) {
                System.out.println(" C0" + route.getFinalVertec() + " |");
            } else if (route.getFinalVertec() < 1000) {
                System.out.println(" C" + route.getFinalVertec() + " |");
            }
        }
    }
}
