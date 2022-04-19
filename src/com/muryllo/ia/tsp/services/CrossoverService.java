/* Decompiler 61ms, total 186ms, lines 78 */
package com.muryllo.ia.tsp.services;

import java.util.ArrayList;
import java.util.stream.IntStream;

import com.muryllo.ia.tsp.interfaces.IAdaptable;
import com.muryllo.ia.tsp.interfaces.IChromosome;
import com.muryllo.ia.tsp.interfaces.IPopulation;

public class CrossoverService {

  protected AlgorithmService AlgorithmService;
  protected TournamentService TournamentService;

  public CrossoverService(AlgorithmService algorithmService, TournamentService tournamentService) {
    this.AlgorithmService = algorithmService;
    this.TournamentService = tournamentService;
  }

  private <N, I extends IChromosome<N> & IAdaptable> I fillNullsInCrossoverChromosome(I crossoverRoute, I route) {
    route.getNucleotides().stream().filter((x) -> {
      return !crossoverRoute.getNucleotides().contains(x);
    }).forEach((cityX) -> {
      for (int y = 0; y < route.getNucleotides().size(); ++y) {
        if (crossoverRoute.getNucleotides().get(y) == null) {
          crossoverRoute.getNucleotides().set(y, cityX);
          break;
        }
      }
    });
    return crossoverRoute;
  }

  public <N, I extends IChromosome<N> & IAdaptable> I crossoverChromosome(
    I chromosome1, 
    I chromosome2,
    Class<I> typeClass) throws Exception 
  {
    I crossoverChromosome;
    try {
      crossoverChromosome = typeClass.getDeclaredConstructor(AlgorithmService.getClass()).newInstance(AlgorithmService);
    } catch (Exception e) {
      throw new Exception("Couldn't crossover the Population.");
    }

    I tempChromosome1 = chromosome1;
    I tempChromosome2 = chromosome2;

    if (Math.random() < 0.5) {
      tempChromosome1 = chromosome2;
      tempChromosome2 = chromosome1;
    }

    for (int x = 0; x < crossoverChromosome.getNucleotides().size() / 2; ++x) {
      crossoverChromosome.getNucleotides().set(x, tempChromosome1.getNucleotides().get(x));
    }

    return this.fillNullsInCrossoverChromosome(crossoverChromosome, tempChromosome2);
  }

  public <N, I extends IChromosome<N> & IAdaptable, P extends IPopulation<N, I>> P crossoverPopulation(
    P population,
    Class<P> classPopulation, 
    Class<I> classChromosome) throws Exception 
  {
    P crossoverPopulation;
    try {
      crossoverPopulation = classPopulation.getDeclaredConstructor(ArrayList.class, int.class)
        .newInstance(AlgorithmService.InitialRoute, population.getChromosomes().size());
    } catch (Exception e) {
      throw new Exception("Couldn't crossover the Population.");
    }

    IntStream.range(0, this.AlgorithmService.NumberOfEliteRoutes).forEach((x) -> {
      crossoverPopulation.getChromosomes().set(x, population.getChromosomes().get(x));
    });

    IntStream.range(this.AlgorithmService.NumberOfEliteRoutes, crossoverPopulation.getChromosomes().size()).forEach((x) -> {
      try {
        I chromosome1 = this.TournamentService.selectPopulation(population, classPopulation).getChromosomes().get(0);
        I chromosome2 = this.TournamentService.selectPopulation(population, classPopulation).getChromosomes().get(0);
        crossoverPopulation.getChromosomes().set(x, this.crossoverChromosome(chromosome1, chromosome2, classChromosome));
      } catch (Exception e) {}
    });
    return crossoverPopulation;
  }
}
