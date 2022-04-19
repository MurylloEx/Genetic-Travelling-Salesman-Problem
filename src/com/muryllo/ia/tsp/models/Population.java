package com.muryllo.ia.tsp.models;

import com.muryllo.ia.tsp.interfaces.IPopulation;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Population implements IPopulation<City, Route> {
  
  private ArrayList<Route> Routes;

  public Population(ArrayList<City> initialRoute, int populationSize) {
    this.Routes = new ArrayList<Route>(populationSize);
    IntStream.range(0, populationSize).forEach((x) -> {
      this.Routes.add(new Route(initialRoute));
    });
  }

  public ArrayList<Route> getChromosomes() {
    return this.Routes;
  }

  public void sortRoutesByFitness() {
    this.Routes.sort((route1, route2) -> {
      int flag = 0;
      if (route1.getFitness() > route2.getFitness()) {
        flag = -1;
      } else if (route1.getFitness() < route2.getFitness()) {
        flag = 1;
      }
      return flag;
    });
  }
}
