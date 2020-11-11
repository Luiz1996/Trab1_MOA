package br.uem.din.moa.Controle;

import br.uem.din.moa.Model.City;
import br.uem.din.moa.Model.Route;

import java.util.ArrayList;
import java.util.List;

public class TravellingSalesman {
    final int DEZ = 10;
    final int CEM = 100;
    final int MIL = 1000;
    final int DEZ_MIL = 10000;
    int totalDistance;
    long initTime;
    long finalTime;
    long timeToSecond;

    //heurística do vizinho mais próximo
    public void nearestNeighborHeuristic_TSP(List<City> myCities) {
        System.out.println("Iniciando rota utilizando a Heurística do Vizinho Mais Próximo...");
        System.out.println("A primeira cidade a entrar na rota será a C000.");

        initTime = System.currentTimeMillis();

        //variáveis auxiliares
        totalDistance = 0;
        int actual = 0;
        int lastPosition;
        int[] citiesOnRoute = new int[(myCities.size() + 1)];
        List<Route> myRoute = new ArrayList<>();
        Route rt = new Route();

        //iniciando rota
        rt.setInitialVertex(0);
        myRoute.add(rt);

        //percorrendo todas as cidades
        for (int allNeighbors = 1; allNeighbors < (citiesOnRoute.length - 1); allNeighbors++) {
            lastPosition = (myRoute.size() - 1);
            //possui todos os vizinhos da cidade atual, partindo da cidade C000
            List<Integer> neighbors = myCities.get(actual).getDistancias();

            //variáveis auxiliares
            int bestNeighbor = 0;
            int bestDistance = Integer.MAX_VALUE;

            //para cada cidade vizinha da cidade atual, partindo de C000
            for (int currentNeighbor = 0; currentNeighbor < neighbors.size(); currentNeighbor++) {
                int actualDistance = neighbors.get(currentNeighbor);

                if ((citiesOnRoute[currentNeighbor] != currentNeighbor/*Validando se a cidade já não existe na rota*/) &&
                        (actualDistance > 0) &&
                        (actualDistance < bestDistance)) {
                    bestNeighbor = currentNeighbor;
                    bestDistance = actualDistance;
                }
            }

            //atualizando informações
            actual = bestNeighbor;
            citiesOnRoute[actual] = actual;
            myRoute.get(lastPosition).setFinalVertex(actual);
            myRoute.get(lastPosition).setVertexDistances(myCities.get(actual).getDistancias().get(myRoute.get(lastPosition).getInitialVertex()));

            //incluindo nova cidade na rota
            rt = new Route();
            rt.setInitialVertex(actual);
            myRoute.add(rt);
        }

        //terminando de setar informações na última cidade da rota
        lastPosition = (myRoute.size() - 1);
        myRoute.get(lastPosition).setFinalVertex(0);
        myRoute.get(lastPosition).setVertexDistances(myCities.get(myRoute.get(lastPosition).getFinalVertex()).getDistancias().get(myRoute.get(lastPosition).getInitialVertex()));

        //imprimindo tempo de execução
        finalTime = System.currentTimeMillis();
        timeToSecond = ((finalTime - initTime) / 1000);

        //Imprimindo resultados
        printRoute_TSP(myRoute, timeToSecond);
    }

    //heurística da inserção mais próxima
    public void nearestInsertionHeuristic_TSP(List<City> myCities) {
        System.out.println("Iniciando rota utilizando a Heurística da Inserção Mais Próxima...");
        System.out.println("O ciclo hamiltoniano inicial conterá as cidades C000, C001 e C002.");

        initTime = System.currentTimeMillis();

        //declarando vetor auxiliar de rota
        totalDistance = 0;
        int remainingCities = (myCities.size() - 3);
        int newVertexOnTheRoute = 0;
        int indexToBeChanged = 0;
        int[] citiesOnRoute = new int[myCities.size()];
        List<Route> myRoute;

        //inicializando ciclo hamiltoniano com os três primeiros vértices
        myRoute = startRoute_C000_C001_C002(myCities, citiesOnRoute);

        //este for realizará iterações até que todas as cidades sejam inseridas na rota
        for (int newCity = 0; newCity < remainingCities; newCity++) {
            int actualDistance = Integer.MAX_VALUE;

            //este for percorrerá cada cidade da rota para identificar a nova cidade mais próxima
            for (int route = 0; route < myRoute.size(); route++) {

                //este for representa a cidade atual, tem como finalidade validar se o mesmo já está na rota e se a distância satisfaz as condições mínimas
                //pula os índices 0, 1 e 2 pois estes ja estão no ciclo hamiltoniano inicial
                for (int actualCity = 3; actualCity < myCities.size(); actualCity++) {

                    //validando se a nova cidade deve ou não pertencer à rota
                    if ((citiesOnRoute[actualCity] != actualCity/*Validando se a cidade já não existe na rota*/) &&
                            (myCities.get(actualCity).getDistancias().get(myRoute.get(route).getFinalVertex()) < actualDistance)) {

                        //setando informações atualizadas
                        actualDistance = myCities.get(actualCity).getDistancias().get(myRoute.get(route).getFinalVertex());
                        indexToBeChanged = route;
                        newVertexOnTheRoute = actualCity;
                    }
                }
            }

            //atualizando variáveis
            citiesOnRoute[newVertexOnTheRoute] = newVertexOnTheRoute;

            //novo vértice que entrará na rota
            Route rt = new Route();
            rt.setInitialVertex(newVertexOnTheRoute);
            rt.setVertexDistances(myCities.get(newVertexOnTheRoute).getDistancias().get(myRoute.get(indexToBeChanged).getFinalVertex()));
            rt.setFinalVertex(myRoute.get(indexToBeChanged).getFinalVertex());

            //atualizando vértice que já existia na rota
            myRoute.get(indexToBeChanged).setFinalVertex(newVertexOnTheRoute);
            myRoute.get(indexToBeChanged).setVertexDistances(myCities.get(myRoute.get(indexToBeChanged).getInitialVertex()).getDistancias().get(newVertexOnTheRoute));

            //inserindo o novo vértice na rota e reposicionando(deslocando para a direita) todos os demais
            Route rtAux;
            for (int i = (indexToBeChanged + 1); i < myRoute.size(); i++) {
                rtAux = myRoute.get(i);
                myRoute.set(i, rt);
                rt = rtAux;
            }
            myRoute.add(rt);
        }

        //imprimindo tempo de execução
        finalTime = System.currentTimeMillis();
        timeToSecond = ((finalTime - initTime) / 1000);

        //Imprimindo resultados
        printRoute_TSP(myRoute, timeToSecond);
    }

    //método responsável por iniciar o ciclo contendo as cidades C000, C001 e C002
    private List<Route> startRoute_C000_C001_C002(List<City> myCities, int[] citiesOnRoute) {
        List<Route> myRoute = new ArrayList<>();

        //iniciando ciclo hamiltoniano com 3 vértices, partindo de C000 e retornando ao mesmo...
        for (int i = 0; i <= 2; i++) {
            Route rt = new Route();

            citiesOnRoute[i] = i;
            rt.setInitialVertex(i);

            if (i == 2) {
                rt.setFinalVertex((0));
                rt.setVertexDistances(myCities.get(i).getDistancias().get((0)));
            } else {
                rt.setFinalVertex((i + 1));
                rt.setVertexDistances(myCities.get(i).getDistancias().get((i + 1)));
            }

            myRoute.add(rt);
        }

        return myRoute;
    }

    //imprimindo a rota com a formatação adequada
    private void printRoute_TSP(List<Route> myRoute, long timeToSecond) {
        System.out.println("Rota realizada.: ");

        System.out.println("\t\t\t\t\t+------------------------+");
        System.out.println("\t\t\t\t\t| INIT  || DIST || FINL  |");
        System.out.println("\t\t\t\t\t|-------||------||-------|");

        for (Route route : myRoute) {
            System.out.print("\t\t\t\t\t");

            if (route.getInitialVertex() < DEZ) {
                System.out.print("| C00" + route.getInitialVertex() + "  ||");
            } else if (route.getInitialVertex() < CEM) {
                System.out.print("| C0" + route.getInitialVertex() + "  ||");
            } else if (route.getInitialVertex() < MIL) {
                System.out.print("| C" + route.getInitialVertex() + "  ||");
            } else {
                System.out.print("| C" + route.getInitialVertex() + " ||");
            }

            if (route.getVertexDistances() < DEZ) {
                System.out.print("    " + route.getVertexDistances() + " ||");
            } else if (route.getVertexDistances() < CEM) {
                System.out.print("   " + route.getVertexDistances() + " ||");
            } else if (route.getVertexDistances() < MIL) {
                System.out.print("  " + route.getVertexDistances() + " ||");
            } else if (route.getVertexDistances() < DEZ_MIL) {
                System.out.print(" " + route.getVertexDistances() + " ||");
            }

            if (route.getFinalVertex() < DEZ) {
                System.out.println(" C00" + route.getFinalVertex() + "  |");
            } else if (route.getFinalVertex() < CEM) {
                System.out.println(" C0" + route.getFinalVertex() + "  |");
            } else if (route.getFinalVertex() < MIL) {
                System.out.println(" C" + route.getFinalVertex() + "  |");
            } else {
                System.out.println(" C" + route.getFinalVertex() + " |");
            }
            totalDistance += route.getVertexDistances();
        }
        System.out.println("\t\t\t\t\t+------------------------+");
        System.out.println("Distância Total: " + totalDistance);
        System.out.println("Tempo de execução em segundos: " + timeToSecond + "s.");
    }
}
