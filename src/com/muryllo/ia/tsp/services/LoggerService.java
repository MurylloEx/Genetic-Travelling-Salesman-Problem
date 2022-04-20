package com.muryllo.ia.tsp.services;

import java.util.Arrays;
import java.util.ArrayList;

import com.muryllo.ia.tsp.models.City;
import com.muryllo.ia.tsp.models.Population;

public class LoggerService {

  public static void printPopulation(Population population) {
    population.getChromosomes().forEach((x) -> {
      System.out.println(
        Arrays.toString(x.getNucleotides().toArray()) + " |  " + 
        String.format("%.4f", x.getFitness()) + "   |  " + 
        String.format("%.2f", x.calculateTotalDistance()));
    });
    System.out.println("");
  }

  public static void printHeading(int generationNumber, ArrayList<City> currentPopulation) {
    System.out.println("> Geracao # " + generationNumber);
    String headingColumn1 = "Rota";
    String remainingHeadingColumns = "Aptidao   | Distancia (em milhas)";
    int cityNamesLength = 0;
    int arrayLength = 0;

    for (arrayLength = 0; arrayLength < currentPopulation.size(); ++arrayLength) {
      cityNamesLength += ((City) currentPopulation.get(arrayLength)).getName().length();
    }

    arrayLength = cityNamesLength + currentPopulation.size() * 2;
    int partialLength = (arrayLength - headingColumn1.length()) / 2;

    for (int x = 0; x < partialLength; ++x) {
      System.out.print(" ");
    }

    System.out.print(headingColumn1);
    
    for (int x = 0; x < partialLength; ++x) {
      System.out.print(" ");
    }
    if (arrayLength % 2 == 0) {
      System.out.print(" ");
    }

    System.out.println(" | " + remainingHeadingColumns);
    cityNamesLength += remainingHeadingColumns.length() + 3;

    for (int x = 0; x < cityNamesLength + currentPopulation.size() * 2; ++x) {
      System.out.print("-");
    }

    System.out.println("");
  }
}
