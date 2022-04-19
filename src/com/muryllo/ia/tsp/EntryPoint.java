/* Decompiler 10ms, total 140ms, lines 39 */
package com.muryllo.ia.tsp;

import java.util.Arrays;
import java.util.ArrayList;

import com.muryllo.ia.tsp.models.City;
import com.muryllo.ia.tsp.models.Population;
import com.muryllo.ia.tsp.services.AlgorithmService;

public class EntryPoint {

  public static final double MUTATION_RATE = 0.1;
  public static final int TOURNAMENT_SELECTION_SIZE = 3;
  public static final int NUMBER_OF_ROUTES = 100;
  public static final int NUMBER_OF_ELITE_ROUTES = 10;
  public static final int NUMBER_OF_GENERATIONS = 30;

  public static ArrayList<City> InitialPopulationOfRoutes = new ArrayList<City>(
    Arrays.asList(
      new City("Boston", 42.3601D, -71.0589D),
      new City("Houston", 29.7604D, -95.3698D),
      new City("Austin", 30.2672D, -97.7431D),
      new City("San Francisco", 37.7749D, -122.4194D),
      new City("Denver", 39.7392D, -104.9903D),
      new City("Los Angeles", 34.0522D, -118.2437D),
      new City("Chicago", 41.8781D, -87.6298D),
      new City("New York", 40.7128D, -74.0059D),
      new City("Dallas", 32.7767D, -96.797D),
      new City("Seattle", 47.6062D, -122.3321D),
      new City("Sydney", -33.8675D, 151.207D),
      new City("Tokyo", 35.6895D, 139.6917D),
      new City("Cape Town", -33.9249D, 18.4241D),
      new City("Miami", 25.7617D, -80.1918D),
      new City("Washington", 47.7511D, -120.7401D)
    )
  );

  public static void main(String[] args) {
    Population population = new Population(InitialPopulationOfRoutes, NUMBER_OF_ROUTES);
    population.sortRoutesByFitness();

    AlgorithmService geneticAlgorithm = new AlgorithmService(
        InitialPopulationOfRoutes,
        MUTATION_RATE,
        NUMBER_OF_ROUTES,
        NUMBER_OF_ELITE_ROUTES,
        NUMBER_OF_GENERATIONS,
        TOURNAMENT_SELECTION_SIZE);

    int generationNumber = 0;

    showHeading(generationNumber++);
    showPopulation(population);

    while (generationNumber < NUMBER_OF_GENERATIONS) {
      showHeading(generationNumber++);
      population = geneticAlgorithm.evolve(population);
      population.sortRoutesByFitness();
      showPopulation(population);
    }

    System.out.println("Melhor rota encontrada ate agora: " + population.getChromosomes().get(0));
    System.out.println("c/ uma distancia de: " + String.format("%.2f", (population.getChromosomes().get(0)).calculateTotalDistance()) + " km");
  }

  private static void showPopulation(Population population) {
    population.getChromosomes().forEach(x -> {
      System.out.println(
        Arrays.toString(x.getNucleotides().toArray()) + " |  " + 
        String.format("%.4f", x.getFitness()) + "   |  " + 
        String.format("%.2f", x.calculateTotalDistance()));
    });
    System.out.println("");
  }

  private static void showHeading(int generationNumber) {
    System.out.println("> Geracao # " + generationNumber);
    String headingColumn1 = "Rota";
    String remainingHeadingColumns = "Aptidao   | Distancia (em milhas)";
    int cityNamesLength = 0;
    for (int x = 0; x < InitialPopulationOfRoutes.size(); x++)
      cityNamesLength += InitialPopulationOfRoutes.get(x).getName().length();
    int arrayLength = cityNamesLength + InitialPopulationOfRoutes.size() * 2;
    int partialLength = (arrayLength - headingColumn1.length()) / 2;
    for (int x = 0; x < partialLength; x++)
      System.out.print(" ");
    System.out.print(headingColumn1);
    for (int x = 0; x < partialLength; x++)
      System.out.print(" ");
    if ((arrayLength % 2) == 0)
      System.out.print(" ");
    System.out.println(" | " + remainingHeadingColumns);
    cityNamesLength += remainingHeadingColumns.length() + 3;
    for (int x = 0; x < cityNamesLength + InitialPopulationOfRoutes.size() * 2; x++)
      System.out.print("-");
    System.out.println("");
  }

}
