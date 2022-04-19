/* Decompiler 6ms, total 131ms, lines 43 */
package com.muryllo.ia.tsp.services;

import java.util.ArrayList;

import com.muryllo.ia.tsp.models.City;
import com.muryllo.ia.tsp.models.Route;
import com.muryllo.ia.tsp.models.Population;

public class AlgorithmService {

  protected double MutationRate = 0.25D;
  protected int NumberOfRoutes = 100;
  protected int NumberOfEliteRoutes = 1;
  protected int NumberOfGenerations = 30;
  protected int TournamentSelectionSize = 3;
  
  protected ArrayList<City> InitialRoute;
  protected TournamentService TournamentService;
  protected MutationService MutationService;
  protected CrossoverService CrossoverService;

  public AlgorithmService(
    ArrayList<City> initialRoute, 
    double mutationRate, 
    int numberOfRoutes, 
    int numberOfEliteRoutes, 
    int numberOfGenerations, 
    int tournamentSelectionSize) 
  {
    this.InitialRoute = initialRoute;
    this.MutationRate = mutationRate;
    this.NumberOfRoutes = numberOfRoutes;
    this.NumberOfEliteRoutes = numberOfEliteRoutes;
    this.NumberOfGenerations = numberOfGenerations;
    this.TournamentSelectionSize = tournamentSelectionSize;
    this.TournamentService = new TournamentService(this);
    this.MutationService = new MutationService(this);
    this.CrossoverService = new CrossoverService(this, this.TournamentService);
  }

  public ArrayList<City> getInitialRoute() {
    return this.InitialRoute;
  }

  public Population evolve(Population population) {
    try {
      return MutationService
        .mutatePopulation(CrossoverService.crossoverPopulation(population, Population.class, Route.class));
    } catch (Exception e) {
      return population;
    }
  }
  
}
