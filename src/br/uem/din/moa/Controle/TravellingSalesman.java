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

        initTime = System.currentTimeMillis();

        //variáveis auxiliares
        totalDistance = 0;
        int actual = 0;
        int[] routeTemp = new int[(myCities.size() + 1)];

        //percorrendo todas as cidades
        for (int allNeighbors = 1; allNeighbors < (routeTemp.length - 1); allNeighbors++) {
            //possui todos os vizinhos da cidade atual, partindo da cidade C000
            List<Integer> neighbors = myCities.get(actual).getDistancias();

            //variáveis auxiliares
            int bestNeighbor = 0;
            int bestDistance = Integer.MAX_VALUE;

            //para cada cidade vizinha da cidade atual, partindo de C000
            for (int currentNeighbor = 0; currentNeighbor < neighbors.size(); currentNeighbor++) {
                int actualDistance = neighbors.get(currentNeighbor);

                if ((notExistsOnTheRoute(routeTemp, currentNeighbor)) &&
                        (actualDistance > 0) &&
                        (actualDistance < bestDistance)) {
                    bestNeighbor = currentNeighbor;
                    bestDistance = actualDistance;
                }
            }

            //atualizando informações
            actual = bestNeighbor;
            routeTemp[allNeighbors] = actual;
        }

        //imprimindo tempo de execução
        finalTime = System.currentTimeMillis();
        timeToSecond = ((finalTime - initTime) / 1000);

        //O trecho abaixo servirá somente para seguirmos um mesmo padrão de representação e impressão de rotas
        //Note que para o Vizinho Mais Próximo, todas as iterações e alimentação de informações foram num vetor[int] e não
        //em um List<Route>, a ideia é seguir o padrão de List<Route>
        List<Route> myRoute = buildRoute(routeTemp, myCities);

        //Imprimindo resultados
        printRoute_TSP(myRoute, timeToSecond);
    }

    private List<Route> buildRoute(int[] routeTemp, List<City> myCities) {
        List<Route> myRoute = new ArrayList<>();

        for (int i = 0; i < (routeTemp.length - 1); i++) {
            Route rt = new Route();

            rt.setInitialVertex(routeTemp[i]);
            if (i == ((routeTemp.length - 1) - 1)) {
                rt.setVertexDistances(myCities.get(routeTemp[i]).getDistancias().get(0));
                rt.setFinalVertex(0);
            } else {
                rt.setVertexDistances(myCities.get(routeTemp[i]).getDistancias().get(routeTemp[(i + 1)]));
                rt.setFinalVertex(routeTemp[(i + 1)]);
            }
            myRoute.add(rt);
        }

        return myRoute;
    }

    //método responsável por validar se uma determinada cidade já existe na rota
    private boolean notExistsOnTheRoute(int[] route, int actualCity) {
        for (int i : route) {
            if (i == actualCity)
                return false;
        }
        return true;
    }

    //heurística da inserção mais próxima
    public void nearestInsertionHeuristic_TSP(List<City> myCities) {
        System.out.println("Iniciando rota utilizando a Heurística da Inserção Mais Próxima...");
        System.out.println("O ciclo hamiltoniano inicial conterá as cidades C000, C001 e C002.");

        initTime = System.currentTimeMillis();

        //declarando vetor auxiliar de rota
        totalDistance = 0;
        int remainingCities = (myCities.size() - 3);
        int[] citiesOnRoute = new int[myCities.size()];
        citiesOnRoute[0] = -1;
        List<Route> myRoute;

        //inicializando ciclo hamiltoniano com os três primeiros vértices
        myRoute = startRoute_C000_C001_C002(myCities);
        citiesOnRoute[0] = 0;
        citiesOnRoute[1] = 1;
        citiesOnRoute[2] = 2;

        //este for realizará iterações até que todas as cidades sejam inseridas na rota
        for (int newCity = 0; newCity < remainingCities; newCity++) {
            int actualDistance = Integer.MAX_VALUE;
            int nearestVertex = 0;
            int newVertexOnTheRoute = 0;
            int auxiliaryVertex = 0;

            //este for percorrerá cada cidade da rota para identificar a nova cidade mais próxima
            for (Route route : myRoute) {

                //este for representa a cidade atual, tem como finalidade validar se o mesmo já está na rota e se a distância satisfaz as condições mínimas
                for (int actualCity = 0; actualCity < myCities.size(); actualCity++) {
                    //validando se o novo vértice deve ou não pertencer à rota
                    if ((citiesOnRoute[actualCity] != actualCity) &&
                            (myCities.get(actualCity).getDistancias().get(route.getFinalVertex()) < actualDistance)) {

                        //setando informações atualizadas
                        actualDistance = myCities.get(actualCity).getDistancias().get(route.getFinalVertex());
                        nearestVertex = route.getInitialVertex();
                        auxiliaryVertex = route.getFinalVertex();
                        newVertexOnTheRoute = actualCity;
                    }

                }
            }

            //atualizando variáveis
            citiesOnRoute[newVertexOnTheRoute] = newVertexOnTheRoute;

            //obtendo o vertice a ser atualizado, sabemos que a partir dele tudo precisa ser reposicionado
            int indexToBeChanged = returnsIndexToBeChanged(myRoute, nearestVertex, auxiliaryVertex);

            //atualizando vértice que já existia na rota
            myRoute.get(indexToBeChanged).setFinalVertex(newVertexOnTheRoute);
            myRoute.get(indexToBeChanged).setVertexDistances(myCities.get(nearestVertex).getDistancias().get(newVertexOnTheRoute));

            //novo vértice que entrará na rota
            Route rt = new Route();
            rt.setInitialVertex(newVertexOnTheRoute);
            rt.setVertexDistances(myCities.get(newVertexOnTheRoute).getDistancias().get(auxiliaryVertex));
            rt.setFinalVertex(auxiliaryVertex);

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

    //este método é responsável por retornar o primeiro índice a ser atualizado, dele em diante todas serão alterados ou reposicionados(deslocamento para direita)
    private int returnsIndexToBeChanged(List<Route> myRoute, int nearestVertex, int auxiliaryVertex) {
        int findIndex = 0;

        //percorre todas as rotas encontrando uma aresta que satisfaça as condições de igualdade entre vertice inicial e final
        for (int index = 0; index < myRoute.size(); index++) {
            if (((myRoute.get(index).getInitialVertex() == nearestVertex) && (myRoute.get(index).getFinalVertex() == auxiliaryVertex)) ||
                    ((myRoute.get(index).getFinalVertex() == nearestVertex) && (myRoute.get(index).getInitialVertex() == auxiliaryVertex))) {
                findIndex = index;
            }
        }
        return findIndex;
    }

    //método responsável por iniciar o ciclo contendo as cidades C000, C001 e C002
    private List<Route> startRoute_C000_C001_C002(List<City> myCities) {
        List<Route> myRoute = new ArrayList<>();

        //iniciando ciclo hamiltoniano com 3 vértices, partindo de C000 e retornando ao mesmo...
        for (int i = 0; i <= 2; i++) {
            Route rt = new Route();

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

        System.out.println("\t\t\t\t\t+----------------------+");
        System.out.println("\t\t\t\t\t| INIT || DIST || FINL |");
        System.out.println("\t\t\t\t\t|------||------||------|");

        for (Route route : myRoute) {
            System.out.print("\t\t\t\t\t");

            if (route.getInitialVertex() < DEZ) {
                System.out.print("| C00" + route.getInitialVertex() + " ||");
            } else if (route.getInitialVertex() < CEM) {
                System.out.print("| C0" + route.getInitialVertex() + " ||");
            } else if (route.getInitialVertex() < MIL) {
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
                System.out.println(" C00" + route.getFinalVertex() + " |");
            } else if (route.getFinalVertex() < CEM) {
                System.out.println(" C0" + route.getFinalVertex() + " |");
            } else if (route.getFinalVertex() < MIL) {
                System.out.println(" C" + route.getFinalVertex() + " |");
            }
            totalDistance += route.getVertexDistances();
        }
        System.out.println("\t\t\t\t\t+----------------------+");
        System.out.println("Distância Total: " + totalDistance);
        System.out.println("Tempo de execução em segundos: " + timeToSecond + "s.");
    }
}
