/* Decompiler 8ms, total 134ms, lines 57 */
package com.muryllo.ia.tsp.models;

import com.muryllo.ia.tsp.interfaces.IAdaptable;
import com.muryllo.ia.tsp.interfaces.IChromosome;
import com.muryllo.ia.tsp.services.AlgorithmService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Route implements IChromosome<City>, IAdaptable {

  private boolean isFitnessChanged = true;
  private double fitness = 0;
  private ArrayList<City> cities = new ArrayList<City>();

  public Route(AlgorithmService geneticAlgorithm) {
    geneticAlgorithm.getInitialRoute().forEach((x) -> {
      this.cities.add(null);
    });
  }

  public Route(ArrayList<City> cities) {
    this.cities.addAll(cities);
    Collections.shuffle(this.cities);
  }

  public double calculateTotalDistance() {
    int citiesSize = this.cities.size();
    return (double) ((int) (this.cities.stream().mapToDouble((x) -> {
      int cityIndex = this.cities.indexOf(x);
      double returnValue = 0;

      if (cityIndex < citiesSize - 1) {
        returnValue = x.distance(this.cities.get(cityIndex + 1));
      }
      
      return returnValue;
    }).sum() + this.cities.get(0).distance(this.cities.get(citiesSize - 1))));
  }

  public ArrayList<City> getNucleotides() {
    this.isFitnessChanged = true;
    return this.cities;
  }

  public double getFitness() {
    if (this.isFitnessChanged) {
      this.fitness = 1.0D / this.calculateTotalDistance() * 10000.0D;
      this.isFitnessChanged = false;
    }
    return this.fitness;
  }

  public String toString() {
    return Arrays.toString(this.cities.toArray());
  }
}
